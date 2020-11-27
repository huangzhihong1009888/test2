package com.jiguang.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/*@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})*/
@MapperScan(basePackages = {"com.jiguang.test.mapper"})
@SpringBootApplication
//@EnableFeignClients
public class TestApplication {

    public static void main(String[] args) {
       SpringApplication.run(TestApplication.class, args);
    }

   /* @Autowired
    private Test2Service test2Service;
    @Bean(name = "beanTest")
    public BeanTest getBean(){
        return new BeanTest();
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(TestApplication.class, args);
        BeanTest bookingService = (BeanTest) ctx.getBean("beanTest");
        System.out.println(bookingService);
    }*/

}
