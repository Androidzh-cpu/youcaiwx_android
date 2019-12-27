package com.ucfo.youcaiwx.module.user.activity;

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
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.module.course.player.adapter.CommonTabAdapter;
import com.ucfo.youcaiwx.module.user.fragment.course.EducationFragment;
import com.ucfo.youcaiwx.module.user.fragment.course.MineCourseFragment;

import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-6-18 上午 11:21
 * FileName: MineCourseActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 我的课程
 */
public class MineCourseActivity extends BaseActivity {
    private XTabLayout mXTablayout;
    private TextView mRighttitleTitlebar;
    private Toolbar mToolbarTitlebar;
    private ViewPager mViewpager;

    @Override
    protected int setContentView() {
        return R.layout.activity_mine_course;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mToolbarTitlebar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbarTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
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
    protected void initView(Bundle savedInstanceState) {
        mXTablayout = (XTabLayout) findViewById(R.id.xTablayout);
        mRighttitleTitlebar = (TextView) findViewById(R.id.titlebar_righttitle);
        mToolbarTitlebar = (Toolbar) findViewById(R.id.titlebar_toolbar);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
    }

    @Override
    protected void initData() {
        super.initData();
        ArrayList<String> titlesList = new ArrayList<String>();
        ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>();

        MineCourseFragment mineCourseFragment = new MineCourseFragment();
        EducationFragment educationFragment = new EducationFragment();
        fragmentArrayList.add(mineCourseFragment);
        fragmentArrayList.add(educationFragment);

        titlesList.add(getResources().getString(R.string.mine_Course));
        titlesList.add(getResources().getString(R.string.mine_EducationCourse));

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        CommonTabAdapter commonTabAdapter = new CommonTabAdapter(supportFragmentManager, fragmentArrayList, titlesList);
        mViewpager.setAdapter(commonTabAdapter);
        mViewpager.setOffscreenPageLimit(fragmentArrayList.size());
        mXTablayout.setupWithViewPager(mViewpager);
    }
}
