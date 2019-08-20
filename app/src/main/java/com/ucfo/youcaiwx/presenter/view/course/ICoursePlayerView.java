package com.ucfo.youcaiwx.presenter.view.course;

import com.ucfo.youcaiwx.entity.course.GetVideoPlayAuthBean;

/**
 * Author:29117
 * Time: 2019-4-23.  上午 9:57
 * Email:2911743255@qq.com
 * ClassName: ICoursePlayerView
 */
public interface ICoursePlayerView {

    //获取视频播放凭证
    void getVideoPlayAuthor(GetVideoPlayAuthBean data);

    //视频收藏结果
    void getVideoCollectResult(int data, int result);

}