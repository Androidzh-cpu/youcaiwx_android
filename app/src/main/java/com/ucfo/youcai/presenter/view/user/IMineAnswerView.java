package com.ucfo.youcai.presenter.view.user;

import com.ucfo.youcai.base.BaseView;
import com.ucfo.youcai.entity.answer.AnswerListDataBean;

/**
 * Author: AND
 * Time: 2019-6-26.  上午 10:10
 * Package: com.ucfo.youcai.presenter.view.user
 * FileName: IMineAnswerView
 * Description:TODO 我的答疑业务
 */
public interface IMineAnswerView extends BaseView {

    //TODO 获取我的答疑列表
    void getMineAnswerList(AnswerListDataBean result);

}
