package com.ucfo.youcaiwx.adapter.questionbank;

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
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.questionbank.QuestionKnowledgeListBean;

import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-5-9.  下午 5:44
 * FileName: QuestionSelfHelpAdapter
 * Description:TODO 自助练习二级列表适配器
 */
public class QuestionSelfHelpAdapter extends BaseExpandableListAdapter {

    private ArrayList<QuestionKnowledgeListBean.DataBean> list;
    private Context context;
    private int plate;

    public QuestionSelfHelpAdapter(ArrayList<QuestionKnowledgeListBean.DataBean> list, Context context, int plate_id) {
        this.list = list;
        this.context = context;
        this.plate = plate_id;
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kowledge_group, parent, false);
            groupholder = new GroupHolder();

            groupholder.item_Title = view.findViewById(R.id.item_list_title);
            groupholder.item_SubTitle = view.findViewById(R.id.item_list_subtitle);
            groupholder.item_Number = view.findViewById(R.id.item_list_number);
            groupholder.item_CenterImage = view.findViewById(R.id.item_list_centerimage);
            groupholder.item_BottomImage = view.findViewById(R.id.item_list_bottomimage);
            groupholder.item_Divider = view.findViewById(R.id.item_list_divider);
            view.setTag(groupholder);
        }
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

        if (plate == Constant.PLATE_5) {//自助练习
            groupholder.item_Number.setVisibility(View.GONE);
            groupholder.item_SubTitle.setVisibility(View.GONE);
        }

        if (list.get(groupPosition).getKnob() != null && list.get(groupPosition).getKnob().size() > 0) {
        } else {
            groupholder.item_BottomImage.setVisibility(View.INVISIBLE);
        }
        QuestionKnowledgeListBean.DataBean dataBean = list.get(groupPosition);
        String sectionName = dataBean.getSection_name();
        if (!TextUtils.isEmpty(sectionName)) {
            groupholder.item_Title.setText(sectionName);
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_knowledge_child, parent, false);
            childholder = new ChildHolder();

            childholder.item_Title = view.findViewById(R.id.item_list_title);
            childholder.item_SubTitle = view.findViewById(R.id.item_list_subtitle);
            childholder.item_Number = view.findViewById(R.id.item_list_number);
            childholder.item_Pratice = view.findViewById(R.id.item_list_pratice);

            childholder.item_TopImage = view.findViewById(R.id.item_list_topimage);
            childholder.item_BottomImage = view.findViewById(R.id.item_list_bottomimage);
            childholder.item_Divider = view.findViewById(R.id.item_list_divider);
            view.setTag(childholder);
        }

        if (plate == Constant.PLATE_5) {//自助练习
            childholder.item_Number.setVisibility(View.GONE);
            childholder.item_Pratice.setVisibility(View.GONE);
            childholder.item_SubTitle.setVisibility(View.GONE);
        }

        if (isLastChild) {//最后一个item
            childholder.item_BottomImage.setVisibility(View.INVISIBLE);
            childholder.item_Divider.setVisibility(View.VISIBLE);
        } else {
            childholder.item_BottomImage.setVisibility(View.VISIBLE);
            childholder.item_Divider.setVisibility(View.INVISIBLE);
        }

        QuestionKnowledgeListBean.DataBean.KnobBean bean = list.get(groupPosition).getKnob().get(childPosition);
        String knobName = bean.getKnob_name();
        if (!TextUtils.isEmpty(knobName)) {
            childholder.item_Title.setText(knobName);
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;//设置为false子类点击事件不响应
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
        RoundTextView item_Pratice;
        View item_TopImage;
        View item_BottomImage;
        View item_Divider;
    }


}
