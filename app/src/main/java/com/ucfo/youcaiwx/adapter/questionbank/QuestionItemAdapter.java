package com.ucfo.youcaiwx.adapter.questionbank;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;

import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.questionbank.DoProblemsBean;
import com.ucfo.youcaiwx.module.questionbank.fragment.QuestionChoiceItemFragment;
import com.ucfo.youcaiwx.module.questionbank.fragment.QuestionDiscussItemFragment;

import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-5-6.  下午 5:24
 * FileName: QuestionItemAdapter
 * Description:TODO  viewpager创建多个做题页fragment
 */
public class QuestionItemAdapter extends FragmentStatePagerAdapter {
    //TODO 所有题目数据集(从接口获取)
    public ArrayList<DoProblemsBean.DataBean.TopicsBean> list;
    //TODO 做题模式
    private String exerciseType;
    //todo 做题板块
    private int plateId;

    //TODO fragment管理器,type等同于做题界面的testmodle,板块
    public QuestionItemAdapter(FragmentManager fm, String exerciseType, ArrayList<DoProblemsBean.DataBean.TopicsBean> questionList, int plateId) {
        super(fm);
        this.exerciseType = exerciseType;
        this.list = questionList;
        this.plateId = plateId;
    }

    @Override
    public Fragment getItem(int position) {
        if (plateId == Constant.PLATE_13 || plateId == Constant.PLATE_16) {
            //TODO 我的收藏板块下 可能有论述和选择共同出现的情况
            String topicType = list.get(position).getTopicType();
            if (TextUtils.equals(topicType, String.valueOf(1))) {
                QuestionChoiceItemFragment itemFragment = new QuestionChoiceItemFragment(position, exerciseType, list.size(), plateId);
                itemFragment.setIndex(position);
                return itemFragment;
            } else {
                QuestionDiscussItemFragment itemFragment = new QuestionDiscussItemFragment(position, exerciseType, list.size(), plateId);
                itemFragment.setIndex(position);
                return itemFragment;
            }
        } else {
            //TODO 非收藏
            if (TextUtils.equals(exerciseType, Constant.EXERCISE_D)) {
                //TODO 论述题
                QuestionDiscussItemFragment itemFragment = new QuestionDiscussItemFragment(position, exerciseType, list.size(), plateId);
                itemFragment.setIndex(position);
                return itemFragment;
            } else {
                //TODO 选择题
                QuestionChoiceItemFragment itemFragment = new QuestionChoiceItemFragment(position, exerciseType, list.size(), plateId);
                itemFragment.setIndex(position);
                return itemFragment;
            }
        }
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }
}
