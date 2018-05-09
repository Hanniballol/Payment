package com.msymobile.payment.sdk.payplatformsdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msymobile.payment.sdk.paylibs.PayCenter;
import com.msymobile.payment.sdk.paylibs.log.Logger;
import com.msymobile.payment.sdk.paylibs.model.ApiErrorEnum;
import com.msymobile.payment.sdk.paylibs.model.PaymentEntity;
import com.msymobile.payment.sdk.paylibs.net.rest.Response;
import com.msymobile.payment.sdk.paylibs.utils.PayCallBack;
import com.msymobile.payment.sdk.paylibs.utils.PaymentCallBack;
import com.msymobile.payment.sdk.paylibs.view.PaymentPopup;
import com.msymobile.payment.sdk.paylibs.view.PaymentResultPopup;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView mPopTv;
    private PaymentPopup mPaymentPopup;
    private GridView mGridView;
    private RelativeLayout mLoadView;
    private TextView mCustemTv;
    private PaymentResultPopup mPaymentResultPopup;
    private ArrayList<PaymentEntity> mPaymentEntities;

    String payId = "pay791598d42c4fe87a";
    String secret = "7136e99399d5e7cf658f92b5175a1e3241dcb2e45e3e9ce3efec4f95b6be1552";
    private EditText mAttachEt;
    private EditText mOrderNumEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        PayCenter.newBuilder(this)
                .paymentPopup(mPaymentPopup)
                .payId(payId)
                .paySecret(secret)
                .build();
        Logger.setDebug(true);
        Logger.setTag("hannibal");
    }

    private void initView() {
        mPopTv = (TextView) findViewById(R.id.pop_tv);
        mCustemTv = (TextView) findViewById(R.id.custom_tv);
        mGridView = (GridView) findViewById(R.id.grid_view);
        mLoadView = (RelativeLayout) findViewById(R.id.load_view);
        mAttachEt = (EditText) findViewById(R.id.attach_et);
        mOrderNumEt = (EditText) findViewById(R.id.order_num_et);
        mPaymentPopup = new PaymentPopup();
        mPaymentResultPopup = new PaymentResultPopup(this);
    }

    private void initData() {

        mPopTv.setOnClickListener(this);
        mCustemTv.setOnClickListener(this);
        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pop_tv:
                mPaymentPopup.show("测试商品2", 1, mOrderNumEt.getText().toString(), mAttachEt.getText().toString());
                break;
            case R.id.custom_tv:
                requestPayment();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        PaymentEntity paymentEntity = mPaymentEntities.get(i);
        requesting();
        switch (paymentEntity.getPaymentEnum()) {
            case PAYMENT_ENUM_WECHAT:
                PayCenter.weChatWebPay(mOrderNumEt.getText().toString(), mAttachEt.getText().toString(), 1, "测试商品1", new PayCallBack() {
                    @Override
                    public void success(int what, int code, Response response) {
                        if (code != ApiErrorEnum.SUCCESS.getValue()) {
                            Toast.makeText(MainActivity.this,ApiErrorEnum.getDescription(code),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failed(int what, Response response) {

                    }
                });
                requestFinish();
                break;
            case PAYMENT_ENUM_ALI:
                PayCenter.aliWebPay(mOrderNumEt.getText().toString(), mAttachEt.getText().toString(), 1, "测试商品1", new PayCallBack() {
                    @Override
                    public void success(int what, int code, Response response) {
                        if (code != ApiErrorEnum.SUCCESS.getValue()) {
                            Toast.makeText(MainActivity.this,ApiErrorEnum.getDescription(code),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failed(int what, Response response) {

                    }
                });
                requestFinish();
                break;
        }
    }

    private void requestPayment() {
        requesting();
        PayCenter.requestPayments(new PaymentCallBack() {
            @Override
            public void success(int what, ArrayList<PaymentEntity> payments, Response response) {
                requestFinish();
                mPaymentEntities = payments;
                mGridView.setAdapter(new PaymetsAdapter(payments, MainActivity.this));
            }

            @Override
            public void failed(int what, Response response) {
                requestFinish();
            }
        });
    }

    public void requesting() {
        mLoadView.setVisibility(View.VISIBLE);
        mGridView.setVisibility(View.GONE);
    }

    public void requestFinish() {
        mLoadView.setVisibility(View.GONE);
        mGridView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPaymentResultPopup.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPaymentResultPopup.dismiss();
        mPaymentPopup.dismiss();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }
}
