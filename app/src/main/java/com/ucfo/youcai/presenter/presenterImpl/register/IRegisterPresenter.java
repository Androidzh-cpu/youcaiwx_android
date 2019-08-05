package com.ucfo.youcai.presenter.presenterImpl.register;

/**
 * Author:29117
 * Time: 2019-3-25.  下午 4:53
 * Email:2911743255@qq.com
 * ClassName: IRegisterPresenter
 * Package: com.ucfo.youcai.presenter.presenterImpl.register
 * Description://注册业务
 * Detail:
 */
public interface IRegisterPresenter {
    //账号注册
    void accountRegister(String mobile, String mobileCode, String passWord, String confirmPassword);

    //获取短信验证码
    void getVerifyCode(String mobile, int state);

    //获取语音验证码
    void getVoiceCode(String mobile);

}
