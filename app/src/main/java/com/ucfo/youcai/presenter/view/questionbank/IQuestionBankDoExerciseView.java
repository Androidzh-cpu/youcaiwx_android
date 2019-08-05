package com.ucfo.youcai.presenter.view.questionbank;

import com.ucfo.youcai.base.BaseView;
import com.ucfo.youcai.entity.questionbank.DoProblemsBean;
import com.ucfo.youcai.entity.questionbank.QuestionDeleteBean;
import com.ucfo.youcai.entity.questionbank.SubmitStatusResultBean;

/**
 * Author: AND
 * Time: 2019-5-16.  下午 4:39
 * Package: com.ucfo.youcai.presenter.view.questionbank
 * FileName: IQuestionBankDoExerciseView
 * Description:TODO 题库业务操作
 */
public interface IQuestionBankDoExerciseView extends BaseView {
    //TODO 6大板块拿题
    void getProblemsList(DoProblemsBean bean);

    //TODO 交卷状态
    void submitStatus(SubmitStatusResultBean bean);

    //TODO 错题中心交卷
    void errorCenterSubmitStatus(int status);

    //TODO 收藏操作状态
    void collectionResult(int status);

    //TODO 题目删除状态
    void deleteResult(QuestionDeleteBean data);
}
