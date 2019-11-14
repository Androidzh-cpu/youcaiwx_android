package com.ucfo.youcaiwx.presenter.presenterImpl.answer;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.answer.AnsweringCourseDetailsBean;
import com.ucfo.youcaiwx.entity.answer.AnsweringQuestionDetailsBean;
import com.ucfo.youcaiwx.presenter.view.answer.IAnsweringCloselyDetailView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-11-12.  上午 10:52
 * Package: com.ucfo.youcaiwx.presenter.presenterImpl.answer
 * FileName: AnsweringCloselyDetailPresenter
 * Description:TODO 答疑追问详情业务
 */
public class AnsweringCloselyDetailPresenter {
    public IAnsweringCloselyDetailView answeringCloselyDetailView;

    public void setAnsweringCloselyDetailView(IAnsweringCloselyDetailView answeringCloselyDetailView) {
        this.answeringCloselyDetailView = answeringCloselyDetailView;
    }

    /**
     * 课程答疑详情
     */
    public void getCourseAnswerDetail(int answer_id, int user_id) {
        OkGo.<String>post(ApiStores.ANSWER_GETANSWERDETAIL)
                .params(Constant.ANSWER_ID, answer_id)
                .params(Constant.USER_ID, user_id)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        answeringCloselyDetailView.showLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        answeringCloselyDetailView.showError();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        answeringCloselyDetailView.showLoadingFinish();
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
                                    JSONObject optJSONObject = jsonObject.optJSONObject(Constant.DATA);
                                    if (optJSONObject.has("reply")) {
                                        Object reply = optJSONObject.get("reply");
                                        if (reply instanceof JSONArray) {
                                            Gson gson = new Gson();
                                            AnsweringCourseDetailsBean answeringCourseDetailsBean = gson.fromJson(body, AnsweringCourseDetailsBean.class);
                                            answeringCloselyDetailView.initCourseAnsweringDetail(answeringCourseDetailsBean);
                                        } else {
                                            answeringCloselyDetailView.initCourseAnsweringDetail(null);
                                        }
                                    } else {
                                        answeringCloselyDetailView.initCourseAnsweringDetail(null);
                                    }
                                } else {
                                    answeringCloselyDetailView.initCourseAnsweringDetail(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            answeringCloselyDetailView.initCourseAnsweringDetail(null);
                        }
                    }
                });

    }

    /**
     * 题库答疑详情
     *
     * @param answer_id
     * @param user_id
     */
    public void getQuestionAnswerDetail(int answer_id, int user_id) {
        OkGo.<String>post(ApiStores.ANSWERQUESTION_ANSWER_DETAIL)
                .params(Constant.ANSWER_ID, answer_id)
                .params(Constant.USER_ID, user_id)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        answeringCloselyDetailView.showLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        answeringCloselyDetailView.showError();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        answeringCloselyDetailView.showLoadingFinish();
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
                                    JSONObject optJSONObject = jsonObject.optJSONObject(Constant.DATA);
                                    if (optJSONObject.has("reply")) {
                                        Object reply = optJSONObject.get("reply");
                                        if (reply instanceof JSONArray) {
                                            Gson gson = new Gson();
                                            AnsweringQuestionDetailsBean questionDetailsBean = gson.fromJson(body, AnsweringQuestionDetailsBean.class);
                                            answeringCloselyDetailView.initQuestionAnsweringDetail(questionDetailsBean);
                                        } else {
                                            answeringCloselyDetailView.initQuestionAnsweringDetail(null);
                                        }
                                    } else {
                                        answeringCloselyDetailView.initQuestionAnsweringDetail(null);
                                    }
                                } else {
                                    answeringCloselyDetailView.initQuestionAnsweringDetail(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            answeringCloselyDetailView.initQuestionAnsweringDetail(null);
                        }
                    }
                });

    }
}
