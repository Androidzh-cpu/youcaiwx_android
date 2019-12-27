package com.ucfo.youcaiwx.presenter.presenterImpl.user;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.user.MineCPECourseBean;
import com.ucfo.youcaiwx.entity.user.MineCourseBean;
import com.ucfo.youcaiwx.entity.user.MineWatchRecordBean;
import com.ucfo.youcaiwx.presenter.view.user.IMineCourseView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-6-18.  下午 2:20
 * FileName: MineCoursePresenter
 * Description:TODO 我的课程业务
 */
public class MineCoursePresenter {
    private IMineCourseView view;

    public MineCoursePresenter(IMineCourseView view) {
        this.view = view;
    }

    /**
     * 获取我的后续教育课程
     */
    public void getMineCPECourseList(int user_id) {
        OkGo.<String>post(ApiStores.EDUCATION_COURSE_MINECOURSE)
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
                                    Object data = jsonObject.get(Constant.DATA);
                                    if (data instanceof JSONArray) {
                                        MineCPECourseBean mineCPECourseBean = new Gson().fromJson(body, MineCPECourseBean.class);
                                        view.getMineCPECourse(mineCPECourseBean);
                                    } else {
                                        view.getMineCPECourse(null);
                                    }
                                } else {
                                    view.getMineCPECourse(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getMineCPECourse(null);
                        }
                    }
                });
    }

    //TODO 我的课程列表
    public void getMineCourseList(int user_id) {
        OkGo.<String>post(ApiStores.MINE_COURSE)
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
                                    Object data = jsonObject.get(Constant.DATA);
                                    if (data instanceof JSONArray) {
                                        Gson gson = new Gson();
                                        MineCourseBean mineCourseBean = gson.fromJson(body, MineCourseBean.class);
                                        view.getMineCourse(mineCourseBean);
                                    } else {
                                        view.getMineCPECourse(null);
                                    }
                                } else {
                                    view.getMineCourse(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getMineCourse(null);
                        }
                    }
                });
    }

    //TODO 观看记录
    public void getMineWatcheRecordList(int user_id) {
        OkGo.<String>post(ApiStores.USER_WATCHRECORD)
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
                                    MineWatchRecordBean mineCourseBean = gson.fromJson(body, MineWatchRecordBean.class);
                                    view.getMineWatchRecord(mineCourseBean);
                                } else {
                                    view.getMineWatchRecord(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getMineWatchRecord(null);
                        }
                    }
                });
    }
}
