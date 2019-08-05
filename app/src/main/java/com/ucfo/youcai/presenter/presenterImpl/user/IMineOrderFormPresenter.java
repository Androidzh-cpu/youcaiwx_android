package com.ucfo.youcai.presenter.presenterImpl.user;

/**
 * Author: AND
 * Time: 2019-6-21.  下午 2:25
 * Package: com.ucfo.youcai.presenter.presenterImpl.user
 * FileName: IMineOrderFormPresenter
 * Description:TODO 订单操作
 */
public interface IMineOrderFormPresenter {

    //TODO 我的订单
    void getMineOrderFromList(int user_id);

    //TODO 取消订单
    void cancelOrderForm(int user_id, String orderNumber);

    //TODO 获取订单详情
    void getOrderFormDetail(int user_id, int status, String orderNumber);

}
