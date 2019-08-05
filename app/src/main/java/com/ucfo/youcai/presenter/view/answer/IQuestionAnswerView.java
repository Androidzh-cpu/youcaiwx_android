package com.ucfo.youcai.presenter.view.answer;

import com.ucfo.youcai.base.BaseView;
import com.ucfo.youcai.entity.answer.QuestionAnswerDetailBean;
import com.ucfo.youcai.entity.answer.QuestionAnswerListBean;

/**
 * Author: AND
 * Time: 2019-5-30.  下午 2:21
 * Package: com.ucfo.youcai.presenter.view.questionbank
 * FileName: IQuestionAnswerView
 * Description:TODO 题库答疑业务
 */
public interface IQuestionAnswerView extends BaseView {

    //TODO 题库答疑列表
    void getAnswerList(QuestionAnswerListBean data);

    //TODO 题库答疑详情
    void getQuestionAnswerDetail(QuestionAnswerDetailBean bean);

}
