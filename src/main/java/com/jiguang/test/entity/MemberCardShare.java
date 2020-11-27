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
 * 会员卡分享表(member_card_share)实体类
 *
 * @author kancy
 * @since 2020-11-27 17:43:41
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("member_card_share")
public class MemberCardShare extends Model<MemberCardShare> implements Serializable {
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
     * 患者id
     */
    private String patientId;
    /**
     * 最大共享金额
     */
    private BigDecimal maxShareMoney;
    /**
     * 已使用共享金额
     */
    private BigDecimal usedShareMoney;
    /**
     * 状态：0、正常；1、停用；
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