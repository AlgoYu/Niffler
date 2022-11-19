package algo.yu.enums;

import java.util.HashMap;
import java.util.Map;

public enum OperatorEnum {
    ADD("+"), SUBTRACT("-"), MULTIPLY("*"), DIVIDE("/"),
    GREATER(">"), LESS("<"), ASSIGN("="),
    AND("&"), OR("|"), INVERT("!");

    private final String symbol;

    OperatorEnum(String symbol) {
        this.symbol = symbol;
    }

    public static Map<String, TokenTypeEnum> getSeparatorTokenMap() {
        Map<String, TokenTypeEnum> map = new HashMap<>();
        for (OperatorEnum operatorEnum : values()) {
            map.put(operatorEnum.symbol, TokenTypeEnum.OPERATOR);
        }
        return map;
    }

    public static boolean isOperator(char ch) {
        String symbol = String.valueOf(ch);
        for (OperatorEnum operatorEnum : values()) {
            if (operatorEnum.symbol.equals(symbol)) {
                return true;
            }
        }
        return false;
    }
}
