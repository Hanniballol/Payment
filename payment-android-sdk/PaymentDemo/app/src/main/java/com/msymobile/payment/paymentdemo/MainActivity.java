package com.msymobile.payment.paymentdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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


/**
 * autour: hannibal
 * date: 2017/10/11
 * e-mail:404769122@qq.com
 * description: demo
 * <p>
 * 本sdk使用 aar实现，eclipse用户请单独联系
 * <p>
 * 请求接口分为:支付方式，支付，支付结果。根据需求提供不同的调用方式
 * <p>
 * 支付方式： 提供简易UI，开发者可选择自定义UI PayCenter.requestPayments()
 * 支付：调用后台返回的URL启动微信客户端，开发者可选择自定义逻辑 PayCenter.weChatWebPay(long amount, String productName)
 * PayCenter.weChatWebPay(long amount, String productName, final PayCallBack callBack)
 * 支付结果: 支付完毕需要询问支付结果，同样，开发者可以选择自定义逻辑 PayCenter.requestPayResult(String oid, final PayResultCallBack callBack)
 * <p>
 * 清单文件配置自行查看
 * <p>
 * gradle :
 * compile(name:'paylibs-release', ext:'aar')
 * <p>
 * repositories {
 * flatDir {
 * dirs 'libs'
 * }
 * }
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView mPopTv;
    private PaymentPopup mPaymentPopup;
    private GridView mGridView;
    private RelativeLayout mLoadView;
    private TextView mCustemTv;
    private PaymentResultPopup mPaymentResultPopup;
    private ArrayList<PaymentEntity> mPaymentEntities;

    //1. 使用本sdk需要配置payId、secret
    String payId = "pay8d2ca5f611ab6f32";
    String secret = "01ba0054b5ab3aa5f62ab8ea5aa80ea51e45401fb855b0282ae4fc570408f9f8";
    private EditText mOrderNumEt;
    private EditText mAttachEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

        //2. 配置支付中心，根据需要添加参数
        PayCenter.newBuilder(this)
                .paymentPopup(mPaymentPopup)
                .payId(payId)
                .paySecret(secret)
                .build();
        //3.开启日志
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
        //4. 显示支付方式，你可以选择自定义UI，或者用原生UI
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
        //5.进行支付
        PaymentEntity paymentEntity = mPaymentEntities.get(i);
        requesting();
        switch (paymentEntity.getPaymentEnum()) {
            case PAYMENT_ENUM_WECHAT:
                // 1：金额
                // "测试商品1"：商品名
                PayCenter.weChatWebPay(mOrderNumEt.getText().toString(), mAttachEt.getText().toString(), 1, "测试商品1", new PayCallBack() {
                    @Override
                    public void success(int what, int code,Response response) {
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
                    public void success(int what, int code,Response response) {
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

    //支付方式，自定义UI获取支付方式，提供微信，支付宝
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
        //6.处理支付结果
        mPaymentResultPopup.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //7.关闭弹窗
        mPaymentResultPopup.dismiss();
        mPaymentPopup.dismiss();
    }
}
