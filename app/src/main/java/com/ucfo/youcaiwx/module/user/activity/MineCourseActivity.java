package com.ucfo.youcaiwx.module.user.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.user.MineCourseAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.user.MineCourseBean;
import com.ucfo.youcaiwx.entity.user.MineWatchRecordBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.MineCoursePresenter;
import com.ucfo.youcaiwx.presenter.view.user.IMineCourseView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.module.course.CourseListActivity;
import com.ucfo.youcaiwx.module.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.widget.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-6-18 上午 11:21
 * FileName: MineCourseActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 我的课程
 */
public class MineCourseActivity extends BaseActivity implements IMineCourseView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.btn_lookCourse)
    Button btnLookCourse;
    @BindView(R.id.linear_holder)
    LinearLayout linearHolder;
    @BindView(R.id.recyclerview)
    ShimmerRecyclerView recyclerview;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    private MineCourseActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int user_id;
    private MineCoursePresenter mineCoursePresenter;
    private ArrayList<MineCourseBean.DataBean> list;
    private MineCourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_mine_course;
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
        titlebarMidtitle.setText(getResources().getString(R.string.mine_Course));
        titlebarRighttitle.setVisibility(View.GONE);
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
    protected void initData() {
        super.initData();
        context = this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(this);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setNestedScrollingEnabled(false);

        list = new ArrayList<>();

        mineCoursePresenter = new MineCoursePresenter(this);
        mineCoursePresenter.getMineCourseList(user_id);
        refreshlayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        refreshlayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        refreshlayout.setEnableAutoLoadMore(false);//是否启用列表惯性滑动到底部时自动加载更多
        refreshlayout.setEnableNestedScroll(true);//是否启用嵌套滚动
        refreshlayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        refreshlayout.setEnableLoadMore(false);
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mineCoursePresenter.getMineCourseList(user_id);
            }
        });

    }

    @OnClick(R.id.btn_lookCourse)
    public void onViewClicked() {
        startActivity(CourseListActivity.class, null);
    }

    @Override
    public void getMineCourse(MineCourseBean data) {
        if (data != null) {
            if (data.getData() != null && data.getData().size() > 0) {
                if (refreshlayout != null) {
                    refreshlayout.setVisibility(View.VISIBLE);
                }
                if (linearHolder != null) {
                    linearHolder.setVisibility(View.GONE);
                }

                List<MineCourseBean.DataBean> beanList = data.getData();
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.clear();
                list.addAll(beanList);

                initAdapter();
            } else {
                if (refreshlayout != null) {
                    refreshlayout.setVisibility(View.GONE);
                }
                if (linearHolder != null) {
                    linearHolder.setVisibility(View.VISIBLE);
                }
            }
        } else {
            if (refreshlayout != null) {
                refreshlayout.setVisibility(View.GONE);
            }
            if (linearHolder != null) {
                linearHolder.setVisibility(View.INVISIBLE);
            }
        }
        refreshlayout.finishRefresh();
        refreshlayout.finishLoadMore();
    }

    @Override
    public void getMineWatchRecord(MineWatchRecordBean data) {
        //TODO nothing
    }

    private void initAdapter() {
        if (courseAdapter == null) {
            courseAdapter = new MineCourseAdapter(this, list);
        }
        courseAdapter.notifyDataSetChanged();
        recyclerview.setAdapter(courseAdapter);
        courseAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                setProcessLoading(null, true);
                Bundle bundle = new Bundle();
                MineCourseBean.DataBean bean = list.get(position);
                String courseIamge = bean.getApp_img();//TODO 课程封面
                int coursePackageId = bean.getPackage_id();//TODO  课程包ID(课程包内含多们课程)
                bundle.putString(Constant.COURSE_COVER_IMAGE, courseIamge);//封面
                bundle.putInt(Constant.COURSE_PACKAGE_ID, coursePackageId);//课程包ID
                bundle.putInt(Constant.COURSE_BUY_STATE, 1);//课程是否购买
                startActivity(VideoPlayPageActivity.class, bundle);
                dismissPorcess();
            }
        });
    }

    @Override
    public void showLoading() {
        setProcessLoading(null, true);
    }

    @Override
    public void showLoadingFinish() {
        dismissPorcess();
    }

    @Override
    public void showError() {
        if (refreshlayout != null) {
            refreshlayout.setVisibility(View.GONE);
        }
        if (linearHolder != null) {
            linearHolder.setVisibility(View.INVISIBLE);
        }
    }
}
