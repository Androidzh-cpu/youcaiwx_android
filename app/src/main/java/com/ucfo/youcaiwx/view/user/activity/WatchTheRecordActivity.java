package com.ucfo.youcaiwx.view.user.activity;

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
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.user.MineWatchRecordAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.user.MineCourseBean;
import com.ucfo.youcaiwx.entity.user.MineWatchRecordBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.MineCoursePresenter;
import com.ucfo.youcaiwx.presenter.view.user.IMineCourseView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.StatusbarUI;
import com.ucfo.youcaiwx.view.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-6-27 上午 10:40
 * FileName: WatchTheRecordActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 观看记录
 */

public class WatchTheRecordActivity extends BaseActivity implements IMineCourseView {
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
    private List<MineWatchRecordBean.DataBean> list;
    private WatchTheRecordActivity context;
    private int user_id;
    private MineCoursePresenter mineCoursePresenter;
    private MineWatchRecordAdapter mineWatchRecordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mineCoursePresenter.getMineWatcheRecordList(user_id);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_watch_the_record;
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
        titlebarMidtitle.setText(getResources().getString(R.string.mine_record));
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
        user_id = SharedPreferencesUtils.getInstance(context).getInt(Constant.USER_ID, 0);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<>();

        mineCoursePresenter = new MineCoursePresenter(this);

        refreshlayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        refreshlayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        refreshlayout.setEnableAutoLoadMore(false);//是否启用列表惯性滑动到底部时自动加载更多
        refreshlayout.setEnableNestedScroll(true);//是否启用嵌套滚动
        refreshlayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        refreshlayout.setEnableLoadMore(false);
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mineCoursePresenter.getMineWatcheRecordList(user_id);
            }
        });
    }

    @Override
    public void getMineCourse(MineCourseBean data) {
        //TODO nothing
    }

    @Override
    public void getMineWatchRecord(MineWatchRecordBean data) {
        if (data != null) {
            if (data.getData() != null && data.getData().size() > 0) {
                List<MineWatchRecordBean.DataBean> beanList = data.getData();
                list.clear();
                list.addAll(beanList);
                initadapter();

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

    private void initadapter() {
        if (mineWatchRecordAdapter == null) {
            mineWatchRecordAdapter = new MineWatchRecordAdapter(list, this);
        } else {
            mineWatchRecordAdapter.notifyDataSetChanged();
        }
        recyclerview.setAdapter(mineWatchRecordAdapter);
        mineWatchRecordAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MineWatchRecordBean.DataBean bean = list.get(position);
                if (!fastClick(1000)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.COURSE_PACKAGE_ID, bean.getPackage_id());//包
                    bundle.putInt(Constant.COURSE_BUY_STATE, bean.getIs_purchase());//已购买状态
                    bundle.putString(Constant.COURSE_SOURCE, Constant.WATCH_RECORD);//播放源
                    bundle.putInt(Constant.COURSE_UN_CON, bean.getIs_zhengke());
                    bundle.putInt(Constant.SECTION_ID, bean.getSection_id());//章
                    bundle.putString(Constant.COURSE_VIDEOID, bean.getVideoId());//阿里VID
                    bundle.putInt(Constant.VIDEO_ID, bean.getVideo_id());//小节ID
                    bundle.putInt(Constant.COURSE_ID, bean.getCourse_id());//课ID
                    startActivity(VideoPlayPageActivity.class, bundle);
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
        loadinglayout.showError();
    }
}
