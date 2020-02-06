package com.ucfo.youcaiwx.module.questionbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.ucfo.youcaiwx.entity.questionbank.TipsBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.questionbank.QuestionBankStageOfTestPresenter;
import com.ucfo.youcaiwx.presenter.view.questionbank.IQuestionBankStageView;
import com.ucfo.youcaiwx.utils.baseadapter.OnItemClickListener;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
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
 * Description:TODO 试卷列表
 * Details:todo 阶段测试,论述题自测使用同一个目录结构,接口也相同和相同的数据;组卷模考另起接口,但结构和格式相同;冲刺训练营另起接口,数据相同
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

    private SharedPreferencesUtils sharedPreferencesUtils;
    private int user_id, course_id, plate_id;
    private Bundle bundle;
    private ArrayList<QuestionStageOfTestBean.DataBean> list;
    private QuestionBankStageOfTestPresenter questionBankStageOfTestPresenter;
    private QuestionStageTestAdapter questionStageTestAdapter;
    private String paperTitle, paperContent;
    private boolean training_dialog;

    @Override
    protected int setContentView() {
        return R.layout.activity_stage_of_testing;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<QuestionStageOfTestBean.DataBean>();
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(this);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);

        questionBankStageOfTestPresenter = new QuestionBankStageOfTestPresenter(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            course_id = bundle.getInt(Constant.COURSE_ID, 0);//TODO 其实就是已购买的题库的id
            plate_id = bundle.getInt(Constant.PLATE_ID, 0);//TODO 6大板块对应的值
            switch (plate_id) {
                case Constant.PLATE_2:
                    //阶段测试
                    titlebarMidtitle.setText(getResources().getString(R.string.question_title_stage_test));
                    break;
                case Constant.PLATE_3:
                    //论述题自测
                    titlebarMidtitle.setText(getResources().getString(R.string.question_title_elaboration_test));
                    break;
                case Constant.PLATE_6:
                    //组卷模考
                    titlebarMidtitle.setText(getResources().getString(R.string.question_title_group_exam));
                    break;
                case Constant.PLATE_7:
                    //冲刺训练营
                    titlebarMidtitle.setText(getResources().getString(R.string.question_title_training_camp));
                    break;
                default:
                    titlebarMidtitle.setText(getResources().getString(R.string.default_title));
                    break;
            }
            //获取章节列表
            questionBankStageOfTestPresenter.getStageOfTestData(course_id, plate_id);
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
        //重新加载
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionBankStageOfTestPresenter.getStageOfTestData(course_id, plate_id);
            }
        });
        //刷新
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
        refreshlayout.finishRefresh();
    }

    private void initAdapter() {
        if (plate_id == Constant.PLATE_7) {
            //冲刺训练营才会有提示框
            questionBankStageOfTestPresenter.getDialogTips(plate_id);
        }

        if (questionStageTestAdapter == null) {
            questionStageTestAdapter = new QuestionStageTestAdapter(this, list, plate_id);
            recyclerview.setAdapter(questionStageTestAdapter);
        } else {
            questionStageTestAdapter.notifyChange(list);
        }
        questionStageTestAdapter.setItemClick(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.COURSE_ID, course_id);
                switch (plate_id) {
                    case Constant.PLATE_2://TODO 阶段测试
                        String paperId = list.get(position).getPaper_id();
                        if (TextUtils.isEmpty(paperId)) {
                            showToast(getResources().getString(R.string.miss_params));
                            return;
                        }
                        bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_E);
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_2);
                        bundle.putInt(Constant.PAPER_ID, Integer.parseInt(paperId));
                        startToActivity(bundle, TESTMODEActivity.class);
                        break;
                    case Constant.PLATE_3://TODO 论述题自测
                        String paper_id = list.get(position).getPaper_id();
                        if (TextUtils.isEmpty(paper_id)) {
                            showToast(getResources().getString(R.string.miss_params));
                            return;
                        }
                        bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_D);
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_3);
                        bundle.putInt(Constant.PAPER_ID, Integer.parseInt(paper_id));
                        startToActivity(bundle, TESTMODEActivity.class);
                        break;
                    case Constant.PLATE_6://TODO 组卷模考
                        String mockId = list.get(position).getMock_id();
                        if (TextUtils.isEmpty(mockId)) {
                            showToast(getResources().getString(R.string.miss_params));
                            return;
                        }
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
                                        bundle.putInt(Constant.MOCK_ID, Integer.parseInt(mockId));
                                        startToActivity(bundle, TESTMODEActivity.class);
                                    }
                                }).show();
                        break;
                    case Constant.PLATE_7:
                        //TODO 冲刺训练营
                        QuestionStageOfTestBean.DataBean bean = list.get(position);
                        String paperType = bean.getPaper_type();
                        String paperStauts = bean.getPaper_stauts();
                        String trainingCampID = bean.getPaper_id();
/*
                        if (TextUtils.equals(paperStauts, String.valueOf(2))) {
                            showToast(getResources().getString(R.string.question_cannotexam));
                            return;
                        }
*/
                        if (TextUtils.isEmpty(paperType)) {
                            showToast(getResources().getString(R.string.miss_params));
                            return;
                        }
                        if (TextUtils.equals(paperType, String.valueOf(1))) {
                            //单选题
                            bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_E);
                        } else if (TextUtils.equals(paperType, String.valueOf(2))) {
                            //论述题
                            bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_D);
                        }
                        if (TextUtils.isEmpty(trainingCampID)) {
                            showToast(getResources().getString(R.string.miss_params));
                        }
                        if (TextUtils.isEmpty(paperTitle) || TextUtils.isEmpty(paperContent)) {
                            return;
                        }
                        new AlertDialog(StageOfTestingActivity.this).builder()
                                .setTitle(paperTitle)
                                .setMsg(paperContent)
                                .setCancelable(true)
                                .setCanceledOnTouchOutside(true)
                                .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_7);
                                        bundle.putInt(Constant.PAPER_TYPE, Integer.parseInt(paperType));
                                        bundle.putInt(Constant.PAPER_ID, Integer.parseInt(trainingCampID));
                                        startToActivity(bundle, TESTMODEActivity.class);
                                    }
                                }).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void getDialogTips(TipsBean data) {
        if (data != null) {
            TipsBean.DataBean bean = data.getData();
            if (bean != null) {
                paperContent = bean.getPaper_content();
                paperTitle = bean.getPaper_title();

                String plateContent = bean.getPlate_content();
                String plateTitle = bean.getPlate_title();
                training_dialog = sharedPreferencesUtils.getBoolean(Constant.TRAINING_DIALOG, false);
                if (!training_dialog) {
                    //提示信息
                    setTrainingCampDialog(plateTitle, plateContent);
                }
            }
        }
    }

    /**
     * 设置训练营弹框
     */
    private void setTrainingCampDialog(String title, String message) {
        if (plate_id == Constant.PLATE_7) {
            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(message)) {
                return;
            }
            new AlertDialog(StageOfTestingActivity.this).builder()
                    .setTitle(title)
                    .setMsg(message)
                    .setCancelable(false)
                    .setCanceledOnTouchOutside(false)
                    .setNegativeButton(getResources().getString(R.string.nottips), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferencesUtils.getInstance(StageOfTestingActivity.this).putBoolean(Constant.TRAINING_DIALOG, true);
                        }
                    })
                    .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();

        }
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
        //TODO nothing
    }

    @Override
    public void showLoadingFinish() {
    }

    @Override
    public void showError() {
        if (loadinglayout != null) {
            loadinglayout.showError();
        }
    }
}
