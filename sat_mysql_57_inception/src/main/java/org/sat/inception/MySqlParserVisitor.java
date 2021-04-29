package org.sat.inception;


import lombok.extern.slf4j.Slf4j;
import org.sat.mysql.parser.MySqlParser;
import org.sat.mysql.parser.MySqlParserBaseVisitor;


@Slf4j
public class MySqlParserVisitor extends MySqlParserBaseVisitor<Object> {

    @Override
    public Object visitDmlStatement(MySqlParser.DmlStatementContext ctx) {
        return super.visitDmlStatement(ctx);
    }

    @Override
    public Object visitDdlStatement(MySqlParser.DdlStatementContext ctx) {
        return super.visitDdlStatement(ctx);
    }
}
