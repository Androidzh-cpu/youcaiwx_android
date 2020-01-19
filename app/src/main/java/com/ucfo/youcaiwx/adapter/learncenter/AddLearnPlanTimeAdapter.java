package com.ucfo.youcaiwx.adapter.learncenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.learncenter.AddLearnPlanCheckTimeBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-19.  上午 11:42
 * FileName: AddLearnPlanTimeAdapter
 * Description:TODO 学习计划添加时间
 */
public class AddLearnPlanTimeAdapter extends BaseAdapter<AddLearnPlanCheckTimeBean.DataBean, AddLearnPlanTimeAdapter.ViewHolder> {

    private List<AddLearnPlanCheckTimeBean.DataBean> list;
    private Context context;
    private int selectPosition = -1;

    public AddLearnPlanTimeAdapter(List<AddLearnPlanCheckTimeBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        AddLearnPlanCheckTimeBean.DataBean bean = list.get(position);
        String test_time = bean.getTest_time();
        holder.mQuestionTitleItem.setText(test_time);

        if (position == selectPosition) {
            holder.mQuestionTitleItem.setBackgroundResource(R.drawable.shape_selfhelp_check);
            holder.mQuestionTitleItem.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        } else {
            holder.mQuestionTitleItem.setBackgroundResource(R.drawable.shape_selfhelp_uncheck);
            holder.mQuestionTitleItem.setTextColor(ContextCompat.getColor(context, R.color.color_666666));
        }
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_addlearnplan_course, viewGroup, false);
        return new ViewHolder(view);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mQuestionTitleItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mQuestionTitleItem = (TextView) itemView.findViewById(R.id.item_list_title);
        }

    }
}
