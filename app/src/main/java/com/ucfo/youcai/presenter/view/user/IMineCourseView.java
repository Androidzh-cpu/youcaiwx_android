package com.ucfo.youcai.presenter.view.user;

import com.ucfo.youcai.base.BaseView;
import com.ucfo.youcai.entity.user.MineCourseBean;
import com.ucfo.youcai.entity.user.MineWatchRecordBean;

/**
 * Author: AND
 * Time: 2019-6-18.  下午 2:15
 * Package: com.ucfo.youcai.presenter.view.user
 * FileName: IMineCourseView
 * Description:TODO 我的课程
 */
public interface IMineCourseView extends BaseView {

    //TODO 我的课程列表
    void getMineCourse(MineCourseBean data);

    //TODO 观看记录列表
    void getMineWatchRecord(MineWatchRecordBean data);

}
