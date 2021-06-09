package com.jiguang.test.controller;

import com.jiguang.test.service.impl.JpaService;
import com.jiguang.test.service.impl.MybatisService;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@RestController
@RequestMapping("mybatis")
public class MybatisController {

    @Resource
    private MybatisService mybatisService;
    @GetMapping("/get")
    public Object get(Long name){
        return mybatisService.findByName(name);

    }
    @GetMapping("/test")
    public void test(HttpServletRequest req){

        System.out.println(req.getParameter("url"));
    }
    @PostMapping("/add")
    public Object add(String name){
        return mybatisService.add(name);
    }
    @PostMapping("/update")
    public Object update(String name){
        return mybatisService.update(name);
    }
    @GetMapping("/del")
    public void del(Long id){
        mybatisService.del(id);
    }
}
