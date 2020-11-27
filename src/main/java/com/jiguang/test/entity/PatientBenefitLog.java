package com.jiguang.test.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 会员权益操作日志(patient_benefit_log)实体类
 *
 * @author kancy
 * @since 2020-11-27 17:58:12
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("patient_benefit_log")
public class PatientBenefitLog extends Model<PatientBenefitLog> implements Serializable {
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
    private Long orgId;
    /**
     * 患者id
     */
    private String patientId;
    /**
     * 权益id
     */
    private String benefitId;
    /**
     * 权益关系id
     */
    private String patientBenefitId;
    /**
     * 操作类型：1 绑定，2 解绑
     */
    private Integer type;
    /**
     * 创建人ID
     */
    private String creator;
    /**
     * 创建人姓名
     */
    private String creatorName;
    /**
     * 创建时间
     */
    private Date createTime;

}