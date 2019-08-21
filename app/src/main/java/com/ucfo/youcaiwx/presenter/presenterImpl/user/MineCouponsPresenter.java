package com.ucfo.youcaiwx.presenter.presenterImpl.user;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.user.MineCouponsBean;
import com.ucfo.youcaiwx.presenter.view.user.IMineCourponsView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-7-30.  上午 9:28
 * FileName: MineCouponsPresenter
 * Description:TODO 我的优惠券
 */
public class MineCouponsPresenter {
    private IMineCourponsView view;

    public MineCouponsPresenter(IMineCourponsView view) {
        this.view = view;
    }

    /**
     * Description:MineCouponsPresenter
     * Time:2019-7-30 上午 9:40
     * Detail:TODO 获取我的优惠券
     */
    public void getMineCouponsData(int user_id, int type) {
        OkGo.<String>post(ApiStores.MINE_COUPONS)
                .params(Constant.USER_ID, user_id)
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
                                    MineCouponsBean mineCouponsBean = gson.fromJson(body, MineCouponsBean.class);
                                    view.getMineCouponData(mineCouponsBean);
                                } else {
                                    view.getMineCouponData(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getMineCouponData(null);
                        }
                    }
                });
    }

    /**
     * 可用优惠券
     *
     * @param userId
     * @param packageId
     */
    public void getAivilableCoupon(int userId, int packageId) {
        OkGo.<String>post(ApiStores.PAY_GET_AVAILABLECOUPON)
                .params(Constant.USER_ID, userId)
                .params(Constant.PACKAGE_ID, packageId)
                .cacheMode(CacheMode.NO_CACHE)
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
                                    MineCouponsBean mineCouponsBean = gson.fromJson(body, MineCouponsBean.class);
                                    view.getMineCouponData(mineCouponsBean);
                                } else {
                                    view.getMineCouponData(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getMineCouponData(null);
                        }
                    }
                });

    }
}
