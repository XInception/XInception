package org.sat.inception;


import org.sat.mysql.parser.MySqlParser;
import org.sat.mysql.parser.MySqlParserBaseVisitor;

public class MySqlParserVisitor extends MySqlParserBaseVisitor<Object> {

    @Override
    public Object visitDmlStatement(MySqlParser.DmlStatementContext ctx) {
        System.out.println("执行dml");
        return super.visitDmlStatement(ctx);
    }

    @Override
    public Object visitDdlStatement(MySqlParser.DdlStatementContext ctx) {
        System.out.println("执行ddl");
        return super.visitDdlStatement(ctx);
    }
}
