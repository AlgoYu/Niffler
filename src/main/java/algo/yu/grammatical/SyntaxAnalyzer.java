package algo.yu.grammatical;

import algo.yu.enums.ASTNodeTypeEnum;
import algo.yu.enums.KeyWordEnum;
import algo.yu.model.Token;

import java.util.List;

public class SyntaxAnalyzer {
    public ASTNode analysis(List<Token> tokens) {
        ASTNode program = new ASTNode();
        program.type = ASTNodeTypeEnum.PROGRAM;
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            parseToken(token);
        }
        return program;
    }

    private void parseToken(Token token) {
        switch (token.getType()) {
            case KEYWORD:
                parseKeyWord(token);
                break;
        }
    }

    private void parseKeyWord(Token token) {
        if (KeyWordEnum.INT.getSymbol().equals(token.getValue())) {
            ASTNode astNode = new ASTNode();
        }
    }
}
