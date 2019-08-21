package com.ucfo.youcaiwx.presenter.presenterImpl.login;

import android.content.Context;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.UcfoApplication;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.login.WXCompletedBean;
import com.ucfo.youcaiwx.utils.encrypt.AESUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Author:29117
 * Time: 2019-3-28.  下午 4:56
 * Email:2911743255@qq.com
 * ClassName: WXLoginPresenter
 * Description:
 * Detail:
 */
public class WXLoginPresenter {

    public WXLoginPresenter(Context context) {
        this.context = context;
    }

    private IWXLoginView view;
    private Context context;

    public WXLoginPresenter(IWXLoginView view, Context context) {
        this.view = view;
        this.context = context;
    }

    //调用微信登录
    public void wxLogin() {
        if (UcfoApplication.api == null) {
            UcfoApplication.api = WXAPIFactory.createWXAPI(context, Constant.WEIXIN_KEY, true);
            UcfoApplication.api.registerApp(Constant.WEIXIN_KEY);
        }
        if (!UcfoApplication.api.isWXAppInstalled()) {
            ToastUtil.showBottomShortText(context, context.getResources().getString(R.string.wx_noinstanl));
            return;
        }
        // send oauth request
        final SendAuth.Req req = new SendAuth.Req();
        //授权读取用户信息
        req.scope = Constant.WEIXIN_PARAMS1;
        //官方说明：用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），
        // 建议第三方带上该参数，可设置为简单的随机数加session进行校验
        req.state = Constant.WEIXIN_PARAMS2;
        UcfoApplication.api.sendReq(req);
    }

    /**
     * Description:WXLoginPresenter
     * Time:2019-3-29   上午 11:59
     * Detail:微信完善信息
     */
    public void wxLoginCompletedInfo(String mobile, String mobileCode, String password, String unionId, String openid) {
        AESUtils aesEncrypt = new AESUtils();
        String encryptMobile = aesEncrypt.encrypt(mobile, Constant.AES_KEY, Constant.AES_IV);
        String resultMobile = "";
        try {
            String str = new String(encryptMobile.getBytes(), "UTF-8");
            resultMobile = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(ApiStores.LOGIN_WECHEATLOGIN_COMPLETED)
                .params(Constant.MOBILE, resultMobile)
                .params(Constant.SMS_CODE, mobileCode)
                .params(Constant.PASSWORD, password)
                .params(Constant.UNIONID, unionId)
                .params(Constant.OPENID, openid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (body != null && !body.equals("")) {
                            Gson gson = new Gson();
                            WXCompletedBean wxCompletedBean = gson.fromJson(body, WXCompletedBean.class);
                            view.wxLoginCompleted(wxCompletedBean);
                        } else {
                            view.wxLoginCompleted(null);
                        }
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        view.startWxCompletedLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.wxLoginCompleted(null);
                        view.finishWxCompletedLoading();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        view.finishWxCompletedLoading();
                    }
                });
    }

    public interface IWXLoginView {

        void wxLoginCompleted(WXCompletedBean data);

        void startWxCompletedLoading();

        void finishWxCompletedLoading();

    }
}
