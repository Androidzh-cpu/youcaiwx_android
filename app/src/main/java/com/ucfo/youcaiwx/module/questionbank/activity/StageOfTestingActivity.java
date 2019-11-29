package com.ucfo.youcaiwx.module.questionbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.questionbank.QuestionStageTestAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.questionbank.QuestionStageOfTestBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.questionbank.QuestionBankStageOfTestPresenter;
import com.ucfo.youcaiwx.presenter.view.questionbank.IQuestionBankStageView;
import com.ucfo.youcaiwx.utils.baseadapter.OnItemClickListener;
import com.ucfo.youcaiwx.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.dialog.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-5-6 上午 10:04
 * FileName: StageOfTestingActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 阶段测试,论述题自测使用同一个目录结构和相同数据,组卷模考另起接口,但结构和格式相同
 */
public class StageOfTestingActivity extends BaseActivity implements IQuestionBankStageView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    private StageOfTestingActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private boolean login_status;
    private int user_id, course_id;
    private Bundle bundle;
    private ArrayList<QuestionStageOfTestBean.DataBean> list;
    private QuestionBankStageOfTestPresenter questionBankStageOfTestPresenter;
    private QuestionStageTestAdapter questionStageTestAdapter;
    private int plate_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_stage_of_testing;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        context = this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(this);
        login_status = sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(false);
        int topBottom = DensityUtil.dip2px(this, 1);
        recyclerview.addItemDecoration(new SpacesItemDecoration(0, topBottom, ContextCompat.getColor(this, R.color.color_E6E6E6)));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<QuestionStageOfTestBean.DataBean>();
        //注册网络
        questionBankStageOfTestPresenter = new QuestionBankStageOfTestPresenter(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            course_id = bundle.getInt(Constant.COURSE_ID, 0);//TODO 其实就是已购买的题库的id
            plate_id = bundle.getInt(Constant.PLATE_ID, 0);//TODO 6大板块对应的值
            switch (plate_id) {
                case Constant.PLATE_2://阶段测试
                    titlebarMidtitle.setText(getResources().getString(R.string.question_title_stage_test));
                    break;
                case Constant.PLATE_3://论述题自测
                    titlebarMidtitle.setText(getResources().getString(R.string.question_title_elaboration_test));
                    break;
                case Constant.PLATE_6://组卷模考
                    titlebarMidtitle.setText(getResources().getString(R.string.question_title_group_exam));
                    break;
                default:
                    titlebarMidtitle.setText(getResources().getString(R.string.default_title));
                    break;
            }
            //获取章节列表
            questionBankStageOfTestPresenter.getStageOfTestData(course_id, plate_id);
        } else {
            loadinglayout.showEmpty();
        }
        //设置重新加载事件
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionBankStageOfTestPresenter.getStageOfTestData(course_id, plate_id);
            }
        });
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                questionBankStageOfTestPresenter.getStageOfTestData(course_id, plate_id);
            }
        });
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

    /**
     * Description:StageOfTestingActivity
     * Time:2019-5-6 上午 11:17
     * Detail:TODO 获取阶段测试列表
     */
    @Override
    public void getStageOfTestData(QuestionStageOfTestBean data) {
        if (data != null) {
            if (data.getData() != null && data.getData().size() > 0) {
                List<QuestionStageOfTestBean.DataBean> dataBeanList = data.getData();
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.clear();
                list.addAll(dataBeanList);
                initAdapter();

                loadinglayout.showContent();
            } else {
                loadinglayout.showEmpty();
            }
        } else {
            loadinglayout.showEmpty();
        }
        refreshlayout.finishRefresh();
    }

    private void initAdapter() {
        if (questionStageTestAdapter == null) {
            questionStageTestAdapter = new QuestionStageTestAdapter(this, list, plate_id);
        }
        questionStageTestAdapter.notifyDataSetChanged();
        recyclerview.setAdapter(questionStageTestAdapter);
        questionStageTestAdapter.setItemClick(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.COURSE_ID, course_id);
                switch (plate_id) {
                    case Constant.PLATE_2://TODO 阶段测试
                        bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_E);
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_2);
                        bundle.putInt(Constant.PAPER_ID, list.get(position).getPaper_id());
                        startToActivity(bundle, TESTMODEActivity.class);
                        break;
                    case Constant.PLATE_3://TODO 论述题自测
                        bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_D);
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_3);
                        bundle.putInt(Constant.PAPER_ID, list.get(position).getPaper_id());
                        startToActivity(bundle, TESTMODEActivity.class);
                        break;
                    case Constant.PLATE_6://TODO 组卷模考
                        new AlertDialog(StageOfTestingActivity.this).builder()
                                .setTitle(getResources().getString(R.string.explication))
                                .setMsg(getResources().getString(R.string.question_examtips))
                                .setCancelable(false)
                                .setCanceledOnTouchOutside(false)
                                .setNegativeButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_E);
                                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_6);
                                        bundle.putInt(Constant.MOCK_ID, list.get(position).getMock_id());
                                        startToActivity(bundle, TESTMODEActivity.class);
                                    }
                                }).show();

                        /*bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_E);
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_6);
                        bundle.putInt(Constant.MOCK_ID, list.get(position).getMock_id());
                        startToActivity(bundle, TESTMODEActivity.class);*/
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 界面跳转,bundle传值
     */
    public void startToActivity(Bundle bundle, Class<?> cls) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
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
