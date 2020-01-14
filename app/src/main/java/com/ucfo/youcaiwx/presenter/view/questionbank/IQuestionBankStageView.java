package com.ucfo.youcaiwx.presenter.view.questionbank;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.questionbank.QuestionStageOfTestBean;
import com.ucfo.youcaiwx.entity.questionbank.TipsBean;

/**
 * Author: AND
 * Time: 2019-5-6.  上午 10:34
 * FileName: IQuestionBankStageView
 * Description:TODO 阶段测试,组卷模考,论述题自测,训练营
 */
public interface IQuestionBankStageView extends BaseView {

    /**
     * 试卷列表
     *
     * @param data
     */
    void getStageOfTestData(QuestionStageOfTestBean data);

    /**
     * 提示框列表
     *
     * @param data
     */
    void getDialogTips(TipsBean data);
}
