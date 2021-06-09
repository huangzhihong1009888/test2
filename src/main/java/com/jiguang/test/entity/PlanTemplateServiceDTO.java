package com.jiguang.test.entity;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 计划模板收费项DTO
 * </p>
 *
 * @author huangzhihong
 * @since 2021-01-21
 */
@Data
@ApiModel(value="计划模板收费项DTO", description="计划模板收费项DTO")
public class PlanTemplateServiceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收费项id")
    private String id;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "机构id")
    private String orgId;

    @ApiModelProperty(value = "计划id")
    private String planId;

    @ApiModelProperty(value = "阶段id")
    private String phaseId;

    @ApiModelProperty(value = "收费项id")
    private String itemId;

    @ApiModelProperty(value = "收费项id")
    private String itemName;

    @ApiModelProperty(value = "收费项类型")
    private String itemType;

    @ApiModelProperty(value = "是否停用 0 否 1 是")
    private Integer isStop;

    @ApiModelProperty(value = "每次数量")
    private BigDecimal onceNum;

    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    @ApiModelProperty(value = "销售单价")
    private BigDecimal priceSell;


    @ApiModelProperty(value = "总数量")
    private BigDecimal totalCount;

    @ApiModelProperty(value = "总原金额")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "销售总金额")
    private BigDecimal totalPriceSell;

    @ApiModelProperty(value = "是否组套套餐 0否 1是")
    private Integer isSet;

    @ApiModelProperty(value = "开始天数")
    private Integer startDay;

    @ApiModelProperty(value = "循环标识 0 单次 1 多次")
    private Boolean cycleFlag;

    @ApiModelProperty(value = "收费项类型 1 普通收费项 2 按次收费项 3 可选收费项")
    private Integer type;

    @ApiModelProperty(value = "循环间隔天数")
    private Integer cycleDay;

    @ApiModelProperty(value = "循环次数")
    private Integer cycleNum;


    public static void main(String[] args) {
        List<PlanTemplateServiceDTO> service = new ArrayList<>();
        String PUSH_MESSAGE_URL = "everjiankangshceme://cn.everjiankang.sysdk/notify_detail?title=%s&content=%s&router=%s";
        final String format = String.format(PUSH_MESSAGE_URL, "req.getTitle()", "11111", "/ihc/taskdetail?isFeedBackTask=0&taskId=0f16dba2193143629c7e4695f127034f");
        System.out.println(format);

    }

    public static List<PlanTemplateServiceDTO> mergeCommonService(Boolean merge, List<PlanTemplateServiceDTO> dtoList) {
        if(!merge||CollectionUtils.isEmpty(dtoList)){
            return dtoList;
        }
        List<PlanTemplateServiceDTO> commonService = dtoList.stream().filter(a -> a.getType() ==1).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(commonService)){
            return dtoList;
        }
        List<PlanTemplateServiceDTO> otherService = dtoList.stream().filter(a -> a.getType() !=1).collect(Collectors.toList());

        List<PlanTemplateServiceDTO> mergeService = new ArrayList<>();
        Map<String, List<PlanTemplateServiceDTO>> map = commonService.stream().collect(Collectors.groupingBy(a -> a.getType() + "_" + a.getItemId()));
        map.forEach((key,value) ->{
            if(CollectionUtils.isNotEmpty(value)){
                PlanTemplateServiceDTO serviceDTO = Convert.convert(PlanTemplateServiceDTO.class,value.get(0));
                BigDecimal price = value.stream().map(PlanTemplateServiceDTO::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal priceSell = value.stream().map(PlanTemplateServiceDTO::getPriceSell).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal totalCount = value.stream().map(PlanTemplateServiceDTO::getTotalCount).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal totalPrice = value.stream().map(PlanTemplateServiceDTO::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal totalPriceSell = value.stream().map(PlanTemplateServiceDTO::getTotalPriceSell).reduce(BigDecimal.ZERO, BigDecimal::add);
                serviceDTO.setPrice(price);
                serviceDTO.setPriceSell(priceSell);
                serviceDTO.setTotalCount(totalCount);
                serviceDTO.setTotalPrice(totalPrice);
                serviceDTO.setTotalPriceSell(totalPriceSell);
                mergeService.add(serviceDTO);
            }

        });
        mergeService.addAll(otherService);
        return mergeService;
    }

}
