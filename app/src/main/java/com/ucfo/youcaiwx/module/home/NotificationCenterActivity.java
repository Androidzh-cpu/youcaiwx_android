package com.ucfo.youcaiwx.module.home;

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
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.home.MessageNotificationAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.home.MessageCenterHomeBean;
import com.ucfo.youcaiwx.entity.home.MessageCenterNoticeBean;
import com.ucfo.youcaiwx.module.answer.AnsweringCourseActivity;
import com.ucfo.youcaiwx.module.answer.AnsweringQuestionActivity;
import com.ucfo.youcaiwx.module.main.activity.WebActivity;
import com.ucfo.youcaiwx.module.user.activity.MineOrderFormDetailActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.home.MessageCenterPresenter;
import com.ucfo.youcaiwx.presenter.view.home.IMessageCenterView;
import com.ucfo.youcaiwx.utils.baseadapter.OnItemClickListener;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-8-8 下午 2:39
 * FileName: NotificationCenterActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 系统通知
 */

public class NotificationCenterActivity extends BaseActivity implements IMessageCenterView {

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
    private int pageIndex = 1;
    private List<MessageCenterNoticeBean.DataBean> list;
    private NotificationCenterActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int userId;
    private MessageCenterPresenter messageCenterPresenter;
    private MessageNotificationAdapter messageNotificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        messageCenterPresenter.getNoticeList(userId, pageIndex, 2);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_notification_center;
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
        titlebarMidtitle.setText(getResources().getString(R.string.message_notification));
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
        list = new ArrayList<>();
        context = this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        userId = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        messageCenterPresenter = new MessageCenterPresenter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(false);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setNestedScrollingEnabled(false);

        messageCenterPresenter = new MessageCenterPresenter(this);
        //messageCenterPresenter.getNoticeList(userId, pageIndex, 2);

        refreshlayout.setDisableContentWhenRefresh(true);
        refreshlayout.setDisableContentWhenLoading(true);
        refreshlayout.setEnableAutoLoadMore(false);
        refreshlayout.setEnableNestedScroll(true);
        refreshlayout.setEnableOverScrollBounce(true);
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                messageCenterPresenter.getNoticeList(userId, pageIndex, 2);
            }
        });
        refreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex += 1;
                messageCenterPresenter.getNoticeList(userId, pageIndex, 2);
            }
        });
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageCenterPresenter.getNoticeList(userId, pageIndex, 2);
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

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
                if (loadinglayout != null) {
                    loadinglayout.showContent();
                }
            } else {//TODO 加载的数据为0的情况
                if (pageIndex == 1) {//初次加载或刷新
                    if (list != null && list.size() > 0) {
                        if (messageNotificationAdapter != null) {
                            messageNotificationAdapter.notifyDataSetChanged();
                        }
                    } else {
                        if (loadinglayout != null) {
                            loadinglayout.showEmpty();
                        }
                    }
                } else {//架子啊更多
                    if (list != null && list.size() > 0) {
                        if (messageNotificationAdapter != null) {
                            messageNotificationAdapter.notifyDataSetChanged();
                        }
                        ToastUtil.showBottomShortText(context, getResources().getString(R.string.noMoreData));
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

    private void initAdapter() {
        if (messageNotificationAdapter == null) {
            messageNotificationAdapter = new MessageNotificationAdapter(list, context);
        } else {
            messageNotificationAdapter.notifyDataSetChanged();
        }
        recyclerview.setAdapter(messageNotificationAdapter);
        messageNotificationAdapter.setItemClick(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int type = list.get(position).getType();

                messageCenterPresenter.havedReadMessage(userId, type, list.get(position).getMessage_id());

                MessageCenterNoticeBean.DataBean bean = list.get(position);
                if (type == 4 || type == 5) {
                    //TODO H5消息
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.WEB_TITLE, bean.getTitle());
                    bundle.putString(Constant.WEB_URL, bean.getHref());
                    startActivity(WebActivity.class, bundle);
                } else if (type == 3) {
                    //TODO 订单支付
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.ORDER_NUM, bean.getOrder_num());
                    bundle.putInt(Constant.STATUS, 1);
                    startActivity(MineOrderFormDetailActivity.class, bundle);
                } else if (type == 1) {
                    //TODO 课程答疑
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.TYPE, Constant.MINE_ANSWER);
                    bundle.putInt(Constant.ANSWER_ID, bean.getContent_id());
                    bundle.putInt(Constant.STATUS, 1);
                    bundle.putString(Constant.TYPE, Constant.MESSAGE_ANSWER);
                    //startActivity(CourseAnswerDetailActivity.class, bundle);
                    startActivity(AnsweringCourseActivity.class, bundle);
                } else if (type == 2) {
                    //TODO 题库答疑
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.ANSWER_ID, list.get(position).getContent_id());
                    bundle.putInt(Constant.STATUS, 1);
                    bundle.putString(Constant.TYPE, Constant.MESSAGE_ANSWER);
                    //startActivity(QuestionAnswerDetailActivity.class, bundle);
                    startActivity(AnsweringQuestionActivity.class, bundle);
                }
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
