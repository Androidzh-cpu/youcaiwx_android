package com.ucfo.youcai.adapter.questionbank;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.ucfo.youcai.R;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.questionbank.DoProblemsAnswerBean;
import com.ucfo.youcai.utils.systemutils.DensityUtil;

import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-5-20.  下午 7:03
 * Package: com.ucfo.youcai.adapter.questionbank
 * FileName: QuestionAnswerSheetAdapter
 * Description:TODO  答题卡适配器
 */
public class QuestionAnswerSheetAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DoProblemsAnswerBean> optionsAnswerList;
    private String EXERCISE_TYPE;

    private final Drawable blue, gray, green, red;

    public QuestionAnswerSheetAdapter(Context context, ArrayList<DoProblemsAnswerBean> list, String exercise_type) {
        this.context = context;
        this.optionsAnswerList = list;
        this.EXERCISE_TYPE = exercise_type;

        blue = ContextCompat.getDrawable(context, R.drawable.shape_cricle_solid_blue);
        gray = ContextCompat.getDrawable(context, R.drawable.shape_cricle_hollow_gray);
        green = ContextCompat.getDrawable(context, R.drawable.shape_cricle_solid_green);
        red = ContextCompat.getDrawable(context, R.drawable.shape_cricle_solid_red);
    }

    @Override
    public int getCount() {
        return optionsAnswerList.size();
    }

    @Override
    public Object getItem(int position) {
        return optionsAnswerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new GridView.LayoutParams(DensityUtil.dip2px(context, 33), DensityUtil.dip2px(context, 33)));
        textView.setText(String.valueOf(optionsAnswerList.get(position).getPosition() + 1));

        if (EXERCISE_TYPE.equals(Constant.EXERCISE_E) || EXERCISE_TYPE.equals(Constant.EXERCISE_D)) {//TODO 正常考试模式和论述题模式一样
            String userAnswer = optionsAnswerList.get(position).getUser_answer();
            if (TextUtils.isEmpty(userAnswer)) {//用户答案为空
                textView.setBackground(gray);
                textView.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
            } else {
                textView.setBackground(blue);
                textView.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            }
        } else if (EXERCISE_TYPE.equals(Constant.EXERCISE_P) || EXERCISE_TYPE.equals(Constant.EXERCISE_A)) {//TODO 练习模式和解析模式一样
            String userAnswer = optionsAnswerList.get(position).getUser_answer();
            String rightAnswer = optionsAnswerList.get(position).getTrue_options();
            if (TextUtils.isEmpty(userAnswer)) {//用户答案为空
                textView.setBackground(gray);
                textView.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
            } else {//用户已填写答案,接下来判断对错
                if (userAnswer.equals(rightAnswer)) {//做对显示绿色
                    textView.setBackground(green);
                    textView.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
                } else {//做错显示红色
                    textView.setBackground(red);
                    textView.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
                }
            }
        }
        return textView;
    }
}
