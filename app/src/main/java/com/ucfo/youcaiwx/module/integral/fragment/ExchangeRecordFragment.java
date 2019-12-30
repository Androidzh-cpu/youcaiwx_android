package com.ucfo.youcaiwx.module.integral.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.integral.IntegralExchangeRecordAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.integral.IntegralExchangeRecordBean;
import com.ucfo.youcaiwx.module.integral.CommodityExchangeActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.integral.IntegralPresenter;
import com.ucfo.youcaiwx.presenter.view.integral.IIntegralExchangeRecordView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-9-19.  上午 10:30
 * Package: com.ucfo.youcaiwx.module.integral.fragment
 * FileName: ExchangeRecordFragment
 * Description:TODO 积分 - 兑换记录
 */
public class ExchangeRecordFragment extends BaseFragment implements IIntegralExchangeRecordView {
    private RecyclerView recyclerview;
    private LoadingLayout loadinglayout;
    private SmartRefreshLayout refreshlayout;

    private int user_id;
    private IntegralPresenter integralPresenter;
    private List<IntegralExchangeRecordBean.DataBean> list;
    private IntegralExchangeRecordAdapter integralExchangeRecordAdapter;
    private RecyclerView mRecyclerview;
    private LoadingLayout mLoadinglayout;
    private SmartRefreshLayout mRefreshlayout;

    @Override
    protected int setContentView() {
        return R.layout.fragment_integral_exchange;
    }

    @Override
    protected void initView(View itemView) {
        recyclerview = (RecyclerView) itemView.findViewById(R.id.recyclerview);
        loadinglayout = (LoadingLayout) itemView.findViewById(R.id.loadinglayout);
        refreshlayout = (SmartRefreshLayout) itemView.findViewById(R.id.refreshlayout);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        int left = DensityUtil.dp2px(12);
        int top = DensityUtil.dp2px(0.5F);
        recyclerview.addItemDecoration(new SpacesItemDecoration(left, top, ContextCompat.getColor(getContext(), R.color.color_E6E6E6)));
    }

    @Override
    protected void initData() {
        SharedPreferencesUtils sharedPreferencesUtils = SharedPreferencesUtils.getInstance(getContext());
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        integralPresenter = new IntegralPresenter(this);

        list = new ArrayList<>();

        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                inqueryExchangeRecord();
            }
        });
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inqueryExchangeRecord();
            }
        });
    }

    @Override
    protected void onLazyLoadOnce() {
        super.onLazyLoadOnce();
        inqueryExchangeRecord();
    }

    private void inqueryExchangeRecord() {
        if (integralPresenter != null) {
            integralPresenter.inquireIntegralExchangeRecord(user_id);
        }
    }

    @Override
    public void integralExchangeRecord(IntegralExchangeRecordBean bean) {
        if (bean != null) {
            if (bean.getData() != null && bean.getData().size() > 0) {
                List<IntegralExchangeRecordBean.DataBean> beanList = bean.getData();
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

        if (refreshlayout != null) {
            refreshlayout.finishRefresh();
        }
    }

    private void initAdapter() {
        if (integralExchangeRecordAdapter == null) {
            integralExchangeRecordAdapter = new IntegralExchangeRecordAdapter(list, getContext());
            recyclerview.setAdapter(integralExchangeRecordAdapter);
        } else {
            integralExchangeRecordAdapter.notifyChange(list);
        }

        integralExchangeRecordAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                IntegralExchangeRecordBean.DataBean dataBean = list.get(position);
                String id = dataBean.getGood_id();
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

}
