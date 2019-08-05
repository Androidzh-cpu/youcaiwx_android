package com.ucfo.youcai.view.pay;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucfo.youcai.R;
import com.ucfo.youcai.base.BaseActivity;
import com.ucfo.youcai.common.ApiStores;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcai.view.main.activity.WebActivity;
import com.ucfo.youcai.widget.customview.LoadingLayout;
import com.ucfo.youcai.widget.dialog.InvoiceActiveDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-7-31 下午 6:05
 * Package: com.ucfo.youcai.view.pay
 * FileName: CommitOrderActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 提交订单
 */
public class CommitOrderActivity extends BaseActivity {

    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.showline)
    View showline;
    @BindView(R.id.text_addAddress)
    TextView textAddAddress;
    @BindView(R.id.address_name)
    TextView addressName;
    @BindView(R.id.address_phone)
    TextView addressPhone;
    @BindView(R.id.address_content)
    TextView addressContent;
    @BindView(R.id.btn_address)
    LinearLayout btnAddress;
    @BindView(R.id.course_image)
    ImageView courseImage;
    @BindView(R.id.course_title)
    TextView courseTitle;
    @BindView(R.id.course_teacher)
    TextView courseTeacher;
    @BindView(R.id.course_endtime)
    TextView courseEndtime;
    @BindView(R.id.coupon_count)
    TextView couponCount;
    @BindView(R.id.coupon_des)
    TextView couponDes;
    @BindView(R.id.btn_coupons)
    LinearLayout btnCoupons;
    @BindView(R.id.invoice_content)
    TextView invoiceContent;
    @BindView(R.id.btn_invoice)
    LinearLayout btnInvoice;
    @BindView(R.id.text_price)
    TextView textPrice;
    @BindView(R.id.text_discountsprice)
    TextView textDiscountsprice;
    @BindView(R.id.text_finalprice)
    TextView textFinalprice;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.btn_paynotice)
    TextView btnPaynotice;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    private CommitOrderActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_commit_order;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

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
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        context = this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
    }

    @Override
    protected void initData() {
        super.initData();

        loadinglayout.showContent();

    }

    @OnClick({R.id.btn_address, R.id.btn_coupons, R.id.btn_invoice, R.id.btn_paynotice, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_address:
                //TODO 添加地址
                break;
            case R.id.btn_coupons:
                //TODO 优惠券
                break;
            case R.id.btn_invoice:
                //TODO 发票
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                InvoiceActiveDialog invoiceActiveDialog = new InvoiceActiveDialog();
                invoiceActiveDialog.show(fragmentTransaction, "invoice");
                break;
            case R.id.btn_paynotice:
                //TODO 支付须知
                Bundle bundle = new Bundle();
                bundle.putString(Constant.WEB_TITLE, getResources().getString(R.string.pay_agreement));
                bundle.putString(Constant.WEB_URL, ApiStores.PAY_AGREEMENT);
                startActivity(WebActivity.class, bundle);
                break;
            case R.id.btn_next:
                //TODO 提交订单
                startActivity(PayActivity.class, null);
                break;
            default:
                break;
        }
    }
}
