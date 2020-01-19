package com.ucfo.youcaiwx.adapter.course;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.course.CourseDirBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;

import java.util.List;

/**
 * Author:29117
 * Time: 2019-4-16.  上午 10:16
 * Email:2911743255@qq.com
 * ClassName: CoursePackageListAdapter
 */
public class CoursePackageListAdapter extends BaseAdapter<CourseDirBean.DataBean, CoursePackageListAdapter.ViewHolder> {
    private List<CourseDirBean.DataBean> list;
    private Context context;

    public CoursePackageListAdapter(List<CourseDirBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void notifyChange(List<CourseDirBean.DataBean> dataBeans) {
        this.list = dataBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        CourseDirBean.DataBean dataBean = list.get(position);
        String name = dataBean.getName();
        String teacherName = dataBean.getTeacher_name();
        if (!TextUtils.isEmpty(name)) {
            holder.mCourseTitleItem.setText(name);
        }
        if (!TextUtils.isEmpty(teacherName)) {
            holder.mCourseAuthorItem.setText(context.getResources().getString(R.string.teacher, teacherName));
        }
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View inflate = layoutInflater.inflate(R.layout.item_coursepackagelist, viewGroup, false);
        return new ViewHolder(inflate);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {


        private TextView mCourseTitleItem;
        private TextView mCourseAuthorItem;
        private TextView mCourseTimeItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {

            mCourseTitleItem = (TextView) itemView.findViewById(R.id.item_course_title);
            mCourseAuthorItem = (TextView) itemView.findViewById(R.id.item_course_author);
            mCourseTimeItem = (TextView) itemView.findViewById(R.id.item_course_time);
        }
    }

}
