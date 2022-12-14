package algo.yu.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * XAVA关键字
 */
public enum KeyWordEnum {
    BOOL("bool"),
    BYTE("byte"), SHORT("short"), INT("int"), LONG("long"),
    FLOAT("float"), DOUBLE("double"),
    STRING("string"),
    IF("if"), ELSE("else"), SWITCH("switch"), CASE("case"), DEFAULT("default"),
    FOR("for"), BREAK("break"),
    MAIN("main"), VOID("void"), FUNCTION("func"), RETURN("return"),
    TRY("try"), CATCH("catch"), FINALLY("finally"),
    NEW("new"), TEMPLATE("template"), EXTENDS("extends"),
    IMPORT("import"), EXPORT("export");

    private final String symbol;

    public String getSymbol() {
        return symbol;
    }

    KeyWordEnum(String symbol) {
        this.symbol = symbol;
    }

    public static Map<String, TokenTypeEnum> getKeyWordTokenMap() {
        Map<String, TokenTypeEnum> map = new HashMap<>();
        for (KeyWordEnum keyWordEnum : values()) {
            map.put(keyWordEnum.symbol, TokenTypeEnum.KEYWORD);
        }
        return map;
    }
}
