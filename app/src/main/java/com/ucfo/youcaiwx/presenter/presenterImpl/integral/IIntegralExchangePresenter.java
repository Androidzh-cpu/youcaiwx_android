package com.ucfo.youcaiwx.presenter.presenterImpl.integral;

/**
 * Author: AND
 * Time: 2019-10-16.  下午 2:58
 * Package: com.ucfo.youcaiwx.presenter.presenterImpl.integral
 * FileName: IIntegralExchangePresenter
 * Description:TODO 积分兑换
 */
public interface IIntegralExchangePresenter {

    /**
     * 商品详情
     */
    void inquireProductDetail(int user_id, String goods_id);

    /**
     * 添加积分商品订单
     */
    void integralAddOrderNumber(int user_id, String good_id);

    /**
     * 订单支付
     */
    void integralExchangePay(int user_id, int address_id, String good_id);
}
