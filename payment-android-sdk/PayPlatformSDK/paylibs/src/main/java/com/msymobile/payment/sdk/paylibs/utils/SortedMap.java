package com.msymobile.payment.sdk.paylibs.utils;

import android.text.TextUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * autour: hannibal
 * date: 2017/10/10
 * e-mail:404769122@qq.com
 * description:
 */
public class SortedMap {

    public SortedMap() {
    }

    protected TreeMap<String, String> sortedDataMap = new TreeMap<>();

    protected void addKeyValuePair(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            sortedDataMap.put(key.toLowerCase().trim(), value.trim());
        }
    }

    protected TreeMap<String, String> getSortedMap() {
        return sortedDataMap;
    }

    public void addMap(Map<String, String> dataMap) {
        if (dataMap != null && !dataMap.isEmpty())
            sortedDataMap.putAll(dataMap);
    }

}
