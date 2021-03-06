package com.ucfo.youcaiwx.presenter.presenterImpl.answer;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.answer.AnswerDetailBean;
import com.ucfo.youcaiwx.entity.answer.AnswerListDataBean;
import com.ucfo.youcaiwx.presenter.view.answer.ICourseAnswerListView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author:29117
 * Time: 2019-4-16.  下午 3:59
 * Email:2911743255@qq.com
 * ClassName: CourseCourseAnswerListPresenter
 * Description:TODO  课程问答列表和问答详情
 */
public class CourseCourseAnswerListPresenter implements ICourseAnswerListPresenter {
    private ICourseAnswerListView view;

    public CourseCourseAnswerListPresenter(ICourseAnswerListView view) {
        this.view = view;
    }

    /**
     * Description:CourseCourseAnswerListPresenter
     * Time:2019-4-16   下午 4:01
     * Detail:TODO  获取视频播放页问答列表
     */
    @Override
    public void getAnswerListData(int video_id, int section_id, int course_id, int package_id, int user_id) {
        OkGo.<String>post(ApiStores.ANSWER_GETANSWERLIST)
                .params(Constant.PACKAGE_ID, package_id)
                .params(Constant.COURSE_ID, course_id)
                .params(Constant.SECTION_ID, section_id)
                .params(Constant.VIDEO_ID, video_id)
                .params(Constant.USER_ID, user_id)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        view.showLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.showError();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        view.showLoadingFinish();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (body != null && !body.equals("")) {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                AnswerListDataBean answerListDataBean = gson.fromJson(body, AnswerListDataBean.class);
                                view.getAnswerListData(answerListDataBean);
                            } else {
                                view.getAnswerListData(null);
                            }
                        } else {
                            view.getAnswerListData(null);
                        }
                    }
                });
    }

    //问答详情
    @Override
    public void getAnswerDetail(int answer_id, int user_id) {
        OkGo.<String>post(ApiStores.ANSWER_GETANSWERDETAIL)
                .params(Constant.ANSWER_ID, answer_id)
                .params(Constant.USER_ID, user_id)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        view.showLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.showError();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        view.showLoadingFinish();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (body != null) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(body);
                                int code = jsonObject.optInt(Constant.CODE);
                                if (code == 200) {
                                    JSONObject optJSONObject = jsonObject.optJSONObject("data");
                                    if (optJSONObject.has("data")) {
                                        Gson gson = new Gson();
                                        AnswerDetailBean answerListDataBean = gson.fromJson(body, AnswerDetailBean.class);
                                        view.getAnswerDetailData(answerListDataBean);
                                    } else {
                                        view.getAnswerDetailData(null);
                                    }
                                } else {
                                    view.getAnswerDetailData(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getAnswerDetailData(null);
                        }
                    }
                });

    }
}
