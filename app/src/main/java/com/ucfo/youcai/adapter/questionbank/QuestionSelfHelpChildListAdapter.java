package com.ucfo.youcai.adapter.questionbank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ucfo.youcai.R;
import com.ucfo.youcai.entity.questionbank.QuestionKnowLedgeChildListBean;
import com.ucfo.youcai.utils.toastutils.ToastUtil;

import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-5-9.  下午 6:23
 * Package: com.ucfo.youcai.adapter.questionbank
 * FileName: QuestionSelfHelpChildListAdapter
 * Description:TODO 自助练习三级列表  知识点最多选择3个
 * Detail:TODO
 */
public class QuestionSelfHelpChildListAdapter extends BaseAdapter {

    private ArrayList<QuestionKnowLedgeChildListBean.DataBean> list;
    private Context context;
    private ViewHolder holder;

    public QuestionSelfHelpChildListAdapter(ArrayList<QuestionKnowLedgeChildListBean.DataBean> list, Context context) {
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

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_knowledge_listchild, parent, false);
            holder = new ViewHolder();
            holder.tv_RadioBtn = (RadioButton) convertView.findViewById(R.id.item_list_radiobtn);
            holder.tv_Checkbox = (CheckBox) convertView.findViewById(R.id.item_list_checkbox);
            holder.tv_Title = (TextView) convertView.findViewById(R.id.item_list_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_RadioBtn.setVisibility(View.GONE);
        holder.tv_Checkbox.setVisibility(View.VISIBLE);

        holder.tv_Title.setText(list.get(position).getKnow_name());

        holder.tv_Checkbox.setChecked(list.get(position).getChecked());

        holder.tv_Checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    list.get(position).setChecked(true);
                } else {
                    list.get(position).setChecked(false);
                }
            }
        });
        holder.tv_Checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum = 0;
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j).getChecked()) {
                        sum++;
                    }
                }
                if (sum > 3) {
                    list.get(position).setChecked(false);
                    notifyDataSetChanged();
                    ToastUtil.showBottomShortText(context, "最多选择三个知识点");
                }
            }
        });
        return convertView;
    }
    protected static class ViewHolder {
        RadioButton tv_RadioBtn;
        CheckBox tv_Checkbox;
        TextView tv_Title;
    }

}
