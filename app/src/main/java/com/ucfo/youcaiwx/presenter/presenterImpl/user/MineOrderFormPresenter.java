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
import com.ucfo.youcaiwx.entity.user.MineOrderFormDetailBean;
import com.ucfo.youcaiwx.entity.user.MineOrderListBean;
import com.ucfo.youcaiwx.presenter.view.user.IMineOrderFromView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-6-21.  下午 2:26
 * FileName: MineOrderFormPresenter
 * Description:TODO 订单操作业务
 */
public class MineOrderFormPresenter implements IMineOrderFormPresenter {
    private IMineOrderFromView view;

    public MineOrderFormPresenter(IMineOrderFromView view) {
        this.view = view;
    }

    //获取我的订单
    @Override
    public void getMineOrderFromList(int user_id) {
        OkGo.<String>post(ApiStores.MINE_ORDERFORM_LIST)
                .params(Constant.USER_ID, user_id)
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
                                    MineOrderListBean mineOrderListBean = gson.fromJson(body, MineOrderListBean.class);
                                    view.getMineOrderFromList(mineOrderListBean);
                                } else {
                                    view.getMineOrderFromList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getMineOrderFromList(null);
                        }
                    }
                });
    }

    @Override
    public void cancelOrderForm(int user_id, String orderNumber) {
        OkGo.<String>post(ApiStores.MINE_ORDERFORM_CANCEL)
                .params(Constant.USER_ID, user_id)
                .params(Constant.ORDER_NUM, orderNumber)
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
                        view.cancelOrderForm(null);
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
                                    view.cancelOrderForm(bean);
                                } else {
                                    view.cancelOrderForm(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.cancelOrderForm(null);
                        }
                    }
                });
    }

    //TODO 获取订单详情
    @Override
    public void getOrderFormDetail(int user_id, int status, String orderNumber) {
        String url = null;
        switch (status) {
            case 1://已付款
            case 3://订单已取消
                url = ApiStores.MINE_ORDERFORM_HASBEENDETAIL;
                break;
            case 2://未付款
                url = ApiStores.MINE_ORDERFORM_NOTPAYDETAIL;
                break;
            default:
                break;
        }
        OkGo.<String>post(url)
                .params(Constant.USER_ID, user_id)
                .params(Constant.ORDER_NUM, orderNumber)
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
                                    MineOrderFormDetailBean bean = gson.fromJson(body, MineOrderFormDetailBean.class);
                                    view.getOrderFormDetail(bean);
                                } else {
                                    view.getOrderFormDetail(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getOrderFormDetail(null);
                        }
                    }
                });
    }
}
