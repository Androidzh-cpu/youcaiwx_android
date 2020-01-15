package com.ucfo.youcaiwx.module.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.questionbank.SubjectAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.questionbank.QuestionMyProjectBean;
import com.ucfo.youcaiwx.entity.questionbank.SubjectInfoBean;
import com.ucfo.youcaiwx.module.login.LoginActivity;
import com.ucfo.youcaiwx.module.main.activity.WebActivity;
import com.ucfo.youcaiwx.module.questionbank.activity.ErrorCenterActivity;
import com.ucfo.youcaiwx.module.questionbank.activity.HighFrequencyWrongTopicActivity;
import com.ucfo.youcaiwx.module.questionbank.activity.KnowledgePracticeActivity;
import com.ucfo.youcaiwx.module.questionbank.activity.QuestionsOnRecordActivity;
import com.ucfo.youcaiwx.module.questionbank.activity.SelfServiceActivity;
import com.ucfo.youcaiwx.module.questionbank.activity.StageOfTestingActivity;
import com.ucfo.youcaiwx.module.questionbank.activity.TESTMODEActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.questionbank.QuestionBankHomePresenter;
import com.ucfo.youcaiwx.presenter.view.questionbank.IQuestionBankHomeView;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.StatusBarUtil;
import com.ucfo.youcaiwx.widget.customview.CirclePercentBar;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * Author:AND
 * Time: 2019-3-12.  上午 10:18
 * Email:2911743255@qq.com
 * ClassName: QuestionBankFragment
 * Description:TODO 首页 - 题库
 * Detail:TODO plate_id代表板块 1知识点练习,2阶段测试,3论述题自测,4错题智能练习,5自主练习,6组卷模考
 */

public class QuestionBankFragment extends BaseFragment implements IQuestionBankHomeView, View.OnClickListener {
    public static final String TAG = "QuestionBankFragment";

    private TextView titlebarMidtitle;
    private Toolbar titlebarToolbar;
    private RoundTextView questionDoexercisecount;
    private CirclePercentBar questionAccuracyPercent;
    private CirclePercentBar questionAveragePercent;
    private CirclePercentBar questionRankingPercent;
    private TextView questionAssessment;
    private TextView questionErrorscenter;
    private TextView questionRecord;
    private RoundTextView questionAtonceExp;
    private LinearLayout questionKnowledgeExercise;
    private LinearLayout questionStageTest;
    private LinearLayout questionElaborationTest;
    private RoundTextView questionHightErrors;
    private LinearLayout questionGroupExam;
    private LinearLayout questionWritesReally;
    private LinearLayout questionbankUnloginhome;
    private LinearLayout questionbankLoginhome;
    private ImageView titlebarMidimage;
    private View statusbarView;
    private View viewLine;
    private LoadingLayout loadinglayout;
    private LinearLayout mtrainingcampzhquestion;
    private LinearLayout mhighterrorszhquestion;
    private LinearLayout mzhlinear;
    private LinearLayout mehlinear;


    private SharedPreferencesUtils sharedPreferencesUtils;
    private QuestionBankHomePresenter questionBankHomePresenter;
    //用户ID,当前选中的题库
    private int userId, currentSubjectId;
    //当前选中的题库的训练营状态
    private String currentSubjectStatus;
    private boolean loginStatus;//用户登录状态
    private long lastClickTime = 0;
    private ArrayList<QuestionMyProjectBean.DataBean> projectList;//已购买题库
    private PopupWindow popupWindow;//题库选择弹框
    private SubjectAdapter subjectAdapter;
    private boolean isShowLoading = false;
    private View contentView;

    @Override
    public void onResume() {
        super.onResume();
        isShowLoading = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (popupWindow != null) {
            if (isAdded()) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        }
    }

    /**
     * 刷新数据
     */
    private void loadNetData() {
        if (sharedPreferencesUtils == null) {
            sharedPreferencesUtils = SharedPreferencesUtils.getInstance(Objects.requireNonNull(getContext()));
        }
        if (questionBankHomePresenter == null) {
            questionBankHomePresenter = new QuestionBankHomePresenter(this);
        }
        loginStatus = sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false);//用户登录状态
        userId = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        currentSubjectId = sharedPreferencesUtils.getInt(Constant.SUBJECT_ID, 0);
        if (loginStatus) {
            //TODO 用户已登录
            questionBankHomePresenter.getMyProejctList(userId);
        } else {
            //TODO 未登录==未开通题库
            //清除题库信息
            clearUserInfo();
            //显示主页面
            showContent();
        }
    }

    /**
     * 清除题库信息
     */
    private void clearUserInfo() {
        if (isAdded()) {
            questionbankUnloginhome.setVisibility(View.VISIBLE);//零元体验题库
            questionbankLoginhome.setVisibility(View.GONE);//真正题库隐藏
            titlebarMidtitle.setText(getResources().getString(R.string.question_default));//设置零元体验标题
            titlebarMidimage.setVisibility(View.GONE);//下拉箭头隐藏
            if (projectList != null) {
                projectList.clear();
            }
            if (sharedPreferencesUtils == null) {
                sharedPreferencesUtils = SharedPreferencesUtils.getInstance(getContext());
            }
            sharedPreferencesUtils.remove(Constant.SUBJECT_ID);
            sharedPreferencesUtils.remove(Constant.SUBJECT_STATUS);

            //清除排名信息,避免出现关闭题库后信息已久留存的问题
            questionAccuracyPercent.setPercentData(0, "%", false, new DecelerateInterpolator());
            questionAveragePercent.setPercentData(0, "", false, new DecelerateInterpolator());
            questionRankingPercent.setPercentData(0, "", false, new DecelerateInterpolator());
            questionDoexercisecount.setText(String.valueOf(0));
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_questionbank;
    }

    @Override
    protected void initView(View itemView) {
        statusbarView = (View) itemView.findViewById(R.id.statusbar_view);
        titlebarMidtitle = (TextView) itemView.findViewById(R.id.titlebar_midtitle);
        titlebarMidtitle.setOnClickListener(this);
        titlebarMidimage = (ImageView) itemView.findViewById(R.id.titlebar_midimage);
        titlebarMidimage.setOnClickListener(this);
        titlebarToolbar = (Toolbar) itemView.findViewById(R.id.titlebar_toolbar);
        viewLine = (View) itemView.findViewById(R.id.view_line);
        questionDoexercisecount = (RoundTextView) itemView.findViewById(R.id.question_doexercisecount);
        questionAccuracyPercent = (CirclePercentBar) itemView.findViewById(R.id.question_accuracy_percent);
        questionAveragePercent = (CirclePercentBar) itemView.findViewById(R.id.question_average_percent);
        questionRankingPercent = (CirclePercentBar) itemView.findViewById(R.id.question_ranking_percent);
        questionAssessment = (TextView) itemView.findViewById(R.id.question_assessment);
        questionAssessment.setOnClickListener(this);
        questionErrorscenter = (TextView) itemView.findViewById(R.id.question_errorscenter);
        questionErrorscenter.setOnClickListener(this);
        questionRecord = (TextView) itemView.findViewById(R.id.question_record);
        questionRecord.setOnClickListener(this);
        questionAtonceExp = (RoundTextView) itemView.findViewById(R.id.question_atonceExp);
        questionAtonceExp.setOnClickListener(this);
        questionbankUnloginhome = (LinearLayout) itemView.findViewById(R.id.questionbank_unloginhome);
        questionKnowledgeExercise = (LinearLayout) itemView.findViewById(R.id.question_Knowledge_exercise);
        questionKnowledgeExercise.setOnClickListener(this);
        questionStageTest = (LinearLayout) itemView.findViewById(R.id.question_stage_test);
        questionStageTest.setOnClickListener(this);
        questionElaborationTest = (LinearLayout) itemView.findViewById(R.id.question_elaboration_test);
        questionElaborationTest.setOnClickListener(this);
        questionHightErrors = (RoundTextView) itemView.findViewById(R.id.question_hight_errors);
        questionHightErrors.setOnClickListener(this);
        questionGroupExam = (LinearLayout) itemView.findViewById(R.id.question_group_exam);
        questionGroupExam.setOnClickListener(this);
        questionWritesReally = (LinearLayout) itemView.findViewById(R.id.question_writes_really);
        questionWritesReally.setOnClickListener(this);
        questionbankLoginhome = (LinearLayout) itemView.findViewById(R.id.questionbank_loginhome);
        loadinglayout = (LoadingLayout) itemView.findViewById(R.id.loadinglayout);
        mtrainingcampzhquestion = (LinearLayout) itemView.findViewById(R.id.question_training_camp_zh);
        mtrainingcampzhquestion.setOnClickListener(this);
        mhighterrorszhquestion = (LinearLayout) itemView.findViewById(R.id.question_hight_errors_zh);
        mhighterrorszhquestion.setOnClickListener(this);
        mzhlinear = (LinearLayout) itemView.findViewById(R.id.linear_zh);
        mehlinear = (LinearLayout) itemView.findViewById(R.id.linear_eh);


        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) statusbarView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = StatusBarUtil.getStatusBarHeight(getContext());
        statusbarView.setLayoutParams(layoutParams);
    }

    @Override
    protected void initData() {
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(getContext());

        projectList = new ArrayList<>();
        //注册网络
        questionBankHomePresenter = new QuestionBankHomePresenter(this);

        loadNetData();

        if (loadinglayout != null) {
            loadinglayout.setRetryListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadNetData();
                }
            });
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            loadNetData();
        }
    }

    /**
     * 界面跳转判断登陆状态
     */
    public void startToActivity(Bundle bundle, Class<?> cls) {
        if (loginStatus) {
            if (projectList != null && projectList.size() > 0) {
                Intent intent = new Intent(getContext(), cls);
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                startActivity(intent);
            } else {
                showToast(getResources().getString(R.string.course_buyBank));
            }
        } else {
            startActivity(LoginActivity.class);
        }

    }

    /**
     * Description:QuestionBankFragment
     * Time:2019-4-26   上午 10:54
     * Detail: TODO 获取我的题库列表
     */
    @Override
    public void getMyProejctList(QuestionMyProjectBean result) {
        if (result != null) {
            if (result.getData() != null && result.getData().size() > 0) {//TODO 购买的有题库
                List<QuestionMyProjectBean.DataBean> dataBeanList = result.getData();
                projectList.clear();
                projectList.addAll(dataBeanList);

                if (projectList != null && projectList.size() > 0) {
                    //TODO  已购买过的科目
                    //获取本地存储的题库ID和题库状态
                    currentSubjectId = sharedPreferencesUtils.getInt(Constant.SUBJECT_ID, 0);
                    currentSubjectStatus = sharedPreferencesUtils.getString(Constant.SUBJECT_STATUS, "");

                    if (projectList.size() == 1) {
                        //TODO  只购买了一个科目
                        //当前选中的科目ID
                        currentSubjectId = projectList.get(0).getId();
                        //当前科目是否开启了训练营
                        currentSubjectStatus = projectList.get(0).getStatus();

                        //存放当前的科目ID和训练营状态
                        sharedPreferencesUtils.putInt(Constant.SUBJECT_ID, currentSubjectId);
                        sharedPreferencesUtils.putString(Constant.SUBJECT_STATUS, currentSubjectStatus);

                        //设置标题
                        titlebarMidtitle.setText(projectList.get(0).getName());
                        titlebarMidimage.setVisibility(View.GONE);
                        //获取对应的科目的信息
                        getSubjectInfo(currentSubjectId);
                        //设置训练营状态
                        setWhetherTrainCamp(currentSubjectStatus);
                    } else {
                        //TODO  购买了多个科目
                        if (currentSubjectId != 0) {
                            //TODO 本地已存储上次的科目记录
                            for (int i = 0; i < projectList.size(); i++) {
                                if (currentSubjectId == projectList.get(i).getId()) {
                                    //由于之前用户在本地已存储过科目,所以状态需要更新
                                    currentSubjectStatus = projectList.get(i).getStatus();
                                    titlebarMidtitle.setText(projectList.get(i).getName());
                                    break;
                                }
                            }
                            titlebarMidimage.setVisibility(View.VISIBLE);
                            //获取对应的科目的信息
                            getSubjectInfo(currentSubjectId);
                            //设置训练营状态
                            setWhetherTrainCamp(currentSubjectStatus);
                        } else {
                            //TODO 未存储上次的科目
                            //默认选中第一个
                            currentSubjectId = projectList.get(0).getId();
                            //当前科目是否开启了训练营
                            currentSubjectStatus = projectList.get(0).getStatus();

                            //存放当前的科目ID和试卷状态
                            sharedPreferencesUtils.putInt(Constant.SUBJECT_ID, currentSubjectId);
                            sharedPreferencesUtils.putString(Constant.SUBJECT_STATUS, currentSubjectStatus);

                            //设置标题
                            titlebarMidtitle.setText(projectList.get(0).getName());
                            titlebarMidimage.setVisibility(View.VISIBLE);

                            //获取对应的科目的信息
                            getSubjectInfo(currentSubjectId);
                            //设置训练营状态
                            setWhetherTrainCamp(currentSubjectStatus);
                        }
                    }
                }
                questionbankUnloginhome.setVisibility(View.GONE);
                questionbankLoginhome.setVisibility(View.VISIBLE);
            } else {
                //TODO  未购买科目
                //清除题库信息
                clearUserInfo();
                //显示主页面
                showContent();
            }
        } else {
            //TODO  未购买科目
            //清除题库信息
            clearUserInfo();
            //显示主页面
            showContent();
        }
    }

    /**
     * 根据响应的学科id获取相应的做题信息
     */
    private void getSubjectInfo(int subjectid) {
        if (questionBankHomePresenter != null) {
            questionBankHomePresenter.getSubjectInfo(userId, subjectid);
        }
    }

    /**
     * 当前选择题库信息
     */
    @Override
    public void getSubjectInfoBean(SubjectInfoBean data) {
        if (isAdded()) {
            if (data != null) {
                SubjectInfoBean.DataBean dataData = data.getData();
                float accuracy = dataData.getAccuracy();//正确率
                int ranking = dataData.getRanking();//学院排名
                float score = dataData.getScore();//平均分
                String totalNum = dataData.getTotal_num();//总体书

                questionAccuracyPercent.setPercentData(accuracy, "%", false, new DecelerateInterpolator());
                questionAveragePercent.setPercentData(score, "", false, new DecelerateInterpolator());
                questionRankingPercent.setPercentData(ranking, "", false, new DecelerateInterpolator());
                questionDoexercisecount.setText(String.valueOf(totalNum));

            } else {
                questionAccuracyPercent.setPercentData(0, "%", false, new DecelerateInterpolator());
                questionAveragePercent.setPercentData(0, "", false, new DecelerateInterpolator());
                questionRankingPercent.setPercentData(0, "", false, new DecelerateInterpolator());
                questionDoexercisecount.setText(String.valueOf(0));
            }
        }
        showContent();
    }

    /**
     * 显示主页面
     */
    public void showContent() {
        if (loadinglayout != null) {
            loadinglayout.showContent();
        }
    }

    @Override
    public void onClick(View view) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > 1000) {
            lastClickTime = currentTime;
            if (sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false)) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.COURSE_ID, currentSubjectId);
                switch (view.getId()) {
                    case R.id.question_assessment:
                        //TODO 能力评估
                        int anInt = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
                        bundle.putString(Constant.WEB_URL, ApiStores.QUESTION_AbilityTOAssess + "?user_id=" + anInt + "&course_id=" + currentSubjectId);
                        bundle.putString(Constant.WEB_TITLE, getResources().getString(R.string.question_title_assessment));
                        startToActivity(bundle, WebActivity.class);
                        break;
                    case R.id.question_errorscenter:
                        //TODO 错题中心
                        startToActivity(bundle, ErrorCenterActivity.class);
                        break;
                    case R.id.question_record:
                        //TODO 答题记录
                        startToActivity(bundle, QuestionsOnRecordActivity.class);
                        break;
                    case R.id.question_atonceExp:
                        //TODO 0元体验题库
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_0);
                        bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_E);
                        startActivity(TESTMODEActivity.class, bundle);
                        break;
                    case R.id.question_Knowledge_exercise:
                        //TODO 知识点练习
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_1);
                        startToActivity(bundle, KnowledgePracticeActivity.class);
                        break;
                    case R.id.question_stage_test:
                        //TODO 阶段测试
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_2);
                        startToActivity(bundle, StageOfTestingActivity.class);
                        break;
                    case R.id.question_elaboration_test:
                        //TODO 论述题自测
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_3);
                        startToActivity(bundle, StageOfTestingActivity.class);
                        break;
                    case R.id.question_hight_errors:
                    case R.id.question_hight_errors_zh:
                        //TODO 高频错题
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_4);
                        startToActivity(bundle, HighFrequencyWrongTopicActivity.class);
                        break;
                    case R.id.question_training_camp_zh:
                        //TODO 冲刺训练营
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_7);
                        startToActivity(bundle, StageOfTestingActivity.class);
                        break;
                    case R.id.question_group_exam:
                        //TODO 组卷模考
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_6);
                        startToActivity(bundle, StageOfTestingActivity.class);
                        break;
                    case R.id.question_writes_really:
                        //TODO  自助练习
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_5);
                        startToActivity(bundle, SelfServiceActivity.class);
                        break;
                    case R.id.titlebar_midtitle:
                    case R.id.titlebar_midimage:
                        //TODO 选择专业题库
                        subjectWindow();
                        break;
                    default:
                        break;
                }
            } else {
                //未登录,去登录页
                startActivity(LoginActivity.class);
            }
        }
    }

    /**
     * Description:QuestionBankFragment
     * Time:2019-4-26   下午 3:15
     * Detail: 学科弹出框
     */
    private void subjectWindow() {
        if (projectList != null && projectList.size() > 1) {
            if (contentView == null) {
                contentView = LayoutInflater.from(getContext()).inflate(R.layout.popuwindow_subject, null, false);
            }
            if (popupWindow == null) {
                popupWindow = new PopupWindow(contentView);
                popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_pulldown));
                popupWindow.setOutsideTouchable(false);
                popupWindow.setFocusable(true);
                popupWindow.setAnimationStyle(R.style.Widget_AppCompat_PopupWindow);
            }
            int[] a = new int[2];
            titlebarToolbar.getLocationInWindow(a);
            popupWindow.showAtLocation(titlebarToolbar, Gravity.TOP | Gravity.CENTER_HORIZONTAL, a[0], titlebarToolbar.getHeight() + a[1] / 3);

            ListView listView = contentView.findViewById(R.id.subject_recyclerview);
            if (subjectAdapter == null) {
                subjectAdapter = new SubjectAdapter(getContext(), projectList);
                listView.setAdapter(subjectAdapter);
            } else {
                subjectAdapter.notifyDataSetChanged();
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //当前选中的科目ID和状态
                    currentSubjectId = projectList.get(position).getId();
                    currentSubjectStatus = projectList.get(position).getStatus();

                    if (sharedPreferencesUtils == null) {
                        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(getContext());
                    }
                    //存放当前的科目ID和状态
                    sharedPreferencesUtils.putInt(Constant.SUBJECT_ID, currentSubjectId);
                    sharedPreferencesUtils.putString(Constant.SUBJECT_STATUS, currentSubjectStatus);

                    isShowLoading = true;
                    //获取对应的科目的信息和训练营状态
                    getSubjectInfo(currentSubjectId);
                    setWhetherTrainCamp(currentSubjectStatus);

                    titlebarMidtitle.setText(projectList.get(position).getName());//设置选中的标题

                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                }
            });
            if (popupWindow != null) {
                if (popupWindow.isShowing()) {
                    titlebarMidimage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.icon_qb_indicatortop));
                }
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        titlebarMidimage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.icon_qb_indicatorbot));
                    }
                });
            }
        }
    }

    /**
     * 设置中英文下冲刺训练营
     */
    private void setWhetherTrainCamp(String currentSubjectStatus) {
        if (isAdded()) {
            if (!TextUtils.isEmpty(currentSubjectStatus)) {
                if (TextUtils.equals(currentSubjectStatus, String.valueOf(1))) {
                    if (mehlinear.getVisibility() != View.GONE) {
                        mehlinear.setVisibility(View.GONE);
                    }
                    if (mzhlinear.getVisibility() != View.VISIBLE) {
                        mzhlinear.setVisibility(View.VISIBLE);
                    }
                } else if (TextUtils.equals(currentSubjectStatus, String.valueOf(2))) {
                    if (mehlinear.getVisibility() != View.VISIBLE) {
                        mehlinear.setVisibility(View.VISIBLE);
                    }
                    if (mzhlinear.getVisibility() != View.GONE) {
                        mzhlinear.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    @Override
    public void showLoading() {
        if (isShowLoading) {
            setProcessLoading(null, true);
            //loadinglayout.showLoading();
        }
    }

    @Override
    public void showLoadingFinish() {
        //dismissPorcess();
        dismissPorcessDelayed(500);
    }

    @Override
    public void showError() {
        if (loadinglayout != null) {
            loadinglayout.showError();
        }
    }
}
