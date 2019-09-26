package com.ucfo.youcaiwx.utils.pay;

/**
 * Author: AND
 * Time: 2019-9-25.  下午 5:47
 * Package: com.ucfo.youcaiwx.utils.pay
 * FileName: PayResultListener
 * Description:TODO 支付返回结果(注: 不是自己服务器的返回结果,而是支付平台的返回结果)
 */
public interface PayResultListener {
    void onPaySuccess();

    void onPayError();

    void onPayCancel();
}

