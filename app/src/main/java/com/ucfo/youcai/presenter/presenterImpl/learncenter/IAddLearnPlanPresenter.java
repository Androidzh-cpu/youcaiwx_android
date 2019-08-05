package com.ucfo.youcai.presenter.presenterImpl.learncenter;

/**
 * Author: AND
 * Time: 2019-7-19.  上午 9:48
 * Package: com.ucfo.youcai.presenter.presenterImpl.learncenter
 * FileName: IAddLearnPlanPresenter
 * Description:TODO  添加学习计划
 */
public interface IAddLearnPlanPresenter {

    //选择专业课
    void checkCourse(int user_id);

    //选则对应课程考试时间
    void checkCourseExamTime(int user_id, int course_id, int types);

    //添加学习计划
    void addLearnPlan(int user_id, int course_id, int types, String test_time);
}
