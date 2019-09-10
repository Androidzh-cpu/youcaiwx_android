package com.ucfo.youcaiwx.entity.pay;

/**
 * Author: AND
 * Time: 2019-9-10.  上午 10:43
 * Package: com.ucfo.youcaiwx.entity.pay
 * FileName: PayAliPayResponseBean
 * Description:TODO 支付宝
 */
public class PayAliPayResponseBean {
    private String orderInfo;

    public String getOrderInfo() {
        return orderInfo == null ? "" : orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }
}
