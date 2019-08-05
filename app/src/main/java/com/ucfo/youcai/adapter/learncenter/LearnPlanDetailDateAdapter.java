package com.ucfo.youcai.adapter.learncenter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.ucfo.youcai.R;
import com.ucfo.youcai.entity.learncenter.LearnPlanDetailBean;
import com.ucfo.youcai.utils.baseadapter.OnItemClickListener;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-22.  上午 11:25
 * Package: com.ucfo.youcai.adapter.learncenter
 * FileName: LearnPlanDetailDateAdapter
 * Description:TODO  学习计划详情适配器
 */
public class LearnPlanDetailDateAdapter extends RecyclerView.Adapter<LearnPlanDetailDateAdapter.ViewHolder> {
    private DisplayMetrics display;
    private Context context;
    private List<LearnPlanDetailBean.DataBean.DateBean> list;
    private final Drawable blue;
    private final Drawable solidBlue;
    public static final int UPDATE_STATE = 101;
    public static final int UPDATE_NAME = 102;

    private OnItemClickListener onItemClickListener;

    public void setItemClick(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public LearnPlanDetailDateAdapter(Context context, List<LearnPlanDetailBean.DataBean.DateBean> list) {
        this.context = context;
        this.list = list;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            display = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(display);
        }


        blue = ContextCompat.getDrawable(context, R.drawable.shape_cricle_hollow_blue);
        solidBlue = ContextCompat.getDrawable(context, R.drawable.shape_cricle_solid_blue);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.width = display.widthPixels / 7;
        holder.itemView.setLayoutParams(layoutParams);

        LearnPlanDetailBean.DataBean.DateBean bean = list.get(position);
        String week = bean.getWeek();
        String day = bean.getDay();
        int is_rest = bean.getIs_rest();//TODO 1:休息日,无计划  2:计划日期
        int sameday = bean.getSameday();//TODO 是否是今天
        boolean checked = bean.isChecked();

        if (!TextUtils.isEmpty(week)) {
            int anInt = Integer.parseInt(week);
            switch (anInt) {
                case 0:
                    holder.mWeekItem.setText("日");
                    break;
                case 1:
                    holder.mWeekItem.setText("一");
                    break;
                case 2:
                    holder.mWeekItem.setText("二");
                    break;
                case 3:
                    holder.mWeekItem.setText("三");
                    break;
                case 4:
                    holder.mWeekItem.setText("四");
                    break;
                case 5:
                    holder.mWeekItem.setText("五");
                    break;
                case 6:
                    holder.mWeekItem.setText("六");
                    break;
            }
        }
        if (!TextUtils.isEmpty(day)) {
            holder.mDayItem.setText(day);
        }
        switch (is_rest) {
            case 1://计划日期
                holder.mWeekItem.setTextColor(Color.parseColor("#4A4A4A"));
                holder.mDayItem.setTextColor(Color.parseColor("#4A4A4A"));
                break;
            case 2:
            default:
                holder.mWeekItem.setTextColor(Color.parseColor("#DCDCDC"));
                holder.mDayItem.setTextColor(Color.parseColor("#DCDCDC"));
                break;
        }
        if (checked) {
            holder.mDayItem.setBackground(solidBlue);
            holder.mDayItem.setTextColor(Color.WHITE);
        } else {
            if (sameday == 1) {
                holder.mDayItem.setBackground(blue);
                holder.mDayItem.setTextColor(Color.parseColor("#4A4A4A"));
            } else {
                holder.mDayItem.setBackground(ContextCompat.getDrawable(context,R.color.transparency));
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, position);
            }
        });

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        //list为空时，必须调用两个参数的onBindViewHolder(@NonNull LabelHolder holder, int position)
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else if (payloads.get(0) instanceof Integer) {
            int payLoad = (int) payloads.get(0);
            switch (payLoad) {
                case UPDATE_STATE:
                    break;
                case UPDATE_NAME:
                    break;
                default:
                    break;
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_learnplandetail_date, viewGroup, false);
        return new ViewHolder(inflate);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mWeekItem;
        private TextView mDayItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mWeekItem = (TextView) itemView.findViewById(R.id.item_week);
            mDayItem = (TextView) itemView.findViewById(R.id.item_day);
        }
    }

}
