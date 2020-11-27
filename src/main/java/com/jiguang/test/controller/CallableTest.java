package com.jiguang.test.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author hzh
 * @date 2020/3/6 15:24
 */
public class CallableTest implements Callable<Integer> {


    @Override
    public Integer call() throws Exception {
        int i = 0;
        for(;i<5;i++)
        {
            System.out.println(Thread.currentThread().getName()+" "+i);
        }
        return i;
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
            CallableTest callableTest = new CallableTest();
        FutureTask<Integer> ft = new FutureTask<>(callableTest);
        new Thread(ft,"有返回值的线程").start();
        System.out.println("子线程的返回值："+ft.get());
    }
}
