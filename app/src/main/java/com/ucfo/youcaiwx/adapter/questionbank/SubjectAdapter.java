package com.ucfo.youcaiwx.adapter.questionbank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.questionbank.QuestionMyProjectBean;

import java.util.ArrayList;

/**
 * Author:29117
 * Time: 2019-4-26.  下午 3:23
 * Email:2911743255@qq.com
 * ClassName: SubjectAdapter
 * Description:TODO 已购买的题库适配器
 */
public class SubjectAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<QuestionMyProjectBean.DataBean> list;

    public SubjectAdapter(Context context, ArrayList<QuestionMyProjectBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subjectlist, parent, false);
            holder = new ViewHolder();
            holder.tv_relation = (TextView) convertView.findViewById(R.id.item_subject);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_relation.setText(list.get(position).getName());
        return convertView;
    }

    static class ViewHolder {
        TextView tv_relation;
    }
}
