package com.ucfo.youcaiwx.presenter.presenterImpl.learncenter;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.learncenter.LearncenterHomeBean;
import com.ucfo.youcaiwx.entity.learncenter.StudyClockInBean;
import com.ucfo.youcaiwx.entity.learncenter.UnFinishPlanBean;
import com.ucfo.youcaiwx.presenter.view.learncenter.ILearncenterHomeView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-7-18.  上午 10:18
 * FileName: LearncenterHomePresenter
 * Description:TODO 学习中心首页
 */
public class LearncenterHomePresenter {
    private ILearncenterHomeView view;

    public LearncenterHomePresenter(ILearncenterHomeView view) {
        this.view = view;
    }

    //TODO 首页数据
    public void learncenterHome(int user_id) {
        String finalUserid;
        if (user_id == 0) {
            finalUserid = "";
        } else {
            finalUserid = String.valueOf(user_id);
        }
        OkGo.<String>post(ApiStores.LEARNCENTER_HOME)
                .params(Constant.USER_ID, finalUserid)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        //view.showLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.learncenterHome(null);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        //view.showLoadingFinish();
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
                                    LearncenterHomeBean learncenterHomeBean = gson.fromJson(body, LearncenterHomeBean.class);
                                    view.learncenterHome(learncenterHomeBean);
                                } else {
                                    view.learncenterHome(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.learncenterHome(null);
                        }
                    }
                });
    }

    //获取未完成的学习计划
    public void getUnFinishPlanList(int user_id) {
        OkGo.<String>post(ApiStores.LEARNCENTER_UNFINISHPLAN)
                .params(Constant.USER_ID, user_id)
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
                                    UnFinishPlanBean bean = gson.fromJson(body, UnFinishPlanBean.class);
                                    view.getUnFinishPlan(bean);
                                } else {
                                    view.getUnFinishPlan(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getUnFinishPlan(null);
                        }
                    }
                });
    }

    //TODO 打卡签到
    public void signDayCard(int user_id) {
        OkGo.<String>post(ApiStores.LEARNCENTER_CLOCKIN)
                .params(Constant.USER_ID, user_id)
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
                        view.studyClockInResult(null);
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
                            Gson gson = new Gson();
                            StudyClockInBean bean = gson.fromJson(body, StudyClockInBean.class);
                            view.studyClockInResult(bean);
                        } else {
                            view.studyClockInResult(null);
                        }
                    }
                });
    }

}
