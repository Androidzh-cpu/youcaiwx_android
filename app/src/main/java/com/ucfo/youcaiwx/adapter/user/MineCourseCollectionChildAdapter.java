package com.ucfo.youcaiwx.adapter.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.user.MineCourseChildListBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;

import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-6-24.  下午 3:38
 * FileName: MineCourseCollectionChildAdapter
 * Description:TODO 我的课程收藏课程列表
 */
public class MineCourseCollectionChildAdapter extends BaseAdapter<MineCourseChildListBean.DataBean, MineCourseCollectionChildAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MineCourseChildListBean.DataBean> list;

    public MineCourseCollectionChildAdapter(Context context, ArrayList<MineCourseChildListBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        MineCourseChildListBean.DataBean dataBean = list.get(position);
        String name = dataBean.getName();
        String teacher_name = dataBean.getTeacher_name();

        if (!TextUtils.isEmpty(name)) {
            holder.mCourseTitleItem.setText(name);
        }
        if (!TextUtils.isEmpty(teacher_name)) {
            holder.mCourseTeacherItem.setText(String.valueOf(context.getResources().getString(R.string.holder_teacher) + "  " + teacher_name));
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_collection_courselist, viewGroup, false);
        MineCourseCollectionChildAdapter.ViewHolder holder = new MineCourseCollectionChildAdapter.ViewHolder(inflate);
        return holder;
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mCourseTitleItem;
        private TextView mCourseTeacherItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {

            mCourseTitleItem = (TextView) itemView.findViewById(R.id.item_course_title);
            mCourseTeacherItem = (TextView) itemView.findViewById(R.id.item_course_teacher);
        }
    }
}
