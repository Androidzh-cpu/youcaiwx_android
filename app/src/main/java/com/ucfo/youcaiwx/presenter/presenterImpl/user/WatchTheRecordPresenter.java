package com.ucfo.youcaiwx.presenter.presenterImpl.user;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.user.MineCPEWatchRecordBean;
import com.ucfo.youcaiwx.entity.user.MineWatchRecordBean;
import com.ucfo.youcaiwx.presenter.view.user.IWatchTheRecordView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-12-30.  上午 11:07
 * Package: com.ucfo.youcaiwx.presenter.presenterImpl.user
 * FileName: WatchTheRecordPresenter
 * Description:TODO 观看记录
 */
public class WatchTheRecordPresenter {
    private IWatchTheRecordView view;

    public WatchTheRecordPresenter(IWatchTheRecordView view) {
        this.view = view;
    }

    /**
     * 一般课程观看记录
     */
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

    /**
     * 后续教育观看记录
     */
    public void getMineCPEWatcheRecordList(int user_id) {
        OkGo.<String>post(ApiStores.EDUCATION_COURSE_WATCHRECORD)
                .tag(this)
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
                                        MineCPEWatchRecordBean mineCPEWatchRecordBean = gson.fromJson(body, MineCPEWatchRecordBean.class);
                                        view.getMineCPEWatchRecord(mineCPEWatchRecordBean);
                                    } else {
                                        view.getMineCPEWatchRecord(null);
                                    }
                                } else {
                                    view.getMineCPEWatchRecord(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getMineCPEWatchRecord(null);
                        }
                    }
                });
    }
}
