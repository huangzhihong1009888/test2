package com.jiguang.test.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 会员权益(patient_benefit)实体类
 *
 * @author kancy
 * @since 2020-11-27 17:54:44
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("patient_benefit")
public class PatientBenefit extends Model<PatientBenefit> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId
	private String id;
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 机构id
     */
    private Long origId;
    /**
     * 患者id
     */
    private String patientId;
    /**
     * 权益id
     */
    private String benefitId;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 终止时间
     */
    private Date endDate;
    /**
     * 状态：0、正常；1、删除
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(update = "now()")
	private Date updateTime;

}