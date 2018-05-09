package com.msymobile.payment.sdk.paylibs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.msymobile.payment.sdk.paylibs.view.PayWebViewClient;

import java.util.HashMap;
import java.util.Map;

import static com.msymobile.payment.sdk.paylibs.Constant.RESULT_CODE;

/**
 * autour: hannibal
 * date: 2017/9/26
 * e-mail:404769122@qq.com
 * description:
 */
public class H5PayCenterActivity extends Activity {
    private WebView mWebView;
    private boolean isBack;
    private String mOid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        mOid = intent.getStringExtra("oid");
        try {
            mWebView = new WebView(this);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setSupportZoom(true);
            mWebView.setWebViewClient(new PayWebViewClient(this));
            Map<String, String> extraHeaders = new HashMap<String, String>();
            extraHeaders.put("Referer", intent.getStringExtra("referer"));
//            mWebView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
            mWebView.loadUrl(url, extraHeaders);
        } catch (Throwable e) {
            finish();
        }
    }

//    final class InJavaScriptLocalObj {
//        @JavascriptInterface
//        public void showSource(String html) {
//            System.out.println("====>html="+html);
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isBack) {
            isBack = true;
        } else {
            Intent intent = new Intent();
            intent.putExtra("oid", mOid);
            setResult(RESULT_CODE, intent);
            isBack = false;
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
