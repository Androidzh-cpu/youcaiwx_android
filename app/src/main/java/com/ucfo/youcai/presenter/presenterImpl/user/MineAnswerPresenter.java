package com.ucfo.youcai.presenter.presenterImpl.user;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcai.common.ApiStores;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.answer.AnswerListDataBean;
import com.ucfo.youcai.presenter.view.user.IMineAnswerView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-6-26.  上午 10:24
 * Package: com.ucfo.youcai.presenter.presenterImpl.user
 * FileName: MineAnswerPresenter
 * Description:TODO 个人中心我的答疑
 */
public class MineAnswerPresenter {
    private IMineAnswerView view;

    public MineAnswerPresenter(IMineAnswerView view) {
        this.view = view;
    }

    public void getMineAnswerList(int type, int user_id, int limit, int page) {
        String url = null;
        if (type == 1) {
            url = ApiStores.MINE_COURSE_ANSWER;
        } else {
            url = ApiStores.MINE_QUESTION_ANSWER;
        }
        OkGo.<String>post(url)
                .params(Constant.USER_ID, user_id)
                .params(Constant.LIMIT, 10)
                .params(Constant.PAGE, page)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
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
                                    AnswerListDataBean mineCourseBean = gson.fromJson(body, AnswerListDataBean.class);
                                    view.getMineAnswerList(mineCourseBean);
                                } else {
                                    view.getMineAnswerList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getMineAnswerList(null);
                        }
                    }
                });
    }
}
