package com.ucfo.youcaiwx.presenter.presenterImpl.pay;

import com.ucfo.youcaiwx.entity.pay.InvoiceInfoBean;

/**
 * Author: AND
 * Time: 2019-8-5.  下午 3:26
 * FileName: IPayPresenter
 * Description:TODO 订单支付
 */
public interface IPayPresenter {

    /**
     * 订单详情
     */
    void getOrderFormDetail(int userId, int packageId,int orderNumType);

    /**
     * 提交订单
     */
    void commitOrderForm(int userId, int packageId, int isLive, int addressId, int user_coupon_id, InvoiceInfoBean invoiceInfoBean);

}
