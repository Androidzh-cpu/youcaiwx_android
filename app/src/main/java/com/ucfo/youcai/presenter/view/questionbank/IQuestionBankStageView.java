package com.ucfo.youcai.presenter.view.questionbank;

import com.ucfo.youcai.base.BaseView;
import com.ucfo.youcai.entity.questionbank.QuestionStageOfTestBean;

/**
 * Author: AND
 * Time: 2019-5-6.  上午 10:34
 * Package: com.ucfo.youcai.presenter.view.questionbank
 * FileName: IQuestionBankStageView
 * Description:TODO 阶段测试
 * Detail:TODO
 */
public interface IQuestionBankStageView extends BaseView {

    void getStageOfTestData(QuestionStageOfTestBean data);
}
