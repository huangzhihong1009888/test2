package com.jiguang.test.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author HuangZhiHong
 * @create 2020/9/15 20:17
 **/
@Configuration
public class DataSourceConfig {

    @Value("${db.mysql.url}")
    private String url;
    @Value("${db.mysql.username}")
    private String username;
    @Value("${db.mysql.password}")
    private String password;
    @Bean
    public DataSource getDataSource() {
        DruidDataSource source = new DruidDataSource();
        source.setPassword(password);
        source.setUsername(username);
        source.setUrl(url);
        source.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return source;
    }
}
