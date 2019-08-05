package com.ucfo.youcai.presenter.presenterImpl.register;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcai.common.ApiStores;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.login.RegisterBean;
import com.ucfo.youcai.presenter.view.register.IRegisterView;
import com.ucfo.youcai.utils.encrypt.AESUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Author:29117
 * Time: 2019-3-25.  下午 4:56
 * Email:2911743255@qq.com
 * ClassName: RegisterPresenter
 * Package: com.ucfo.youcai.presenter.presenterImpl.register
 * Description:
 * Detail:
 */
public class RegisterPresenter implements IRegisterPresenter {
    private IRegisterView registerView;

    public RegisterPresenter(IRegisterView registerView) {
        this.registerView = registerView;
    }

    //账号注册
    @Override
    public void accountRegister(String mobile, String mobileCode, String passWord, String confirmPassword) {
        AESUtils aesEncrypt = new AESUtils();
        String encryptMobile = aesEncrypt.encrypt(mobile, Constant.AES_KEY, Constant.AES_IV);
        String resultMobile = "";
        try {
            String str = new String(encryptMobile.getBytes(), "UTF-8");
            resultMobile = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(ApiStores.REGISTER_ACCOUMENT)
                .tag(this)
                .params(Constant.MOBILE, resultMobile)//手机号
                .params(Constant.SMS_CODE, mobileCode)//短信验证码
                .params(Constant.PASSWORD, passWord)//密码
                .params(Constant.PASSWORD_CONFIRM, confirmPassword)//确认密码
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        registerView.showLoading();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (body != null && !body.equals("")) {
                            Gson gson = new Gson();
                            RegisterBean registerBean = gson.fromJson(body, RegisterBean.class);
                            registerView.registerResult(registerBean);
                        } else {
                            registerView.registerResult(null);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        registerView.registerResult(null);
                        registerView.showError();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        registerView.showLoadingFinish();
                    }
                });
    }

    /**
     * Description:RegisterPresenter
     * Time:2019-3-26   上午 9:25
     * Detail:获取短信验证码
     */
    @Override
    public void getVerifyCode(String mobile, int state) {
        AESUtils aesEncrypt = new AESUtils();
        String encryptMobile = aesEncrypt.encrypt(mobile, Constant.AES_KEY, Constant.AES_IV);
        String resultMobile = "";
        try {
            String str = new String(encryptMobile.getBytes(), "UTF-8");
            resultMobile = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(ApiStores.REGISTER_GETSMSCODE)
                .tag(this)
                .params(Constant.MOBILE, resultMobile)//手机号
                .params(Constant.SMS_STATE, state)//1注册2登录
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

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
                                    registerView.sendCodeSuccess(code, msg);
                                } else {
                                    registerView.sendCodeSuccess(code, msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            registerView.sendCodeSuccess(-1, null);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        registerView.sendCodeSuccess(-1, null);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
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
            String str = new String(encryptMobile.getBytes(), "UTF-8");
            resultMobile = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(ApiStores.REGISTER_GETVOICECODE)
                .tag(this)
                .params(Constant.MOBILE, resultMobile)//手机号
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

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
                                    registerView.sendVoiceCodeSuccess(code, msg);
                                } else {
                                    registerView.sendVoiceCodeSuccess(code, msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            registerView.sendVoiceCodeSuccess(-1, null);
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        registerView.sendVoiceCodeSuccess(-1, null);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });


    }
}
