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
 * 会员卡批次(member_card_batch)实体类
 *
 * @author kancy
 * @since 2020-11-27 17:38:49
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("member_card_batch")
public class MemberCardBatch extends Model<MemberCardBatch> implements Serializable {
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
     * 批次类型：1、储值卡；2、打折卡
     */
    private String type;
    /**
     * 批次名称
     */
    private String name;
    /**
     * 卡面链接
     */
    private String img;
    /**
     * 账户金额门槛
     */
    private BigDecimal moneyThreshold;
    /**
     * 赠送金额
     */
    private BigDecimal givedMoney;
    /**
     * 赠送金额是否共享 ：0、共享；1、不共享
     */
    private Integer shareGivedMoney;
    /**
     * 可共享的人数，不可共享时为null
     */
    private Integer shareCount;
    /**
     * 折扣 (折扣说明如 50 表示 5 折)
     */
    private Integer discount;
    /**
     * 折扣规则
     */
    private String discountRule;
    /**
     * 折扣是否共享 ：0、共享；1、不共享
     */
    private Integer shareDiscount;
    /**
     * 可使用的服务范围
     */
    private Object serviceIds;
    /**
     * 适用终端：0 全部，1 患者小程序，2 PC门诊结算收银台
     */
    private String terminal;
    /**
     * 有效期（单位：月）
     */
    private Integer validPeriod;
    /**
     * 是否可延期：0、否；1、是
     */
    private Integer canDelay;
    /**
     * 最大可延期次数
     */
    private Integer maxDelayCount;
    /**
     * 单次延期几个月
     */
    private Integer delayMonth;
    /**
     * 商品id
     */
    private String productId;
    /**
     * 会员卡状态：0、正常；1、删除；2、冻结；3、已退卡;4、已转卡；5、退卡中；6、退卡失败
     */
    private String status;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 创建人姓名
     */
    private String creatorName;
    /**
     * 发行数量
     */
    private Integer totalCount;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人
     */
    private String updator;
    /**
     * 更新者姓名
     */
    private String updatorName;
    /**
     * 修改时间
     */
    @TableField(update = "now()")
	private Date updateTime;

}