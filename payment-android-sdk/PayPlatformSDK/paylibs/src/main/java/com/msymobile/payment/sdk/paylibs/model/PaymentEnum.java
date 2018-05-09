package com.msymobile.payment.sdk.paylibs.model;

/**
 * autour: hannibal
 * date: 2017/9/18
 * e-mail:404769122@qq.com
 * description: 支付方式
 */
public enum PaymentEnum {
    PAYMENT_ENUM_WECHAT(1001, "WechatH5"),
    PAYMENT_ENUM_ALI(1002, "AlipayH5"),
    PAYMENT_ENUM_UNKNOW(-1, "未知");

    private final int paySign;
    private final String msg;

    PaymentEnum(int paySign, String msg) {
        this.paySign = paySign;
        this.msg = msg;
    }

    public int getPaySign() {
        return paySign;
    }

    public String getMsg() {
        return msg;
    }
}
