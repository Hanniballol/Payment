package com.msymobile.payment.sdk.paylibs.utils;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * autour: hannibal
 * date: 2017/10/10
 * e-mail:404769122@qq.com
 * description: 签名加密
 */
public class HMACAuthentication {
    /**
     * HMAC sha1签名后返回base64编码字符串
     *
     * @param key  String 签名key字符串
     * @param data String 要签名的数据字符串
     * @return String 签名结果字符串
     */
    public static String sha1Base64(String key, String data) {
        String type = "HmacSHA1";
        SecretKeySpec secret = new SecretKeySpec(key.getBytes(), type);
        Mac mac = null;
        try {
            mac = Mac.getInstance(type);
            mac.init(secret);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] digest = mac.doFinal(data.getBytes());
        return Base64.encodeToString(digest, Base64.DEFAULT);
    }

    /**
     * HMAC sha256签名后返回base64编码字符串
     *
     * @param key  String 签名key字符串
     * @param data String 要签名的数据字符串
     * @return String 签名结果字符串
     */
    public static String sha256Base64(String key, String data) {
        String type = "HmacSHA256";
        SecretKeySpec secret = new SecretKeySpec(key.getBytes(), type);
        Mac mac = null;
        try {
            mac = Mac.getInstance(type);
            mac.init(secret);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] digest = mac.doFinal(data.getBytes());
        return Base64.encodeToString(digest, Base64.DEFAULT);
    }
}
