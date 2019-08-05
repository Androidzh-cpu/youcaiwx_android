package com.ucfo.youcai.presenter.presenterImpl.answer;

/**
 * Author: AND
 * Time: 2019-5-30.  下午 2:33
 * Package: com.ucfo.youcai.presenter.presenterImpl.answer
 * FileName: IQuestionAnswerPresenter
 * Description:TODO 题库答疑
 */
public interface IQuestionAnswerPresenter {

    //TODO 获取题库答疑列表
    void getQuestionAnswerList(int user_id, int question_id, int type);

    //TODO 获取题库答疑详情
    void getQuestionAnswerDetail(int user_id, int answer_id);

}
