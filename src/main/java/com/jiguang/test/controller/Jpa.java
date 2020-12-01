package com.jiguang.test.controller;

import com.jiguang.test.service.impl.JpaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLException;

@RestController
public class Jpa {

    @Resource
    private JpaService jpaService;
    @GetMapping("/jpa")
    public Object getJpa(String name){
        try {
            return jpaService.findByName(name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    @PostMapping("/add")
    public Object add(String name){
        return jpaService.add(name);
    }
    @PostMapping("/update")
    public Object update(String name){
        return jpaService.update(name);
    }
    @GetMapping("/del")
    public void del(Long id){
         jpaService.del(id);
    }
}
