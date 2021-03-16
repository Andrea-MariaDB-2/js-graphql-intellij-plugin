/*
 * Copyright (c) 2018-present, Jim Kynde Meyer
 * All rights reserved.
 * <p>
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.intellij.lang.jsgraphql.schema;

import com.google.common.collect.Maps;
import com.intellij.json.JsonFileType;
import com.intellij.lang.jsgraphql.GraphQLFileType;
import com.intellij.lang.jsgraphql.GraphQLLanguage;
import com.intellij.lang.jsgraphql.endpoint.ide.project.JSGraphQLEndpointNamedTypeRegistry;
import com.intellij.lang.jsgraphql.ide.editor.GraphQLIntrospectionService;
import com.intellij.lang.jsgraphql.ide.project.GraphQLInjectionSearchHelper;
import com.intellij.lang.jsgraphql.ide.project.GraphQLPsiSearchHelper;
import com.intellij.lang.jsgraphql.ide.project.graphqlconfig.GraphQLConfigManager;
import com.intellij.lang.jsgraphql.psi.GraphQLFile;
import com.intellij.lang.jsgraphql.psi.GraphQLPsiUtil;
import com.intellij.lang.jsgraphql.schema.builder.GraphQLCompositeRegistry;
import com.intellij.lang.jsgraphql.types.GraphQLException;
import com.intellij.lang.jsgraphql.types.InvalidSyntaxError;
import com.intellij.lang.jsgraphql.types.language.SourceLocation;
import com.intellij.lang.jsgraphql.types.schema.idl.TypeDefinitionRegistry;
import com.intellij.lang.jsgraphql.types.schema.idl.errors.SchemaProblem;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.intellij.lang.jsgraphql.schema.GraphQLSchemaKeys.*;

public class GraphQLRegistryProvider implements Disposable {

    private static final Logger LOG = Logger.getInstance(GraphQLRegistryProvider.class);

    private final GraphQLPsiSearchHelper graphQLPsiSearchHelper;
    private final Project project;
    private final GlobalSearchScope graphQLFilesScope;
    private final GlobalSearchScope jsonIntrospectionScope;
    private final PsiManager psiManager;
    private final JSGraphQLEndpointNamedTypeRegistry graphQLEndpointNamedTypeRegistry;
    private final GraphQLConfigManager graphQLConfigManager;

    private final Map<GlobalSearchScope, GraphQLTypeDefinitionRegistry> scopeToRegistry = Maps.newConcurrentMap();

    public static GraphQLRegistryProvider getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, GraphQLRegistryProvider.class);
    }

    public GraphQLRegistryProvider(Project project) {
        this.project = project;
        graphQLFilesScope = GlobalSearchScope.getScopeRestrictedByFileTypes(GlobalSearchScope.projectScope(project), GraphQLFileType.INSTANCE);
        jsonIntrospectionScope = GlobalSearchScope.getScopeRestrictedByFileTypes(GlobalSearchScope.projectScope(project), JsonFileType.INSTANCE);
        psiManager = PsiManager.getInstance(project);
        graphQLEndpointNamedTypeRegistry = JSGraphQLEndpointNamedTypeRegistry.getService(project);
        graphQLPsiSearchHelper = GraphQLPsiSearchHelper.getInstance(project);
        graphQLConfigManager = GraphQLConfigManager.getService(project);

        project.getMessageBus().connect(this).subscribe(GraphQLSchemaChangeListener.TOPIC, schemaVersion -> {
            scopeToRegistry.clear();
        });
    }

    @NotNull
    public GraphQLTypeDefinitionRegistry getRegistry(@NotNull PsiElement scopedElement) {
        // Get the search scope that limits schema definition for the scoped element
        GlobalSearchScope schemaScope = graphQLPsiSearchHelper.getSchemaScope(scopedElement);

        return scopeToRegistry.computeIfAbsent(schemaScope, s -> {
            List<GraphQLException> errors = new ArrayList<>();
            Ref<Boolean> processedGraphQL = Ref.create(false);
            GraphQLSchemaDocumentProcessor processor = new GraphQLSchemaDocumentProcessor(processedGraphQL);

            // GraphQL files
            FileTypeIndex.processFiles(GraphQLFileType.INSTANCE, file -> {
                PsiFile psiFile = psiManager.findFile(file);
                if (psiFile != null) {
                    processor.process(psiFile);
                }
                return true;
            }, graphQLFilesScope.intersectWith(schemaScope));

            // JSON GraphQL introspection result files
            if (!graphQLConfigManager.getConfigurationsByPath().isEmpty()) {
                // need one or more configurations to be able to point "schemaPath" to relevant JSON files
                // otherwise all JSON files would be in scope
                FileTypeIndex.processFiles(JsonFileType.INSTANCE, file -> {
                    // only JSON files that are directly referenced as "schemaPath" from the .graphqlconfig will be
                    // considered within scope, so we can just go ahead and try to turn the JSON into GraphQL
                    final PsiFile psiFile = psiManager.findFile(file);
                    if (psiFile != null) {
                        try {
                            synchronized (GRAPHQL_INTROSPECTION_JSON_TO_SDL) {
                                final String introspectionJsonAsGraphQL = GraphQLIntrospectionService.getInstance(project).printIntrospectionAsGraphQL(psiFile.getText());
                                final GraphQLFile currentSDLPsiFile = psiFile.getUserData(GRAPHQL_INTROSPECTION_JSON_TO_SDL);
                                if (currentSDLPsiFile != null && currentSDLPsiFile.getText().equals(introspectionJsonAsGraphQL)) {
                                    // already have a PSI file that matches the introspection SDL
                                    processor.process(currentSDLPsiFile);
                                } else {
                                    final PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(project);
                                    final String fileName = file.getPath();
                                    final GraphQLFile newIntrospectionFile = (GraphQLFile) psiFileFactory.createFileFromText(fileName, GraphQLLanguage.INSTANCE, introspectionJsonAsGraphQL);
                                    newIntrospectionFile.putUserData(IS_GRAPHQL_INTROSPECTION_SDL, true);
                                    newIntrospectionFile.putUserData(GRAPHQL_INTROSPECTION_SDL_TO_JSON, psiFile);
                                    newIntrospectionFile.getVirtualFile().putUserData(IS_GRAPHQL_INTROSPECTION_SDL, true);
                                    newIntrospectionFile.getVirtualFile().putUserData(GRAPHQL_INTROSPECTION_SDL_TO_JSON, psiFile);
                                    newIntrospectionFile.getVirtualFile().setWritable(false);
                                    psiFile.putUserData(GRAPHQL_INTROSPECTION_JSON_TO_SDL, newIntrospectionFile);
                                    processor.process(newIntrospectionFile);
                                }
                            }
                        } catch (ProcessCanceledException e) {
                            throw e;
                        } catch (Exception e) {
                            final List<SourceLocation> sourceLocation = Collections.singletonList(new SourceLocation(1, 1, GraphQLPsiUtil.getFileName(psiFile)));
                            errors.add(new SchemaProblem(Collections.singletonList(new InvalidSyntaxError(sourceLocation, e.getMessage()))));
                        }
                    }
                    return true;
                }, jsonIntrospectionScope.intersectWith(schemaScope));
            }

            // Injected GraphQL
            graphQLPsiSearchHelper.processInjectedGraphQLPsiFiles(scopedElement, schemaScope, processor);

            // Built-in that are additions to a default registry which already has the GraphQL spec directives
            graphQLPsiSearchHelper.processAdditionalBuiltInPsiFiles(schemaScope, processor);

            // Types defined using GraphQL Endpoint Language
            VirtualFile virtualFile = GraphQLPsiUtil.getVirtualFile(scopedElement.getContainingFile());
            if (virtualFile != null && graphQLConfigManager.getEndpointLanguageConfiguration(virtualFile, null) != null) {
                final GraphQLTypeDefinitionRegistry endpointTypesAsRegistry = graphQLEndpointNamedTypeRegistry.getTypesAsRegistry(scopedElement);
                try {
                    processor.getCompositeRegistry().merge(endpointTypesAsRegistry.getTypeDefinitionRegistry());
                    errors.addAll(endpointTypesAsRegistry.getErrors());
                } catch (GraphQLException e) {
                    errors.add(e);
                }
            }

            TypeDefinitionRegistry registry = processor.getCompositeRegistry().buildTypeDefinitionRegistry();
            return new GraphQLTypeDefinitionRegistry(registry, errors, processedGraphQL.get());
        });

    }

    @Override
    public void dispose() {
    }

}
