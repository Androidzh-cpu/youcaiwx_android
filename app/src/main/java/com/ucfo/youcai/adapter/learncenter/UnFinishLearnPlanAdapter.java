package com.ucfo.youcai.adapter.learncenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.ucfo.youcai.R;
import com.ucfo.youcai.entity.learncenter.UnFinishPlanBean;
import com.ucfo.youcai.widget.customview.NestedListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-19.  下午 4:13
 * Package: com.ucfo.youcai.adapter.learncenter
 * FileName: UnFinishLearnPlanAdapter
 * Description:TODO 未完成学习计划
 */
public class UnFinishLearnPlanAdapter extends BaseExpandableListAdapter {
    private List<UnFinishPlanBean.DataBean> list;
    private Context context;
    private List<UnFinishPlanBean.DataBean.VideoBean> videoBeanList;

    public UnFinishLearnPlanAdapter(List<UnFinishPlanBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;

        videoBeanList = new ArrayList<>();
    }

    @Override
    public int getGroupCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getVideo() == null ? 0 : 1;
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
                view = LayoutInflater.from(context).inflate(R.layout.item_lc_unfinish_group, parent, false);
            }
            groupholder = new GroupHolder();
            groupholder.mTitleItem = (TextView) view.findViewById(R.id.item_title);
            groupholder.mBtnToStudy = (RoundTextView) view.findViewById(R.id.btnToStudy);
            view.setTag(groupholder);
        }
        UnFinishPlanBean.DataBean bean = list.get(groupPosition);
        String plan_name = bean.getPlan_name();
        groupholder.mTitleItem.setText(plan_name);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final boolean[] expandGroup = {false};
        View view = null;
        ChildHolder childholder = null;
        if (convertView != null) {
            view = convertView;
            childholder = (ChildHolder) view.getTag();
        } else {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_lc_unfinish_child, parent, false);
            }
            childholder = new ChildHolder();
            childholder.mNestedlistview = (NestedListView) view.findViewById(R.id.nestedlistview);
            childholder.mBtnSpread = (TextView) view.findViewById(R.id.btnSpread);
            childholder.lineView = (View) view.findViewById(R.id.line);
            view.setTag(childholder);
        }
        List<UnFinishPlanBean.DataBean.VideoBean> videoBeans = list.get(groupPosition).getVideo();//视频列表
        videoBeanList.clear();
        videoBeanList.addAll(list.get(groupPosition).getVideo());
        if (videoBeanList.size() > 3) {
            for (int i = 3; i < videoBeanList.size(); i++) {
                videoBeanList.remove(i);
            }
            expandGroup[0] = false;
        }
        UnFinishLearnVideoAdapter videoAdapter = new UnFinishLearnVideoAdapter(videoBeanList, context);
        childholder.mNestedlistview.setAdapter(videoAdapter);
        videoAdapter.notifyDataSetChanged();
        if (expandGroup[0]) {
            childholder.mBtnSpread.setText(context.getResources().getString(R.string.closed));
        } else {
            childholder.mBtnSpread.setText(context.getResources().getString(R.string.expand));
        }
        ChildHolder finalChildholder = childholder;
        childholder.mBtnSpread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoBeanList.clear();
                videoBeanList.addAll(list.get(groupPosition).getVideo());
                if (expandGroup[0]) {//关闭列表
                    finalChildholder.mBtnSpread.setText(context.getResources().getString(R.string.expand));
                    for (int i = 3; i < videoBeanList.size(); i++) {
                        videoBeanList.remove(i);
                    }
                    videoAdapter.notifyDataSetChanged();
                } else {//展开列表
                    finalChildholder.mBtnSpread.setText(context.getResources().getString(R.string.closed));
                    videoAdapter.notifyDataSetChanged();
                }
                expandGroup[0] = !expandGroup[0];
            }
        });
        if (videoBeans.size() > 3) {
            childholder.lineView.setVisibility(View.VISIBLE);
            childholder.mBtnSpread.setVisibility(View.VISIBLE);
        } else {
            childholder.lineView.setVisibility(View.GONE);
            childholder.mBtnSpread.setVisibility(View.GONE);
        }
        return view;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;//设置为false子类点击事件不响应
    }

    private static class GroupHolder {
        TextView mTitleItem;
        RoundTextView mBtnToStudy;
    }

    private static class ChildHolder {
        NestedListView mNestedlistview;
        TextView mBtnSpread;
        View lineView;
    }
}
