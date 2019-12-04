package com.ucfo.youcaiwx.presenter.presenterImpl.register;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.presenter.view.register.IForgetPwdView;
import com.ucfo.youcaiwx.utils.encrypt.AESUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Author:29117
 * Time: 2019-3-26.  下午 4:02
 * Email:2911743255@qq.com
 * ClassName: ForgetPwdPresenter
 */
public class ForgetPwdPresenter implements IForgetPwdPresenter {
    private IForgetPwdView view;

    public ForgetPwdPresenter(IForgetPwdView view) {
        this.view = view;
    }

    //重置密码
    @Override
    public void resetPassWord(String mobile, String mobileCode, String passWord) {
        AESUtils aesEncrypt = new AESUtils();
        String encryptMobile = aesEncrypt.encrypt(mobile, Constant.AES_KEY, Constant.AES_IV);
        String resultMobile = "";
        try {
            String str = new String(encryptMobile.getBytes(), "UTF-8");
            resultMobile = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(ApiStores.RESET_PASSWORD)
                .tag(this)
                .params(Constant.MOBILE, resultMobile)//手机号
                .params("verifycode", mobileCode)//短信验证码
                .params(Constant.PASSWORD, passWord)//密码
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        view.showLoading();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        ///////
                        if (body != null && !body.equals("")) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(body);
                                int code = jsonObject.optInt(Constant.CODE);
                                String msg = jsonObject.optString(Constant.MSG);
                                if (code == 200) {
                                    view.registerResult(code, msg);
                                } else {
                                    view.registerResult(code, msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.registerResult(-1, null);
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.registerResult(-1, null);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        view.showLoadingFinish();
                    }
                });


    }

    //获取短信验证码
    @Override
    public void getVerifyCode(String mobile) {
        AESUtils aesEncrypt = new AESUtils();
        String encryptMobile = aesEncrypt.encrypt(mobile, Constant.AES_KEY, Constant.AES_IV);
        String resultMobile = "";
        try {
            String str = new String(encryptMobile.getBytes(), "UTF-8");
            resultMobile = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(ApiStores.FORGET_GETSMSCODE)
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
                                    view.sendCodeSuccess(code, msg);
                                } else {
                                    view.sendCodeSuccess(code, msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.sendCodeSuccess(-1, null);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.sendCodeSuccess(-1, null);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        view.showLoadingFinish();
                    }
                });

    }

    //获取微信短信验证码
    @Override
    public void getWXVerifyCode(String mobile, int state) {
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
                        if (body != null && !body.equals("")) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(body);
                                int code = jsonObject.optInt(Constant.CODE);
                                String msg = jsonObject.optString(Constant.MSG);
                                if (code == 200) {
                                    view.sendCodeSuccess(code, msg);
                                } else {
                                    view.sendCodeSuccess(code, msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.sendCodeSuccess(-1, null);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.sendCodeSuccess(-1, null);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        view.showLoadingFinish();
                    }
                });
    }

    //获取语音验证码
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
                                    view.sendVoiceCodeSuccess(code, msg);
                                } else {
                                    view.sendVoiceCodeSuccess(code, msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.sendVoiceCodeSuccess(-1, null);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.sendVoiceCodeSuccess(-1, null);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        view.showLoadingFinish();
                    }
                });

    }
}
