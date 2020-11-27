package com.jiguang.test.config.datasource;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author HuangZhiHong
 * @create 2020/9/15 20:14
 **/
@Slf4j
public class ThreadLocalUtils {
    public static ThreadLocal<Map<String, String>> threadLocal = new ThreadLocal();

    public static String setTenantId(String value){
        Map<String, String> map = (Map)threadLocal.get();
        if (null == map) {
            map = new ConcurrentHashMap();
            threadLocal.set(map);
        }
        return (String)((Map)threadLocal.get()).put("key",value);
    }
    public static String getTenantId(){
        return threadLocal.get()==null ? "5000":threadLocal.get().get("key");
    }
}
