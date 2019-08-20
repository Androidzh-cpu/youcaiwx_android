package com.ucfo.youcaiwx.adapter.download;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.download.DownloadCompletedDirBean;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-10.  下午 1:13
 * FileName: DownloadComletedDirAdapter
 * Description:TODO 下载完成章节列表
 */
public class DownloadComletedDirAdapter extends BaseExpandableListAdapter {
    private List<DownloadCompletedDirBean.DataBean> list;
    private Context context;
    private boolean isEditing;

    public DownloadComletedDirAdapter(List<DownloadCompletedDirBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void notifychange(boolean Editing) {
        this.isEditing = Editing;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getSon() == null ? 0 : list.get(groupPosition).getSon().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getSon().get(childPosition);
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
        return true;
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
                view = LayoutInflater.from(context).inflate(R.layout.item_coursedir_group, parent, false);
            }
            groupholder = new GroupHolder();
            groupholder.course_title = (TextView) view.findViewById(R.id.item_course_sectionname);
            groupholder.course_groupbtn = (ImageView) view.findViewById(R.id.item_course_groupbtn);
            view.setTag(groupholder);
        }
        //判断是否已经打开列表
        if (isExpanded) {//已打开
            groupholder.course_groupbtn.setImageResource(R.mipmap.icon_top_arrows);
        } else {//关闭状态
            groupholder.course_groupbtn.setImageResource(R.mipmap.icon_bottom_arrows);
        }
        String section_name = list.get(groupPosition).getSectionName();
        if (!TextUtils.isEmpty(section_name)) {
            groupholder.course_title.setText(section_name);
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
                view = LayoutInflater.from(context).inflate(R.layout.item_downloaddir_child, parent, false);
            }
            childholder = new ChildHolder();
            childholder.course_sectionname = (TextView) view.findViewById(R.id.item_course_sectionname);
            childholder.course_titme = (TextView) view.findViewById(R.id.item_course_sectiontime);
            childholder.checkbox = (CheckBox) view.findViewById(R.id.checkbox);
            childholder.list_item = (LinearLayout) view.findViewById(R.id.list_item);
            view.setTag(childholder);
        }
        if (list.get(groupPosition).getSon() != null && list.get(groupPosition).getSon().size() > 0) {
            DownloadCompletedDirBean.DataBean.SonBean videoBean = list.get(groupPosition).getSon().get(childPosition);
            if (!TextUtils.isEmpty(videoBean.getVideoName())) {
                childholder.course_sectionname.setText(videoBean.getVideoName());
            }
            if (!TextUtils.isEmpty(videoBean.getVideoDuration())) {
                childholder.course_titme.setText(videoBean.getVideoDuration());
            }
            childholder.course_sectionname.setTextColor(ContextCompat.getColor(context,R.color.color_666666));
            childholder.course_sectionname.setTextColor(ContextCompat.getColor(context,R.color.color_999999));
            childholder.checkbox.setChecked(videoBean.isChecked());

            if (isEditing) {
                childholder.checkbox.setVisibility(View.VISIBLE);
            } else {
                childholder.checkbox.setVisibility(View.GONE);
            }
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;//设置为false子类点击事件不响应
    }


    protected static class GroupHolder {
        TextView course_title;
        ImageView course_groupbtn;
    }

    protected static class ChildHolder {
        TextView course_titme;
        TextView course_sectionname;
        CheckBox checkbox;
        LinearLayout list_item;
    }

}
