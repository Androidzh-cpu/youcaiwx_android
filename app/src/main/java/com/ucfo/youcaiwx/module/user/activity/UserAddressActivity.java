package com.ucfo.youcaiwx.module.user.activity;

import android.content.Intent;
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
import com.ucfo.youcaiwx.adapter.address.UserAddressListAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.address.AddressDetailBean;
import com.ucfo.youcaiwx.entity.address.AddressListBean;
import com.ucfo.youcaiwx.entity.address.StateStatusBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.UserAddressPresenter;
import com.ucfo.youcaiwx.presenter.view.user.IUserAddressView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.baseadapter.OnItemClickListener;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-6-13 下午 1:59
 * FileName: UserAddressActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 我的地址
 */

public class UserAddressActivity extends BaseActivity implements IUserAddressView {

    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    private UserAddressActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int user_id;
    private boolean loginstatus;
    private ArrayList<AddressListBean.DataBean> list;
    private UserAddressPresenter userAddressPresenter;
    private UserAddressListAdapter listAdapter;
    private boolean payCheckAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userAddressPresenter.getUserAddressList(user_id);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_user_address;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

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
        titlebarMidtitle.setText(getResources().getString(R.string.mine_userAddress));
        titlebarRighttitle.setText(getResources().getString(R.string.mine_addAddress));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        context = this;

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(false);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        super.initData();
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        loginstatus = sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //支付页选取地址
            payCheckAddress = bundle.getBoolean(Constant.PAY_EDIT, false);
        }

        list = new ArrayList<>();
        userAddressPresenter = new UserAddressPresenter(this);

        if (!loginstatus) {
            loadinglayout.showEmpty();
        }

        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAddressPresenter.getUserAddressList(user_id);
            }
        });
        refreshlayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        refreshlayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        refreshlayout.setEnableAutoLoadMore(false);//是否启用列表惯性滑动到底部时自动加载更多
        refreshlayout.setEnableNestedScroll(true);//是否启用嵌套滚动
        refreshlayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                userAddressPresenter.getUserAddressList(user_id);
            }
        });
    }


    @OnClick(R.id.titlebar_righttitle)
    public void onViewClicked() {
        //TODO 添加地址
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.TYPE, 1);
        startActivity(EditAddressActivity.class, bundle);
    }

    @Override
    public void getUserAddressList(AddressListBean databean) {
        if (databean != null) {
            if (databean.getData().size() > 0 && databean.getData() != null) {
                List<AddressListBean.DataBean> beanList = databean.getData();
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
        refreshlayout.finishLoadMore();
    }

    private void initAdapter() {
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        } else {
            listAdapter = new UserAddressListAdapter(list, context);
        }
        recyclerview.setAdapter(listAdapter);
        //跳转编辑页
        listAdapter.setItemClick(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.TYPE, 0);
                bundle.putInt(Constant.ADDRESS_ID, list.get(position).getAddress_id());
                startActivity(EditAddressActivity.class, bundle);
            }
        });

        listAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AddressListBean.DataBean bean = list.get(position);
                if (payCheckAddress) {
                    Intent intent = new Intent();
                    intent.putExtra(Constant.ADDRESS_NAME, bean.getConsignee());
                    intent.putExtra(Constant.ADDRESS_ID, bean.getAddress_id());
                    intent.putExtra(Constant.ADDRESS, bean.getAddress());
                    intent.putExtra(Constant.TELEPHONE, bean.getTelephone());
                    setResult(Constant.REQUEST_ADDRESS, intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void addAddressStatus(StateStatusBean data) {
        //TODO nothing
    }

    @Override
    public void getAddressDetail(AddressDetailBean detailBean) {
        //TODO nothing
    }

    @Override
    public void deleteAddressStatus(StateStatusBean data) {
        //TODO nothing
    }

    @Override
    public void showLoading() {
        //setProcessLoading(null, true);
    }

    @Override
    public void showLoadingFinish() {
        //dismissPorcess();
    }

    @Override
    public void showError() {
        loadinglayout.showError();
    }
}
