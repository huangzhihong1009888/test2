package com.jiguang.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * Application
 * </p>
 *
 * @author kancy
 * @since 2020-11-27 17:03:20
 * @description 由 Mybatisplus Code Generator 创建
 **/
@MapperScan(basePackages = {"com.jiguang.test.dao.mybatis"})
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
