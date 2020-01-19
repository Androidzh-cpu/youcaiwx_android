package com.ucfo.youcaiwx.adapter.user;

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
import com.bumptech.glide.request.RequestOptions;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.user.MineCPEWatchRecordBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Author: AND
 * Time: 2019-12-30.  上午 11:29
 * Package: com.ucfo.youcaiwx.adapter.user
 * FileName: MineCPEWatchRecordAdapter
 * Description:TODO 我的CPE课程适配器
 */
public class MineCPEWatchRecordAdapter extends BaseAdapter<MineCPEWatchRecordBean.DataBean, MineCPEWatchRecordAdapter.ViewHolder> {
    private List<MineCPEWatchRecordBean.DataBean> list;
    private Context context;

    public MineCPEWatchRecordAdapter(List<MineCPEWatchRecordBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void notifyChange(List<MineCPEWatchRecordBean.DataBean> dataBeanList) {
        this.list = dataBeanList;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        MineCPEWatchRecordBean.DataBean bean = list.get(position);
        String appImg = bean.getApp_img();
        String name = bean.getName();
        String videoName = bean.getVideo_name();
        String videoTime = bean.getVideo_time();
        String complete = bean.getComplete();

        RequestOptions requestOptions = new RequestOptions()
                .transform(new RoundedCornersTransformation(DensityUtil.dp2px(5), 0))
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.image_loaderror)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        GlideUtils.load(context, appImg, holder.mCourseImageItem, requestOptions);

        if (!TextUtils.isEmpty(name)) {
            holder.mCourseTitleItem.setText(name);
        }
        if (!TextUtils.isEmpty(videoName)) {
            holder.mCourseSubtitleItem.setText(videoName);
        }
        holder.mCourseTimeItem.setText(String.valueOf(context.getString(R.string.course_courseDuration) + videoTime));

        if (TextUtils.equals(complete, String.valueOf(1))) {
            holder.mCompletedTxt.setText(context.getResources().getString(R.string.completed));
            holder.mCompletedTxt.setTextColor(ContextCompat.getColor(context, R.color.color_0267FF));
        } else {
            holder.mCompletedTxt.setText(context.getResources().getString(R.string.no_completed));
            holder.mCompletedTxt.setTextColor(ContextCompat.getColor(context, R.color.color_666666));
        }
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View inflate = layoutInflater.inflate(R.layout.item_watchrecord, viewGroup, false);
        return new ViewHolder(inflate);
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mCourseImageItem;
        private TextView mCourseTitleItem;
        private TextView mCourseSubtitleItem;
        private TextView mCourseTimeItem;
        private TextView mCompletedTxt;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mCourseImageItem = (ImageView) itemView.findViewById(R.id.item_course_image);
            mCourseTitleItem = (TextView) itemView.findViewById(R.id.item_course_title);
            mCourseSubtitleItem = (TextView) itemView.findViewById(R.id.item_course_subtitle);
            mCourseTimeItem = (TextView) itemView.findViewById(R.id.item_course_time);
            mCompletedTxt = (TextView) itemView.findViewById(R.id.txt_completed);
        }
    }

}
