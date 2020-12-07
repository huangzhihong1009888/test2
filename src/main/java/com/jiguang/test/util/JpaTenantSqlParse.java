package com.jiguang.test.util;
import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlDeleteStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlUpdateStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public  class JpaTenantSqlParse {

    public  static final String TABLE_FIELD_TENANT_ID = "tenant_id";

    public  static final DbType MYSQL_STRING = DbType.mysql;

    public  static final List<String> NOT_HAVE_TENANT_ID_TABLE_LIST = Arrays.asList("member_card_batch");

    public static void main(String[] args) throws SQLException {
        String sql = "update  member_card_batch  set id = ? where id =? ";
        System.out.println(getSql(sql));
    }

    public static String getSql(String sql) {
        log.debug("租户解析开始，原始SQL：{}", sql);
        try {
            String newSql = null;
            // 解析sql
            MySqlStatementParser parser = new MySqlStatementParser(sql);
            SQLStatement stmt = parser.parseStatement();
            if (stmt instanceof SQLSelectStatement) {
                SQLSelect sqlSelect = ((SQLSelectStatement)stmt).getSelect();
                if (sqlSelect.getQuery() instanceof SQLUnionQuery) {
                    SQLUnionQuery unionQuery = (SQLUnionQuery)sqlSelect.getQuery();
                    newSql = doUnionSelect(unionQuery);
                } else {
                    newSql = doSelectSql(sql, (MySqlSelectQueryBlock)sqlSelect.getQueryBlock());
                }
            } else if (stmt instanceof MySqlUpdateStatement) {
                MySqlUpdateStatement update = (MySqlUpdateStatement)stmt;
                newSql = doUpdateSql(sql, update);
            } else if (stmt instanceof MySqlDeleteStatement) {
                MySqlDeleteStatement delete = (MySqlDeleteStatement)stmt;
                newSql = doDeleteSql(sql, delete);
            }

            log.debug("租户解析结束，解析后SQL：{}", newSql);
            return newSql;
        }catch (Exception e){
            log.error("租户解析SQL异常{}",e.getMessage());
        }
           
        return sql;
    }

    /**
     * 处理union查询语句
     * @param unionQuery 语句
     * @return 处理结果
     */
    public static String doUnionSelect(SQLUnionQuery unionQuery) {
        SQLSelectQuery left = unionQuery.getLeft();
        SQLSelectQuery right = unionQuery.getRight();
        if (left instanceof SQLUnionQuery) {
            doUnionSelect((SQLUnionQuery)left);
        } else {
            doSelectSql(String.valueOf(left), (MySqlSelectQueryBlock)left);
        }
        if (right instanceof SQLUnionQuery) {
            doUnionSelect((SQLUnionQuery)right);
        } else {
            doSelectSql(String.valueOf(right), (MySqlSelectQueryBlock)right);
        }
        return String.valueOf(unionQuery);
    }

    /**
     * 处理查询语句
     *
     * @param sql SQL
     * @return 处理后的SQL
     */
    public static String doSelectSql(String sql, MySqlSelectQueryBlock select) {
        // 获取where对象
        SQLExpr where = select.getWhere();
        List<SQLSelectItem> selectList = select.getSelectList();
        // 遍历查询的字段，如果查询字段中有子查询 则加上租户ID查询条件
        for (SQLSelectItem sqlSelectItem :selectList) {
            if (sqlSelectItem.getExpr() instanceof SQLQueryExpr) {
                SQLQueryExpr expr = (SQLQueryExpr)sqlSelectItem.getExpr();
                String newFieldSql = doSelectSql(String.valueOf(expr), (MySqlSelectQueryBlock)expr.getSubQuery().getQueryBlock());
                SQLExpr subSelect = SQLUtils.toMySqlExpr(newFieldSql);
                sqlSelectItem.setExpr(subSelect);
            }
        }

        // 获取所查询的表
        SQLTableSource from = select.getFrom();
        // 如果from语句是子查询
        if (from instanceof SQLSubqueryTableSource) {
            String fromString = String.valueOf(from);
            SQLSubqueryTableSource subqueryTableSource = (SQLSubqueryTableSource)from;
            String subQuery = doSelectSql(fromString, (MySqlSelectQueryBlock)subqueryTableSource.getSelect().getQueryBlock());
            log.debug("sql from have subQuery = {}", subQuery);
            SQLSelect sqlSelectBySql = getSqlSelectBySql(subQuery);
            ((SQLSubqueryTableSource)from).setSelect(sqlSelectBySql);
            select.setWhere(getNewWhereCondition(select, where, sql, from));
        }
        // 如果from语句是关联查询
        if (from instanceof SQLJoinTableSource) {
            SQLJoinTableSource joinFrom = (SQLJoinTableSource)from;
            SQLTableSource left = joinFrom.getLeft();
            SQLTableSource right = joinFrom.getRight();
            setTableSourceNewSql(left);
            setTableSourceNewSql(right);
        }
        select.setWhere(getNewWhereCondition(select, where, sql, from));
        return select.toString();
    }

    /**
     * 处理更新语句
     *
     * @param sql sql语句
     * @param stmt 解析的语句
     * @return 修改的后的sql
     */
    public static String doUpdateSql(String sql, SQLStatement stmt) {
        MySqlUpdateStatement update = (MySqlUpdateStatement)stmt;
        SQLExpr where = update.getWhere();
        // 拼接where条件
        update.setWhere(getNewWhereCondition(null, where, sql, update.getTableSource()));
        return update.toString();
    }

    /**
     * 处理delete语句
     *
     * @param sql sql语句
     * @param stmt 解析的语句
     * @return 修改的后的sql
     */
    public static String doDeleteSql(String sql, SQLStatement stmt) {
        MySqlDeleteStatement delete = (MySqlDeleteStatement)stmt;
        SQLExpr where = delete.getWhere();
        // 拼接where条件
        delete.setWhere(getNewWhereCondition(null, where, sql, delete.getTableSource()));
        return delete.toString();
    }

    /**
     * 添加where条件
     *
     * @param where where语句
     * @return 修改后的where条件
     */
    public static SQLExpr getNewWhereCondition(MySqlSelectQueryBlock select, SQLExpr where, String sql,
                                         SQLTableSource tableSource) {
        List<SQLObject> sqlObjects =where.getChildren();
        for (SQLObject sqlObject :sqlObjects) {
            if (sqlObject instanceof SQLBinaryOpExpr) {
                SQLExpr sqlExpr =  ((SQLBinaryOpExpr)sqlObject).getRight();
                // 如果where中包含子查询
                if (sqlExpr instanceof SQLQueryExpr) {
                    SQLSelect subSelect = ((SQLQueryExpr)sqlExpr).subQuery;
                    // 获取子查询语句
                    String subQuery = String.valueOf(subSelect);
                    // 处理子查询语句
                    String newSubQuery = doSelectSql(subQuery, (MySqlSelectQueryBlock)subSelect.getQueryBlock());
                    SQLSelect sqlSelectBySql = getSqlSelectBySql(newSubQuery);
                    ((SQLQueryExpr)sqlExpr).setSubQuery(sqlSelectBySql);
                }
            }
            // 如果where中包含子查询
            if (sqlObject instanceof SQLInSubQueryExpr) {
                SQLSelect subSelect = ((SQLInSubQueryExpr)sqlObject).subQuery;
                // 获取子查询语句
                String subQuery = String.valueOf(subSelect);
                // 处理子查询语句
                String newSubQuery = doSelectSql(subQuery, (MySqlSelectQueryBlock)subSelect.getQueryBlock());
                SQLSelect sqlSelectBySql = getSqlSelectBySql(newSubQuery);
                ((SQLInSubQueryExpr)sqlObject).setSubQuery(sqlSelectBySql);
            }
        }


        SQLBinaryOpExpr binaryOpExprWhere = new SQLBinaryOpExpr(MYSQL_STRING);
        List<SourceFromInfo> tableNameList = new ArrayList<>();
        getTableNames(select, tableSource, tableNameList);
        if (CollectionUtils.isEmpty(tableNameList)) {
            return where;
        }
        // 根据多个表名获取拼接条件
        SQLBinaryOpExpr conditionByTableName = getWhereConditionByTableList(tableNameList);
        log.debug("get tableInfos = {}", JSON.toJSONString(tableNameList));
        // 没有需要添加的条件，直接返回
        if (ObjectUtils.isEmpty(conditionByTableName)) {
            return where;
        }
        // 没有where条件时 则返回需要添加的条件
        if (where == null) {
            return conditionByTableName;
        }
        binaryOpExprWhere.setLeft(conditionByTableName);
        binaryOpExprWhere.setOperator(SQLBinaryOperator.BooleanAnd);
        binaryOpExprWhere.setRight(where.clone());
        if (isTenantIdAndOrCondition(where)) {
            log.debug("the sql contains or condition by tenant_id, sql = {}", sql);
        }
        return binaryOpExprWhere;
    }

    /**
     * 根据from语句得到的表名拼接条件
     *
     * @param tableNameList 表名列表
     * @return 拼接后的条件
     */
    public static SQLBinaryOpExpr getWhereConditionByTableList(List<SourceFromInfo> tableNameList) {
        // 先过滤掉不需要添加条件的
        tableNameList =
                tableNameList.stream().filter(fromInfo -> fromInfo.isNeedAddCondition()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(tableNameList)) {
            return null;
        }
        SQLBinaryOpExpr allCondition = new SQLBinaryOpExpr(MYSQL_STRING);
        for (int i = 0; i < tableNameList.size(); i++) {
            SourceFromInfo tableNameInfo = tableNameList.get(i);
            SQLBinaryOpExpr thisTenantIdWhere = getTenantIdCondition(tableNameInfo);
            // 如果是最后一个且不是第一个则将当期table条件设置为右侧条件
            if (i > 0 && i == tableNameList.size() - 1) {
                allCondition.setOperator(SQLBinaryOperator.BooleanAnd);
                allCondition.setRight(thisTenantIdWhere);
                break;
            }
            // 如果是只有一个table 则直接设置最终条件为当期table条件
            if (tableNameList.size() == 1) {
                allCondition = thisTenantIdWhere;
                break;
            }
            if (allCondition.getLeft() == null) {
                allCondition.setLeft(thisTenantIdWhere);
            } else {
                SQLBinaryOpExpr condition = getAndCondition((SQLBinaryOpExpr)allCondition.getLeft(), thisTenantIdWhere);
                allCondition.setLeft(condition);
            }
        }
        return allCondition;
    }

    /**
     * 拼接and条件
     *
     * @param left 左侧条件
     * @param right 右侧条件
     * @return 拼接后的条件
     */
    public static SQLBinaryOpExpr getAndCondition(SQLBinaryOpExpr left, SQLBinaryOpExpr right) {
        SQLBinaryOpExpr condition = new SQLBinaryOpExpr(MYSQL_STRING);
        condition.setLeft(left);
        condition.setOperator(SQLBinaryOperator.BooleanAnd);
        condition.setRight(right);
        return condition;
    }

    /**
     * 根据表信息拼接tenantId 条件
     *
     * @param tableNameInfo 表信息
     * @return 拼接后的条件
     */
    public static SQLBinaryOpExpr getTenantIdCondition(SourceFromInfo tableNameInfo) {
        SQLBinaryOpExpr tenantIdWhere = new SQLBinaryOpExpr(MYSQL_STRING);
        String tenantId = "5000";
        if (StringUtils.isEmpty(tableNameInfo.getAlias())) {
            // 拼接新的条件
            tenantIdWhere.setOperator(SQLBinaryOperator.Equality);
            tenantIdWhere.setLeft(new SQLIdentifierExpr(TABLE_FIELD_TENANT_ID));
            // 设置当前租户ID条件
            tenantIdWhere.setRight(new SQLCharExpr(tenantId));
        } else {
            // 拼接别名条件
            tenantIdWhere.setLeft(new SQLPropertyExpr(tableNameInfo.getAlias(), TABLE_FIELD_TENANT_ID));
            tenantIdWhere.setOperator(SQLBinaryOperator.Equality);
            tenantIdWhere.setRight(new SQLCharExpr(tenantId));
        }
        return tenantIdWhere;
    }

    /**
     * 查询所有的表信息
     *
     * @param select from语句对应的select语句
     * @param tableSource from语句
     * @param tableNameList sql中from语句中所有表信息
     */
    public static void getTableNames(MySqlSelectQueryBlock select, SQLTableSource tableSource,
                               List<SourceFromInfo> tableNameList) {
        // 子查询
        if (tableSource instanceof SQLSubqueryTableSource) {
            SourceFromInfo fromInfo = new SourceFromInfo();
            fromInfo.setSubQuery(true);
            SQLSubqueryTableSource subqueryTableSource = (SQLSubqueryTableSource)tableSource;
            // 设置别名
            fromInfo.setAlias(subqueryTableSource.getAlias());
            List<SQLSelectItem> selectList = select.getSelectList();
            Optional.ofNullable(selectList).filter(list -> !CollectionUtils.isEmpty(selectList)).map(list -> {
                list.forEach(item -> {
                    String itemString = String.valueOf(item);
                    // 如果查询字段中有tenant_id 字段则需要加条件 否则不用加
                    if (StringUtils.contains(itemString, TABLE_FIELD_TENANT_ID)) {
                        fromInfo.setNeedAddCondition(true);
                        return;
                    }
                });
                return list;
            });
            tableNameList.add(fromInfo);
        }
        // 连接查询
        if (tableSource instanceof SQLJoinTableSource) {
            SQLJoinTableSource joinSource = (SQLJoinTableSource)tableSource;
            SQLTableSource left = joinSource.getLeft();
            SQLTableSource right = joinSource.getRight();
            // 子查询则递归获取
            if (left instanceof SQLSubqueryTableSource) {
                getTableNames((MySqlSelectQueryBlock)((SQLSubqueryTableSource)left).getSelect().getQuery(), left,
                        tableNameList);
            }
            // 子查询则递归获取
            if (right instanceof SQLSubqueryTableSource) {
                getTableNames((MySqlSelectQueryBlock)((SQLSubqueryTableSource)right).getSelect().getQuery(), right,
                        tableNameList);
            }
            // 连接查询 左边是单表
            if (left instanceof SQLExprTableSource) {
                addOnlyTable(left, tableNameList);
            }
            // 连接查询 右边是单表
            if (right instanceof SQLExprTableSource) {
                addOnlyTable(right, tableNameList);
            }
            // 连接查询 左边还是连接查询 则递归继续获取表名
            if (left instanceof SQLJoinTableSource) {
                getTableNames(null, left, tableNameList);
            }
            // 连接查询 右边还是连接查询 则递归继续获取表名
            if (right instanceof SQLJoinTableSource) {
                getTableNames(null, right, tableNameList);
            }
        }
        // 普通表查询
        if (tableSource instanceof SQLExprTableSource) {
            addOnlyTable(tableSource, tableNameList);
        }
    }

    /**
     * 如果当前from语句只有单表，则添加到list中
     *
     * @param tableSource from语句
     * @param tableNameList 表信息list
     */
    public static void addOnlyTable(SQLTableSource tableSource, List<SourceFromInfo> tableNameList) {
        SourceFromInfo fromInfo = new SourceFromInfo();
        // 普通表查询
        String tableName = String.valueOf(tableSource);
        fromInfo.setTableName(tableName);
        fromInfo.setAlias(tableSource.getAlias());
        if (!NOT_HAVE_TENANT_ID_TABLE_LIST.contains(tableName)) {
            fromInfo.setNeedAddCondition(true);
        }
        tableNameList.add(fromInfo);
    }

    /**
     * 条件中是否为 and or 表达式
     *
     * @param where sql中where条件语句
     * @return 判断结果
     */
    public static boolean isContainsTenantIdCondition(SQLExpr where) {
        if (!(where instanceof SQLBinaryOpExpr)) {
            return false;
        }
        SQLBinaryOpExpr binaryOpExpr = (SQLBinaryOpExpr)where;
        SQLExpr left = binaryOpExpr.getLeft();
        SQLExpr right = binaryOpExpr.getRight();
        // 是否包含tenant_id 为查询条件
        if (!(left instanceof SQLBinaryOpExpr) && !(right instanceof SQLBinaryOpExpr)
                && (TABLE_FIELD_TENANT_ID.equals(String.valueOf(left))
                || TABLE_FIELD_TENANT_ID.equals(String.valueOf(right)))) {
            return true;
        }
        return false;
    }

    /**
     * 是否包括 or tenant_id = xx的条件
     *
     * @param where sql中where条件语句
     * @return 判断结果
     */
    public static boolean isTenantIdAndOrCondition(SQLExpr where) {
        if (!(where instanceof SQLBinaryOpExpr)) {
            return false;
        }
        SQLBinaryOpExpr binaryOpExpr = (SQLBinaryOpExpr)where;
        if ((isContainsTenantIdCondition(binaryOpExpr.getLeft())
                || isContainsTenantIdCondition(binaryOpExpr.getRight()))
                && "BooleanOr".equals(String.valueOf(binaryOpExpr.getOperator()))) {
            return true;
        }
        return isTenantIdAndOrCondition(binaryOpExpr.getLeft()) || isTenantIdAndOrCondition(binaryOpExpr.getRight());
    }

    /**
     * from语句是子查询的 处理子查询 并更新from语句
     *
     * @param tableSource from语句
     */
    public static void setTableSourceNewSql(SQLTableSource tableSource) {
        if (!(tableSource instanceof SQLSubqueryTableSource)) {
            return;
        }
        SQLSubqueryTableSource subqueryTableSource = (SQLSubqueryTableSource)tableSource;
        String leftSubQueryString = String.valueOf(subqueryTableSource.getSelect());
        String newLeftSubQueryString = doSelectSql(leftSubQueryString, (MySqlSelectQueryBlock)subqueryTableSource.getSelect().getQueryBlock());
        SQLSelect sqlselect = getSqlSelectBySql(newLeftSubQueryString);
        subqueryTableSource.setSelect(sqlselect);
    }

    /**
     * 将String类型select sql语句转化为SQLSelect对象
     *
     * @param sql 查询SQL语句
     * @return 转化后的对象实体
     */
    public static SQLSelect getSqlSelectBySql(String sql) {
        SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, MYSQL_STRING);
        List<SQLStatement> parseStatementList = parser.parseStatementList();
        if (CollectionUtils.isEmpty(parseStatementList)) {
            return null;
        }
        SQLSelectStatement sstmt = (SQLSelectStatement)parseStatementList.get(0);
        SQLSelect sqlselect = sstmt.getSelect();
        return sqlselect;
    }
}
