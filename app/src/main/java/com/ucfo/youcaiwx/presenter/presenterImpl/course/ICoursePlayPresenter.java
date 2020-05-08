package com.ucfo.youcaiwx.presenter.presenterImpl.course;

import com.ucfo.youcaiwx.entity.course.CourseSocketBean;

/**
 * Author: AND
 * Time: 2020-5-8.  下午 2:36
 * Package: com.ucfo.youcaiwx.presenter.presenterImpl.course
 * FileName: ICoursePlayPresenter
 * Description:TODO 视频播放
 */
public interface ICoursePlayPresenter {

    /**
     * Description:CoursePlayPresenter
     * Time:2019-4-23   上午 10:17
     * Detail: 视频收藏
     */
    void getVideoCollect(int package_id, int course_id, int section_id, int video_id, int user_id, int collectState);

    /**
     * Description:CoursePlayPresenter
     * Time:2019-4-23   上午 10:21
     * Detail: 获取视频播放凭证
     */
    void getVideoPlayAuthor(String vid, int video_id, int course_id, int section_id, int user_id, int course_packageId);

    /**
     * 重载获取视频凭证(过了好几个月后我才想起来这个方法,好像是后续教育用到了,唉)
     */
    void getVideoPlayAuthor(String vid, int video_id);

    /**
     * 获取课程包的购买状态
     */
    void getCoursePackageBuyState(String package_id, String user_id, String course_Source);

    /**
     * 每次播放时向服务端发送信息记录(我也不知道)
     */
    void sendFirstSocket(CourseSocketBean bean);

    /**
     * 唉,又是后续教育
     */
    void sendFirstSocketByEducation(CourseSocketBean bean);

    /**
     * 检查是否签到
     */
    void checkWetherSignin(String user_id, String course_id, String video_id);

    /**
     * 签到
     */
    void educationSignin(String user_id, String course_id, String video_id);
}
