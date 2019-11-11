package com.ucfo.youcaiwx.module.pay;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.bugly.crashreport.CrashReport;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.pay.PayAliPayResponseBean;
import com.ucfo.youcaiwx.entity.pay.PayWeChatResponseBean;
import com.ucfo.youcaiwx.module.main.activity.WebActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.pay.PayMentPresenter;
import com.ucfo.youcaiwx.presenter.view.pay.IPayMentView;
import com.ucfo.youcaiwx.utils.pay.PayStateCallback2;
import com.ucfo.youcaiwx.utils.pay.PaymentHelper2;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-9-5 上午 11:35
 * Package: com.ucfo.youcaiwx.view.pay
 * FileName: PayActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 订单支付(支付宝,微信,银联,京东)
 */
public class PayActivity extends BaseActivity implements IPayMentView {
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
    LinearLayout btnWechatPay;
    @BindView(R.id.pay_Alipay)
    LinearLayout btnAlipay;
    @BindView(R.id.btn_payJingdong)
    LinearLayout btnJingDong;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    private PayActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int userId;
    private Bundle bundle;
    private String orderFormNum;
    private float price;
    private PayMentPresenter payMentPresenter;
    //京东支付表示
    private boolean isPayBuyJingDong = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPayBuyJingDong) {
            if (payMentPresenter != null) {
                if (!TextUtils.isEmpty(orderFormNum)) {
                    payMentPresenter.checkPayResult(orderFormNum);
                }
            }
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_pay;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(titlebarToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        titlebarMidtitle.setText(getResources().getString(R.string.pay_title));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showline.setVisibility(View.GONE);
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
            orderFormNum = bundle.getString(Constant.ORDER_NUM, "");
            price = bundle.getFloat(Constant.COURSE_PRICE, 0);

            //订单编号
            textOrdernum.setText(String.valueOf(orderFormNum));
            //订单价格
            textPrice.setText(String.valueOf(price));
        }

        if (TextUtils.isEmpty(orderFormNum)) {
            loadinglayout.showEmpty();
        } else {
            payMentPresenter = new PayMentPresenter(this);
            loadinglayout.showContent();
        }
    }

    /**
     * 是否允许点击
     */
    private void setPayButtonEnable(boolean flag) {
        btnWechatPay.setEnabled(flag);
        btnAlipay.setEnabled(flag);
    }

    @OnClick({R.id.btn_WechatPay, R.id.pay_Alipay, R.id.btn_payJingdong})
    public void onViewClicked(View view) {
        setPayButtonEnable(false);
        switch (view.getId()) {
            case R.id.btn_WechatPay:
                //TODO 微信支付
                wechatPayMethod();
                break;
            case R.id.pay_Alipay:
                //TODO 支付宝支付
                //alipayMethod();
                break;
            case R.id.btn_payJingdong:
                //TODO 京东支付
                String jingDongPay = PaymentHelper2.startJingDongPay(String.valueOf(userId), String.valueOf(price), orderFormNum);
                bundle.putString(Constant.WEB_TITLE, getResources().getString(R.string.pay_jingdong));
                bundle.putString(Constant.WEB_URL, ApiStores.PAY_JINGDONG + jingDongPay);
                startActivity(WebActivity.class, bundle);
                isPayBuyJingDong = true;
                break;
            default:
                break;
        }
        setPayButtonEnable(true);
    }

    /**
     * Description:PayActivity
     * Time:2019-9-10 上午 9:27
     * Detail:TODO 微信支付
     */
    private void wechatPayMethod() {
        payMentPresenter.initWeCheatOrderForm(orderFormNum, String.valueOf(userId));
    }

    /**
     * Description:PayActivity
     * Time:2019-9-10 上午 9:27
     * Detail:TODO 支付宝支付
     */
    private void alipayMethod() {
        payMentPresenter.initAlipayOrderForm(orderFormNum, String.valueOf(userId));
    }

    /**
     * 支付平台支付回调
     */
    public PayStateCallback2 payStateCallback = new PayStateCallback2() {
        @Override
        public void onPaySuccess(String describe) {
            payMentPresenter.checkPayResult(orderFormNum);
        }

        @Override
        public void onPayWatting(String describe) {
            toastInfo(describe);
        }

        @Override
        public void onPayFailed(String describe) {
            toastInfo(describe);
        }

        @Override
        public void onPayCancel(String describe) {
            toastInfo(describe);
        }
    };

    /**
     * 根据提交订单得到的订单号通过服务端获取到支付平台下发的订单,然后调用支付平台
     */
    @Override
    public void initWeCheatOrderFormDetail(PayWeChatResponseBean data) {
        if (data != null) {
            PaymentHelper2.getInstance(PayActivity.this, payStateCallback).startWeChatPay(data);
        } else {
            toastInfo(getResources().getString(R.string.operation_Error));
        }
    }

    @Override
    public void initAlipayOrderFormDetail(PayAliPayResponseBean data) {
        //PaymentHelper2.getInstance(PayActivity.this, payStateCallback).startAliPay(data);
    }

    /**
     * 待支付平台支付成功后再请求一次服务端以确认结果
     */
    @Override
    public void checkPayResult(int status) {
        if (status == 1) {
            toastInfo(getResources().getString(R.string.pay_result_success));
            finish();
        } else {
            toastInfo(getResources().getString(R.string.pay_result_failed));
        }
    }

    @Override
    public void showLoading() {
        setProcessLoading(null, true);
    }

    @Override
    public void showLoadingFinish() {
        dismissPorcess();
    }

    @Override
    public void showError() {
        //loadinglayout.showError();
    }

    private void toastInfo(String s) {
        if (s != null) {
            ToastUtil.showBottomShortText(this, s);
        }
    }
}
