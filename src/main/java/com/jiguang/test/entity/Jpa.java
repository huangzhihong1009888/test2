package com.jiguang.test.entity;

import com.alibaba.fastjson.JSON;
import com.jiguang.test.enums.SetMealEnum;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (city)实体类
 *
 * @author kancy
 * @since 2020-09-28 16:39:52
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@Table(name = "jpa")
public class Jpa  implements Serializable {

    private SetMealEnum setMealEnum;
    /**
     * id
     */
    @Id
	private Long id;
    /**
     * name
     */
    private String name;




}