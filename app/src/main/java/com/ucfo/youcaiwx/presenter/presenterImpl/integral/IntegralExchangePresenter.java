package com.ucfo.youcaiwx.presenter.presenterImpl.integral;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.integral.IntegralAddOrderNumBean;
import com.ucfo.youcaiwx.entity.integral.IntegralProductDetailBean;
import com.ucfo.youcaiwx.presenter.view.integral.IIntegralExchangeDetailView;
import com.ucfo.youcaiwx.presenter.view.integral.IIntegralExchangeView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-10-16.  下午 2:59
 * Package: com.ucfo.youcaiwx.presenter.presenterImpl.integral
 * FileName: IntegralExchangePresenter
 * Description:TODO 积分兑换
 */
public class IntegralExchangePresenter implements IIntegralExchangePresenter {
    private IIntegralExchangeDetailView integralExchangeDetailView;
    private IIntegralExchangeView integralExchangeView;

    public void setIntegralExchangeDetailView(IIntegralExchangeDetailView integralExchangeDetailView) {
        this.integralExchangeDetailView = integralExchangeDetailView;
    }

    public void setIntegralExchangeView(IIntegralExchangeView integralExchangeView) {
        this.integralExchangeView = integralExchangeView;
    }

    /**
     * 积分商品详情页
     */
    @Override
    public void inquireProductDetail(int user_id, String goods_id) {
        OkGo.<String>post(ApiStores.INTEGRAL_PRODUCT_DETAIL)
                .params(Constant.USER_ID, user_id)
                .params(Constant.PRODUCT_ID, goods_id)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        integralExchangeDetailView.showError();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        integralExchangeDetailView.showLoadingFinish();
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        integralExchangeDetailView.showLoading();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(body);
                            int code = jsonObject.optInt(Constant.CODE);
                            if (code == 200) {
                                if (jsonObject.has(Constant.DATA)) {
                                    IntegralProductDetailBean fromJson = new Gson().fromJson(body, IntegralProductDetailBean.class);
                                    integralExchangeDetailView.inquiryProductDetail(fromJson);
                                } else {
                                    integralExchangeDetailView.inquiryProductDetail(null);
                                }
                            } else {
                                integralExchangeDetailView.inquiryProductDetail(null);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 商品订单详情信息
     */
    @Override
    public void integralAddOrderNumber(int user_id, String good_id) {
        OkGo.<String>post(ApiStores.INTEGRAL_ADDORDER)
                .params(Constant.USER_ID, user_id)
                .params(Constant.PRODUCT_ID, good_id)
                .execute(new StringCallback() {
                    @Override
                    public void onFinish() {
                        super.onFinish();
                        integralExchangeView.showLoadingFinish();
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        integralExchangeView.showLoading();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(body);
                            int code = jsonObject.optInt(Constant.CODE);
                            String message = jsonObject.optString(Constant.MESSAGE);
                            if (code == 200) {
                                if (jsonObject.has(Constant.DATA)) {
                                    IntegralAddOrderNumBean integralAddOrderNumBean = new Gson().fromJson(body, IntegralAddOrderNumBean.class);
                                    integralExchangeView.integralAddOrderNumber(integralAddOrderNumBean, message);
                                } else {
                                    integralExchangeView.integralAddOrderNumber(null, message);
                                }
                            } else {
                                integralExchangeView.integralAddOrderNumber(null, message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
