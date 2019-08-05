package com.ucfo.youcai.presenter.view.questionbank;

import com.ucfo.youcai.base.BaseView;
import com.ucfo.youcai.entity.questionbank.QuestionBankHightErrorBean;
import com.ucfo.youcai.entity.questionbank.QuestionErrorCenterBean;
import com.ucfo.youcai.entity.questionbank.QuestionKnowLedgeChildListBean;
import com.ucfo.youcai.entity.questionbank.QuestionKnowledgeListBean;

/**
 * Author: AND
 * Time: 2019-5-7.  下午 1:55
 * Package: com.ucfo.youcai.presenter.view.questionbank
 * FileName: IQuestionBankKonwledgeView
 * Description:TODO 知识点练习业务
 * Detail:TODO
 */
public interface IQuestionBankKonwledgeView extends BaseView {
    //知识点练习二级列表
    void getKnowledgeList(QuestionKnowledgeListBean data);

    //知识点练习和高频错题三姐列表
    void getKnowledgeChildList(QuestionKnowLedgeChildListBean questionKnowLedgeChildListBean);

    //系统高频错题二级列表
    void getHighFrequencyWrongTopic(QuestionBankHightErrorBean questionBankHightErrorBean);

    //我的二级列表
    void getErrorCenterSectionList(QuestionErrorCenterBean questionErrorCenterBean);
}
