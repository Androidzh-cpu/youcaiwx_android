package com.ucfo.youcaiwx.module.user.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.androidkun.xtablayout.XTabLayout;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.module.course.player.adapter.CommonTabAdapter;
import com.ucfo.youcaiwx.module.user.fragment.record.CPEWatchTheRecordFragment;
import com.ucfo.youcaiwx.module.user.fragment.record.WatchTheRecordFragment;

import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-6-27 上午 10:40
 * FileName: WatchTheRecordActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 观看记录
 */

public class WatchTheRecordActivity extends BaseActivity {
    private Toolbar titlebarToolbar;
    private XTabLayout tabLayout;
    private ViewPager viewPager;

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
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        titlebarToolbar = (Toolbar) findViewById(R.id.titlebar_toolbar);
        tabLayout = (XTabLayout) findViewById(R.id.xTablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    @Override
    protected void initData() {
        super.initData();
        ArrayList<String> titlesList = new ArrayList<String>();
        ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>();

        WatchTheRecordFragment watchTheRecordFragment = new WatchTheRecordFragment();
        CPEWatchTheRecordFragment cpeWatchTheRecordFragment = new CPEWatchTheRecordFragment();
        fragmentArrayList.add(watchTheRecordFragment);
        fragmentArrayList.add(cpeWatchTheRecordFragment);

        titlesList.add(getResources().getString(R.string.mine_record));
        titlesList.add(getResources().getString(R.string.mine_CPErecord));

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        CommonTabAdapter commonTabAdapter = new CommonTabAdapter(supportFragmentManager, fragmentArrayList, titlesList);
        viewPager.setAdapter(commonTabAdapter);
        viewPager.setOffscreenPageLimit(fragmentArrayList.size());
        tabLayout.setupWithViewPager(viewPager);
    }
}
