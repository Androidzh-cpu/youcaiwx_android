package com.ucfo.youcai.view.home;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ucfo.youcai.R;
import com.ucfo.youcai.base.BaseActivity;
import com.ucfo.youcai.widget.customview.LoadingLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-8-8 下午 2:29
 * Package: com.ucfo.youcai.view.home
 * FileName: AnnouncementCenterActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 网校公告
 */

public class AnnouncementCenterActivity extends BaseActivity {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.showline)
    View showline;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
        showline.setVisibility(View.GONE);
        titlebarMidtitle.setText(getResources().getString(R.string.message_notice));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_announcement_center;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

}
