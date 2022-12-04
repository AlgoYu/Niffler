package algo.yu.enums;

import java.util.HashMap;
import java.util.Map;

public enum LiteralEnum {
    TRUE("true"), FALSE("false");

    private final String symbol;

    public String getSymbol() {
        return symbol;
    }

    LiteralEnum(String symbol) {
        this.symbol = symbol;
    }

    public static Map<String, TokenTypeEnum> getLiteralTokenMap() {
        Map<String, TokenTypeEnum> map = new HashMap<>();
        for (LiteralEnum operatorEnum : values()) {
            map.put(operatorEnum.symbol, TokenTypeEnum.LITERAL);
        }
        return map;
    }
}
