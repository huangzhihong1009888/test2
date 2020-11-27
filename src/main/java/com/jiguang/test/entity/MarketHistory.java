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
 * 历史记录表(market_history)实体类
 *
 * @author kancy
 * @since 2020-11-27 17:17:15
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("market_history")
public class MarketHistory extends Model<MarketHistory> implements Serializable {
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
     * 类型，1：会员卡批次，2：优惠券批次，3：身份卡
     */
    private Integer type;
    /**
     * 对象ID
     */
    private String objId;
    /**
     * 操作人ID
     */
    private String operatorId;
    /**
     * 操作人姓名
     */
    private String operatorName;
    /**
     * 新记录
     */
    private String content;
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