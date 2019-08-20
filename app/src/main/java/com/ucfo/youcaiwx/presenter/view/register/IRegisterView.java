package com.ucfo.youcaiwx.presenter.view.register;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.login.RegisterBean;

/**
 * Author:29117
 * Time: 2019-3-25.  下午 4:46
 * Email:2911743255@qq.com
 * ClassName: IRegisterView
 * Description:
 * Detail:
 */
public interface IRegisterView extends BaseView {

    //注册结果
    void registerResult(RegisterBean data);

    //短信验证码已发送
    void sendCodeSuccess(int code, String message);

    //语音验证码已发送
    void sendVoiceCodeSuccess(int code, String message);

}
