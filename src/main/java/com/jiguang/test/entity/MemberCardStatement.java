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
 * 储值卡变动记录表(member_card_statement)实体类
 *
 * @author kancy
 * @since 2020-11-27 17:51:05
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("member_card_statement")
public class MemberCardStatement extends Model<MemberCardStatement> implements Serializable {
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
     * 储值卡id
     */
    private String cardId;
    /**
     * 使用患者id
     */
    private String usePatientId;
    /**
     * 变动类型：1、充值；2、消费
     */
    private Integer changeType;
    /**
     * 旧储值余额
     */
    private BigDecimal oldStoredMoney;
    /**
     * 新储值余额
     */
    private BigDecimal newStoredMoney;
    /**
     * 变动储值余额
     */
    private BigDecimal changeStoredMoney;
    /**
     * 旧赠金余额
     */
    private BigDecimal oldGivedMoney;
    /**
     * 新赠金余额
     */
    private BigDecimal newGivedMoney;
    /**
     * 变动赠金余额
     */
    private BigDecimal changeGivedMoney;
    /**
     * 充值方式：1、现金；2、刷卡；3、支付宝；4、微信
     */
    private Integer chargeMethod;
    /**
     * 充值流水号
     */
    private String chargeStatement;
    /**
     * 备注
     */
    private Object comment;
    /**
     * 操作人
     */
    private String operator;
    /**
     * 操作人名
     */
    private String operatorName;
    /**
     * 操作时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(update = "now()")
	private Date updateTime;
    /**
     * 订单id
     */
    private String orderId;

}