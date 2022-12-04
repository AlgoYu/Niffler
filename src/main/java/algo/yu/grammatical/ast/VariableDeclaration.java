package algo.yu.grammatical.ast;

import algo.yu.enums.ASTNodeTypeEnum;

public class VariableDeclaration extends AST {
    private AST dataType;
    private AST identifier;
    private AST expression;

    public AST getDataType() {
        return dataType;
    }

    public void setDataType(AST dataType) {
        this.dataType = dataType;
    }

    public AST getIdentifier() {
        return identifier;
    }

    public void setIdentifier(AST identifier) {
        this.identifier = identifier;
    }

    public AST getExpression() {
        return expression;
    }

    public void setExpression(AST expression) {
        this.expression = expression;
    }

    public VariableDeclaration() {
        type = ASTNodeTypeEnum.VARIABLE_DECLARATION;
    }
}
