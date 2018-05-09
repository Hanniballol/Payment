package com.msymobile.payment.sdk.paylibs;

/**
 * autour: hannibal
 * date: 2017/9/18
 * e-mail:404769122@qq.com
 * description:
 */
public interface Constant {
    String BASE_URL = BuildConfig.API_HOST;
    String WECHAT_URL = BASE_URL + "/wechat/create/order";
    String ALI_URL = BASE_URL + "/alipay/create/order";
    String PAY_WEB_URL = BASE_URL + "/v1.0/trade/submit";
    String ALI_WEB_URL = BASE_URL + "/alipay/web/pay";
    String PAYMENT_URL = BASE_URL + "/v1.0/trade/way";
    String QUERY_URL = BASE_URL + "/v1.0/trade/query";

    String CONTENT_TYPE = "Content-Type";
    String TEXT_XML = "text/xml";
    String CONTENT_MD5 = "Content-MD5";
    String X_SPR_APP = "x-spr-app";
    String X_SPR_DATE = "x-spr-date";
    String AUTHORIZATION = "authorization";
    String DEVICE_ID = "x-device-id";
    String VERSION = "x-version";

    String AAR_VERSION = "1";

    int REQUEST_CODE = 10086;
    int RESULT_CODE = 10087;

}
