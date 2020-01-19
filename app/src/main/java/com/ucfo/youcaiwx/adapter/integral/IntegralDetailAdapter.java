package com.ucfo.youcaiwx.adapter.integral;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.integral.IntegralDetailBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-10-15.  上午 9:46
 * Package: com.ucfo.youcaiwx.adapter.integral
 * FileName: IntegralDetailAdapter
 * Description:TODO 积分明细适配
 */
public class IntegralDetailAdapter extends BaseAdapter<IntegralDetailBean.DataBean, IntegralDetailAdapter.ViewHolder> {
    private List<IntegralDetailBean.DataBean> list;
    private Context context;

    public IntegralDetailAdapter(List<IntegralDetailBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void notifyChange(List<IntegralDetailBean.DataBean> dataBeanList) {
        this.list = dataBeanList;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        IntegralDetailBean.DataBean bean = list.get(position);
        String detailed = bean.getDetailed();
        String name = bean.getName();
        String uptime = bean.getUptime();
        int type = bean.getType();

        if (!TextUtils.isEmpty(name)) {
            holder.mDetailTitleItem.setText(name);
        }
        if (!TextUtils.isEmpty(detailed)) {
            if (type == 1) {
                holder.mDetailPointItem.setText(context.getResources().getString(R.string.integral_pointAdd, detailed));
                holder.mDetailPointItem.setTextColor(ContextCompat.getColor(context, R.color.color_E84342));
            } else {
                holder.mDetailPointItem.setText(context.getResources().getString(R.string.integral_pointMinus, detailed));
                holder.mDetailPointItem.setTextColor(ContextCompat.getColor(context, R.color.color_0267FF));
            }
        }
        if (!TextUtils.isEmpty(uptime)) {
            holder.mDetailTimeItem.setText(uptime);
        }
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_integral_detail, viewGroup, false);
        return new ViewHolder(view);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mDetailTitleItem;
        private TextView mDetailTimeItem;
        private TextView mDetailPointItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mDetailTitleItem = (TextView) itemView.findViewById(R.id.item_detail_title);
            mDetailTimeItem = (TextView) itemView.findViewById(R.id.item_detail_time);
            mDetailPointItem = (TextView) itemView.findViewById(R.id.item_detail_point);
        }
    }
}
