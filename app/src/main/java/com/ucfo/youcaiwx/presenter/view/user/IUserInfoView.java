package com.ucfo.youcaiwx.presenter.view.user;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.address.StateStatusBean;
import com.ucfo.youcaiwx.entity.user.UserInfoBean;

/**
 * Author:29117
 * Time: 2019-4-2.  上午 11:25
 * Email:2911743255@qq.com
 * ClassName: IUserInfoView
 * Description:TODO 用户个人信息
 */
public interface IUserInfoView extends BaseView {
    //TODO 个人基本信息
    void getUserInfo(UserInfoBean bean);

    //TODO 修改结果
    void retouceResult(StateStatusBean result);

}
