package com.jiguang.test.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 科室范围表(market_dpt_range)实体类
 *
 * @author kancy
 * @since 2020-11-27 17:15:13
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("market_dpt_range")
public class MarketDptRange extends Model<MarketDptRange> implements Serializable {
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
     * 营销类型，1：活动,2身份卡,3优惠券,4储值卡,5套餐
     */
    private Integer marketType;
    /**
     * 范围类型，3：科室
     */
    private Integer rangeType;
    /**
     * 范围ID
     */
    private String rangeId;
    /**
     * 范围code
     */
    private String rangeCode;
    /**
     * 营销ID
     */
    private String marketId;

}