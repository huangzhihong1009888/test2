package com.jiguang.test.jdk;

/**
 * Subject
 *
 * @author user
 */
public interface Subject {
    /**
     * 你好
     *
     * @param name name
     * @return string
     */
    public String sayHello(String name);

    /**
     * 再见
     *
     * @return string
     */
    public String sayGoodBye();
}
