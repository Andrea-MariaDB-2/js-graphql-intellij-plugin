/*
 *  Copyright (c) 2015-present, Jim Kynde Meyer
 *  All rights reserved.
 *
 *  This source code is licensed under the MIT license found in the
 *  LICENSE file in the root directory of this source tree.
 */
package com.intellij.lang.jsgraphql.ide.formatter;

import com.intellij.codeInsight.editorActions.enter.EnterBetweenBracesHandler;
import com.intellij.lang.jsgraphql.GraphQLLanguage;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

/**
 * Adds a new indented line when pressing enter between [] in an empty GraphQL list
 */
public class GraphQLEnterInEmptyListHandler extends EnterBetweenBracesHandler {

    @Override
    public Result preprocessEnter(@NotNull PsiFile file,
                                  @NotNull Editor editor,
                                  @NotNull Ref<Integer> caretOffsetRef,
                                  @NotNull Ref<Integer> caretAdvance,
                                  @NotNull DataContext dataContext,
                                  EditorActionHandler originalHandler) {
        if (!file.getLanguage().is(GraphQLLanguage.INSTANCE)) {
            return Result.Continue;
        }
        return super.preprocessEnter(file, editor, caretOffsetRef, caretAdvance, dataContext, originalHandler);
    }

    @Override
    protected boolean isBracePair(char c1, char c2) {
        return c1 == '[' && c2 == ']';
    }

}
