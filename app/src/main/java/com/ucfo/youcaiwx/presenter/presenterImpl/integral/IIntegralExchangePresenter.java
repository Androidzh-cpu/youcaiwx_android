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
     * 商品订单详情
     */
    void integralAddOrderNumber(int user_id, String good_id);

}
