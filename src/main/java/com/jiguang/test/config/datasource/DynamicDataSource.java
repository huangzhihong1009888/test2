package com.jiguang.test.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HuangZhiHong
 * @create 2020/9/15 18:48
 **/
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Value("${application.saasEnable:false}")
    boolean saasEnable;

    /**
     * 该方法返回一个key，该key是bean中的beanName，并赋值给lookupKey，
     * 由此key可以通过resolvedDataSources属性的键来获取对应的DataSource值，
     * 从而达到数据源切换的功能。
     */
    @Override
    protected Object determineCurrentLookupKey() {
        log.info("---------------------------come---------"+ThreadLocalUtils.getTenantId());
        return ThreadLocalUtils.getTenantId();
    }
    Map<Object, Object> targetDataSources = new HashMap<>();
    /**
     *  设置默认数据源super.setTargetDataSources 必须不为null
     */
    @Override
    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
        this.targetDataSources.put("5000", defaultTargetDataSource);
        super.setTargetDataSources(this.targetDataSources);
        super.setDefaultTargetDataSource(defaultTargetDataSource);
    }

   /* @Override
    public Connection getConnection() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        

            conn = super.getConnection();
            stmt = conn.createStatement();
            stmt.executeQuery("select 1 ");
            Connection var37 = conn;
            return var37;
    }*/
    /**
     * 根据参数动态获取数据源
     */
    @Override
    protected DataSource determineTargetDataSource() {
        if(!saasEnable){
            log.info("没有开启saas模式，直接返回工程配置的数据源");
            return super.determineTargetDataSource();
        }else{
            log.info("开启saas模式");
            String tenantId = ThreadLocalUtils.getTenantId();
            if (this.targetDataSources.containsKey(tenantId)) {
                return (DataSource)this.targetDataSources.get(tenantId);
            }else{
               //根据参数 动态获取数据源 例如可以去数据库查询
                DataSource dataSource =getDataSource2( tenantId);
                if(dataSource==null){
                    log.error("没有找到数据源返回默认数据源");
                    //父类方法 根据 determineCurrentLookupKey 得到key，获取数据源
                    return super.determineTargetDataSource();
                }
                //获取数据源放入后，参数为key 数据源为value 放入setTargetDataSources
                this.targetDataSources.put(tenantId, dataSource);
                super.setTargetDataSources(this.targetDataSources);
                //resolvedDataSources这个数据结构是通过targetDataSources构建而来，
                // 存储结构也是数据库标识和数据源的映射关系
                //因为数据源bean是动态生成的，然后需要添加到targetDataSources中，
                // 此时需要调用afterPropertiesSet()方法，来通知spring有bean更新。
                super.afterPropertiesSet();
                return dataSource;
            }
        }
    }

    /**
     * 模拟根据参数去数据库查询 数据源
     */
    public DataSource getDataSource2(String tenantId) {
        if(tenantId.equals("22")){
            return  null;
        }
        DruidDataSource source = new DruidDataSource();
        source.setPassword("dev-user");
        source.setUsername("dev-user");
        source.setUrl("jdbc:mysql://10.100.10.173:4000/thc_titan_member?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&serverTimezone=GMT%2B8");
        source.setDriverClassName("com.mysql.cj.jdbc.Driver");

        return source;
    }

}
