package com.ucfo.youcai.presenter.view.user;

import com.ucfo.youcai.base.BaseView;
import com.ucfo.youcai.entity.user.MineCouponsBean;

/**
 * Author: AND
 * Time: 2019-7-30.  上午 9:27
 * Package: com.ucfo.youcai.presenter.view.user
 * FileName: IMineCourponsView
 * Description:TODO 优惠券
 */
public interface IMineCourponsView extends BaseView {

    void getMineCouponData(MineCouponsBean result);

}
