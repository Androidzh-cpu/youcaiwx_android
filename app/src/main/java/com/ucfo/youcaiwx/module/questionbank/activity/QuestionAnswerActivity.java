package com.ucfo.youcaiwx.module.questionbank.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.module.course.player.adapter.CommonTabAdapter;
import com.ucfo.youcaiwx.module.questionbank.fragment.AllAnswerQuestionFragment;
import com.ucfo.youcaiwx.module.questionbank.fragment.MineAskQuestionsFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-5-29 下午 6:39
 * FileName: QuestionAnswerActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 题库所属答疑,分为全部答疑和我的提问
 */
public class QuestionAnswerActivity extends BaseActivity {

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
    @BindView(R.id.btn_ask)
    LinearLayout btnAsk;
    private AllAnswerQuestionFragment allAnswerQuestionFragment;
    private MineAskQuestionsFragment mineAskQuestionsFragment;
    private Bundle bundle;
    private int question_id, course_id;

    @Override
    protected int setContentView() {
        return R.layout.activity_question_answer;
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
        titlebarMidtitle.setText(getResources().getString(R.string.question_tab_query));
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

        bundle = getIntent().getExtras();
        if (bundle != null) {
            question_id = bundle.getInt(Constant.QUESTION_ID, 0);
            course_id = bundle.getInt(Constant.COURSE_ID, 0);
        } else {
            finish();
        }
    }

    @Override
    protected void initData() {
        super.initData();
        ArrayList<String> titlesList = new ArrayList<String>();
        ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>();

        allAnswerQuestionFragment = new AllAnswerQuestionFragment();
        mineAskQuestionsFragment = new MineAskQuestionsFragment();
        fragmentArrayList.add(allAnswerQuestionFragment);
        fragmentArrayList.add(mineAskQuestionsFragment);

        titlesList.add(getResources().getString(R.string.answer_title_allAnswer));
        titlesList.add(getResources().getString(R.string.answer_title_mineAsk));

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        CommonTabAdapter commonTabAdapter = new CommonTabAdapter(supportFragmentManager, fragmentArrayList, titlesList);
        viewpager.setAdapter(commonTabAdapter);//viewpager设置适配器
        viewpager.setOffscreenPageLimit(fragmentArrayList.size());//设置预加载页面数量
        tablayout.setupWithViewPager(viewpager);//tablayout关联viewpager
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    @OnClick(R.id.btn_ask)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TYPE, Constant.TYPE_QUESTION_ASK);
        bundle.putInt(Constant.QUESTION_ID, question_id);
        bundle.putInt(Constant.COURSE_ID, course_id);
        startActivity(QuestionAskQuestionActivity.class, bundle);
    }
}
