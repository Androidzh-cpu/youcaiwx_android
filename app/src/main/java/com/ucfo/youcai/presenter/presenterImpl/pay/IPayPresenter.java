package com.ucfo.youcai.presenter.presenterImpl.pay;

/**
 * Author: AND
 * Time: 2019-8-5.  下午 3:26
 * Package: com.ucfo.youcai.presenter.presenterImpl.pay
 * FileName: IPayPresenter
 * Description:TODO 订单支付
 */
public interface IPayPresenter {

    /**
     * 订单详情
     */
    void getOrderFormDetail(int userId, int packageId);

    /**
     * 提交订单
     */
    void commitOrderForm(int userId, int packageId, int isLive, int addressId, int user_coupon_id);

}
