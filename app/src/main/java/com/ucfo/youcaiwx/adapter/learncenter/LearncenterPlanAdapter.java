package com.ucfo.youcaiwx.adapter.learncenter;

import android.content.Context;
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
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.learncenter.LearncenterHomeBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.baseadapter.OnItemClickListener;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-18.  下午 2:18
 * FileName: LearncenterPlanAdapter
 * Description:TODO 学习中心--学习计划列表
 */
public class LearncenterPlanAdapter extends BaseAdapter<LearncenterHomeBean.DataBean.PlanBean, LearncenterPlanAdapter.ViewHolder> {
    private List<LearncenterHomeBean.DataBean.PlanBean> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    //定义一个公共方法,当外部类调用时可以通过此方法点击item
    public void setItemClick(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public LearncenterPlanAdapter(List<LearncenterHomeBean.DataBean.PlanBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void notifyChange(List<LearncenterHomeBean.DataBean.PlanBean> dataBeanList) {
        this.list = dataBeanList;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        LearncenterHomeBean.DataBean.PlanBean bean = list.get(position);
        String plan_name = bean.getPlan_name();
        int plan_num = bean.getPlan_num();
        holder.mCount.setText(String.valueOf(plan_num));
        if (!TextUtils.isEmpty(plan_name)) {
            holder.mTitle.setText(plan_name);
        }
        holder.mJoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, position);
            }
        });
        int i = position % 3;
        switch (i) {
            case 0://余数为0
                holder.mBackgroundItem.setBackground(ContextCompat.getDrawable(context, R.color.color_FFF8F2));
                break;
            case 1://余数为1
                holder.mBackgroundItem.setBackground(ContextCompat.getDrawable(context, R.color.color_F9F6FF));
                break;
            case 2://余数为2
                holder.mBackgroundItem.setBackground(ContextCompat.getDrawable(context, R.color.color_EEFFFD));
                break;
            default:
                break;
        }
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View inflate = layoutInflater.inflate(R.layout.item_lc_plan, viewGroup, false);
        return new ViewHolder(inflate);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mCount;
        private RoundTextView mJoinBtn;
        private LinearLayout mBackgroundItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View view) {
            mTitle = (TextView) view.findViewById(R.id.title);
            mCount = (TextView) view.findViewById(R.id.count);
            mJoinBtn = (RoundTextView) view.findViewById(R.id.btn_join);
            mBackgroundItem = (LinearLayout) itemView.findViewById(R.id.item_background);
        }
    }
}
