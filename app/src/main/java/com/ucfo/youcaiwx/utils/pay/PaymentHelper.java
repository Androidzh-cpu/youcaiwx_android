package com.ucfo.youcaiwx.utils.pay;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
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
 * Time: 2019-9-10.  上午 9:59
 * Package: com.ucfo.youcaiwx.utils.pay
 * FileName: PaymentHelper
 * Description:TODO 微信支付工具类
 * 微信师傅得在子线程开启支付
 */
public class PaymentHelper {
    //调起支付的页面
    private AppCompatActivity appCompatActivity;
    //支付标识
    private static final int SDK_PAY_FLAG = 1;

    /**
     * Description:PaymentHelper
     * Time:2019-9-10 上午 10:07
     * Detail:TODO 调用微信支付
     */
    public void startWeChatPay(AppCompatActivity activity, PayWeChatResponseBean payWeChatResponseBean) {
        this.appCompatActivity = activity;
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
            ToastUtil.showBottomLongText(activity, appCompatActivity.getString(R.string.pay_noinstanl));
            return;
        }
        //我们把请求到的参数全部给微信,调起微信APP的对象
        PayReq req = new PayReq();
        req.appId = payWeChatResponseBean.getAppid();
        req.partnerId = payWeChatResponseBean.getPartnerid();
        req.prepayId = payWeChatResponseBean.getPrepayid();
        req.nonceStr = payWeChatResponseBean.getNoncestr();
        req.timeStamp = payWeChatResponseBean.getTimestamp();
        req.packageValue = payWeChatResponseBean.getPackageX();
        req.sign = payWeChatResponseBean.getSign();
        //发送调起微信的请求
        wxapi.sendReq(req);
    }

    /**
     * Description:PaymentHelper
     * Time:2019-9-10 上午 10:43
     * Detail:TODO 支付宝支付
     */
    public void startAliPay(AppCompatActivity activity, PayAliPayResponseBean aliPayResponseBean) {
        this.appCompatActivity = activity;
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
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        ToastUtil.showBottomLongText(appCompatActivity, "支付成功");
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ToastUtil.showBottomLongText(appCompatActivity, "支付结果确认中");
                        } else if (TextUtils.equals(resultStatus, "6001")) { //用户中途取消
                            ToastUtil.showBottomLongText(appCompatActivity, "取消支付");
                        } else {
                            // 其他值就可以判断为支付失败
                            ToastUtil.showBottomLongText(appCompatActivity, "支付失败");
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };
}