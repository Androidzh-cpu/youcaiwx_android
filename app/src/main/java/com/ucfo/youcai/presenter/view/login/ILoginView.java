package com.ucfo.youcai.presenter.view.login;

import com.ucfo.youcai.base.BaseView;
import com.ucfo.youcai.entity.login.LoginBean;

/**
 * Author:29117
 * Time: 2019-3-22.  下午 4:21
 * Email:2911743255@qq.com
 * ClassName: ILoginView
 * Package: com.ucfo.youcai.presenter.presenterImpl.login
 * Description:
 * Detail:
 */
public interface ILoginView extends BaseView {
    //登录结果
    void loginResult(LoginBean data);

    //短信验证码已发送
    void sendCodeSuccess(int code, String message);

    //语音验证码已发送
    void sendVoiceCodeSuccess(int code, String message);

}
