package com.ucfo.youcaiwx.adapter.learncenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.learncenter.AddLearnPlanCheckCourseBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-19.  上午 10:13
 * FileName: AddLearnPlanCourseAdapter
 * Description:TODO 添加学习计划-选择课程适配器
 */
public class AddLearnPlanCourseAdapter extends BaseAdapter<AddLearnPlanCheckCourseBean.DataBean, AddLearnPlanCourseAdapter.ViewHolder> {
    private List<AddLearnPlanCheckCourseBean.DataBean> list;
    private Context context;
    private int selectPosition = -1;

    public AddLearnPlanCourseAdapter(List<AddLearnPlanCheckCourseBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setSelectPosition(int position) {
        this.selectPosition = position;
        notifyDataSetChanged();
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        AddLearnPlanCheckCourseBean.DataBean bean = list.get(position);

        String course_name = bean.getCourse_name();
        String state = bean.getState();
        holder.mQuestionTitleItem.setText(course_name);
        if (state.equals("1")) {
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }

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
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_addlearnplan_course, viewGroup, false);
        return new ViewHolder(inflate);
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mQuestionTitleItem;
        private ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mQuestionTitleItem = (TextView) itemView.findViewById(R.id.item_list_title);
            imageView = (ImageView) itemView.findViewById(R.id.item_list_image);
        }

    }
}
