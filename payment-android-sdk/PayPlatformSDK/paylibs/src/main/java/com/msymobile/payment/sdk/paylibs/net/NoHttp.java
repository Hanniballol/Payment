/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.msymobile.payment.sdk.paylibs.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.msymobile.payment.sdk.paylibs.net.cache.CacheEntity;
import com.msymobile.payment.sdk.paylibs.net.rest.ByteArrayRequest;
import com.msymobile.payment.sdk.paylibs.net.rest.ImageRequest;
import com.msymobile.payment.sdk.paylibs.net.rest.JsonArrayRequest;
import com.msymobile.payment.sdk.paylibs.net.rest.JsonObjectRequest;
import com.msymobile.payment.sdk.paylibs.net.rest.ProtocolRequest;
import com.msymobile.payment.sdk.paylibs.net.rest.Request;
import com.msymobile.payment.sdk.paylibs.net.rest.RequestQueue;
import com.msymobile.payment.sdk.paylibs.net.rest.Response;
import com.msymobile.payment.sdk.paylibs.net.rest.StringRequest;
import com.msymobile.payment.sdk.paylibs.net.rest.SyncRequestExecutor;
import com.msymobile.payment.sdk.paylibs.net.tools.CacheStore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.CookieManager;
import java.net.CookieStore;

/**
 * NoHttp.
 *
 * Created in Jul 28, 2015 7:32:22 PM.
 * @author Yan Zhenjie.
 */
public class NoHttp {

    @SuppressLint("StaticFieldLeak")
    private static InitializationConfig sInitializeConfig;

    private NoHttp() {
    }

    /**
     * Initialize NoHttp, should invoke on {@link android.app.Application#onCreate()}.
     *
     * @param context {@link Context}.
     */
    public static void initialize(Context context) {
        sInitializeConfig = InitializationConfig.newBuilder(context)
                .build();
    }

    /**
     * Initialize NoHttp, should invoke on {@link android.app.Application#onCreate()}.
     *
     * @deprecated use {@link #initialize(InitializationConfig)} instead.
     */
    @Deprecated
    public static void initialize(Context context, Config config) {
        sInitializeConfig = InitializationConfig.newBuilder(context)
                .connectionTimeout(config.mConnectTimeout)
                .readTimeout(config.mReadTimeout)
                .cookieStore(config.mCookieStore)
                .cacheStore(config.mCacheStore)
                .networkExecutor(config.mNetworkExecutor)
                .build();
    }

    /**
     * Initialize NoHttp, should invoke on {@link android.app.Application#onCreate()}.
     */
    public static void initialize(InitializationConfig initializeConfig) {
        sInitializeConfig = initializeConfig;
    }

    /**
     * Test initialized.
     */
    private static void testInitialize() {
        if (sInitializeConfig == null)
            throw new ExceptionInInitializerError("Please invoke NoHttp.initialize(Application) on Application#onCreate()");
    }

    /**
     * Gets context of app.
     */
    public static Context getContext() {
        testInitialize();
        return sInitializeConfig.getContext();
    }

    /**
     * Get InitializationConfig.
     */
    public static InitializationConfig getInitializeConfig() {
        testInitialize();
        return sInitializeConfig;
    }


    /**
     * Gets connect timeout.
     *
     * @deprecated use {@link #getInitializeConfig()} instead.
     */
    @Deprecated
    public static int getConnectTimeout() {
        return sInitializeConfig.getConnectTimeout();
    }

    /**
     * Gets read timeout.
     *
     * @deprecated use {@link #getInitializeConfig()} instead.
     */
    @Deprecated
    public static int getReadTimeout() {
        return sInitializeConfig.getReadTimeout();
    }

    /**
     * Gets cookie manager.
     *
     * @deprecated use {@link #getInitializeConfig()} instead.
     */
    @Deprecated
    public static CookieManager getCookieManager() {
        return sInitializeConfig.getCookieManager();
    }

    /**
     * Gets cache store.
     *
     * @deprecated use {@link #getInitializeConfig()} instead.
     */
    @Deprecated
    public static CacheStore<CacheEntity> getCacheStore() {
        return sInitializeConfig.getCacheStore();
    }

    /**
     * Gets executor implement of http.
     *
     * @deprecated use {@link #getInitializeConfig()} instead.
     */
    @Deprecated
    public static NetworkExecutor getNetworkExecutor() {
        return sInitializeConfig.getNetworkExecutor();
    }

    /**
     * Create a queue of request, the default thread pool size is 3.
     *
     * @return returns the request queue, the queue is used to control the entry of the request.
     * @see #newRequestQueue(int)
     */
    public static RequestQueue newRequestQueue() {
        return newRequestQueue(3);
    }

    /**
     * Create a queue of request.
     *
     * @param threadPoolSize request the number of concurrent.
     * @return returns the request queue, the queue is used to control the entry of the request.
     * @see #newRequestQueue()
     */
    public static RequestQueue newRequestQueue(int threadPoolSize) {
        RequestQueue requestQueue = new RequestQueue(threadPoolSize);
        requestQueue.start();
        return requestQueue;
    }

    /**
     * Create a String type request, the request method is {@link RequestMethod#GET}.
     *
     * @param url such as: {@code http://www.nohttp.net}.
     * @return {@code Request<String>}.
     * @see #createStringRequest(String, RequestMethod)
     */
    public static Request<String> createStringRequest(String url) {
        return new StringRequest(url);
    }

    /**
     * Create a String type request, custom request method, method from {@link RequestMethod}.
     *
     * @param url           such as: {@code http://www.nohttp.net}.
     * @param requestMethod {@link RequestMethod}.
     * @return {@code Request<String>}.
     * @see #createStringRequest(String)
     */
    public static Request<String> createStringRequest(String url, RequestMethod requestMethod) {
        return new StringRequest(url, requestMethod);
    }

    /**
     * Create a JSONObject type request, the request method is {@link RequestMethod#GET}.
     *
     * @param url such as: {@code http://www.nohttp.net}.
     * @return {@code Request<JSONObject>}.
     * @see #createJsonObjectRequest(String, RequestMethod)
     */
    public static Request<JSONObject> createJsonObjectRequest(String url) {
        return new JsonObjectRequest(url);
    }

    /**
     * Create a JSONObject type request, custom request method, method from {@link RequestMethod}.
     *
     * @param url           such as: {@code http://www.nohttp.net}.
     * @param requestMethod {@link RequestMethod}.
     * @return {@code Request<JSONObject>}.
     * @see #createJsonObjectRequest(String)
     */
    public static Request<JSONObject> createJsonObjectRequest(String url, RequestMethod requestMethod) {
        return new JsonObjectRequest(url, requestMethod);
    }

    /**
     * Create a JSONArray type request, the request method is {@link RequestMethod#GET}.
     *
     * @param url such as: {@code http://www.nohttp.net}.
     * @return {@code Request<JSONArray>}.
     * @see #createJsonArrayRequest(String, RequestMethod)
     */
    public static Request<JSONArray> createJsonArrayRequest(String url) {
        return new JsonArrayRequest(url);
    }

    /**
     * Create a JSONArray type request, custom request method, method from {@link RequestMethod}.
     *
     * @param url           such as: {@code http://www.nohttp.net}.
     * @param requestMethod {@link RequestMethod}.
     * @return {@code Request<JSONArray>}.
     * @see #createJsonArrayRequest(String)
     */
    public static Request<JSONArray> createJsonArrayRequest(String url, RequestMethod requestMethod) {
        return new JsonArrayRequest(url, requestMethod);
    }

    /**
     * Create a Image type request, the request method is {@link RequestMethod#GET}.
     *
     * @param url such as: {@code http://www.nohttp.net}.
     * @return {@code Request<Bitmap>}.
     * @see #createImageRequest(String, RequestMethod)
     * @see #createImageRequest(String, RequestMethod, int, int, Bitmap.Config, ImageView.ScaleType)
     */
    public static Request<Bitmap> createImageRequest(String url) {
        return createImageRequest(url, RequestMethod.GET);
    }

    /**
     * Create a Image type request.
     *
     * @param url           such as: {@code http://www.nohttp.net}.
     * @param requestMethod {@link RequestMethod}.
     * @return {@code Request<Bitmap>}.
     * @see #createImageRequest(String)
     * @see #createImageRequest(String, RequestMethod, int, int, Bitmap.Config, ImageView.ScaleType)
     */
    public static Request<Bitmap> createImageRequest(String url, RequestMethod requestMethod) {
        return createImageRequest(url, requestMethod, 1000, 1000, Bitmap.Config.ARGB_8888, ImageView.ScaleType.CENTER_INSIDE);
    }

    /**
     * Create a Image type request.
     *
     * @param url           such as: {@code http://www.nohttp.net}.
     * @param requestMethod {@link RequestMethod}.
     * @param maxWidth      width.
     * @param maxHeight     height.
     * @param config        config.
     * @param scaleType     scaleType.
     * @return {@code Request<Bitmap>}.
     * @see #createImageRequest(String)
     * @see #createImageRequest(String, RequestMethod)
     */
    public static Request<Bitmap> createImageRequest(String url, RequestMethod requestMethod,
                                                     int maxWidth, int maxHeight,
                                                     Bitmap.Config config, ImageView.ScaleType scaleType) {
        return new ImageRequest(url, requestMethod, maxWidth, maxHeight, config, scaleType);
    }

    /**
     * Create a byte array request, the request method is {@link RequestMethod#GET}.
     *
     * @param url url.
     * @return {@code Request<byte[]>}.
     * @see #createByteArrayRequest(String, RequestMethod)
     */
    public static Request<byte[]> createByteArrayRequest(String url) {
        return new ByteArrayRequest(url);
    }

    /**
     * Create a byte array request.
     *
     * @param url    url.
     * @param method {@link RequestMethod}.
     * @return {@code Request<byte[]>}.
     * @see #createByteArrayRequest(String)
     */
    public static Request<byte[]> createByteArrayRequest(String url, RequestMethod method) {
        return new ByteArrayRequest(url, method);
    }

    /**
     * Initiate a synchronization request.
     *
     * @param request request object.
     * @param <T>     {@link T}.
     * @return {@link Response}.
     */
    public static <T> Response<T> startRequestSync(ProtocolRequest<?, T> request) {
        return SyncRequestExecutor.INSTANCE.execute(request);
    }




    /**
     * Default thread pool size for request queue.
     */
    private static RequestQueue sRequestQueueInstance;

    /**
     * Get default RequestQueue.
     *
     * @return {@link RequestQueue}.
     */
    public static RequestQueue getRequestQueueInstance() {
        if (sRequestQueueInstance == null)
            synchronized (NoHttp.class) {
                if (sRequestQueueInstance == null) {
                    sRequestQueueInstance = newRequestQueue();
                }
            }
        return sRequestQueueInstance;
    }

    /**
     * @deprecated use {@link InitializationConfig} instead.
     */
    @Deprecated
    public static final class Config {

        private int mConnectTimeout = 10 * 1000;
        private int mReadTimeout = 10 * 1000;

        private CookieStore mCookieStore;
        private CacheStore<CacheEntity> mCacheStore;

        private NetworkExecutor mNetworkExecutor;

        public Config() {
        }

        /**
         * Set default connect timeout.
         *
         * @param timeout ms.
         * @return {@link Config}.
         */
        public Config setConnectTimeout(int timeout) {
            mConnectTimeout = timeout;
            return this;
        }

        /**
         * Set default read timeout.
         *
         * @param timeout ms.
         * @return {@link Config}.
         */
        public Config setReadTimeout(int timeout) {
            mReadTimeout = timeout;
            return this;
        }

        /**
         * Sets cookie manager.
         *
         * @param cookieStore {@link CookieStore}.
         * @return {@link Config}.
         */
        public Config setCookieStore(CookieStore cookieStore) {
            this.mCookieStore = cookieStore;
            return this;
        }

        /**
         * Sets cache store.
         *
         * @param cacheStore {@link CacheStore}.
         * @return {@link Config}.
         */
        public Config setCacheStore(CacheStore<CacheEntity> cacheStore) {
            this.mCacheStore = cacheStore;
            return this;
        }

        /**
         * Set the Http request interface, realizes the Http request.
         *
         * @param executor {@link NetworkExecutor}.
         * @return {@link Config}.
         */
        public Config setNetworkExecutor(NetworkExecutor executor) {
            this.mNetworkExecutor = executor;
            return this;
        }
    }

}
