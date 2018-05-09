package com.msymobile.payment.sdk.paylibs.model;

/**
 * autour: hannibal
 * date: 2017/9/18
 * e-mail:404769122@qq.com
 * description:
 */
public enum PayStatusEnum {
    UMPAY(0), //未支付
    PAY(1), //已支付
    FAIL(2), //支付失败
    NET_ERROR(-2), //网络错误
    UNKNOWN(-1); //未知
    private final int paySign;

    PayStatusEnum(int paySign) {
        this.paySign = paySign;
    }

    public int getPaySign() {
        return paySign;
    }
}
