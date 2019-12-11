package com.ucfo.youcaiwx.module.questionbank.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.questionbank.ResultReportSheetAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.questionbank.ResultReportBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.questionbank.QuestionResultReportPresenter;
import com.ucfo.youcaiwx.presenter.view.questionbank.IQuestionResultReportView;
import com.ucfo.youcaiwx.utils.ConvertUtil;
import com.ucfo.youcaiwx.utils.LogUtils;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.module.course.player.utils.TimeFormater;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.customview.NestedGridView;
import com.ucfo.youcaiwx.widget.multiwave.MultiWaveHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-5-22 上午 9:31
 * FileName: ResultsStatisticalActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 成绩统计页
 */
public class ResultsStatisticalActivity extends BaseActivity implements IQuestionResultReportView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.question_errorAnalysis)
    Button questionErrorAnalysis;
    @BindView(R.id.question_allAnalysis)
    Button questionAllAnalysis;
    @BindView(R.id.resultreport_title)
    TextView resultreportTitle;
    @BindView(R.id.resultreport_time)
    TextView resultreportTime;
    @BindView(R.id.resultreport_accuracy_percent)
    TextView resultreportAccuracyPercent;
    @BindView(R.id.waveHeader)
    MultiWaveHeader waveHeader;
    @BindView(R.id.resultreport_rightnum)
    TextView resultreportRightnum;
    @BindView(R.id.resultreport_notmakenum)
    TextView resultreportNotmakenum;
    @BindView(R.id.resultreport_wrongnum)
    TextView resultreportWrongnum;
    @BindView(R.id.resultreport_havemakednum)
    TextView resultreportHavemakednum;
    @BindView(R.id.resultreport_entirenum)
    TextView resultreportEntirednum;
    @BindView(R.id.listView)
    NestedGridView listView;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    private Bundle bundle;
    private ResultsStatisticalActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int user_id = 0, paper_id = 0, course_id = 0;
    private QuestionResultReportPresenter questionResultReportPresenter;
    private ArrayList<ResultReportBean.DataBean.QuestionContentBean> list;
    private ResultReportSheetAdapter sheetAdapter;
    private int used_time = 0, section_id = 0, plate_id = 0, analysis_type;
    private String question_content, know_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_results_statistical;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

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
        titlebarMidtitle.setText(getResources().getString(R.string.question_title_ResultsStatistical));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

        //注册网络
        questionResultReportPresenter = new QuestionResultReportPresenter(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            //TODO 6大板块成绩统计
            paper_id = bundle.getInt(Constant.PAPER_ID, 0);//试卷ID
            course_id = bundle.getInt(Constant.COURSE_ID, 0);//专业ID
            //TODO 错题中心成绩统计
            used_time = bundle.getInt(Constant.USED_TIME, 0);//用时时间
            section_id = bundle.getInt(Constant.SECTION_ID, 0);//章
            plate_id = bundle.getInt(Constant.PLATE_ID, 101);//板块
            know_id = bundle.getString(Constant.KNOW_ID, "0");//知识点
            question_content = bundle.getString(Constant.QUESTION_CONTENT, "");//交卷的JSON串

            switch (plate_id) {
                case Constant.PLATE_0://0元体验
                    questionResultReportPresenter.getFreeExperienceResultReportData(used_time, user_id, question_content);
                    break;
                case Constant.PLATE_8://错题中心重新做题成绩统计
                    questionResultReportPresenter.getErrorCenterResultReportData(used_time, course_id, section_id, question_content);
                    break;
                case Constant.PLATE_14://学习中心
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_A);
                    bundle.putInt(Constant.PAPER_ID, paper_id);
                    bundle.putInt(Constant.PLATE_ID, Constant.PLATE_15);
                    bundle.putInt(Constant.COURSE_ID, course_id);
                    startActivity(TESTMODEActivity.class, bundle);
                    finish();
                    break;
                default:
                    questionResultReportPresenter.getResultReportData(user_id, paper_id);
                    break;
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
        LogUtils.e("成绩统计页------bundle: " + bundle);
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (plate_id) {
                    case Constant.PLATE_0://0元体验
                        questionResultReportPresenter.getFreeExperienceResultReportData(used_time, user_id, question_content);
                        break;
                    case Constant.PLATE_8://错题中心重新做题成绩统计
                        questionResultReportPresenter.getErrorCenterResultReportData(used_time, course_id, section_id, question_content);
                        break;
                    default:
                        questionResultReportPresenter.getResultReportData(user_id, paper_id);
                        break;
                }
            }
        });
    }

    @OnClick({R.id.question_errorAnalysis, R.id.question_allAnalysis})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_A);//解析模式模式
        bundle.putInt(Constant.COURSE_ID, course_id);//专业ID
        switch (view.getId()) {
            case R.id.question_errorAnalysis://TODO 错题解析
                analysis_type = 1;
                switch (plate_id) {
                    case Constant.PLATE_0://0元体验
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_0);
                        bundle.putInt(Constant.ANALYSIS_TYPE, analysis_type);//解析类型
                        bundle.putString(Constant.QUESTION_CONTENT, question_content);
                        startActivity(TESTMODEActivity.class, bundle);
                        break;
                    case Constant.PLATE_8://错题中心解析
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_10);//错题中心错题解析
                        bundle.putInt(Constant.ANALYSIS_TYPE, analysis_type);//解析类型
                        bundle.putString(Constant.KNOW_ID, know_id);
                        bundle.putInt(Constant.SECTION_ID, section_id);
                        bundle.putString(Constant.QUESTION_CONTENT, question_content);
                        startActivity(TESTMODEActivity.class, bundle);
                        break;
                    default://6大板块解析
                        bundle.putInt(Constant.PAPER_ID, paper_id);
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_9);
                        bundle.putInt(Constant.ANALYSIS_TYPE, analysis_type);//1:错题解析2:全部解析
                        startActivity(TESTMODEActivity.class, bundle);
                        break;
                }
                break;
            case R.id.question_allAnalysis://TODO 全部解析
                analysis_type = 2;
                switch (plate_id) {
                    case Constant.PLATE_0://0元体验
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_0);
                        bundle.putInt(Constant.ANALYSIS_TYPE, analysis_type);//解析类型
                        bundle.putString(Constant.QUESTION_CONTENT, question_content);
                        startActivity(TESTMODEActivity.class, bundle);
                        break;
                    case Constant.PLATE_8://错题中心解析
                        bundle.putInt(Constant.SECTION_ID, section_id);//章
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_10);//错题中心错题解析
                        bundle.putString(Constant.KNOW_ID, know_id);//知识点ID
                        bundle.putString(Constant.QUESTION_CONTENT, question_content);//知识点ID
                        bundle.putInt(Constant.ANALYSIS_TYPE, analysis_type);//解析类型
                        startActivity(TESTMODEActivity.class, bundle);
                        break;
                    default://6大板块解析
                        bundle.putInt(Constant.PAPER_ID, paper_id);
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_9);
                        bundle.putInt(Constant.ANALYSIS_TYPE, analysis_type);
                        startActivity(TESTMODEActivity.class, bundle);
                        break;
                }
                break;
        }
    }

    @Override
    public void getResultReportData(ResultReportBean data) {
        if (data != null) {
            if (data.getData() != null) {
                ResultReportBean.DataBean bean = data.getData();

                initInfo(bean);

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
     * Description:ResultsStatisticalActivity
     * Time:2019-5-24 下午 3:09
     * Detail:TODO 设置详细信息
     */
    private void initInfo(ResultReportBean.DataBean bean) {
        list.clear();

        String accuracy = bean.getAccuracy();//正确率
        String paper_name = bean.getPaper_name();//试卷名
        int false_num = bean.getFalse_num();//错误数量
        int not_num = bean.getNot_num();//未做数量
        int true_num = bean.getTrue_num();//正确题目数量
        int used_time = bean.getUsed_time();//用时时间
        int question_num = bean.getQuestion_num();//题目总数
        int as_num = bean.getAs_num();//已做题数量
        String formatSeconds = TimeFormater.formatSeconds(used_time);

        resultreportRightnum.setText(String.valueOf(true_num));//TODO 正确题目数量
        resultreportNotmakenum.setText(String.valueOf(not_num));//TODO 未做题目数量
        resultreportWrongnum.setText(String.valueOf(false_num));//TODO 错题数量
        resultreportHavemakednum.setText(String.valueOf(as_num));//TODO 已做
        resultreportEntirednum.setText(String.valueOf(question_num));//TODO 总数
        String holder = getResources().getString(R.string.holder_usedtime);
        String text = String.valueOf(holder + formatSeconds);
        resultreportTime.setText(text);//TODO 用时时间
        if (!TextUtils.isEmpty(paper_name)) {
            resultreportTitle.setText(paper_name);//TODO 试卷名
        }
        float accuracyPercent;
        if (!TextUtils.isEmpty(accuracy)) {//题目正确率
            accuracyPercent = ConvertUtil.convertToFloat(accuracy, 0);
        } else {
            accuracyPercent = 0;
        }
        int b = Math.round(accuracyPercent * 100);//采用round方式转换为整型
        String percent1 = String.valueOf(b);
        String percent2 = "%";
        String percent3 = percent1 + percent2;
        SpannableString spannableString1 = new SpannableString(percent3);
        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor("#FF333333"));
        AbsoluteSizeSpan ab31 = new AbsoluteSizeSpan(31, true);
        AbsoluteSizeSpan ab15 = new AbsoluteSizeSpan(17, true);
        spannableString1.setSpan(colorSpan1, 0, percent3.length() - 0, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString1.setSpan(ab31, 0, percent1.length() - 0, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(ab15, percent1.length() - 0, percent3.length() - 0, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        resultreportAccuracyPercent.setText(spannableString1);//TODO 设置进度百分比
        resultreportAccuracyPercent.bringToFront();// 设置水波纹文字显示百分比
        waveHeader.setProgress(accuracyPercent);//TODO 设置水波纹进度

        List<ResultReportBean.DataBean.QuestionContentBean> question_contents = bean.getQuestion_content();
        if (question_contents != null) {
            list.addAll(question_contents);

            if (sheetAdapter != null) {
                sheetAdapter.notifyDataSetChanged();
            } else {
                sheetAdapter = new ResultReportSheetAdapter(this, list);
            }
            listView.setAdapter(sheetAdapter);
        }
    }

    @Override
    public void showLoading() {
        loadinglayout.showLoading();
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
