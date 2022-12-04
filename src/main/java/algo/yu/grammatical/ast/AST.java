package algo.yu.grammatical.ast;

import algo.yu.enums.ASTNodeTypeEnum;

public class AST {
    protected ASTNodeTypeEnum type;

    public ASTNodeTypeEnum getType() {
        return type;
    }

    public void setType(ASTNodeTypeEnum type) {
        this.type = type;
    }
}
