package com.ucfo.youcaiwx.module.questionbank.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;
import com.tencent.bugly.crashreport.CrashReport;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.questionbank.QuestionAnswerSheetAdapter;
import com.ucfo.youcaiwx.adapter.questionbank.QuestionItemAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.questionbank.DoProblemsAnswerBean;
import com.ucfo.youcaiwx.entity.questionbank.DoProblemsBean;
import com.ucfo.youcaiwx.entity.questionbank.ErrorCenterSubmitAnswerBean;
import com.ucfo.youcaiwx.entity.questionbank.QuestionDeleteBean;
import com.ucfo.youcaiwx.entity.questionbank.SubmitStatusResultBean;
import com.ucfo.youcaiwx.module.course.player.utils.TimeFormater;
import com.ucfo.youcaiwx.module.main.activity.MainActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.questionbank.QuestionBankExercisePresenter;
import com.ucfo.youcaiwx.presenter.view.questionbank.IQuestionBankDoExerciseView;
import com.ucfo.youcaiwx.utils.ActivityUtil;
import com.ucfo.youcaiwx.utils.LogUtils;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.time.CountDownTimerSupport;
import com.ucfo.youcaiwx.utils.time.OnCountDownTimerListener;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.customview.NestedGridView;
import com.ucfo.youcaiwx.widget.dialog.PauseExamDialog;
import com.ucfo.youcaiwx.widget.dialog.TestModeIconDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-5-6 下午 4:08
 * FileName: TESTMODEActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 工程模式,做题主界面
 * Detail:=_=都已经乱套了,就酱紫吧
 */
public class TESTMODEActivity extends BaseActivity implements IQuestionBankDoExerciseView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.tv_headerTitle)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_Time)
    TextView tvTime;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.btn_collection)
    TextView btnCollection;
    @BindView(R.id.btn_answersheet)
    TextView btnAnswersheet;
    @BindView(R.id.btn_pause)
    TextView btnPause;
    @BindView(R.id.btn_query)
    TextView btnQuery;
    @BindView(R.id.btn_submit)
    RoundTextView btnSubmit;
    @BindView(R.id.linear_submit)
    LinearLayout btnSubmitLinear;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.linear_bottom_function)
    LinearLayout linearBottomFunction;
    @BindView(R.id.showline)
    View showline;

    public ArrayList<DoProblemsBean.DataBean.TopicsBean> questionList;//TODO 所有题目数据集(从接口获取)
    public ArrayList<DoProblemsAnswerBean> optionsAnswerList;//TODO 所有题目数据集,主要用于答题卡和用户答题操作

    /*****************************************************TODO start 倒计时  **************************/
    private int millisecond = 1000;//TODO 一千毫秒,计时器基本单位
    private int resultCountDownTime = 0;//TODO 最终倒计时用时时间
    private int CountdownTime = 0;//TODO 总倒计时时间 单位:seconds
    private long CountDownInterval = 1 * millisecond;//TODO 倒计时时间间隔(默认1秒 单位:seconds )
    private CountDownTimerSupport countDownTimer;
    /*****************************************************TODO end 倒计时  **************************/

    /*****************************************************TODO start 正计时  **************************/
    private Handler timer = new Handler();
    private int timerSeconds = 0;//TODO 正计时时间 单位:seconds
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            timerSeconds++;
            tvTime.setText(TimeFormater.formatSeconds(timerSeconds));
            timer.postDelayed(this, CountDownInterval);
        }
    };
    /*****************************************************TODO end 正计时  *************************/
    private TESTMODEActivity testModeActivity;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private Bundle bundle;
    private boolean timeState = false, complete = true, isDone, discuss_analysis = false;//登录状态,是否全部作答,默认全部作答,是否做题用于退出保存判断,论述题解析
    private Drawable unCollectionImage, collectionImage;//收藏,未收藏图片资源
    private QuestionBankExercisePresenter questionBankExercisePresenter;
    private BottomSheetDialog answerSheetDialog;//答题卡弹窗
    private String EXERCISE_TYPE, question_title, question_content;//练习模式,试卷名称,交卷的json串:用于错题中心错题解析
    private int timingType = 1;//TODO 计时类型,1为正计时,-1为倒计时: 默认为正计时
    private int user_id, plate_id, course_id, mock_id, section_id, paper_type = 1, paper_id, continue_plate;//TODO 用户ID,板块ID,专业ID,组卷ID,章ID,卷子类型,试卷ID,继续做题板块
    private int knob_id, num, submitStatus = 1, analysisType, id;//TODO 知识点ID ,节,知识点做题数,交卷状态:1正常交卷,2退出保存,解析模式的类型,答题记录的试卷id
    private QuestionAnswerSheetAdapter answerSheetAdapter;
    private String loadingTips = null;
    private QuestionItemAdapter questionItemAdapter;
    private String know_id, question_id;
    /**
     * 做题翻页(别问,问就是广播大法)
     */
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(Constant.BroadcastReceiver_TONEXT, intent.getAction())) {
                //跳转至下一页
                jumpToNextPage();
            } else if (TextUtils.equals(Constant.BroadcastReceiver_TOPAGE, intent.getAction())) {
                int index = intent.getIntExtra(Constant.INDEX, 0);
                //跳转至指定页
                jumpToAppointPage(index);
            }
        }
    };
    private String videoName;

    @Override
    protected void onRestart() {
        super.onRestart();
        //TODO 由停止状态变为运行状态之前调用，也就是Activity被重新启动了
        resumeCounter();//恢复计时
    }

    @Override
    protected void onPause() {
        super.onPause();
        //TODO 系统准备去启动或者恢复另一个Activity的时候调用
        stopCounter();//停止计时
    }

    @Override
    protected void onDestroy() {
        //广播注销
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
        ActivityUtil.getInstance().removeActivity(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_testmode;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);

        ActivityUtil.getInstance().addActivity(this);

        testModeActivity = TESTMODEActivity.this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(testModeActivity);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);//用户id

        collectionImage = ContextCompat.getDrawable(this, R.mipmap.icon_qb_collection);
        if (collectionImage != null) {
            collectionImage.setBounds(0, 0, collectionImage.getMinimumWidth(), collectionImage.getMinimumHeight());
        }
        unCollectionImage = ContextCompat.getDrawable(this, R.mipmap.icon_qb_uncollection);
        if (unCollectionImage != null) {
            unCollectionImage.setBounds(0, 0, unCollectionImage.getMinimumWidth(), unCollectionImage.getMinimumHeight());
        }

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);//TODO 禁止屏幕截图

        // 上报后的Crash会显示该标签
        CrashReport.setUserSceneTag(this, Constant.BUGLY_TAG_EXERCISE);
    }

    @Override
    protected void initData() {
        super.initData();
        questionList = new ArrayList<DoProblemsBean.DataBean.TopicsBean>();//题目数据,此集合只用于创建item,显示题目信息
        optionsAnswerList = new ArrayList<DoProblemsAnswerBean>();//答题卡数据,此集合用于创建答题卡,做题处理,交卷处理

        //注册网络
        questionBankExercisePresenter = new QuestionBankExercisePresenter(this);

        //获取参数信息
        bundle = getIntent().getExtras();
        if (bundle != null) {
            EXERCISE_TYPE = bundle.getString(Constant.EXERCISE_TYPE, "");//TODO 做题模式,必传,用于区分考试模式,练习模式,或者斯巴达模式或者其他模式
            plate_id = bundle.getInt(Constant.PLATE_ID, 101);//TODO 板块ID(区分类别)
            continue_plate = bundle.getInt(Constant.CONTINUE_PLATE, 101);//TODO 继续做题板块ID
            mock_id = bundle.getInt(Constant.MOCK_ID, 0);//TODO 组卷模考ID
            course_id = bundle.getInt(Constant.COURSE_ID, 0);//TODO 专业ID
            section_id = bundle.getInt(Constant.SECTION_ID, 0);//TODO 章ID
            paper_id = bundle.getInt(Constant.PAPER_ID, 0);//TODO 试卷ID,交卷完毕之后会生成一个paper_id用于查询成绩和错题解析使用
            id = bundle.getInt(Constant.ID, 0);//TODO 试卷ID,交卷完毕之后会生成一个paper_id用于查询成绩和错题解析使用
            know_id = bundle.getString(Constant.KNOW_ID, "0");//TODO 知识点ID,多个知识点ID的字符串数组
            videoName = bundle.getString(Constant.VIDEO_NAME, getResources().getString(R.string.default_title));//视频名
            question_id = bundle.getString(Constant.QUESTION_ID, "0");//TODO 题目ID

            num = bundle.getInt(Constant.NUM, 0);//TODO 知识点做题数
            knob_id = bundle.getInt(Constant.KNOB_ID, 0);//TODO 节ID
            analysisType = bundle.getInt(Constant.ANALYSIS_TYPE, 0);//TODO 错题解析类型:1错题解析2全部解析
            question_content = bundle.getString(Constant.QUESTION_CONTENT, "");//TODO 错题解析需要的交卷时传输的json串,由于是交完卷子后传递的,所以已经UTF-8编码通过,用于错题中心错题解析时不再需要编码


            LogUtils.e("Bundle------: " + bundle);
            //TODO 设置做题类型
            questionBankExercisePresenter.setEXERCISE_TYPE(EXERCISE_TYPE);
            questionBankExercisePresenter.setContext(this);

            //获取试题开始作答
            loadNetData();
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
        //重试事件监听
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNetData();
            }
        });
    }

    /**
     * 正儿八经的数据依赖后
     */
    private void loadNetData() {
        if (questionBankExercisePresenter == null) {
            questionBankExercisePresenter = new QuestionBankExercisePresenter(this);
            questionBankExercisePresenter.setEXERCISE_TYPE(EXERCISE_TYPE);
            questionBankExercisePresenter.setContext(this);
        }
        //TODO 获取相应版块的试卷
        switch (plate_id) {
            case Constant.PLATE_0://TODO 0元体验
                if (analysisType == 1) {
                    titlebarMidtitle.setText(getResources().getString(R.string.question_title_errorAnalysis));
                    questionBankExercisePresenter.getFreeExperineceAnalysis(user_id, analysisType, question_content);
                } else if (analysisType == 2) {
                    titlebarMidtitle.setText(getResources().getString(R.string.question_title_allAnalysis));
                    questionBankExercisePresenter.getFreeExperineceAnalysis(user_id, analysisType, question_content);
                } else {
                    questionBankExercisePresenter.getFreeExperinece(user_id);
                    titlebarMidtitle.setText(getResources().getString(R.string.question_default));
                }
                break;
            case Constant.PLATE_1://TODO 知识点练习(考试模式和练习模式)
                paper_type = 1;
                questionBankExercisePresenter.getKnowledgePractice(course_id, user_id, plate_id, paper_type, section_id, knob_id, know_id, num);
                titlebarMidtitle.setText(getResources().getString(R.string.question_title_Knowledge_exercise));
                break;
            case Constant.PLATE_2://TODO 阶段测试
                paper_type = 1;
                questionBankExercisePresenter.getStageOfTesting(course_id, user_id, plate_id, paper_type, paper_id);
                titlebarMidtitle.setText(getResources().getString(R.string.question_title_stage_test));
                break;
            case Constant.PLATE_3://TODO 论述题自测
                paper_type = 2;
                questionBankExercisePresenter.getDissCussData(course_id, user_id, plate_id, paper_type, paper_id);
                titlebarMidtitle.setText(getResources().getString(R.string.question_title_elaboration_test));
                break;
            case Constant.PLATE_4://TODO 系统高频高频错题
                paper_type = 1;
                questionBankExercisePresenter.getQuestionHightErrors(course_id, user_id, plate_id, paper_type, section_id, know_id, String.valueOf(knob_id));
                titlebarMidtitle.setText(getResources().getString(R.string.question_title_question_hight_errors));
                break;
            case Constant.PLATE_5://TODO 自助练习
                LogUtils.e("know_id------: " + know_id.trim());
                paper_type = 1;
                questionBankExercisePresenter.getSelfHelpPractice(course_id, user_id, plate_id, paper_type, section_id, know_id.trim(), String.valueOf(knob_id), num);
                titlebarMidtitle.setText(getResources().getString(R.string.question_title_writes_really));
                break;
            case Constant.PLATE_6://TODO 组卷模考
                paper_type = 1;
                questionBankExercisePresenter.getGroupExamProblemsData(course_id, plate_id, paper_type, mock_id, user_id);
                titlebarMidtitle.setText(getResources().getString(R.string.question_title_group_exam));
                break;
            case Constant.PLATE_7://TODO 错题中心   直接查看错题
                questionBankExercisePresenter.getErrorCenterCheckAnalysis(course_id, user_id, section_id, know_id);
                titlebarMidtitle.setText(getResources().getString(R.string.question_title_question_my_errors));
                break;
            case Constant.PLATE_8://TODO 错题中心  重新做题
                questionBankExercisePresenter.getErrorCenterReform(course_id, user_id, section_id, String.valueOf(knob_id), know_id);
                titlebarMidtitle.setText(getResources().getString(R.string.question_title_question_my_errors));
                break;
            case Constant.PLATE_9://TODO 6大板块错题解析(错题解析和全部解析)
                questionBankExercisePresenter.getErrorAnalysis(paper_id, user_id, analysisType);
                if (analysisType == 1) {//1:错题解析2:全部解析
                    titlebarMidtitle.setText(getResources().getString(R.string.question_title_errorAnalysis));
                } else {
                    titlebarMidtitle.setText(getResources().getString(R.string.question_title_allAnalysis));
                }
                break;
            case Constant.PLATE_10://TODO 错题中心错题解析(错题解析和全部解析)
                questionBankExercisePresenter.getErrorCenterAnalysis(user_id, course_id, section_id, know_id, analysisType, question_content);
                if (analysisType == 1) {//1:错题解析2:全部解析
                    titlebarMidtitle.setText(getResources().getString(R.string.question_title_errorAnalysis));
                } else {
                    titlebarMidtitle.setText(getResources().getString(R.string.question_title_allAnalysis));
                }
                break;
            case Constant.PLATE_11://TODO 答题记录继续做题
                questionBankExercisePresenter.getContinueExamData(user_id, id);
                titlebarMidtitle.setText(getResources().getString(R.string.question_tips_holder6));
                break;
            case Constant.PLATE_12://TODO 答题记录论述题查看解析
                questionBankExercisePresenter.getContinueDiscussAnalysis(user_id, paper_id);
                titlebarMidtitle.setText(getResources().getString(R.string.question_tips_holder7));
                break;
            case Constant.PLATE_13://TODO 我的收藏查看收藏
                questionBankExercisePresenter.getMineCollectionList(user_id, course_id, section_id, knob_id, know_id);
                titlebarMidtitle.setText(getResources().getString(R.string.mine_Collection));
                break;
            case Constant.PLATE_14://TODO 学习中心
                questionBankExercisePresenter.getLearnPlanExerciseList(user_id, know_id, videoName);
                break;
            case Constant.PLATE_15://TODO 学习中心试题解析
                questionBankExercisePresenter.getLearnPlanExerciseAnalysis(user_id, paper_id);
                titlebarMidtitle.setText(getResources().getString(R.string.question_title_allAnalysis));
                break;
            case Constant.PLATE_16://TODO 查看试题
                questionBankExercisePresenter.getQuestionDetailed(user_id, question_id);
                titlebarMidtitle.setText(getResources().getString(R.string.question_title_details));
                break;
            default:
                break;
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
        showline.setVisibility(View.GONE);
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackPressListener();
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            BackPressListener();
        }
        return false;
    }

    //TODO 初始化倒计时控件
    private void initCountDownTimer() {
        countDownTimer = new CountDownTimerSupport(CountdownTime, CountDownInterval);
        countDownTimer.setOnCountDownTimerListener(new OnCountDownTimerListener() {
            @Override
            public void onTick(long millisUntilFinished) {
                int millis = Integer.parseInt(String.valueOf(millisUntilFinished / millisecond));//总的剩余时间
                int countDownTimemillis = Integer.parseInt(String.valueOf(CountdownTime / millisecond));//倒计时总时长
                resultCountDownTime = countDownTimemillis - millis;//统计倒计时时间

                tvTime.setText(TimeFormater.formatSeconds(millis));
            }

            @Override
            public void onFinish() {
                countDownTimer.reset();
                tvTime.setText(getResources().getString(R.string.time_format));
                ToastUtil.showBottomLongText(testModeActivity, getResources().getString(R.string.question_tips_Itstimeto));
                submitPaper();
            }
        });
    }

    /**
     * TODO 初次开始计时
     */
    public void startCounter(int type, int startTime) {
        switch (type) {
            case -1://倒计时
                CountdownTime = startTime * millisecond;
                initCountDownTimer();
                if (countDownTimer != null) {
                    countDownTimer.start();
                }
                break;
            case 1://正计时
                timerSeconds = startTime;
                if (timer != null) {
                    timer.postDelayed(runnable, CountDownInterval);
                }
                break;
            default:
                break;
        }
    }

    /**
     * TODO 暂停计时
     */
    public void stopCounter() {
        switch (timingType) {
            case -1://倒计时
                if (countDownTimer != null) {
                    countDownTimer.pause();
                }
                break;
            case 1://正计时
                if (timer != null) {
                    timer.removeCallbacks(runnable);
                }
                break;
            default:
                break;
        }
    }

    /**
     * //TODO 重新开始计时
     */
    public void resumeCounter() {
        if (TextUtils.equals(EXERCISE_TYPE, Constant.EXERCISE_A) || discuss_analysis) {
            //todo nothing
        } else {
            switch (timingType) {
                case -1:
                    //倒计时
                    if (countDownTimer != null) {
                        countDownTimer.resume();
                    }
                    break;
                case 1:
                    //正计时
                    if (timer != null) {
                        timer.postDelayed(runnable, CountDownInterval);
                    }
                    break;
                default:
                    if (timer != null) {
                        timer.postDelayed(runnable, CountDownInterval);
                    }
                    break;
            }
        }
    }

    /**
     * TODO 获取计时器时间计量
     */
    public int getTimeMillis() {
        if (timingType == -1) {//倒计时返回总计用时时间
            return resultCountDownTime;
        } else {
            return timerSeconds;//正计时总时
        }
    }

    /**
     * TODO 跳转至下一个题目做题
     */
    public void jumpToNextPage() {
        int currentItem = viewPager.getCurrentItem();
        currentItem = currentItem + 1;
        jumpToAppointPage(currentItem);
    }

    // TODO 跳转至指定页
    public void jumpToAppointPage(int index) {
        viewPager.setCurrentItem(index);
    }

    /**
     * Description:TESTMODEActivity
     * Time:2019-5-21 上午 10:28
     * Detail:交卷事件(钉~~,交钱成功)
     */
    public void submitPaper() {
        loadingTips = getResources().getString(R.string.question_tips_submitting);//进度框提示文字

        //TODO step1: 状态
        submitStatus = 1;//1:交卷  2:退出保存

        //TODO step2:遍历做的题目集合，是否存在未做的题目，是 ：跳出循环
        int size = optionsAnswerList.size();
        for (int i = 0; i < size; i++) {
            String userAnswer = optionsAnswerList.get(i).getUser_answer();
            if (TextUtils.isEmpty(userAnswer)) {//有一道题答案为空,就标记为未完成状态
                complete = false;//显示为未作答状态
                break;
            }
        }
        //TODO step3: 获取做题时间
        int countDownTimemillis = Integer.parseInt(String.valueOf(CountdownTime / millisecond));//倒计时总时长

        //TODO  step4: 判断做题完成状态
        if (complete) {//TODO 已全部做完
            switch (plate_id) {
                case Constant.PLATE_0://TODO 0元体验交卷
                    freeExpercienceSubmit();
                    break;
                case Constant.PLATE_8://todo 错题中心-重新做题交卷
                    questionBankExercisePresenter.errorCenterSubmit(optionsAnswerList, user_id, course_id);
                    break;
                case Constant.PLATE_11://todo 答题记录-交卷
                    questionBankExercisePresenter.contimueSubmitPapers(course_id, id, user_id, continue_plate, submitStatus, section_id,
                            knob_id, know_id, paper_id, mock_id, getTimeMillis(), countDownTimemillis, optionsAnswerList);
                    break;
                case Constant.PLATE_14://TODO 学习中心交卷
                    questionBankExercisePresenter.learnCenterSubmitPapers(course_id, user_id, submitStatus, getTimeMillis(), optionsAnswerList, videoName, know_id);
                    break;
                default://todo 6大板块-交卷
                    questionBankExercisePresenter.submitPapers(course_id, user_id, plate_id, submitStatus, section_id,
                            knob_id, know_id, paper_id, mock_id, getTimeMillis(), countDownTimemillis, optionsAnswerList);
                    break;
            }
        } else {//TODO 题目未全部作答
            /**
             * 组卷模考模式下直接交卷,不显示交卷弹框
             */
            if (plate_id == Constant.PLATE_6 || continue_plate == Constant.PLATE_6) {
                if (plate_id == Constant.PLATE_11) {
                    //6大板块继续做题,需要传递继续做题获取的板块
                    questionBankExercisePresenter.submitPapers(course_id, user_id, continue_plate, submitStatus, section_id,
                            knob_id, know_id, paper_id, mock_id, getTimeMillis(), countDownTimemillis, optionsAnswerList);
                } else {
                    questionBankExercisePresenter.submitPapers(course_id, user_id, plate_id, submitStatus, section_id,
                            knob_id, know_id, paper_id, mock_id, getTimeMillis(), countDownTimemillis, optionsAnswerList);
                }
            } else {
                /**
                 * 非组卷模考需提示未做完信息
                 */
                TestModeIconDialog testModeIconDialog = new TestModeIconDialog(testModeActivity).builder();
                testModeIconDialog.setIcon(R.mipmap.icon_qb_submit);
                testModeIconDialog.setCancelable(false);
                testModeIconDialog.setCanceledOnTouchOutside(false);
                testModeIconDialog.setMsg(getResources().getString(R.string.question_tips_unfinished));
                testModeIconDialog.setNegativeButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        testModeIconDialog.dismiss();
                    }
                });
                testModeIconDialog.setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (plate_id) {
                            case Constant.PLATE_0://TODO 0元体验交卷
                                freeExpercienceSubmit();
                                break;
                            case Constant.PLATE_8://todo 错题中心-重新做题交卷
                                questionBankExercisePresenter.errorCenterSubmit(optionsAnswerList, user_id, course_id);
                                break;
                            case Constant.PLATE_11://todo 答题记录-各大板块-交卷
                                /*questionBankExercisePresenter.submitPapers(course_id, user_id, continue_plate, submitStatus, section_id,knob_id, know_id, paper_id, mock_id, getTimeMillis(), countDownTimemillis, optionsAnswerList);*/
                                questionBankExercisePresenter.contimueSubmitPapers(course_id, id, user_id, continue_plate, submitStatus, section_id,
                                        knob_id, know_id, paper_id, mock_id, getTimeMillis(), countDownTimemillis, optionsAnswerList);
                                break;
                            case Constant.PLATE_14://TODO 学习中心交卷
                                questionBankExercisePresenter.learnCenterSubmitPapers(course_id, user_id, submitStatus, getTimeMillis(), optionsAnswerList, videoName, know_id);
                                break;
                            default://todo 6大板块交卷
                                questionBankExercisePresenter.submitPapers(course_id, user_id, plate_id, submitStatus, section_id,
                                        knob_id, know_id, paper_id, mock_id, getTimeMillis(), countDownTimemillis, optionsAnswerList);
                                break;
                        }
                    }
                });
                testModeIconDialog.show();
            }
        }
    }

    /**
     * TODO 0元体验交卷
     */
    private void freeExpercienceSubmit() {
        ArrayList<ErrorCenterSubmitAnswerBean> answerList = new ArrayList<>();
        int size = optionsAnswerList.size();
        for (int i = 0; i < size; i++) {
            DoProblemsAnswerBean bean = optionsAnswerList.get(i);
            String questionId = bean.getQuestion_id();
            String trueOptions = bean.getTrue_options();
            String userAnswer = bean.getUser_answer();
            answerList.add(new ErrorCenterSubmitAnswerBean(questionId, trueOptions, userAnswer));
        }
        String json = new Gson().toJson(answerList);
        String questionContent = "";
        try {
            String str = new String(json.getBytes(), Constant.UTF_8);
            questionContent = URLEncoder.encode(str, Constant.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.USED_TIME, getTimeMillis());
        bundle.putString(Constant.QUESTION_CONTENT, questionContent);
        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_0);
        startActivity(ResultsStatisticalActivity.class, bundle);
        finish();
    }

    /**
     * TODO 返回键监听事件(顶部返回按钮和功能区返回)
     */
    private void BackPressListener() {
        loadingTips = getResources().getString(R.string.question_tips_saveing);//进度框提示文字

        //TODO step1:判断做题模式和板块
        if (TextUtils.equals(EXERCISE_TYPE, Constant.EXERCISE_A)) {
            //todo 解析模式直接退出
            finish();//解析模式直接返回上一级
            return;
        }
        if (plate_id == Constant.PLATE_8 || plate_id == Constant.PLATE_0
                || plate_id == Constant.PLATE_14 || plate_id == Constant.PLATE_16) {
            //todo 错题中心的做题模式,0元体验,学习计划,试题查看 直接退出
            finish();//返回上一级
            return;
        }
        if (getDiscussAnalysis()) {//todo 论述题解析模式直接退出
            finish();
            return;
        }

        submitStatus = 2;//1:交卷  2:退出保存

        //TODO step2:遍历做的题目集合，是否存在未做的题目,全部答案为空则直接退出做题页,有的话就显示提示框
        int size = optionsAnswerList.size();
        for (int i = 0; i < size; i++) {
            String userAnswer = optionsAnswerList.get(i).getUser_answer();
            if (!TextUtils.isEmpty(userAnswer)) {//用户已作答
                isDone = true;//是否做完题用于退出保存判断
                break;
            }
        }
        //TODO step3: 获取做题时间
        int countDownTimemillis = Integer.parseInt(String.valueOf(CountdownTime / millisecond));//倒计时总时长

        //TODO step4: 判断做题完成状态
        if (isDone) {//做了一部分题目
            TestModeIconDialog testModeIconDialog = new TestModeIconDialog(this).builder();
            testModeIconDialog.setIcon(R.mipmap.icon_qb_save);
            testModeIconDialog.setCancelable(false);
            testModeIconDialog.setCanceledOnTouchOutside(false);
            testModeIconDialog.setMsg(getResources().getString(R.string.question_tips_savepager));
            testModeIconDialog.setNegativeButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    testModeIconDialog.dismiss();
                    finish();
                }
            });
            testModeIconDialog.setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (plate_id == Constant.PLATE_11) {//todo 答题记录机组做题退出保存
                        questionBankExercisePresenter.contimueSubmitPapers(course_id, id, user_id, continue_plate, submitStatus, section_id,
                                knob_id, know_id, paper_id, mock_id, getTimeMillis(), countDownTimemillis, optionsAnswerList);
                    } else {//todo 6大板块退出保存
                        questionBankExercisePresenter.submitPapers(course_id, user_id, plate_id, submitStatus, section_id,
                                knob_id, know_id, paper_id, mock_id, getTimeMillis(), countDownTimemillis, optionsAnswerList);
                    }
                }
            });
            testModeIconDialog.show();
        } else {//一道未作,懒到极致
            finish();
        }
    }

    /**
     * TODO 删除当前错题
     */
    public void deleteCurrentQuestion() {
        String id = questionList.get(viewPager.getCurrentItem()).getID();
        int parseInt = Integer.parseInt(id);
        questionBankExercisePresenter.deleteProblems(course_id, user_id, parseInt);
    }

    /**
     * TODO 取消收藏当前题目
     */
    public void cancelCurrentQuestion() {
        int currentItem = viewPager.getCurrentItem();//当前题目所在位置坐标==题目在list的坐标
        int questionId = Integer.parseInt(questionList.get(currentItem).getID());
        int collection = Integer.parseInt(questionList.get(currentItem).getCollection());//当前题目收藏状态
        //请求收藏接口,更改收藏状态
        loadingTips = getResources().getString(R.string.net_loadingtext);
        questionBankExercisePresenter.setProblemsCollection(user_id, course_id, questionId, collection);
    }

    /**
     * Description:TESTMODEActivity
     * Time:2019-5-16 下午 5:59
     * Detail:TODO  获取板块题目
     */
    @Override
    public void getProblemsList(DoProblemsBean bean) {
        if (bean != null) {
            if (bean.getData() != null) {
                DoProblemsBean.DataBean beanData = bean.getData();
                if (beanData.getTopics() != null && beanData.getTopics().size() > 0) {
                    DoProblemsBean.DataBean data = bean.getData();

                    doExerciseFunction(data);//TODO 做题逻辑处理

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
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
    }

    /**
     * Description:TESTMODEActivity
     * Time:2019-5-27 上午 11:43
     * Detail:TODO 收藏接口回调
     */
    @Override
    public void collectionResult(int status) {
        switch (status) {
            case 1://状态1 操作成功
                if (plate_id == Constant.PLATE_13) {//
                    removeQuestion();
                } else {
                    int currentItem = viewPager.getCurrentItem();
                    int collection = Integer.parseInt(questionList.get(currentItem).getCollection());//当前题目收藏状态
                    if (collection == 0) {//未收藏
                        questionList.get(currentItem).setCollection(String.valueOf(1));//设置为收藏
                        ToastUtil.showBottomShortText(testModeActivity, getResources().getString(R.string.question_tips_collection1));
                    } else if (collection == 1) {//已收藏
                        questionList.get(currentItem).setCollection(String.valueOf(0));//设置未取消收藏
                        ToastUtil.showBottomShortText(testModeActivity, getResources().getString(R.string.question_tips_collection0));
                    }
                    collection = Integer.parseInt(questionList.get(currentItem).getCollection());
                    setCollectionIcon(collection);//更改收藏图标
                }
                break;
            case 2://状态2 操作失败
                ToastUtil.showBottomShortText(testModeActivity, getResources().getString(R.string.operation_Error));
                break;
            default:
                break;
        }
    }

    /**
     * Description:TESTMODEActivity
     * Time:2019-5-23 下午 5:13
     * Detail:TODO 交卷状态回调
     */
    @Override
    public void submitStatus(SubmitStatusResultBean bean) {
        if (bean != null) {
            int state = bean.getData().getState();
            String paperId = bean.getData().getPaper_id();//获取此试卷的ID用于查询成绩
            if (state == 1) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.COURSE_ID, course_id);
                switch (submitStatus) {
                    case 1://交卷操作
                        if (TextUtils.equals(EXERCISE_TYPE, Constant.EXERCISE_D)) {//论述题交卷模式
                            //不跳页,直接隐藏计时器,打开论述题解析内容(因为论述题并没有成绩统计和错题这一说,走解析接口也是多走一步)
                            discussAnalysis();
                        } else {
                            if (plate_id == Constant.PLATE_14) {//学习中心交卷
                                bundle.putInt(Constant.PAPER_ID, Integer.parseInt(paperId));
                                bundle.putInt(Constant.PLATE_ID, Constant.PLATE_14);
                                bundle.putInt(Constant.COURSE_ID, course_id);
                                startActivity(ResultsStatisticalActivity.class, bundle);
                                finish();
                            } else {//其他版块交卷
                                if (!TextUtils.isEmpty(paperId)) {
                                    bundle.putInt(Constant.PAPER_ID, Integer.parseInt(paperId));
                                } else {//为空传输默认值
                                    bundle.putInt(Constant.PAPER_ID, 0);
                                }
                                startActivity(ResultsStatisticalActivity.class, bundle);
                                finish();
                            }
                        }
                        break;
                    case 2://退出保存操作
                        ToastUtil.showBottomShortText(testModeActivity, getResources().getString(R.string.question_tips_hasSaved));
                        if (plate_id == Constant.PLATE_5) {//TODO 自助练习模式
                            /*ActivityUtil.getInstance().finishActivity(SelfServiceChildListActivity.class);
                            ActivityUtil.getInstance().finishActivity(SelfServiceListActivity.class);
                            ActivityUtil.getInstance().finishActivity(SelfServiceActivity.class);
                            finish();*/
                            Intent intent = new Intent(this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra(Constant.INDEX, 2);
                            startActivity(intent);
                            finish();
                        } else {
                            finish();
                        }
                        break;
                    default:
                        finish();
                        break;
                }
            } else {
                ToastUtil.showBottomShortText(testModeActivity, getResources().getString(R.string.operation_Error));
            }
        } else {
            ToastUtil.showBottomShortText(testModeActivity, getResources().getString(R.string.operation_Error));
        }
    }

    //TODO 错题中心重新做题交卷
    @Override
    public void errorCenterSubmitStatus(int status) {
        switch (status) {
            case 0://交卷失败
                ToastUtil.showBottomShortText(testModeActivity, getResources().getString(R.string.operation_Error));
                break;
            case 1://交卷成功
                ArrayList<ErrorCenterSubmitAnswerBean> answerList = new ArrayList<>();
                int size = optionsAnswerList.size();
                for (int i = 0; i < size; i++) {
                    DoProblemsAnswerBean bean = optionsAnswerList.get(i);
                    String question_id = bean.getQuestion_id();
                    String true_options = bean.getTrue_options();
                    String user_answer = bean.getUser_answer();
                    answerList.add(new ErrorCenterSubmitAnswerBean(question_id, true_options, user_answer));
                }
                String json = new Gson().toJson(answerList);

                String questionContent = "";
                try {
                    String str = new String(json.getBytes(), Constant.UTF_8);
                    questionContent = URLEncoder.encode(str, Constant.UTF_8);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.USED_TIME, getTimeMillis());
                bundle.putInt(Constant.COURSE_ID, course_id);
                bundle.putInt(Constant.SECTION_ID, section_id);
                bundle.putString(Constant.QUESTION_CONTENT, questionContent);
                bundle.putInt(Constant.PLATE_ID, plate_id);
                bundle.putString(Constant.KNOW_ID, know_id);
                startActivity(ResultsStatisticalActivity.class, bundle);
                finish();
                break;
            default:
                break;
        }
    }

    //TODO 题目删除状态
    @Override
    public void deleteResult(QuestionDeleteBean data) {
        if (data != null) {
            QuestionDeleteBean.DataBean data1 = data.getData();
            if (data1 != null) {
                int state = data1.getState();
                switch (state) {//TODO 删除状态1成功0失败
                    case 0:
                        ToastUtil.showBottomShortText(testModeActivity, getResources().getString(R.string.operation_Error));
                        break;
                    case 1:
                        removeQuestion();
                        break;
                    default:
                        break;
                }
            } else {
                ToastUtil.showBottomShortText(testModeActivity, getResources().getString(R.string.operation_Error));
            }
        } else {
            ToastUtil.showBottomShortText(testModeActivity, getResources().getString(R.string.operation_Error));
        }
    }

    //TODO 从当前页面中一处错题
    private void removeQuestion() {
        //todo step1: 获取当前页面的坐标
        int currentIndex = viewPager.getCurrentItem();
        //todo step2: 删除当前题目刷新ui，包括左上角题目数
        questionList.remove(currentIndex);//删除本地数据
        //todo step3: 在更新adapter的内容后，应该调用一下adapter的notifyDataSetChanged方法，否则在ADT22以上使用会报这个错
        questionItemAdapter = new QuestionItemAdapter(getSupportFragmentManager(), EXERCISE_TYPE, questionList, plate_id);
        questionItemAdapter.notifyDataSetChanged();
        viewPager.setAdapter(questionItemAdapter);
        //todo step4: 如果不是在第一页,删除后跳转至上一页
        if (currentIndex > 0) {
            currentIndex--;
        }
        //todo step5: 回到上一页
        if (currentIndex >= 0) {
            if (questionList.size() != 0) {
                jumpToAppointPage(currentIndex);
            } else {
                finish();
            }
        }
    }

    //TODO 论述题解析
    private void discussAnalysis() {
        discuss_analysis = true;

        stopCounter();

        tvTime.setText("");

        btnQuery.setVisibility(btnQuery.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);//答疑显示
        btnSubmitLinear.setVisibility(btnSubmitLinear.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);//交卷按钮挂掉
        btnPause.setVisibility(btnPause.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);//暂停按钮挂掉

        //todo step3: 在更新adapter的内容后，应该调用一下adapter的notifyDataSetChanged方法，否则在ADT22以上使用会报这个错
        questionItemAdapter = new QuestionItemAdapter(getSupportFragmentManager(), EXERCISE_TYPE, questionList, plate_id);
        questionItemAdapter.notifyDataSetChanged();
        viewPager.setAdapter(questionItemAdapter);
        //todo step4: 不走解析接口,直接打开已有的解析
        jumpToAppointPage(0);
    }

    /**
     * Description:TESTMODEActivity
     * Time:2019-5-17 上午 9:24
     * Detail:TODO 做题页做题功能处理
     */
    private void doExerciseFunction(DoProblemsBean.DataBean dataBean) {
        List<DoProblemsBean.DataBean.TopicsBean> topics = dataBean.getTopics();

        String title = dataBean.getTitle();
        String answerTime = dataBean.getAnswer_time();
        //TODO step1:设置标题,强转开始时间为int型
        int startTime = 0;
        if (!TextUtils.isEmpty(answerTime)) {
            startTime = Integer.parseInt(answerTime);
        }
        if (!TextUtils.isEmpty(title)) {
            //试卷名称
            tvHeaderTitle.setText(title);
        }

        //TODO step2:清空List,添加新的数据
        questionList.clear();
        questionList.addAll(topics);//TODO 存储获取的题目信息

        //TODO step3:清空数据集,创建答题卡数据
        initAnswerSheet();

        //TODO step4:开始倒计时
        if (TextUtils.equals(EXERCISE_TYPE, Constant.EXERCISE_A)) {//解析模式关闭所有计时器
            //TODO nothing
        } else {
            if (plate_id == Constant.PLATE_6 || continue_plate == Constant.PLATE_6) {//只有组卷模考为倒计时
                timingType = -1;
                startCounter(timingType, startTime);
            } else {//其余模块均为正计时
                timingType = 1;
                if (plate_id == Constant.PLATE_11) {//继续做题
                    startCounter(timingType, startTime);
                } else {//其他模块做题
                    startCounter(timingType, 0);
                }
            }
        }
        //TODO step5:设置底部功能区
        setAnalyesStyle();

        //TODO step6: 设置适配器以及答题页逻辑操作处理
        setCollectionIcon(Integer.parseInt(questionList.get(0).getCollection()));//设置收藏状态

        //我屮艸芔茻,viewpager2这个时候刚出来,修改了好多viewpager的bug,纠结用哪个,但是官方给的远程依赖还处于alpha阶段,BUG是肯定存在的了,替代VP1是早晚的
        questionItemAdapter = new QuestionItemAdapter(getSupportFragmentManager(), EXERCISE_TYPE, questionList, plate_id);
        questionItemAdapter.notifyDataSetChanged();
        viewPager.setAdapter(questionItemAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //滑动页面时调用，有三个参数 var1当前页面、var2偏移比例、var3滑动像素
            }

            @Override
            public void onPageSelected(int position) {
                //进入一个新的页面调用这个方法，var1当前位于哪个页面。

                setCollectionIcon(Integer.parseInt(questionList.get(position).getCollection()));//设置收藏状态
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //滑动状态更改时调用 var1：有三个状态，1按下时调用，抬起时如果发生了滑动值会变为2（不发生滑动不会有2），滑动结束时变为0
            }
        });

        //TODO step7: 注册广播,开始做题
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BroadcastReceiver_TONEXT);
        filter.addAction(Constant.BroadcastReceiver_TOPAGE);
        lbm.registerReceiver(mMessageReceiver, filter);

        switch (plate_id) {
            case Constant.PLATE_11:
                //todo 答题记录继续做题跳转至最后一道做过的题目位置
                int currentIndex = 0;
                int size = optionsAnswerList.size();
                for (int i = 0; i < size; i++) {
                    String userAnswer = optionsAnswerList.get(i).getUser_answer();
                    if (!TextUtils.isEmpty(userAnswer)) {
                        //如果为空，那就跳
                        currentIndex = i;
                    }
                }
                jumpToAppointPage(currentIndex);
                break;
            case Constant.PLATE_12:
            case Constant.PLATE_16:
                //TODO 直接打开解析模式
                discussAnalysis();
                break;
            case Constant.PLATE_13:
                discuss_analysis = true;
                break;
            default:
                break;
        }


    }

    /**
     * Description:TESTMODEActivity
     * Time:2019-5-20 下午 6:50
     * Detail:TODO 创建答题卡
     */
    private void initAnswerSheet() {
        if (TextUtils.equals(EXERCISE_TYPE, Constant.EXERCISE_D)) {
            //论述题答题卡

            int size = questionList.size();
            for (int i = 0; i < size; i++) {
                DoProblemsBean.DataBean.TopicsBean bean = questionList.get(i);
                //题目ID
                String id = bean.getID();
                //正确答案
                String right = bean.getOptions().get(0).getRight();
                String discussUseranswer = questionList.get(i).getDiscuss_useranswer();

                DoProblemsAnswerBean doProblemsAnswerBean = new DoProblemsAnswerBean();
                //题目ID
                doProblemsAnswerBean.setQuestion_id(id);
                //题目索引
                doProblemsAnswerBean.setPosition(i);
                //正确答案
                doProblemsAnswerBean.setTrue_options(right);
                //用户答案
                doProblemsAnswerBean.setUser_answer(discussUseranswer);
                optionsAnswerList.add(doProblemsAnswerBean);
            }
        } else {
            //选择题答题卡

            int size = questionList.size();
            for (int i = 0; i < size; i++) {
                DoProblemsBean.DataBean.TopicsBean bean = questionList.get(i);
                //题目ID
                String id = bean.getID();
                //TODO 正确答案(正确答案放在了四个选项里,所以随便取一个吧),用户答案也一样的(老的数据结构是放在选项外层的)
                String right = bean.getOptions().get(0).getRight();
                String userOption = bean.getOptions().get(0).getUserOption();

                DoProblemsAnswerBean doProblemsAnswerBean = new DoProblemsAnswerBean();
                //题目ID
                doProblemsAnswerBean.setQuestion_id(id);
                //题目索引
                doProblemsAnswerBean.setPosition(i);
                //正确答案
                doProblemsAnswerBean.setTrue_options(right);
                //用户答案
                doProblemsAnswerBean.setUser_answer(userOption);
                optionsAnswerList.add(doProblemsAnswerBean);
            }
        }
    }

    /**
     * Description:TESTMODEActivity
     * Time:2019-5-20 下午 6:50
     * Detail:TODO 设置底部功能区
     */
    private void setAnalyesStyle() {
        if (TextUtils.equals(EXERCISE_TYPE, Constant.EXERCISE_A)) {
            //TODO 解析模式
            if (plate_id == Constant.PLATE_7 || plate_id == Constant.PLATE_0 ||
                    plate_id == Constant.PLATE_13 || plate_id == Constant.PLATE_16) {
                //错题中心查看错题,0元体验,查看收藏,试题详情直接隐藏底部功能区
                linearBottomFunction.setVisibility(btnQuery.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
            } else {//普通解析模式
                btnQuery.setVisibility(btnQuery.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);//答疑显示
                btnSubmitLinear.setVisibility(btnSubmitLinear.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);//交卷按钮挂掉
                btnPause.setVisibility(btnPause.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);//暂停按钮挂掉
            }
        } else {
            //TODO 非解析模式
            if (plate_id == Constant.PLATE_0) {
                //0元体验模式
                btnCollection.setVisibility(btnCollection.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);//收藏按钮挂掉
                btnQuery.setVisibility(btnQuery.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);//答疑按钮挂掉
                btnSubmitLinear.setVisibility(btnSubmitLinear.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);//交卷按钮显示
            } else {//TODO 普通考试模式
                btnQuery.setVisibility(btnQuery.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);//答疑按钮挂掉
                btnSubmitLinear.setVisibility(btnSubmitLinear.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);//交卷按钮显示
            }
        }
    }


    /**
     * Description:TESTMODEActivity
     * Time:2019-5-23 上午 11:22
     * Detail:TODO 根据收藏状态更改按钮状态,收藏状态更改题目的数据,而不是答题卡的数据
     */
    private void setCollectionIcon(int i) {
        switch (i) {
            case 0://未收藏
                btnCollection.setCompoundDrawables(null, unCollectionImage, null, null);
                break;
            case 1://已收藏
                btnCollection.setCompoundDrawables(null, collectionImage, null, null);
                break;
            default:
                btnCollection.setCompoundDrawables(null, unCollectionImage, null, null);
                break;
        }
    }

    /**
     * Description:TESTMODEActivity
     * Time:2019-5-27 上午 10:37
     * Detail:TODO 题目收藏按钮功能
     */
    private void funcionCollection() {
        int currentItem = viewPager.getCurrentItem();//当前题目所在位置坐标==题目在list的坐标
        int question_id = Integer.parseInt(questionList.get(currentItem).getID());//question_id
        int collection = Integer.parseInt(questionList.get(currentItem).getCollection());//当前题目收藏状态
        //请求收藏接口,更改收藏状态
        loadingTips = getResources().getString(R.string.net_loadingtext);
        questionBankExercisePresenter.setProblemsCollection(user_id, course_id, question_id, collection);
    }


    @OnClick({R.id.btn_collection, R.id.btn_answersheet, R.id.btn_pause, R.id.btn_query, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_collection:
                //TODO 收藏
                funcionCollection();
                break;
            case R.id.btn_answersheet:
                //TODO 答题卡
                answerSheetDialog();
                break;
            case R.id.btn_pause:
                //TODO 暂停
                stopCounter();
                new PauseExamDialog(testModeActivity).builder()
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .setPauseButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                resumeCounter();
                            }
                        }).show();
                break;
            case R.id.btn_query:
                //TODO 答疑
                Bundle bundle = new Bundle();
                String question_id = optionsAnswerList.get(viewPager.getCurrentItem()).getQuestion_id();
                if (!TextUtils.isEmpty(question_id)) {
                    bundle.putInt(Constant.QUESTION_ID, Integer.parseInt(question_id));
                    bundle.putInt(Constant.COURSE_ID, course_id);
                    startActivity(QuestionAnswerActivity.class, bundle);
                }
                break;
            case R.id.btn_submit:
                //TODO 交卷
                submitPaper();
                break;
            default:
                break;
        }
    }


    /**
     * Description:TESTMODEActivity
     * Time:2019-5-20 上午 9:43
     * Detail:TODO 底部答题卡操作区
     */
    private ImageButton closeBtn;
    private NestedGridView gridView;
    private Button submitBtn;

    public void answerSheetDialog() {
        if (answerSheetDialog != null) {
            answerSheetDialog.show();
        } else {
            answerSheetDialog = new BottomSheetDialog(this);
            View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_question_answersheet, null, false);
            answerSheetDialog.setContentView(inflate);
            answerSheetDialog.setCanceledOnTouchOutside(true);
            answerSheetDialog.setCancelable(true);
            answerSheetDialog.show();
            //设置透明背景
            answerSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
            //给你安排的明明白白的
            closeBtn = inflate.findViewById(R.id.answer_closebtn);
            gridView = inflate.findViewById(R.id.listView);
            submitBtn = inflate.findViewById(R.id.answer_submit);
        }
        if (TextUtils.equals(EXERCISE_TYPE, Constant.EXERCISE_A) || getDiscussAnalysis()) {
            //解析模式挂掉交卷按钮
            submitBtn.setVisibility(View.GONE);
        }
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerSheetDialog.dismiss();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPaper();
                answerSheetDialog.dismiss();
            }
        });
        //设置数据源,更新适配器
        if (answerSheetAdapter == null) {
            answerSheetAdapter = new QuestionAnswerSheetAdapter(this, optionsAnswerList, EXERCISE_TYPE);
        }
        answerSheetAdapter.notifyDataSetChanged();
        /**
         * 一切为了UI
         */
        switch (optionsAnswerList.size()) {
            case 1:
                gridView.setNumColumns(1);
                break;
            case 2:
                gridView.setNumColumns(2);
                break;
            case 3:
                gridView.setNumColumns(3);
                break;
            case 4:
                gridView.setNumColumns(4);
                break;
            default:
                gridView.setNumColumns(5);
                break;
        }
        gridView.setAdapter(answerSheetAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jumpToAppointPage(optionsAnswerList.get(position).getPosition());
                if (answerSheetDialog != null) {
                    answerSheetDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void showLoading() {
        setProcessLoading(loadingTips, true);
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

    //TODO********************************************** 供外部调用数据

    public ArrayList<DoProblemsBean.DataBean.TopicsBean> getQuestionList() {
        if (questionList == null) {
            return new ArrayList<>();
        }
        return questionList;
    }

    public void setQuestionList(ArrayList<DoProblemsBean.DataBean.TopicsBean> questionList) {
        this.questionList = questionList;
    }

    public ArrayList<DoProblemsAnswerBean> getOptionsAnswerList() {
        if (optionsAnswerList == null) {
            return new ArrayList<>();
        }
        return optionsAnswerList;
    }

    public void setOptionsAnswerList(ArrayList<DoProblemsAnswerBean> optionsAnswerList) {
        this.optionsAnswerList = optionsAnswerList;
    }

    public boolean getDiscussAnalysis() {
        return discuss_analysis;
    }

}
