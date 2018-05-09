package com.msymobile.payment.sdk.paylibs.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * autour: hannibal
 * date: 2017/11/6
 * e-mail:404769122@qq.com
 * description:
 */
public enum ApiErrorEnum {
    SUCCESS(0, "success", "成功"),
    DATA_NOT_FOUND(1, "data not found", "记录未找到"),
    NOT_PAY(2, "order not pay", "订单未支付"),
    ILLEGAL_ARGUMENT(5, "illegal argument", "非法参数"),
    DATA_EXISTS(8, "data exists", "记录已存在"),
    SMS_CODE_ERROR(12, "SMS verification code error", "短信验证码错误"),
    CREATE_ORDER_ERROR(20, "create order error", "创建订单失败"),
    CREATE_WECHAT_ORDER_ERROR(21, "create wechat order error", "创建微信预订单失败"),
    GOODS_NOT_FOUND(22, "goods not found", "商品不存在"),
    ORDER_NOT_FOUND(23, "order not found", "订单不存在"),
    APPINFO_NOT_FOUND(24, "app info not found", "应用不存在"),
    GOODSINFO_NOT_FOUND(25, "goods info not found", "找不到商品列表"),
    GOODS_RECORD_NULL(27, "goods record is null", "购买的商品列表为空"),
    PAY_TYPE_NULL(28, "pay type is null", "支付方法列表为空"),
    IOS_INSERT_ORDER_FAIL(29, "ios insert orderInfo fails", "插入订单失败"),
    NOT_FOUND_CHANNEL(30, "channel not found", "找不到对应的支付方式"),
    APP_NOT_PAYMENT(31, "app no found payment channel", "该应用没有配置支付方式"),
    AUTHENTICATION_FAILURE(401, "authentication.failure", "身份验证失败"),
    PARAM_ERROR(-100, "param.error", "请求参数错误"),
    ACTION_ERROR(-101, "action.error", "操作失败"),
    SYSTEM_ERROR(-199, "system.error", "系统错误"),
    UNKNOWN(-1, "unknown", "未知");

    private Integer code;
    private String localCode;
    private String description;

    ApiErrorEnum(Integer code, String localCode, String description) {
        this.code = code;
        this.localCode = localCode;
        this.description = description;
    }

    public Integer getValue() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getLocalCode() {
        return localCode;
    }

    /**
     * 根据code值获取对应的枚举值
     *
     * @param code Integer code值
     * @return ClassEnum 枚举值，如果不存在返回 UNKNOWN
     */
    public static ApiErrorEnum valueOfCode(Integer code) {
        if (code != null) {
            for (ApiErrorEnum typeEnum : values()) {
                if (typeEnum.getValue().equals(code)) {
                    return typeEnum;
                }
            }
        }
        return UNKNOWN;
    }


    /**
     * 根据code 直接获取描述信息
     *
     * @param code Integer 编码
     * @return String 对应枚举的描述，如果不是有效枚举返回“未知”
     */
    public static String getDescription(Integer code) {
        return valueOfCode(code).getDescription();
    }

    /**
     * 获取枚举列表，将未知对象去掉，用于列表选项
     *
     * @return List<NurseryGradeEnum> 学校枚举列表
     */
    public static List<ApiErrorEnum> getEnumList() {
        List<ApiErrorEnum> enumList = new ArrayList<>();
        Collections.addAll(enumList, values());
        enumList.remove(UNKNOWN);
        return enumList;
    }

}
