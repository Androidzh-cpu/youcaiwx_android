package com.ucfo.youcaiwx.presenter.presenterImpl.home.education;

/**
 * Author: AND
 * Time: 2019-12-24.  上午 9:54
 * Package: com.ucfo.youcaiwx.presenter.presenterImpl.home.education
 * FileName: IEducationPsenter
 * Description:TODO　后续教育
 */
public interface IEducationPsenter {

    /**
     * 分类列表
     */
    void initClassification();

    /**
     * 课程列表
     *
     * @param type_id
     * @param sort
     * @param screening
     * @param limit
     * @param page
     */
    void initCourseListData(String type_id, String sort, String screening, int limit, int page);
}
