package com.ucfo.youcaiwx.module.questionbank.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.questionbank.QuestionKnowledgeAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.questionbank.QuestionBankHightErrorBean;
import com.ucfo.youcaiwx.entity.questionbank.QuestionErrorCenterBean;
import com.ucfo.youcaiwx.entity.questionbank.QuestionKnowLedgeChildListBean;
import com.ucfo.youcaiwx.entity.questionbank.QuestionKnowledgeListBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.questionbank.QuestionBankKnowledgePresenter;
import com.ucfo.youcaiwx.presenter.view.questionbank.IQuestionBankKonwledgeView;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-5-5 下午 5:38
 * FileName: KnowledgePracticeActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 知识点练习
 * detail:TODO 选择相应章节后跳转至知识点页面,然后开始做题
 */
public class KnowledgePracticeActivity extends BaseActivity implements IQuestionBankKonwledgeView {
    private TextView titlebarMidtitle;
    private TextView titlebarRighttitle;
    private Toolbar titlebarToolbar;
    private RadioButton radiobtn15;
    private RadioButton radiobtn30;
    private RadioGroup radiogroup;
    private ExpandableListView listView;
    private LoadingLayout loadinglayout;
    private View showline;

    private SharedPreferencesUtils sharedPreferencesUtils;
    private boolean loginStaus;
    private int user_id, course_id, plate_id, num;//用户ID,题库ID,板块ID, 题目数量
    private Bundle bundle;
    private QuestionBankKnowledgePresenter questionBankKnowledgePresenter;
    private ArrayList<QuestionKnowledgeListBean.DataBean> list;
    private QuestionKnowledgeAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
        loadNetData();
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_knowledge_practice;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        titlebarMidtitle = (TextView) findViewById(R.id.titlebar_midtitle);
        titlebarRighttitle = (TextView) findViewById(R.id.titlebar_righttitle);
        titlebarToolbar = (Toolbar) findViewById(R.id.titlebar_toolbar);
        showline = (View) findViewById(R.id.showline);
        radiobtn15 = (RadioButton) findViewById(R.id.radiobtn_15);
        radiobtn30 = (RadioButton) findViewById(R.id.radiobtn_30);
        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        listView = (ExpandableListView) findViewById(R.id.listView);
        loadinglayout = (LoadingLayout) findViewById(R.id.loadinglayout);
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<>();
        questionBankKnowledgePresenter = new QuestionBankKnowledgePresenter(this);

        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(this);
        loginStaus = sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        if (!loginStaus) {
            loadinglayout.showEmpty();
        }


        bundle = getIntent().getExtras();
        if (bundle != null) {
            course_id = bundle.getInt(Constant.COURSE_ID, 0);
            plate_id = bundle.getInt(Constant.PLATE_ID, 0);
            //loadNetData();
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
        //重新加载
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNetData();
            }
        });
        //添加选中试题数量
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radiobtn_15:
                        num = 15;
                        break;
                    case R.id.radiobtn_30:
                        num = 30;
                        break;
                }
            }
        });
    }

    /**
     * 客户说想实时刷新这个列表的数据
     */
    private void loadNetData() {
        if (questionBankKnowledgePresenter != null) {
            questionBankKnowledgePresenter.getKnowledgeListData(course_id, user_id);
        } else {
            questionBankKnowledgePresenter = new QuestionBankKnowledgePresenter(this);
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
        titlebarMidtitle.setText(getResources().getString(R.string.question_title_Knowledge_exercise));
        showline.setVisibility(View.GONE);
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Description:KnowledgePracticeActivity
     * Time:2019-5-7 下午 2:06
     * Detail:TODO 获取知识点列表
     */
    @Override
    public void getKnowledgeList(QuestionKnowledgeListBean data) {
        if (data != null) {
            if (data.getData() != null && data.getData().size() > 0) {
                List<QuestionKnowledgeListBean.DataBean> beanList = data.getData();
                if (list == null) {
                    list = new ArrayList<>();
                }
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

    @Override
    public void getKnowledgeChildList(QuestionKnowLedgeChildListBean questionKnowLedgeChildListBean) {
        //TODO nothing
    }

    @Override
    public void getHighFrequencyWrongTopic(QuestionBankHightErrorBean questionBankHightErrorBean) {
        //TODO nothing
    }

    @Override
    public void getErrorCenterSectionList(QuestionErrorCenterBean questionErrorCenterBean) {
        //TODO  nothing
    }

    /**
     * Description:KnowledgePracticeActivity
     * Time:2019-5-7 下午 2:25
     * Detail:设置适配器
     */
    private void initAdapter() {
        if (adapter == null) {
            adapter = new QuestionKnowledgeAdapter(list, this, plate_id);
        }
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (!radiobtn15.isChecked() && !radiobtn30.isChecked()) {
                    ToastUtil.showBottomShortText(KnowledgePracticeActivity.this, getResources().getString(R.string.question_tips_checkNum));
                    return false;
                } else {
                    if (radiobtn15.isChecked()) {
                        num = 15;
                    } else if (radiobtn30.isChecked()) {
                        num = 30;
                    }
                    int section_id = list.get(groupPosition).getSection_id();
                    int child_id = list.get(groupPosition).getKnob().get(childPosition).getKnob_id();
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.COURSE_ID, course_id);
                    bundle.putInt(Constant.SECTION_ID, section_id);
                    bundle.putInt(Constant.KNOB_ID, child_id);
                    bundle.putInt(Constant.PLATE_ID, plate_id);
                    bundle.putInt(Constant.NUM, num);

                    startActivity(KnowledgeChildListActivity.class, bundle);
                }
                return true;//返回值切记为true,这样才能使得二级监听有响应
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
