package com.ucfo.youcai.presenter.presenterImpl.home;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcai.common.ApiStores;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.home.MessageCenterHomeBean;
import com.ucfo.youcai.entity.home.MessageCenterNoticeBean;
import com.ucfo.youcai.presenter.view.home.IMessageCenterView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-8-9.  下午 1:45
 * Package: com.ucfo.youcai.presenter.presenterImpl.home
 * FileName: MessageCenterPresenter
 * Description:TODO 消息公告
 */
public class MessageCenterPresenter implements IMessageCenterPresenter {
    private IMessageCenterView view;

    public MessageCenterPresenter(IMessageCenterView view) {
        this.view = view;
    }

    @Override
    public void getMessageHome(int userId) {
        OkGo.<String>post(ApiStores.MESSAGE_CENTER_HOME)
                .params(Constant.USER_ID, userId)
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
                                    MessageCenterHomeBean homeBean = gson.fromJson(body, MessageCenterHomeBean.class);
                                    view.getMessageCenterHome(homeBean);
                                } else {
                                    view.getMessageCenterHome(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getMessageCenterHome(null);
                        }
                    }
                });
    }

    @Override
    public void getNoticeList(int userId, int page, int types) {
        OkGo.<String>post(ApiStores.MESSAGE_CENTER_NOTICE)
                .params(Constant.USER_ID, userId)
                .params(Constant.LIMIT, 10)
                .params(Constant.PAGE, page)
                .params("types", types)
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
                                    MessageCenterNoticeBean noticeBean = gson.fromJson(body, MessageCenterNoticeBean.class);
                                    view.getMessageCenterNoticeList(noticeBean);
                                } else {
                                    view.getMessageCenterNoticeList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getMessageCenterNoticeList(null);
                        }
                    }
                });

    }
}
