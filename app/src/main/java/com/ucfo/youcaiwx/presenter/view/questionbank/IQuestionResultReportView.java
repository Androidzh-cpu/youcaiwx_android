package com.ucfo.youcaiwx.presenter.view.questionbank;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.questionbank.ResultReportBean;

/**
 * Author: AND
 * Time: 2019-5-24.  下午 2:50
 * FileName: IQuestionResultReportView
 * Description:TODO
 * Detail:TODO 成绩统计详情页
 */
public interface IQuestionResultReportView extends BaseView {

    void getResultReportData(ResultReportBean data);

}
