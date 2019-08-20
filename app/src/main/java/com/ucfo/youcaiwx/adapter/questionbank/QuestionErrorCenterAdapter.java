package com.ucfo.youcaiwx.adapter.questionbank;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.questionbank.QuestionErrorCenterBean;

import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-5-10.  下午 5:55
 * FileName: QuestionErrorCenterAdapter
 * Description:TODO 我的错题列表适配器
 */
public class QuestionErrorCenterAdapter extends BaseExpandableListAdapter {


    private ArrayList<QuestionErrorCenterBean.DataBean> list;
    private Context context;
    private int plate;

    public QuestionErrorCenterAdapter(ArrayList<QuestionErrorCenterBean.DataBean> list, Context context, int plate_id) {
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

        if (plate == Constant.PLATE_7) {//系统高频错题
            groupholder.item_Number.setVisibility(View.VISIBLE);
            groupholder.item_SubTitle.setVisibility(View.GONE);
        }

        if (list.get(groupPosition).getKnob() != null && list.get(groupPosition).getKnob().size() > 0) {
        } else {
            groupholder.item_BottomImage.setVisibility(View.INVISIBLE);
        }

        QuestionErrorCenterBean.DataBean dataBean = list.get(groupPosition);
        String section_name = dataBean.getSection_name();
        String holder = context.getResources().getString(R.string.question_tips_errorCount);
        int sectionnum = dataBean.getSectionerrornum();
        if (!TextUtils.isEmpty(section_name)) {
            groupholder.item_Title.setText(section_name);
        }
        String sectionnumber = String.valueOf(sectionnum + holder);
        SpannableString spannableString = new SpannableString(sectionnumber);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF999999"));
        spannableString.setSpan(colorSpan, sectionnumber.length() - holder.length(), sectionnumber.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        groupholder.item_Number.setText(spannableString);

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

            childholder.item_TopImage = view.findViewById(R.id.item_list_topimage);
            childholder.item_BottomImage = view.findViewById(R.id.item_list_bottomimage);
            childholder.item_Divider = view.findViewById(R.id.item_list_divider);
            view.setTag(childholder);
        }

        if (plate == Constant.PLATE_7) {
            childholder.item_Number.setVisibility(View.VISIBLE);
            childholder.item_Pratice.setVisibility(View.VISIBLE);
            childholder.item_SubTitle.setVisibility(View.GONE);
        }

        if (isLastChild) {//最后一个item
            childholder.item_BottomImage.setVisibility(View.INVISIBLE);
            childholder.item_Divider.setVisibility(View.VISIBLE);
        } else {
            childholder.item_BottomImage.setVisibility(View.VISIBLE);
            childholder.item_Divider.setVisibility(View.INVISIBLE);
        }

        QuestionErrorCenterBean.DataBean.KnobBean bean = list.get(groupPosition).getKnob().get(childPosition);
        String knob_name = bean.getKnob_name();
        String holder = context.getResources().getString(R.string.question_tips_errorCount);
        int knownum = bean.getKnowerrornum();
        if (!TextUtils.isEmpty(knob_name)) {
            childholder.item_Title.setText(knob_name);
        }
        String sectionnumber = String.valueOf(knownum + holder);
        SpannableString spannableString = new SpannableString(sectionnumber);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF999999"));
        spannableString.setSpan(colorSpan, sectionnumber.length() - holder.length(), sectionnumber.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        childholder.item_Number.setText(spannableString);
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
