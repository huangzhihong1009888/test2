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
 * 营销范围表(market_org_range)实体类
 *
 * @author kancy
 * @since 2020-11-27 18:19:29
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("market_org_range")
public class MarketOrgRange extends Model<MarketOrgRange> implements Serializable {
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
     * 主键id
     */
    private String rangeId;
    /**
     * 主键id
     */
    private String toolId;
    /**
     * 营销类型：1活动,2身份卡,3优惠券, 4 会员卡
     */
    private Integer toolType;
    /**
     * 机构范围类型：1层级类型,2 区域类型
     */
    private Integer rangeType;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 修改时间
     */
    @TableField(update = "now()")
	private Date updateTime;
    /**
     * 修改人
     */
    private String updator;
    /**
     * 是否删除
     */
    private Integer isdelete;

}