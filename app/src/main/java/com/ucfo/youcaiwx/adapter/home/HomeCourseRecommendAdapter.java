package com.ucfo.youcaiwx.adapter.home;

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
import com.ucfo.youcaiwx.entity.home.HomeBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.util.List;

/**
 * Author:AND
 * Time: 2019/3/14.  17:04
 * Email:2911743255@qq.com
 * ClassName: HomeCourseRecommendAdapter
 * Description:首页课程推荐适配器
 * Detail:
 */
public class HomeCourseRecommendAdapter extends BaseAdapter<HomeBean.DataBean.CurriculumBean, HomeCourseRecommendAdapter.ViewHolder> {
    private Context context;
    private List<HomeBean.DataBean.CurriculumBean> list;

    public HomeCourseRecommendAdapter(List<HomeBean.DataBean.CurriculumBean> list, Context context) {
        this.context = context;
        this.list = list;
    }


    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {

        HomeBean.DataBean.CurriculumBean bean = list.get(position);
        String app_img = bean.getApp_img();
        String name = bean.getName();
        String teacher_name = bean.getTeacher_name();
        String price = bean.getPrice();
        String billing_status = bean.getBilling_status();//计费方式: 1免费2收费3按积分越换4按等级进入
        String join_num = bean.getJoin_num();
        if (!TextUtils.isEmpty(app_img)) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.banner_default)
                    .error(R.mipmap.image_loaderror)
                    .transform(new CenterCrop(),new RoundedCorners(DensityUtil.dp2px(5)))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            GlideUtils.load(context, app_img, holder.mCourseImageItem, requestOptions);
        }
        if (!TextUtils.isEmpty(teacher_name)) {
            holder.mCourseAuthorItem.setText(String.valueOf(context.getResources().getString(R.string.holder_teacher) + teacher_name));
        }
        if (!TextUtils.isEmpty(name)) {
            holder.mCourseTitleItem.setText(name);
        }
        if (!TextUtils.isEmpty(billing_status)) {
            switch (Integer.parseInt(billing_status)) {//1免费2收费3按积分越换4按等级进入
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
        } else {
            holder.mCoursePriceItem.setBackgroundResource(R.drawable.item_home_purpleback);
            holder.mCoursePriceItem.setText(context.getResources().getString(R.string.course_free));
            holder.mCoursePriceItem.setTextColor(ContextCompat.getColor(context, R.color.color_5B78F6));
        }
        if (!TextUtils.isEmpty(join_num)) {
            holder.mCourseCountItem.setText(String.valueOf(context.getResources().getString(R.string.course_joincount) + join_num));
        }

    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_home_course, viewGroup, false);
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
