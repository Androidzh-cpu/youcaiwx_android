package com.ucfo.youcaiwx.adapter.integral;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.integral.IntegralExchangeRecordBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Author: AND
 * Time: 2019-10-18.  下午 6:01
 * Package: com.ucfo.youcaiwx.adapter.integral
 * FileName: IntegralExchangeRecordAdapter
 * Description:TODO 积分兑换记录
 */
public class IntegralExchangeRecordAdapter extends BaseAdapter<IntegralExchangeRecordBean.DataBean, IntegralExchangeRecordAdapter.ViewHolder> {
    private List<IntegralExchangeRecordBean.DataBean> list;
    private Context context;

    public IntegralExchangeRecordAdapter(List<IntegralExchangeRecordBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void notifyChange(List<IntegralExchangeRecordBean.DataBean> dataBeanList) {
        this.list = dataBeanList;

        notifyDataSetChanged();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        IntegralExchangeRecordBean.DataBean bean = list.get(position);

        String type = bean.getType();
        String image = bean.getImage();
        String productId = bean.getId();
        if (TextUtils.equals(type, String.valueOf(1))) {
            //TODO 实体商品
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.icon_default)
                    .error(R.mipmap.image_loaderror)
                    .transform(new CenterCrop(), new RoundedCornersTransformation(DensityUtil.dp2px(5), 0))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            GlideUtils.load(context, image, holder.mImageIntegral, requestOptions);
            holder.mCouponLinear.setVisibility(View.INVISIBLE);
        } else if (TextUtils.equals(type, String.valueOf(2))) {
            //TODO 课程
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.icon_default)
                    .error(R.mipmap.image_loaderror)
                    .transform(new CenterCrop(), new RoundedCornersTransformation(DensityUtil.dp2px(3), 0))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            GlideUtils.load(context, image, holder.mImageIntegral, requestOptions);

            holder.mCouponLinear.setVisibility(View.INVISIBLE);
        } else if (TextUtils.equals(type, String.valueOf(3))) {
            //TODO 优惠券
            holder.mImageIntegral.setBackground(ContextCompat.getDrawable(context, R.mipmap.icon_integral_coupon));
            holder.mCouponLinear.setVisibility(View.VISIBLE);

            String isType = bean.getIs_type();
            String couponPrice = bean.getCoupon_price();
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

        }

        String uptime = bean.getUptime();
        String name = bean.getName();
        String detailed = bean.getDetailed();

        if (!TextUtils.isEmpty(name)) {
            holder.mTitleItem.setText(name);
        }
        if (!TextUtils.isEmpty(uptime)) {
            holder.mTimeItem.setText(uptime);
        }
        if (!TextUtils.isEmpty(detailed)) {
            holder.mIntegralItem.setText(context.getResources().getString(R.string.integral_holder, detailed));
        }

    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_integral_exchange, viewGroup, false);
        return new ViewHolder(view);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageIntegral;
        private TextView mRmbText;
        private TextView mCouponsMoneyItem;
        private TextView mDiscountText;
        private LinearLayout mCouponLinear;
        private TextView mTitleItem;
        private TextView mIntegralItem;
        private TextView mTimeItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mImageIntegral = (ImageView) itemView.findViewById(R.id.integral_image);
            mRmbText = (TextView) itemView.findViewById(R.id.text_rmb);
            mCouponsMoneyItem = (TextView) itemView.findViewById(R.id.item_coupons_money);
            mDiscountText = (TextView) itemView.findViewById(R.id.text_discount);
            mCouponLinear = (LinearLayout) itemView.findViewById(R.id.linear_coupon);
            mTitleItem = (TextView) itemView.findViewById(R.id.item_title);
            mIntegralItem = (TextView) itemView.findViewById(R.id.item_integral);
            mTimeItem = (TextView) itemView.findViewById(R.id.item_time);
        }
    }
}
