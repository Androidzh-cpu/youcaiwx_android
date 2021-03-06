package com.ucfo.youcaiwx.presenter.presenterImpl.pay;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.pay.PayAliPayResponseBean;
import com.ucfo.youcaiwx.entity.pay.PayWeChatResponseBean;
import com.ucfo.youcaiwx.presenter.view.pay.IPayMentView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-9-10.  上午 11:44
 * Package: com.ucfo.youcaiwx.presenter.presenterImpl.pay
 * FileName: PayMentPresenter
 * Description:TODO 正式支付,从服务器获取订单信息后调取App支付
 */
public class PayMentPresenter implements IPayMentPresenter {
    private IPayMentView view;

    public PayMentPresenter(IPayMentView view) {
        this.view = view;
    }

    /**
     * Description:PayMentPresenter
     * Time:2019-9-10 上午 11:47
     * Detail:TODO 获取微信订单信息
     */
    @Override
    public void initWeCheatOrderForm(String orderForm, String userID) {
        OkGo.<String>post(ApiStores.PAY_GET_WECHAT_PARAMAS)
                .params(Constant.ORDER_NUM, orderForm)
                .params(Constant.USER_ID, userID)
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
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(body);
                            int code = jsonObject.optInt(Constant.CODE);
                            if (code == 200) {
                                Object opt = jsonObject.get(Constant.DATA);
                                if (opt != null && opt instanceof JSONObject) {
                                    Gson gson = new Gson();
                                    PayWeChatResponseBean payWeChatResponseBean = gson.fromJson(body, PayWeChatResponseBean.class);
                                    view.initWeCheatOrderFormDetail(payWeChatResponseBean);
                                } else {
                                    view.initWeCheatOrderFormDetail(null);
                                }
                            } else {
                                view.initWeCheatOrderFormDetail(null);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * Description:PayMentPresenter
     * Time:2019-9-10 上午 11:47
     * Detail:TODO 获取支付宝订单
     */
    @Override
    public void initAlipayOrderForm(String orderForm, String userID) {
        OkGo.<String>post("")
                .retryCount(1)
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
                                    PayAliPayResponseBean payAliPayResponseBean = gson.fromJson(body, PayAliPayResponseBean.class);
                                    view.initAlipayOrderFormDetail(payAliPayResponseBean);
                                } else {
                                    view.initAlipayOrderFormDetail(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.initAlipayOrderFormDetail(null);
                        }
                    }
                });

    }

    /**
     * Description:PayMentPresenter
     * Time:2019-9-10 上午 11:48
     * Detail:TODO 从服务端检查最终支付结果
     */
    @Override
    public void checkPayResult(String orderFormNum) {
        OkGo.<String>post(ApiStores.PAY_CHECK_PAY_RESULT)
                .params(Constant.ORDER_NUM, orderFormNum)
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
                                    if (jsonObject.has(Constant.DATA)) {
                                        JSONObject objectData = jsonObject.optJSONObject(Constant.DATA);
                                        String string = objectData.optString(Constant.STATUS);
                                        if (TextUtils.isEmpty(string)) {
                                            view.checkPayResult(2);
                                        } else {
                                            int parseInt = Integer.parseInt(string);
                                            view.checkPayResult(parseInt);
                                        }
                                    } else {
                                        view.checkPayResult(2);
                                    }
                                } else {
                                    view.checkPayResult(2);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.checkPayResult(2);
                        }
                    }
                });
    }
}
