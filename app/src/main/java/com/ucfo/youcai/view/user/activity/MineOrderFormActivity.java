package com.ucfo.youcai.view.user.activity;

import android.graphics.Color;
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
import com.ucfo.youcai.R;
import com.ucfo.youcai.adapter.user.MineOrderFormListAdapter;
import com.ucfo.youcai.base.BaseActivity;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.address.StateStatusBean;
import com.ucfo.youcai.entity.user.MineOrderFormDetailBean;
import com.ucfo.youcai.entity.user.MineOrderListBean;
import com.ucfo.youcai.presenter.presenterImpl.user.MineOrderFormPresenter;
import com.ucfo.youcai.presenter.view.user.IMineOrderFromView;
import com.ucfo.youcai.utils.CallUtils;
import com.ucfo.youcai.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcai.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcai.utils.systemutils.StatusbarUI;
import com.ucfo.youcai.utils.toastutils.ToastUtil;
import com.ucfo.youcai.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-6-21 上午 9:12
 * Package: com.ucfo.youcai.view.user.activity
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
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    private MineOrderFormActivity context;
    private int user_id;
    private MineOrderFormPresenter mineOrderFormPresenter;
    private ArrayList<MineOrderListBean.DataBean> list;
    private int pageIndex = 1;
    private MineOrderFormListAdapter mineOrderFormListAdapter;
    private String tiptext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        StatusbarUI.setStatusBarUIMode(this, Color.TRANSPARENT, true);
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
    protected void initData() {
        super.initData();
        context = this;
        user_id = SharedPreferencesUtils.getInstance(context).getInt(Constant.USER_ID, 0);
        list = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
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
        if (mineOrderFormListAdapter != null) {
            mineOrderFormListAdapter.notifyDataSetChanged();
        } else {
            mineOrderFormListAdapter = new MineOrderFormListAdapter(context, list);
        }
        recyclerview.setAdapter(mineOrderFormListAdapter);

        //支付,取消,联系客服操作
        mineOrderFormListAdapter.setOthersClick(new MineOrderFormListAdapter.IOrderCallback() {
            @Override
            public void clickPay(int position) {
                //TODO 去支付
            }

            @Override
            public void clickCancel(int position) {
                String order_num = list.get(position).getOrder_num();
                tiptext = getResources().getString(R.string.net_canceltext);
                mineOrderFormPresenter.cancelOrderForm(user_id, order_num);
            }

            @Override
            public void clickConnect() {
                CallUtils.makeCallWithPermission(context);
            }
        });
        mineOrderFormListAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int payStatus = list.get(position).getPay_status();
                if (payStatus != 3) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.ORDER_NUM, list.get(position).getOrder_num());
                    bundle.putInt(Constant.STATUS, payStatus);
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

                    ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Success));
                } else {
                    ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
                }
            } else {
                ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
            }
        } else {
            ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
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
        loadinglayout.showError();
    }
}
