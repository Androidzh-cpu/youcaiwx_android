package com.ucfo.youcaiwx.presenter.presenterImpl.user;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.address.AddressDetailBean;
import com.ucfo.youcaiwx.entity.address.AddressListBean;
import com.ucfo.youcaiwx.entity.address.StateStatusBean;
import com.ucfo.youcaiwx.presenter.view.user.IUserAddressView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-6-13.  下午 4:09
 * FileName: UserAddressPresenter
 * Description:TODO 地址操作业务
 */
public class UserAddressPresenter implements IUserAddressPresenter {
    private IUserAddressView view;

    public UserAddressPresenter(IUserAddressView view) {
        this.view = view;
    }

    @Override
    public void getUserAddressList(int user_id) {
        OkGo.<String>post(ApiStores.USER_ADDRESS_LIST)
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
                                    AddressListBean addressListBean = gson.fromJson(body, AddressListBean.class);
                                    view.getUserAddressList(addressListBean);
                                } else {
                                    view.getUserAddressList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getUserAddressList(null);
                        }
                    }
                });
    }

    //TODO 添加地址
    @Override
    public void addAddress(int user_id, String userName, String userPhone, String userAddress, int type) {
        OkGo.<String>post(ApiStores.USER_ADDADDRESS)
                .params(Constant.USER_ID, user_id)
                .params(Constant.ADDRESS_NAME, userName)
                .params(Constant.TELEPHONE, userPhone)
                .params(Constant.ADDRESS, userAddress)
                .params(Constant.IS_DEFAULT, type)
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
                        view.addAddressStatus(null);
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
                                    view.addAddressStatus(bean);
                                } else {
                                    view.addAddressStatus(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.addAddressStatus(null);
                        }
                    }
                });

    }

    //TODO 获取地址详细信息
    @Override
    public void getAddressDetail(int user_id, int address_id) {
        OkGo.<String>post(ApiStores.USER_GETADDRESS_DETAIL)
                .params(Constant.USER_ID, user_id)
                .params(Constant.ADDRESS_ID, address_id)
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
                        view.getAddressDetail(null);
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
                                    AddressDetailBean addressDetailBean = gson.fromJson(body, AddressDetailBean.class);
                                    view.getAddressDetail(addressDetailBean);
                                } else {
                                    view.getAddressDetail(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getAddressDetail(null);
                        }
                    }
                });
    }

    //删除地址
    @Override
    public void deleteAddress(int user_id, int address_id) {
        OkGo.<String>post(ApiStores.USER_ADDRESS_DELETE)
                .params(Constant.USER_ID, user_id)
                .params(Constant.ADDRESS_ID, address_id)
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
                        view.deleteAddressStatus(null);
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
                                    StateStatusBean addressDetailBean = gson.fromJson(body, StateStatusBean.class);
                                    view.deleteAddressStatus(addressDetailBean);
                                } else {
                                    view.deleteAddressStatus(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.deleteAddressStatus(null);
                        }
                    }
                });

    }

    //TODO 修改地址
    @Override
    public void updateAddress(int user_id, int address_id, String userName, String userPhone, String userAddress, int type) {
        OkGo.<String>post(ApiStores.USER_ADDRESS_EDIT)
                .params(Constant.USER_ID, user_id)
                .params(Constant.ADDRESS_ID, address_id)
                .params(Constant.ADDRESS_NAME, userName)
                .params(Constant.TELEPHONE, userPhone)
                .params(Constant.ADDRESS, userAddress)
                .params(Constant.IS_DEFAULT, type)
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
                        view.addAddressStatus(null);
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
                                    StateStatusBean addressDetailBean = gson.fromJson(body, StateStatusBean.class);
                                    view.addAddressStatus(addressDetailBean);
                                } else {
                                    view.addAddressStatus(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.addAddressStatus(null);
                            view.deleteAddressStatus(null);
                        }
                    }
                });

    }
}
