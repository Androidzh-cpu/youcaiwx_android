package com.ucfo.youcaiwx.presenter.view.learncenter;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.address.StateStatusBean;
import com.ucfo.youcaiwx.entity.learncenter.AddLearnPlanCheckCourseBean;
import com.ucfo.youcaiwx.entity.learncenter.AddLearnPlanCheckTimeBean;

/**
 * Author: AND
 * Time: 2019-7-19.  上午 9:48
 * FileName: IAddlearnPlanView
 * Description:TODO 添加学习计划
 */
public interface IAddlearnPlanView extends BaseView {

    //选择专业课
    void getCheckCourseList(AddLearnPlanCheckCourseBean result);

    //选择考试时间
    void getCheckTimeList(AddLearnPlanCheckTimeBean result);

    //计划添加结果
    void addLearnPlanResult(StateStatusBean data);
}
