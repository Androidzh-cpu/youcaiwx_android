package com.ucfo.youcaiwx.adapter.course;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.course.CourseIntroductionBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author:29117
 * Time: 2019-4-4.  下午 4:58
 * Email:2911743255@qq.com
 * ClassName: CourseTeacherAdapter
 */
public class CourseTeacherAdapter extends BaseAdapter<CourseIntroductionBean.DataBean.TeacehrListBean, CourseTeacherAdapter.ViewHolder> {
    private List<CourseIntroductionBean.DataBean.TeacehrListBean> list;
    private Context context;

    public CourseTeacherAdapter(List<CourseIntroductionBean.DataBean.TeacehrListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        CourseIntroductionBean.DataBean.TeacehrListBean bean = list.get(position);
        String longevity = bean.getIntroduce();
        String teacher_name = bean.getTeacher_name();
        String pictrue = bean.getPictrue();//头像
        if (!TextUtils.isEmpty(longevity)) {
            holder.mTeacherdetailTv.setText(longevity);
        }
        if (!TextUtils.isEmpty(teacher_name)) {
            holder.mTeachernameTv.setText(teacher_name);
        }
        Glide.with(context).load(pictrue).error(R.mipmap.icon_headdefault).into(holder.mTeacherImage);
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_course_teacher, viewGroup, false);
        CourseTeacherAdapter.ViewHolder holder = new CourseTeacherAdapter.ViewHolder(inflate);
        return holder;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {


        private CircleImageView mTeacherImage;
        private TextView mTeachernameTv;
        private TextView mTeacherdetailTv;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mTeacherImage = (CircleImageView) itemView.findViewById(R.id.image_teacher);
            mTeachernameTv = (TextView) itemView.findViewById(R.id.tv_teachername);
            mTeacherdetailTv = (TextView) itemView.findViewById(R.id.tv_teacherdetail);
        }
    }

}