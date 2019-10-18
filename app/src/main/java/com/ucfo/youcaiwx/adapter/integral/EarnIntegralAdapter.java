package com.ucfo.youcaiwx.adapter.integral;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.integral.EarnIntegralBean;
import com.ucfo.youcaiwx.utils.baseadapter.OnItemClickListener;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-10-15.  下午 5:14
 * Package: com.ucfo.youcaiwx.adapter.integral
 * FileName: EarnIntegralAdapter
 * Description:TODO 赚取积分
 */
public class EarnIntegralAdapter extends RecyclerView.Adapter<EarnIntegralAdapter.ViewHolder> {
    //TODO 1.日常任务  2.新手任务
    private int type;
    private Context context;
    private List<EarnIntegralBean.DataBean.DailyBean> dailyBeanList;
    private List<EarnIntegralBean.DataBean.NoviceBean> noviceBeanList;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public EarnIntegralAdapter(int type, Context context, List<EarnIntegralBean.DataBean.DailyBean> dailyBeanList, List<EarnIntegralBean.DataBean.NoviceBean> noviceBeanList) {
        this.type = type;
        this.context = context;
        this.dailyBeanList = dailyBeanList;
        this.noviceBeanList = noviceBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_integral_earn, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (type == 1) {
            //日常任务
            holder.mNoviceStateItem.setVisibility(View.GONE);
            holder.mDailyLinearItem.setVisibility(View.VISIBLE);

            EarnIntegralBean.DataBean.DailyBean dailyBean = dailyBeanList.get(position);
            String count = dailyBean.getCount();
            String name = dailyBean.getName();
            String number = dailyBean.getNumber();
            String score = dailyBean.getScore();
            if (!TextUtils.isEmpty(name)) {
                holder.mTitleItem.setText(name);
            }
            if (!TextUtils.isEmpty(score)) {
                holder.mSubtitleItem.setText(context.getResources().getString(R.string.integral_point2, score));
            }
            if (!TextUtils.isEmpty(count)) {
                holder.mTotalCountItem.setText(count);
            }
            if (!TextUtils.isEmpty(number)) {
                holder.mCountItem.setText(number);
            }
        } else {
            //新手任务
            holder.mNoviceStateItem.setVisibility(View.VISIBLE);
            holder.mDailyLinearItem.setVisibility(View.GONE);

            EarnIntegralBean.DataBean.NoviceBean noviceBean = noviceBeanList.get(position);
            String count = noviceBean.getCount();
            String name = noviceBean.getName();
            String number = noviceBean.getNumber();
            String score = noviceBean.getScore();
            int id = noviceBean.getId();

            if (!TextUtils.isEmpty(name)) {
                holder.mTitleItem.setText(name);
            }
            if (!TextUtils.isEmpty(score)) {
                holder.mSubtitleItem.setText(context.getResources().getString(R.string.integral_point2, score));
            }

            switch (id) {
                case 5:
                    //编辑昵称
                    if (TextUtils.equals(count, number)) {
                        holder.mNoviceStateItem.setTextColor(ContextCompat.getColor(context, R.color.color_999999));
                        holder.mNoviceStateItem.setText(context.getResources().getString(R.string.integral_gotocompleted));
                    } else {
                        holder.mNoviceStateItem.setTextColor(ContextCompat.getColor(context, R.color.color_0267FF));
                        holder.mNoviceStateItem.setText(context.getResources().getString(R.string.integral_gotoUpdate));
                    }
                    break;
                case 6:
                    //编辑头像
                    if (TextUtils.equals(count, number)) {
                        holder.mNoviceStateItem.setTextColor(ContextCompat.getColor(context, R.color.color_999999));
                        holder.mNoviceStateItem.setText(context.getResources().getString(R.string.integral_gotocompleted));
                    } else {
                        holder.mNoviceStateItem.setTextColor(ContextCompat.getColor(context, R.color.color_0267FF));
                        holder.mNoviceStateItem.setText(context.getResources().getString(R.string.integral_gotoupload));
                    }
                    break;
                case 7:
                    //关注公众号
                    if (TextUtils.equals(count, number)) {
                        holder.mNoviceStateItem.setTextColor(ContextCompat.getColor(context, R.color.color_999999));
                        holder.mNoviceStateItem.setText(context.getResources().getString(R.string.integral_gotocompleted));
                    } else {
                        holder.mNoviceStateItem.setTextColor(ContextCompat.getColor(context, R.color.color_0267FF));
                        holder.mNoviceStateItem.setText(context.getResources().getString(R.string.integral_gotostar));
                    }
                    break;
                default:
                    break;
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (type == 1) {
            return dailyBeanList.size();
        } else {
            return noviceBeanList.size();
        }
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {


        private TextView mTitleItem;
        private TextView mSubtitleItem;
        private TextView mCountItem;
        private TextView mTotalCountItem;
        private LinearLayout mDailyLinearItem;
        private TextView mNoviceStateItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mTitleItem = (TextView) itemView.findViewById(R.id.item_title);
            mSubtitleItem = (TextView) itemView.findViewById(R.id.item_subtitle);
            mCountItem = (TextView) itemView.findViewById(R.id.item_count);
            mTotalCountItem = (TextView) itemView.findViewById(R.id.item_totalCount);
            mDailyLinearItem = (LinearLayout) itemView.findViewById(R.id.item_daily_linear);
            mNoviceStateItem = (TextView) itemView.findViewById(R.id.item_novice_state);
        }
    }
}
