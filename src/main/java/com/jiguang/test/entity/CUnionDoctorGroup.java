package com.jiguang.test.entity;

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
 * 医生患者分组表
 * </p>
 *
 * @author author
 * @since 2021-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CUnionDoctorGroup对象", description="医生患者分组表")
public class CUnionDoctorGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "组名称")
    private String name;

    @ApiModelProperty(value = "医生id")
    private String doctorId;

    @ApiModelProperty(value = "操作人")
    private String creatorId;

    @ApiModelProperty(value = "操作人名")
    private String creatorName;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createTime;


}
