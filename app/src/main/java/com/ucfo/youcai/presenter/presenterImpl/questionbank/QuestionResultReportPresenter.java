package com.ucfo.youcai.presenter.presenterImpl.questionbank;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcai.common.ApiStores;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.questionbank.ResultReportBean;
import com.ucfo.youcai.presenter.view.questionbank.IQuestionResultReportView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-5-24.  下午 2:49
 * Package: com.ucfo.youcai.presenter.presenterImpl.questionbank
 * FileName: QuestionResultReportPresenter
 * Description:TODO 成绩统计业务操作
 */
public class QuestionResultReportPresenter {
    private IQuestionResultReportView view;

    public QuestionResultReportPresenter(IQuestionResultReportView view) {
        this.view = view;
    }

    /**
     * Description:QuestionResultReportPresenter
     * Time:2019-5-24 下午 2:55
     * Detail:TODO 获取我的成绩
     */
    public void getResultReportData(int user_id, int paper_id) {
        OkGo.<String>post(ApiStores.QUESTION_GETRESULTREPORT)
                .tag(this)
                .params(Constant.USER_ID, user_id)//用户ID
                .params(Constant.PAPER_ID, paper_id)//试卷ID
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
                                    Gson gson = new Gson();
                                    ResultReportBean reportBean = gson.fromJson(body, ResultReportBean.class);
                                    view.getResultReportData(reportBean);
                                } else {
                                    view.getResultReportData(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getResultReportData(null);
                        }
                    }
                });

    }

    /**
     * Description:QuestionResultReportPresenter
     * Time:2019-5-24 下午 2:55
     * Detail:TODO 获取错题中心我的成绩
     */
    public void getErrorCenterResultReportData(int used_time, int course_id, int section_id, String question_content) {
        OkGo.<String>post(ApiStores.QUESTION_ERRORCENTER_GETRESULTREPORT)
                .tag(this)
                .params(Constant.USED_TIME, used_time)//时间
                .params(Constant.COURSE_ID, course_id)//专业
                .params(Constant.SECTION_ID, section_id)//章
                .params(Constant.QUESTION_CONTENT, question_content)//题目信息
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
                                    Gson gson = new Gson();
                                    ResultReportBean reportBean = gson.fromJson(body, ResultReportBean.class);
                                    view.getResultReportData(reportBean);
                                } else {
                                    view.getResultReportData(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getResultReportData(null);
                        }
                    }
                });

    }

    /**
     * Description:QuestionResultReportPresenter
     * Time:2019-5-24 下午 2:55
     * Detail:TODO 获取错题中心我的成绩
     */
    public void getFreeExperienceResultReportData(int used_time, int user_id, String question_content) {
        OkGo.<String>post(ApiStores.QUESTION_FREE_EXPERIENCE_RESULTREPORT)
                .tag(this)
                .params(Constant.USED_TIME, used_time)
                .params(Constant.USER_ID, user_id)
                .params(Constant.QUESTION_CONTENT, question_content)
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
                                    Gson gson = new Gson();
                                    ResultReportBean reportBean = gson.fromJson(body, ResultReportBean.class);
                                    view.getResultReportData(reportBean);
                                } else {
                                    view.getResultReportData(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getResultReportData(null);
                        }
                    }
                });

    }

}
