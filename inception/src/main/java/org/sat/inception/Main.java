package org.sat.inception;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.sat.mysql.lexer.MySqlLexer;
import org.sat.mysql.parser.MySqlParser;
import org.sat.mysql.parser.MySqlParserBaseVisitor;

import java.io.IOException;

public class Main {
    public static void main(String[] argv) throws IOException {
        System.out.println("start ");
        String fileName = "inception/test.sql";
        CharStream source = CharStreams.fromFileName(fileName);

        MySqlLexer lexer = new MySqlLexer(source);

        MySqlParser parser = new MySqlParser(new CommonTokenStream(lexer));
        parser.setBuildParseTree(true);
        parser.removeErrorListeners();
        parser.addErrorListener(new ThrowingErrorListener());
        ParseTree tree = parser.root();
        MySqlParserVisitor visitor = new MySqlParserVisitor();
        visitor.visit(tree);
//        //show AST in GUI
//        JFrame frame = new JFrame("Antlr AST");
//        JPanel panel = new JPanel();
//        TreeViewer viewer = new TreeViewer(Arrays.asList(
//                parser.getRuleNames()),tree);
//        viewer.setScale(1.5); // Scale a little
//        panel.add(viewer);
//        frame.add(panel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
    }
}
