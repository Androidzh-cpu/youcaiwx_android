package com.ucfo.youcai.view.pay;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ucfo.youcai.R;
import com.ucfo.youcai.base.BaseActivity;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.utils.sharedutils.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
}
