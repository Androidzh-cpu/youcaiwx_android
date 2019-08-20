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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.home.HomeBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.glideutils.GlideRoundTransform;

import java.util.List;

/**
 * Author:AND
 * Time: 2019/3/14.  19:06
 * Email:2911743255@qq.com
 * ClassName: HomeNewsAdapter
 * Description:
 * Detail:
 */
public class HomeNewsAdapter extends BaseAdapter<HomeBean.DataBean.CurriculumBean, HomeNewsAdapter.ViewHolder> {
    private List<HomeBean.DataBean.InformationBean> list;
    private Context context;

    public HomeNewsAdapter(List<HomeBean.DataBean.InformationBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        HomeBean.DataBean.InformationBean bean = list.get(position);
        String create_time = bean.getCreate_time();
        String imageurl = bean.getImageurl();
        String title = bean.getTitle();
        String source = bean.getSource();
        if (!TextUtils.isEmpty(create_time)) {
            holder.mCourseTimeItem.setText(create_time);
        }
        if (!TextUtils.isEmpty(title)) {
            holder.mCourseTitleItem.setText(title);
        }
        if (!TextUtils.isEmpty(source)) {
            String s = String.valueOf(context.getResources().getString(R.string.source) + ": " + source);
            holder.mCourseAuthorItem.setText(s);
        }
        if (!TextUtils.isEmpty(imageurl)) {
            Glide.with(context).load(imageurl).transform(new CenterCrop(context), new GlideRoundTransform(context, 6))
                    .diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(holder.mCourseImageItem);
        }
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_home_news, viewGroup, false);
        HomeNewsAdapter.ViewHolder holder = new HomeNewsAdapter.ViewHolder(inflate);
        return holder;
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

        private void initView(@NonNull final View itemView) {

            mCourseImageItem = (ImageView) itemView.findViewById(R.id.item_course_image);
            mCourseTitleItem = (TextView) itemView.findViewById(R.id.item_course_title);
            mCourseAuthorItem = (TextView) itemView.findViewById(R.id.item_course_author);
            mCourseTimeItem = (TextView) itemView.findViewById(R.id.item_course_time);
        }
    }

}