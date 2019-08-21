package com.ucfo.youcaiwx.presenter.presenterImpl.answer;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.answer.QuestionAnswerDetailBean;
import com.ucfo.youcaiwx.entity.answer.QuestionAnswerListBean;
import com.ucfo.youcaiwx.presenter.view.answer.IQuestionAnswerView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-5-30.  下午 2:33
 * FileName: QuestionAnswerPresenter
 * Description:TODO 题库答疑和课程答疑分开吧还是
 */
public class QuestionAnswerPresenter implements IQuestionAnswerPresenter {
    private IQuestionAnswerView view;

    public QuestionAnswerPresenter(IQuestionAnswerView view) {
        this.view = view;
    }

    //TODO 获取题库答疑列表
    @Override
    public void getQuestionAnswerList(int user_id, int question_id, int type) {
        OkGo.<String>post(ApiStores.ANSWERQUESTION_ANSWER_LIST)
                .tag(this)
                .params(Constant.USER_ID, user_id)
                .params(Constant.QUESTION_ID, question_id)
                .params(Constant.TYPE, type)
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
                        if (!body.equals("")) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(body);
                                int code = jsonObject.optInt(Constant.CODE);
                                if (code == 200) {
                                    QuestionAnswerListBean questionAnswerListBean = new Gson().fromJson(body, QuestionAnswerListBean.class);
                                    view.getAnswerList(questionAnswerListBean);
                                } else {
                                    view.getAnswerList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getAnswerList(null);
                        }
                    }
                });
    }

    //TODO 答疑详情
    @Override
    public void getQuestionAnswerDetail(int user_id, int answer_id) {
        OkGo.<String>post(ApiStores.ANSWERQUESTION_ANSWER_DETAIL)
                .params(Constant.USER_ID, user_id)
                .params(Constant.ANSWER_ID, answer_id)
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
                                    JSONObject optjsonobject = jsonObject.optJSONObject("data");
                                    if (optjsonobject.has("data")) {
                                        QuestionAnswerDetailBean bean = new Gson().fromJson(body, QuestionAnswerDetailBean.class);
                                        view.getQuestionAnswerDetail(bean);
                                    } else {
                                        view.getQuestionAnswerDetail(null);
                                    }
                                } else {
                                    view.getQuestionAnswerDetail(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getQuestionAnswerDetail(null);
                        }
                    }
                });
    }
}
