package algo.yu.grammatical.ast;

import java.util.List;

public class Program extends AST {
    List<AST> body;

    public List<AST> getBody() {
        return body;
    }

    public void setBody(List<AST> body) {
        this.body = body;
    }
}
