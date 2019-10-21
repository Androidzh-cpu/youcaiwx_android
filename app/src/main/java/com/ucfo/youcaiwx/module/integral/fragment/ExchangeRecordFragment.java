package com.ucfo.youcaiwx.module.integral.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author: AND
 * Time: 2019-9-19.  上午 10:30
 * Package: com.ucfo.youcaiwx.module.integral.fragment
 * FileName: ExchangeRecordFragment
 * Description:TODO 积分 - 兑换记录
 */
public class ExchangeRecordFragment extends BaseFragment implements IIntegralExchangeRecordView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    Unbinder unbinder;
    private int user_id;
    private IntegralPresenter integralPresenter;
    private List<IntegralExchangeRecordBean.DataBean> list;
    private IntegralExchangeRecordAdapter integralExchangeRecordAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void initView(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        int left = DensityUtil.dp2px(12);
        int top = DensityUtil.dp2px(0.5F);
        recyclerview.addItemDecoration(new SpacesItemDecoration(left, top, ContextCompat.getColor(getActivity(), R.color.color_E6E6E6)));
    }

    @Override
    protected void initData() {
        SharedPreferencesUtils sharedPreferencesUtils = SharedPreferencesUtils.getInstance(getActivity());
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
    protected int setContentView() {
        return R.layout.fragment_integral_exchange;
    }

    @Override
    public void integralExchangeRecord(IntegralExchangeRecordBean bean) {
        if (bean != null) {
            if (bean.getData() != null && bean.getData().size() > 0) {
                List<IntegralExchangeRecordBean.DataBean> beanList = bean.getData();
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

        refreshlayout.finishRefresh();
    }

    private void initAdapter() {
        if (integralExchangeRecordAdapter == null) {
            integralExchangeRecordAdapter = new IntegralExchangeRecordAdapter(list, getActivity());
        } else {
            integralExchangeRecordAdapter.notifyDataSetChanged();
        }
        recyclerview.setAdapter(integralExchangeRecordAdapter);

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
        loadinglayout.showError();
    }

}
