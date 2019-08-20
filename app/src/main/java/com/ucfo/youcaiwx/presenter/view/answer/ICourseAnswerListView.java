package com.ucfo.youcaiwx.presenter.view.answer;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.answer.AnswerDetailBean;
import com.ucfo.youcaiwx.entity.answer.AnswerListDataBean;

/**
 * Author:29117
 * Time: 2019-4-16.  下午 3:58
 * Email:2911743255@qq.com
 * ClassName: ICourseAnswerListView
 * Description:TODO 问答页列表数据
 */
public interface ICourseAnswerListView extends BaseView {

    //问答列表
    void getAnswerListData(AnswerListDataBean dataBean);

    //问答详情
    void getAnswerDetailData(AnswerDetailBean dataBean);


}
