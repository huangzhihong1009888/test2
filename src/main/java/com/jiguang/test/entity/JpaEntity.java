package com.jiguang.test.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jiguang.test.enums.EnumConvertFactory;
import com.jiguang.test.enums.EnumConverter;
import com.jiguang.test.enums.SetMealEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * (stu)实体类
 *
 * @author kancy
 * @since 2020-09-04 19:56:57
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity(name ="jpa_table")
public class JpaEntity {
    private static final long serialVersionUID = 1L;
    @Convert(converter = SetMealEnum.Converter.class)
    private SetMealEnum setMealEnum;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    /**
     * 名字
     */
    private String name;
    /**
     * zhi
     */
    private String value;

    @Transient
    private String test;
    private String tenantId;


}