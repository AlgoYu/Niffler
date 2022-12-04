package algo.yu.enums;

import java.util.HashMap;
import java.util.Map;

public enum SeparatorEnum {
    LEFT_PARENTHESES("("), RIGHT_PARENTHESES(")"),
    LEFT_CURLY_BRACKETS("{"), RIGHT_CURLY_BRACKETS("}"),
    LEFT_SQUARE_BRACKETS("["), RIGHT_SQUARE_BRACKETS("]"),
    SEMICOLON(";"), DOT("."), COMMA(",");

    private final String symbol;

    SeparatorEnum(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Map<String, TokenTypeEnum> getSeparatorTokenMap() {
        Map<String, TokenTypeEnum> map = new HashMap<>();
        for (SeparatorEnum separatorEnum : values()) {
            map.put(separatorEnum.symbol, TokenTypeEnum.SEPARATOR);
        }
        return map;
    }

    public static boolean isSeparator(char ch) {
        String symbol = String.valueOf(ch);
        for (SeparatorEnum separatorEnum : values()) {
            if (separatorEnum.symbol.equals(symbol)) {
                return true;
            }
        }
        return false;
    }
}
