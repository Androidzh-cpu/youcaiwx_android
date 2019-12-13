package com.ucfo.youcaiwx.adapter.user;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.user.MineCourseCollectionDirBean;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-6-20.  上午 11:52
 * FileName: MineCourseCollectionDirAdapter
 * Description:TODO 我的收藏课程播放目录
 */
public class MineCourseCollectionDirAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<MineCourseCollectionDirBean.DataBean.SectionBean> list;
    private int groupIndex = -1, sonIndex = -1;

    public MineCourseCollectionDirAdapter(Context context, List<MineCourseCollectionDirBean.DataBean.SectionBean> list) {
        this.context = context;
        this.list = list;
    }

    public void notifyChange(List<MineCourseCollectionDirBean.DataBean.SectionBean> dataBeanList) {
        this.list = dataBeanList;

        notifyDataSetChanged();
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
            //view = LayoutInflater.from(context).inflate(R.layout.item_coursedir_group, parent, false);
            groupholder = new GroupHolder();
            groupholder.course_title = (TextView) view.findViewById(R.id.item_course_sectionname);
            groupholder.course_groupbtn = (ImageView) view.findViewById(R.id.item_course_groupbtn);
            view.setTag(groupholder);
        }
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
                view = LayoutInflater.from(context).inflate(R.layout.item_coursedir_child, parent, false);
            }
            //view = LayoutInflater.from(context).inflate(R.layout.item_coursedir_child, parent, false);
            childholder = new ChildHolder();
            childholder.course_sectionname = (TextView) view.findViewById(R.id.item_course_sectionname);
            childholder.course_titme = (TextView) view.findViewById(R.id.item_course_sectiontime);
            view.setTag(childholder);
        }
        if (list.get(groupPosition).getVideo() != null && list.get(groupPosition).getVideo().size() > 0) {
            MineCourseCollectionDirBean.DataBean.SectionBean.VideoBean videoBean = list.get(groupPosition).getVideo().get(childPosition);
            if (videoBean != null) {
                if (!TextUtils.isEmpty(videoBean.getVideo_name())) {
                    childholder.course_sectionname.setText(videoBean.getVideo_name());
                }
                if (!TextUtils.isEmpty(videoBean.getVideo_time())) {
                    childholder.course_titme.setText(videoBean.getVideo_time());
                }
                if (groupIndex == groupPosition && sonIndex == childPosition) {
                    childholder.course_sectionname.setTextColor(ContextCompat.getColor(context, R.color.color_0267FF));
                    childholder.course_titme.setTextColor(ContextCompat.getColor(context, R.color.color_0267FF));
                } else {
                    childholder.course_sectionname.setTextColor(ContextCompat.getColor(context, R.color.color_666666));
                    childholder.course_titme.setTextColor(ContextCompat.getColor(context, R.color.color_999999));
                }
            }
        }
        return view;
    }

    public void setSelectPosition(int groupPosition, int childPosition) {
        groupIndex = groupPosition;
        sonIndex = childPosition;
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
    }
}
