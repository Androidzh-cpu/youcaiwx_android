package com.ucfo.youcaiwx.presenter.view.register;

import com.ucfo.youcaiwx.base.BaseView;

/**
 * Author:29117
 * Time: 2019-3-26.  下午 4:05
 * Email:2911743255@qq.com
 * ClassName: IForgetPwdView
 * Description:
 * Detail:
 */
public interface IForgetPwdView extends BaseView {

    //忘记密码修改结果
    void registerResult(int code, String msg);

    //短信验证码已发送
    void sendCodeSuccess(int code, String msg);

    //语音验证码已发送
    void sendVoiceCodeSuccess(int code, String msg);

}
