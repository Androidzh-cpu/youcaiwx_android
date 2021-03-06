package com.ucfo.youcaiwx.presenter.presenterImpl.questionbank;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.questionbank.QuestionMyProjectBean;
import com.ucfo.youcaiwx.entity.questionbank.SubjectInfoBean;
import com.ucfo.youcaiwx.presenter.view.questionbank.IQuestionBankHomeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author:29117
 * Time: 2019-4-26.  上午 10:45
 * ClassName: QuestionBankHomePresenter
 * Description:TODO 题库首页业务
 */
public class QuestionBankHomePresenter {
    private IQuestionBankHomeView view;

    public QuestionBankHomePresenter(IQuestionBankHomeView view) {
        this.view = view;
    }

    /**
     * Description:QuestionBankHomePresenter
     * Time:2019-4-26   上午 10:46
     * Detail: TODO 获取已购买的可以用的题库
     */
    public void getMyProejctList(int user_id) {
        OkGo.<String>post(ApiStores.QUESTION_GETPROJECT)
                .params(Constant.USER_ID, user_id)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        //view.showLoading();
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
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int code = jsonObject.optInt(Constant.CODE);
                            if (code == 200) {
                                if (jsonObject.has(Constant.DATA)) {
                                    Object data = jsonObject.get(Constant.DATA);
                                    if (data instanceof JSONArray) {
                                        QuestionMyProjectBean questionMyProjectBean = new Gson().fromJson(response.body(), QuestionMyProjectBean.class);
                                        view.getMyProejctList(questionMyProjectBean);
                                    } else {
                                        view.getMyProejctList(null);
                                    }
                                } else {
                                    view.getMyProejctList(null);
                                }
                            } else {
                                view.getMyProejctList(null);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * Description:QuestionBankHomePresenter
     * Time:2019-4-26   下午 4:22
     * Detail:TODO 获取题库做题详细信息
     */
    public void getSubjectInfo(int user_id, int major_id) {
        OkGo.<String>post(ApiStores.QUESTION_GETPROJECTINFO)
                .tag(this)
                .params(Constant.USER_ID, user_id)
                .params(Constant.COURSE_ID, major_id)
                .execute(new StringCallback() {
                    @Override
                    public void onFinish() {
                        super.onFinish();
                        view.showLoadingFinish();
                    }

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
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (!body.equals("")) {
                            SubjectInfoBean subjectInfoBean = new Gson().fromJson(body, SubjectInfoBean.class);
                            view.getSubjectInfoBean(subjectInfoBean);
                        } else {
                            view.getSubjectInfoBean(null);
                        }
                    }
                });
    }
}
