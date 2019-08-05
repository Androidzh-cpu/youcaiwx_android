package com.ucfo.youcai.adapter.learncenter;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.ucfo.youcai.R;
import com.ucfo.youcai.entity.learncenter.LearnPlanDetailVideoBean;
import com.ucfo.youcai.utils.baseadapter.BaseAdapter;
import com.ucfo.youcai.utils.glideutils.GlideRoundTransform;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-22.  下午 2:41
 * Package: com.ucfo.youcai.adapter.learncenter
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
        String coverURL = bean.getCoverURL();
        String video_name = bean.getVideo_name();
        String video_time = bean.getVideo_time();
        if (!TextUtils.isEmpty(video_name)) {
            holder.mCourseTitleItem.setText(video_name);
        }
        if (!TextUtils.isEmpty(video_time)) {
            holder.mCourseSubtitleItem.setText(video_time);
        }
        Glide.with(context)
                .load(coverURL)
                .asBitmap()
                .placeholder(R.mipmap.banner_default)
                .transform(new CenterCrop(context), new GlideRoundTransform(context, 4))
                .error(R.mipmap.image_loaderror)
                .dontAnimate()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .into(holder.mCourseImageItem);

        String sameday = bean.getSameday();
        if (sameday.equals("1")) {//当天计划
            holder.mCourseTitleItem.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
            holder.mCourseSubtitleItem.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
            holder.mCourseNotesItem.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
            holder.mCourseExerciseItem.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
            int is_watch = bean.getIs_watch();
            if (is_watch == 1) {//已完成计划
                holder.mCourse2StatusItem.setVisibility(View.VISIBLE);
            } else {//未完成计划
                holder.mCourseStatusItem.setVisibility(View.GONE);
            }
        } else {
            holder.mCourseTitleItem.setTextColor(ContextCompat.getColor(context, R.color.color_C7C7C7));
            holder.mCourseSubtitleItem.setTextColor(ContextCompat.getColor(context, R.color.color_C7C7C7));
            holder.mCourseNotesItem.setTextColor(ContextCompat.getColor(context, R.color.color_C7C7C7));
            holder.mCourseExerciseItem.setTextColor(ContextCompat.getColor(context, R.color.color_C7C7C7));

            holder.mCourseStatusItem.setVisibility(View.VISIBLE);
            holder.mCourse2StatusItem.setVisibility(View.GONE);
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
        private ImageView mCourseStatusItem, mCourse2StatusItem;
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
            mCourseStatusItem = (ImageView) itemView.findViewById(R.id.item_course_status);
            mCourse2StatusItem = (ImageView) itemView.findViewById(R.id.item_course_status2);
            mCourseNotesItem = (TextView) itemView.findViewById(R.id.item_course_notes);
            mCourseExerciseItem = (TextView) itemView.findViewById(R.id.item_course_exercise);
        }
    }
}
