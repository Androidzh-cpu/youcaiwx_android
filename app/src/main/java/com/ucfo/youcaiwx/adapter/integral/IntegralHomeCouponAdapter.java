package com.ucfo.youcaiwx.adapter.integral;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.integral.IntegralShopHomeBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-10-14.  下午 3:50
 * Package: com.ucfo.youcaiwx.adapter.integral
 * FileName: IntegralHomeCouponAdapter
 * Description:TODO 积分商城首页
 */
public class IntegralHomeCouponAdapter extends BaseAdapter<IntegralShopHomeBean.DataBean.CouponBean, IntegralHomeCouponAdapter.ViewHolder> {
    private Context context;
    private List<IntegralShopHomeBean.DataBean.CouponBean> list;

    public IntegralHomeCouponAdapter(Context context, List<IntegralShopHomeBean.DataBean.CouponBean> list) {
        this.context = context;
        this.list = list;
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        IntegralShopHomeBean.DataBean.CouponBean couponBean = list.get(position);
        String name = couponBean.getName();//优惠券名
        String couponPrice = couponBean.getCoupon_price();//优惠金额
        String range = couponBean.getRange();//适用范围1课程2直播3全部
        String endTime = couponBean.getEnd_time();//到期时间
        String isType = couponBean.getIs_type();//1:满减 2:打折
        String integralPrice = couponBean.getIntegral_price();

        if (!TextUtils.isEmpty(couponPrice)) {
            if (TextUtils.equals(isType, String.valueOf(1))) {//满减
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
            holder.mCouponsValidityItem.setText(context.getResources().getString(R.string.coupon_ValidityEndTime, endTime));
        }
        if (!TextUtils.isEmpty(range)) {
            int parseInt = Integer.parseInt(range);
            switch (parseInt) {
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
        if (!TextUtils.isEmpty(integralPrice)) {
            holder.mIntegral.setText(context.getResources().getString(R.string.integral_holder, integralPrice));
        }

    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_integral_coupon, viewGroup, false);
        return new ViewHolder(view);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mCouponsMoneyItem;
        private TextView mCouponsDesItem;
        private TextView mCouponsValidityItem;
        private RoundTextView mCouponsRangeItem;
        private TextView mRmbText;
        private TextView mDiscountText, mIntegral;
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
            mIntegral = (TextView) itemView.findViewById(R.id.item_integral);
        }
    }
}
