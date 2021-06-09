package com.jiguang.test.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Invocation handler
 *
 * @param <T> parameter
 * @author HuangZhiHong
 * @create 2020 /8/25 18:01
 */
public class MybatisMapperProxy<T> implements InvocationHandler {


    /**
     * 这个就是我们要代理的真实对象
     */
    private final T mapperInterface;

    /**
     * 构造方法，给我们要代理的真实对象赋初值
     *
     * @param mapperInterface subject
     */
    public MybatisMapperProxy( T mapperInterface)
    {
        this.mapperInterface = mapperInterface;
    }

    /**
     * 该方法负责集中处理动态代理类上的所有方法调用。
     * 调用处理器根据这三个参数进行预处理或分派到委托类实例上反射执行
     *
     * @param proxy  代理类实例
     * @param method 被调用的方法对象
     * @param args   调用参数
     * @return object
     * @throws Throwable throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        //在代理真实对象前我们可以添加一些自己的操作
        System.out.println("开启事务");
        System.out.println(proxy.getClass().getName());
        System.out.println(method.getDeclaringClass());
        //当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        } else {
            return method.invoke(mapperInterface,args);
        }
    }

    /**
     * Get object t
     * @return the t
     */
    public  T newInstance(MybatisMapperProxy<T> mapperProxy){

        return (T)Proxy.newProxyInstance(mapperInterface.getClass().getClassLoader(), mapperInterface.getClass().getInterfaces(), mapperProxy);
    };

}
