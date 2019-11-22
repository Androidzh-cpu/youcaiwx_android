package com.ucfo.youcaiwx.module.questionbank.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.questionbank.OptionsAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.questionbank.DoProblemsAnswerBean;
import com.ucfo.youcaiwx.entity.questionbank.DoProblemsBean;
import com.ucfo.youcaiwx.module.questionbank.activity.TESTMODEActivity;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.widget.customview.NestedListView;
import com.ucfo.youcaiwx.widget.dialog.AlertDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-5-6.  下午 5:29
 * FileName: QuestionChoiceItemFragment
 * Description:TODO  单选题
 * Detail:TODO 根据type判断考试模式和练习模式
 */
@SuppressLint("ValidFragment")
public class QuestionChoiceItemFragment extends BaseFragment implements AbsListView.OnItemClickListener, View.OnClickListener {
    ////////////////////////////////////////////////////////////////////TODO 又是一个分水岭@_@ ///////////////////////////////////////////////////////////////////////////////////////
    private int index, allIndex, plate_id;//当前索引,全部的页面数量 , 板块ID
    private String EXERCISE_TYPE;//卷子的模式
    private TextView mCurrentIndexQuestion, mCountIndexQuestion;
    private TextView mContent1Question, mContent2Question, mContent3Question;
    private ImageView mImage1Question, mImage2Question, mAnalysisImageQuestion;
    private NestedListView mOptionsListviewQuestion;
    private TextView mAnalysisTureQuestion, mAnalysisFalseQuestion, mAnalysisContentQuestion;
    private LinearLayout mAnalysisAreaQuestion;
    private TESTMODEActivity testmodeActivity;
    private ArrayList<DoProblemsBean.DataBean.TopicsBean> questionList;
    private ArrayList<DoProblemsAnswerBean> optionsAnswerList;
    private Transferee transferee;
    private LocalBroadcastManager mLocalBroadcastManager;
    private Intent intent;
    private OptionsAdapter adapter;
    private TextView mRemovequestionQuestion;
    private TextView mAnalysisErrorRateQuestion;
    private ImageView mCollectionBtn;

    @Override
    protected int setContentView() {
        return R.layout.fragment_chiocequestionitem;
    }

    public QuestionChoiceItemFragment() {
    }

    public QuestionChoiceItemFragment(int index, String type, int allIndex, int plate_id) {
        this.index = index;
        this.allIndex = allIndex;
        this.plate_id = plate_id;
        this.EXERCISE_TYPE = type;//传递的type来识别，此处接口只是选择题模型，A是考试模式，B是练习模式 D解析模式
    }


    @Override
    protected void initView(View view) {
        mCurrentIndexQuestion = (TextView) view.findViewById(R.id.question_current_index);
        mCountIndexQuestion = (TextView) view.findViewById(R.id.question_count_index);
        mContent1Question = (TextView) view.findViewById(R.id.question_content1);
        mImage1Question = (ImageView) view.findViewById(R.id.question_image1);
        mContent2Question = (TextView) view.findViewById(R.id.question_content2);
        mImage2Question = (ImageView) view.findViewById(R.id.question_image2);
        mContent3Question = (TextView) view.findViewById(R.id.question_content3);
        mOptionsListviewQuestion = (NestedListView) view.findViewById(R.id.question_optionsListview);
        mAnalysisTureQuestion = (TextView) view.findViewById(R.id.question_analysis_ture);
        mAnalysisFalseQuestion = (TextView) view.findViewById(R.id.question_analysis_false);
        mAnalysisContentQuestion = (TextView) view.findViewById(R.id.question_analysis_content);
        mAnalysisImageQuestion = (ImageView) view.findViewById(R.id.question_analysis_image);
        mAnalysisAreaQuestion = (LinearLayout) view.findViewById(R.id.question_analysis_area);
        mRemovequestionQuestion = (TextView) view.findViewById(R.id.question_removequestion);
        mRemovequestionQuestion.setOnClickListener(this);
        mAnalysisErrorRateQuestion = (TextView) view.findViewById(R.id.question_analysis_errorRate);
        mCollectionBtn = (ImageView) view.findViewById(R.id.btn_collection);
        mCollectionBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        intent = new Intent(Constant.BroadcastReceiver_TONEXT);//广播意图 答案正确自动跳转到下一页

        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity instanceof TESTMODEActivity) {
            testmodeActivity = (TESTMODEActivity) fragmentActivity;
            questionList = testmodeActivity.getQuestionList();
            optionsAnswerList = testmodeActivity.getOptionsAnswerList();
        }
        //TODO 判断移除错题和收藏
        if (plate_id == Constant.PLATE_7) {
            //错题中心查看解析
            mRemovequestionQuestion.setVisibility(View.VISIBLE);
        } else {
            mRemovequestionQuestion.setVisibility(View.GONE);
        }
        if (plate_id == Constant.PLATE_13) {
            //我的收藏查看试题
            mCollectionBtn.setVisibility(View.VISIBLE);
        } else {
            mCollectionBtn.setVisibility(View.GONE);
        }

        //TODO 图片预览初始化
        transferee = Transferee.getDefault(testmodeActivity);

        //索引值和全部页面数量
        mCurrentIndexQuestion.setText(String.valueOf(index + 1));
        mCountIndexQuestion.setText(String.valueOf(allIndex));

        //TODO 设置题干
        initTopicData();

        //TODO 选项列表
        adapter = new OptionsAdapter(questionList, index, testmodeActivity, EXERCISE_TYPE);
        adapter.notifyDataChanged(optionsAnswerList.get(index).getUser_answer());
        mOptionsListviewQuestion.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mOptionsListviewQuestion.setAdapter(adapter);

        /************************************************************** TODO 巨丑无比的分割线 **************************************************************************/
        //TODO 选项的点击事件
        if (TextUtils.equals(EXERCISE_TYPE, Constant.EXERCISE_E)) {
            //TODO 考试模式
            mAnalysisAreaQuestion.setVisibility(mAnalysisAreaQuestion.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);//关闭解析区域
            mOptionsListviewQuestion.setOnItemClickListener(this::onItemClick);
        } else if (TextUtils.equals(EXERCISE_TYPE, Constant.EXERCISE_A)) {
            //TODO 解析模式

            //设置解析内容
            initAnalysis();

            //注销点击事件
            mOptionsListviewQuestion.setOnItemClickListener(null);
            //打开解析区域
            mAnalysisAreaQuestion.setVisibility(mAnalysisAreaQuestion.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);

        } else {
            //TODO 练习模式(作对不显示答案跳转下一页,做错显示解析)

            //设置解析内容
            initAnalysis();
            //设置点击事件
            mOptionsListviewQuestion.setOnItemClickListener(this::onItemClick);
        }
        //TODO 继续做题模式下的练习模式处理
        if (plate_id == Constant.PLATE_11) {
            if (TextUtils.equals(EXERCISE_TYPE, Constant.EXERCISE_P)) {
                //设置解析内容
                initAnalysis();
                String userAnswer = optionsAnswerList.get(index).getUser_answer();//答题卡用户选项
                if (!TextUtils.isEmpty(userAnswer)) {
                    //显示解析区域
                    mAnalysisAreaQuestion.setVisibility(mAnalysisAreaQuestion.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (TextUtils.equals(EXERCISE_TYPE, Constant.EXERCISE_P)) {
            //TODO 练习模式
            String userOption = questionList.get(index).getOptions().get(position).getOption();//获取用户选择的的选项
            String rightAnswer = optionsAnswerList.get(index).getTrue_options();//获取正确答案
            String userAnswer = optionsAnswerList.get(index).getUser_answer();//答题卡用户选项

            if (TextUtils.isEmpty(userAnswer)) {
                //未答题可以进行答题操作,答完题后不需进行修改答案

                //题目刷新user答案
                adapter.notifyDataChanged(userOption);
                //重新给答题卡响应题目设置用户答案
                optionsAnswerList.get(index).setUser_answer(userOption);
                //总数据重新复制
                testmodeActivity.setOptionsAnswerList(optionsAnswerList);

                if (userOption.equals(rightAnswer)) {
                    //TODO 做对
                    //发送广播,跳转至下一页
                    mLocalBroadcastManager.sendBroadcast(intent);
                } else {
                    //TODO 做错了

                    //TODO 设置解析内容
                    initAnalysis();
                    //TODO 打开解析view
                    mAnalysisAreaQuestion.setVisibility(mAnalysisAreaQuestion.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
                }
            }
        } else {
            //TODO 考试模式
            //获取用户的选项
            String userOption = questionList.get(index).getOptions().get(position).getOption();
            adapter.notifyDataChanged(userOption);

            optionsAnswerList.get(index).setUser_answer(userOption);//重新给答题卡响应题目设置用户答案
            testmodeActivity.setOptionsAnswerList(optionsAnswerList);//总数据重新复制

            initAnalysis();//重新设置解析答案

            mLocalBroadcastManager.sendBroadcast(intent);//发送广播,跳转至下一页

            //交卷提示
            //hintSubmitPaper();
        }
    }

    /**
     * 是否交卷(艹了,自己做到哪自己不知道吗,还TM给你提示,题目上写的索引是TM干啥的)
     */
    private void hintSubmitPaper() {
        int temporary = questionList.size() - 1;
        if (index == temporary) {
            new AlertDialog(testmodeActivity).builder()
                    .setMsg(getResources().getString(R.string.question_tips_whetherSavePager))
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
                            if (testmodeActivity != null) {
                                testmodeActivity.submitPaper();
                            }
                        }
                    }).show();

        }
    }

    /**
     * Description:QuestionChoiceItemFragment
     * Time:2019-5-21 下午 2:43
     * Detail:TODO 设置解析
     */
    private void initAnalysis() {
        String analysisPic = questionList.get(index).getAnalysisPic();//解析图片
        String analysis = questionList.get(index).getAnalysis();//解析文字
        String eprone = questionList.get(index).getEprone();//错误率提示
        String rightAnswer = optionsAnswerList.get(index).getTrue_options();//正确答案
        String userOption = optionsAnswerList.get(index).getUser_answer();//用户答案

        if (!TextUtils.isEmpty(eprone)) {//错误率提示
            mAnalysisErrorRateQuestion.setText(eprone);
            mAnalysisErrorRateQuestion.setVisibility(mAnalysisErrorRateQuestion.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
        } else {
            mAnalysisErrorRateQuestion.setVisibility(mAnalysisErrorRateQuestion.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
        }

        if (!TextUtils.isEmpty(analysis)) {//解析题干
            String holder = getResources().getString(R.string.holder_analysis);

            String text = String.valueOf(holder + analysis);
            SpannableString spannableString = new SpannableString(text);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF333333"));
            /*AbsoluteSizeSpan ab = new AbsoluteSizeSpan(12, true);
            spannableString.setSpan(ab, 0, holder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, holder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //粗体
            spannableString.setSpan(colorSpan, 0, holder.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            mAnalysisContentQuestion.setText(spannableString);

        } else {
            mAnalysisContentQuestion.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(analysisPic)) {//解析图片
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.icon_default)
                    .error(R.mipmap.image_loaderror)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            GlideUtils.load(testmodeActivity, analysisPic, mAnalysisImageQuestion, requestOptions);

        } else {
            mAnalysisImageQuestion.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(rightAnswer)) {//正确答案
            mAnalysisTureQuestion.setText(rightAnswer);
        } else {
            mAnalysisTureQuestion.setText(getResources().getString(R.string.holder_nodata));
        }
        if (!TextUtils.isEmpty(userOption)) {//正确答案
            mAnalysisFalseQuestion.setText(userOption);
            if (rightAnswer.equals(userOption)) {//做对了
                mAnalysisFalseQuestion.setTextColor(ContextCompat.getColor(testmodeActivity, R.color.color_F99111));
            } else {//做错了
                mAnalysisFalseQuestion.setTextColor(ContextCompat.getColor(testmodeActivity, R.color.color_E84342));
            }
        } else {
            mAnalysisFalseQuestion.setText(getResources().getString(R.string.holder_notmake));
            mAnalysisFalseQuestion.setTextColor(ContextCompat.getColor(testmodeActivity, R.color.color_F99111));
        }

        TransferConfig config = TransferConfig.build()
                .setMissPlaceHolder(R.mipmap.icon_default)
                .setErrorPlaceHolder(R.mipmap.icon_default)
                .setProgressIndicator(new ProgressPieIndicator())
                .setIndexIndicator(new NumberIndexIndicator())
                .setJustLoadHitImage(true)
                .bindImageView(mAnalysisImageQuestion, analysisPic);
        mAnalysisImageQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferee.apply(config).show();
            }
        });

    }

    /**
     * Description:QuestionChoiceItemFragment
     * Time:2019-5-21 上午 11:55
     * Detail:TODO 设置题干内容
     */
    private void initTopicData() {
        List<String> topicList = questionList.get(index).getTopic();
        int size = topicList.size();
        switch (size) {
            case 1:
                topic1(topicList, 0);
                break;
            case 2:
                topic1(topicList, 0);
                topic2(topicList, 1);
                break;
            case 3:
                topic1(topicList, 0);
                topic2(topicList, 1);
                topic3(topicList, 2);
                break;
            case 4:
                topic1(topicList, 0);
                topic2(topicList, 1);
                topic3(topicList, 2);
                topic4(topicList, 3);
                break;
            case 5:
                topic1(topicList, 0);
                topic2(topicList, 1);
                topic3(topicList, 2);
                topic4(topicList, 3);
                topic5(topicList, 4);
                break;
            default:
                mContent1Question.setText(getResources().getString(R.string.holder_nodata));
                break;
        }
    }

    //TODO 题干文字1
    public void topic1(List<String> list, int index) {
        String topicText1 = list.get(index);
        if (!TextUtils.isEmpty(topicText1)) {
            mContent1Question.setText(topicText1);
        }
    }

    //TODO 题干图片1
    public void topic2(List<String> list, int index) {
        String topicImage1 = list.get(index);
        mImage1Question.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(topicImage1)) {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.icon_default)
                    .error(R.mipmap.image_loaderror)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            GlideUtils.load(testmodeActivity, topicImage1, mImage1Question, requestOptions);
        }
        TransferConfig config = TransferConfig.build()
                .setMissPlaceHolder(R.mipmap.icon_default)
                .setErrorPlaceHolder(R.mipmap.icon_default)
                .setProgressIndicator(new ProgressPieIndicator())
                .setIndexIndicator(new NumberIndexIndicator())
                .setJustLoadHitImage(true)
                .bindImageView(mImage1Question, topicImage1);
        mImage1Question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferee.apply(config).show();
            }
        });
    }

    //TODO 题干文字2
    public void topic3(List<String> list, int index) {
        String topicText2 = list.get(index);
        mContent2Question.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(topicText2)) {
            mContent2Question.setText(topicText2);
        }
    }

    //TODO 题干图片2
    public void topic4(List<String> list, int index) {
        String topicImage2 = list.get(index);
        mImage2Question.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(topicImage2)) {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.icon_default)
                    .error(R.mipmap.image_loaderror)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            GlideUtils.load(testmodeActivity, topicImage2, mImage2Question, requestOptions);
        }
        TransferConfig config = TransferConfig.build()
                .setMissPlaceHolder(R.mipmap.icon_default)
                .setErrorPlaceHolder(R.mipmap.icon_default)
                .setProgressIndicator(new ProgressPieIndicator())
                .setIndexIndicator(new NumberIndexIndicator())
                .setJustLoadHitImage(true)
                .bindImageView(mImage2Question, topicImage2);
        mImage2Question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferee.apply(config).show();
            }
        });
    }

    //TODO 题干文字3
    public void topic5(List<String> list, int index) {
        String topicText3 = list.get(index);
        mContent3Question.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(topicText3)) {
            mContent3Question.setText(topicText3);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.question_removequestion:
                //TODO 19/05/28 移除当前错题
                testmodeActivity.deleteCurrentQuestion();
                break;
            case R.id.btn_collection:
                //TODO 19/05/28 取消收藏==移除当前错题
                testmodeActivity.cancelCurrentQuestion();
                break;
            default:
                break;
        }
    }
}
