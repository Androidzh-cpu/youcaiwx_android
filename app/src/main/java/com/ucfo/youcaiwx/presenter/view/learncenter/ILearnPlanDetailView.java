package com.ucfo.youcaiwx.presenter.view.learncenter;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.learncenter.LearnPlanDetailBean;
import com.ucfo.youcaiwx.entity.learncenter.LearnPlanDetailVideoBean;

/**
 * Author: AND
 * Time: 2019-7-22.  上午 10:33
 * FileName: ILearnPlanDetailView
 * Description:TODO 学习计划详情
 */
public interface ILearnPlanDetailView extends BaseView {

    void getPlanDetailList(LearnPlanDetailBean result);

    void getPlanDetailVideoList(LearnPlanDetailVideoBean result);

    void exitPlanResult(int code);

}
