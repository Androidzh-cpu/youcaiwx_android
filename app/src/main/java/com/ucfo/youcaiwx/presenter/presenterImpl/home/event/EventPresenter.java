package com.ucfo.youcaiwx.presenter.presenterImpl.home.event;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.home.event.EventDetailedBean;
import com.ucfo.youcaiwx.entity.home.event.EventListBean;
import com.ucfo.youcaiwx.presenter.view.home.event.IEventView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-12-20.  下午 3:40
 * Package: com.ucfo.youcaiwx.presenter.presenterImpl.home.event
 * FileName: EventPresenter
 * Description:TODO 活动
 */
public class EventPresenter implements IEventPresenter {
    public IEventView view;

    public EventPresenter(IEventView view) {
        this.view = view;
    }

    @Override
    public void initEventList() {
        OkGo.<String>post(ApiStores.HOME_EVENTLIST)
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
                                    Object data = jsonObject.get(Constant.DATA);
                                    if (data instanceof JSONArray) {
                                        Gson gson = new Gson();
                                        EventListBean eventListBean = gson.fromJson(body, EventListBean.class);
                                        view.initEventList(eventListBean);
                                    } else {
                                        view.initEventList(null);
                                    }
                                } else {
                                    view.initEventList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.initEventList(null);
                        }
                    }
                });
    }

    /**
     * 活动详情
     */
    @Override
    public void initEventDetailed(String user_id, String id) {
        OkGo.<String>post(ApiStores.HOME_EVENTDETAILED)
                .tag(this)
                .params(Constant.USER_ID, user_id)
                .params("preview_id", id)
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
                                    Object data = jsonObject.get(Constant.DATA);
                                    if (data instanceof JSONObject) {
                                        Gson gson = new Gson();
                                        EventDetailedBean bean = gson.fromJson(body, EventDetailedBean.class);
                                        view.initEventDetailed(bean);
                                    } else {
                                        view.initEventDetailed(null);
                                    }
                                } else {
                                    view.initEventDetailed(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.initEventDetailed(null);
                        }
                    }
                });

    }

    /**
     * 活动报名
     *
     * @param userId
     * @param id
     * @param name
     * @param phone
     */
    @Override
    public void commitEventInformation(String userId, String id, String name, String phone) {
        //HOME_EVENT_COMMIT
        OkGo.<String>post(ApiStores.HOME_EVENT_COMMIT)
                .tag(this)
                .params(Constant.USER_ID, userId)
                .params("preview_id", id)
                .params("user_name", name)
                .params("mobile", phone)
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
                                String message = jsonObject.optString(Constant.MESSAGE);
                                if (code == 200) {
                                    JSONObject object = jsonObject.optJSONObject(Constant.DATA);
                                    if (object.has(Constant.STATUS)) {
                                        String string = object.optString(Constant.STATUS);
                                        if (!TextUtils.isEmpty(string)) {
                                            int i = Integer.parseInt(string);
                                            view.eventApplyResult(i, message);
                                        } else {
                                            view.eventApplyResult(0, message);
                                        }
                                    } else {
                                        view.eventApplyResult(0, message);
                                    }
                                } else {
                                    view.eventApplyResult(0, message);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.eventApplyResult(0, null);
                        }
                    }
                });

    }
}
