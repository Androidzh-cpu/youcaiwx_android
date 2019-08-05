package com.ucfo.youcai.adapter.user;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.roundview.RoundTextView;
import com.ucfo.youcai.R;
import com.ucfo.youcai.entity.user.MineOrderListBean;
import com.ucfo.youcai.utils.baseadapter.BaseAdapter;
import com.ucfo.youcai.view.user.activity.MineOrderFormActivity;
import com.ucfo.youcai.widget.customview.NiceImageView;

import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-6-21.  下午 2:39
 * Package: com.ucfo.youcai.adapter.user
 * FileName: MineOrderFormListAdapter
 * Description:TODO 我的订单适配器
 */
public class MineOrderFormListAdapter extends BaseAdapter<MineOrderListBean.DataBean, MineOrderFormListAdapter.ViewHolder> {
    private MineOrderFormActivity context;
    private ArrayList<MineOrderListBean.DataBean> list;
    private final String string;
    private IOrderCallback callback;

    public MineOrderFormListAdapter(MineOrderFormActivity context, ArrayList<MineOrderListBean.DataBean> list) {
        this.context = context;
        this.list = list;
        string = context.getResources().getString(R.string.RMB);
    }

    public void setOthersClick(IOrderCallback iOrderCallback) {
        this.callback = iOrderCallback;
    }

    public interface IOrderCallback {

        void clickPay(int position);

        void clickCancel(int position);

        void clickConnect();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        MineOrderListBean.DataBean bean = list.get(position);

        String app_img = bean.getApp_img();//封面
        String order_num = bean.getOrder_num();//订单号
        int pay_status = bean.getPay_status();//支付状态1表示已经付款2未付款3订单取消
        String pay_price = bean.getPay_price();//实付价格
        String package_name = bean.getPackage_name();//课程名
        //TODO 封面图
        Glide.with(context)
                .load(app_img)
                .asBitmap()
                .placeholder(R.mipmap.banner_default)
                .error(R.mipmap.banner_default)
                .dontAnimate()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .into(holder.mImageOrder);


        if (!TextUtils.isEmpty(order_num)) {//TODO 订单编号
            holder.mNumberOrder.setText(order_num);
        } else {
            holder.mNumberOrder.setText("");
        }
        if (!TextUtils.isEmpty(package_name)) {//TODO 课程名
            holder.mTitleOrder.setText(package_name);
        } else {
            holder.mTitleOrder.setText("");
        }
        //TODO 课程价格
        holder.mPriceOrder.setText(String.valueOf(string + pay_price));
        //TODO 订单状态
        switch (pay_status) {
            case 1://已付款
                holder.mStatusOrder.setText(context.getString(R.string.successfulDeal));
                holder.mStatusOrder.setTextColor(ContextCompat.getColor(context,R.color.color_333333));
                break;
            case 2://未付款
                holder.mStatusOrder.setText(context.getString(R.string.ForthePayment));
                holder.mStatusOrder.setTextColor(ContextCompat.getColor(context,R.color.color_F99111));
                break;
            case 3://订单已取消
            default:
                holder.mStatusOrder.setText(context.getString(R.string.HasBeenCancelled));
                holder.mStatusOrder.setTextColor(ContextCompat.getColor(context,R.color.color_333333));
                break;
        }
        if (pay_status == 2) {
            holder.mPayOrder.setVisibility(holder.mPayOrder.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
            holder.mCancelOrder.setVisibility(holder.mCancelOrder.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
        } else {
            holder.mPayOrder.setVisibility(holder.mPayOrder.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
            holder.mCancelOrder.setVisibility(holder.mCancelOrder.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
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
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
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
