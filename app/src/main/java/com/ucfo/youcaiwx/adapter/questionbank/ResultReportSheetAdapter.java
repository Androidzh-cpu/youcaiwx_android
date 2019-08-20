package com.ucfo.youcaiwx.adapter.questionbank;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.questionbank.ResultReportBean;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-5-24.  下午 3:36
 * FileName: ResultReportSheetAdapter
 * Description:TODO 成绩统计做题数适配器
 */
public class ResultReportSheetAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ResultReportBean.DataBean.QuestionContentBean> list;
    private int gray = R.drawable.shape_cricle_hollow_gray;
    private int green = R.drawable.shape_cricle_solid_green;
    private int red = R.drawable.shape_cricle_solid_red;

    public ResultReportSheetAdapter(Context context, ArrayList<ResultReportBean.DataBean.QuestionContentBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
        textView.setText(String.valueOf(position + 1));

        String true_options = list.get(position).getTrue_options();
        String user_answer = list.get(position).getUser_answer();

        if (!TextUtils.isEmpty(user_answer)) {//用户已作答
            if (user_answer.equals(true_options)) {//做对了
                textView.setBackgroundResource(green);
                textView.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            } else {//做错了
                textView.setBackgroundResource(red);
                textView.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            }
        } else {//用户未作答
            textView.setBackgroundResource(gray);
            textView.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
        }

        return textView;
    }
}
