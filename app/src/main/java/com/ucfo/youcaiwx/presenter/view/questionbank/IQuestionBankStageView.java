package com.ucfo.youcaiwx.presenter.view.questionbank;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.questionbank.QuestionStageOfTestBean;

/**
 * Author: AND
 * Time: 2019-5-6.  上午 10:34
 * FileName: IQuestionBankStageView
 * Description:TODO 阶段测试
 * Detail:TODO
 */
public interface IQuestionBankStageView extends BaseView {

    void getStageOfTestData(QuestionStageOfTestBean data);
}
