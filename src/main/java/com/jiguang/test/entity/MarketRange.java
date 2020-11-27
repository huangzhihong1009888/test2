package com.jiguang.test.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 营销范围表(market_range)实体类
 *
 * @author kancy
 * @since 2020-11-27 17:29:45
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("market_range")
public class MarketRange extends Model<MarketRange> implements Serializable {
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
    private Integer origId;
    /**
     * 营销类型，1：活动
     */
    private Integer marketType;
    /**
     * 范围类型，1:分类，2：x项目
     */
    private Integer rangeType;
    /**
     * 减免金额 同 折扣 二选一
     */
    private BigDecimal saleMoney;
    /**
     * 折扣
     */
    private Integer discount;
    /**
     * 范围ID
     */
    private String rangeId;
    /**
     * 营销ID
     */
    private String marketId;
    /**
     * 项目编码
     */
    private String rangeCode;

}