package com.ucfo.youcaiwx.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.UcfoApplication;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.login.WXLoginBean;
import com.ucfo.youcaiwx.entity.login.WxUserInfoEvent;
import com.ucfo.youcaiwx.presenter.presenterImpl.integral.EarnIntegralPresenter;
import com.ucfo.youcaiwx.utils.ActivityUtil;
import com.ucfo.youcaiwx.utils.LogUtils;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.AppUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.module.login.CompleteAndForgetActivity;
import com.ucfo.youcaiwx.module.login.LoginActivity;
import com.ucfo.youcaiwx.module.login.SMSLoginActivity;
import com.ucfo.youcaiwx.module.main.activity.MainActivity;
import com.umeng.message.PushAgent;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author:29117
 * Time: 2019-3-28.  下午 3:21
 * Email:2911743255@qq.com
 * ClassName: WXEntryActivity
 * Description:微信回调(透明activity)
 */
public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {
    //TODO  微信操作状态码
    //登录操作
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    //分享操作
    private static final int RETURN_MSG_TYPE_SHARE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.getInstance().addActivity(this);
        //如果没回调onResp，八成是这句没有写
        boolean handleIntent = UcfoApplication.api.handleIntent(getIntent(), this);
        //下面代码是判断微信分享后返回WXEnteryActivity的，如果handleIntent==false,说明没有调用IWXAPIEventHandler，则需要在这里销毁这个透明的Activity;
        if (!handleIntent) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtil.getInstance().removeActivity(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        UcfoApplication.api.handleIntent(intent, this);
    }

    /**
     * 微信发送请求到第三方应用时，会回调到该方法
     */
    @Override
    public void onReq(BaseReq baseReq) {
        switch (baseReq.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                break;
            default:
                break;
        }
        finish();
    }

    /**
     * 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
     * app发送消息给微信，处理返回消息的回调
     */
    @Override
    public void onResp(BaseResp baseResp) {
        LogUtils.e("wechat  baseResp:" + baseResp.errCode);
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                //TODO ERR_OK = 0 用户同意
                LogUtils.e("onResp: 用户授权");
                switch (baseResp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //TODO 登录业务
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) baseResp).code;
                        LogUtils.e("wecheat code : " + code);
                        getAccessToken(code);
                        break;
                    case RETURN_MSG_TYPE_SHARE:
                        //TODO 分享业务
                        //设置签到积分
                        int userId = SharedPreferencesUtils.getInstance(this).getInt(Constant.USER_ID, 0);
                        EarnIntegralPresenter.getInstance().earnIntegralForTask(userId);

                        int anInt = SharedPreferencesUtils.getInstance(this).getInt(Constant.USER_ID, 0);
                        EarnIntegralPresenter.getInstance().earnIntegralForTask(0, anInt);
                        WXEntryActivity.this.finish();
                        break;
                    default:
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //TODO ERR_USER_CANCEL = -2（用户取消）
                LogUtils.e("onResp: 用户取消授权");
                if (RETURN_MSG_TYPE_SHARE == baseResp.getType()) {
                    ToastUtil.showBottomShortText(this, getResources().getString(R.string.sharedError));
                    WXEntryActivity.this.finish();
                } else {
                    ToastUtil.showBottomShortText(this, getResources().getString(R.string.login_error));
                    WXEntryActivity.this.finish();
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //TODO ERR_AUTH_DENIED = -4（用户拒绝授权）
                LogUtils.e("onResp: 发送请求被拒绝");
                ToastUtil.showBottomShortText(this, getResources().getString(R.string.TheSendRequestWasDenied));
                WXEntryActivity.this.finish();
                break;
            default:
                break;
        }


    }

    /**
     * 获取access_token
     * <p>
     * code 用户换取access_token的code，仅在ErrCode为0时有效
     */
    private void getAccessToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Constant.WEIXIN_KEY + "&secret="
                + Constant.WEIXIN_SECRET + "&code=" + code + "&grant_type=authorization_code";
        OkGo.<String>get(url)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject object = new JSONObject(response.body());
                            String accessToken = object.optString("access_token");//接口调用凭证
                            int expires_in = object.optInt("expires_in");//access_token接口调用凭证超时时间，单位（秒）
                            String openId = object.optString("openid");//授权用户唯一标识
                            String unionid = object.optString("unionid");//当且仅当该移动应用已获得该用户的userinfo授权时，才会出现该字段
                            String scope = object.optString("scope");//用户授权的作用域，使用逗号（,）分隔
                            String refresh_token = object.optString("refresh_token");//用户刷新access_token
                            if (!TextUtils.isEmpty(openId)) {
                                getUserInfo(accessToken, openId);
                            }
                            LogUtils.e("getAccessToken()---------微信通过code获取access_token" + response.body());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 获取微信用户信息  UnionID机制
     * 通过access_token调用接口
     * 微信用户已授权给第三方应用帐号相应接口作用域（scope）。
     */
    private void getUserInfo(String accessToken, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId;
        OkGo.<String>get(url)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject object = new JSONObject(response.body());
                            WxUserInfoEvent wxUserInfo = new WxUserInfoEvent();
                            String unionid = object.optString("unionid");
                            String nickname = object.optString("nickname");
                            wxUserInfo.setUnionid(unionid);
                            wxUserInfo.setNickname(nickname);
                            wxLogin(unionid, openId, nickname);

                            LogUtils.e("getUserInfo()---------获取微信用户信息" + object.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtil.showBottomShortText(getApplicationContext(), getResources().getString(R.string.login_error));
                        WXEntryActivity.this.finish();
                    }
                });
    }

    /**
     * 请求后台开始登录
     */
    private void wxLogin(String unionid, String openId, String nickname) {
        String registrationId = PushAgent.getInstance(this).getRegistrationId();
        String appIMEI = AppUtils.getAppIMEI(this);
        OkGo.<String>post(ApiStores.LOGIN_WECHEATLOGIN)
                .params(Constant.UNIONID, unionid)
                .params(Constant.DEVICES, appIMEI)
                .params(Constant.DEVICES_TOKEN, registrationId)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtil.showBottomShortText(getApplicationContext(), getResources().getString(R.string.login_error));
                        WXEntryActivity.this.finish();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            Gson gson = new Gson();
                            WXLoginBean wxLoginBean = gson.fromJson(response.body(), WXLoginBean.class);
                            //后台登录成功
                            setUserInfo(wxLoginBean, unionid, openId, nickname);
                            LogUtils.e("wecheat loginresult: " + response.body());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * Description:WXEntryActivity
     * Time:2019-3-29   下午 1:58
     * Detail:根据后台返回结果判断下一步操作
     */
    public void setUserInfo(WXLoginBean userInfo, String unionid, String openId, String nickname) {
        int code = userInfo.getCode();
        if (code == 200) {
            WXLoginBean.DataBean data = userInfo.getData();
            String equipment = data.getEquipment();//绑定状态
            int parseInt = Integer.parseInt(equipment);
            switch (parseInt) {
                case 1:
                    //1未绑定手机号
                    LogUtils.e("wecheat setUserInfo:1未绑定手机号");
                    Intent conpletedIntent = new Intent(WXEntryActivity.this, CompleteAndForgetActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.TYPE, Constant.TYPE_COMPLET);//完善信息
                    bundle.putString(Constant.UNIONID, unionid);
                    bundle.putString(Constant.OPENID, openId);
                    bundle.putString(Constant.USER_NAME, nickname);
                    conpletedIntent.putExtras(bundle);
                    startActivity(conpletedIntent);
                    WXEntryActivity.this.finish();
                    ActivityUtil.getInstance().finishActivity(LoginActivity.class);
                    ActivityUtil.getInstance().finishActivity(SMSLoginActivity.class);
                    break;
                case 2:
                    //2已成功
                    LogUtils.e("wecheat setUserInfo:2登录成功");
                    String userId = data.getId();
                    String status = data.getStatus();
                    SharedPreferencesUtils.getInstance(getApplicationContext()).putBoolean(Constant.LOGIN_STATUS, true);
                    SharedPreferencesUtils.getInstance(getApplicationContext()).putInt(Constant.USER_ID, Integer.parseInt(userId));
                    SharedPreferencesUtils.getInstance(getApplicationContext()).putInt(Constant.USER_STATUS, Integer.parseInt(status));
                    ToastUtil.showBottomShortText(getApplicationContext(), getResources().getString(R.string.login_success));
                    Intent intent = new Intent(WXEntryActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    WXEntryActivity.this.finish();
                    break;
                case 3:
                    //3设备达到上限
                    LogUtils.e("wecheat setUserInfo:3设备达到上限");
                    ToastUtil.showBottomShortText(getApplicationContext(), getResources().getString(R.string.wx_upperlimit));
                    WXEntryActivity.this.finish();
                    break;
                default:
                    ToastUtil.showBottomShortText(getApplicationContext(), getResources().getString(R.string.login_error));
                    WXEntryActivity.this.finish();
                    break;
            }
        } else {
            ToastUtil.showBottomShortText(getApplicationContext(), getResources().getString(R.string.login_error));
            WXEntryActivity.this.finish();
        }
    }
}
