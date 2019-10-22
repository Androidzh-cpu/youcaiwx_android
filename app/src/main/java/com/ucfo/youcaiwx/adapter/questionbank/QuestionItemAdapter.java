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
    public ArrayList<DoProblemsBean.DataBean.TopicsBean> list;//TODO 所有题目数据集(从接口获取)
    private String EXERCISE_TYPE;
    private int plate_id;

    //TODO fragment管理器,type等同于做题界面的testmodle,标题
    public QuestionItemAdapter(FragmentManager fm, String exercise_type, ArrayList<DoProblemsBean.DataBean.TopicsBean> questionList, int plate_id) {
        super(fm);
        this.EXERCISE_TYPE = exercise_type;
        this.list = questionList;
        this.plate_id = plate_id;
    }

    @Override
    public Fragment getItem(int position) {
        if (plate_id == Constant.PLATE_13 || plate_id == Constant.PLATE_16) {
            //我的收藏下可能有论述和选择共同出现的情况
            String topicType = list.get(position).getTopicType();
            if (TextUtils.equals(topicType, String.valueOf(1))) {
                return new QuestionChoiceItemFragment(position, EXERCISE_TYPE, list.size(), plate_id);
            } else {
                return new QuestionDiscussItemFragment(position, EXERCISE_TYPE, list.size(), plate_id);
            }
        } else {//非收藏
            if (TextUtils.equals(EXERCISE_TYPE, Constant.EXERCISE_D)) {//TODO 论述题模式
                return new QuestionDiscussItemFragment(position, EXERCISE_TYPE, list.size(), plate_id);
            } else {//TODO 选择题模式
                return new QuestionChoiceItemFragment(position, EXERCISE_TYPE, list.size(), plate_id);
            }
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
