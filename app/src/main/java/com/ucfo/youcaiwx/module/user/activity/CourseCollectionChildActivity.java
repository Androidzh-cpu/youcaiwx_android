package com.ucfo.youcaiwx.module.user.activity;

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
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.user.MineCourseCollectionChildAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.user.MineCourseBean;
import com.ucfo.youcaiwx.entity.user.MineCourseChildListBean;
import com.ucfo.youcaiwx.entity.user.MineCourseCollectionDirBean;
import com.ucfo.youcaiwx.entity.user.MineQuestionCollectionListBean;
import com.ucfo.youcaiwx.entity.user.ProjectListBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.MineCollectionPresenter;
import com.ucfo.youcaiwx.presenter.view.user.IMineCollectionView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-6-24 下午 3:21
 * FileName: CourseCollectionChildActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 课程收藏-课程列表
 */

public class CourseCollectionChildActivity extends BaseActivity implements IMineCollectionView {

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
    private CourseCollectionChildActivity context;
    private int user_id;
    private MineCollectionPresenter mineCollectionPresenter;
    private Bundle bundle;
    private int packege_id;
    private ArrayList<MineCourseChildListBean.DataBean> list;
    private MineCourseCollectionChildAdapter mineCourseCollectionChildAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        if (bundle != null) {
            mineCollectionPresenter.getMineCourseCollectionList(user_id, packege_id);
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_course_collection_child;
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
        titlebarRighttitle.setVisibility(View.GONE);
        titlebarMidtitle.setText(getResources().getString(R.string.mine_collection_course));
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

    }

    @Override
    protected void initData() {
        super.initData();
        context = this;
        user_id = SharedPreferencesUtils.getInstance(context).getInt(Constant.USER_ID, 0);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            packege_id = bundle.getInt(Constant.PACKAGE_ID, 0);
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(false);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setItemAnimator(new DefaultItemAnimator());

        list = new ArrayList<>();
        mineCollectionPresenter = new MineCollectionPresenter(this);

        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mineCollectionPresenter.getMineCourseCollectionList(user_id, packege_id);
            }
        });
        refreshlayout.setDisableContentWhenRefresh(true);
        refreshlayout.setDisableContentWhenLoading(true);
        refreshlayout.setEnableAutoLoadMore(false);
        refreshlayout.setEnableNestedScroll(true);
        refreshlayout.setEnableOverScrollBounce(true);
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mineCollectionPresenter.getMineCourseCollectionList(user_id, packege_id);
            }
        });
    }

    @Override
    public void getPorjectList(ProjectListBean data) {
        //TODO nothing
    }

    @Override
    public void getMineCoursePackageCollectionList(MineCourseBean data) {
        //TODO nothing
    }

    @Override
    public void getMineQuestioinCollectionList(MineQuestionCollectionListBean data) {
        //TODO nothing
    }

    @Override
    public void getMineCourseCollectionChildList(MineCourseChildListBean data) {
        if (data != null) {
            if (data.getData() != null) {
                List<MineCourseChildListBean.DataBean> beanList = data.getData();
                list.clear();
                list.addAll(beanList);

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

    @Override
    public void getCollectionDirList(MineCourseCollectionDirBean data) {
        //TODO nothing
    }

    private void initAdapter() {
        if (mineCourseCollectionChildAdapter == null) {
            mineCourseCollectionChildAdapter = new MineCourseCollectionChildAdapter(this, list);
            recyclerview.setAdapter(mineCourseCollectionChildAdapter);
        } else {
            mineCourseCollectionChildAdapter.notifyChange(list);
        }
        mineCourseCollectionChildAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!fastClick(1000)) {
                    if (list.get(position).getCourse_id() != 0) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constant.PACKAGE_ID, packege_id);
                        bundle.putInt(Constant.COURSE_ID, list.get(position).getCourse_id());
                        bundle.putString(Constant.TITLE, list.get(position).getName());
                        bundle.putString(Constant.TEACHER_NAME, list.get(position).getTeacher_name());
                        bundle.putInt(Constant.COURSE_UN_CON, Integer.parseInt(list.get(position).getIs_zhengke()));
                        startActivity(CourseCollectionDirActivity.class, bundle);
                    }
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
