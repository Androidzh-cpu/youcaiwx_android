package com.ucfo.youcaiwx.module.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.home.InformationAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.home.InformationListBean;
import com.ucfo.youcaiwx.module.main.activity.WebActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.home.InformationPresenter;
import com.ucfo.youcaiwx.presenter.view.home.IInformationView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-9-11 上午 9:13
 * Package: com.ucfo.youcaiwx.view.home
 * FileName: InformationActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 资讯页
 */
public class InformationActivity extends BaseActivity implements IInformationView {
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
    private InformationPresenter informationPresenter;
    private ArrayList<InformationListBean.DataBean> list;
    private int pageIndex = 1;
    private String parent_id, type_id;
    private InformationAdapter informationAdapter;

    @Override
    protected int setContentView() {
        return R.layout.activity_information;
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
        titlebarMidtitle.setText(getResources().getString(R.string.home_news));
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
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<>();
        informationPresenter = new InformationPresenter(this);

        informationPresenter.getInformationListData(pageIndex, parent_id, type_id);
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                informationPresenter.getInformationListData(pageIndex, parent_id, type_id);
            }
        });
        refreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex += 1;
                informationPresenter.getInformationListData(pageIndex, parent_id, type_id);
            }
        });
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                informationPresenter.getInformationListData(pageIndex, parent_id, type_id);
            }
        });
    }

    @Override
    public void infomationList(InformationListBean dataBean) {
        if (dataBean != null) {//TODO 分页加载或者初次加载都成功情况下
            if (dataBean.getData() != null && dataBean.getData().size() > 0) {
                List<InformationListBean.DataBean> data = dataBean.getData();
                if (pageIndex == 1) {//初次加载或刷新操作
                    list.clear();
                    list.addAll(data);
                } else {//加载更多
                    if (data.size() > 0) {
                        list.addAll(data);
                    }
                }
                initAdapter();
                if (loadinglayout != null) {
                    loadinglayout.showContent();
                }
            } else {//TODO 加载的数据为0的情况
                if (pageIndex == 1) {//初次加载或刷新
                    if (list != null && list.size() > 0) {
                        informationAdapter.notifyDataSetChanged();
                    } else {
                        if (loadinglayout != null) {
                            loadinglayout.showEmpty();
                        }
                    }
                } else {//架子啊更多
                    if (list != null && list.size() > 0) {
                        informationAdapter.notifyDataSetChanged();
                        refreshlayout.finishLoadMoreWithNoMoreData();
                    } else {
                        if (loadinglayout != null) {
                            loadinglayout.showEmpty();
                        }
                    }
                }
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
        refreshlayout.finishRefresh();
        refreshlayout.finishLoadMore();
    }

    //初始化适配器
    private void initAdapter() {
        if (informationAdapter == null) {
            informationAdapter = new InformationAdapter(list, this);
            recyclerview.setAdapter(informationAdapter);
        } else {
            informationAdapter.notifyChange(list);
        }
        informationAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                InformationListBean.DataBean dataBean = list.get(position);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.WEB_URL, dataBean.getUrl());
                bundle.putString(Constant.WEB_TITLE, dataBean.getTitle());
                startActivity(WebActivity.class, bundle);
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
