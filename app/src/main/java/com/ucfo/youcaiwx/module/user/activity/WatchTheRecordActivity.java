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
import com.ucfo.youcaiwx.adapter.user.MineWatchRecordAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.user.MineCourseBean;
import com.ucfo.youcaiwx.entity.user.MineWatchRecordBean;
import com.ucfo.youcaiwx.module.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.MineCoursePresenter;
import com.ucfo.youcaiwx.presenter.view.user.IMineCourseView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-6-27 上午 10:40
 * FileName: WatchTheRecordActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 观看记录
 */

public class WatchTheRecordActivity extends BaseActivity implements IMineCourseView {
    private TextView titlebarMidtitle;
    private TextView titlebarRighttitle;
    private Toolbar titlebarToolbar;
    private RecyclerView recyclerview;
    private LoadingLayout loadinglayout;
    private SmartRefreshLayout refreshlayout;

    private List<MineWatchRecordBean.DataBean> list;
    private int user_id;
    private MineCoursePresenter mineCoursePresenter;
    private MineWatchRecordAdapter mineWatchRecordAdapter;

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
        titlebarMidtitle = (TextView) findViewById(R.id.titlebar_midtitle);
        titlebarRighttitle = (TextView) findViewById(R.id.titlebar_righttitle);
        titlebarToolbar = (Toolbar) findViewById(R.id.titlebar_toolbar);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        loadinglayout = (LoadingLayout) findViewById(R.id.loadinglayout);
        refreshlayout = (SmartRefreshLayout) findViewById(R.id.refreshlayout);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        super.initData();
        user_id = SharedPreferencesUtils.getInstance(this).getInt(Constant.USER_ID, 0);

        list = new ArrayList<>();

        mineCoursePresenter = new MineCoursePresenter(this);

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
        refreshlayout.finishLoadMore();
    }

    private void initadapter() {
        if (mineWatchRecordAdapter == null) {
            mineWatchRecordAdapter = new MineWatchRecordAdapter(list, WatchTheRecordActivity.this);
            recyclerview.setAdapter(mineWatchRecordAdapter);
        } else {
            mineWatchRecordAdapter.notifyChange(list);
        }
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
        if (loadinglayout != null) {
            loadinglayout.showError();
        }
    }
}
