package com.ucfo.youcai.presenter.view.learncenter;

import com.ucfo.youcai.base.BaseView;
import com.ucfo.youcai.entity.learncenter.LearncenterHomeBean;
import com.ucfo.youcai.entity.learncenter.StudyClockInBean;
import com.ucfo.youcai.entity.learncenter.UnFinishPlanBean;

/**
 * Author: AND
 * Time: 2019-7-18.  上午 10:23
 * Package: com.ucfo.youcai.presenter.view.learncenter
 * FileName: ILearncenterHomeView
 * Description:TODO 学习中心首页
 */
public interface ILearncenterHomeView extends BaseView {
    //学习中心首页
    void learncenterHome(LearncenterHomeBean result);

    //打卡成功
    void studyClockInResult(StudyClockInBean result);

    //未完成学习计划
    void getUnFinishPlan(UnFinishPlanBean result);
}
