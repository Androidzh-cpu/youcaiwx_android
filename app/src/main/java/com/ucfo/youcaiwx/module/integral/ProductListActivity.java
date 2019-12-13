package com.ucfo.youcaiwx.module.integral;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.integral.IntegralCouponAdapter;
import com.ucfo.youcaiwx.adapter.integral.IntegralProductAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.integral.IntegralProductListBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.integral.IntegralPresenter;
import com.ucfo.youcaiwx.presenter.view.integral.IIntegralGoodsListView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-10-15 \
 * Package: com.ucfo.youcaiwx.module.integral
 * FileName: ProductListActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 商品列表
 */
public class ProductListActivity extends BaseActivity implements IIntegralGoodsListView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.showline)
    View showline;
    @BindView(R.id.text_integral)
    TextView textIntegral;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private Bundle bundle;
    private String type;
    private List<IntegralProductListBean.DataBean> list;
    private IntegralPresenter integralPresenter;
    private IntegralProductAdapter integralProductAdapter;
    private IntegralCouponAdapter integralCouponAdapter;

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
    protected int setContentView() {
        return R.layout.activity_product_list;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);

        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(this);

    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<>();

        bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getString(Constant.TYPE);
            String string = bundle.getString(Constant.INTEGRAL, "");
            if (!TextUtils.isEmpty(string)) {
                textIntegral.setText(string);
            } else {
                textIntegral.setText("***");
            }

            integralPresenter = new IntegralPresenter(this);

            inquireProductData();
        }
        initLayoutManager();

        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inquireProductData();
            }
        });
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                inquireProductData();
            }
        });
    }

    private void inquireProductData() {
        //设置标题
        if (TextUtils.equals(type, Constant.INTEGRAL_TYPE_PRODUCT)) {
            //TODO 实体商品
            titlebarMidtitle.setText(getResources().getString(R.string.integral_exchangegoods));

            integralPresenter.inquireIntegralProductList(2);
        } else if (TextUtils.equals(type, Constant.INTEGRAL_TYPE_COUPON)) {
            //TODO 优惠券
            titlebarMidtitle.setText(getResources().getString(R.string.integral_exchangecoupon));

            integralPresenter.inquireIntegralProductList(1);
        }
    }

    private void initLayoutManager() {
        if (TextUtils.equals(type, Constant.INTEGRAL_TYPE_PRODUCT)) {
            //商品列表
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
            gridLayoutManager.setSpanCount(3);
            recyclerview.setLayoutManager(gridLayoutManager);
        } else if (TextUtils.equals(type, Constant.INTEGRAL_TYPE_COUPON)) {
            //优惠券列表
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerview.setLayoutManager(linearLayoutManager);
            int topbottom = DensityUtil.dp2px(0.5F);
            int leftRight = DensityUtil.dp2px(12);
            recyclerview.addItemDecoration(new SpacesItemDecoration(0, topbottom, ContextCompat.getColor(this, R.color.color_E6E6E6)));
        }
    }

    @Override
    public void inqueryIntegralProductList(IntegralProductListBean bean) {
        if (bean != null) {
            if (bean.getData().size() > 0) {
                List<IntegralProductListBean.DataBean> beanList = bean.getData();
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

        refreshlayout.finishRefresh();
    }

    private void initAdapter() {
        if (TextUtils.equals(type, Constant.INTEGRAL_TYPE_PRODUCT)) {
            if (integralProductAdapter == null) {
                integralProductAdapter = new IntegralProductAdapter(list, this);
                recyclerview.setAdapter(integralProductAdapter);
            } else {
                integralProductAdapter.notifyChange(list);
            }
            integralProductAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    IntegralProductListBean.DataBean bean = list.get(position);
                    int id = bean.getId();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.PRODUCT_ID, String.valueOf(id));
                    startActivity(CommodityExchangeActivity.class, bundle);
                }
            });
        } else if (TextUtils.equals(type, Constant.INTEGRAL_TYPE_COUPON)) {
            if (integralCouponAdapter == null) {
                integralCouponAdapter = new IntegralCouponAdapter(this, list);
                recyclerview.setAdapter(integralCouponAdapter);
            } else {
                integralCouponAdapter.notifyChange(list);
            }
            integralCouponAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    IntegralProductListBean.DataBean bean = list.get(position);
                    int id = bean.getId();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.PRODUCT_ID, String.valueOf(id));
                    startActivity(CommodityExchangeActivity.class, bundle);
                }
            });
        }
    }

    @Override
    public void showLoading() {

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
