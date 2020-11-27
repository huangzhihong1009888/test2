package com.jiguang.test.jdk.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Method;

/**
 * 接口调用完毕后发送kafka消息
 *
 * @author LiuSen
 * @date 2019 /11/05
 */
@Slf4j
@Aspect
@Component
public class TestAop {


    /**
     * Send
     */
    @Pointcut("execution(* com.jiguang.test.controller.Feign.*+.*(..) )")
    public void send(){

    }

    /**
     * Do before *
     *
     * @param joinPoint join point
     * @throws NoSuchMethodException no such method exception
     */
    @Before("send()")
    public void doBefore(JoinPoint joinPoint) throws NoSuchMethodException {
        Class<?> interfaces[] =joinPoint.getTarget().getClass().getInterfaces();
        RestFeign restFeign =interfaces[0].getAnnotation(RestFeign.class);
        System.out.println(restFeign.name());
        // 获取调用参数的名称
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method methods =interfaces[0].getMethod(methodSignature.getMethod().getName());
        System.out.println(methods);
        GetMapping getMapping =methods.getAnnotation(GetMapping.class);
        System.out.println(getMapping.value()[0]);
        System.out.println("开启事务");
    }

    /**
     * Do after *
     *
     * @param joinPoint join point
     */
    @AfterReturning("send()")
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("结束事务");
    }
   /* @Around("send() && @annotation(restFeign)")
    public Object around(ProceedingJoinPoint point, RestFeign restFeign) throws Throwable {

        point.proceed();
        return null;
    }*/
}
