package algo.yu.model;

import algo.yu.enums.TokenTypeEnum;

public class Token {
    private int line;
    private TokenTypeEnum tokenTypeEnum;
    private String value;

    public Token(int line, TokenTypeEnum tokenTypeEnum, String value) {
        this.line = line;
        this.tokenTypeEnum = tokenTypeEnum;
        this.value = value;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public TokenTypeEnum getToken() {
        return tokenTypeEnum;
    }

    public void setToken(TokenTypeEnum tokenTypeEnum) {
        this.tokenTypeEnum = tokenTypeEnum;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
