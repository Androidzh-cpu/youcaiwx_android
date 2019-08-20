package com.ucfo.youcaiwx.view.questionbank.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.questionbank.QuestionSelfHelpCheckAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.systemutils.StatusbarUI;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-5-9 下午 2:40
 * FileName: SelfServiceActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 自助练习,选择题数
 */

public class SelfServiceActivity extends BaseActivity {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_next)
    Button btnNext;
    private ArrayList<Integer> list;
    private Bundle bundle;
    private int course_id, plate_id;
    private QuestionSelfHelpCheckAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_self_service;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<>();

        list.add(5);
        list.add(10);
        list.add(15);
        list.add(20);
        list.add(25);
        list.add(30);


        bundle = getIntent().getExtras();
        if (bundle != null) {
            plate_id = bundle.getInt(Constant.PLATE_ID, 0);
            course_id = bundle.getInt(Constant.COURSE_ID, 0);
        } else {
            //TODO nothing
        }


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(gridLayoutManager);


        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new QuestionSelfHelpCheckAdapter(list, this);
        }
        recyclerview.setAdapter(adapter);
        adapter.setSelectPosition(-1);
        adapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.setSelectPosition(position);
                adapter.notifyDataSetChanged();
            }
        });

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
        titlebarMidtitle.setText(getResources().getString(R.string.question_title_writes_really));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        int selectPosition = adapter.getSelectPosition();
        if (selectPosition >= 0) {
            Intent intent = new Intent(this, SelfServiceListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.COURSE_ID, course_id);
            bundle.putInt(Constant.NUMBER, list.get(selectPosition).intValue());
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            ToastUtil.showBottomShortText(this, getResources().getString(R.string.question_tips_checkquestionnumber));
        }
    }
}
