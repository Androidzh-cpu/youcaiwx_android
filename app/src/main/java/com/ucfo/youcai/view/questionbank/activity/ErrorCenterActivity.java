package com.ucfo.youcai.view.questionbank.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.ucfo.youcai.R;
import com.ucfo.youcai.adapter.questionbank.QuestionErrorCenterAdapter;
import com.ucfo.youcai.base.BaseActivity;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.questionbank.QuestionBankHightErrorBean;
import com.ucfo.youcai.entity.questionbank.QuestionErrorCenterBean;
import com.ucfo.youcai.entity.questionbank.QuestionKnowLedgeChildListBean;
import com.ucfo.youcai.entity.questionbank.QuestionKnowledgeListBean;
import com.ucfo.youcai.presenter.presenterImpl.questionbank.QuestionBankKnowledgePresenter;
import com.ucfo.youcai.presenter.view.questionbank.IQuestionBankKonwledgeView;
import com.ucfo.youcai.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcai.utils.systemutils.StatusbarUI;
import com.ucfo.youcai.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-5-10 下午 5:23
 * Package: com.ucfo.youcai.view.questionbank.activity
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_error_center;
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

            presenter.getErrorCenterSectionList(course_id, user_id);
        } else {
            loadinglayout.showEmpty();
        }
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getErrorCenterSectionList(course_id, user_id);
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

                loadinglayout.showContent();
            } else {
                loadinglayout.showEmpty();
            }
        } else {
            loadinglayout.showEmpty();
        }
    }

    /**
     * Description:ErrorCenterActivity
     * Time:2019-5-10 下午 5:52
     * Detail:TODO 设置适配器
     */
    private void initAdapter() {
        if (questionErrorCenterAdapter != null) {
            questionErrorCenterAdapter.notifyDataSetChanged();
        } else {
            questionErrorCenterAdapter = new QuestionErrorCenterAdapter(list, context, Constant.PLATE_7);
        }
        listView.setAdapter(questionErrorCenterAdapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int section_id = list.get(groupPosition).getSection_id();
                int child_id = list.get(groupPosition).getKnob().get(childPosition).getKnob_id();
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.PLATE_ID, Constant.PLATE_7);
                bundle.putInt(Constant.COURSE_ID, course_id);
                bundle.putInt(Constant.SECTION_ID, section_id);
                bundle.putInt(Constant.KNOB_ID, child_id);
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
        loadinglayout.showError();
    }
}
