package com.ucfo.youcaiwx.presenter.view.integral;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.integral.IntegralAddOrderNumBean;
import com.ucfo.youcaiwx.entity.integral.IntegralOrderExchangeResultBean;

/**
 * Author: AND
 * Time: 2019-10-17.  下午 4:23
 * Package: com.ucfo.youcaiwx.presenter.view.integral
 * FileName: IIntegralExchangeView
 * Description:TODO 积分兑换
 */
public interface IIntegralExchangeView extends BaseView {

    /**
     * 添加积分商品订单
     */
    void integralAddOrderForm(IntegralAddOrderNumBean dataBean, String desc);

    /**
     * 积分订单兑换
     */
    void integralOrderFormExchange(IntegralOrderExchangeResultBean data);

}
