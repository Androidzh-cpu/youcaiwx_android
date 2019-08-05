package com.ucfo.youcai.presenter.presenterImpl.learncenter;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcai.common.ApiStores;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.address.StateStatusBean;
import com.ucfo.youcai.entity.learncenter.AddLearnPlanCheckCourseBean;
import com.ucfo.youcai.entity.learncenter.AddLearnPlanCheckTimeBean;
import com.ucfo.youcai.presenter.view.learncenter.IAddlearnPlanView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-7-19.  上午 9:50
 * Package: com.ucfo.youcai.presenter.presenterImpl.learncenter
 * FileName: AddLearnPlanPresenter
 * Description:TODO 添加学习计划
 */
public class AddLearnPlanPresenter implements IAddLearnPlanPresenter {
    private IAddlearnPlanView view;

    public AddLearnPlanPresenter(IAddlearnPlanView view) {
        this.view = view;
    }

    //选择课程
    @Override
    public void checkCourse(int user_id) {
        OkGo.<String>post(ApiStores.LEARNCENTER_CHECKCOURSE)
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
                                    AddLearnPlanCheckCourseBean courseBean = gson.fromJson(body, AddLearnPlanCheckCourseBean.class);
                                    view.getCheckCourseList(courseBean);
                                } else {
                                    view.getCheckCourseList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getCheckCourseList(null);
                        }
                    }
                });

    }

    //选择时间
    @Override
    public void checkCourseExamTime(int user_id, int course_id, int types) {
        OkGo.<String>post(ApiStores.LEARNCENTER_CHECKTIME)
                .params(Constant.USER_ID, user_id)
                .params(Constant.COURSE_ID, course_id)
                .params("types", types)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.showError();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
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
                                    AddLearnPlanCheckTimeBean courseBean = gson.fromJson(body, AddLearnPlanCheckTimeBean.class);
                                    view.getCheckTimeList(courseBean);
                                } else {
                                    view.getCheckTimeList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getCheckTimeList(null);
                        }
                    }
                });

    }

    @Override
    public void addLearnPlan(int user_id, int course_id, int types, String test_time) {
        OkGo.<String>post(ApiStores.LEARNCENTER_ADDLEARNPLAN)
                .params(Constant.USER_ID, user_id)
                .params(Constant.COURSE_ID, course_id)
                .params("types", types)
                .params("test_time", test_time)
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
                                    StateStatusBean courseBean = gson.fromJson(body, StateStatusBean.class);
                                    view.addLearnPlanResult(courseBean);
                                } else {
                                    view.getCheckTimeList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getCheckTimeList(null);
                        }
                    }
                });

    }
}
