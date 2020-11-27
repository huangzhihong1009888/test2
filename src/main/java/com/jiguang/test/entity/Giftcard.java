package com.jiguang.test.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 优惠券(giftcard)实体类
 *
 * @author kancy
 * @since 2020-11-27 17:03:20
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("giftcard")
public class Giftcard extends Model<Giftcard> implements Serializable {
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
     * 优惠券批次id
     */
    private String batchId;
    /**
     * 机构id
     */
    private Long origId;
    /**
     * 患者id
     */
    private String patientId;
    /**
     * 优惠券编码
     */
    private String code;
    /**
     * 使用患者id
     */
    private String usePatientId;
    /**
     * 使用时间
     */
    private Date useTime;
    /**
     * 使用在哪个订单上
     */
    private String orderId;
    /**
     * 状态：1、未使用；2、已使用；3、冻结；4、作废
     */
    private String status;
    /**
     * 销售价
     */
    private BigDecimal salePrice;
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