package com.msymobile.payment.sdk.paylibs.utils;


import com.msymobile.payment.sdk.paylibs.net.NoHttp;
import com.msymobile.payment.sdk.paylibs.net.rest.OnResponseListener;
import com.msymobile.payment.sdk.paylibs.net.rest.Request;
import com.msymobile.payment.sdk.paylibs.net.rest.RequestQueue;

/**
 * autour: hannibal
 * date: 2017/9/19
 * e-mail:404769122@qq.com
 * description:请求线程队列管理
 */
 public class CallServer {

    private static CallServer instance;

    public static CallServer getInstance() {
        if (instance == null)
            synchronized (CallServer.class) {
                if (instance == null)
                    instance = new CallServer();
            }
        return instance;
    }

    private RequestQueue mRequestQueue;

    private CallServer() {
        mRequestQueue = NoHttp.newRequestQueue(3);
    }

    public <T> void request(int what, Request<T> request, OnResponseListener<T> listener) {
        mRequestQueue.add(what, request, listener);
    }

    public <T> void request(int what, Request<T> request, HttpListener<T> callback) {
        mRequestQueue.add(what, request, new HttpResponseListener<T>(request, callback));
    }

    public void cancelAll(){
        mRequestQueue.cancelAll();
    }
}
