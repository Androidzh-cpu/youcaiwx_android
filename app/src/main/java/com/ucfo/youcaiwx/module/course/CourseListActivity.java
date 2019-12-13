package com.ucfo.youcaiwx.module.course;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.course.CourseListPagerAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.entity.course.CourseDataListBean;
import com.ucfo.youcaiwx.entity.course.CourseSubjectsBean;
import com.ucfo.youcaiwx.module.course.fragment.CourseChildListFragment;
import com.ucfo.youcaiwx.presenter.presenterImpl.course.CourseListPresenter;
import com.ucfo.youcaiwx.presenter.view.course.ICourseListView;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-11-26 上午 9:50
 * Package: com.ucfo.youcaiwx.module.course
 * FileName: CourseListActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 所有售卖中的课程
 */
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
    
    @Override
    protected int setContentView() {
        return R.layout.activity_course_list;
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
        titlebarMidtitle.setText(getResources().getString(R.string.tab_class));
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
        ButterKnife.bind(this);

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
            if (loadinglayout != null) {
                loadinglayout.showContent();
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
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
        if (loadinglayout != null) {
            loadinglayout.showError();
        }
    }
}
