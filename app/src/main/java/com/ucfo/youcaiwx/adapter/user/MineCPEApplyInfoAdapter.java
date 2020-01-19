package com.ucfo.youcaiwx.adapter.user;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.user.CPEApplyInfoBean;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.util.List;

/**
 * Author: AND
 * Time: 2020-1-3.  上午 9:27
 * Package: com.ucfo.youcaiwx.adapter.user
 * FileName: MineCPEApplyInfoAdapter
 * Description:TODO 测评证明信息申请
 */
public class MineCPEApplyInfoAdapter extends BaseExpandableListAdapter {
    private List<CPEApplyInfoBean.DataBean> list;
    private Context context;

    public MineCPEApplyInfoAdapter(List<CPEApplyInfoBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void notifychange(List<CPEApplyInfoBean.DataBean> list) {
        this.list = list;

        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getList() == null ? 0 : list.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = null;
        GroupHolder groupholder = null;
        if (convertView != null) {
            view = convertView;
            groupholder = (GroupHolder) view.getTag();
        } else {
            if (view == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cpeapplyfor_group, parent, false);
            }
            groupholder = new GroupHolder();
            groupholder.txt_name = (TextView) view.findViewById(R.id.txt_name);
            groupholder.checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            view.setTag(groupholder);
        }

        String time = list.get(groupPosition).getTime();
        boolean checked = list.get(groupPosition).getChecked();
        groupholder.checkBox.setChecked(checked);
        if (!TextUtils.isEmpty(time)) {
            String text = "";
            if (context != null) {
                text = context.getResources().getString(R.string.year, time);
            }
            groupholder.txt_name.setText(text);
        }
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = null;
        ChildHolder childholder = null;
        if (convertView != null) {
            view = convertView;
            childholder = (ChildHolder) view.getTag();
        } else {
            if (view == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cpeapplyfor_child, parent, false);
            }
            childholder = new ChildHolder();
            childholder.checkbox = (CheckBox) view.findViewById(R.id.checkbox);
            childholder.imageView = (ImageView) view.findViewById(R.id.image);
            childholder.txt_name = (TextView) view.findViewById(R.id.txt_name);
            childholder.txt_teacher = (TextView) view.findViewById(R.id.txt_teacher);
            childholder.txt_point = (TextView) view.findViewById(R.id.txt_point);
            childholder.txt_time = (TextView) view.findViewById(R.id.txt_time);
            view.setTag(childholder);
        }
        if (list.get(groupPosition).getList() != null && list.get(groupPosition).getList().size() > 0) {
            CPEApplyInfoBean.DataBean.ListBean bean = list.get(groupPosition).getList().get(childPosition);

            String name = bean.getName();
            String appImg = bean.getApp_img();
            String teacherName = bean.getTeacher_name();
            String endtime = bean.getEnd_time();
            String cpeIntegral = bean.getCpe_integral();
            boolean checked = bean.getChecked();
            childholder.checkbox.setChecked(checked);


            if (!TextUtils.isEmpty(name)) {
                childholder.txt_name.setText(name);
            }
            if (!TextUtils.isEmpty(teacherName)) {
                childholder.txt_teacher.setText(teacherName);
            }
            if (!TextUtils.isEmpty(endtime)) {
                childholder.txt_time.setText(context.getResources().getString(R.string.complted_time, endtime));
            }
            if (!TextUtils.isEmpty(cpeIntegral)) {
                childholder.txt_point.setText(context.getResources().getString(R.string.event_point, cpeIntegral));
            }
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.icon_default)
                    .error(R.mipmap.icon_default)
                    .transform(new CenterCrop(), new RoundedCorners(DensityUtil.dp2px(5)))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            GlideUtils.load(context, appImg, childholder.imageView, requestOptions);
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        //设置为false子类点击事件不响应
        return true;
    }

    protected static class GroupHolder {
        TextView txt_name;
        CheckBox checkBox;
    }

    protected static class ChildHolder {
        TextView txt_name;
        TextView txt_time;
        TextView txt_teacher;
        TextView txt_point;
        ImageView imageView;
        CheckBox checkbox;

    }

}
