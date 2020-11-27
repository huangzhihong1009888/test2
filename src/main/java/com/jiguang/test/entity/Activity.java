package com.jiguang.test.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 营销活动表
 * </p>
 *
 * @author author
 * @since 2020-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("activity")
@ApiModel(value="Activity对象", description="营销活动表")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "机构id")
    private Integer origId;

    @ApiModelProperty(value = "活动名称")
    private String name;

    @ApiModelProperty(value = "活动开始时间")
    private LocalDateTime startDate;

    @ApiModelProperty(value = "活动结束时间")
    private LocalDateTime endDate;

    @ApiModelProperty(value = "没人最多使用次数，为null 不限制")
    private Integer maxTimes;

    @ApiModelProperty(value = "折扣 同减免金额 二选一")
    private Integer discount;

    @ApiModelProperty(value = "减免金额")
    private BigDecimal saleMoney;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否身份卡同时使用")
    private Boolean canUseBenefit;

    @ApiModelProperty(value = "是否会员卡同时使用")
    private Boolean canUseCard;

    @ApiModelProperty(value = "适用终端：0 全部，1 患者小程序，2 PC门诊结算收银台")
    private String terminal;

    @ApiModelProperty(value = "状态，1：审核中，2：正常")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "创建人名")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改人")
    private String updator;

    @ApiModelProperty(value = "修改人名")
    private String updatorName;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "折扣规则")
    private String discountRule;

    @ApiModelProperty(value = "是否集团创建 默认 0不是，1是")
    private Integer isGroup;


}
