package com.ucfo.youcaiwx.presenter.view.course;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.course.CourseDataListBean;
import com.ucfo.youcaiwx.entity.course.CourseSubjectsBean;

/**
 * Author:29117
 * Time: 2019-4-1.  上午 9:52
 * Email:2911743255@qq.com
 * ClassName: ICourseListView
 * Description:
 * Detail:
 */
public interface ICourseListView extends BaseView {

    void getCourseSubject(CourseSubjectsBean data);

    void getCourseDataList(CourseDataListBean result);

}
