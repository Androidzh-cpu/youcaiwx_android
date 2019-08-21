package com.ucfo.youcaiwx.presenter.presenterImpl.user;

/**
 * Author:29117
 * Time: 2019-4-2.  上午 11:26
 * Email:2911743255@qq.com
 * ClassName: IUserInfoPresenter
 * Description:TODO 个人信息业务操作
 */
public interface IUserInfoPresenter {

    //TODO 获取个人基本信息
    void getUserInfo(int user_id);

    //TODO 修改个人信息
    void retoucheInfo(int user_id, int type, String content);

    //TODO 修改密码
    void modifyPassword(int user_id, String oldpassword, String newPassword, String newPasswordAgain);

}
