package com.msymobile.payment.sdk.paylibs.utils;


import com.msymobile.payment.sdk.paylibs.log.Logger;
import com.msymobile.payment.sdk.paylibs.net.error.NetworkError;
import com.msymobile.payment.sdk.paylibs.net.error.NotFoundCacheError;
import com.msymobile.payment.sdk.paylibs.net.error.TimeoutError;
import com.msymobile.payment.sdk.paylibs.net.error.URLError;
import com.msymobile.payment.sdk.paylibs.net.error.UnKnownHostError;
import com.msymobile.payment.sdk.paylibs.net.rest.OnResponseListener;
import com.msymobile.payment.sdk.paylibs.net.rest.Request;
import com.msymobile.payment.sdk.paylibs.net.rest.Response;

/**
 * autour: hannibal
 * date: 2017/9/19
 * e-mail:404769122@qq.com
 * description:请求回调
 */
public class HttpResponseListener<T> implements OnResponseListener<T> {

    /**
     * Request.
     */
    private Request<?> mRequest;
    /**
     * 结果回调.
     */
    private HttpListener<T> callback;

    /**
     * @param request      请求对象.
     * @param httpCallback 回调对象.
     */
    public HttpResponseListener(Request<?> request, HttpListener<T> httpCallback) {
        this.mRequest = request;
        this.callback = httpCallback;
    }

    /**
     * 开始请求, 这里显示一个dialog.
     */
    @Override
    public void onStart(int what) {

    }

    /**
     * 结束请求, 这里关闭dialog.
     */
    @Override
    public void onFinish(int what) {

    }

    /**
     * 成功回调.
     */
    @Override
    public void onSucceed(int what, Response<T> response) {
        if (callback != null) {
            callback.onSucceed(what, response);
        }
    }

    /**
     * 失败回调.
     */
    @Override
    public void onFailed(int what, Response<T> response) {
        Exception exception = response.getException();
        if (exception instanceof NetworkError) {
            Logger.e("网络不佳");
        } else if (exception instanceof TimeoutError) {
            Logger.e("请求超时");
        } else if (exception instanceof UnKnownHostError) {
            Logger.e("找不到服务器");
        } else if (exception instanceof URLError) {
            Logger.e("错误的请求");
        } else if (exception instanceof NotFoundCacheError) {
            //未做缓存处理
        } else {
            Logger.e("未知错误");
        }

        Logger.e("error : " + exception.getMessage());
        if (callback != null)
            callback.onFailed(what, response);
    }
}
