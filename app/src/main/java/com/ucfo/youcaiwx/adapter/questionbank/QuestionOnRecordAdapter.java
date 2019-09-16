package com.ucfo.youcaiwx.adapter.questionbank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.questionbank.QuestionOnRecordBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;

import java.util.List;

/**
 * Author:29117
 * Time: 2019-4-30.  上午 10:42
 * Email:2911743255@qq.com
 * ClassName: QuestionOnRecordAdapter  答题记录适配器
 */
public class QuestionOnRecordAdapter extends BaseAdapter<QuestionOnRecordBean.DataBean, QuestionOnRecordAdapter.ViewHolder> {
    private Context context;
    private List<QuestionOnRecordBean.DataBean> list;
    private OnItemClickListener onItemClickListener;

    public QuestionOnRecordAdapter(Context context, List<QuestionOnRecordBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        QuestionOnRecordBean.DataBean dataBean = list.get(position);
        String create_times = dataBean.getCreate_times();//创建时间
        int state = dataBean.getState();//答题记录操作状态
        String paper_name = dataBean.getPaper_name();//名称

        if (!TextUtils.isEmpty(create_times)) {
            holder.mQuestionrecordTimeItem.setText(create_times);
        }
        if (!TextUtils.isEmpty(paper_name)) {
            holder.mQuestionrecordTitleItem.setText(paper_name);
        }
        switch (state) {
            case 1://TODO  1成绩统计
                holder.mQuestionrecordStateItem.setBackgroundResource(R.drawable.item_questionrecord_orange);
                holder.mQuestionrecordStateItem.setTextColor(ContextCompat.getColor(context, R.color.color_F99111));
                holder.mQuestionrecordStateItem.setText(context.getResources().getString(R.string.question_title_ResultsStatistical));
                break;
            case 2://TODO 2继续做题
                holder.mQuestionrecordStateItem.setBackgroundResource(R.drawable.item_questionrecord_blue);
                holder.mQuestionrecordStateItem.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                holder.mQuestionrecordStateItem.setText(context.getResources().getString(R.string.question_tips_holder6));

                break;
            case 3://TODO  3查看试题
                holder.mQuestionrecordStateItem.setBackgroundResource(R.drawable.item_questionrecord_green);
                holder.mQuestionrecordStateItem.setTextColor(ContextCompat.getColor(context, R.color.color_0AAB55));
                holder.mQuestionrecordStateItem.setText(context.getResources().getString(R.string.question_tips_holder7));
                break;
            default:
                break;
        }
        //调用接口,模拟点击
        holder.mQuestionrecordStateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用接口里的点击方法,传入布局和索引
                onItemClickListener.onItemClick(holder.itemView, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_questionbankonrecord, viewGroup, false);
        return new ViewHolder(inflate);
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    //定义一个公共方法,当外部类调用时可以通过此方法点击item
    public void setItemClick(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mQuestionrecordTitleItem;
        private TextView mQuestionrecordTimeItem;
        private Button mQuestionrecordStateItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {

            mQuestionrecordTitleItem = (TextView) itemView.findViewById(R.id.item_questionrecord_title);
            mQuestionrecordTimeItem = (TextView) itemView.findViewById(R.id.item_questionrecord_time);
            mQuestionrecordStateItem = (Button) itemView.findViewById(R.id.item_questionrecord_state);
        }

    }
}
