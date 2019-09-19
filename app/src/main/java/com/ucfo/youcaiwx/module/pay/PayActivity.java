package com.ucfo.youcaiwx.module.pay;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.tencent.bugly.crashreport.CrashReport;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-9-5 上午 11:35
 * Package: com.ucfo.youcaiwx.view.pay
 * FileName: PayActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 订单支付
 */
public class PayActivity extends BaseActivity {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.showline)
    View showline;
    @BindView(R.id.text_ordernum)
    TextView textOrdernum;
    @BindView(R.id.text_price)
    TextView textPrice;
    @BindView(R.id.btn_WechatPay)
    TextView btnWechatPay;
    @BindView(R.id.pay_Alipay)
    TextView payAlipay;
    private PayActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int userId;
    private Bundle bundle;
    private String orderNum;
    private float price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_pay;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        context = this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        userId = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);

        // 上报后的Crash会显示该标签
        CrashReport.setUserSceneTag(this, Constant.BUGLY_TAG_PAY);
    }

    @Override
    protected void initData() {
        super.initData();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            orderNum = bundle.getString(Constant.ORDER_NUM, "");
            price = bundle.getFloat(Constant.COURSE_PRICE, 0);

            textOrdernum.setText(String.valueOf(orderNum));
            textPrice.setText(String.valueOf(price));
        }
    }

    @OnClick({R.id.btn_WechatPay, R.id.pay_Alipay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_WechatPay:
                //TODO 微信支付
                break;
            case R.id.pay_Alipay:
                //TODO 支付宝支付
                break;
            default:
                break;
        }
    }

    /**
     * Description:PayActivity
     * Time:2019-9-10 上午 9:27
     * Detail:TODO 微信支付
     */
    private void wechatPayMethod() {

    }

    /**
     * Description:PayActivity
     * Time:2019-9-10 上午 9:27
     * Detail:TODO 支付宝支付
     */
    private void alipayMethod() {

    }
}
