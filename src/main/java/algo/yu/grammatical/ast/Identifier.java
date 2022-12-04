package algo.yu.grammatical.ast;

import algo.yu.enums.ASTNodeTypeEnum;

public class Identifier extends AST {
    private String identifier;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Identifier() {
        type = ASTNodeTypeEnum.IDENTIFIER;
    }
}
