package com.ucfo.youcaiwx.presenter.presenterImpl.questionbank;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.questionbank.QuestionOnRecordBean;
import com.ucfo.youcaiwx.presenter.view.questionbank.IQuestionOnRecordView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author:29117
 * Time: 2019-4-30.  上午 10:08
 * ClassName: QuestionBankRecordPresenter
 * Description:TODO  答题记录
 */
public class QuestionBankRecordPresenter {
    private IQuestionOnRecordView view;

    public QuestionBankRecordPresenter(IQuestionOnRecordView view) {
        this.view = view;
    }

    /**
     * Description:QuestionBankRecordPresenter
     * Time:2019-4-30   上午 10:08
     * Detail: 获取我的答题记录
     */
    public void getQestionBankRecordData(int user_id, int course_id, int pageIndex) {

        OkGo.<String>post(ApiStores.QUESTION_GETQUSTIONONRECORD)
                .tag(this)
                .params(Constant.USER_ID, user_id)
                .params(Constant.COURSE_ID, course_id)
                .params(Constant.PAGE, pageIndex)
                .params(Constant.LIMIT, 10)
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
                        if (!body.equals("") && body != null) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(body);
                                int code = jsonObject.optInt(Constant.CODE);
                                if (code == 200) {
                                    QuestionOnRecordBean questionOnRecordBean = new Gson().fromJson(body, QuestionOnRecordBean.class);
                                    view.getQuestionRecordData(questionOnRecordBean);
                                } else {
                                    view.getQuestionRecordData(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getQuestionRecordData(null);
                        }
                    }
                });
    }
}
