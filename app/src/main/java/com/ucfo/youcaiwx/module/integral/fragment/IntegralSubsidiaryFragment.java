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
import com.ucfo.youcaiwx.adapter.integral.IntegralDetailAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.integral.IntegralDetailBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.integral.IntegralPresenter;
import com.ucfo.youcaiwx.presenter.view.integral.IIntegralDetaillView;
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
 * Time: 2019-9-19.  上午 10:31
 * Package: com.ucfo.youcaiwx.module.integral.fragment
 * FileName: IntegralSubsidiaryFragment
 * Description:TODO 积分 - 积分明细
 */
public class IntegralSubsidiaryFragment extends BaseFragment implements IIntegralDetaillView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    Unbinder unbinder;
    private IntegralPresenter integralPresenter;
    private int user_id;
    private List<IntegralDetailBean.DataBean> list;
    private IntegralDetailAdapter integralDetailAdapter;

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
        initLayoutManager();
    }

    private void initLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        int topBottom = DensityUtil.dp2px(0.5F);
        int leftRight = DensityUtil.dp2px(12);
        recyclerview.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom, ContextCompat.getColor(context, R.color.color_E6E6E6)));
    }

    @Override
    protected void initData() {
        integralPresenter = new IntegralPresenter(this);
        SharedPreferencesUtils sharedPreferencesUtils = SharedPreferencesUtils.getInstance(getActivity());
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);

        list = new ArrayList<>();

        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                integralPresenter.inquireIntegralDetail(user_id);
            }
        });
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                integralPresenter.inquireIntegralDetail(user_id);
            }
        });
    }

    @Override
    protected void onLazyLoadOnce() {
        super.onLazyLoadOnce();
        integralPresenter.inquireIntegralDetail(user_id);
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_integral_subsidiary;
    }

    @Override
    public void integralDetail(IntegralDetailBean data) {
        if (data != null) {
            if (data.getData() != null && data.getData().size() > 0) {
                List<IntegralDetailBean.DataBean> dataBeanList = data.getData();
                list.clear();
                list.addAll(dataBeanList);
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
        if (integralDetailAdapter == null) {
            integralDetailAdapter = new IntegralDetailAdapter(list, getActivity());
            recyclerview.setAdapter(integralDetailAdapter);
        } else {
            integralDetailAdapter.notifyChange(list);
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
