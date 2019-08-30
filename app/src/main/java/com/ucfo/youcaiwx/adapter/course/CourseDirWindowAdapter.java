package com.ucfo.youcaiwx.adapter.course;

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
import com.ucfo.youcaiwx.entity.course.CourseDirBean;

import java.util.List;

/**
 * Author:29117
 * Time: 2019-4-16.  上午 11:58
 * Email:2911743255@qq.com
 * ClassName: CourseDirWindowAdapter
 * Description:TODO  课程弹出框适配器
 */
public class CourseDirWindowAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<CourseDirBean.DataBean.SectionBean> list;
    private int groupIndex = -1, sonIndex = -1;
    private int currentPlayCourseIndex = -1, currentClickCourseIndex = -1;

    public CourseDirWindowAdapter(Context context, List<CourseDirBean.DataBean.SectionBean> list) {
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
            groupholder.courseTitle = (TextView) view.findViewById(R.id.item_course_sectionname);
            groupholder.courseGroupbtn = (ImageView) view.findViewById(R.id.item_course_groupbtn);
            view.setTag(groupholder);
        }
        //判断是否已经打开列表
        if (isExpanded) {//已打开
            groupholder.courseGroupbtn.setImageResource(R.mipmap.icon_top_arrows);
        } else {//关闭状态
            groupholder.courseGroupbtn.setImageResource(R.mipmap.icon_bottom_arrows);
        }
        String section_name = list.get(groupPosition).getSection_name();
        if (!TextUtils.isEmpty(section_name)) {
            groupholder.courseTitle.setText(section_name);
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
            childholder = new ChildHolder();
            childholder.courseSectionname = (TextView) view.findViewById(R.id.item_course_sectionname);
            childholder.courseTitme = (TextView) view.findViewById(R.id.item_course_sectiontime);
            view.setTag(childholder);
        }
        if (list.get(groupPosition).getVideo() != null && list.get(groupPosition).getVideo().size() > 0) {
            CourseDirBean.DataBean.SectionBean.VideoBean videoBean = list.get(groupPosition).getVideo().get(childPosition);

            if (videoBean != null) {
                if (!TextUtils.isEmpty(videoBean.getVideo_name())) {
                    childholder.courseSectionname.setText(videoBean.getVideo_name());
                }
                if (!TextUtils.isEmpty(videoBean.getVideo_time())) {
                    childholder.courseTitme.setText(videoBean.getVideo_time());
                }

                //TODO 添加多个套餐中播放课程的索引
                if (currentPlayCourseIndex == currentClickCourseIndex) {
                    if (groupIndex == groupPosition && sonIndex == childPosition) {
                        childholder.courseSectionname.setTextColor(ContextCompat.getColor(context, R.color.color_0267FF));
                        childholder.courseTitme.setTextColor(ContextCompat.getColor(context, R.color.color_0267FF));
                    } else {
                        childholder.courseSectionname.setTextColor(ContextCompat.getColor(context, R.color.color_666666));
                        childholder.courseTitme.setTextColor(ContextCompat.getColor(context, R.color.color_999999));
                    }
                } else {
                    childholder.courseSectionname.setTextColor(ContextCompat.getColor(context, R.color.color_666666));
                    childholder.courseTitme.setTextColor(ContextCompat.getColor(context, R.color.color_999999));
                }
            }
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;//设置为false子类点击事件不响应
    }

    public void setSelectPosition(int groupPosition, int childPosition, int currentPlayCourse, int currentClickCourse) {
        groupIndex = groupPosition;
        sonIndex = childPosition;
        currentPlayCourseIndex = currentPlayCourse;
        currentClickCourseIndex = currentClickCourse;
    }

    protected static class GroupHolder {
        TextView courseTitle;
        ImageView courseGroupbtn;
    }

    protected static class ChildHolder {
        TextView courseTitme;
        TextView courseSectionname;
    }
}
