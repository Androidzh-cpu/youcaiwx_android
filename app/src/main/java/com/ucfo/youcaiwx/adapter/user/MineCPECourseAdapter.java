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
import com.ucfo.youcaiwx.entity.user.MineCPECourseBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-12-27.  下午 3:53
 * Package: com.ucfo.youcaiwx.adapter.user
 * FileName: MineCPECourseAdapter
 * Description:TODO 我的CPE垃圾
 */
public class MineCPECourseAdapter extends BaseAdapter<MineCPECourseBean.DataBean, MineCPECourseAdapter.ViewHolder> {
    private Context context;
    private List<MineCPECourseBean.DataBean> list;

    public MineCPECourseAdapter(Context context, List<MineCPECourseBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public void notifyChange(List<MineCPECourseBean.DataBean> dataBeanList) {
        this.list = dataBeanList;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        MineCPECourseBean.DataBean bean = list.get(position);

        String appImg = bean.getApp_img();
        String cpeIntegral = bean.getCpe_integral();
        String joinNum = bean.getJoin_num();
        String name = bean.getName();
        String teacherName = bean.getTeacher_name();
        String studyDays = bean.getStudy_days();

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.image_loaderror)
                .transform(new RoundedCorners(DensityUtil.dp2px(5)))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        GlideUtils.load(context, appImg, holder.mCourseImageItem, requestOptions);

        if (!TextUtils.isEmpty(teacherName)) {
            holder.mCourseTeacherItem.setText(context.getResources().getString(R.string.teacher, teacherName));
        }
        if (!TextUtils.isEmpty(name)) {
            holder.mCourseTitleItem.setText(name);
        }
        holder.mCourseCountItem.setText(context.getResources().getString(R.string.course_NumOfLearning, String.valueOf(joinNum)));

        if (!TextUtils.isEmpty(cpeIntegral)) {
            holder.mCoursePointItem.setText(context.getResources().getString(R.string.event_point, cpeIntegral));
        }

        holder.mCoursePriceItem.setVisibility(View.GONE);
        holder.mCourseTimeItem.setVisibility(View.VISIBLE);

        if (!TextUtils.isEmpty(studyDays)) {
            holder.mCourseTimeItem.setText(context.getResources().getString(R.string.orderForm_endtime2, studyDays));
        }
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_cpecourse, viewGroup, false);
        return new ViewHolder(inflate);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mCourseImageItem;
        private TextView mCourseTitleItem;
        private TextView mCourseTeacherItem;
        private TextView mCoursePointItem;
        private TextView mCoursePriceItem;
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
            mCoursePointItem = (TextView) itemView.findViewById(R.id.item_course_point);
            mCoursePriceItem = (TextView) itemView.findViewById(R.id.item_course_price);
            mCourseTimeItem = (TextView) itemView.findViewById(R.id.item_course_time);
            mCourseCountItem = (TextView) itemView.findViewById(R.id.item_course_count);
        }
    }
}
