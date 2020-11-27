package com.jiguang.test.jdk;

/**
 * Real subject
 *
 * @author HuangZhiHong
 * @create 2020 /8/25 17:57
 */
public class RealSubject implements Subject{
    /**
     * Say hello string
     *
     * @param name name
     * @return the string
     */
    @Override
    public String sayHello(String name) {
        System.out.println(name);
        return "hello " + name;
    }

    /**
     * Say good bye string
     *
     * @return the string
     */
    @Override
    public String sayGoodBye() {
        return " good bye ";
    }
}
