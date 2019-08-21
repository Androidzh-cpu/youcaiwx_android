package com.ucfo.youcaiwx.presenter.presenterImpl.user;

/**
 * Author: AND
 * Time: 2019-6-18.  下午 5:55
 * FileName: IMineCollectionPresenter
 * Description:TODO 收藏业务操作方法
 */
public interface IMineCollectionPresenter {

    //TODO 获取我的课程收藏列表
    void getMineCoursePackageCollectionList(int user_id, int page);

    //TODO 获取我的课程二级列表
    void getMineCourseCollectionList(int user_id, int package_id);

    //TODO 我的课程收藏播放目录
    void getCollectionDirList(int user_id, int package_id, int course_id);

    //TODO 题库收藏标签
    void getProjectList(int user_id);

    //TODO 题库收藏二级列表
    void getMineQuestioinCollectionList(int user_id, int course_id);

}
