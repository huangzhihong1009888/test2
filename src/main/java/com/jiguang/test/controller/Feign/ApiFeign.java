package com.jiguang.test.controller.Feign;

import com.jiguang.test.jdk.aop.RestFeign;
import org.springframework.web.bind.annotation.GetMapping;

@RestFeign(name ="test")
//@FeignClient("test")
public interface ApiFeign {

    @GetMapping("/test")
    Object getApi();
    @GetMapping("/test1")
    String getName();

}
