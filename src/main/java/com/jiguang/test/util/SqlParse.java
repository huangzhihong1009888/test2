package com.jiguang.test.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Data
@Slf4j
public class SqlParse {
    /**
     * 当前租户ID，
     */
    private static String tenantId;

    /**
     * 需进行租户解析的租户字段名,本项目中为固定名称
     */
    private static String tenantIdColumn = "tenant_id";

    public static void main(String[] args) throws JSQLParserException {
        String sql = "select * from member_card_batch  where name like  ? escape ? ";


        CCJSqlParserUtil.parse(sql);
    }

    /**
     * 不需要租户id的表
     */
    private static final List<String> ignoreTables = Arrays.asList();

    public static String parseSql(String sql){
        try {
            tenantId = "5000";
            log.info("租户解析开始，原始SQL：{}", sql);
            Statements statements = CCJSqlParserUtil.parseStatements(sql);
            StringBuilder sqlStringBuilder = new StringBuilder();
            int i = 0;
            for (Statement statement : statements.getStatements()) {
                if (null != statement) {
                    if (i++ > 0) {
                        sqlStringBuilder.append(';');
                    }
                    sqlStringBuilder.append(processParser(statement));
                }
            }
            String newSql = sqlStringBuilder.toString();
            log.info("租户解析结束，解析后SQL：{}", newSql);
            return newSql;
        }catch (Exception e){
            log.error("租户解析失败，解析SQL异常{}", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public static String processParser(Statement statement) {
        if (statement instanceof Insert) {
            processInsert((Insert) statement);
        } else if (statement instanceof Select) {
            processSelectBody(((Select) statement).getSelectBody());
        } else if (statement instanceof Update) {
            processUpdate((Update) statement);
        } else if (statement instanceof Delete) {
            processDelete((Delete) statement);
        }
        /**
         * 返回处理后的SQL
         */
        return statement.toString();
    }

    public static void processSelectBody(SelectBody selectBody) {

        if (selectBody instanceof PlainSelect) {
            processPlainSelect((PlainSelect) selectBody);
        } else if (selectBody instanceof WithItem) {
            WithItem withItem = (WithItem) selectBody;
            if (withItem.getSelectBody() != null) {
                processSelectBody(withItem.getSelectBody());
            }
        } else {
            SetOperationList operationList = (SetOperationList) selectBody;
            if (operationList.getSelects() != null && operationList.getSelects().size() > 0) {
                operationList.getSelects().forEach(SqlParse::processSelectBody);
            }
        }
    }

    /**
     * insert 语句处理
     */

    public static void processInsert(Insert insert) {
        if (ignoreTable(insert.getTable().getName())) {
            // 过滤退出执行
            return;
        }
        //判断已经有该字段 则直接return
        boolean exists = insert.getColumns().stream().anyMatch(a -> Objects.equals(a.getColumnName(),tenantIdColumn));
        if(exists){
            return;
        }
        insert.getColumns().add(new Column(tenantIdColumn));
        if (insert.getSelect() != null) {
            processPlainSelect((PlainSelect) insert.getSelect().getSelectBody(), true);
        } else if (insert.getItemsList() != null) {
            ItemsList itemsList = insert.getItemsList();
            if (itemsList instanceof MultiExpressionList) {
                ((MultiExpressionList) itemsList).getExprList().forEach(el -> el.getExpressions().add(new StringValue(tenantId)));
            } else {
                ((ExpressionList) insert.getItemsList()).getExpressions().add(new StringValue(tenantId));
            }
        } else {
            throw new RuntimeException("Failed to process multiple-table update, please exclude the tableName or statementId");
        }
    }

    /**
     * update 语句处理
     */

    public static void processUpdate(Update update) {
        final Table table = update.getTable();
        if (ignoreTable(table.getName())) {
            // 过滤退出执行
            return;
        }
        update.setWhere(andExpression(table, update.getWhere()));
    }

    /**
     * delete 语句处理
     */

    public static void processDelete(Delete delete) {
        if (ignoreTable(delete.getTable().getName())) {
            // 过滤退出执行
            return;
        }
        delete.setWhere(andExpression(delete.getTable(), delete.getWhere()));
    }

    /**
     * delete update 语句 where 处理
     */
    protected static BinaryExpression andExpression(Table table, Expression where) {
        //获得where条件表达式
        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(getAliasColumn(table));
        equalsTo.setRightExpression(new StringValue(tenantId));
        if (null != where) {
            if (where instanceof OrExpression) {
                return new AndExpression(equalsTo, new Parenthesis(where));
            } else {
                return new AndExpression(equalsTo, where);
            }
        }
        return equalsTo;
    }

    /**
     * 处理 PlainSelect
     */
    protected static void processPlainSelect(PlainSelect plainSelect) {

        processPlainSelect(plainSelect, false);
    }

    /**
     * 处理 PlainSelect
     *
     * @param plainSelect ignore
     * @param addColumn   是否添加租户列,insert into select语句中需要
     */
    protected static void processPlainSelect(PlainSelect plainSelect, boolean addColumn) {
        FromItem fromItem = plainSelect.getFromItem();
        if (fromItem instanceof Table) {
            Table fromTable = (Table) fromItem;
            if (!ignoreTable(fromTable.getName())) {
                plainSelect.setWhere(builderExpression(plainSelect.getWhere(), fromTable));
            }
            if (addColumn) {
                plainSelect.getSelectItems().add(new SelectExpressionItem(
                        new Column(tenantIdColumn)));
            }
        } else {
            processFromItem(fromItem);
        }
        List<Join> joins = plainSelect.getJoins();
        if (joins != null && joins.size() > 0) {
            joins.forEach(j -> {
                processJoin(j);
                processFromItem(j.getRightItem());
            });
        }
    }

    /**
     * 处理子查询等
     */
    protected static void processFromItem(FromItem fromItem) {
        if (fromItem instanceof SubJoin) {
            SubJoin subJoin = (SubJoin) fromItem;
            if (subJoin.getJoinList() != null) {
                subJoin.getJoinList().forEach(SqlParse::processJoin);
            }
            if (subJoin.getLeft() != null) {
                processFromItem(subJoin.getLeft());
            }
        } else if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            if (subSelect.getSelectBody() != null) {
                processSelectBody(subSelect.getSelectBody());
            }
        } else if (fromItem instanceof ValuesList) {
        } else if (fromItem instanceof LateralSubSelect) {
            LateralSubSelect lateralSubSelect = (LateralSubSelect) fromItem;
            if (lateralSubSelect.getSubSelect() != null) {
                SubSelect subSelect = lateralSubSelect.getSubSelect();
                if (subSelect.getSelectBody() != null) {
                    processSelectBody(subSelect.getSelectBody());
                }
            }
        }
    }

    /**
     * 处理联接语句
     */
    protected static void processJoin(Join join) {
        if (join.getRightItem() instanceof Table) {
            Table fromTable = (Table) join.getRightItem();
            join.setOnExpression(builderExpression(join.getOnExpression(), fromTable));
        }
    }

    /**
     * 处理条件:
     * 支持 getTenantHandler().getTenantId()是一个完整的表达式：tenant in (1,2)
     * 默认tenantId的表达式： LongValue(1)这种依旧支持
     */
    protected static Expression builderExpression(Expression currentExpression, Table table) {
        final Expression tenantExpression = new StringValue(tenantId);
        Expression appendExpression;
        if (!(tenantExpression instanceof SupportsOldOracleJoinSyntax)) {
            appendExpression = new EqualsTo();
            ((EqualsTo) appendExpression).setLeftExpression(getAliasColumn(table));
            ((EqualsTo) appendExpression).setRightExpression(tenantExpression);
        } else {
            appendExpression = processTableAlias4CustomizedTenantIdExpression(tenantExpression, table);
        }
        if (currentExpression == null) {
            return appendExpression;
        }
        if (currentExpression instanceof BinaryExpression) {
            BinaryExpression binaryExpression = (BinaryExpression) currentExpression;
            doExpression(binaryExpression.getLeftExpression());
            doExpression(binaryExpression.getRightExpression());
        } else if (currentExpression instanceof InExpression) {
            InExpression inExp = (InExpression) currentExpression;
            ItemsList rightItems = inExp.getRightItemsList();
            if (rightItems instanceof SubSelect) {
                processSelectBody(((SubSelect) rightItems).getSelectBody());
            }
        }
        if (currentExpression instanceof OrExpression) {
            return new AndExpression(new Parenthesis(currentExpression), appendExpression);
        } else {
            return new AndExpression(currentExpression, appendExpression);
        }
    }

    protected static void doExpression(Expression expression) {
        if (expression instanceof FromItem) {
            processFromItem((FromItem) expression);
        } else if (expression instanceof InExpression) {
            InExpression inExp = (InExpression) expression;
            ItemsList rightItems = inExp.getRightItemsList();
            if (rightItems instanceof SubSelect) {
                processSelectBody(((SubSelect) rightItems).getSelectBody());
            }
        }
    }

    /**
     * 目前: 针对自定义的tenantId的条件表达式[tenant_id in (1,2,3)]，无法处理多租户的字段加上表别名
     * select a.id, b.name
     * from a
     * join b on b.aid = a.id and [b.]tenant_id in (1,2) --别名[b.]无法加上 TODO
     *
     * @param expression
     * @param table
     * @return 加上别名的多租户字段表达式
     */
    protected static Expression processTableAlias4CustomizedTenantIdExpression(Expression expression, Table table) {
        //cannot add table alias for customized tenantId expression,
        // when tables including tenantId at the join table poistion
        return expression;
    }

    /**
     * 租户字段别名设置
     * <p>tableName.tenantId 或 tableAlias.tenantId</p>
     *
     * @param table 表对象
     * @return 字段
     */
    protected static Column getAliasColumn(Table table) {
        StringBuilder column = new StringBuilder();
        if (null == table.getAlias()) {
            column.append(table.getName());
        } else {
            column.append(table.getAlias().getName());
        }
        column.append(".");
        column.append(tenantIdColumn);
        return new Column(column.toString());
    }
    public static boolean ignoreTable(String tableName) {
        return ignoreTables.stream().anyMatch((t) -> t.equalsIgnoreCase(tableName));
    }
}
