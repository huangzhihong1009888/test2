package com.jiguang.test.entity;

import java.util.Map;

public enum ResultCode  {

    SUCCESS(0, "成功"),
    FAILED(-1, "失败"),

    ERROR(3, "系统繁忙，请稍后重试"),
    ILLEGAL_OPERATION(4, "非法操作"),
    FORBIDDEN(5, "禁止"),
    EXISTS(6, "已经存在"),
    SERVICE_UNAVAILABLE(7, "服务不可调用"),
    OVER_LIMIT(8, "访问超限"),
    NOT_EXISTS(9, "不存在"),
    STATUS_INCORRECT(10, "状态不符"),
    MISSING_ARGUMENT(11, "参数缺失"),
    NEED_LOGIN(12, "需要登陆"),
    MORE_THAN_ONE(13, "数据异常"),
    UPDATE_OR_INSERT_ERROR(14, "数据更新失败"),
    INVALID_CODE(40029, "code已失效"),
    NON_BIND_PATIENT(30030, "请先绑定患者"),
    NON_EXIST_PATIENT(30031, "当前用户信息不一致，请联系客服核实档案信息后购买!"),
    TOKEN_FAILURE(901192, "token已失效!"),
    TOKEN_401(401, "需要登录"),
    TOKEN_ILLEGAL(901191, "token非法，无法解密!")

    ;

    private int index;
    private String desc;

    ResultCode(int index, String desc) {
        this.index = index;
        this.desc = desc;
    }


}
