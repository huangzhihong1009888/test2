package com.jiguang.test.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author HuangZhiHong
 * @create 2020/9/15 20:17
 **/
@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource getDataSource() {
        DruidDataSource source = new DruidDataSource();
        source.setPassword("root");
        source.setUsername("root");
        source.setUrl("jdbc:mysql://localhost:3306/yoga?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&serverTimezone=GMT%2B8");
        source.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return source;
    }
}
