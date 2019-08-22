package com.ucfo.youcaiwx.presenter.presenterImpl.course;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.course.GetVideoPlayAuthBean;
import com.ucfo.youcaiwx.presenter.view.course.ICoursePlayerView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author:29117
 * Time: 2019-4-23.  上午 10:16
 * Email:2911743255@qq.com
 * ClassName: CoursePlayPresenter
 */
public class CoursePlayPresenter {
    private ICoursePlayerView view;

    public CoursePlayPresenter(ICoursePlayerView view) {
        this.view = view;
    }

    /**
     * Description:CoursePlayPresenter
     * Time:2019-4-23   上午 10:17
     * Detail: 视频收藏
     */
    public void getVideoCollect(int package_id, int course_id, int section_id, int video_id, int user_id, int collectState) {
        switch (collectState) {
            case 1://已收藏该视频,取消收藏操作
                collectState = 2;
                break;
            case 2://未收藏该视频,收藏操作
                collectState = 1;
                break;
        }
        int finalLoperation = collectState;
        OkGo.<String>post(ApiStores.COURSE_VIDEOCOLLECT)
                .params(Constant.USER_ID, user_id)//用户ID
                .params(Constant.PACKAGE_ID, package_id)//课程包ID
                .params(Constant.COURSE_ID, course_id)//课程id
                .params(Constant.SECTION_ID, section_id)//章节ID
                .params(Constant.VIDEO_ID, video_id)//视频ID
                .params("static", finalLoperation)//收藏状态
                .retryCount(1)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int code = jsonObject.optInt("code");//获取接口返回状态
                            if (code == 200) {
                                int resultState = jsonObject.optInt("data");
                                view.getVideoCollectResult(finalLoperation, resultState);
                            } else {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * Description:CoursePlayPresenter
     * Time:2019-4-23   上午 10:21
     * Detail: 获取视频播放凭证
     */
    public void getVideoPlayAuthor(String vid, int video_id, int course_id, int section_id, int user_id, int course_packageId) {
        OkGo.<String>post(ApiStores.COURSE_GETVIDEO_CREDENTIALS)
                .params(Constant.COURSE_VIDEOID, vid)//阿里库里的vid
                .params(Constant.USER_ID, user_id)//用户ID
                .params(Constant.PACKAGE_ID, course_packageId)//课程包ID
                .params(Constant.COURSE_ID, course_id)//课程id
                .params(Constant.SECTION_ID, section_id)//章节ID
                .params(Constant.VIDEO_ID, video_id)//小节视频ID
                .retryCount(1)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.getVideoPlayAuthor(null);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int code = jsonObject.optInt("code");//获取接口返回状态
                            if (code == 200) {
                                Gson gson = new Gson();
                                GetVideoPlayAuthBean videoPlayAuthBean = gson.fromJson(body, GetVideoPlayAuthBean.class);
                                view.getVideoPlayAuthor(videoPlayAuthBean);
                            } else {
                                view.getVideoPlayAuthor(null);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}