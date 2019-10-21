package com.ucfo.youcaiwx.presenter.presenterImpl.answer;

/**
 * Author:29117
 * Time: 2019-4-16.  下午 3:59
 * Email:2911743255@qq.com
 * ClassName: ICourseAnswerListPresenter
 */
public interface ICourseAnswerListPresenter {

    /**
     * Description:ICourseAnswerListPresenter
     * Time:2019-4-17   下午 3:13
     * Detail:TODO 获取问答列表
     */
    void getAnswerListData(int video_id, int section_id, int course_id, int package_id);

    /**
     * Description:ICourseAnswerListPresenter
     * Time:2019-4-17   下午 3:15
     * Detail: TODO 获取问答详情
     */
    void getAnswerDetail(int answer_id,int user_id);


}
