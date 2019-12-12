package com.ucfo.youcaiwx.module.questionbank.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.questionbank.QuestionHightWrongAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.questionbank.QuestionBankHightErrorBean;
import com.ucfo.youcaiwx.entity.questionbank.QuestionErrorCenterBean;
import com.ucfo.youcaiwx.entity.questionbank.QuestionKnowLedgeChildListBean;
import com.ucfo.youcaiwx.entity.questionbank.QuestionKnowledgeListBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.questionbank.QuestionBankKnowledgePresenter;
import com.ucfo.youcaiwx.presenter.view.questionbank.IQuestionBankKonwledgeView;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-5-9 上午 9:29
 * FileName: HighFrequencyWrongTopicActivity
 * ORG: www.youcaiwx.com
 * Description:TODO  系统高频错题
 */

public class HighFrequencyWrongTopicActivity extends BaseActivity implements IQuestionBankKonwledgeView {

    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.listView)
    ExpandableListView listView;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;

    private HighFrequencyWrongTopicActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private boolean loginStaus;
    private int user_id, plate_id, course_id;
    private Bundle bundle;
    private QuestionBankKnowledgePresenter questionBankKnowledgePresenter;
    private ArrayList<QuestionBankHightErrorBean.DataBean> list;
    private QuestionHightWrongAdapter questionHightWrongAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        if (questionBankKnowledgePresenter == null) {
            questionBankKnowledgePresenter = new QuestionBankKnowledgePresenter(this);
        }
        questionBankKnowledgePresenter.getHighFrequencyWrongTopic(course_id);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_high_frequency_wrong_topic;
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
        titlebarRighttitle.setVisibility(View.GONE);
        titlebarMidtitle.setText(getResources().getString(R.string.question_title_question_hight_errors));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
        ButterKnife.bind(this);

        context = this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        loginStaus = sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        if (!loginStaus) {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<>();
        questionBankKnowledgePresenter = new QuestionBankKnowledgePresenter(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            course_id = bundle.getInt(Constant.COURSE_ID, 0);
            plate_id = bundle.getInt(Constant.PLATE_ID, 0);
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
        //重新加载
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionBankKnowledgePresenter.getHighFrequencyWrongTopic(course_id);
            }
        });
    }

    @Override
    public void getKnowledgeList(QuestionKnowledgeListBean data) {
        //TODO NOTHING
    }

    @Override
    public void getKnowledgeChildList(QuestionKnowLedgeChildListBean questionKnowLedgeChildListBean) {
        //TODO NOTHING
    }

    @Override
    public void getHighFrequencyWrongTopic(QuestionBankHightErrorBean questionBankHightErrorBean) {
        if (questionBankHightErrorBean != null) {
            if (questionBankHightErrorBean.getData() != null && questionBankHightErrorBean.getData().size() > 0) {
                List<QuestionBankHightErrorBean.DataBean> data = questionBankHightErrorBean.getData();
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.clear();
                list.addAll(data);

                initAdapter();

                if (loadinglayout != null) {
                    loadinglayout.showContent();
                }
            } else {
                if (loadinglayout != null) {
                    loadinglayout.showEmpty();
                }
            }

        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
    }

    @Override
    public void getErrorCenterSectionList(QuestionErrorCenterBean questionErrorCenterBean) {
        //TODO nothing
    }

    private void initAdapter() {
        if (questionHightWrongAdapter == null) {
            questionHightWrongAdapter = new QuestionHightWrongAdapter(list, context, plate_id);
        }
        questionHightWrongAdapter.notifyDataSetChanged();
        listView.setAdapter(questionHightWrongAdapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int section_id = list.get(groupPosition).getSection_id();
                int child_id = list.get(groupPosition).getKnob().get(childPosition).getKnob_id();
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.SECTION_ID, section_id);
                bundle.putInt(Constant.COURSE_ID, course_id);
                bundle.putInt(Constant.KNOB_ID, child_id);
                bundle.putInt(Constant.PLATE_ID, plate_id);
                startActivity(KnowledgeChildListActivity.class, bundle);
                return true;
            }
        });
    }

    @Override
    public void showLoading() {
        setProcessLoading(null, true);
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
