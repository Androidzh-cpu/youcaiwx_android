package com.ucfo.youcaiwx.module.questionbank.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.questionbank.QuestionSelfHelpAdapter;
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
 * Time: 2019-5-9 下午 4:47
 * FileName: SelfServiceListActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 自主练习二级列表选择
 */
public class SelfServiceListActivity extends BaseActivity implements IQuestionBankKonwledgeView {

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
    private ArrayList<QuestionKnowledgeListBean.DataBean> list;
    private SelfServiceListActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private boolean loginStaus;
    private int user_id;
    private QuestionBankKnowledgePresenter questionBankKnowledgePresenter;
    private Bundle bundle;
    private int course_id, number;
    private QuestionSelfHelpAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_self_service_list;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        context = this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<>();
        questionBankKnowledgePresenter = new QuestionBankKnowledgePresenter(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            course_id = bundle.getInt(Constant.COURSE_ID, 0);
            number = bundle.getInt(Constant.NUMBER, 0);

            questionBankKnowledgePresenter.getKnowledgeListData(course_id, user_id);
        } else {
            loadinglayout.showEmpty();
        }

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
        titlebarMidtitle.setText(getResources().getString(R.string.question_title_writes_really));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void getKnowledgeList(QuestionKnowledgeListBean data) {
        if (data != null) {
            if (data.getData() != null && data.getData().size() > 0) {
                List<QuestionKnowledgeListBean.DataBean> beanList = data.getData();
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
     * Description:SelfServiceListActivity
     * Time:2019-5-9 下午 5:30
     * Detail:TODO 添加适配器
     */
    private void initAdapter() {

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new QuestionSelfHelpAdapter(list, this, Constant.PLATE_5);
        }
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int section_id = list.get(groupPosition).getSection_id();
                int knob_id = list.get(groupPosition).getKnob().get(childPosition).getKnob_id();

                Bundle bundle = new Bundle();
                bundle.putInt(Constant.NUMBER, number);
                bundle.putInt(Constant.COURSE_ID, course_id);
                bundle.putInt(Constant.SECTION_ID, section_id);
                bundle.putInt(Constant.KNOB_ID, knob_id);
                bundle.putInt(Constant.PLATE_ID, Constant.PLATE_5);
                startActivity(SelfServiceChildListActivity.class, bundle);
                return true;
            }
        });
    }

    @Override
    public void getKnowledgeChildList(QuestionKnowLedgeChildListBean questionKnowLedgeChildListBean) {
        //TODO NOTHING
    }

    @Override
    public void getHighFrequencyWrongTopic(QuestionBankHightErrorBean questionBankHightErrorBean) {
        //TODO NOTHING
    }

    @Override
    public void getErrorCenterSectionList(QuestionErrorCenterBean questionErrorCenterBean) {
        //TODO NOTHIGN
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
