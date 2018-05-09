package com.msymobile.payment.paymentdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.msymobile.payment.sdk.paylibs.model.PaymentEntity;

import java.util.ArrayList;


/**
 * autour: hannibal
 * date: 2017/9/17
 * e-mail:404769122@qq.com
 * description:
 */
public class PaymetsAdapter extends BaseAdapter {
    ArrayList<PaymentEntity> mPaymentEntities;
    Context mContext;

    public PaymetsAdapter(ArrayList<PaymentEntity> list, Context context) {
        this.mPaymentEntities = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mPaymentEntities.size();
    }

    @Override
    public Object getItem(int i) {
        return mPaymentEntities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(null == view){
            view = View.inflate(mContext,R.layout.payment_gridlayout_item,null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        PaymentEntity paymentEntity = mPaymentEntities.get(i);
        viewHolder.mTextView.setText(paymentEntity.getPayment());
        viewHolder.mImageView.setBackgroundResource(paymentEntity.getIcon());
        return view;
    }

    private static class ViewHolder {
        ImageView mImageView;
        TextView mTextView;

        public ViewHolder(View itemView) {
            mImageView = (ImageView) itemView.findViewById(R.id.payment_icon);
            mTextView = (TextView) itemView.findViewById(R.id.payment_text);
        }
    }
}
