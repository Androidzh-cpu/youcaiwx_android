package com.ucfo.youcai.view.course;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.ucfo.youcai.R;
import com.ucfo.youcai.adapter.course.CourseListPagerAdapter;
import com.ucfo.youcai.base.BaseActivity;
import com.ucfo.youcai.entity.course.CourseDataListBean;
import com.ucfo.youcai.entity.course.CourseSubjectsBean;
import com.ucfo.youcai.presenter.presenterImpl.course.CourseListPresenter;
import com.ucfo.youcai.presenter.view.course.ICourseListView;
import com.ucfo.youcai.utils.systemutils.StatusbarUI;
import com.ucfo.youcai.view.course.fragment.CourseChildListFragment;
import com.ucfo.youcai.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseListActivity extends BaseActivity implements ICourseListView {

    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.tablayout)
    XTabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    private ArrayList<String> titlesList;
    private ArrayList<Fragment> fragmentArrayList;
    private int currentPosition;
    private CourseListPresenter courseListPresenter;
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_course_list;
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
        titlebarRighttitle.setVisibility(View.GONE);
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
        //注册网络
        courseListPresenter = new CourseListPresenter(this);
    }

    @Override
    protected void initData() {
        super.initData();
        titlesList = new ArrayList<>();
        fragmentArrayList = new ArrayList<>();
        //获取课程分类列表
        courseListPresenter.getCourseSubjects();
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseListPresenter.getCourseSubjects();
            }
        });
    }


    /**
     * 初始化标题数据
     */
    private void initTablayout(List<CourseSubjectsBean.DataBean> data) {
        for (int i = 0; i < data.size(); i++) {
            titlesList.add(data.get(i).getClass_name().trim());//创建标题
            CourseChildListFragment courseChildListFragment = new CourseChildListFragment();//创建碎片
            courseChildListFragment.setId(data.get(i).getId());//为创建的额fragment添加课程ID
            courseChildListFragment.setPosition(i);//为创建的额fragment添加索引值
            fragmentArrayList.add(courseChildListFragment);
        }
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        //创建适配器
        CourseListPagerAdapter courseListPagerAdapter = new CourseListPagerAdapter(supportFragmentManager, fragmentArrayList, titlesList);
        viewpager.setAdapter(courseListPagerAdapter);//viewpager设置适配器
        viewpager.setOffscreenPageLimit(titlesList.size());//设置预加载页面数量
        viewpager.setCurrentItem(0);//设置当前viewpager
        //tablayout.setViewPager(viewpager);//tablayout  viewpager联动
        tablayout.setupWithViewPager(viewpager);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;//获取当前索引值
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //viewpager的滑动状态：当我们手指按下时 state=1，当我们手指抬起时 state=2，
                // 当viewpager处于空闲状态时 state=0；所以我们完全可以在state=0时 去加载或者处理我们的事情，因为这时候滑动已经结束。
                if (state == 0) {
                }
            }
        });

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    //课程筛选列表
    @Override
    public void getCourseSubject(CourseSubjectsBean result) {
        if (result != null) {
            List<CourseSubjectsBean.DataBean> data = result.getData();
            initTablayout(data);
            loadinglayout.showContent();
        } else {
            loadinglayout.showEmpty();
        }
    }

    @Override
    public void getCourseDataList(CourseDataListBean result) {
        //TODO nothing
    }

    @Override
    public void showLoading() {
        setProcessLoading(getResources().getString(R.string.net_loadingtext), true);
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
