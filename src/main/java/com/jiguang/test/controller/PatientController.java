package com.jiguang.test.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author duguohui
 */
@Api(tags = "患者档案信息接口")
@RestController
@RequestMapping("/patient")
public class PatientController {


    @ApiOperation("查询患者信息（医保专用）")
    @PostMapping("/getPatientById")
    public String getByYb(){
        return "恭喜你查询到患者信息（医保专用）";
    }


}
