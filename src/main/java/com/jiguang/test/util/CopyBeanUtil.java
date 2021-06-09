package com.jiguang.test.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * jpa cope bean util
 * @author HuangZhiHong
 * @create 2020/10/31 16:05
 **/
public class CopyBeanUtil {

    public static void main(String[] args) {
        //System.out.println(DateUtil.offsetDay(new Date(),4-1));
        Date startDate = DateUtil.parse("2021-06-02 17:10:08","yyyy-MM-dd HH:mm:ss");
        Date endDate = DateUtil.parse("2021-06-02 18:25:30","yyyy-MM-dd HH:mm:ss");

        if(endDate.before(new Date())){
            System.out.println(100);
        }
        if(startDate.after(new Date())){
            System.out.println(0);
        }
        long a = DateUtil.between(startDate, endDate, DateUnit.HOUR)+1;
        long b = DateUtil.between(startDate, new Date(), DateUnit.HOUR);
        System.out.println(a);
        System.out.println(b);
        System.out.println(Math.round((float)16*100/(float)720));

    }
    /**
     * jpa 修改时 把要修改的数据赋值到jap对象
     * newObj 数据库查出来的bean
     * obj 接受参数的bean
     */
    public static  void copyObject(Object newObj, Object obj) {
        if(Objects.isNull(newObj)||Objects.isNull(obj)){
            return;
        }
        Class<?> jpaClazz = newObj.getClass();
        // 遍历往上获取父类，直至最后一个父类
        for (; jpaClazz != Object.class; jpaClazz = jpaClazz.getSuperclass()) {
            // 获取当前类所有的字段
            Field[] jpaField = jpaClazz.getDeclaredFields();
            for (Field field : jpaField) {
                field.setAccessible(true);
                String jpaName = field.getName();
                try {
                    Field objField = getFieldByClass(jpaName, obj);
                    if (Objects.nonNull(objField)) {
                        objField.setAccessible(true);
                        if (Objects.nonNull(objField.get(obj))) {
                            jpaName = jpaName.substring(0, 1).toUpperCase() + jpaName.substring(1);
                            Method method = newObj.getClass().getMethod("set" + jpaName, field.getType());
                            method.invoke(newObj, objField.get(obj));
                        }
                    }

                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }


            }
        }

    }

    private static Field getFieldByClass(String fieldName, Object object) {
        Field field = null;
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (Exception e) {

            }
        }
        return field;
    }
}
