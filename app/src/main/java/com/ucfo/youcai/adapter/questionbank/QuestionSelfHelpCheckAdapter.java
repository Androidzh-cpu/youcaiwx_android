package com.ucfo.youcai.adapter.questionbank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ucfo.youcai.R;
import com.ucfo.youcai.utils.baseadapter.BaseAdapter;

import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-5-9.  下午 3:06
 * Package: com.ucfo.youcai.adapter.questionbank
 * FileName: QuestionSelfHelpCheckAdapter
 * Description:TODO  自主练习题目数量选择
 * Detail:TODO
 */
public class QuestionSelfHelpCheckAdapter extends BaseAdapter<Integer, QuestionSelfHelpCheckAdapter.ViewHolder> {
    private ArrayList<Integer> list;
    private Context context;


    private int selectPosition = -1;

    public QuestionSelfHelpCheckAdapter(ArrayList<Integer> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setSelectPosition(int position) {
        this.selectPosition = position;
    }
    public int getSelectPosition() {
        return selectPosition;
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        int intValue = list.get(position).intValue();
        String string = String.valueOf(intValue);
        holder.mQuestionTitleItem.setText(string);

        if (position == selectPosition) {
            holder.mQuestionTitleItem.setBackgroundResource(R.drawable.shape_selfhelp_check);
            holder.mQuestionTitleItem.setTextColor(ContextCompat.getColor(context,R.color.colorWhite));
        } else {
            holder.mQuestionTitleItem.setBackgroundResource(R.drawable.shape_selfhelp_uncheck);
            holder.mQuestionTitleItem.setTextColor(ContextCompat.getColor(context,R.color.color_666666));
        }

    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_selfhelp_text, viewGroup, false);
        QuestionSelfHelpCheckAdapter.ViewHolder holder = new QuestionSelfHelpCheckAdapter.ViewHolder(inflate);
        return holder;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mQuestionTitleItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mQuestionTitleItem = (TextView) itemView.findViewById(R.id.item_list_title);
        }

    }

}
