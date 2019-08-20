package com.ucfo.youcaiwx.presenter.view.user;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.user.MineCourseBean;
import com.ucfo.youcaiwx.entity.user.MineCourseChildListBean;
import com.ucfo.youcaiwx.entity.user.MineCourseCollectionDirBean;
import com.ucfo.youcaiwx.entity.user.MineQuestionCollectionListBean;
import com.ucfo.youcaiwx.entity.user.ProjectListBean;

/**
 * Author: AND
 * Time: 2019-6-18.  下午 5:45
 * FileName: IMineCollectionView
 * Description:TODO 我的收藏业务
 */
public interface IMineCollectionView extends BaseView {

    //TODO 题库收藏列表
    void getPorjectList(ProjectListBean data);

    //TODO 题库收藏章节目录
    void getMineQuestioinCollectionList(MineQuestionCollectionListBean data);

    //TODO 我的课程套餐收藏列表
    void getMineCoursePackageCollectionList(MineCourseBean data);

    //TODO 课程收藏二级列表
    void getMineCourseCollectionChildList(MineCourseChildListBean data);

    //TODO 课程收藏播放目录
    void getCollectionDirList(MineCourseCollectionDirBean data);

}
