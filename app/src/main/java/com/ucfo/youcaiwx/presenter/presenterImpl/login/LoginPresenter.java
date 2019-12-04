package com.ucfo.youcaiwx.presenter.presenterImpl.login;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.login.LoginBean;
import com.ucfo.youcaiwx.presenter.view.login.ILoginView;
import com.ucfo.youcaiwx.utils.encrypt.AESUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Author:29117
 * Time: 2019-3-22.  下午 4:23
 * Email:2911743255@qq.com
 * ClassName: LoginPresenter
 */
public class LoginPresenter implements ILoginPresenter {

    private ILoginView loginView;

    public LoginPresenter(ILoginView view) {
        this.loginView = view;
    }

    //TODO 账号密码登录
    @Override
    public void commonLogin(String phone, String password, String andoridid, String deviceToken) {
        AESUtils aesEncrypt = new AESUtils();
        String encrypt = aesEncrypt.encrypt(phone, Constant.AES_KEY, Constant.AES_IV);
        String iphone = "";
        try {
            String str = new String(encrypt.getBytes(), Constant.UTF_8);
            iphone = URLEncoder.encode(str, Constant.UTF_8);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(ApiStores.LOGIN_ACCOUMENT)
                .params(Constant.MOBILE, iphone)//手机号
                .params(Constant.PASSWORD, password)//密码
                .params(Constant.DEVICES, andoridid)
                .params(Constant.DEVICES_TOKEN, deviceToken)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        loginView.showLoading();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (body != null && !body.equals("")) {
                            Gson gson = new Gson();
                            LoginBean loginBean = gson.fromJson(body, LoginBean.class);
                            loginView.loginResult(loginBean);
                        } else {
                            loginView.loginResult(null);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        loginView.loginResult(null);
                        loginView.showError();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        loginView.showLoadingFinish();
                    }
                });

    }

    //短信登录
    @Override
    public void smsLogin(String phone, String verificationCode, String andoridid, String deviceToken) {
        AESUtils aesEncrypt = new AESUtils();
        String encrypt = aesEncrypt.encrypt(phone, Constant.AES_KEY, Constant.AES_IV);
        String iphone = "";
        try {
            String str = new String(encrypt.getBytes(), Constant.UTF_8);
            iphone = URLEncoder.encode(str, Constant.UTF_8);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(ApiStores.LOGIN_SMSLOGIN)
                .tag(this)
                .params(Constant.MOBILE, iphone)
                .params(Constant.SMS_CODE, verificationCode)
                .params(Constant.DEVICES, andoridid)
                .params(Constant.DEVICES_TOKEN, deviceToken)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        loginView.showLoading();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (body != null && !body.equals("")) {
                            Gson gson = new Gson();
                            LoginBean loginBean = gson.fromJson(body, LoginBean.class);
                            loginView.loginResult(loginBean);
                        } else {
                            loginView.loginResult(null);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        loginView.showError();
                        loginView.loginResult(null);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        loginView.showLoadingFinish();
                    }
                });
    }

    @Override
    public void getVerifyCode(String mobile, int state) {
        AESUtils aesEncrypt = new AESUtils();
        String encryptMobile = aesEncrypt.encrypt(mobile, Constant.AES_KEY, Constant.AES_IV);
        String resultMobile = "";
        try {
            String str = new String(encryptMobile.getBytes(), Constant.UTF_8);
            resultMobile = URLEncoder.encode(str, Constant.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(ApiStores.REGISTER_GETSMSCODE)
                .tag(this)
                .params(Constant.MOBILE, resultMobile)//手机号
                .params(Constant.SMS_STATE, state)//1注册2登录
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (!body.equals("")) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(body);
                                int code = jsonObject.optInt(Constant.CODE);
                                String msg = jsonObject.optString(Constant.MSG);
                                if (code == 200) {
                                    loginView.sendCodeSuccess(code, msg);
                                } else {
                                    loginView.sendCodeSuccess(code, msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loginView.sendCodeSuccess(-1, null);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        loginView.sendCodeSuccess(-1, null);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        loginView.showLoadingFinish();
                    }
                });
    }

    /**
     * Description:RegisterPresenter
     * Time:2019-3-26   上午 9:39
     * Detail:获取语音验证码
     */
    @Override
    public void getVoiceCode(String mobile) {
        AESUtils aesEncrypt = new AESUtils();
        String encryptMobile = aesEncrypt.encrypt(mobile, Constant.AES_KEY, Constant.AES_IV);
        String resultMobile = "";
        try {
            String str = new String(encryptMobile.getBytes(), Constant.UTF_8);
            resultMobile = URLEncoder.encode(str, Constant.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(ApiStores.REGISTER_GETVOICECODE)
                .tag(this)
                .params(Constant.MOBILE, resultMobile)//手机号
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (body != null && !body.equals("")) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(body);
                                int code = jsonObject.optInt(Constant.CODE);
                                String msg = jsonObject.optString(Constant.MSG);
                                if (code == 200) {
                                    loginView.sendVoiceCodeSuccess(code, msg);
                                } else {
                                    loginView.sendVoiceCodeSuccess(code, msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loginView.sendVoiceCodeSuccess(-1, null);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        loginView.sendVoiceCodeSuccess(-1, null);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        loginView.showLoadingFinish();
                    }
                });
    }
}
