package com.ucfo.youcaiwx.adapter.questionbank;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.questionbank.DoProblemsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-5-17.  上午 11:31
 * FileName: OptionsAdapter
 * Description:TODO 选择题适配器
 */
public class OptionsAdapter extends BaseAdapter {
    private ArrayList<DoProblemsBean.DataBean.TopicsBean> questionList;
    private Context context;
    private int index;
    private String EXERCISETYPE, userOptions = "";
    private final Drawable blue, gray, green, red;

    public OptionsAdapter(ArrayList<DoProblemsBean.DataBean.TopicsBean> questionList,
                          int index, Context context, String exerciseType) {
        this.questionList = questionList;
        this.context = context;
        this.index = index;
        this.EXERCISETYPE = exerciseType;

        blue = ContextCompat.getDrawable(context, R.drawable.shape_cricle_hollow_blue);
        gray = ContextCompat.getDrawable(context, R.drawable.shape_cricle_hollow_gray);
        green = ContextCompat.getDrawable(context, R.drawable.shape_cricle_hollow_green);
        red = ContextCompat.getDrawable(context, R.drawable.shape_cricle_hollow_red);
    }

    @Override
    public int getCount() {
        return questionList.get(index).getOptions().size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_questionbank_options, parent, false);
            holder = new ViewHolder();
            holder.tvOption = (CheckedTextView) convertView.findViewById(R.id.item_list_options);
            holder.tvOptionContent = (TextView) convertView.findViewById(R.id.item_list_optionscontent);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //获取选项内容
        List<DoProblemsBean.DataBean.TopicsBean.OptionsBean> optionsBeanList = questionList.get(index).getOptions();
        String option = optionsBeanList.get(position).getOption();//选项ABCD
        String topic = optionsBeanList.get(position).getTopic();//选项内容
        String rightAnswer = optionsBeanList.get(position).getRight();

        if (!TextUtils.isEmpty(option)) {
            holder.tvOption.setText(option);
        } else {
            holder.tvOption.setText(String.valueOf(position));
        }
        if (!TextUtils.isEmpty(topic)) {
            holder.tvOptionContent.setText(topic);
        } else {
            holder.tvOptionContent.setText(context.getResources().getString(R.string.holder_nodata));
        }


        if (EXERCISETYPE.equals(Constant.EXERCISE_E)) {//TODO 考试模式
            if (TextUtils.isEmpty(userOptions)) {//用户答案为空
                holder.tvOption.setBackground(gray);
                holder.tvOption.setTextColor(ContextCompat.getColor(context, R.color.color_666666));
            } else {//用户已选择答案
                if (option.equals(userOptions)) {//用户选择答案和选项符合变为蓝色
                    holder.tvOption.setBackground(blue);
                    holder.tvOption.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                } else {
                    holder.tvOption.setBackground(gray);
                    holder.tvOption.setTextColor(ContextCompat.getColor(context, R.color.color_666666));
                }
            }
        } else if (EXERCISETYPE.equals(Constant.EXERCISE_P) || EXERCISETYPE.equals(Constant.EXERCISE_A)) {//TODO 练习模式和解析模式一样
            if (TextUtils.isEmpty(userOptions)) {//用户答案为空
                holder.tvOption.setBackground(gray);
                holder.tvOption.setTextColor(ContextCompat.getColor(context, R.color.color_666666));
            } else {//用户已选择答案
                if (userOptions.equals(rightAnswer)) {//选对了   A  A
                    if (option.equals(rightAnswer)) {
                        holder.tvOption.setBackground(green);
                        holder.tvOption.setTextColor(ContextCompat.getColor(context, R.color.color_0AAB55));
                    } else {
                        holder.tvOption.setBackground(gray);
                        holder.tvOption.setTextColor(ContextCompat.getColor(context, R.color.color_666666));
                    }
                } else {//选错了
                    if (option.equals(userOptions)) {//标记用户答案为红色
                        holder.tvOption.setBackground(red);
                        holder.tvOption.setTextColor(ContextCompat.getColor(context, R.color.color_E84342));
                    } else if (option.equals(rightAnswer)) {//标记正确答案
                        holder.tvOption.setBackground(green);
                        holder.tvOption.setTextColor(ContextCompat.getColor(context, R.color.color_0AAB55));
                    } else {
                        holder.tvOption.setBackground(gray);
                        holder.tvOption.setTextColor(ContextCompat.getColor(context, R.color.color_666666));
                    }
                }
            }
        }
        return convertView;
    }

    public void notifyDataChanged(String userOptions) {
        this.userOptions = userOptions;
        notifyDataSetChanged();
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    protected static class ViewHolder {
        CheckedTextView tvOption;
        TextView tvOptionContent;
    }

}
