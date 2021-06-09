package com.jiguang.test.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by cxz on 6/19/14.
 */
@Slf4j
@Component
@Aspect
public class MybatisAop {
    @Around("@annotation(com.jiguang.test.config.aop.MyTransactional)")
    public Object handle(ProceedingJoinPoint joinPoint) {
        log.info("aop============进来了===========");
        try {
            final Object proceed = joinPoint.proceed();
            return  proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
