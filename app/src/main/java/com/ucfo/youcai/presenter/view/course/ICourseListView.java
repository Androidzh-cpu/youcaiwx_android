package com.ucfo.youcai.presenter.view.course;

import com.ucfo.youcai.base.BaseView;
import com.ucfo.youcai.entity.course.CourseDataListBean;
import com.ucfo.youcai.entity.course.CourseSubjectsBean;

/**
 * Author:29117
 * Time: 2019-4-1.  上午 9:52
 * Email:2911743255@qq.com
 * ClassName: ICourseListView
 * Package: com.ucfo.youcai.presenter.view.course
 * Description:
 * Detail:
 */
public interface ICourseListView extends BaseView {

    void getCourseSubject(CourseSubjectsBean data);

    void getCourseDataList(CourseDataListBean result);

}
