package com.ucfo.youcai.presenter.presenterImpl.login;

/**
 * Author:29117
 * Time: 2019-3-22.  下午 4:22
 * Email:2911743255@qq.com
 * ClassName: ILoginPresenter
 * Package: com.ucfo.youcai.presenter.presenterImpl.login
 * Description:TODO 登录业务处理
 * Detail:
 */
public interface ILoginPresenter {
    /**
     * Description:ILoginPresenter
     * Time:2019-3-26   下午 2:33
     * Detail:账号登录
     */
    void commonLogin(String phone, String password, String andoridid, String deviceToken);

    /**
     * Description:ILoginPresenter
     * Time:2019-3-26   下午 2:33
     * Detail:短信登登录
     */
    void smsLogin(String phone, String verificationCode, String andoridid, String deviceToken);

    //获取短信验证码
    void getVerifyCode(String mobile, int state);

    //获取语音验证码
    void getVoiceCode(String mobile);

}
