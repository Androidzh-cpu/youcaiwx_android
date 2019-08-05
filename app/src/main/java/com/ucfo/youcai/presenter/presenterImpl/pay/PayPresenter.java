package com.ucfo.youcai.presenter.presenterImpl.pay;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcai.common.ApiStores;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.pay.OrderFormDetailBean;
import com.ucfo.youcai.presenter.view.IPayView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-8-5.  下午 3:31
 * Package: com.ucfo.youcai.presenter.presenterImpl.pay
 * FileName: PayPresenter
 * Description:TODO 订单支付业务
 */
public class PayPresenter implements IPayPresenter {
    private IPayView view;

    public PayPresenter(IPayView view) {
        this.view = view;
    }

    @Override
    public void getOrderFormDetail(int userId, int packageId) {
        OkGo.<String>post(ApiStores.PAY_GET_ORDERFORMDETAIL)
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
                                    OrderFormDetailBean bean = gson.fromJson(body, OrderFormDetailBean.class);
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
