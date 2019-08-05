package com.ucfo.youcai.adapter.user;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.ucfo.youcai.R;
import com.ucfo.youcai.entity.user.MineCouponsBean;
import com.ucfo.youcai.utils.baseadapter.BaseAdapter;

import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-7-29.  下午 2:32
 * Package: com.ucfo.youcai.adapter.user
 * FileName: MineCouponsAdapter
 * Description:TODO 我的优惠券
 */
public class MineCouponsAdapter extends BaseAdapter<String, MineCouponsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MineCouponsBean.DataBean> list;
    private int type;
    private final Drawable usableCoupon, unusedCoupon;

    public MineCouponsAdapter(Context context, ArrayList<MineCouponsBean.DataBean> list, int type) {
        this.context = context;
        this.list = list;
        this.type = type;
        usableCoupon = ContextCompat.getDrawable(context, R.mipmap.icon_usable_coupon);
        unusedCoupon = ContextCompat.getDrawable(context, R.mipmap.icon_unused_coupon);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        switch (type) {
            case 1:
                holder.mHolderLinear.setBackground(usableCoupon);
                holder.mCouponsRangeItem.setTextColor(ContextCompat.getColor(context, R.color.color_FD7D74));
                break;
            case 2:
            default:
                holder.mHolderLinear.setBackground(unusedCoupon);
                holder.mCouponsRangeItem.setTextColor(ContextCompat.getColor(context, R.color.color_DCDCDC));
                break;
        }

        MineCouponsBean.DataBean dataBean = list.get(position);
        String name = dataBean.getName();//优惠券名
        String couponPrice = dataBean.getCoupon_price();//优惠金额
        int range = dataBean.getRange();//适用范围1课程2直播3全部
        String endTime = dataBean.getEnd_time();//到期时间
        int isType = dataBean.getIs_type();//1:满减 2:打折

        if (!TextUtils.isEmpty(couponPrice)) {
            if (isType == 1) {//满减
                float v = Float.parseFloat(couponPrice);
                int b = Math.round(v);
                holder.mCouponsMoneyItem.setText(String.valueOf(b));
                holder.mRmbText.setVisibility(View.VISIBLE);
                holder.mDiscountText.setVisibility(View.GONE);
            } else {//打折劵
                holder.mCouponsMoneyItem.setText(String.valueOf(couponPrice));
                holder.mRmbText.setVisibility(View.GONE);
                holder.mDiscountText.setVisibility(View.VISIBLE);
            }
        } else {
            holder.mCouponsMoneyItem.setText(String.valueOf(0));
        }
        if (!TextUtils.isEmpty(name)) {
            holder.mCouponsDesItem.setText(name);
        }
        if (!TextUtils.isEmpty(endTime)) {
            holder.mCouponsValidityItem.setText(endTime);
        }
        switch (range) {
            case 1://课程
                holder.mCouponsRangeItem.setText(context.getResources().getString(R.string.coupon_course));
                break;
            case 2://直播
                holder.mCouponsRangeItem.setText(context.getResources().getString(R.string.coupon_live));
                break;
            case 3://全部
                holder.mCouponsRangeItem.setText(context.getResources().getString(R.string.coupon_all));
                break;
            default:
                break;
        }
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_mine_coupons, viewGroup, false);
        return new ViewHolder(view);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mCouponsMoneyItem;
        private TextView mCouponsDesItem;
        private TextView mCouponsValidityItem;
        private RoundTextView mCouponsRangeItem;
        private TextView mRmbText;
        private TextView mDiscountText;
        private LinearLayout mHolderLinear;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mCouponsMoneyItem = (TextView) itemView.findViewById(R.id.item_coupons_money);
            mCouponsDesItem = (TextView) itemView.findViewById(R.id.item_coupons_des);
            mCouponsValidityItem = (TextView) itemView.findViewById(R.id.item_coupons_validity);
            mCouponsRangeItem = (RoundTextView) itemView.findViewById(R.id.item_coupons_range);
            mRmbText = (TextView) itemView.findViewById(R.id.text_rmb);
            mDiscountText = (TextView) itemView.findViewById(R.id.text_discount);
            mHolderLinear = (LinearLayout) itemView.findViewById(R.id.linear_holder);
        }
    }
}
