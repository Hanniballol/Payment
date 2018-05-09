package com.msymobile.payment.sdk.paylibs.utils;


import com.msymobile.payment.sdk.paylibs.net.rest.Response;

/**
 * autour: hannibal
 * date: 2017/10/11
 * e-mail:404769122@qq.com
 * description: 支付回调
 */
public abstract class PayCallBack {

    /**
     * @param what     本次请求的标识
     * @param code     服务器响应结果状态标识码
     * @param response 响应对象
     */
    public abstract void success(int what, int code, Response response);

    /**
     * @param what     本次请求的标识
     * @param response 响应对象
     */
    public abstract void failed(int what, Response response);
}
