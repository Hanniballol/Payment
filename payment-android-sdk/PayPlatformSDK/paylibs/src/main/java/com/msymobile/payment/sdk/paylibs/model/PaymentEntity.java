package com.msymobile.payment.sdk.paylibs.model;

import android.text.TextUtils;

import com.msymobile.payment.sdk.paylibs.R;

import static com.msymobile.payment.sdk.paylibs.model.PaymentEnum.PAYMENT_ENUM_ALI;
import static com.msymobile.payment.sdk.paylibs.model.PaymentEnum.PAYMENT_ENUM_WECHAT;


/**
 * autour: hannibal
 * date: 2017/9/17
 * e-mail:404769122@qq.com
 * description:商品
 */
public class PaymentEntity {

    private PaymentEnum mPaymentEnum;

    private String payment;

    private int icon;

    public PaymentEntity(String str) {
        handleContent(str);
    }

    private void handleContent(String str) {
        if(TextUtils.equals(str,PAYMENT_ENUM_WECHAT.getMsg())){
            mPaymentEnum = PAYMENT_ENUM_WECHAT;
            payment = "微信支付";
            icon = R.mipmap.weixinzhifu;
        }else if(TextUtils.equals(str,PAYMENT_ENUM_ALI.getMsg())){
            mPaymentEnum = PAYMENT_ENUM_ALI;
            payment = "支付宝";
            icon = R.mipmap.zhifubaozhifu;
        }
    }

    public PaymentEnum getPaymentEnum() {
        return mPaymentEnum;
    }

    public void setPaymentEnum(PaymentEnum paymentEnum) {
        mPaymentEnum = paymentEnum;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
