package com.ucfo.youcai.view.home;

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
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcai.R;
import com.ucfo.youcai.adapter.home.MessageNoticeAdapter;
import com.ucfo.youcai.base.BaseActivity;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.home.MessageCenterHomeBean;
import com.ucfo.youcai.entity.home.MessageCenterNoticeBean;
import com.ucfo.youcai.presenter.presenterImpl.home.MessageCenterPresenter;
import com.ucfo.youcai.presenter.view.home.IMessageCenterView;
import com.ucfo.youcai.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcai.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcai.utils.toastutils.ToastUtil;
import com.ucfo.youcai.view.main.activity.WebActivity;
import com.ucfo.youcai.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-8-8 下午 2:29
 * Package: com.ucfo.youcai.view.home
 * FileName: AnnouncementCenterActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 网校公告
 */

public class AnnouncementCenterActivity extends BaseActivity implements IMessageCenterView {
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
    private List<MessageCenterNoticeBean.DataBean> list;
    private AnnouncementCenterActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int userId;
    private MessageCenterPresenter messageCenterPresenter;
    private MessageNoticeAdapter messageNoticeAdapter;
    private int pageIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        showline.setVisibility(View.GONE);
        titlebarMidtitle.setText(getResources().getString(R.string.message_notice));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_announcement_center;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        context = this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        userId = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);

        list = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(false);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setNestedScrollingEnabled(false);

        messageCenterPresenter = new MessageCenterPresenter(this);
        messageCenterPresenter.getNoticeList(userId, pageIndex, 1);

        refreshlayout.setDisableContentWhenRefresh(true);
        refreshlayout.setDisableContentWhenLoading(true);
        refreshlayout.setEnableAutoLoadMore(false);
        refreshlayout.setEnableNestedScroll(true);
        refreshlayout.setEnableOverScrollBounce(true);
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                messageCenterPresenter.getNoticeList(userId, pageIndex, 1);
            }
        });
        refreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex += 1;
                messageCenterPresenter.getNoticeList(userId, pageIndex, 1);
            }
        });
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageCenterPresenter.getNoticeList(userId, pageIndex, 1);
            }
        });
    }

    @Override
    public void getMessageCenterHome(MessageCenterHomeBean data) {
        //TODO nothing
    }

    @Override
    public void getMessageCenterNoticeList(MessageCenterNoticeBean dataBean) {
        if (dataBean != null) {//TODO 分页加载或者初次加载都成功情况下
            if (dataBean.getData() != null && dataBean.getData().size() > 0) {
                List<MessageCenterNoticeBean.DataBean> data = dataBean.getData();
                if (pageIndex == 1) {//初次加载或刷新操作
                    list.clear();
                    list.addAll(data);
                } else {//加载更多
                    if (data.size() > 0) {
                        list.addAll(data);
                    }
                }
                initAdapter();
                loadinglayout.showContent();
            } else {//TODO 加载的数据为0的情况
                if (pageIndex == 1) {//初次加载或刷新
                    if (list != null && list.size() > 0) {
                        messageNoticeAdapter.notifyDataSetChanged();
                    } else {
                        loadinglayout.showEmpty();
                    }
                } else {//架子啊更多
                    if (list != null && list.size() > 0) {
                        messageNoticeAdapter.notifyDataSetChanged();
                        ToastUtil.showBottomShortText(context, getResources().getString(R.string.holder_noMoreData));
                    } else {
                        loadinglayout.showEmpty();
                    }
                }
            }
        } else {
            loadinglayout.showEmpty();
        }

        refreshlayout.finishRefresh();
        refreshlayout.finishLoadMore();

    }

    private void initAdapter() {
        if (messageNoticeAdapter == null) {
            messageNoticeAdapter = new MessageNoticeAdapter(list, context);
        } else {
            messageNoticeAdapter.notifyDataSetChanged();
        }
        recyclerview.setAdapter(messageNoticeAdapter);

        messageNoticeAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.WEB_TITLE, list.get(position).getTitle());
                bundle.putString(Constant.WEB_URL, "https://www.iconfont.cn/");
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
        loadinglayout.showEmpty();
    }
}
