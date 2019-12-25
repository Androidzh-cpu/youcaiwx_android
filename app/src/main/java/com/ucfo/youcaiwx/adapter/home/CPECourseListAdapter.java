package com.ucfo.youcaiwx.adapter.home;

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
import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.home.education.EducationCourseListBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-12-24.  下午 3:15
 * Package: com.ucfo.youcaiwx.adapter.home
 * FileName: CPECourseListAdapter
 * Description:TODO CPE课程适配器
 */
public class CPECourseListAdapter extends BaseAdapter<EducationCourseListBean.DataBeanX.DataBean, CPECourseListAdapter.ViewHolder> {

    private Context context;
    private List<EducationCourseListBean.DataBeanX.DataBean> list;

    public CPECourseListAdapter(Context context, List<EducationCourseListBean.DataBeanX.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public void notifyChange(List<EducationCourseListBean.DataBeanX.DataBean> dataBeanList) {
        this.list = dataBeanList;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        EducationCourseListBean.DataBeanX.DataBean bean = list.get(position);
        String appImg = bean.getApp_img();
        String joinNum = bean.getJoin_num();
        String name = bean.getName();
        String teacherName = bean.getTeacher_name();
        String cpeIntegral = bean.getCpe_integral();
        String price = bean.getPrice();

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.image_loaderror)
                .transform(new RoundedCorners(DensityUtil.dp2px(5)))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        GlideUtils.load(context, appImg, holder.mCourseImageItem, requestOptions);
        if (!TextUtils.isEmpty(teacherName)) {
            holder.mCourseTeacherItem.setText(String.valueOf(context.getResources().getString(R.string.holder_teacher) + teacherName));
        }
        if (!TextUtils.isEmpty(name)) {
            holder.mCourseTitleItem.setText(name);
        }
        holder.mCourseCountItem.setText(context.getResources().getString(R.string.course_NumOfLearning, String.valueOf(joinNum)));

        if (!TextUtils.isEmpty(cpeIntegral)) {
            holder.mCoursePointItem.setText(context.getResources().getString(R.string.event_point, cpeIntegral));
        }
        if (!TextUtils.isEmpty(price)) {
            holder.mCoursePriceItem.setText(String.valueOf(context.getResources().getString(R.string.RMB) + price));
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
        private RoundTextView mCoursePriceItem;
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
            mCoursePriceItem = (RoundTextView) itemView.findViewById(R.id.item_course_price);
            mCourseCountItem = (TextView) itemView.findViewById(R.id.item_course_count);
        }
    }

}
