package com.ucfo.youcaiwx.adapter.user;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.user.MineOrderListBean;
import com.ucfo.youcaiwx.module.user.activity.MineOrderFormActivity;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.widget.customview.NiceImageView;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-6-21.  下午 2:39
 * FileName: MineOrderFormListAdapter
 * Description:TODO 我的订单适配器
 */
public class MineOrderFormListAdapter extends BaseAdapter<MineOrderListBean.DataBean, MineOrderFormListAdapter.ViewHolder> {
    private MineOrderFormActivity context;
    private List<MineOrderListBean.DataBean> list;
    private final String RMB;
    private IOrderCallback callback;

    public MineOrderFormListAdapter(MineOrderFormActivity context, List<MineOrderListBean.DataBean> list) {
        this.context = context;
        this.list = list;
        RMB = context.getResources().getString(R.string.RMB);
    }

    public void setOthersClick(IOrderCallback iOrderCallback) {
        this.callback = iOrderCallback;
    }

    public void notifyChange(List<MineOrderListBean.DataBean> dataBeanList) {
        this.list = dataBeanList;
        notifyDataSetChanged();
    }

    public interface IOrderCallback {

        void clickPay(int position);

        void clickCancel(int position);

        void clickConnect();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        MineOrderListBean.DataBean bean = list.get(position);

        String appImg = bean.getApp_img();//封面
        String orderNum = bean.getOrder_num();//订单号
        String payStatus = bean.getPay_status();//支付状态1表示已经付款2未付款3订单取消
        String payPrice = bean.getPay_price();//实付价格
        String packageName = bean.getPackage_name();//课程名
        //TODO 封面图
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.image_loaderror)
                .transform(new CenterCrop(), new RoundedCorners(DensityUtil.dp2px(5)))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        GlideUtils.load(context, appImg, holder.mImageOrder, requestOptions);


        if (!TextUtils.isEmpty(orderNum)) {//TODO 订单编号
            holder.mNumberOrder.setText(orderNum);
        } else {
            holder.mNumberOrder.setText("");
        }
        if (!TextUtils.isEmpty(packageName)) {//TODO 课程名
            holder.mTitleOrder.setText(packageName);
        } else {
            holder.mTitleOrder.setText("");
        }
        //TODO 课程价格
        holder.mPriceOrder.setText(String.valueOf(RMB + payPrice));
        //TODO 订单状态
        if (!TextUtils.isEmpty(payStatus)) {
            int parseInt = Integer.parseInt(payStatus);
            switch (parseInt) {
                case 1://已付款
                    holder.mStatusOrder.setText(context.getString(R.string.successfulDeal));
                    holder.mStatusOrder.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
                    break;
                case 2://未付款
                    holder.mStatusOrder.setText(context.getString(R.string.ForthePayment));
                    holder.mStatusOrder.setTextColor(ContextCompat.getColor(context, R.color.color_F99111));
                    break;
                case 3://订单已取消
                default:
                    holder.mStatusOrder.setText(context.getString(R.string.HasBeenCancelled));
                    holder.mStatusOrder.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
                    break;
            }
            if (TextUtils.equals(String.valueOf("2"), payStatus)) {
                holder.mPayOrder.setVisibility(holder.mPayOrder.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
                holder.mCancelOrder.setVisibility(holder.mCancelOrder.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
            } else {
                holder.mPayOrder.setVisibility(holder.mPayOrder.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
                holder.mCancelOrder.setVisibility(holder.mCancelOrder.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
            }
        }

        holder.mPayOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.clickPay(position);
            }
        });
        holder.mCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.clickCancel(position);
            }
        });
        holder.mServiceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.clickConnect();
            }
        });

    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_mine_orderformlist, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mNumberOrder;
        private TextView mStatusOrder;
        private NiceImageView mImageOrder;
        private TextView mTitleOrder;
        private TextView mPriceOrder;
        private TextView mServiceOrder;
        private TextView mCancelOrder;
        private RoundTextView mPayOrder;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mNumberOrder = (TextView) itemView.findViewById(R.id.order_number);
            mStatusOrder = (TextView) itemView.findViewById(R.id.order_status);
            mImageOrder = (NiceImageView) itemView.findViewById(R.id.order_image);
            mTitleOrder = (TextView) itemView.findViewById(R.id.order_title);
            mPriceOrder = (TextView) itemView.findViewById(R.id.order_price);
            mServiceOrder = (TextView) itemView.findViewById(R.id.order_service);
            mCancelOrder = (TextView) itemView.findViewById(R.id.order_cancel);
            mPayOrder = (RoundTextView) itemView.findViewById(R.id.order_pay);
        }
    }


}
