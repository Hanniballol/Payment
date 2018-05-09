package com.msymobile.payment.sdk.paylibs.utils;


import com.msymobile.payment.sdk.paylibs.net.rest.Response;

/**
 * autour: hannibal
 * date: 2017/9/19
 * e-mail:404769122@qq.com
 * description:请求回调
 */
public interface HttpListener<T> {

    void onSucceed(int what, Response<T> response);

    void onFailed(int what, Response<T> response);

}
