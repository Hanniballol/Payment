package com.msymobile.payment.sdk.paylibs.view;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.msymobile.payment.sdk.paylibs.PayCenter;
import com.msymobile.payment.sdk.paylibs.R;
import com.msymobile.payment.sdk.paylibs.log.Logger;
import com.msymobile.payment.sdk.paylibs.net.rest.Response;
import com.msymobile.payment.sdk.paylibs.utils.PayResultCallBack;

import static com.msymobile.payment.sdk.paylibs.Constant.REQUEST_CODE;
import static com.msymobile.payment.sdk.paylibs.Constant.RESULT_CODE;
import static com.msymobile.payment.sdk.paylibs.model.PayStatusEnum.PAY;


/**
 * autour: hannibal
 * date: 2017/10/10
 * e-mail:404769122@qq.com
 * description:
 */
public class PaymentResultPopup implements View.OnClickListener {
    private PopupWindow mPopupWindow;
    private Activity mContext;
    private String mOid;

    public PaymentResultPopup(Activity context) {
        this.mContext = context;
        init();
    }

    private void init() {
        if (null == mContext) {
            throw new NullPointerException("context can't be null");
        }
        View contentView = View.inflate(mContext, R.layout.payment_result_popup, null);
        TextView completeTv = (TextView) contentView.findViewById(R.id.complete_tv);
        TextView restartTv = (TextView) contentView.findViewById(R.id.restart_tv);

        mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);

        completeTv.setOnClickListener(this);
        restartTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.complete_tv) {
            requestPayResult();
        } else if (i == R.id.restart_tv) {
            requestPayResult();
        }
    }

    public void dismiss() {
        if (null != mPopupWindow && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public void requestPayResult() {
        PayCenter.requestPayResult(mOid, new PayResultCallBack() {
            @Override
            public void success(int what, int flag, Response response) {
                Logger.e(response.get().toString());
                if (flag != PAY.getPaySign()) {
                    Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }

            @Override
            public void failed(int what, Response response) {
                dismiss();
            }
        });
    }

    public void show() {
        if (null != mPopupWindow) {
            mPopupWindow.showAtLocation(mContext.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            show();
            mOid = data.getStringExtra("oid");
        }
    }
}
