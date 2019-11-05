package com.ucfo.youcaiwx.presenter.presenterImpl.pay;

/**
 * Author: AND
 * Time: 2019-9-10.  上午 11:45
 * Package: com.ucfo.youcaiwx.presenter.presenterImpl.pay
 * FileName: IPayMentPresenter
 * Description:TODO 微信,支付包获取订单
 */
public interface IPayMentPresenter {

    /**
     * 获取微信支付参数
     */
    void initWeCheatOrderForm(String orderForm,String userID);

    void initAlipayOrderForm();

    void checkPayResult();

}
