package com.ucfo.youcaiwx.presenter.view.answer;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.answer.AnsweringCourseDetailsBean;
import com.ucfo.youcaiwx.entity.answer.AnsweringQuestionDetailsBean;

/**
 * Author: AND
 * Time: 2019-11-12.  上午 10:27
 * Package: com.ucfo.youcaiwx.presenter.view.answer
 * FileName: IAnsweringCloselyDetailView
 * Description:TODO 支付详情追问
 */
public interface IAnsweringCloselyDetailView extends BaseView {

    /**
     * 课程答疑详情
     */
    void initCourseAnsweringDetail(AnsweringCourseDetailsBean data);

    /**
     * 题库答疑详情
     */
    void initQuestionAnsweringDetail(AnsweringQuestionDetailsBean data);


}
