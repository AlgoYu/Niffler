package algo.yu.grammatical.ast;

import algo.yu.enums.ASTNodeTypeEnum;

public class DataType extends AST {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DataType() {
        type = ASTNodeTypeEnum.DATA_TYPE;
    }
}
