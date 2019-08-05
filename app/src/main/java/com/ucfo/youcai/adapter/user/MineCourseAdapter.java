package com.ucfo.youcai.adapter.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.ucfo.youcai.R;
import com.ucfo.youcai.entity.user.MineCourseBean;
import com.ucfo.youcai.utils.baseadapter.BaseAdapter;
import com.ucfo.youcai.utils.glideutils.GlideRoundTransform;

import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-6-18.  下午 2:28
 * Package: com.ucfo.youcai.adapter.user
 * FileName: MineCourseAdapter
 * Description:TODO 我的课程
 */
public class MineCourseAdapter extends BaseAdapter<MineCourseBean.DataBean, MineCourseAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MineCourseBean.DataBean> list;

    public MineCourseAdapter(Context context, ArrayList<MineCourseBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        MineCourseBean.DataBean bean = list.get(position);
        String app_img = bean.getApp_img();
        int join_num = bean.getJoin_num();
        String name = bean.getName();
        String teacher_name = bean.getTeacher_name();
        int study_days = bean.getStudy_days();//课时
        //GlideUtils.loadRoundImageView(context, app_img, holder.mCourseImageItem, R.mipmap.banner_default, 2);
        Glide.with(context)
                .load(app_img)
                .transform(new CenterCrop(context), new GlideRoundTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(holder.mCourseImageItem);
        if (!TextUtils.isEmpty(teacher_name)) {
            holder.mCourseTeacherItem.setText(String.valueOf(context.getResources().getString(R.string.holder_teacher) + teacher_name));
        }
        if (!TextUtils.isEmpty(name)) {
            holder.mCourseTitleItem.setText(name);
        }
        holder.mCourseCountItem.setText(String.valueOf(join_num + context.getResources().getString(R.string.mine_Course_holder1)));
        holder.mCourseTimeItem.setText(String.valueOf(study_days + context.getResources().getString(R.string.mine_Course_holder2)));
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_minecourse, viewGroup, false);
        MineCourseAdapter.ViewHolder holder = new MineCourseAdapter.ViewHolder(inflate);
        return holder;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mCourseImageItem;
        private TextView mCourseTitleItem;
        private TextView mCourseTeacherItem;
        private TextView mCourseTimeItem;
        private TextView mCourseCountItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mCourseImageItem = (ImageView) itemView.findViewById(R.id.item_course_image);
            mCourseTitleItem = (TextView) itemView.findViewById(R.id.item_course_title);
            mCourseTeacherItem = (TextView) itemView.findViewById(R.id.item_course_teacher);
            mCourseTimeItem = (TextView) itemView.findViewById(R.id.item_course_time);
            mCourseCountItem = (TextView) itemView.findViewById(R.id.item_course_count);
        }
    }

}
