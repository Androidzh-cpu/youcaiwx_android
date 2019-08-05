package com.ucfo.youcai.view.learncenter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.ucfo.youcai.R;
import com.ucfo.youcai.base.BaseActivity;
import com.ucfo.youcai.utils.ActivityUtil;
import com.ucfo.youcai.utils.systemutils.StatusbarUI;
import com.ucfo.youcai.view.main.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-7-16 上午 9:26
 * Package: com.ucfo.youcai.view.learncenter
 * FileName: AddLearningPlanCompleteActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 添加学习计划完成欢迎页
 */

public class AddLearningPlanCompleteActivity extends BaseActivity {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.btn_tolearn)
    RoundTextView btnTolearn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_add_learning_plan_complete;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

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
        titlebarMidtitle.setText(getResources().getString(R.string.learncenter_addPlan));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_tolearn)
    public void onViewClicked() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        ActivityUtil.getInstance().finishToActivity(MainActivity.class, true);
    }
}
