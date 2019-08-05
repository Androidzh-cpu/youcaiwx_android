package com.ucfo.youcai.presenter.view.answer;

import com.ucfo.youcai.base.BaseView;
import com.ucfo.youcai.entity.answer.AnswerKnowListBean;

/**
 * Author:29117
 * Time: 2019-4-19.  上午 9:09
 * Email:2911743255@qq.com
 * ClassName: IAnswerAskQuestionView
 * Package: com.ucfo.youcai.presenter.view.answer
 * Description:TODO 视频提问问题
 */
public interface IAnswerAskQuestionView extends BaseView {

    void askQuestionResult(int code);

    void getKnowList(AnswerKnowListBean data);
}
