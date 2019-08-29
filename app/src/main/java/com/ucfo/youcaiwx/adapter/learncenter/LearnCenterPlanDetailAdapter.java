package com.ucfo.youcaiwx.adapter.learncenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.learncenter.LearncenterHomeBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.glideutils.GlideCircleTransform;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-18.  下午 3:42
 * FileName: LearnCenterPlanDetailAdapter
 * Description:TODO 学习计划--详情列表
 */
//                       _ooOoo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                       O\ = /O
//                   ____/`---'\____
//                 .   ' \\| |// `.
//                  / \\||| : |||// \
//                / _||||| -:- |||||- \
//                  | | \\\ - /// | |
//                | \_| ''\---/'' | |
//                 \ .-\__ `-` ___/-. /
//              ______`. .' /--.--\ `. . __
//           ."" '< `.___\_<|>_/___.' >'"".
//          | | : `- \`.;`\ _ /`;.`/ - ` : | |
//            \ \ `-. \_ __\ /__ _/ .-` / /
//    ======`-.____`-.___\_____/___.-`____.-'======
//                       `=---='
//             佛祖保佑             永无BUG
public class LearnCenterPlanDetailAdapter extends BaseAdapter<LearncenterHomeBean.DataBean.LearnListBean, LearnCenterPlanDetailAdapter.ViewHolder> {

    private List<LearncenterHomeBean.DataBean.LearnListBean> list;
    private Context context;
    private String userBeanHead;
    private final Drawable indicator_end;
    private final Drawable indicator_start;
    private final DisplayMetrics displayMetrics;

    public void setUserBeanHead(String userBeanHead) {
        this.userBeanHead = userBeanHead;
    }

    public LearnCenterPlanDetailAdapter(List<LearncenterHomeBean.DataBean.LearnListBean> list, Context context) {
        this.list = list;
        this.context = context;

        indicator_end = ContextCompat.getDrawable(context, R.mipmap.icon_lc_indicatorend);
        indicator_start = ContextCompat.getDrawable(context, R.mipmap.icon_lc_indicatorstart);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        displayMetrics = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(displayMetrics);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        LearncenterHomeBean.DataBean.LearnListBean bean = list.get(position);
        String planName = bean.getPlan_name();//标题
        int number = bean.getNumber();//距离考试时间
        String schedule = bean.getSchedule();//出勤天数
        int planDays = bean.getPlan_days();//计划总工天数
        int joinDays = bean.getJoin_days();//已开多少天
        String isOverdue = bean.getIs_overdue();
        int progress = joinDays * 100 / planDays;//2*100/4
        holder.mProgressbar.setProgress(progress);

        if (!TextUtils.isEmpty(planName)) {
            holder.mTitle.setText(planName);
        }
        holder.mStudyAlldayItem.setText(context.getResources().getString(R.string.learncenter_examTips2, String.valueOf(planDays)));
        holder.mStudydayItem.setText(context.getResources().getString(R.string.learncenter_examTips1, String.valueOf(joinDays)));
        holder.mExamdayItem.setText(String.valueOf(number));
        int i = position % 3;
        switch (i) {
            case 0://余数为0
                holder.mBackgroundItem.setBackground(ContextCompat.getDrawable(context, R.color.color_FFF8F2));
                break;
            case 1://余数为1
                holder.mBackgroundItem.setBackground(ContextCompat.getDrawable(context, R.color.color_F2F7FF));
                break;
            case 2://余数为2
                holder.mBackgroundItem.setBackground(ContextCompat.getDrawable(context, R.color.color_F9F6FF));
                break;
            default:
                break;
        }
        if (TextUtils.equals(isOverdue, String.valueOf(2))) {
            holder.mBackgroundItem.setBackground(ContextCompat.getDrawable(context, R.color.color_E6E6E6));
            holder.mLayoutFrame.setVisibility(View.GONE);
            holder.mTextSeekbar.setVisibility(View.GONE);
            holder.mPlanEndText.setVisibility(View.VISIBLE);
        } else {
            holder.mLayoutFrame.setVisibility(View.VISIBLE);
            holder.mTextSeekbar.setVisibility(View.VISIBLE);
            holder.mPlanEndText.setVisibility(View.GONE);
        }
        int progress2 = Integer.parseInt(schedule) * 100 / planDays;
        holder.mSeekbar.setProgress(progress2);
        holder.mTextSeekbar.setText(context.getResources().getString(R.string.progressbarIndetior, String.valueOf(schedule)));
        SimpleTarget<GlideDrawable> simpleTarget = new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
                int w = resource.getIntrinsicWidth();
                int h = resource.getIntrinsicHeight();
                Bitmap.Config config = resource.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
                Bitmap bitmap = Bitmap.createBitmap(w, h, config);
                Canvas canvas = new Canvas(bitmap);
                resource.setBounds(0, 0, w, h);
                resource.draw(canvas);
                int bitmapWidth = bitmap.getWidth();
                int bitmapHeight = bitmap.getHeight();
                Matrix matrix = new Matrix();
                float scaleWidth = ((float) DensityUtil.dip2px(context, 22) / bitmapWidth);
                float scaleHeight = ((float) DensityUtil.dip2px(context, 22) / bitmapHeight);
                matrix.postScale(scaleWidth, scaleHeight);
                Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
                BitmapDrawable drawable = new BitmapDrawable(context.getResources(), newbmp);
                holder.mSeekbar.setThumb(drawable);
                setSeekBarDistance(holder.mSeekbar, holder.mTextSeekbar);
            }
        };
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 22, context.getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 22, context.getResources().getDisplayMetrics());
        Glide.with(context).load(userBeanHead).centerCrop().placeholder(R.mipmap.icon_headdefault)
                .override(width, height)
                .transform(new GlideCircleTransform(context, 3, ContextCompat.getColor(context, R.color.color_FAA827)))
                .skipMemoryCache(false).priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(simpleTarget);
        holder.mSeekbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        holder.mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int thumbWidth = seekBar.getThumb().getBounds().width();
                int thumbPadding = thumbWidth / 2;
                seekBar.setPadding(thumbPadding, 0, thumbPadding, 0);
                float finalPostion = 0;
                float textWidth = DensityUtil.dp2px(82);
                int b = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, context.getResources().getDisplayMetrics());
                float seekBarWidth = displayMetrics.widthPixels - b - thumbWidth;
                float thumbhalfswidth = thumbWidth / 2;
                float average = seekBarWidth / seekBar.getMax();
                float residueAverage = (seekBar.getMax() - seekBar.getProgress()) * average;
                if (residueAverage < textWidth) {
                    holder.mTextSeekbar.setBackground(indicator_end);
                    finalPostion = average * progress + thumbhalfswidth - textWidth + thumbWidth;
                } else {
                    holder.mTextSeekbar.setBackground(indicator_start);
                    finalPostion = thumbhalfswidth + average * progress - DensityUtil.dp2px(5);
                }
                holder.mTextSeekbar.setX(finalPostion);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setSeekBarDistance(SeekBar seekBar, TextView textView) {
        int progress = seekBar.getProgress();
        int thumbWidth = seekBar.getThumb().getBounds().width();
        int thumbPadding = thumbWidth / 2;
        seekBar.setPadding(thumbPadding, 0, thumbPadding, 0);
        float finalPostion = 0;
        float textWidth = DensityUtil.dp2px(82);
        int b = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, context.getResources().getDisplayMetrics());
        float seekBarWidth = displayMetrics.widthPixels - b - thumbWidth;
        float thumbhalfswidth = thumbWidth / 2;
        float average = seekBarWidth / seekBar.getMax();
        float residueAverage = (seekBar.getMax() - seekBar.getProgress()) * average;
        if (residueAverage < textWidth) {
            textView.setBackground(indicator_end);
            finalPostion = average * progress + thumbhalfswidth - textWidth + thumbWidth;
        } else {
            textView.setBackground(indicator_start);
            finalPostion = thumbhalfswidth + average * progress - DensityUtil.dp2px(5);
        }
        textView.setX(finalPostion);
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_lc_plandetail, viewGroup, false);
        return new ViewHolder(inflate);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mExamdayItem;
        private TextView mStudydayItem;
        private TextView mStudyAlldayItem;
        private LinearLayout mBackgroundItem;
        private ProgressBar mProgressbar;
        private SeekBar mSeekbar;
        private TextView mTextSeekbar;
        private TextView mPlanEndText;
        private FrameLayout mLayoutFrame;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View view) {
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mExamdayItem = (TextView) itemView.findViewById(R.id.item_examday);
            mStudydayItem = (TextView) itemView.findViewById(R.id.item_studyday);
            mStudyAlldayItem = (TextView) itemView.findViewById(R.id.item_studyAllday);
            mBackgroundItem = (LinearLayout) itemView.findViewById(R.id.item_background);
            mProgressbar = (ProgressBar) itemView.findViewById(R.id.progressbar);
            mSeekbar = (SeekBar) itemView.findViewById(R.id.seekbar);
            mTextSeekbar = (TextView) itemView.findViewById(R.id.seekbar_text);
            mPlanEndText = (TextView) itemView.findViewById(R.id.text_plan_end);
            mLayoutFrame = (FrameLayout) itemView.findViewById(R.id.frame_layout);
        }
    }
}
