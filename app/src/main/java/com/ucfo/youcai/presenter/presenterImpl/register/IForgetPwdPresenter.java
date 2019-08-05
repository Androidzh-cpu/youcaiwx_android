package com.ucfo.youcai.presenter.presenterImpl.register;

/**
 * Author:29117
 * Time: 2019-3-26.  下午 4:06
 * Email:2911743255@qq.com
 * ClassName: IForgetPwdPresenter
 * Package: com.ucfo.youcai.presenter.presenterImpl.register
 * Description:忘记密码业务
 * Detail:
 */
public interface IForgetPwdPresenter {

    //重置密码
    void resetPassWord(String mobile, String mobileCode, String passWord);

    //获取短信验证码
    void getVerifyCode(String mobile);

    void getWXVerifyCode(String mobile, int state);

    //获取语音验证码
    void getVoiceCode(String mobile);

}
