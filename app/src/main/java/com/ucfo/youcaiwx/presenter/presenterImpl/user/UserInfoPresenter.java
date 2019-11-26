package com.ucfo.youcaiwx.presenter.presenterImpl.user;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.address.StateStatusBean;
import com.ucfo.youcaiwx.entity.user.UserInfoBean;
import com.ucfo.youcaiwx.presenter.view.user.IUserInfoView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author:29117
 * Time: 2019-4-2.  上午 11:27
 * Email:2911743255@qq.com
 * ClassName: UserInfoPresenter
 * Description:TODO 用户个人信息业务
 */
public class UserInfoPresenter implements IUserInfoPresenter {
    private IUserInfoView view;

    public UserInfoPresenter(IUserInfoView view) {
        this.view = view;
    }

    //TODO 获取个人基本信息
    @Override
    public void getUserInfo(int user_id) {
        OkGo.<String>post(ApiStores.USER_GETUSERINFO)
                .params(Constant.USER_ID, user_id)
                .retryCount(1)
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
                        view.getUserInfo(null);
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
                                    UserInfoBean userInfoBean = gson.fromJson(body, UserInfoBean.class);
                                    view.getUserInfo(userInfoBean);
                                } else {
                                    view.getUserInfo(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getUserInfo(null);
                        }
                    }
                });
    }


    // TODO 修改个人信息
    @Override
    public void retoucheInfo(int user_id, int type, String content) {
        OkGo.<String>post(ApiStores.USER_RETOUCHE_USERINFO)
                .params(Constant.USER_ID, user_id)
                .params(Constant.TYPE, type)
                .params(Constant.CONTENT, content)
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
                        view.retouceResult(null);
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
                                    StateStatusBean bean = gson.fromJson(body, StateStatusBean.class);
                                    view.retouceResult(bean);
                                } else {
                                    view.retouceResult(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.retouceResult(null);
                        }
                    }
                });
    }

    //修改密码
    @Override
    public void modifyPassword(int user_id, String oldpassword, String newPassword, String newPasswordAgain) {
        OkGo.<String>post(ApiStores.USER_RETOUCHE_PASSWORD)
                .params(Constant.USER_ID, user_id)
                .params("password", oldpassword)
                .params("pwd", newPassword)
                .params("repwd", newPasswordAgain)
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
                        view.retouceResult(null);
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
                                    Gson gson = new Gson();
                                    StateStatusBean bean = gson.fromJson(body, StateStatusBean.class);
                                    view.retouceResult(bean);
                                } else {
                                    view.retouceResult(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.retouceResult(null);
                        }
                    }
                });
    }
}
