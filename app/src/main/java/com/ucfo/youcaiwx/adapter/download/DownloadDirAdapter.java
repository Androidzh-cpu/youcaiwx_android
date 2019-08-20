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
import com.ucfo.youcaiwx.entity.course.CourseDirBean;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-3.  上午 9:47
 * FileName: DownloadDirAdapter
 * Description:TODO 下载列表
 */
public class DownloadDirAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<CourseDirBean.DataBean.SectionBean> list;

    public DownloadDirAdapter(Context context, List<CourseDirBean.DataBean.SectionBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getVideo() == null ? 0 : list.get(groupPosition).getVideo().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getVideo().get(childPosition);
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
        String section_name = list.get(groupPosition).getSection_name();
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
        //binddata
        if (list.get(groupPosition).getVideo() != null && list.get(groupPosition).getVideo().size() > 0) {
            CourseDirBean.DataBean.SectionBean.VideoBean videoBean = list.get(groupPosition).getVideo().get(childPosition);
            if (videoBean != null) {
                if (!TextUtils.isEmpty(videoBean.getVideo_name())) {
                    childholder.course_sectionname.setText(videoBean.getVideo_name());
                }
                if (!TextUtils.isEmpty(videoBean.getVideo_time())) {
                    childholder.course_titme.setText(videoBean.getVideo_time());
                }
                childholder.course_sectionname.setTextColor(ContextCompat.getColor(context, R.color.color_666666));
                childholder.course_titme.setTextColor(ContextCompat.getColor(context, R.color.color_999999));
                childholder.checkbox.setChecked(videoBean.getChecked());
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
