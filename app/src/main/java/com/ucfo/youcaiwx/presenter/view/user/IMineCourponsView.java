package com.ucfo.youcaiwx.presenter.view.user;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.user.MineCouponsBean;

/**
 * Author: AND
 * Time: 2019-7-30.  上午 9:27
 * FileName: IMineCourponsView
 * Description:TODO 优惠券
 */
public interface IMineCourponsView extends BaseView {

    void getMineCouponData(MineCouponsBean result);

}
