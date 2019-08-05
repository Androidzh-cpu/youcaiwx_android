package com.ucfo.youcai.presenter.presenterImpl.course;

/**
 * Author:29117
 * Time: 2019-4-1.  上午 9:41
 * Email:2911743255@qq.com
 * ClassName: ICourseListPresenter
 * Package: com.ucfo.youcai.presenter.presenterImpl.course
 * Description:
 * Detail:
 */
public interface ICourseListPresenter {

    //获取课程分类列表
    void getCourseSubjects();

    //获取课程列表
    void getCourseDataList(int id,int user_id);
}
