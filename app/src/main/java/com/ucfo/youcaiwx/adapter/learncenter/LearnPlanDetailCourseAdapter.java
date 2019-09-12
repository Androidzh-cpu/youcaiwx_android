package com.ucfo.youcaiwx.adapter.learncenter;

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
import com.ucfo.youcaiwx.entity.learncenter.LearnPlanDetailVideoBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-22.  下午 2:41
 * FileName: LearnPlanDetailCourseAdapter
 * Description:TODO 计划详情适配器
 */

public class LearnPlanDetailCourseAdapter extends BaseAdapter<LearnPlanDetailVideoBean.DataBean.VideoBean, LearnPlanDetailCourseAdapter.ViewHolder> {
    private Context context;
    private List<LearnPlanDetailVideoBean.DataBean.VideoBean> list;

    public LearnPlanDetailCourseAdapter(Context context, List<LearnPlanDetailVideoBean.DataBean.VideoBean> list) {
        this.context = context;
        this.list = list;
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void setOnNoteClick(int position);

        void setOnExerciseClick(int position);
    }

    public void setItemClick(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        LearnPlanDetailVideoBean.DataBean.VideoBean bean = list.get(position);
        String beancoverurl = bean.getCoverURL();
        String videoName = bean.getVideo_name();
        String videoTime = bean.getVideo_time();
        if (!TextUtils.isEmpty(videoName)) {
            holder.mCourseTitleItem.setText(videoName);
        }
        if (!TextUtils.isEmpty(videoTime)) {
            holder.mCourseSubtitleItem.setText(videoTime);
        }
        RequestOptions requestOptions = new RequestOptions()
                .transform(new CenterCrop(), new RoundedCorners(DensityUtil.dp2px(5)))
                .placeholder(R.mipmap.banner_default)
                .error(R.mipmap.image_loaderror)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        GlideUtils.load(context, beancoverurl, holder.mCourseImageItem, requestOptions);

        String sameday = bean.getSameday();
        if (TextUtils.equals(sameday, String.valueOf(1))) {
            //当天计划
            holder.mCourseTitleItem.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
            holder.mCourseSubtitleItem.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
            holder.mCourseNotesItem.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
            holder.mCourseExerciseItem.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
            int isWatch = bean.getIs_watch();
            if (isWatch == 1) {
                //已完成计划
                holder.compltedImageView.setVisibility(View.VISIBLE);
                holder.lockedImageView.setVisibility(View.GONE);
            } else {
                //未完成当天计划
                holder.lockedImageView.setVisibility(View.GONE);
                holder.compltedImageView.setVisibility(View.GONE);
            }
        } else {
            //非当天计划
            holder.mCourseTitleItem.setTextColor(ContextCompat.getColor(context, R.color.color_C7C7C7));
            holder.mCourseSubtitleItem.setTextColor(ContextCompat.getColor(context, R.color.color_C7C7C7));
            holder.mCourseNotesItem.setTextColor(ContextCompat.getColor(context, R.color.color_C7C7C7));
            holder.mCourseExerciseItem.setTextColor(ContextCompat.getColor(context, R.color.color_C7C7C7));

            holder.lockedImageView.setVisibility(View.VISIBLE);
            holder.compltedImageView.setVisibility(View.GONE);
        }

        holder.mCourseNotesItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.setOnNoteClick(position);
            }
        });
        holder.mCourseExerciseItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.setOnExerciseClick(position);
            }
        });
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_learnplandetail_course, viewGroup, false);
        return new ViewHolder(inflate);
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mCourseImageItem;
        private TextView mCourseTitleItem;
        private TextView mCourseSubtitleItem;
        private ImageView lockedImageView, compltedImageView;
        private TextView mCourseNotesItem;
        private TextView mCourseExerciseItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mCourseImageItem = (ImageView) itemView.findViewById(R.id.item_course_image);
            mCourseTitleItem = (TextView) itemView.findViewById(R.id.item_course_title);
            mCourseSubtitleItem = (TextView) itemView.findViewById(R.id.item_course_subtitle);
            lockedImageView = (ImageView) itemView.findViewById(R.id.item_course_status);
            compltedImageView = (ImageView) itemView.findViewById(R.id.item_course_status2);
            mCourseNotesItem = (TextView) itemView.findViewById(R.id.item_course_notes);
            mCourseExerciseItem = (TextView) itemView.findViewById(R.id.item_course_exercise);
        }
    }
}
