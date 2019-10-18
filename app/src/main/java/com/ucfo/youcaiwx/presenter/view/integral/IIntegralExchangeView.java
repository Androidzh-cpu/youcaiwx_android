package com.ucfo.youcaiwx.presenter.view.integral;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.integral.IntegralAddOrderNumBean;

/**
 * Author: AND
 * Time: 2019-10-17.  下午 4:23
 * Package: com.ucfo.youcaiwx.presenter.view.integral
 * FileName: IIntegralExchangeView
 * Description:TODO 积分兑换
 */
public interface IIntegralExchangeView extends BaseView {

    void integralAddOrderNumber(IntegralAddOrderNumBean dataBean, String desc);

}
