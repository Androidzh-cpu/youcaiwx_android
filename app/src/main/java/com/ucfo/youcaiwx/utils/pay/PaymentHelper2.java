package com.ucfo.youcaiwx.utils.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.pay.PayAliPayResponseBean;
import com.ucfo.youcaiwx.entity.pay.PayWeChatResponseBean;
import com.ucfo.youcaiwx.utils.LogUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;

import java.util.Map;

/**
 * Author: AND
 * Time: 2019-9-26.  下午 3:11
 * Package: com.ucfo.youcaiwx.utils.pay
 * FileName: PaymentHelper2
 * Description:TODO 支付工具类实践2
 */
public class PaymentHelper2 {
    @SuppressLint("StaticFieldLeak")
    private static PaymentHelper2 instance;
    private PayStateCallback2 payStateCallback2;
    private Activity activity;
    private static final int SDK_PAY_FLAG = 1;

    private PaymentHelper2() {
    }

    public static PaymentHelper2 getInstance(Activity activity, PayStateCallback2 payStateCallback2) {
        if (instance == null) {
            instance = new PaymentHelper2();
        }
        instance.setActivity(activity);
        instance.setPayStateCallback(payStateCallback2);
        return instance;
    }

    private void setActivity(Activity activity) {
        this.activity = activity;
    }

    private void setPayStateCallback(PayStateCallback2 payStateCallback2) {
        this.payStateCallback2 = payStateCallback2;
    }

    public static PaymentHelper2 getInstance() {
        if (instance == null) {
            instance = new PaymentHelper2();
        }
        return instance;
    }

    public PayStateCallback2 getPayStateCallback() {
        return payStateCallback2;
    }

    /**
     * 根据服务端返回的订单号,调用支付宝支付
     */
    public void startAliPay(PayAliPayResponseBean aliPayResponseBean) {
        if (activity == null || aliPayResponseBean == null) {
            return;
        }
        //支付宝支付请求所需的签名字符串
        final String orderInfo = aliPayResponseBean.getOrderInfo();
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                LogUtils.e("msp" + result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝支付回调
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表参考：https://docs.open.alipay.com/204/105301
                    if (TextUtils.equals(resultStatus, String.valueOf(9000))) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。支付结果一定要调用自己的服务端来确定，不能通过支付宝的回调结果来判断！
                        ToastUtil.showBottomLongText(activity, "支付成功");
                        if (payStateCallback2 != null) {
                            payStateCallback2.onPaySuccess("支付成功");
                        }
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, String.valueOf(8000))) {
                            ToastUtil.showBottomLongText(activity, "支付结果确认中");
                            if (payStateCallback2 != null) {
                                payStateCallback2.onPayWatting("支付结果确认中");
                            }
                        } else if (TextUtils.equals(resultStatus, String.valueOf(6001))) { //用户中途取消
                            ToastUtil.showBottomLongText(activity, "取消支付");
                        } else {
                            // 其他值就可以判断为支付失败
                            ToastUtil.showBottomLongText(activity, "支付失败");
                            if (payStateCallback2 != null) {
                                payStateCallback2.onPayFailed("支付失败");
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 开启微信支付
     */
    public void startWeChatPay(PayWeChatResponseBean data) {
        /*PayWeChatResponseBean.DataBean payWeChatResponseBean = new PayWeChatResponseBean.DataBean();
        payWeChatResponseBean.setAppid("wx55d839e60fb4b35f");
        payWeChatResponseBean.setPartnerid("1317252201");
        payWeChatResponseBean.setPrepayid("wx051541481897699561eb602f1837498600");
        payWeChatResponseBean.setPackage("Sign=WXPay");
        payWeChatResponseBean.setNoncestr("gCemzgBDleRpyUFf");
        payWeChatResponseBean.setTimestamp("1572939889");
        payWeChatResponseBean.setSign("C6DCB543BB8246AC53E392C34ABF42E9");
        data.setData(payWeChatResponseBean);*/
        PayWeChatResponseBean.DataBean payWeChatResponseBean = data.getData();
        if (activity == null || payWeChatResponseBean == null) {
            return;
        }
        if (!TextUtils.equals(Constant.WEIXIN_KEY, payWeChatResponseBean.getAppid())) {
            return;
        }
        IWXAPI wxapi = WXAPIFactory.createWXAPI(activity, Constant.WEIXIN_KEY, true);
        // 将该app注册到微信
        wxapi.registerApp(Constant.WEIXIN_KEY);
        if (!wxapi.isWXAppInstalled()) {
            ToastUtil.showBottomLongText(activity, activity.getString(R.string.pay_noinstanl));
            return;
        }
        //我们把请求到的参数全部给微信,调起微信APP的对象
        PayReq req = new PayReq();
        req.appId = payWeChatResponseBean.getAppid();
        req.partnerId = payWeChatResponseBean.getPartnerid();
        req.prepayId = payWeChatResponseBean.getPrepayid();
        req.nonceStr = payWeChatResponseBean.getNoncestr();
        req.timeStamp = payWeChatResponseBean.getTimestamp();
        req.packageValue = payWeChatResponseBean.getPackage();
        req.sign = payWeChatResponseBean.getSign();
        //发送调起微信的请求
        wxapi.sendReq(req);
        LogUtils.e("微信支付请求参数------------------" + new Gson().toJson(payWeChatResponseBean));
    }
}
