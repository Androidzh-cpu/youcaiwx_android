package com.ucfo.youcaiwx.adapter.questionbank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.questionbank.QuestionStageOfTestBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.baseadapter.OnItemClickListener;

import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-5-6.  上午 11:38
 * FileName: QuestionStageTestAdapter
 * Description:TODO 阶段测试
 */
public class QuestionStageTestAdapter extends BaseAdapter<QuestionStageOfTestBean.DataBean, QuestionStageTestAdapter.ViewHolder> {
    private Context context;
    private ArrayList<QuestionStageOfTestBean.DataBean> list;
    private OnItemClickListener onItemClickListener;
    private int plate_id;

    public QuestionStageTestAdapter(Context context, ArrayList<QuestionStageOfTestBean.DataBean> list, int plate_id) {
        this.context = context;
        this.list = list;
        this.plate_id = plate_id;
    }

    public void notifyChange(ArrayList<QuestionStageOfTestBean.DataBean> dataBeanList) {
        this.list = dataBeanList;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setItemClick(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        QuestionStageOfTestBean.DataBean bean = list.get(position);
        String sectionName = bean.getSection_name();
        String paperName = bean.getPaper_name();
        String difficulty = bean.getDifficulty();

        if (plate_id == Constant.PLATE_2 || plate_id == Constant.PLATE_3 || plate_id == Constant.PLATE_7) {
            if (!TextUtils.isEmpty(paperName)) {
                holder.mStageKnowledgeTitleItem.setText(paperName);
            } else {
                holder.mStageKnowledgeTitleItem.setText(R.string.default_title);
            }
        } else {
            if (!TextUtils.isEmpty(sectionName)) {
                holder.mStageKnowledgeTitleItem.setText(sectionName);
            } else {
                holder.mStageKnowledgeTitleItem.setText(R.string.default_title);
            }
        }


        if (!TextUtils.isEmpty(difficulty)) {//TODO 难易度A级,B级,C级
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(context.getResources().getString(R.string.holder_difficulty));
            if (difficulty.equals("C")) {
                stringBuilder.append(context.getResources().getString(R.string.difficulty));
                holder.mStageKnowledgeSubtitleItem.setText(stringBuilder.toString());
                holder.mStageKnowledgeSubtitleItem.setTextColor(ContextCompat.getColor(context, R.color.color_E84342));
            } else if (difficulty.equals("B")) {
                stringBuilder.append(context.getResources().getString(R.string.medium));
                holder.mStageKnowledgeSubtitleItem.setText(stringBuilder.toString());
                holder.mStageKnowledgeSubtitleItem.setTextColor(ContextCompat.getColor(context, R.color.color_F99111));
            } else if (difficulty.equals("A")) {
                stringBuilder.append(context.getResources().getString(R.string.easy));
                holder.mStageKnowledgeSubtitleItem.setText(stringBuilder.toString());
                holder.mStageKnowledgeSubtitleItem.setTextColor(ContextCompat.getColor(context, R.color.color_0AAB55));
            }
        } else {
            holder.mStageKnowledgeSubtitleItem.setText(R.string.default_title);
        }

        holder.mStageKnowledgeMakeProblemItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, position);
            }
        });

    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_stage_knowledge, viewGroup, false);
        return new QuestionStageTestAdapter.ViewHolder(inflate);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mStageKnowledgeTitleItem;
        private TextView mStageKnowledgeSubtitleItem;
        private RoundTextView mStageKnowledgeMakeProblemItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mStageKnowledgeTitleItem = (TextView) itemView.findViewById(R.id.item_stage_knowledge_Title);
            mStageKnowledgeSubtitleItem = (TextView) itemView.findViewById(R.id.item_stage_knowledge_Subtitle);
            mStageKnowledgeMakeProblemItem = (RoundTextView) itemView.findViewById(R.id.item_stage_knowledge_makeProblem);
        }

    }
}
