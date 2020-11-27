package com.jiguang.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jiguang.test.enums.SetMealEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (city)实体类
 *
 * @author kancy
 * @since 2020-09-28 16:39:52
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("city")
public class City extends Model<City> implements Serializable {
    private static final long serialVersionUID = 1L;
    private SetMealEnum setMealEnum;

    /**
     * id
     */
	private Long id;
    /**
     * city
     */
    private String city;
    /**
     * area
     */
    private String area;
    /**
     * company
     */
    private String company;
    /**
     * name
     */
    private String name;


}