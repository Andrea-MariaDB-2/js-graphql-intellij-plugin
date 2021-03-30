// This is a generated file. Not intended for manual editing.
package com.intellij.lang.jsgraphql.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.lang.jsgraphql.psi.impl.GraphQLDirectivesAware;
import  com.intellij.lang.jsgraphql.psi.impl.GraphQLDescriptionAware;
import  com.intellij.lang.jsgraphql.psi.impl.GraphQLTypeNameDefinitionOwner;
import  com.intellij.lang.jsgraphql.psi.impl.GraphQLTypeNameExtensionOwner;
import com.intellij.lang.jsgraphql.psi.impl.GraphQLDescriptionAware;
import com.intellij.psi.PsiNamedElement;

public class GraphQLVisitor extends PsiElementVisitor {

  public void visitAlias(@NotNull GraphQLAlias o) {
    visitElement(o);
  }

  public void visitArgument(@NotNull GraphQLArgument o) {
    visitNamedElement(o);
  }

  public void visitArguments(@NotNull GraphQLArguments o) {
    visitElement(o);
  }

  public void visitArgumentsDefinition(@NotNull GraphQLArgumentsDefinition o) {
    visitElement(o);
  }

  public void visitArrayValue(@NotNull GraphQLArrayValue o) {
    visitValue(o);
  }

  public void visitBooleanValue(@NotNull GraphQLBooleanValue o) {
    visitValue(o);
  }

  public void visitDefaultValue(@NotNull GraphQLDefaultValue o) {
    visitElement(o);
  }

  public void visitDefinition(@NotNull GraphQLDefinition o) {
    visitElement(o);
  }

  public void visitDirective(@NotNull GraphQLDirective o) {
    visitNamedElement(o);
  }

  public void visitDirectiveDefinition(@NotNull GraphQLDirectiveDefinition o) {
    visitTypeSystemDefinition(o);
    // visitDescriptionAware(o);
  }

  public void visitDirectiveLocation(@NotNull GraphQLDirectiveLocation o) {
    visitElement(o);
  }

  public void visitDirectiveLocations(@NotNull GraphQLDirectiveLocations o) {
    visitElement(o);
  }

  public void visitEnumTypeDefinition(@NotNull GraphQLEnumTypeDefinition o) {
    visitTypeDefinition(o);
    // visitDirectivesAware(o);
  }

  public void visitEnumTypeExtensionDefinition(@NotNull GraphQLEnumTypeExtensionDefinition o) {
    visitTypeExtension(o);
    // visitDirectivesAware(o);
  }

  public void visitEnumValue(@NotNull GraphQLEnumValue o) {
    visitValue(o);
    // visitNamedElement(o);
  }

  public void visitEnumValueDefinition(@NotNull GraphQLEnumValueDefinition o) {
    visitDirectivesAware(o);
  }

  public void visitEnumValueDefinitions(@NotNull GraphQLEnumValueDefinitions o) {
    visitElement(o);
  }

  public void visitField(@NotNull GraphQLField o) {
    visitDirectivesAware(o);
    // visitNamedElement(o);
  }

  public void visitFieldDefinition(@NotNull GraphQLFieldDefinition o) {
    visitDirectivesAware(o);
    // visitNamedElement(o);
  }

  public void visitFieldsDefinition(@NotNull GraphQLFieldsDefinition o) {
    visitElement(o);
  }

  public void visitFloatValue(@NotNull GraphQLFloatValue o) {
    visitValue(o);
  }

  public void visitFragmentDefinition(@NotNull GraphQLFragmentDefinition o) {
    visitDefinition(o);
    // visitDirectivesAware(o);
    // visitNamedElement(o);
  }

  public void visitFragmentSelection(@NotNull GraphQLFragmentSelection o) {
    visitElement(o);
  }

  public void visitFragmentSpread(@NotNull GraphQLFragmentSpread o) {
    visitDirectivesAware(o);
    // visitNamedElement(o);
  }

  public void visitIdentifier(@NotNull GraphQLIdentifier o) {
    visitElement(o);
  }

  public void visitImplementsInterfaces(@NotNull GraphQLImplementsInterfaces o) {
    visitElement(o);
  }

  public void visitInlineFragment(@NotNull GraphQLInlineFragment o) {
    visitDirectivesAware(o);
  }

  public void visitInputObjectTypeDefinition(@NotNull GraphQLInputObjectTypeDefinition o) {
    visitTypeDefinition(o);
    // visitDirectivesAware(o);
  }

  public void visitInputObjectTypeExtensionDefinition(@NotNull GraphQLInputObjectTypeExtensionDefinition o) {
    visitTypeExtension(o);
    // visitDirectivesAware(o);
  }

  public void visitInputObjectValueDefinitions(@NotNull GraphQLInputObjectValueDefinitions o) {
    visitElement(o);
  }

  public void visitInputValueDefinition(@NotNull GraphQLInputValueDefinition o) {
    visitDirectivesAware(o);
    // visitNamedElement(o);
  }

  public void visitIntValue(@NotNull GraphQLIntValue o) {
    visitValue(o);
  }

  public void visitInterfaceTypeDefinition(@NotNull GraphQLInterfaceTypeDefinition o) {
    visitTypeDefinition(o);
    // visitDirectivesAware(o);
  }

  public void visitInterfaceTypeExtensionDefinition(@NotNull GraphQLInterfaceTypeExtensionDefinition o) {
    visitTypeExtension(o);
    // visitDirectivesAware(o);
  }

  public void visitListType(@NotNull GraphQLListType o) {
    visitType(o);
  }

  public void visitNonNullType(@NotNull GraphQLNonNullType o) {
    visitType(o);
  }

  public void visitNullValue(@NotNull GraphQLNullValue o) {
    visitValue(o);
  }

  public void visitObjectField(@NotNull GraphQLObjectField o) {
    visitNamedElement(o);
  }

  public void visitObjectTypeDefinition(@NotNull GraphQLObjectTypeDefinition o) {
    visitTypeDefinition(o);
    // visitDirectivesAware(o);
  }

  public void visitObjectTypeExtensionDefinition(@NotNull GraphQLObjectTypeExtensionDefinition o) {
    visitTypeExtension(o);
    // visitDirectivesAware(o);
  }

  public void visitObjectValue(@NotNull GraphQLObjectValue o) {
    visitValue(o);
  }

  public void visitOperationDefinition(@NotNull GraphQLOperationDefinition o) {
    visitDefinition(o);
    // visitNamedElement(o);
  }

  public void visitOperationType(@NotNull GraphQLOperationType o) {
    visitElement(o);
  }

  public void visitOperationTypeDefinition(@NotNull GraphQLOperationTypeDefinition o) {
    visitElement(o);
  }

  public void visitOperationTypeDefinitions(@NotNull GraphQLOperationTypeDefinitions o) {
    visitElement(o);
  }

  public void visitQuotedString(@NotNull GraphQLQuotedString o) {
    visitElement(o);
  }

  public void visitScalarTypeDefinition(@NotNull GraphQLScalarTypeDefinition o) {
    visitTypeDefinition(o);
    // visitDirectivesAware(o);
  }

  public void visitScalarTypeExtensionDefinition(@NotNull GraphQLScalarTypeExtensionDefinition o) {
    visitTypeExtension(o);
    // visitDirectivesAware(o);
  }

  public void visitSchemaDefinition(@NotNull GraphQLSchemaDefinition o) {
    visitTypeSystemDefinition(o);
    // visitDirectivesAware(o);
  }

  public void visitSelection(@NotNull GraphQLSelection o) {
    visitElement(o);
  }

  public void visitSelectionSet(@NotNull GraphQLSelectionSet o) {
    visitElement(o);
  }

  public void visitSelectionSetOperationDefinition(@NotNull GraphQLSelectionSetOperationDefinition o) {
    visitOperationDefinition(o);
  }

  public void visitStringValue(@NotNull GraphQLStringValue o) {
    visitValue(o);
  }

  public void visitTemplateDefinition(@NotNull GraphQLTemplateDefinition o) {
    visitDefinition(o);
  }

  public void visitTemplateSelection(@NotNull GraphQLTemplateSelection o) {
    visitElement(o);
  }

  public void visitTemplateVariable(@NotNull GraphQLTemplateVariable o) {
    visitValue(o);
  }

  public void visitType(@NotNull GraphQLType o) {
    visitElement(o);
  }

  public void visitTypeCondition(@NotNull GraphQLTypeCondition o) {
    visitElement(o);
  }

  public void visitTypeDefinition(@NotNull GraphQLTypeDefinition o) {
    visitTypeSystemDefinition(o);
  }

  public void visitTypeExtension(@NotNull GraphQLTypeExtension o) {
    visitTypeSystemDefinition(o);
  }

  public void visitTypeName(@NotNull GraphQLTypeName o) {
    visitType(o);
    // visitNamedElement(o);
  }

  public void visitTypeNameDefinition(@NotNull GraphQLTypeNameDefinition o) {
    visitType(o);
    // visitNamedElement(o);
  }

  public void visitTypeSystemDefinition(@NotNull GraphQLTypeSystemDefinition o) {
    visitDefinition(o);
  }

  public void visitTypedOperationDefinition(@NotNull GraphQLTypedOperationDefinition o) {
    visitOperationDefinition(o);
    // visitDirectivesAware(o);
  }

  public void visitUnionMembers(@NotNull GraphQLUnionMembers o) {
    visitElement(o);
  }

  public void visitUnionMembership(@NotNull GraphQLUnionMembership o) {
    visitElement(o);
  }

  public void visitUnionTypeDefinition(@NotNull GraphQLUnionTypeDefinition o) {
    visitTypeDefinition(o);
    // visitDirectivesAware(o);
  }

  public void visitUnionTypeExtensionDefinition(@NotNull GraphQLUnionTypeExtensionDefinition o) {
    visitTypeExtension(o);
    // visitDirectivesAware(o);
  }

  public void visitValue(@NotNull GraphQLValue o) {
    visitElement(o);
  }

  public void visitVariable(@NotNull GraphQLVariable o) {
    visitValue(o);
    // visitPsiNamedElement(o);
  }

  public void visitVariableDefinition(@NotNull GraphQLVariableDefinition o) {
    visitDirectivesAware(o);
  }

  public void visitVariableDefinitions(@NotNull GraphQLVariableDefinitions o) {
    visitElement(o);
  }

  public void visitDirectivesAware(@NotNull GraphQLDirectivesAware o) {
    visitElement(o);
  }

  public void visitNamedElement(@NotNull GraphQLNamedElement o) {
    visitElement(o);
  }

  public void visitElement(@NotNull GraphQLElement o) {
    super.visitElement(o);
  }

}
