package com.jiguang.test.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author zhaoyue
 * @description:
 * @create 2020/7/16 1:42 下午
 **/
@Mapper(uses = EnumConver.class)
public interface DemoConvert {

    /**
     *
     */
    DemoConvert INSTANCE = Mappers.getMapper(DemoConvert.class);


    /**
     * 转换
     *
     * @return
     */
    @Mappings({
            @Mapping(source = "type", target = "type")})
    ApiDO convert(ApiDTO apiDTO);

    /**
     * 转换
     *
     * @return
     */
    @Mappings({
            @Mapping(source = "type", target = "type")})
    ApiDTO convert(ApiDO apiDO);



}
