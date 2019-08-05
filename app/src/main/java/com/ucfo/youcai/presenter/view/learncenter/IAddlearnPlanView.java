package com.ucfo.youcai.presenter.view.learncenter;

import com.ucfo.youcai.base.BaseView;
import com.ucfo.youcai.entity.address.StateStatusBean;
import com.ucfo.youcai.entity.learncenter.AddLearnPlanCheckCourseBean;
import com.ucfo.youcai.entity.learncenter.AddLearnPlanCheckTimeBean;

/**
 * Author: AND
 * Time: 2019-7-19.  上午 9:48
 * Package: com.ucfo.youcai.presenter.view.learncenter
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
