package com.ucfo.youcai.presenter.view.questionbank;

import com.ucfo.youcai.base.BaseView;
import com.ucfo.youcai.entity.questionbank.ResultReportBean;

/**
 * Author: AND
 * Time: 2019-5-24.  下午 2:50
 * Package: com.ucfo.youcai.presenter.view.questionbank
 * FileName: IQuestionResultReportView
 * Description:TODO
 * Detail:TODO 成绩统计详情页
 */
public interface IQuestionResultReportView extends BaseView {

    void getResultReportData(ResultReportBean data);

}
