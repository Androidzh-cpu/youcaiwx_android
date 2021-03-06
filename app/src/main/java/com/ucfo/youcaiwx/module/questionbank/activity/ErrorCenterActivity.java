package com.ucfo.youcaiwx.module.questionbank.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.questionbank.QuestionErrorCenterAdapter;
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
 * Time: 2019-5-10 下午 5:23
 * FileName: ErrorCenterActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 错题中心二级目录
 */
public class ErrorCenterActivity extends BaseActivity implements IQuestionBankKonwledgeView {

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
    private ErrorCenterActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private boolean login_status;
    private int user_id;
    private Bundle bundle;
    private int course_id;
    private ArrayList<QuestionErrorCenterBean.DataBean> list;
    private QuestionBankKnowledgePresenter presenter;
    private QuestionErrorCenterAdapter questionErrorCenterAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        loadNetData();
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_error_center;
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
        titlebarMidtitle.setText(getResources().getString(R.string.question_title_question_my_errors));
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

    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<>();

        context = this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        login_status = sharedPreferencesUtils.getBoolean(Constant.STATE, false);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        presenter = new QuestionBankKnowledgePresenter(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            course_id = bundle.getInt(Constant.COURSE_ID, 0);
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNetData();
            }
        });
    }

    private void loadNetData() {
        if (presenter == null) {
            presenter = new QuestionBankKnowledgePresenter(this);
        }
        presenter.getErrorCenterSectionList(course_id, user_id);
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
        //TODO NOTHING
    }

    /**
     * Description:ErrorCenterActivity
     * Time:2019-5-10 下午 5:49
     * Detail:TODO 我的错题中心
     */
    @Override
    public void getErrorCenterSectionList(QuestionErrorCenterBean questionErrorCenterBean) {
        if (questionErrorCenterBean != null) {
            if (questionErrorCenterBean.getData() != null && questionErrorCenterBean.getData().size() > 0) {

                List<QuestionErrorCenterBean.DataBean> beanList = questionErrorCenterBean.getData();
                list.clear();
                list.addAll(beanList);

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

    /**
     * Description:ErrorCenterActivity
     * Time:2019-5-10 下午 5:52
     * Detail:TODO 设置适配器
     */
    private void initAdapter() {
        if (questionErrorCenterAdapter == null) {
            questionErrorCenterAdapter = new QuestionErrorCenterAdapter(list, context, Constant.PLATE_17);
            listView.setAdapter(questionErrorCenterAdapter);
        } else {
            questionErrorCenterAdapter.notifyChange(list);
        }
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int sectionId = list.get(groupPosition).getSection_id();
                int knobId = list.get(groupPosition).getKnob().get(childPosition).getKnob_id();
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.PLATE_ID, Constant.PLATE_17);
                bundle.putInt(Constant.COURSE_ID, course_id);
                bundle.putInt(Constant.SECTION_ID, sectionId);
                bundle.putInt(Constant.KNOB_ID, knobId);
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
