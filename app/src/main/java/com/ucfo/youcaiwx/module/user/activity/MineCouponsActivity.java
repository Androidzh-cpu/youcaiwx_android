package com.ucfo.youcaiwx.module.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.user.MineCouponsAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.user.MineCouponsBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.MineCouponsPresenter;
import com.ucfo.youcaiwx.presenter.view.user.IMineCourponsView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-29 上午 10:11
 * FileName: MineCouponsActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 我的可用优惠券
 */
public class MineCouponsActivity extends BaseActivity implements IMineCourponsView, View.OnClickListener {

    private TextView titlebarMidtitle;
    private TextView titlebarRighttitle;
    private Toolbar titlebarToolbar;
    private View showline;
    private TextView couponCount;
    private RecyclerView recyclerview;
    private TextView btnDisabled;
    private LoadingLayout loadinglayout;
    private SmartRefreshLayout refreshLayout;

    private MineCouponsPresenter mineCouponsPresenter;
    private MineCouponsActivity context;
    private int user_id;
    private ArrayList<MineCouponsBean.DataBean> list;
    private MineCouponsAdapter mineCouponsAdapter;
    private boolean payEdit;
    private int coursePackageId;
    private Bundle bundle;

    @Override
    protected int setContentView() {
        return R.layout.activity_mine_coupons;
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
        titlebarMidtitle.setText(getResources().getString(R.string.mine_Coupons));
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
        titlebarMidtitle = (TextView) findViewById(R.id.titlebar_midtitle);
        titlebarRighttitle = (TextView) findViewById(R.id.titlebar_righttitle);
        titlebarToolbar = (Toolbar) findViewById(R.id.titlebar_toolbar);
        showline = (View) findViewById(R.id.showline);
        couponCount = (TextView) findViewById(R.id.coupon_count);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        btnDisabled = (TextView) findViewById(R.id.btn_disabled);
        btnDisabled.setOnClickListener(this);
        loadinglayout = (LoadingLayout) findViewById(R.id.loadinglayout);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);


        context = this;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        int topBottom = DensityUtil.dip2px(this, 26);
        int leftRight = DensityUtil.dip2px(this, 19);
        recyclerview.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom, ContextCompat.getColor(this, R.color.transparency)));
    }

    @Override
    protected void initData() {
        super.initData();
        mineCouponsPresenter = new MineCouponsPresenter(this);
        user_id = SharedPreferencesUtils.getInstance(context).getInt(Constant.USER_ID, 0);
        list = new ArrayList<>();

        bundle = getIntent().getExtras();
        if (bundle != null) {
            payEdit = bundle.getBoolean(Constant.PAY_EDIT, false);
            coursePackageId = bundle.getInt(Constant.COURSE_PACKAGE_ID, 0);
            mineCouponsPresenter.getAivilableCoupon(user_id, coursePackageId);
        } else {
            mineCouponsPresenter.getMineCouponsData(user_id, 1);
        }
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bundle != null) {
                    mineCouponsPresenter.getAivilableCoupon(user_id, coursePackageId);
                } else {
                    mineCouponsPresenter.getMineCouponsData(user_id, 1);
                }
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (bundle != null) {
                    mineCouponsPresenter.getAivilableCoupon(user_id, coursePackageId);
                } else {
                    mineCouponsPresenter.getMineCouponsData(user_id, 1);
                }
            }
        });
    }

    @Override
    public void getMineCouponData(MineCouponsBean result) {
        if (result != null) {
            if (result.getData().size() > 0) {
                List<MineCouponsBean.DataBean> beanList = result.getData();
                list.clear();
                list.addAll(beanList);
                initAdapter();
                if (loadinglayout != null) {
                    loadinglayout.showContent();
                }
            } else {
                if (loadinglayout != null) {
                    loadinglayout.showEmpty();
                }
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
        refreshLayout.finishRefresh();
    }

    private void initAdapter() {
        if (mineCouponsAdapter == null) {
            mineCouponsAdapter = new MineCouponsAdapter(this, list, 1);
            recyclerview.setAdapter(mineCouponsAdapter);
        } else {
            mineCouponsAdapter.notifyChange(list);
        }
        mineCouponsAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (payEdit) {
                    MineCouponsBean.DataBean bean = list.get(position);
                    Intent intent = new Intent();
                    intent.putExtra(Constant.TYPE, bean.getIs_type());
                    intent.putExtra(Constant.PAY_COUPONPRICE, bean.getCoupon_price());
                    intent.putExtra(Constant.PAY_COUPONID, bean.getCoupon_id());
                    setResult(Constant.REQUEST_COUPON, intent);
                    finish();
                }
            }
        });
        couponCount.setText(String.valueOf(list.size()));
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void showLoadingFinish() {

    }

    @Override
    public void showError() {
        loadinglayout.showError();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_disabled:
                startActivity(MineDisabledCouponsActivity.class, null);
                break;
            default:
                break;
        }
    }
}
