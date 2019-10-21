package com.ucfo.youcaiwx.module.integral;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.utils.ActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-10-18 下午 2:52
 * Package: com.ucfo.youcaiwx.module.integral
 * FileName: IntegralPayResultActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 积分商品支付成功
 */
public class IntegralPayResultActivity extends BaseActivity {
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
    private String orderNum;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_integral_pay_result;
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
                BackPressListener();
            }
        });
    }

    /**
     * 返回上一级
     */
    private void BackPressListener() {
        finish();
        ActivityUtil.getInstance().finishToActivity(MineIntegralActivity.class, true);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            BackPressListener();
        }
        return false;
    }


    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderNum = bundle.getString(Constant.ORDER_NUM, "");
            price = bundle.getString(Constant.COURSE_PRICE, "0");

            textOrdernum.setText(orderNum);
            textPrice.setText(getResources().getString(R.string.integral_holder, price));
        }
    }

}
