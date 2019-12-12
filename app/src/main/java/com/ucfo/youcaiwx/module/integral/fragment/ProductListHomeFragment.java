package com.ucfo.youcaiwx.module.integral.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.integral.IntegralHomeCouponAdapter;
import com.ucfo.youcaiwx.adapter.integral.IntegralHomeGoodsAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.integral.IntegralShopHomeBean;
import com.ucfo.youcaiwx.module.integral.CommodityExchangeActivity;
import com.ucfo.youcaiwx.module.integral.MineIntegralActivity;
import com.ucfo.youcaiwx.module.integral.ProductListActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.integral.IntegralPresenter;
import com.ucfo.youcaiwx.presenter.view.integral.IIntegralHomeView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author: AND
 * Time: 2019-9-19.  上午 10:29
 * Package: com.ucfo.youcaiwx.module.integral.fragment
 * FileName: ProductListHomeFragment
 * Description:TODO 积分-商品列表-首页
 */
public class ProductListHomeFragment extends BaseFragment implements IIntegralHomeView {

    @BindView(R.id.btn_coupon_checkall)
    TextView btnCouponCheckall;
    @BindView(R.id.recyclerview_coupon)
    RecyclerView recyclerviewCoupon;
    @BindView(R.id.btn_goods_checkall)
    TextView btnGoodsCheckall;
    @BindView(R.id.recyclerview_goods)
    RecyclerView recyclerviewGoods;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    Unbinder unbinder;
    private IntegralPresenter integralPresenter;
    private List<IntegralShopHomeBean.DataBean.CouponBean> couponBeanList;
    private List<IntegralShopHomeBean.DataBean.ShopBean> shopBeanList;
    private IntegralHomeCouponAdapter integralHomeCouponAdapter;
    private IntegralHomeGoodsAdapter integralHomeGoodsAdapter;
    private MineIntegralActivity mineIntegralActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        if (rootView != null) {
            unbinder = ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void initView(View view) {
        FragmentActivity activity = getActivity();
        if (activity instanceof MineIntegralActivity) {
            mineIntegralActivity = (MineIntegralActivity) getActivity();
        }

        initLyaoutManager();
    }

    private void initLyaoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerviewCoupon.setLayoutManager(linearLayoutManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        gridLayoutManager.setSpanCount(3);
        recyclerviewGoods.setLayoutManager(gridLayoutManager);
        recyclerviewGoods.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        couponBeanList = new ArrayList<>();
        shopBeanList = new ArrayList<>();

        integralPresenter = new IntegralPresenter(this);
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                integralPresenter.inquireIntegralProductHome();
            }
        });
        refreshlayout.setEnableLoadMore(false);
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                integralPresenter.inquireIntegralProductHome();
            }
        });
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_integral_productlist;
    }

    @Override
    protected void onLazyLoadOnce() {
        super.onLazyLoadOnce();
        integralPresenter.inquireIntegralProductHome();
    }

    @Override
    public void inquiryIntegral(String integral) {
        //TODO nothing
    }

    @Override
    public void inqieryIntegralHome(IntegralShopHomeBean integralShopHomeBean) {
        if (integralShopHomeBean != null) {
            IntegralShopHomeBean.DataBean data = integralShopHomeBean.getData();
            List<IntegralShopHomeBean.DataBean.CouponBean> coupon = data.getCoupon();
            List<IntegralShopHomeBean.DataBean.ShopBean> shop = data.getShop();

            couponBeanList.clear();
            couponBeanList.addAll(coupon);
            shopBeanList.clear();
            shopBeanList.addAll(shop);
            initAdapter();
            if (loadinglayout != null) {
                loadinglayout.showContent();
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
        refreshlayout.finishRefresh();
    }

    private void initAdapter() {
        if (integralHomeCouponAdapter == null) {
            integralHomeCouponAdapter = new IntegralHomeCouponAdapter(getActivity(), couponBeanList);
        } else {
            integralHomeCouponAdapter.notifyDataSetChanged();
        }
        recyclerviewCoupon.setAdapter(integralHomeCouponAdapter);
        integralHomeCouponAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                IntegralShopHomeBean.DataBean.CouponBean couponBean = couponBeanList.get(position);
                int couponBeanId = couponBean.getId();
                Bundle bundle = new Bundle();
                bundle.putString(Constant.PRODUCT_ID, String.valueOf(couponBeanId));
                startActivity(CommodityExchangeActivity.class, bundle);
            }
        });

        if (integralHomeGoodsAdapter == null) {
            integralHomeGoodsAdapter = new IntegralHomeGoodsAdapter(getActivity(), shopBeanList);
        } else {
            integralHomeGoodsAdapter.notifyDataSetChanged();
        }
        recyclerviewGoods.setAdapter(integralHomeGoodsAdapter);
        integralHomeGoodsAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                IntegralShopHomeBean.DataBean.ShopBean shopBean = shopBeanList.get(position);
                String id = shopBean.getId();
                Bundle bundle = new Bundle();
                bundle.putString(Constant.PRODUCT_ID, id);
                startActivity(CommodityExchangeActivity.class, bundle);
            }
        });
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

    @OnClick({R.id.btn_coupon_checkall, R.id.btn_goods_checkall})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.INTEGRAL, mineIntegralActivity.getIntegral());
        switch (view.getId()) {
            case R.id.btn_coupon_checkall:
                //优惠券查看更多;
                bundle.putString(Constant.TYPE, Constant.INTEGRAL_TYPE_COUPON);
                startActivity(ProductListActivity.class, bundle);
                break;
            case R.id.btn_goods_checkall:
                //商品查看更多
                bundle.putString(Constant.TYPE, Constant.INTEGRAL_TYPE_PRODUCT);
                startActivity(ProductListActivity.class, bundle);
                break;
            default:
                break;
        }
    }
}
