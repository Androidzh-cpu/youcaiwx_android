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
import com.ucfo.youcaiwx.entity.pay.CommitOrderFormBean;
import com.ucfo.youcaiwx.entity.pay.InvoiceInfoBean;
import com.ucfo.youcaiwx.entity.pay.OrderFormDetailBean;
import com.ucfo.youcaiwx.presenter.view.IPayView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-8-5.  下午 3:31
 * FileName: PayPresenter
 * Description:TODO 订单支付业务
 */
public class PayPresenter implements IPayPresenter {
    private IPayView view;

    public PayPresenter(IPayView view) {
        this.view = view;
    }

    /**
     * 订单详情
     */
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

    /**
     * 添加订单
     */
    @Override
    public void commitOrderForm(int userId, int packageId, int isLive, int addressId, int user_coupon_id, InvoiceInfoBean invoiceInfoBean) {
        int haveCoupon, invoiceType = 0, userOrcompany = 0;
        String companyName = "", taxpayerNumber = "", companAddress = "", companTel = "", companOpenBank = "", companBankNum = "";
        if (invoiceInfoBean == null) {
            //不添加发票
            haveCoupon = 2;
        } else {
            //添加发票
            haveCoupon = 1;
            //普通发票和增值税发票
            invoiceType = invoiceInfoBean.getInvoiceType();
            userOrcompany = invoiceInfoBean.getInvoiceForm();
            if (!TextUtils.isEmpty(invoiceInfoBean.getPersonName())) {
                companyName = invoiceInfoBean.getPersonName();
            } else if (!TextUtils.isEmpty(invoiceInfoBean.getCompanyName())) {
                companyName = invoiceInfoBean.getCompanyName();
            } else if (!TextUtils.isEmpty(invoiceInfoBean.getSpecialName())) {
                companyName = invoiceInfoBean.getSpecialName();
            }
            if (!TextUtils.isEmpty(invoiceInfoBean.getCompanyNnumber())) {
                taxpayerNumber = invoiceInfoBean.getCompanyNnumber();
            } else if (!TextUtils.isEmpty(invoiceInfoBean.getSpecialBankNum())) {
                taxpayerNumber = invoiceInfoBean.getSpecialBankNum();
            }
            companAddress = invoiceInfoBean.getSpecialAddress();
            companTel = invoiceInfoBean.getSpecialPhone();
            companOpenBank = invoiceInfoBean.getSpecialBank();
            companBankNum = invoiceInfoBean.getSpecialBankNum();
        }

        OkGo.<String>post(ApiStores.PAY_ADDORDERFORM)
                .params(Constant.USER_ID, userId)
                .params(Constant.PACKAGE_ID, packageId)
                .params(Constant.IS_LIVE, isLive)
                .params(Constant.ADDRESS_ID, addressId)
                .params("user_coupon_id", user_coupon_id)
                .params("haveCoupon", haveCoupon)
                .params("couponType", invoiceType)
                .params("userOrcompany", userOrcompany)
                .params("companyName", companyName)
                .params("taxpayerNumber", taxpayerNumber)
                .params("companAddress", companAddress)
                .params("companTel", companTel)
                .params("companOpenBank", companOpenBank)
                .params("companBankNum", companBankNum)
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
                        view.commitOrderForm(null);
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
                                    CommitOrderFormBean formBean = gson.fromJson(body, CommitOrderFormBean.class);
                                    view.commitOrderForm(formBean);
                                } else {
                                    view.commitOrderForm(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.commitOrderForm(null);
                        }
                    }
                });
    }

}
