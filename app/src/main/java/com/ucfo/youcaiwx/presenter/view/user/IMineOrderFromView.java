package com.ucfo.youcaiwx.presenter.view.user;

import com.ucfo.youcaiwx.entity.user.MineOrderFormDetailBean;
import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.address.StateStatusBean;
import com.ucfo.youcaiwx.entity.user.MineOrderListBean;

/**
 * Author: AND
 * Time: 2019-6-21.  上午 11:56
 * FileName: IMineOrderFromView
 * Description:TODO 订单业务
 */
public interface IMineOrderFromView extends BaseView {

    //todo 我的订单
    void getMineOrderFromList(MineOrderListBean data);

    //TODO 取消订单
    void cancelOrderForm(StateStatusBean data);

    //TODO 获取订单详情
    void getOrderFormDetail(MineOrderFormDetailBean data);
}
