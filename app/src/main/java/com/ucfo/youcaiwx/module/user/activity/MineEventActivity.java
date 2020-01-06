package com.ucfo.youcaiwx.module.user.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.home.EventListAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.home.event.EventDetailedBean;
import com.ucfo.youcaiwx.entity.home.event.EventListBean;
import com.ucfo.youcaiwx.module.home.event.EventDetailedActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.home.event.EventPresenter;
import com.ucfo.youcaiwx.presenter.view.home.event.IEventView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2020-1-6 上午 10:47
 * Package: com.ucfo.youcaiwx.module.user.activity
 * FileName: MineEventActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 我的活动
 */
public class MineEventActivity extends BaseActivity implements IEventView {

    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.showline)
    View showline;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;

    private List<EventListBean.DataBean> list;
    private EventPresenter eventPresenter;
    private EventListAdapter listAdapter;
    private int user_id;

    @Override
    protected int setContentView() {
        return R.layout.activity_mine_event;
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
        titlebarMidtitle.setText(getResources().getString(R.string.mine_event));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<>();
        user_id = SharedPreferencesUtils.getInstance(this).getInt(Constant.USER_ID, 0);
        eventPresenter = new EventPresenter(this);
        loadNetData();

        refreshlayout.setEnableLoadMore(false);
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadNetData();
            }
        });
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNetData();
            }
        });
    }

    private void loadNetData() {
        if (eventPresenter != null) {
            eventPresenter.initMineEvent(String.valueOf(user_id));
        }
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void initEventList(EventListBean bean) {
        if (bean != null) {
            if (bean.getData() != null && bean.getData().size() > 0) {
                List<EventListBean.DataBean> data = bean.getData();
                list.clear();
                list.addAll(data);

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
        if (listAdapter == null) {
            listAdapter = new EventListAdapter(this, list);
            recyclerview.setAdapter(listAdapter);
        } else {
            listAdapter.notifyChange(list);
        }
        listAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.ID, list.get(position).getId());
                startActivity(EventDetailedActivity.class, bundle);
            }
        });

    }

    @Override
    public void initEventDetailed(EventDetailedBean bean) {
        //TODO nothing
    }

    @Override
    public void eventApplyResult(int status, String msg) {
        //TODO nothing
    }

    @Override
    public void showLoading() {
        //TODO nothing
    }

    @Override
    public void showLoadingFinish() {
        //TODO nothing
    }

    @Override
    public void showError() {
        if (loadinglayout != null) {
            loadinglayout.showError();
        }
    }
}
