package com.ucfo.youcaiwx.view.user.activity;

import android.graphics.Color;
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
import com.ucfo.youcaiwx.utils.systemutils.StatusbarUI;
import com.ucfo.youcaiwx.view.course.player.adapter.CommonTabAdapter;
import com.ucfo.youcaiwx.view.user.fragment.FragmentCourseCollection;
import com.ucfo.youcaiwx.view.user.fragment.FragmentQuestionCollection;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-6-18 下午 4:22
 * FileName: MineCollectionActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 我饿收藏
 */

public class MineCollectionActivity extends BaseActivity {
    @BindView(R.id.xTablayout)
    XTabLayout xTablayout;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

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
        //状态栏白色,字体黑色
        StatusbarUI.setStatusBarUIMode(this, Color.TRANSPARENT, true);
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