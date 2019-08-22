package com.ucfo.youcaiwx.view.main.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
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
import com.ucfo.youcaiwx.presenter.presenterImpl.questionbank.QuestionBankHomePresenter;
import com.ucfo.youcaiwx.presenter.view.questionbank.IQuestionBankHomeView;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.StatusBarUtil;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.view.login.LoginActivity;
import com.ucfo.youcaiwx.view.main.activity.MainActivity;
import com.ucfo.youcaiwx.view.main.activity.WebActivity;
import com.ucfo.youcaiwx.view.questionbank.activity.ErrorCenterActivity;
import com.ucfo.youcaiwx.view.questionbank.activity.HighFrequencyWrongTopicActivity;
import com.ucfo.youcaiwx.view.questionbank.activity.KnowledgePracticeActivity;
import com.ucfo.youcaiwx.view.questionbank.activity.QuestionsOnRecordActivity;
import com.ucfo.youcaiwx.view.questionbank.activity.SelfServiceActivity;
import com.ucfo.youcaiwx.view.questionbank.activity.StageOfTestingActivity;
import com.ucfo.youcaiwx.view.questionbank.activity.TESTMODEActivity;
import com.ucfo.youcaiwx.widget.customview.CirclePercentBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author:AND
 * Time: 2019-3-12.  上午 10:18
 * Email:2911743255@qq.com
 * ClassName: QuestionBankFragment
 * Description:TODO 首页 - 题库
 * Detail:TODO plate_id代表板块 1知识点练习,2阶段测试,3论述题自测,4错题智能练习,5自主练习,6组卷模考
 */
public class QuestionBankFragment extends BaseFragment implements IQuestionBankHomeView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    Unbinder unbinder;
    @BindView(R.id.question_doexercisecount)
    RoundTextView questionDoexercisecount;
    @BindView(R.id.question_accuracy_percent)
    CirclePercentBar questionAccuracyPercent;
    @BindView(R.id.question_average_percent)
    CirclePercentBar questionAveragePercent;
    @BindView(R.id.question_ranking_percent)
    CirclePercentBar questionRankingPercent;
    @BindView(R.id.question_assessment)
    TextView questionAssessment;
    @BindView(R.id.question_errorscenter)
    TextView questionErrorscenter;
    @BindView(R.id.question_record)
    TextView questionRecord;
    @BindView(R.id.question_atonceExp)
    RoundTextView questionAtonceExp;
    @BindView(R.id.question_Knowledge_exercise)
    LinearLayout questionKnowledgeExercise;
    @BindView(R.id.question_stage_test)
    LinearLayout questionStageTest;
    @BindView(R.id.question_elaboration_test)
    LinearLayout questionElaborationTest;
    @BindView(R.id.question_hight_errors)
    RoundTextView questionHightErrors;
    @BindView(R.id.question_group_exam)
    LinearLayout questionGroupExam;
    @BindView(R.id.question_writes_really)
    LinearLayout questionWritesReally;
    @BindView(R.id.questionbank_unloginhome)
    LinearLayout questionbankUnloginhome;
    @BindView(R.id.questionbank_loginhome)
    LinearLayout questionbankLoginhome;
    @BindView(R.id.titlebar_midimage)
    ImageView titlebarMidimage;
    @BindView(R.id.statusbar_view)
    View statusbarView;
    @BindView(R.id.view_line)
    View viewLine;
    private MainActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private QuestionBankHomePresenter questionBankHomePresenter;
    //用户ID,当前选中的题库
    private int userId, currentSubjectId;
    private boolean loginStatus;//用户登录状态
    private long lastClickTime = 0;
    private ArrayList<QuestionMyProjectBean.DataBean> projectList;//已购买题库
    private PopupWindow popupWindow;//题库选择弹框
    private SubjectAdapter subjectAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        if (rootView != null) {
            unbinder = ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loginStatus = sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false);//用户登录状态
        userId = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        currentSubjectId = sharedPreferencesUtils.getInt(Constant.SUBJECT_ID, 0);
        if (loginStatus) {//TODO 用户已登录
            questionBankHomePresenter.getMyProejctList(userId);
        } else {//TODO 未登录
            questionbankUnloginhome.setVisibility(View.VISIBLE);//零元体验题库
            questionbankLoginhome.setVisibility(View.GONE);//真正题库隐藏
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_questionbank;
    }

    @Override
    protected void initView(View view) {
        FragmentActivity activity = getActivity();
        if (activity instanceof MainActivity) {
            context = (MainActivity) activity;
        }

        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) statusbarView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = StatusBarUtil.getStatusBarHeight(context);
        statusbarView.setLayoutParams(layoutParams);
    }

    @Override
    protected void initData() {
        projectList = new ArrayList<>();
        //注册网络
        questionBankHomePresenter = new QuestionBankHomePresenter(this);
    }

    /**
     * 界面跳转判断登陆状态
     */
    public void startToActivity(Bundle bundle, Class<?> cls) {
        if (loginStatus) {
            if (projectList != null && projectList.size() > 0) {
                Intent intent = new Intent(getActivity(), cls);
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                startActivity(intent);
            } else {
                ToastUtil.showBottomShortText(context, getResources().getString(R.string.course_bugBank));
            }
        } else {
            startActivity(new Intent(context, LoginActivity.class));
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

                if (projectList != null && projectList.size() > 0) {//TODO  已购买过的科目
                    currentSubjectId = sharedPreferencesUtils.getInt(Constant.SUBJECT_ID, 0);

                    if (projectList.size() == 1) {//TODO  只购买了一个科目
                        currentSubjectId = projectList.get(0).getId();//当前选中的科目ID
                        sharedPreferencesUtils.putInt(Constant.SUBJECT_ID, currentSubjectId);//存放当前的科目ID

                        titlebarMidtitle.setText(projectList.get(0).getName());
                        titlebarMidimage.setVisibility(View.GONE);
                        questionBankHomePresenter.getSubjectInfo(userId, currentSubjectId);//获取对应的科目的信息
                    } else {//TODO  购买了多个科目
                        if (currentSubjectId != 0) {//TODO 本地已存储上次的科目
                            for (int i = 0; i < projectList.size(); i++) {
                                if (currentSubjectId == projectList.get(i).getId()) {
                                    titlebarMidtitle.setText(projectList.get(i).getName());
                                    break;
                                }
                            }
                            questionBankHomePresenter.getSubjectInfo(userId, currentSubjectId);
                            titlebarMidimage.setVisibility(View.VISIBLE);
                        } else {//TODO 未存储上次的科目
                            currentSubjectId = projectList.get(0).getId();//默认选中第一个
                            sharedPreferencesUtils.putInt(Constant.SUBJECT_ID, currentSubjectId);//存放当前的科目ID
                            titlebarMidtitle.setText(projectList.get(0).getName());
                            titlebarMidimage.setVisibility(View.VISIBLE);

                            questionBankHomePresenter.getSubjectInfo(userId, currentSubjectId);
                        }
                    }
                }
                questionbankUnloginhome.setVisibility(View.GONE);//零元体验题库
                questionbankLoginhome.setVisibility(View.VISIBLE);//真正题库隐藏
            } else {//TODO  未购买科目
                questionbankUnloginhome.setVisibility(View.VISIBLE);//零元体验题库
                questionbankLoginhome.setVisibility(View.GONE);//真正题库隐藏
                titlebarMidtitle.setText(getResources().getString(R.string.question_default));

                projectList.clear();
                sharedPreferencesUtils.remove(Constant.SUBJECT_ID);
            }
        } else {//TODO  未购买科目
            questionbankUnloginhome.setVisibility(View.VISIBLE);//零元体验题库
            questionbankLoginhome.setVisibility(View.GONE);//真正题库隐藏
            titlebarMidimage.setVisibility(View.GONE);//下拉箭头隐藏
            titlebarMidtitle.setText(getResources().getString(R.string.question_default));

            projectList.clear();
            sharedPreferencesUtils.remove(Constant.SUBJECT_ID);
        }
    }

    @Override
    public void getSubjectInfoBean(SubjectInfoBean data) {
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

    @OnClick({R.id.question_assessment, R.id.question_errorscenter, R.id.question_record, R.id.question_atonceExp,
            R.id.question_Knowledge_exercise, R.id.question_stage_test, R.id.question_elaboration_test,
            R.id.question_hight_errors, R.id.question_group_exam, R.id.question_writes_really, R.id.titlebar_midimage, R.id.titlebar_midtitle})
    public void onViewClicked(View view) {
        //防止过快点击
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > 1000) {
            lastClickTime = currentTime;
            if (sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false)) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.COURSE_ID, currentSubjectId);
                switch (view.getId()) {
                    case R.id.question_assessment://TODO 能力评估
                        bundle.putString(Constant.WEB_URL, ApiStores.QUESTION_AbilityTOAssess + "?userId=" + userId + "&course_id=" + currentSubjectId);
                        bundle.putString(Constant.WEB_TITLE, getResources().getString(R.string.question_title_assessment));
                        startToActivity(bundle, WebActivity.class);
                        break;
                    case R.id.question_errorscenter://TODO 错题中心
                        startToActivity(bundle, ErrorCenterActivity.class);
                        break;
                    case R.id.question_record://TODO 答题记录
                        startToActivity(bundle, QuestionsOnRecordActivity.class);
                        break;
                    case R.id.question_atonceExp://TODO 0元体验题库
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_0);
                        bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_E);
                        startActivity(TESTMODEActivity.class, bundle);
                        break;
                    case R.id.question_Knowledge_exercise://TODO 知识点练习
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_1);
                        startToActivity(bundle, KnowledgePracticeActivity.class);
                        break;
                    case R.id.question_stage_test://TODO 阶段测试
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_2);
                        startToActivity(bundle, StageOfTestingActivity.class);
                        break;
                    case R.id.question_elaboration_test://TODO 论述题自测
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_3);
                        startToActivity(bundle, StageOfTestingActivity.class);
                        break;
                    case R.id.question_hight_errors://TODO 高频错题
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_4);
                        startToActivity(bundle, HighFrequencyWrongTopicActivity.class);
                        break;
                    case R.id.question_group_exam://TODO 组卷模考
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_6);
                        startToActivity(bundle, StageOfTestingActivity.class);
                        break;
                    case R.id.question_writes_really://TODO  自助练习
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_5);
                        startToActivity(bundle, SelfServiceActivity.class);
                        break;
                    case R.id.titlebar_midtitle:
                    case R.id.titlebar_midimage://TODO 选择专业题库
                        subjectWindow();
                        break;
                    default:
                        break;
                }
            } else {//未登录,去登录页
                startActivity(new Intent(context, LoginActivity.class));
            }
        }
    }

    /**
     * Description:QuestionBankFragment
     * Time:2019-4-26   下午 3:15
     * Detail: 学科弹出框
     */
    @SuppressLint("InflateParams")
    private void subjectWindow() {
        if (projectList.size() > 1) {
            View mContentView = null;
            if (mContentView == null) {
                mContentView = LayoutInflater.from(getActivity()).inflate(R.layout.popuwindow_subject, null, false);
            }
            if (popupWindow == null) {
                popupWindow = new PopupWindow(mContentView);
                popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.icon_test9));
                popupWindow.setOutsideTouchable(false);
                popupWindow.setFocusable(true);
            }
            int[] a = new int[2];
            titlebarToolbar.getLocationInWindow(a);
            popupWindow.showAtLocation(titlebarToolbar, Gravity.TOP | Gravity.CENTER_HORIZONTAL, a[0], titlebarToolbar.getHeight() + a[1] / 3);

            ListView recyclerView = mContentView.findViewById(R.id.subject_recyclerview);
            if (subjectAdapter == null) {
                subjectAdapter = new SubjectAdapter(getActivity(), projectList);
            } else {
                subjectAdapter.notifyDataSetChanged();
            }
            recyclerView.setAdapter(subjectAdapter);
            recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    currentSubjectId = projectList.get(position).getId();//当前选中的科目ID
                    sharedPreferencesUtils.putInt(Constant.SUBJECT_ID, currentSubjectId);//存放当前的科目ID
                    questionBankHomePresenter.getSubjectInfo(userId, projectList.get(position).getId());

                    titlebarMidtitle.setText(projectList.get(position).getName());//设置选中的标题
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                }
            });
            if (popupWindow != null) {
                if (popupWindow.isShowing()) {
                    titlebarMidimage.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.icon_qb_indicatortop));
                }
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        titlebarMidimage.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.icon_qb_indicatorbot));
                    }
                });
            }
        }
    }

    public void toastInfo(CharSequence text) {
        ToastUtil.showBottomShortText(getActivity(), text);
    }
}
