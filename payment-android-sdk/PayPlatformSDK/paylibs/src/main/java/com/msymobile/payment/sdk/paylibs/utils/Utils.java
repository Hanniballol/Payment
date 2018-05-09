package com.msymobile.payment.sdk.paylibs.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.msymobile.payment.sdk.paylibs.net.NoHttp;
import com.msymobile.payment.sdk.paylibs.net.RequestMethod;
import com.msymobile.payment.sdk.paylibs.net.rest.Request;
import com.msymobile.payment.sdk.paylibs.net.rest.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.msymobile.payment.sdk.paylibs.Constant.AUTHORIZATION;
import static com.msymobile.payment.sdk.paylibs.Constant.BASE_URL;
import static com.msymobile.payment.sdk.paylibs.Constant.CONTENT_MD5;
import static com.msymobile.payment.sdk.paylibs.Constant.TEXT_XML;
import static com.msymobile.payment.sdk.paylibs.Constant.X_SPR_APP;
import static com.msymobile.payment.sdk.paylibs.Constant.X_SPR_DATE;

/**
 * autour: hannibal
 * date: 2017/9/19
 * e-mail:404769122@qq.com
 * description:工具
 */
public class Utils {

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }


    private static String getRandomStr() {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    private static String getTimeStamp() {
        return Long.toString(System.currentTimeMillis());
    }

    private static String getContentMD5() {
        return HeaderBizMsgCrypt.encodeByMD5(getRandomStr());
    }

    public static Request handlePostRequestHead(String key, Context context, HashMap<String, String> map, String url, String payId) {
        Request request = NoHttp.createStringRequest(url, RequestMethod.POST);
        String requestMethod = RequestMethod.POST.toString();
        String contentMd5 = Utils.getContentMD5();
        String contentType = TEXT_XML;
        String packageName = Utils.getPackageName(context);
        String timeStamp = Utils.getTimeStamp();
        StringBuilder authorization = new StringBuilder();

        JSONObject object = new JSONObject();
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                object.put(entry.getKey(), entry.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SortedMap params = new SortedMap();
        params.addMap(map);

        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException("url can't be null");
        }

        String substring = url.substring(BASE_URL.length(), url.length());
        StringBuilder urlBuilder = new StringBuilder(substring);
//        urlBuilder.append("?");
//        boolean isMoreThanOne = false;
//        for (Map.Entry<String, String> entry : params.getSortedMap().entrySet()) {
//            if (isMoreThanOne) {
//                urlBuilder.append("&");
//            }
//            urlBuilder.append(entry.getKey()).append("=").append(entry.getValue());
//            if (!isMoreThanOne) {
//                isMoreThanOne = true;
//            }
//
//        }

        SortedMap xSpr = new SortedMap();
        xSpr.addKeyValuePair(X_SPR_APP, packageName);
        xSpr.addKeyValuePair(X_SPR_DATE, timeStamp);

        authorization.append(requestMethod).append("\n");
        authorization.append(contentMd5).append("\n");
        authorization.append(contentType).append("\n");

        for (Map.Entry<String, String> entry : xSpr.getSortedMap().entrySet()) {
            authorization.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        authorization.append(urlBuilder);
        String msg = HMACAuthentication.sha256Base64(key, authorization.toString());

        request.setDefineRequestBody(object.toString(), contentType);
        request.addHeader(X_SPR_DATE, timeStamp);
        request.addHeader(X_SPR_APP, packageName);
        request.addHeader(CONTENT_MD5, contentMd5);
        request.addHeader(AUTHORIZATION, "T " + payId + ":" + msg);

        return request;
    }

    public static Request handleGetRequestHead(String key, Context context, String url, String payId) {
        Request request = new StringRequest(url);
        String requestMethod = RequestMethod.GET.toString();
        String contentMd5 = Utils.getContentMD5();
        String contentType = TEXT_XML;
        String packageName = Utils.getPackageName(context);
        String timeStamp = Utils.getTimeStamp();
        StringBuilder authorization = new StringBuilder();

        SortedMap params = new SortedMap();

        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException("url can't be null");
        }
        String substring = url.substring(BASE_URL.length(), url.length());
        StringBuilder urlBuilder = new StringBuilder(substring);
        boolean isMoreThanOne = false;
        for (Map.Entry<String, String> entry : params.getSortedMap().entrySet()) {
            if (isMoreThanOne) {
                urlBuilder.append("&");
            }
            urlBuilder.append(entry.getKey()).append("=").append(entry.getValue());
            if (!isMoreThanOne) {
                isMoreThanOne = true;
            }
        }

        SortedMap xSpr = new SortedMap();
        xSpr.addKeyValuePair(X_SPR_APP, packageName);
        xSpr.addKeyValuePair(X_SPR_DATE, timeStamp);

        authorization.append(requestMethod).append("\n");
        authorization.append(contentMd5).append("\n");
        authorization.append(contentType).append("\n");

        for (Map.Entry<String, String> entry : xSpr.getSortedMap().entrySet()) {
            authorization.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        authorization.append(urlBuilder);
        String msg = HMACAuthentication.sha256Base64(key, authorization.toString());

        request.addHeader(X_SPR_DATE, timeStamp);
        request.addHeader(X_SPR_APP, packageName);
        request.addHeader(CONTENT_MD5, contentMd5);
        request.addHeader(AUTHORIZATION, "T " + payId + ":" + msg);

        return request;
    }
}
