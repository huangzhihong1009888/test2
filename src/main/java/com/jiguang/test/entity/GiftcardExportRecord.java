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
 * 优惠券导出记录表(giftcard_export_record)实体类
 *
 * @author kancy
 * @since 2020-11-27 17:13:38
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("giftcard_export_record")
public class GiftcardExportRecord extends Model<GiftcardExportRecord> implements Serializable {
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
     * 券批次id
     */
    private String batchId;
    /**
     * 张数
     */
    private Integer count;
    /**
     * 路径
     */
    private String url;
    /**
     * 优惠券号集合
     */
    private Object codes;
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