package com.ucfo.youcai.presenter.view.learncenter;

import com.ucfo.youcai.base.BaseView;
import com.ucfo.youcai.entity.learncenter.LearnPlanDetailBean;
import com.ucfo.youcai.entity.learncenter.LearnPlanDetailVideoBean;

/**
 * Author: AND
 * Time: 2019-7-22.  上午 10:33
 * Package: com.ucfo.youcai.presenter.view.learncenter
 * FileName: ILearnPlanDetailView
 * Description:TODO 学习计划详情
 */
public interface ILearnPlanDetailView extends BaseView {

    void getPlanDetailList(LearnPlanDetailBean result);

    void getPlanDetailVideoList(LearnPlanDetailVideoBean result);

    void exitPlanResult(int code);

}
