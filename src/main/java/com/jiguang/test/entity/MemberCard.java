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
 * 会员卡(member_card)实体类
 *
 * @author kancy
 * @since 2020-11-27 17:34:33
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("member_card")
public class MemberCard extends Model<MemberCard> implements Serializable {
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
     * 卡批次id
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
     * 会员卡类型
     */
    private String batchType;
    /**
     * 购买者
     */
    private String cardBuyer;
    /**
     * 储值金额
     */
    private BigDecimal storedMoney;
    /**
     * 赠送金额
     */
    private BigDecimal givedMoney;
    /**
     * 有效期开始时间
     */
    private Date startDate;
    /**
     * 有效期结束时间
     */
    private Date endDate;
    /**
     * 已延期次数
     */
    private Integer delayedCount;
    /**
     * 状态：0、正常；1、删除；2、冻结；3、已退卡；4、已转卡；5、退卡中；6、退卡失败
     */
    private String status;
    /**
     * 来源ID
     */
    private String sourceId;
    /**
     * 来源类型
     */
    private Integer sourceType;
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