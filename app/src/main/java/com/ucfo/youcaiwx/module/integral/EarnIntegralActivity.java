package com.ucfo.youcaiwx.module.integral;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.entity.integral.EarnIntegralBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.integral.IntegralPresenter;
import com.ucfo.youcaiwx.presenter.view.integral.IIntegralEarnView;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-9-19 上午 10:08
 * Package: com.ucfo.youcaiwx.module.integral
 * FileName: EarnIntegralActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 赚积分
 */
public class EarnIntegralActivity extends BaseActivity implements IIntegralEarnView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.showline)
    View showline;
    @BindView(R.id.recyclerview_day)
    RecyclerView recyclerviewDay;
    @BindView(R.id.recyclerview_new)
    RecyclerView recyclerviewNew;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    private IntegralPresenter integralPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_earn_integral;
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
        titlebarMidtitle.setText(getResources().getString(R.string.integral_gainIntegral));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }

    @Override
    protected void initData() {
        super.initData();
        integralPresenter = new IntegralPresenter(this);
        integralPresenter.earnIntegral();

        loadinglayout.setOnErrorInflateListener(new LoadingLayout.OnInflateListener() {
            @Override
            public void onInflate(View inflated) {
                integralPresenter.earnIntegral();
            }
        });
    }

    @Override
    public void earnItegral(EarnIntegralBean data) {
        if (data != null) {
            loadinglayout.showContent();
        } else {
            loadinglayout.showEmpty();
        }
    }

    @Override
    public void showLoading() {
        loadinglayout.showLoading();
    }

    @Override
    public void showLoadingFinish() {
    }

    @Override
    public void showError() {
        loadinglayout.showError();
    }
}
