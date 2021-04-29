package org.sat.inception;


import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.sat.mysql.lexer.MySqlLexer;
import org.sat.mysql.parser.MySqlParser;


@Slf4j
public class XInception {
    public void checkSql(String sql){
        log.info("check sql {}",sql);
        CharStream source = CharStreams.fromString(sql.toUpperCase());
        MySqlLexer lexer = new MySqlLexer(source);
        MySqlParser parser = new MySqlParser(new CommonTokenStream(lexer));
        parser.setBuildParseTree(true);
        parser.removeErrorListeners();
        parser.addErrorListener(new ThrowingErrorListener());
        ParseTree tree = parser.root();
        MySqlParserVisitor visitor = new MySqlParserVisitor();
        visitor.visit(tree);
    }
}
