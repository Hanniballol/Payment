package com.msymobile.payment.sdk.paylibs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.msymobile.payment.sdk.paylibs.log.Logger;
import com.msymobile.payment.sdk.paylibs.model.ApiErrorEnum;
import com.msymobile.payment.sdk.paylibs.model.PaymentEntity;
import com.msymobile.payment.sdk.paylibs.net.InitializationConfig;
import com.msymobile.payment.sdk.paylibs.net.NoHttp;
import com.msymobile.payment.sdk.paylibs.net.URLConnectionNetworkExecutor;
import com.msymobile.payment.sdk.paylibs.net.cache.DBCacheStore;
import com.msymobile.payment.sdk.paylibs.net.cookie.DBCookieStore;
import com.msymobile.payment.sdk.paylibs.net.rest.Request;
import com.msymobile.payment.sdk.paylibs.net.rest.Response;
import com.msymobile.payment.sdk.paylibs.utils.CallServer;
import com.msymobile.payment.sdk.paylibs.utils.DeviceUuidFactory;
import com.msymobile.payment.sdk.paylibs.utils.HttpListener;
import com.msymobile.payment.sdk.paylibs.utils.PayCallBack;
import com.msymobile.payment.sdk.paylibs.utils.PayResultCallBack;
import com.msymobile.payment.sdk.paylibs.utils.PaymentCallBack;
import com.msymobile.payment.sdk.paylibs.utils.Utils;
import com.msymobile.payment.sdk.paylibs.view.PaymentPopup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.msymobile.payment.sdk.paylibs.Constant.AAR_VERSION;
import static com.msymobile.payment.sdk.paylibs.Constant.CONTENT_TYPE;
import static com.msymobile.payment.sdk.paylibs.Constant.DEVICE_ID;
import static com.msymobile.payment.sdk.paylibs.Constant.PAYMENT_URL;
import static com.msymobile.payment.sdk.paylibs.Constant.QUERY_URL;
import static com.msymobile.payment.sdk.paylibs.Constant.REQUEST_CODE;
import static com.msymobile.payment.sdk.paylibs.Constant.TEXT_XML;
import static com.msymobile.payment.sdk.paylibs.Constant.VERSION;
import static com.msymobile.payment.sdk.paylibs.Constant.PAY_WEB_URL;
import static com.msymobile.payment.sdk.paylibs.model.PaymentEnum.PAYMENT_ENUM_ALI;
import static com.msymobile.payment.sdk.paylibs.model.PaymentEnum.PAYMENT_ENUM_WECHAT;


/**
 * autour: hannibal
 * date: 2017/9/18
 * e-mail:404769122@qq.com
 * description: 支付中心
 */
public class PayCenter {

    private static Activity sContext;
    private static PaymentPopup sPaymentPopup;
    private static String sWechatId;
    private static String sPayId;
    private static String sPaySecret;

    public static Context getContext() {
        return sContext;
    }

    public static Builder newBuilder(Activity context) {
        return new Builder(context);
    }

    private PayCenter(Builder builder) {

        sContext = builder.mContext;
        sWechatId = builder.mWechatId;
        sPaymentPopup = builder.mPaymentPopup;
        sPayId = builder.mPayId;
        sPaySecret = builder.mPaySecret;

        if (null == sContext)
            throw new NullPointerException("context can't be null");

        if (null == sPayId)
            throw new NullPointerException("payId can't be null");

        if (null == sPaySecret)
            throw new NullPointerException("paySecret can't be null");

        initNoHttp(sContext);
        initPaymentPopup();
    }


    private static void initNoHttp(Context context) {
        NoHttp.initialize(InitializationConfig.newBuilder(context)
                .connectionTimeout(10 * 1000)
                .readTimeout(10 * 1000)
                .cacheStore(new DBCacheStore(context).setEnable(false))
                .cookieStore(new DBCookieStore(context).setEnable(false))
                .networkExecutor(new URLConnectionNetworkExecutor())
                .addHeader(CONTENT_TYPE, TEXT_XML)
                .addHeader(VERSION, AAR_VERSION)
                .addHeader(DEVICE_ID, String.valueOf(new DeviceUuidFactory(sContext).getDeviceUuid()))
                .build()
        );
    }

    private void initPaymentPopup() {
        if (null == sPaymentPopup)
            throw new NullPointerException("you need configuration PaymentPopup");

        sPaymentPopup.init();
    }

    public final static class Builder {
        private Activity mContext;
        private PaymentPopup mPaymentPopup;
        private String mWechatId;
        private String mPayId;
        private String mPaySecret;

        private Builder(Activity context) {
            this.mContext = context;
        }

        public Builder wechatId(String wechatId) {
            this.mWechatId = wechatId;
            return this;
        }

        public Builder paymentPopup(PaymentPopup paymentPopup) {
            this.mPaymentPopup = paymentPopup;
            return this;
        }

        public Builder payId(String payId) {
            this.mPayId = payId;
            return this;
        }

        public Builder paySecret(String paySecret) {
            this.mPaySecret = paySecret;
            return this;
        }

        public PayCenter build() {
            return new PayCenter(this);
        }
    }

    public static void weChatWebPay(String appOrderNum, String attach, long amount, String productName, PayCallBack callBack) {
        pay(appOrderNum, attach, PAYMENT_ENUM_WECHAT.getMsg(), amount, productName, callBack);
    }

    public static void weChatWebPay(String appOrderNum, long amount, String productName, PayCallBack callBack) {
        weChatWebPay(appOrderNum, null, amount, productName, callBack);
    }

    public static void weChatWebPay(String appOrderNum, String attach, long amount, String productName) {
        weChatWebPay(appOrderNum, attach, amount, productName, null);
    }

    public static void weChatWebPay(String appOrderNum, long amount, String productName) {
        weChatWebPay(appOrderNum, null, amount, productName, null);
    }

    public static void aliWebPay(String appOrderNum, String attach, long amount, String productName, PayCallBack callBack) {
        pay(appOrderNum, attach, PAYMENT_ENUM_ALI.getMsg(), amount, productName, callBack);
    }

    public static void aliWebPay(String appOrderNum, long amount, String productName, PayCallBack callBack) {
        pay(appOrderNum, null, PAYMENT_ENUM_ALI.getMsg(), amount, productName, callBack);
    }

    public static void aliWebPay(String appOrderNum, String attach, long amount, String productName) {
        aliWebPay(appOrderNum, attach, amount, productName, null);
    }

    public static void aliWebPay(String appOrderNum, long amount, String productName) {
        aliWebPay(appOrderNum, null, amount, productName, null);
    }

    private static void pay(String appOrderNum, String payment, long amount, String productName, final PayCallBack callBack) {
        pay(appOrderNum, null, payment, amount, productName, callBack);
    }

    /**
     * 支付
     *
     * @param appOrderNum 自定义订单号
     * @param attach      拓展信息
     * @param payment     支付方式
     * @param amount      支付金额
     * @param productName 商品名称
     * @param callBack    回调
     */
    private static void pay(String appOrderNum, String attach, String payment, long amount, String productName, final PayCallBack callBack) {
        if (amount < 0 || amount == 0) {
            Logger.e("金额数格式错误!");
            return;
        }
        if (TextUtils.isEmpty(productName)) {
            Logger.e("商品名不能为空!");
            return;
        }
        if (TextUtils.isEmpty(appOrderNum)) {
            Logger.e("app自定义订单号不能为空!");
            return;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("paymentType", payment);
        map.put("amount", amount + "");
        map.put("productName", productName);
        map.put("appOrderNum", appOrderNum);
        if (!TextUtils.isEmpty(attach)) {
            map.put("attach", attach);
        }
        Request requestHead = Utils.handlePostRequestHead(sPaySecret, sContext, map, PAY_WEB_URL, sPayId);
        CallServer.getInstance().request(1, requestHead, new HttpListener() {
            @Override
            public void onSucceed(int what, Response response) {
                if (null != sPaymentPopup) {
                    sPaymentPopup.requestFinish();
                }
                Logger.e(response);
                if (response.responseCode() == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.get().toString());
                        JSONObject result = jsonObject.optJSONObject("result");
                        int code = jsonObject.getInt("code");
                        if (code == ApiErrorEnum.SUCCESS.getValue()) {

                            Intent intent = new Intent(sContext, H5PayCenterActivity.class);
                            intent.putExtra("url", result.optString("url"));
                            intent.putExtra("oid", result.optString("oid"));
                            intent.putExtra("referer",result.optString("referer"));
                            sContext.startActivityForResult(intent, REQUEST_CODE);
                        }
                        if (null != callBack) {
                            callBack.success(what, code, response);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (null != callBack) {
                        callBack.failed(what, response);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                if (null != callBack) {
                    callBack.failed(what, response);
                }
                if (null != sPaymentPopup) {
                    sPaymentPopup.requestFinish();
                }
            }
        });
    }

    /**
     * 支付方式
     *
     * @param paymentCallBack 回调
     */
    public static void requestPayments(final PaymentCallBack paymentCallBack) {
        final ArrayList<PaymentEntity> list = new ArrayList<PaymentEntity>();
        Request request = Utils.handleGetRequestHead(sPaySecret, sContext, PAYMENT_URL, sPayId);
        CallServer.getInstance().request(2, request, new HttpListener() {
            @Override
            public void onSucceed(int what, Response response) {
                Logger.e(response.get().toString());
                if (null != sPaymentPopup) {
                    sPaymentPopup.requestFinish();
                }
                if (response.responseCode() == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.get().toString());
                        int code = jsonObject.getInt("code");
                        if (code == ApiErrorEnum.SUCCESS.getValue()) {

                            JSONArray array = jsonObject.getJSONArray("result");
                            for (int i = 0; i < array.length(); i++) {
                                list.add(new PaymentEntity(array.getString(i)));
                            }
                            paymentCallBack.success(what, list, response);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    paymentCallBack.failed(what, response);
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                if (null != sPaymentPopup) {
                    sPaymentPopup.requestFinish();
                }
                paymentCallBack.failed(what, response);
            }
        });
    }

    /**
     * 支付结果回调
     *
     * @param oid      订单号
     * @param callBack 回调
     */
    public static void requestPayResult(String oid, final PayResultCallBack callBack) {
        if (TextUtils.isEmpty(oid)) {
            Logger.e("订单号格式错误!");
            return;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("oid", oid.trim());
        Request requestHead = Utils.handlePostRequestHead(sPaySecret, sContext, map, QUERY_URL, sPayId);
        CallServer.getInstance().request(3, requestHead, new HttpListener() {
            @Override
            public void onSucceed(int what, Response response) {
                if (response.responseCode() == 200) {
                    try {
                        Logger.e(response);
                        JSONObject jsonObject = new JSONObject(response.get().toString());
                        int code = jsonObject.getInt("code");
                        if (code == ApiErrorEnum.SUCCESS.getValue()) {
                            int result = jsonObject.optInt("result");
                            callBack.success(3, result, response);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    callBack.failed(what, response);
                }
            }

            @Override
            public void onFailed(int what, Response response) {
                callBack.failed(what, response);
            }
        });
    }
}
