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
import com.bumptech.glide.request.RequestOptions;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.user.MineWatchRecordBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.view.course.player.utils.TimeFormater;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Author: AND
 * Time: 2019-6-27.  下午 4:31
 * FileName: MineWatchRecordAdapter
 * Description:TODO 我的观看记录
 */
public class MineWatchRecordAdapter extends BaseAdapter<MineWatchRecordBean.DataBean, MineWatchRecordAdapter.ViewHolder> {
    private List<MineWatchRecordBean.DataBean> list;
    private Context context;

    public MineWatchRecordAdapter(List<MineWatchRecordBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        MineWatchRecordBean.DataBean bean = list.get(position);
        String app_img = bean.getApp_img();
        String course_name = bean.getCourse_name();
        String video_name = bean.getVideo_name();
        int watch_time = bean.getWatch_time();

        RequestOptions requestOptions = new RequestOptions()
                .transform(new RoundedCornersTransformation(DensityUtil.dp2px(5),0))
                .placeholder(R.mipmap.banner_default)
                .error(R.mipmap.image_loaderror)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        GlideUtils.load(context, app_img, holder.mCourseImageItem, requestOptions);

        if (!TextUtils.isEmpty(course_name)) {
            holder.mCourseTitleItem.setText(course_name);
        }
        if (!TextUtils.isEmpty(video_name)) {
            holder.mCourseSubtitleItem.setText(video_name);
        }
        String formatMs = TimeFormater.formatSeconds(watch_time);
        holder.mCourseTimeItem.setText(String.valueOf(context.getString(R.string.course_courseDuration) + ": " + formatMs));
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_watchrecord, viewGroup, false);
        return new ViewHolder(inflate);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mCourseImageItem;
        private TextView mCourseTitleItem;
        private TextView mCourseSubtitleItem;
        private TextView mCourseTimeItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mCourseImageItem = (ImageView) itemView.findViewById(R.id.item_course_image);
            mCourseTitleItem = (TextView) itemView.findViewById(R.id.item_course_title);
            mCourseSubtitleItem = (TextView) itemView.findViewById(R.id.item_course_subtitle);
            mCourseTimeItem = (TextView) itemView.findViewById(R.id.item_course_time);
        }
    }
}
