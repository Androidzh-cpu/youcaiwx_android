package com.ucfo.youcai.presenter.presenterImpl.learncenter;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcai.common.ApiStores;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.learncenter.LearnPlanDetailBean;
import com.ucfo.youcai.entity.learncenter.LearnPlanDetailVideoBean;
import com.ucfo.youcai.presenter.view.learncenter.ILearnPlanDetailView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-7-22.  上午 10:32
 * Package: com.ucfo.youcai.presenter.presenterImpl.learncenter
 * FileName: LearnPlanDetailPresenter
 * Description:TODO 学习计划详情
 */
public class LearnPlanDetailPresenter {
    private ILearnPlanDetailView view;

    public LearnPlanDetailPresenter(ILearnPlanDetailView view) {
        this.view = view;
    }

    //TODO 获取计划详情
    public void getLearnPlanDetailList(int user_id, int course_id, int plan_id, int type) {
        OkGo.<String>post(ApiStores.LEARNCENTER_LEARNPLANDETAIL)
                .tag(this)
                .params(Constant.USER_ID, user_id)
                .params(Constant.COURSE_ID, course_id)
                .params("plan_id", plan_id)
                .params("types", type)
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
                                    LearnPlanDetailBean detailBean = gson.fromJson(body, LearnPlanDetailBean.class);
                                    view.getPlanDetailList(detailBean);
                                } else {
                                    view.getPlanDetailList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getPlanDetailList(null);
                        }
                    }
                });

    }

    //TODO 获取计划详情视频列表
    public void getLearnPlanDetailVideoList(int user_id, int course_id, int plan_id, int type, int sameday, int days) {
        OkGo.<String>post(ApiStores.LEARNCENTER_LEARNPLANDETAILVIDEO)
                .tag(this)
                .params(Constant.USER_ID, user_id)
                .params(Constant.COURSE_ID, course_id)
                .params("plan_id", plan_id)
                .params("types", type)
                .params("sameday", sameday)
                .params("days", days)
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
                                    LearnPlanDetailVideoBean detailBean = gson.fromJson(body, LearnPlanDetailVideoBean.class);
                                    view.getPlanDetailVideoList(detailBean);
                                } else {
                                    view.getPlanDetailVideoList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getPlanDetailVideoList(null);
                        }
                    }
                });

    }

}
