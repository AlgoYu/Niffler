package algo.yu.grammatical;

import algo.yu.enums.ASTNodeTypeEnum;
import algo.yu.enums.KeyWordEnum;
import algo.yu.enums.LiteralEnum;
import algo.yu.enums.OperatorEnum;
import algo.yu.enums.SeparatorEnum;
import algo.yu.enums.TokenTypeEnum;
import algo.yu.grammatical.ast.AST;
import algo.yu.grammatical.ast.DataType;
import algo.yu.grammatical.ast.Identifier;
import algo.yu.grammatical.ast.Program;
import algo.yu.grammatical.ast.VariableDeclaration;
import algo.yu.model.Token;

import java.util.ArrayList;
import java.util.List;

public class SyntaxAnalyzer {
    private List<Token> tokens;
    private int cur;
    private Token curToken;

    private boolean hasNextToken() {
        return cur < tokens.size() - 1;
    }

    private Token peekNext() {
        return cur < tokens.size() ? tokens.get(cur + 1) : null;
    }

    private Token nextToken() {
        if (!hasNextToken()) {
            throw new RuntimeException("没有下一个Token");
        }
        cur++;
        curToken = tokens.get(cur);
        return curToken;
    }

    public AST analysis(List<Token> tokens) {
        this.tokens = tokens;
        cur = 0;
        curToken = tokens.get(0);
        // 程序结果
        Program program = new Program();
        program.setType(ASTNodeTypeEnum.PROGRAM);
        // 解析程序体
        List<AST> body = new ArrayList<>();
        while (hasNextToken()) {
            body.add(parseToken());
        }
        program.setBody(body);
        return program;
    }

    private AST parseToken() {
        return parserVariableDeclaration();
    }

    /**
     * VariableDeclaration -> TYPE ID = EXPRESSION;
     *
     * @return
     */
    private AST parserVariableDeclaration() {
        AST dataType = parseDataType();
        nextToken();
        AST identifier = parseIdentifier();
        nextToken();
        if (!OperatorEnum.ASSIGN.getSymbol().equals(curToken.getValue())) {
            throw new RuntimeException("解析赋值语句失败");
        }
        nextToken();
        AST express = parseExpress();
        nextToken();
        if (!SeparatorEnum.SEMICOLON.getSymbol().equals(curToken.getValue())) {
            throw new RuntimeException("解析赋值语句失败");
        }
        VariableDeclaration variableDeclaration = new VariableDeclaration();
        variableDeclaration.setDataType(dataType);
        variableDeclaration.setIdentifier(identifier);
        variableDeclaration.setExpression(express);
        return variableDeclaration;
    }

    private AST parseExpress() {
        return null;
    }

    private AST parseDataType() {
        DataType dataType = new DataType();
        String value = curToken.getValue();
        dataType.setValue(value);
        return dataType;
    }

    private AST parseAssignmentStatement() {
        String value = curToken.getValue();
        if (value.equals(KeyWordEnum.INT.getSymbol())) {
            nextToken();
            VariableDeclaration variableDeclaration = new VariableDeclaration();
            AST identifier = parseIdentifier();
            return variableDeclaration;
        } else {
            throw new RuntimeException("无法解析赋值语句" + curToken.getLine() + "行");
        }
    }

    private AST parseIdentifier() {
        String value = curToken.getValue();
        if (!TokenTypeEnum.IDENTIFIER.equals(curToken.getType())) {
            throw new RuntimeException("无法解析标识符" + curToken.getLine() + "行");
        }
        Identifier ast = new Identifier();
        ast.setIdentifier(value);
        return ast;
    }

    private AST parseLiteral() {
        String value = curToken.getValue();
        if (!TokenTypeEnum.LITERAL.equals(curToken.getType())) {
            throw new RuntimeException("无法解析字面量" + curToken.getLine() + "行");
        }
        if (value.contains("\"")) {
            return parseStringLiteral();
        } else if (LiteralEnum.TRUE.getSymbol().equals(value) || LiteralEnum.FALSE.getSymbol().equals(value)) {
            return parseBooleLiteral();
        } else {
            return parseNumberLiteral();
        }
    }

    private AST parseNumberLiteral() {
        AST ast = new AST();
        ast.setType(ASTNodeTypeEnum.NUMBER_LITERAL);
        String value = curToken.getValue();
        if (value.contains(".")) {
//            ast.setValue(Double.parseDouble(value));
        } else {
//            ast.setValue(Long.parseLong(value));
        }
        return ast;
    }

    private AST parseBooleLiteral() {
        AST ast = new AST();
        ast.setType(ASTNodeTypeEnum.NUMBER_LITERAL);
//        ast.setValue(Boolean.parseBoolean(curToken.getValue()));
        return ast;
    }

    private AST parseStringLiteral() {
        AST ast = new AST();
        ast.setType(ASTNodeTypeEnum.NUMBER_LITERAL);
//        ast.setValue(curToken.getValue().substring(1, curToken.getValue().length() - 1));
        return ast;
    }
}
