package com.ucfo.youcaiwx.adapter.user;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.user.MineQuestionCollectionListBean;

import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-6-19.  下午 3:09
 * FileName: MineQuestionCollectionListAdapter
 * Description:TODO 我的题库收藏列表适配器
 */
public class MineQuestionCollectionListAdapter extends BaseExpandableListAdapter {
    private ArrayList<MineQuestionCollectionListBean.DataBean> list;
    private Context context;

    public MineQuestionCollectionListAdapter(ArrayList<MineQuestionCollectionListBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void notifyChange(ArrayList<MineQuestionCollectionListBean.DataBean> dataBeanList) {
        this.list = dataBeanList;

        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getKnob() == null ? 0 : list.get(groupPosition).getKnob().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getKnob().get(childPosition);
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
            view = LayoutInflater.from(context).inflate(R.layout.item_kowledge_group, parent, false);
            groupholder = new GroupHolder();

            groupholder.item_Title = view.findViewById(R.id.item_list_title);
            groupholder.item_SubTitle = view.findViewById(R.id.item_list_subtitle);
            groupholder.item_Number = view.findViewById(R.id.item_list_number);
            groupholder.item_CenterImage = view.findViewById(R.id.item_list_centerimage);
            groupholder.item_BottomImage = view.findViewById(R.id.item_list_bottomimage);
            groupholder.item_Divider = view.findViewById(R.id.item_list_divider);
            view.setTag(groupholder);
        }
        groupholder.item_SubTitle.setVisibility(View.VISIBLE);
        groupholder.item_Number.setVisibility(View.GONE);
        //判断是否已经打开列表
        if (isExpanded) {//已打开
            groupholder.item_CenterImage.setImageResource(R.mipmap.icon_qb_minus);
            groupholder.item_BottomImage.setVisibility(View.VISIBLE);
            groupholder.item_Divider.setVisibility(View.INVISIBLE);
        } else {//关闭状态
            groupholder.item_CenterImage.setImageResource(R.mipmap.icon_qb_add);
            groupholder.item_BottomImage.setVisibility(View.INVISIBLE);
            groupholder.item_Divider.setVisibility(View.VISIBLE);
        }
        if (list.get(groupPosition).getKnob() != null && list.get(groupPosition).getKnob().size() > 0) {
        } else {
            groupholder.item_BottomImage.setVisibility(View.INVISIBLE);
        }

        MineQuestionCollectionListBean.DataBean bean = list.get(groupPosition);
        String section_name = bean.getSection_name();
        int sectioncoun = bean.getSectioncoun();

        if (!TextUtils.isEmpty(section_name)) {
            groupholder.item_Title.setText(section_name);
        }
        String holder = context.getResources().getString(R.string.question_tips_Count);
        String sectionnumber = String.valueOf(sectioncoun + holder);
        groupholder.item_SubTitle.setText(sectionnumber);
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
            view = LayoutInflater.from(context).inflate(R.layout.item_knowledge_child, parent, false);
            childholder = new ChildHolder();

            childholder.item_Title = view.findViewById(R.id.item_list_title);
            childholder.item_SubTitle = view.findViewById(R.id.item_list_subtitle);
            childholder.item_Number = view.findViewById(R.id.item_list_number);
            childholder.item_Pratice = view.findViewById(R.id.item_list_pratice);
            childholder.item_Look = view.findViewById(R.id.item_list_look);

            childholder.item_TopImage = view.findViewById(R.id.item_list_topimage);
            childholder.item_BottomImage = view.findViewById(R.id.item_list_bottomimage);
            childholder.item_Divider = view.findViewById(R.id.item_list_divider);
            view.setTag(childholder);
        }
        childholder.item_SubTitle.setVisibility(View.VISIBLE);
        childholder.item_Number.setVisibility(View.GONE);

        if (isLastChild) {//最后一个item
            childholder.item_BottomImage.setVisibility(View.INVISIBLE);
            childholder.item_Divider.setVisibility(View.VISIBLE);
        } else {
            childholder.item_BottomImage.setVisibility(View.VISIBLE);
            childholder.item_Divider.setVisibility(View.INVISIBLE);
        }
        childholder.item_Look.setVisibility(View.VISIBLE);

        MineQuestionCollectionListBean.DataBean.KnobBean bean = list.get(groupPosition).getKnob().get(childPosition);
        String knob_name = bean.getKnob_name();
        int knobcoun = bean.getKnobcoun();

        if (!TextUtils.isEmpty(knob_name)) {
            childholder.item_Title.setText(knob_name);
        }

        String holder = context.getResources().getString(R.string.question_tips_Count);
        String sectionnumber = String.valueOf(knobcoun + holder);
        childholder.item_SubTitle.setText(sectionnumber);
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;//设置为false子类点击事件不响应    4132     2049=6181  -----3100 3370=6470
    }

    protected static class GroupHolder {
        TextView item_Title;
        TextView item_SubTitle;
        TextView item_Number;
        ImageView item_CenterImage;
        View item_BottomImage;
        View item_Divider;
    }

    protected static class ChildHolder {
        TextView item_Title;
        TextView item_SubTitle;
        TextView item_Number;
        TextView item_Look;
        RoundTextView item_Pratice;
        View item_TopImage;
        View item_BottomImage;
        View item_Divider;
    }
}
