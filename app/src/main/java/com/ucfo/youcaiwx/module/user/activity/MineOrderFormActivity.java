package com.ucfo.youcaiwx.module.user.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
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
import com.ucfo.youcaiwx.adapter.user.MineOrderFormListAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.address.StateStatusBean;
import com.ucfo.youcaiwx.entity.user.MineOrderFormDetailBean;
import com.ucfo.youcaiwx.entity.user.MineOrderListBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.MineOrderFormPresenter;
import com.ucfo.youcaiwx.presenter.view.user.IMineOrderFromView;
import com.ucfo.youcaiwx.utils.CallUtils;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author: AND
 * Time: 2019-6-21 上午 9:12
 * FileName: MineOrderFormActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 我的订单
 */
public class MineOrderFormActivity extends BaseActivity implements IMineOrderFromView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private MineOrderFormActivity activity;
    private int user_id;
    private MineOrderFormPresenter mineOrderFormPresenter;
    private ArrayList<MineOrderListBean.DataBean> list;
    private int pageIndex = 1;
    private MineOrderFormListAdapter mineOrderFormListAdapter;
    private String tiptext;

    @Override
    protected void onResume() {
        super.onResume();
        if (mineOrderFormPresenter == null) {
            mineOrderFormPresenter = new MineOrderFormPresenter(this);
        }
        mineOrderFormPresenter.getMineOrderFromList(user_id);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_mine_order_form;
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
        titlebarMidtitle.setText(getResources().getString(R.string.mine_order));
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
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        loadinglayout = (LoadingLayout) findViewById(R.id.loadinglayout);
        refreshlayout = (SmartRefreshLayout) findViewById(R.id.refreshlayout);
    }

    @Override
    protected void initData() {
        super.initData();
        activity = this;
        user_id = SharedPreferencesUtils.getInstance(activity).getInt(Constant.USER_ID, 0);
        list = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setNestedScrollingEnabled(false);


        mineOrderFormPresenter = new MineOrderFormPresenter(this);
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mineOrderFormPresenter.getMineOrderFromList(user_id);
            }
        });
        refreshlayout.setDisableContentWhenRefresh(true);//刷新的时候禁止列表的操作
        refreshlayout.setDisableContentWhenLoading(true);//加载的时候禁止列表的操作
        refreshlayout.setEnableLoadMore(false);
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                mineOrderFormPresenter.getMineOrderFromList(user_id);
            }
        });
    }

    @Override
    public void getMineOrderFromList(MineOrderListBean data) {
        if (data != null) {
            if (data.getData() != null && data.getData().size() > 0) {
                List<MineOrderListBean.DataBean> dataBeanList = data.getData();
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
        if (mineOrderFormListAdapter == null) {
            mineOrderFormListAdapter = new MineOrderFormListAdapter(MineOrderFormActivity.this, list);
            recyclerview.setAdapter(mineOrderFormListAdapter);
        } else {
            mineOrderFormListAdapter.notifyChange(list);
        }
        //TODO 支付,取消,联系客服操作
        mineOrderFormListAdapter.setOthersClick(new MineOrderFormListAdapter.IOrderCallback() {
            @Override
            public void clickPay(int position) {
                //TODO 去支付
                String payStatus = list.get(position).getPay_status();
                boolean equals = TextUtils.equals(payStatus, String.valueOf("3"));
                if (!equals) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.ORDER_NUM, list.get(position).getOrder_num());
                    bundle.putInt(Constant.STATUS, Integer.parseInt(payStatus));
                    startActivity(MineOrderFormDetailActivity.class, bundle);
                }
            }

            @Override
            public void clickCancel(int position) {
                String order_num = list.get(position).getOrder_num();
                tiptext = getResources().getString(R.string.net_canceltext);
                mineOrderFormPresenter.cancelOrderForm(user_id, order_num);
            }

            @Override
            public void clickConnect() {
                CallUtils.makeCallWithPermission(activity);
            }
        });
        mineOrderFormListAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String payStatus = list.get(position).getPay_status();
                boolean equals = TextUtils.equals(payStatus, String.valueOf("3"));
                if (!equals) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.ORDER_NUM, list.get(position).getOrder_num());
                    bundle.putInt(Constant.STATUS, Integer.parseInt(payStatus));
                    startActivity(MineOrderFormDetailActivity.class, bundle);
                }
            }
        });
    }

    @Override
    public void cancelOrderForm(StateStatusBean data) {
        if (data != null) {
            if (data.getData() != null) {
                int state = data.getData().getState();
                if (state == 1) {
                    mineOrderFormPresenter.getMineOrderFromList(user_id);

                    ToastUtil.showBottomShortText(activity, getResources().getString(R.string.operation_Success));
                } else {
                    ToastUtil.showBottomShortText(activity, getResources().getString(R.string.operation_Error));
                }
            } else {
                ToastUtil.showBottomShortText(activity, getResources().getString(R.string.operation_Error));
            }
        } else {
            ToastUtil.showBottomShortText(activity, getResources().getString(R.string.operation_Error));
        }
    }

    @Override
    public void getOrderFormDetail(MineOrderFormDetailBean data) {
        //TODO nothing
    }

    @Override
    public void showLoading() {
        setProcessLoading(tiptext, true);
    }

    @Override
    public void showLoadingFinish() {
        dismissPorcess();
    }

    @Override
    public void showError() {
        if (loadinglayout != null) {
            loadinglayout.showError();
        }
    }
}
