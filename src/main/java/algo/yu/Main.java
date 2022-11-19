package algo.yu;

import algo.yu.lexical.LexicalAnalyzer;
import algo.yu.model.Token;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
//        List<Token> analysis = lexicalAnalyzer.analysis("/Users/xiaoyu/JavaProject/Niffler/source/test1.n");
//        List<Token> analysis = lexicalAnalyzer.analysis("/Users/xiaoyu/JavaProject/Niffler/source/test2.n");
//        List<Token> analysis = lexicalAnalyzer.analysis("/Users/xiaoyu/JavaProject/Niffler/source/test3.n");
//        List<Token> analysis = lexicalAnalyzer.analysis("/Users/xiaoyu/JavaProject/Niffler/source/test4.n");
        List<Token> analysis = lexicalAnalyzer.analysis("/Users/xiaoyu/JavaProject/Niffler/source/test5.n");

        String json = gson.toJson(analysis);
        System.out.println(json);
        Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        systemClipboard.setContents(new StringSelection(json), null);
    }
}
