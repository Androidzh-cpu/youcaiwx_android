package com.ucfo.youcaiwx.module.pay;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tencent.bugly.crashreport.CrashReport;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.pay.FinalPayResultBean;
import com.ucfo.youcaiwx.entity.pay.PayAliPayResponseBean;
import com.ucfo.youcaiwx.entity.pay.PayWeChatResponseBean;
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
    TextView btnWechatPay;
    @BindView(R.id.pay_Alipay)
    TextView payAlipay;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    private PayActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int userId;
    private Bundle bundle;
    private String orderNum;
    private float price;
    private PayMentPresenter payMentPresenter;

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
            orderNum = bundle.getString(Constant.ORDER_NUM, "");
            price = bundle.getFloat(Constant.COURSE_PRICE, 0);

            //订单编号
            textOrdernum.setText(String.valueOf(orderNum));
            //订单价格
            textPrice.setText(String.valueOf(price));
        }

        if (TextUtils.isEmpty(orderNum)) {
            loadinglayout.showEmpty();
        } else {
            payMentPresenter = new PayMentPresenter(this);
            loadinglayout.showContent();
        }

    }

    @OnClick({R.id.btn_WechatPay, R.id.pay_Alipay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_WechatPay:
                //TODO 微信支付
                wechatPayMethod();
                break;
            case R.id.pay_Alipay:
                //TODO 支付宝支付
                alipayMethod();
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
        payMentPresenter.initWeCheatOrderForm();
    }

    /**
     * Description:PayActivity
     * Time:2019-9-10 上午 9:27
     * Detail:TODO 支付宝支付
     */
    private void alipayMethod() {
        payMentPresenter.initAlipayOrderForm();
    }

    /**
     * 支付平台支付回调
     */
    private PayStateCallback2 payStateCallback = new PayStateCallback2() {
        @Override
        public void onPaySuccess(String describe) {
            ToastUtil.showBottomShortText(context, "支付成功");
        }

        @Override
        public void onPayWatting(String describe) {
            ToastUtil.showBottomShortText(context, "正再支付");
        }

        @Override
        public void onPayFailed(String describe) {
            ToastUtil.showBottomShortText(context, "支付失败");
        }

        @Override
        public void onPayCancel() {

        }
    };

    /**
     * 根据提交订单得到的订单号通过服务端获取到支付平台下发的订单,然后调用支付平台
     */
    @Override
    public void initWeCheatOrderFormDetail(PayWeChatResponseBean data) {
        PaymentHelper2.getInstance(this, payStateCallback).startWeChatPay(null);
    }

    @Override
    public void initAlipayOrderFormDetail(PayAliPayResponseBean data) {
        PaymentHelper2.getInstance(this, payStateCallback).startAliPay(null);
    }

    /**
     * 待支付平台支付成功后再请求一次服务端以确认结果
     */
    @Override
    public void checkPayResult(FinalPayResultBean data) {

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
        loadinglayout.showError();
    }
}
