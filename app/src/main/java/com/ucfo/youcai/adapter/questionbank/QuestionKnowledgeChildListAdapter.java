package com.ucfo.youcai.adapter.questionbank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ucfo.youcai.R;
import com.ucfo.youcai.entity.questionbank.QuestionKnowLedgeChildListBean;

import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-5-8.  下午 3:31
 * Package: com.ucfo.youcai.adapter.questionbank
 * FileName: QuestionKnowledgeChildListAdapter
 * Description:TODO 知识点练习三姐列表
 */
public class QuestionKnowledgeChildListAdapter extends BaseAdapter {
    private int selectedPosition = -1;
    private ArrayList<QuestionKnowLedgeChildListBean.DataBean> list;
    private Context context;

    public QuestionKnowledgeChildListAdapter(ArrayList<QuestionKnowLedgeChildListBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_knowledge_listchild, parent, false);
            holder = new ViewHolder();
            holder.tv_RadioBtn = (RadioButton) convertView.findViewById(R.id.item_list_radiobtn);
            holder.tv_Title = (TextView) convertView.findViewById(R.id.item_list_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_Title.setText(list.get(position).getKnow_name());

        //被选中和未被选中的显示样式
        if (selectedPosition == position) {
            if (!holder.tv_RadioBtn.isChecked()) {
                holder.tv_RadioBtn.setChecked(true);
            }
        } else {
            if (holder.tv_RadioBtn.isChecked()) {
                holder.tv_RadioBtn.setChecked(false);
            }
        }

        return convertView;
    }

    public void selectedItemPosition(int position) {
        this.selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    protected static class ViewHolder {
        RadioButton tv_RadioBtn;
        TextView tv_Title;
    }
}
