package com.ucfo.youcaiwx.presenter.view.home.education;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.home.education.EducationCourseListBean;
import com.ucfo.youcaiwx.entity.home.education.EducationTypeBean;

/**
 * Author: AND
 * Time: 2019-12-24.  上午 9:52
 * Package: com.ucfo.youcaiwx.presenter.view.home.education
 * FileName: IEducationView
 * Description:TODO 后续教育
 */
public interface IEducationView extends BaseView {

    /**
     * 获取后续教育分类数据
     */
    void initClassification(EducationTypeBean bean);

    /**
     * 获取分类列表
     *
     * @param bean
     */
    void initCourseList(EducationCourseListBean bean);

}
