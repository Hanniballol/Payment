package com.msymobile.payment.sdk.paylibs.view;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.msymobile.payment.sdk.paylibs.PayCenter;
import com.msymobile.payment.sdk.paylibs.R;
import com.msymobile.payment.sdk.paylibs.log.Logger;
import com.msymobile.payment.sdk.paylibs.model.PaymentEntity;
import com.msymobile.payment.sdk.paylibs.net.rest.Response;
import com.msymobile.payment.sdk.paylibs.utils.PaymentCallBack;

import java.util.ArrayList;

/**
 * autour: hannibal
 * date: 2017/9/17
 * e-mail:404769122@qq.com
 * description:
 */
public class PaymentPopup implements AdapterView.OnItemClickListener, View.OnClickListener {

    private PopupWindow mPopupWindow;
    private Activity mContext;
    private ArrayList<PaymentEntity> mPaymentEntities;
    private RelativeLayout mLoadView;
    private GridView mGridView;
    private String productName;
    private long amound;
    private String appOrderNum;
    private String attach;

    public String getAppOrderNum() {
        return appOrderNum;
    }

    public void setAppOrderNum(String appOrderNum) {
        this.appOrderNum = appOrderNum;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public long getAmound() {
        return amound;
    }

    public void setAmound(long amound) {
        this.amound = amound;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public PaymentPopup() {
        mPaymentEntities = new ArrayList<PaymentEntity>();
    }

    public void init() {
        mContext = (Activity) PayCenter.getContext();
        if (null == mContext) {
            throw new NullPointerException("you need init PayCenter first");
        }
        View contentView = View.inflate(mContext, R.layout.payment_popup, null);
        mGridView = (GridView) contentView.findViewById(R.id.grid_view);
        TextView cancelTv = (TextView) contentView.findViewById(R.id.cancel_tv);
        mLoadView = (RelativeLayout) contentView.findViewById(R.id.load_view);
        mGridView.setOnItemClickListener(this);
        cancelTv.setOnClickListener(this);

        mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        requestPayment();
    }

    public void requestPayment() {
        PayCenter.requestPayments(new PaymentCallBack() {
            @Override
            public void success(int what, ArrayList<PaymentEntity> payments, Response response) {
                requestFinish();
                mPaymentEntities = payments;
                mGridView.setAdapter(new PaymentAdapter(payments, mContext));
            }

            @Override
            public void failed(int what, Response response) {
                requestFinish();
            }
        });
    }

    public void dismiss() {
        if (null != mPopupWindow && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public void show(String productName, long amound, String appOrderNum, String attach) {
        if (amound < 0 || amound == 0) {
            Logger.e("请输入正确金额");
            return;
        }
        if (TextUtils.isEmpty(productName)) {
            Logger.e("商品名格式错误");
            return;
        }
        if (TextUtils.isEmpty(appOrderNum)) {
            Logger.e("app自定义订单号为空");
            return;
        }
        this.appOrderNum = appOrderNum;
        this.attach = attach;
        this.amound = amound;
        this.productName = productName;
        if (null != mPopupWindow && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(mContext.getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        PaymentEntity paymentEntity = mPaymentEntities.get(i);
        requesting();
        switch (paymentEntity.getPaymentEnum()) {
            case PAYMENT_ENUM_WECHAT:
                PayCenter.weChatWebPay(appOrderNum, attach, amound, productName);
                dismiss();
                break;
            case PAYMENT_ENUM_ALI:
                PayCenter.aliWebPay(appOrderNum, attach, amound, productName);
                dismiss();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}
