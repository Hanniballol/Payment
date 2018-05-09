package com.msymobile.payment.sdk.paylibs.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * autour: hannibal
 * date: 2017/9/18
 * e-mail:404769122@qq.com
 * description:
 */
public class PayWebViewClient extends WebViewClient{

    private final Activity mContext;

    public PayWebViewClient(Activity context) {
        this.mContext = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        return false;
    }
//    @Override
//    public void onPageFinished(WebView view, String url) {
//        super.onPageFinished(view, url);
//        view.loadUrl("javascript:window.local_obj.showSource('<head>'+"
//                + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
//    }
}
