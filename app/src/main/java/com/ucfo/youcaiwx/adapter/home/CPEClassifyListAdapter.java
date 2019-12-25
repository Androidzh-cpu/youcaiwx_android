package com.ucfo.youcaiwx.adapter.home;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.home.education.EducationTypeBean;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-12-24.  下午 4:43
 * Package: com.ucfo.youcaiwx.adapter.home
 * FileName: CPEClassifyListAdapter
 * Description:TODO 分类适配器
 */
public class CPEClassifyListAdapter extends BaseAdapter {
    private List<EducationTypeBean.DataBean.TypeBean> list;
    private Context context;

    public CPEClassifyListAdapter(List<EducationTypeBean.DataBean.TypeBean> data, Context context) {
        this.list = data;
        this.context = context;
    }

    public void notifyChange(List<EducationTypeBean.DataBean.TypeBean> dataBeanList) {
        this.list = dataBeanList;

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public EducationTypeBean.DataBean.TypeBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @param parent ListView对象
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.popuwindow_cpe_listitem, null);
            holder = new ViewHolder();
            holder.tv_relation = (TextView) convertView.findViewById(R.id.tv_relation);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String typeName = list.get(position).getType_name();
        boolean checked = list.get(position).getChecked();
        if (checked) {
            holder.tv_relation.setTextColor(ContextCompat.getColor(context, R.color.color_0267FF));
        } else {
            holder.tv_relation.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
        }
        if (!TextUtils.isEmpty(typeName)) {
            holder.tv_relation.setText(typeName);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tv_relation;
    }

}
