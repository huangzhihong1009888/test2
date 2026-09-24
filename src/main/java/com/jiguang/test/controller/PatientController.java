package com.jiguang.test.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author duguohui
 */
@Slf4j
@Api(tags = "患者档案信息接口")
@RestController
@RequestMapping("/patient")
public class PatientController {


    @ApiOperation("查询患者信息（医保专用）111")
    @GetMapping("/getPatientById")
    public String getByYb(){
        log.info("我进来了111111111111111111");
        return "恭喜你查询到患者信息1111111";
    }


}
