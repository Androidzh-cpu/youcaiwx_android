package com.ucfo.youcaiwx.module.user.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.user.MineCouponsAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.user.MineCouponsBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.MineCouponsPresenter;
import com.ucfo.youcaiwx.presenter.view.user.IMineCourponsView;
import com.ucfo.youcaiwx.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-30 上午 11:26
 * FileName: MineDisabledCouponsActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 失效优惠券
 */

public class MineDisabledCouponsActivity extends BaseActivity implements IMineCourponsView {

    private TextView titlebarMidtitle;
    private TextView titlebarRighttitle;
    private Toolbar titlebarToolbar;
    private View showline;
    private RecyclerView recyclerview;
    private LoadingLayout loadinglayout;

    private ArrayList<MineCouponsBean.DataBean> list;
    private MineCouponsAdapter mineCouponsAdapter;
    private MineCouponsPresenter mineCouponsPresenter;
    private int user_id;

    @Override
    protected int setContentView() {
        return R.layout.activity_mine_disabled_coupons;
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
        titlebarMidtitle.setText(getResources().getString(R.string.mine_disabledCoupons));
        showline.setVisibility(View.GONE);
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        titlebarMidtitle = (TextView) findViewById(R.id.titlebar_midtitle);
        titlebarRighttitle = (TextView) findViewById(R.id.titlebar_righttitle);
        titlebarToolbar = (Toolbar) findViewById(R.id.titlebar_toolbar);
        showline = (View) findViewById(R.id.showline);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        loadinglayout = (LoadingLayout) findViewById(R.id.loadinglayout);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        int topBottom = DensityUtil.dip2px(this, 26);
        int leftRight = DensityUtil.dip2px(this, 19);
        recyclerview.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom, getResources().getColor(R.color.transparent)));
        recyclerview.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    @Override
    protected void initData() {
        super.initData();
        mineCouponsPresenter = new MineCouponsPresenter(this);
        user_id = SharedPreferencesUtils.getInstance(this).getInt(Constant.USER_ID, 0);
        list = new ArrayList<>();

        mineCouponsPresenter.getMineCouponsData(user_id, 2);
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mineCouponsPresenter.getMineCouponsData(user_id, 2);
            }
        });
    }


    @Override
    protected void processLogic(Bundle savedInstanceState) {

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
    }

    private void initAdapter() {
        if (mineCouponsAdapter == null) {
            mineCouponsAdapter = new MineCouponsAdapter(this, list, 2);
            recyclerview.setAdapter(mineCouponsAdapter);
        } else {
            mineCouponsAdapter.notifyChange(list);
        }
    }

    @Override
    public void showLoading() {
        if (loadinglayout != null) {
            loadinglayout.showLoading();
        }
    }

    @Override
    public void showLoadingFinish() {
    }

    @Override
    public void showError() {
        if (loadinglayout != null) {
            loadinglayout.showError();
        }
    }
}
