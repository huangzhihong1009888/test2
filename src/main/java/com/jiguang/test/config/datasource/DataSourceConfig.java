package com.jiguang.test.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author HuangZhiHong
 * @create 2020/9/15 20:17
 **/
@Configuration
public class DataSourceConfig {
    @Bean
    @Primary
    public DataSource dynamicDataSource() {
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setDefaultTargetDataSource(getDataSource1());
        return dataSource;
    }
    public DataSource getDataSource1() {
        DruidDataSource source = new DruidDataSource();
        source.setPassword("root");
        source.setUsername("root");
        source.setUrl("jdbc:mysql://182.92.188.87:3306/test?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&serverTimezone=GMT%2B8");
        source.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return source;
    }
}
