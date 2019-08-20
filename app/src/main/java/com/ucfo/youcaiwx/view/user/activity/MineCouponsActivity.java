package com.ucfo.youcaiwx.view.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-7-29 上午 10:11
 * FileName: MineCouponsActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 我的可用优惠券
 */
public class MineCouponsActivity extends BaseActivity implements IMineCourponsView {

    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.showline)
    View showline;
    @BindView(R.id.coupon_count)
    TextView couponCount;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_disabled)
    TextView btnDisabled;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    private MineCouponsPresenter mineCouponsPresenter;
    private MineCouponsActivity context;
    private int user_id;
    private ArrayList<MineCouponsBean.DataBean> list;
    private MineCouponsAdapter mineCouponsAdapter;
    private boolean payEdit;
    private int coursePackageId;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

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
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        context = this;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        int topBottom = DensityUtil.dip2px(context, 26);
        int leftRight = DensityUtil.dip2px(context, 19);
        recyclerview.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom, ContextCompat.getColor(context, R.color.transparency)));
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
    }

    @Override
    public void getMineCouponData(MineCouponsBean result) {
        if (result != null) {
            if (result.getData().size() > 0) {
                List<MineCouponsBean.DataBean> beanList = result.getData();
                list.clear();
                list.addAll(beanList);
                initAdapter();
                loadinglayout.showContent();
            } else {
                loadinglayout.showEmpty();
            }
        } else {
            loadinglayout.showEmpty();
        }
    }

    private void initAdapter() {
        if (mineCouponsAdapter == null) {
            mineCouponsAdapter = new MineCouponsAdapter(this, list, 1);
        } else {
            mineCouponsAdapter.notifyDataSetChanged();
        }
        recyclerview.setAdapter(mineCouponsAdapter);
        mineCouponsAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (payEdit) {
                    MineCouponsBean.DataBean bean = list.get(position);
                    Intent intent = new Intent();
                    intent.putExtra(Constant.TYPE, bean.getIs_type());
                    intent.putExtra(Constant.PAY_COUPONPRICE, bean.getCoupon_price());
                    intent.putExtra(Constant.PAY_COUPONID, bean.getCoupon_id());
                    setResult(10001, intent);
                    finish();
                }
            }
        });

        couponCount.setText(String.valueOf(list.size()));
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

    @OnClick(R.id.btn_disabled)
    public void onViewClicked() {
        startActivity(MineDisabledCouponsActivity.class, null);
    }
}
