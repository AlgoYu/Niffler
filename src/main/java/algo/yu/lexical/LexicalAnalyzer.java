package algo.yu.lexical;

import algo.yu.enums.LiteralEnum;
import algo.yu.enums.OperatorEnum;
import algo.yu.enums.SeparatorEnum;
import algo.yu.enums.LexicalStateEnum;
import algo.yu.model.Token;
import algo.yu.enums.KeyWordEnum;
import algo.yu.model.Sentence;
import algo.yu.enums.TokenTypeEnum;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LexicalAnalyzer {
    // 关键字
    private static final Map<String, TokenTypeEnum> keywordMap = new HashMap<>() {
        {
            putAll(KeyWordEnum.getKeyWordTokenMap());
            putAll(OperatorEnum.getSeparatorTokenMap());
            putAll(SeparatorEnum.getSeparatorTokenMap());
            putAll(LiteralEnum.getLiteralTokenMap());
        }
    };
    // 状态机
    private static final Map<LexicalStateEnum, TokenTypeEnum> stateMap = new HashMap<>() {
        {
            put(LexicalStateEnum.IDENTIFIER, TokenTypeEnum.IDENTIFIER);
            put(LexicalStateEnum.NUMBER, TokenTypeEnum.LITERAL);
            put(LexicalStateEnum.STRING, TokenTypeEnum.LITERAL);
            put(LexicalStateEnum.SEPARATOR, TokenTypeEnum.SEPARATOR);
            put(LexicalStateEnum.OPERATOR, TokenTypeEnum.OPERATOR);
        }
    };

    public List<Token> analysis(String filePath) {
        // 扫描每一行
        List<Sentence> sentences = getSentence(filePath);
        if (sentences == null || sentences.isEmpty()) {
            return null;
        }
        // 解析为Tokens
        return analysisTokens(sentences);
    }

    private List<Token> analysisTokens(List<Sentence> sentences) {
        List<Token> result = new ArrayList<>();
        for (int i = 0; i < sentences.size(); i++) {
            Sentence sentence = sentences.get(i);
            // 空格、字符串分割
            String[] stringArray = splitWhite(sentence.getText());
            for (String string : stringArray) {
                // 在代码后的注释
                if (string.startsWith("//")) {
                    break;
                }
                // 关键字识别
                TokenTypeEnum tokenTypeEnum = keywordMap.get(string.toLowerCase());
                if (tokenTypeEnum != null) {
                    result.add(new Token(sentence.getRow(), tokenTypeEnum, string));
                    continue;
                }
                // 自动机
                result.addAll(automata(sentence, string));
            }
        }
        return result;
    }

    private List<Token> automata(Sentence sentence, String string) {
        List<Token> result = new ArrayList<>();
        LexicalStateEnum state = LexicalStateEnum.INIT;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);
            switch (state) {
                case INIT:
                    if (Character.isLetter(ch)) {
                        state = LexicalStateEnum.IDENTIFIER;
                    } else if (Character.isDigit(ch)) {
                        state = LexicalStateEnum.NUMBER;
                    } else if (ch == '"') {
                        state = LexicalStateEnum.STRING;
                    } else if (SeparatorEnum.isSeparator(ch)) {
                        state = LexicalStateEnum.SEPARATOR;
                    } else if (OperatorEnum.isOperator(ch)) {
                        state = LexicalStateEnum.OPERATOR;
                    } else {
                        state = LexicalStateEnum.INVALID;
                    }
                    break;
                // 标识符
                case IDENTIFIER:
                    if (Character.isLetterOrDigit(ch)) {
                        break;
                    }
                    result.add(generateElement(sentence.getRow(), sb, keywordMap.getOrDefault(sb.toString().toLowerCase(), TokenTypeEnum.IDENTIFIER)));
                    if (OperatorEnum.isOperator(ch)) {
                        state = LexicalStateEnum.OPERATOR;
                    } else if (SeparatorEnum.isSeparator(ch)) {
                        state = LexicalStateEnum.SEPARATOR;
                    } else {
                        state = LexicalStateEnum.INVALID;
                    }
                    break;
                // 数字
                case NUMBER:
                    if (Character.isDigit(ch) || ch == '.') {
                        break;
                    }
                    result.add(generateElement(sentence.getRow(), sb, TokenTypeEnum.LITERAL));
                    if (SeparatorEnum.isSeparator(ch)) {
                        state = LexicalStateEnum.SEPARATOR;
                    } else if (Character.isLetter(ch)) {
                        state = LexicalStateEnum.IDENTIFIER;
                    } else if (OperatorEnum.isOperator(ch)) {
                        state = LexicalStateEnum.OPERATOR;
                    } else {
                        state = LexicalStateEnum.INVALID;
                    }
                    break;
                // 字符串
                case STRING:
                    break;
                // 分隔符
                case SEPARATOR:
                    result.add(generateElement(sentence.getRow(), sb, TokenTypeEnum.SEPARATOR));
                    if (SeparatorEnum.isSeparator(ch)) {
                        break;
                    }
                    if (Character.isLetter(ch)) {
                        state = LexicalStateEnum.IDENTIFIER;
                        break;
                    } else if (Character.isDigit(ch)) {
                        state = LexicalStateEnum.NUMBER;
                        break;
                    } else {
                        state = LexicalStateEnum.INVALID;
                    }
                    break;
                // 操作符
                case OPERATOR:
                    result.add(generateElement(sentence.getRow(), sb, TokenTypeEnum.OPERATOR));
                    if (OperatorEnum.isOperator(ch)) {
                        break;
                    }
                    if (Character.isLetter(ch)) {
                        state = LexicalStateEnum.IDENTIFIER;
                        break;
                    } else if (Character.isDigit(ch)) {
                        state = LexicalStateEnum.NUMBER;
                    } else if (SeparatorEnum.isSeparator(ch)) {
                        state = LexicalStateEnum.SEPARATOR;
                    } else {
                        state = LexicalStateEnum.INVALID;
                    }
                    break;
                case INVALID:
                    throw new RuntimeException(String.format("无法识别%d行字符%s", sentence.getRow(), ch));
                    // 无效
                default:
                    state = LexicalStateEnum.INVALID;
                    break;
            }
            sb.append(ch);
        }
        if (state != LexicalStateEnum.INVALID && state != LexicalStateEnum.INIT) {
            TokenTypeEnum tokenTypeEnum = stateMap.get(state);
            if (tokenTypeEnum == null) {
                throw new RuntimeException(String.format("无法识别%d行字符%s", sentence.getRow(), sb.toString()));
            }
            result.add(generateElement(sentence.getRow(), sb, tokenTypeEnum));
        }
        return result;
    }

    private String[] splitWhite(String word) {
        StringBuilder sb = new StringBuilder();
        List<String> result = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c == ' ') {
                if (sb.length() > 0) {
                    result.add(sb.toString());
                    sb.setLength(0);
                }
            } else if (c == '"') {
                sb.append(c);
                i++;
                while (i < word.length() && word.charAt(i) != '"') {
                    sb.append(word.charAt(i));
                    i++;
                }
                if (i < word.length()) {
                    sb.append(word.charAt(i));
                    result.add(sb.toString());
                    sb.setLength(0);
                } else {
                    throw new RuntimeException("找不到字符串末尾");
                }
            } else {
                sb.append(c);
            }
        }
        if (sb.length() > 0) {
            result.add(sb.toString());
        }
        String[] strings = new String[result.size()];
        return result.toArray(strings);
    }

    private Token generateElement(int line, StringBuilder sb, TokenTypeEnum tokenTypeEnum) {
        Token element = new Token(line, tokenTypeEnum, sb.toString());
        sb.setLength(0);
        return element;
    }

    private List<Sentence> getSentence(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String str = null;
            int row = 0;
            List<Sentence> sentences = new ArrayList<>();
            while ((str = bufferedReader.readLine()) != null) {
                row++;
                // 空行
                if (str.isBlank()) {
                    continue;
                }
                // 行注释
                if (str.startsWith("//")) {
                    continue;
                }
                // 多行注释
                if (str.startsWith("/*")) {
                    String tmp = null;
                    while ((tmp = bufferedReader.readLine()) != null && !tmp.endsWith("*/")) {
                        row++;
                    }
                    continue;
                }
                // 代码
                sentences.add(new Sentence(row, str));
            }
            return sentences;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
