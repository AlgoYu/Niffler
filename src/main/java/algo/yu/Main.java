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
//        List<Element> analysis = lexicalAnalyzer.analysis("/Users/xiaoyu/JavaProject/xava/source/test1.xava");
//        List<Element> analysis = lexicalAnalyzer.analysis("/Users/xiaoyu/JavaProject/xava/source/test2.xava");
        List<Token> analysis = lexicalAnalyzer.analysis("/Users/xiaoyu/JavaProject/xava/source/test3.xava");
//        List<Element> analysis = lexicalAnalyzer.analysis("/Users/xiaoyu/JavaProject/xava/source/test2.xava");
//        List<Element> analysis = lexicalAnalyzer.analysis("/Users/xiaoyu/JavaProject/xava/source/test2.xava");

        String json = gson.toJson(analysis);
        System.out.println(json);
        Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        systemClipboard.setContents(new StringSelection(json), null);
    }
}
