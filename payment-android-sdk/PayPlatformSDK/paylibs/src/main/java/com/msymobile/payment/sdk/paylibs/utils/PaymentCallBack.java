package com.msymobile.payment.sdk.paylibs.utils;


import com.msymobile.payment.sdk.paylibs.model.PaymentEntity;
import com.msymobile.payment.sdk.paylibs.net.rest.Response;

import java.util.ArrayList;

/**
 * autour: hannibal
 * date: 2017/9/27
 * e-mail:404769122@qq.com
 * description: 支付方式回调
 */
public abstract class PaymentCallBack {

    /**
     * @param what     本次请求的标识
     * @param payments 支付对象集合
     * @param response 响应对象
     */
    public abstract void success(int what, ArrayList<PaymentEntity> payments, Response response);

    /**
     * @param what     本次请求的标识
     * @param response 响应对象
     */
    public abstract void failed(int what, Response response);
}
