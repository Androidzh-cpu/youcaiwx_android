package com.ucfo.youcaiwx.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.utils.LogUtils;
import com.ucfo.youcaiwx.utils.pay.PaymentHelper2;

/**
 * Author: AND
 * Time: 2019-9-10.  上午 9:31
 * Package: com.ucfo.youcaiwx.wxapi
 * FileName: WXPayEntryActivity
 * Description:TODO 微信支付回调页
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constant.WEIXIN_KEY);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        LogUtils.e("onReq  " + baseReq.toString());
    }

    /**
     * Description:WXPayEntryActivity
     * Time:2019-9-10 上午 9:35
     * Detail:errCode = -1  //签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等
     * https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_12&index=2
     */
    @Override
    public void onResp(BaseResp baseResp) {
        LogUtils.e("onResp   " + baseResp.toString());
        /**
         * 结果码参考：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_5
         */
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int code = baseResp.errCode;
            //PayListenerUtil.getInstance(this).addCancel();
            switch (code) {
                case 0:
                    Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
                    PaymentHelper2.getInstance().getPayStateCallback().onPaySuccess("支付成功");
                    finish();
                    break;
                case -1:
                    // 支付失败 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等
                    Toast.makeText(this, "支付失败", Toast.LENGTH_LONG).show();
                    PaymentHelper2.getInstance().getPayStateCallback().onPayFailed("支付失败");
                    finish();
                    break;
                case -2:
                    Toast.makeText(this, "支付取消", Toast.LENGTH_LONG).show();
                    PaymentHelper2.getInstance().getPayStateCallback().onPayCancel();
                    finish();
                    break;
                default:
                    break;
            }
        }
    }
}
