package com.ucfo.youcaiwx.presenter.view.questionbank;

import com.ucfo.youcaiwx.entity.questionbank.QuestionMyProjectBean;
import com.ucfo.youcaiwx.entity.questionbank.SubjectInfoBean;

/**
 * Author:29117
 * Time: 2019-4-26.  上午 10:28
 * Email:2911743255@qq.com
 * ClassName: IQuestionBankHomeView
 * Description:TODO 题库首页操作
 * Detail:TODO
 */
public interface IQuestionBankHomeView {
    /**
     * Description:IQuestionBankHomeView
     * Time:2019-4-26   上午 10:44
     * Detail: 获取我购买的题库
     */
    void getMyProejctList(QuestionMyProjectBean data);

    /**
     * Description:IQuestionBankHomeView
     * Time:2019-4-26   上午 10:44
     * Detail: 获取题库对应信息
     */
    void getSubjectInfoBean(SubjectInfoBean data);

}
