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
import com.ucfo.youcaiwx.module.user.fragment.FragmentCourseCollection;
import com.ucfo.youcaiwx.module.user.fragment.FragmentQuestionCollection;

import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-6-18 下午 4:22
 * FileName: MineCollectionActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 我de收藏
 */
public class MineCollectionActivity extends BaseActivity {
    private XTabLayout xTablayout;
    private Toolbar titlebarToolbar;
    private ViewPager viewpager;

    @Override
    protected int setContentView() {
        return R.layout.activity_mine_collection;
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
        xTablayout = (XTabLayout) findViewById(R.id.xTablayout);
        titlebarToolbar = (Toolbar) findViewById(R.id.titlebar_toolbar);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
    }

    @Override
    protected void initData() {
        super.initData();
        ArrayList<String> titlesList = new ArrayList<String>();
        ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>();

        FragmentCourseCollection fragmentCourseCollection = new FragmentCourseCollection();
        FragmentQuestionCollection fragmentQuestionCollection = new FragmentQuestionCollection();
        fragmentArrayList.add(fragmentCourseCollection);
        fragmentArrayList.add(fragmentQuestionCollection);

        titlesList.add(getResources().getString(R.string.mine_collection_course));
        titlesList.add(getResources().getString(R.string.mine_collection_question));

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        CommonTabAdapter commonTabAdapter = new CommonTabAdapter(supportFragmentManager, fragmentArrayList, titlesList);
        viewpager.setAdapter(commonTabAdapter);//viewpager设置适配器
        viewpager.setOffscreenPageLimit(fragmentArrayList.size());//设置预加载页面数量
        xTablayout.setupWithViewPager(viewpager);//tablayout关联viewpager
    }

}
