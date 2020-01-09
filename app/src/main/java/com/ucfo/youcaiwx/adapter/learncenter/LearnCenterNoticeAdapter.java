package com.ucfo.youcaiwx.adapter.learncenter;

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
import com.ucfo.youcaiwx.entity.learncenter.LearncenterHomeBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-19.  上午 9:03
 * FileName: LearnCenterNoticeAdapter
 * Description:TODO 学习公告适配器
 */
public class LearnCenterNoticeAdapter extends BaseAdapter<LearncenterHomeBean.DataBean.NewsBean, LearnCenterNoticeAdapter.ViewHolder> {
    private List<LearncenterHomeBean.DataBean.NewsBean> list;
    private Context context;

    public LearnCenterNoticeAdapter(List<LearncenterHomeBean.DataBean.NewsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void notifyChange(List<LearncenterHomeBean.DataBean.NewsBean> dataBeanList) {
        this.list = dataBeanList;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        LearncenterHomeBean.DataBean.NewsBean bean = list.get(position);
        String creates_time = bean.getCreates_time();
        String title = bean.getTitle();
        String source = bean.getSource();
        String imageurl = bean.getImage();
        if (!TextUtils.isEmpty(creates_time)) {
            holder.mCourseTimeItem.setText(creates_time);
        }
        if (!TextUtils.isEmpty(title)) {
            holder.mCourseTitleItem.setText(title);
        }
        if (!TextUtils.isEmpty(source)) {
            String s = context.getResources().getString(R.string.source, source);
            holder.mCourseAuthorItem.setText(s);
        }
        if (!TextUtils.isEmpty(imageurl)) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.icon_default)
                    .error(R.mipmap.icon_default)
                    .transform(new RoundedCorners(DensityUtil.dp2px(5)))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            GlideUtils.load(context, imageurl, holder.mCourseImageItem, requestOptions);
        }
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_home_news, viewGroup, false);
        return new ViewHolder(inflate);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mCourseImageItem;
        private TextView mCourseTitleItem;
        private TextView mCourseAuthorItem;
        private TextView mCourseTimeItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View view) {
            mCourseImageItem = (ImageView) itemView.findViewById(R.id.item_course_image);
            mCourseTitleItem = (TextView) itemView.findViewById(R.id.item_course_title);
            mCourseAuthorItem = (TextView) itemView.findViewById(R.id.item_course_author);
            mCourseTimeItem = (TextView) itemView.findViewById(R.id.item_course_time);
        }
    }

}
