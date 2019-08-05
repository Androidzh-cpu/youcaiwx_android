package com.ucfo.youcai.presenter.view.questionbank;

import com.ucfo.youcai.base.BaseView;
import com.ucfo.youcai.entity.questionbank.QuestionOnRecordBean;

/**
 * Author:29117
 * Time: 2019-4-30.  上午 10:06
 * Email:2911743255@qq.com
 * ClassName: IQuestionOnRecordView
 * Package: com.ucfo.youcai.presenter.view.questionbank
 * Description:TODO 答疑记录
 * Detail:TODO
 */
public interface IQuestionOnRecordView extends BaseView {

    /**
     * Description:IQuestionOnRecordView
     * Time:2019-4-30   上午 10:07
     * Detail: 获取答疑记录
     */
    void getQuestionRecordData(QuestionOnRecordBean dataBean);

}
