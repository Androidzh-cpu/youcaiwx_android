package com.ucfo.youcaiwx.presenter.presenterImpl.integral;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.integral.EarnIntegralBean;
import com.ucfo.youcaiwx.entity.integral.IntegralCouponsListBean;
import com.ucfo.youcaiwx.entity.integral.IntegralDetailBean;
import com.ucfo.youcaiwx.entity.integral.IntegralExchangeRecordBean;
import com.ucfo.youcaiwx.entity.integral.IntegralProductListBean;
import com.ucfo.youcaiwx.entity.integral.IntegralShopHomeBean;
import com.ucfo.youcaiwx.presenter.view.integral.IIntegralDetaillView;
import com.ucfo.youcaiwx.presenter.view.integral.IIntegralEarnView;
import com.ucfo.youcaiwx.presenter.view.integral.IIntegralExchangeView;
import com.ucfo.youcaiwx.presenter.view.integral.IIntegralGoodsListView;
import com.ucfo.youcaiwx.presenter.view.integral.IIntegralHomeView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-10-11.  下午 3:06
 * Package: com.ucfo.youcaiwx.presenter.presenterImpl.integral
 * FileName: IntegralPresenter
 * Description:TODO 积分业务
 */
public class IntegralPresenter implements IIntegralPresenter {
    //积分商城首页
    private IIntegralHomeView integralHomeView;
    //积分商品列表(优惠券和实物商品)
    private IIntegralGoodsListView integralGoodsListView;
    //赚取积分
    private IIntegralEarnView integralEarnView;
    //积分兑换记录
    private IIntegralExchangeView iIntegralExchangeView;
    //积分明细
    private IIntegralDetaillView integralDetaillView;

    public IntegralPresenter(IIntegralExchangeView iIntegralExchangeView) {
        this.iIntegralExchangeView = iIntegralExchangeView;
    }

    public IntegralPresenter(IIntegralDetaillView integralDetaillView) {
        this.integralDetaillView = integralDetaillView;
    }

    public IntegralPresenter(IIntegralEarnView integralEarnView) {
        this.integralEarnView = integralEarnView;
    }

    public IntegralPresenter(IIntegralGoodsListView integralGoodsListView) {
        this.integralGoodsListView = integralGoodsListView;
    }

    public IntegralPresenter(IIntegralHomeView integralHomeView) {
        this.integralHomeView = integralHomeView;
    }

    /**
     * 我的积分查询
     */
    @Override
    public void inquireIntegral(int userId) {
        OkGo.<String>post(ApiStores.INTEGRAL_MINEINTEGRAL)
                .params(Constant.USER_ID, userId)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        integralHomeView.inquiryIntegral(String.valueOf(0));
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        integralHomeView.inquiryIntegral(String.valueOf("***"));
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
                                    JSONObject optjsonobject = jsonObject.optJSONObject(Constant.DATA);
                                    String optString = optjsonobject.optString(Constant.INTEGRAL);
                                    integralHomeView.inquiryIntegral(optString);
                                } else {
                                    integralHomeView.inquiryIntegral(String.valueOf(0));
                                }
                            } else {
                                integralHomeView.inquiryIntegral(String.valueOf(0));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 商品列表(积分首页)
     */
    @Override
    public void inquireIntegralProductHome() {
        OkGo.<String>post(ApiStores.INTEGRAL_HOME)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        integralHomeView.showError();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        integralHomeView.showLoadingFinish();
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        integralHomeView.showLoading();
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
                                    IntegralShopHomeBean homeBean = new Gson().fromJson(body, IntegralShopHomeBean.class);
                                    integralHomeView.inqieryIntegralHome(homeBean);
                                } else {
                                    integralHomeView.inqieryIntegralHome(null);
                                }
                            } else {
                                integralHomeView.inqieryIntegralHome(null);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * 全部积分优惠券列表
     */
    @Override
    public void inquereIntegralCouponList() {
        OkGo.<String>post(ApiStores.INTEGRAL_COUPONLIST)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        integralGoodsListView.showError();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        integralGoodsListView.showLoadingFinish();
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        integralGoodsListView.showLoading();
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
                                    IntegralCouponsListBean listBean = new Gson().fromJson(body, IntegralCouponsListBean.class);
                                    integralGoodsListView.inqueryIntegralCouponList(listBean);
                                } else {
                                    integralGoodsListView.inqueryIntegralCouponList(null);
                                }
                            } else {
                                integralGoodsListView.inqueryIntegralCouponList(null);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 全部积分商品(不包含优惠券商品)
     */
    @Override
    public void inquireIntegralProductList() {
        OkGo.<String>post(ApiStores.INTEGRAL_PRODUCTLIST)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        integralGoodsListView.showError();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        integralGoodsListView.showLoadingFinish();
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        integralGoodsListView.showLoading();
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
                                    IntegralProductListBean listBean = new Gson().fromJson(body, IntegralProductListBean.class);
                                    integralGoodsListView.inqueryIntegralProductList(listBean);
                                } else {
                                    integralGoodsListView.inqueryIntegralProductList(null);
                                }
                            } else {
                                integralGoodsListView.inqueryIntegralProductList(null);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 积分兑换记录
     */
    @Override
    public void inquireIntegralExchangeRecord(int userId) {
        OkGo.<String>post(ApiStores.INTEGRAL_EXCHANGERECORD)
                .params(Constant.USER_ID, userId)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        iIntegralExchangeView.showError();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        iIntegralExchangeView.showLoadingFinish();
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        iIntegralExchangeView.showLoading();
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
                                    IntegralExchangeRecordBean listBean = new Gson().fromJson(body, IntegralExchangeRecordBean.class);
                                    iIntegralExchangeView.integralExchangeRecord(listBean);
                                } else {
                                    iIntegralExchangeView.integralExchangeRecord(null);
                                }
                            } else {
                                iIntegralExchangeView.integralExchangeRecord(null);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * 积分明细
     */
    @Override
    public void inquireIntegralDetail(int userId) {
        OkGo.<String>post(ApiStores.INTEGRAL_DETAIL)
                .params(Constant.USER_ID, userId)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        integralDetaillView.showError();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        integralDetaillView.showLoadingFinish();
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        integralDetaillView.showLoading();
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
                                    IntegralDetailBean listBean = new Gson().fromJson(body, IntegralDetailBean.class);
                                    integralDetaillView.integralDetail(listBean);
                                } else {
                                    integralDetaillView.integralDetail(null);
                                }
                            } else {
                                integralDetaillView.integralDetail(null);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * 赚积分
     */
    @Override
    public void earnIntegral() {
        OkGo.<String>post(ApiStores.INTEGRAL_EARNPOINT)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        integralEarnView.showError();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        integralEarnView.showLoadingFinish();
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        integralEarnView.showLoading();
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
                                    EarnIntegralBean listBean = new Gson().fromJson(body, EarnIntegralBean.class);
                                    integralEarnView.earnItegral(listBean);
                                } else {
                                    integralEarnView.earnItegral(null);
                                }
                            } else {
                                integralEarnView.earnItegral(null);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
