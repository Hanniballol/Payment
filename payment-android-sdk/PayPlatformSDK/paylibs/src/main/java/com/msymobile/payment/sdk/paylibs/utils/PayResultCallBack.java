package com.msymobile.payment.sdk.paylibs.utils;


import com.msymobile.payment.sdk.paylibs.net.rest.Response;

/**
 * autour: hannibal
 * date: 2017/10/11
 * e-mail:404769122@qq.com
 * description: 支付结果回调
 */
public abstract class PayResultCallBack {
    /**
     * @param what     本次请求的标识
     * @param flag     订单查询结果
     * @param response 响应对象
     */
    public abstract void success(int what, int flag, Response response);

    /**
     * @param what     本次请求的标识
     * @param response 响应对象
     */
    public abstract void failed(int what, Response response);
}
