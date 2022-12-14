package algo.yu.model;

import algo.yu.enums.TokenTypeEnum;

public class Token {
    private int line;
    private TokenTypeEnum type;
    private String value;

    public Token(int line, TokenTypeEnum type, String value) {
        this.line = line;
        this.type = type;
        this.value = value;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public TokenTypeEnum getType() {
        return type;
    }

    public void setType(TokenTypeEnum type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
