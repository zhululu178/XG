package com.letsup.habit.app.backend.util.mybatis;


import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.update.Update;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 只针对单条记录的更新，做乐观锁判读
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
@Component
public class OptimisticLockInterceptor implements Interceptor {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private static List<String> IDS = new ArrayList<>(2);
    static {
        IDS.add("com.letsup.habit.app.backend.dao.mapper.HabAppHabitMapperExt.updateByPrimaryKeySelective");
        IDS.add("com.letsup.habit.app.backend.dao.mapper.HabAppHabitDailyRecordMapperExt.updateByPrimaryKeySelective");
        IDS.add("com.letsup.habit.app.backend.dao.mapper.HabAppFamilyMemberMapperExt.updateByPrimaryKeySelective");
    }

    //数据库列名
    private static String VERSION_COLUMN = "version";
    //实体类字段名
    private static String VERSION_FIELD = "version";
    //拦截类型
    private static final String METHOD_TYPE = "prepare";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String interceptMethod = invocation.getMethod().getName();
        if (!METHOD_TYPE.equals(interceptMethod)) {
            return invocation.proceed();
        }

        StatementHandler handler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = SystemMetaObject.forObject(handler);
        MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        SqlCommandType sqlCmdType = ms.getSqlCommandType();
        if (sqlCmdType != SqlCommandType.UPDATE) {
            return invocation.proceed();
        }
        if(!IDS.contains(ms.getId())) {//过滤掉不必要的方法
            return invocation.proceed();
        }
        Object originalVersion = metaObject.getValue("delegate.boundSql.parameterObject." + VERSION_FIELD);
        if (originalVersion == null) {//没有设置version字段
            throw new DataIntegrityViolationException("version字段值不能为空");
        }
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        String originalSql = boundSql.getSql();
        if (logger.isDebugEnabled()) {
            logger.debug("originalSql: " + originalSql);
        }
        originalSql = addVersionToSql(originalSql, VERSION_COLUMN, originalVersion);
        metaObject.setValue("delegate.boundSql.sql", originalSql);
        metaObject.setValue("delegate.boundSql.parameterObject." + VERSION_FIELD, (Long) originalVersion + 1);
        if (logger.isDebugEnabled()) {
            logger.debug("originalSql after add version: " + originalSql);
            logger.debug("delegate.boundSql.parameterObject." + VERSION_FIELD + originalSql);
        }
        return invocation.proceed();
    }

    private String addVersionToSql(String originalSql, String versionColumnName, Object originalVersion) {
        try {
            Statement stmt = CCJSqlParserUtil.parse(originalSql);
            if (!(stmt instanceof Update)) {
                return originalSql;
            }
            Update update = (Update) stmt;
            Expression where = update.getWhere();
            if (where != null) {
                AndExpression and = new AndExpression(where, buildVersionEquals(versionColumnName, originalVersion));
                update.setWhere(and);
            } else {
                update.setWhere(buildVersionEquals(versionColumnName, originalVersion));
            }
            return stmt.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return originalSql;
        }
    }

    private Expression buildVersionEquals(String versionColumnName, Object originalVersion) {
        EqualsTo equal = new EqualsTo();
        Column column = new Column();
        column.setColumnName(versionColumnName);
        equal.setLeftExpression(column);
        LongValue val = new LongValue(originalVersion.toString());
        equal.setRightExpression(val);
        return equal;
    }

    @Override
    public Object plugin(Object target) {
        if(target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
