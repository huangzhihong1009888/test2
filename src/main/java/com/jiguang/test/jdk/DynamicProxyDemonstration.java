package com.jiguang.test.jdk;

import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Dynamic proxy demonstration
 *
 * @author HuangZhiHong
 * @create 2020 /8/25 18:08
 */
public class DynamicProxyDemonstration {
    /**
     * Main
     *
     * @param args args
     */
    public static void main(String[] args)
    {
        //代理的真实对象
        Subject realSubject = new RealSubject();
        /**
         * 该方法用于为指定类装载器、一组接口及调用处理器生成动态代理类实例
         */
        Subject subject = InvocationHandlerImpl.getObject(realSubject);

        System.out.println("动态代理对象的类型："+subject.getClass().getName());

        String hello = subject.sayHello("执行代码");
        System.out.println(hello);
        //createProxyClassFile();
//        String goodbye = subject.SayGoodBye();
//        System.out.println(goodbye);
    }

    /**
     * Create proxy class file
     */
    private static void createProxyClassFile(){
        String name = "ProxySubject";
        byte[] data = ProxyGenerator.generateProxyClass(name,new Class[]{Subject.class});
        FileOutputStream out =null;
        try {
            out = new FileOutputStream(name+".class");
            System.out.println((new File("hello")).getAbsolutePath());
            out.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null!=out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
