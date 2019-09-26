package com.ucfo.youcaiwx.utils.pay;

/**
 * Author: AND
 * Time: 2019-9-26.  下午 3:09
 * Package: com.ucfo.youcaiwx.utils.pay
 * FileName: PayStateCallback
 * Description:TODO 支付工具类实践2
 */
public interface PayStateCallback {
    //支付成功
    void onPaySuccess(String desc);

    //支付结果确认中
    void onPayWatting(String describe);

    //支付失败
    void onPayFailed(String describe);
}
