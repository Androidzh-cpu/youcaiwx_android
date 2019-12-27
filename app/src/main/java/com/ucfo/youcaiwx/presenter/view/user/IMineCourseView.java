package com.ucfo.youcaiwx.presenter.view.user;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.user.MineCPECourseBean;
import com.ucfo.youcaiwx.entity.user.MineCourseBean;
import com.ucfo.youcaiwx.entity.user.MineWatchRecordBean;

/**
 * Author: AND
 * Time: 2019-6-18.  下午 2:15
 * FileName: IMineCourseView
 * Description:TODO 我的课程
 */
public interface IMineCourseView extends BaseView {

    //TODO 我的课程列表
    void getMineCourse(MineCourseBean data);

    //TODO 我的CPE课程
    void getMineCPECourse(MineCPECourseBean data);

    //TODO 观看记录列表
    void getMineWatchRecord(MineWatchRecordBean data);

}
