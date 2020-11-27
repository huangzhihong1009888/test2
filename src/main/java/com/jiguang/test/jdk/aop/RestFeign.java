package com.jiguang.test.jdk.aop;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Rest feign
 *
 * @param
 * @author HuangZhiHong
 * @create 2020 /9/2 13:07
 * @return
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestFeign {
    /**
     * Name string
     *
     * @return the string
     */
    String name() default "";
}
