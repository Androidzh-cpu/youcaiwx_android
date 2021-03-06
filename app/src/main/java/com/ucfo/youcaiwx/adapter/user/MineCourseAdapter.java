package com.ucfo.youcaiwx.adapter.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.user.MineCourseBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-6-18.  下午 2:28
 * FileName: MineCourseAdapter
 * Description:TODO 我的课程
 */
public class MineCourseAdapter extends BaseAdapter<MineCourseBean.DataBean, MineCourseAdapter.ViewHolder> {
    private Context context;
    private List<MineCourseBean.DataBean> list;

    public MineCourseAdapter(Context context, List<MineCourseBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public void notifyChange(List<MineCourseBean.DataBean> dataBeanList) {
        this.list = dataBeanList;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        MineCourseBean.DataBean bean = list.get(position);
        String appImg = bean.getApp_img();
        int joinNum = bean.getJoin_num();
        String name = bean.getName();
        String teacherName = bean.getTeacher_name();
        int studyDays = bean.getStudy_days();//课时
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.image_loaderror)
                .transform(new RoundedCorners(DensityUtil.dp2px(5)))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        GlideUtils.load(context, appImg, holder.mCourseImageItem, requestOptions);
        if (!TextUtils.isEmpty(teacherName)) {
            holder.mCourseTeacherItem.setText(teacherName);
        }
        if (!TextUtils.isEmpty(name)) {
            holder.mCourseTitleItem.setText(name);
        }
        holder.mCourseCountItem.setText(context.getResources().getString(R.string.course_NumOfLearning, String.valueOf(joinNum)));
        holder.mCourseTimeItem.setText(context.getResources().getString(R.string.orderForm_endtime2, String.valueOf(studyDays)));
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View inflate = layoutInflater.inflate(R.layout.item_minecourse, viewGroup, false);
        return new MineCourseAdapter.ViewHolder(inflate);
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
