package com.jiguang.test.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author HuangZhiHong
 * @create 2020/9/25 17:25
 **/
@Component
public class TheadUtil {
    private  ThreadLocal<Object> threadLocal = new InheritableThreadLocal<>();
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    public void luck(){
        System.out.println(threadLocal.get());
        threadLocal.set(UUID.randomUUID().toString());
    }

    public Future<?> submit(Runnable threadService) {
        Future<?> future = this.taskExecutor.submit(() -> {
            try {
                threadService.run();
            } catch (Exception var4) {
                var4.printStackTrace();
            }

        });
        return future;
    }

    public <T> Future<T> submit(final Callable<T> callable) {
        Future<T> future = this.taskExecutor.submit(() -> {
            T result = null;

            try {
                result = callable.call();
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return result;
        });
        return future;
    }

}
