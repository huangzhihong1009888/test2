package com.jiguang.test.util;


import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;

/**
 * mybatis insert sql 拦截
 * @author HuangZhiHong
 * @create 2020/10/20 19:58
 **/
@Intercepts({@Signature(type = Executor.class,method = "update",args = {MappedStatement.class,Object.class})})
@Component
public class ParamInterceptor implements Interceptor {
    private static final String TENANT_ID = "tenantId";
    private static final String CREATE_TIME = "createTime";
    private static final String CREATOR = "creator";
    private static final String CREATOR_NAME = "creatorName";
    private static final String UPDATE_TIME = "updateTime";
    private static final String UPDATOR = "updator";
    private static final String UPDATOR_NAME = "updatorName";
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //获取mybatis xml对象
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];

        // 获取 SQL 命令
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        //判断是否是 insert 标签的sql
        if (SqlCommandType.INSERT.equals(sqlCommandType)) {
            // 获取传入xml的参数
            Object parameter = invocation.getArgs()[1];

            //如果有@param注解或者多个参数 则得到ParamMap 循环遍历赋值，如果没有注解 只有一个对象参数 则直接赋值
            if(parameter instanceof MapperMethod.ParamMap){
                MapperMethod.ParamMap map = (MapperMethod.ParamMap) parameter;
                //循环参数map得到每一个obj 判断是否有需要填充的字段 并进行赋值
                for(Object key : map.keySet()){
                    Object  obj = map.get(key);
                    addValue(obj,TENANT_ID,"5000");
                    addValue(parameter,CREATE_TIME,new Date());
                }
            }else{
                addValue(parameter,TENANT_ID,"5000");
                addValue(parameter,CREATE_TIME,new Date());
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {

        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private void addValue(Object obj,String fieldName,Object value ) {
        try {
            //只有对象中有这个字段 才进行赋值
            if(isExistFieldName(fieldName,obj)){
                Class<?> aClass = obj.getClass();
                // 获取私有成员变量
                Field field = aClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object defaultValue = field.get(obj);
                if(null == defaultValue){
                    field.set(obj, value);
                }
            }
        }catch (Exception ignored){}

    }

    public static Boolean isExistFieldName(String fieldName, Object obj) {
        boolean flag = false;
        //如果是基础类型或者string类型返回false
        if(ClassUtils.isPrimitiveOrWrapper(obj.getClass())||String.class.isAssignableFrom(obj.getClass())){
            return false;
        }
        //获取这个类的所有属性
        Field[] fields = obj.getClass().getDeclaredFields();
        //循环遍历所有的fields
        for(Field field : fields) {
            if (field.getName().equals(fieldName)) {
                flag = true;
                break;
            }
        }

        return flag;
    }


}
