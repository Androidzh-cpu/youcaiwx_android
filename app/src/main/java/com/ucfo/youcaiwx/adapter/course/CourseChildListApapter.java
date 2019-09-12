package com.ucfo.youcaiwx.adapter.course;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.course.CourseDataListBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.util.List;

/**
 * Author:29117
 * Time: 2019-4-1.  下午 2:37
 * Email:2911743255@qq.com
 * ClassName: CourseChildListApapter
 * Description: 课程列表适配器
 */
public class CourseChildListApapter extends BaseAdapter<CourseDataListBean.DataBean, CourseChildListApapter.ViewHolder> {
    private List<CourseDataListBean.DataBean> list;
    private Context context;

    public CourseChildListApapter(List<CourseDataListBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        CourseDataListBean.DataBean dataBean = list.get(position);
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.banner_default)
                .error(R.mipmap.image_loaderror)
                .transform(new CenterCrop(), new RoundedCorners(DensityUtil.dp2px(5)))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        GlideUtils.load(context, dataBean.getApp_img(), holder.mCourseImageItem, requestOptions);

        int billingStatus = dataBean.getBilling_status();//付费
        String price = dataBean.getPrice();//课程价格
        String teacherName = dataBean.getTeacher_name();//老师名称
        String name = dataBean.getName();//课程名称
        int studyDays = dataBean.getStudy_days();//课时
        if (!TextUtils.isEmpty(name)) {
            holder.mCourseTitleItem.setText(name);
        }
        if (!TextUtils.isEmpty(teacherName)) {
            holder.mCourseAuthorItem.setText(String.valueOf(context.getString(R.string.holder_teacher) + teacherName));
        }
        holder.mCourseCountItem.setText(String.valueOf(context.getString(R.string.orderForm_endtime3, String.valueOf(studyDays))));
        switch (billingStatus) {//1免费2收费3按积分越换4按等级进入
            case 1:
                holder.mCoursePriceItem.setBackgroundResource(R.drawable.item_home_purpleback);
                holder.mCoursePriceItem.setText(context.getResources().getString(R.string.course_free));
                holder.mCoursePriceItem.setTextColor(ContextCompat.getColor(context, R.color.color_5B78F6));
                break;
            case 2:
                holder.mCoursePriceItem.setBackgroundResource(R.drawable.item_home_orangeback);
                holder.mCoursePriceItem.setText(String.valueOf(context.getResources().getString(R.string.RMB) + price));
                holder.mCoursePriceItem.setTextColor(ContextCompat.getColor(context, R.color.color_F88C00));
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                break;
        }
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_course_list, viewGroup, false);
        return new ViewHolder(inflate);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mCourseImageItem;
        private TextView mCourseTitleItem;
        private TextView mCourseAuthorItem;
        private TextView mCoursePriceItem;
        private TextView mCourseCountItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mCourseImageItem = (ImageView) itemView.findViewById(R.id.item_course_image);
            mCourseTitleItem = (TextView) itemView.findViewById(R.id.item_course_title);
            mCourseAuthorItem = (TextView) itemView.findViewById(R.id.item_course_author);
            mCoursePriceItem = (TextView) itemView.findViewById(R.id.item_course_price);
            mCourseCountItem = (TextView) itemView.findViewById(R.id.item_course_count);
        }
    }

}
