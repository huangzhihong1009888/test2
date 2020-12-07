package com.jiguang.test.util;

import com.alibaba.druid.sql.parser.Token;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.alibaba.druid.util.JdbcUtils;
/**
 * 参考Mybatis-Plus插件中的TenantSqlParser进行租户解析处理，其实现为使用jsqlparser对sql进行解析，拼装SQL语句
 *
 */
@Slf4j
public class JpaInterceptor implements StatementInspector {

    @Override
    public String inspect(String sql) {
        //使用mysql解析
        MySqlStatementParser sqlStatementParser = new MySqlStatementParser(sql);
        Token token = sqlStatementParser.getExprParser().getLexer().token();
        // 识别查询sql
        if (!"SELECT".equals(token.name)) {
            return sql;
        }
        //解析select查询
        SQLSelectStatement sqlStatement = (SQLSelectStatement) sqlStatementParser.parseSelect();
        SQLSelect sqlSelect = sqlStatement.getSelect();
        //获取sql查询块
        SQLSelectQueryBlock sqlSelectQuery = (SQLSelectQueryBlock) sqlSelect.getQuery();
        StringBuffer out = new StringBuffer();
        //创建sql解析的标准化输出
        SQLASTOutputVisitor sqlastOutputVisitor = SQLUtils.createFormatOutputVisitor(out, null, JdbcUtils.MYSQL);

        //解析from
        out.delete(0, out.length());
        sqlSelectQuery.getFrom().accept(sqlastOutputVisitor);
        String appendSql = "tenant_id = '5000'";
        String[] where_s = sql.split("where ");
        sql = where_s[0];
        for (int i = 1; i < where_s.length; i++) {
            sql += "WHERE " + appendSql + " AND " + where_s[i];
        }
        return SqlParse.parseSql(sql);
    }

}