package com.ucfo.youcaiwx.module.questionbank.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.ucfo.youcaiwx.widget.dialog.TestModeIconDialog;

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
    private TextView mAnalysisContent2Question;
    private ImageView mAnalysisImage2Question;

    private QuestionChoiceListener questionChoiceListener;

    public interface QuestionChoiceListener {
        ArrayList<DoProblemsBean.DataBean.TopicsBean> choiceGetQuestionList();

        ArrayList<DoProblemsAnswerBean> choiceGetOptionsAnswerList();

        int choiceGetContimuePlateID();

        void choiceSetOptionsAnswerList(ArrayList<DoProblemsAnswerBean> optionsAnswerList);

        void choiceSubmitPaper();

        void choiceDeleteCurrentQuestion();

        void choiceCancelCurrentQuestion();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof QuestionChoiceListener) {
            questionChoiceListener = (QuestionChoiceListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement QuestionChoiceListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        questionChoiceListener = null;
    }

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

        //题干部分
        mContent1Question = (TextView) view.findViewById(R.id.question_content1);
        mImage1Question = (ImageView) view.findViewById(R.id.question_image1);
        mContent2Question = (TextView) view.findViewById(R.id.question_content2);
        mImage2Question = (ImageView) view.findViewById(R.id.question_image2);
        mContent3Question = (TextView) view.findViewById(R.id.question_content3);

        //题目选项
        mOptionsListviewQuestion = (NestedListView) view.findViewById(R.id.question_optionsListview);

        //正确错误答案
        mAnalysisTureQuestion = (TextView) view.findViewById(R.id.question_analysis_ture);
        mAnalysisFalseQuestion = (TextView) view.findViewById(R.id.question_analysis_false);

        //解析-----> 文字,图片,文字,图片
        mAnalysisContentQuestion = (TextView) view.findViewById(R.id.question_analysis_content);
        mAnalysisImageQuestion = (ImageView) view.findViewById(R.id.question_analysis_image);
        mAnalysisContent2Question = (TextView) view.findViewById(R.id.question_analysis_content2);
        mAnalysisImage2Question = (ImageView) view.findViewById(R.id.question_analysis_image2);

        //解析区域
        mAnalysisAreaQuestion = (LinearLayout) view.findViewById(R.id.question_analysis_area);

        //移除当前题目按钮
        mRemovequestionQuestion = (TextView) view.findViewById(R.id.question_removequestion);
        mRemovequestionQuestion.setOnClickListener(this);

        //改题目错误率
        mAnalysisErrorRateQuestion = (TextView) view.findViewById(R.id.question_analysis_errorRate);

        //取消收藏按钮
        mCollectionBtn = (ImageView) view.findViewById(R.id.btn_collection);
        mCollectionBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        //广播意图 答案正确自动跳转到下一页
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        intent = new Intent(Constant.BroadcastReceiver_TONEXT);

        if (questionChoiceListener != null) {
            questionList = questionChoiceListener.choiceGetQuestionList();
            optionsAnswerList = questionChoiceListener.choiceGetOptionsAnswerList();
        } else {
            if (getActivity() != null) {
                FragmentActivity fragmentActivity = getActivity();
                if (fragmentActivity instanceof TESTMODEActivity) {
                    testmodeActivity = (TESTMODEActivity) fragmentActivity;
                }
                questionList = testmodeActivity.getQuestionList();
                optionsAnswerList = testmodeActivity.getOptionsAnswerList();
            }
        }

        //TODO 判断移除错题和收藏
        if (plate_id == Constant.PLATE_17) {
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
        transferee = Transferee.getDefault(getContext());

        //索引值和全部页面数量
        int currentIndex = index + 1;
        mCurrentIndexQuestion.setText(String.valueOf(currentIndex));
        mCountIndexQuestion.setText(String.valueOf(allIndex));

        //TODO 设置题干
        initTopicData();

        //TODO 选项列表
        if (questionList != null && questionList.size() > 0) {
            adapter = new OptionsAdapter(questionList, index, getContext(), EXERCISE_TYPE);
        }
        if (optionsAnswerList != null && optionsAnswerList.size() > 0) {
            //此处报了个错,又是下标越界
            adapter.notifyDataChanged(optionsAnswerList.get(index).getUser_answer());
        }
        mOptionsListviewQuestion.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mOptionsListviewQuestion.setAdapter(adapter);

        /************************************************************** TODO 巨丑无比的分割线 **************************************************************************/
        //TODO 选项的点击事件处理,不同模式不同处理
        if (TextUtils.equals(EXERCISE_TYPE, Constant.EXERCISE_E)) {
            //TODO 考试模式

            mAnalysisAreaQuestion.setVisibility(View.GONE);//关闭解析区域
            mOptionsListviewQuestion.setOnItemClickListener(this);
        } else if (TextUtils.equals(EXERCISE_TYPE, Constant.EXERCISE_A)) {
            //TODO 解析模式

            //设置解析内容
            initAnalysis();

            //注销点击事件
            mOptionsListviewQuestion.setOnItemClickListener(null);

            //打开解析区域
            mAnalysisAreaQuestion.setVisibility(View.VISIBLE);

        } else {
            //TODO 练习模式(作对不显示答案跳转下一页,做错显示解析)

            //设置解析内容
            initAnalysis();

            //一个调皮的吐司
            //ToastUtil.showBottomShortText(getContext(),"不好意思,就算是继续做题,你还是得走练习模式" );

            //由于viewpager至能缓存三个fragment,所以要判断一下是否填写过答案然后再对解析内容进行显示
            if (optionsAnswerList != null && optionsAnswerList.size() > 0) {
                String userAnswer = optionsAnswerList.get(index).getUser_answer();//答题卡用户选项
                String rightAnswer = optionsAnswerList.get(index).getTrue_options();//获取正确答案
                if (!TextUtils.isEmpty(userAnswer)) {
                    //TODO 用户已作答
                    if (TextUtils.equals(userAnswer, rightAnswer)) {
                        //牛皮,就这智商还能作对
                        //mAnalysisAreaQuestion.setVisibility(View.GONE);//原定是做对不再显示解析的,后来LD让改的,那就改
                        mAnalysisAreaQuestion.setVisibility(View.VISIBLE);
                    } else {
                        //诶,对,这才是正常水平嘛
                        mAnalysisAreaQuestion.setVisibility(View.VISIBLE);
                    }
                } else {
                    //TODO 用户未作答
                    mAnalysisAreaQuestion.setVisibility(View.GONE);
                }
            }

            //设置点击事件
            mOptionsListviewQuestion.setOnItemClickListener(this);
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
                questionChoiceListener.choiceSetOptionsAnswerList(optionsAnswerList);

                if (TextUtils.equals(userOption, rightAnswer)) {
                    //做对(别问我为啥练习模式做对了也显示解析,问领导去...)

                    /**LD让根据学生需求改成做对显示解析,sb***/
                    //设置解析内容
                    initAnalysis();
                    //打开解析view
                    mAnalysisAreaQuestion.setVisibility(mAnalysisAreaQuestion.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
                    /**LD让根据学生需求改成做对显示解析,sb***/

                    //发送广播,跳转至下一页
                    mLocalBroadcastManager.sendBroadcast(intent);
                } else {
                    // 做错

                    //设置解析内容
                    initAnalysis();
                    //打开解析view
                    mAnalysisAreaQuestion.setVisibility(mAnalysisAreaQuestion.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
                }
            }
        } else {
            //TODO 考试模式
            //获取用户的选项
            String userOption = questionList.get(index).getOptions().get(position).getOption();
            adapter.notifyDataChanged(userOption);

            //重新给答题卡响应题目设置用户答案
            optionsAnswerList.get(index).setUser_answer(userOption);
            //总数据重新复制
            questionChoiceListener.choiceSetOptionsAnswerList(optionsAnswerList);
            //initAnalysis();//重新设置解析答案
            //交卷提示
            hintSubmitPaper();

            mLocalBroadcastManager.sendBroadcast(intent);//发送广播,跳转至下一页
        }
    }

    /**
     * 最后一道题提示交卷(shabisile)
     */
    private void hintSubmitPaper() {
        if (questionList != null && questionList.size() > 0) {
            boolean complete = true;
            int temporary = questionList.size() - 1;
            if (index == temporary) {
                if (optionsAnswerList != null && optionsAnswerList.size() > 0) {
                    int size = optionsAnswerList.size();
                    for (int i = 0; i < size; i++) {
                        String userAnswer = optionsAnswerList.get(i).getUser_answer();
                        //有一道题答案为空,就标记为未完成状态
                        if (TextUtils.isEmpty(userAnswer)) {
                            //显示为未作答状态
                            complete = false;
                            break;
                        }
                    }
                }
                if (complete) {
                    //已全做,现在是不管什么板块,都等于要给个提示,不给提示的话点最后一道就直接交卷太SB
                    new TestModeIconDialog(getContext()).builder()
                            .setIcon(R.mipmap.icon_qb_submit)
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
                                    if (questionChoiceListener != null) {
                                        questionChoiceListener.choiceSubmitPaper();
                                    }
                                }
                            }).show();
                } else {
                    //部分题目未解答
                    int continue_plate = 0;
                    if (questionChoiceListener != null) {
                        continue_plate = questionChoiceListener.choiceGetContimuePlateID();
                    }
                    //组卷模考还是得给个提示,其他模块就直接提交了
                    if (plate_id == Constant.PLATE_6 || continue_plate == Constant.PLATE_6) {
                        new TestModeIconDialog(getContext()).builder()
                                .setIcon(R.mipmap.icon_qb_submit)
                                .setMsg(getResources().getString(R.string.question_tips_whetherSavePager_noCompleted))
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
                                        if (questionChoiceListener != null) {
                                            questionChoiceListener.choiceSubmitPaper();
                                        }
                                    }
                                }).show();

                    } else {
                        if (questionChoiceListener != null) {
                            questionChoiceListener.choiceSubmitPaper();
                        }
                    }
                }
            }
        }
    }


/*
    //最初的要求是这样的,啊,不对,原来就没有最后一道题提示交卷这一说
    private void hintSubmitPaper() {
        if (questionList != null && questionList.size() > 0) {
            boolean complete = true;
            int temporary = questionList.size() - 1;
            if (index == temporary) {
                if (optionsAnswerList != null) {
                    for (int i = 0; i < optionsAnswerList.size(); i++) {
                        String userAnswer = optionsAnswerList.get(i).getUser_answer();
                        if (TextUtils.isEmpty(userAnswer)) {//有一道题答案为空,就标记为未完成状态
                            complete = false;//显示为未作答状态
                            break;
                        }
                    }
                }
                if (complete) {
                    new AlertDialog(getContext()).builder()
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
                                    questionChoiceListener.choiceSubmitPaper();
                                }
                            }).show();
                }
            }
        }
    }
*/

    /**
     * Description:QuestionChoiceItemFragment
     * Time:2019-5-21 下午 2:43
     * Detail:TODO 设置解析
     */
    private void initAnalysis() {
        //解析图片1
        String analysisPic = "";
        //解析图片2
        String analysisPic2 = "";
        //解析文字1
        String analysis = "";
        //解析文字2
        String analysis2 = "";
        //错误率提示
        String eprone = "";
        //正确答案
        String rightAnswer = "";
        //用户答案
        String userOption = "";
        if (questionList != null && questionList.size() > 0) {
            analysisPic = questionList.get(index).getAnalysisPic();
            analysisPic2 = questionList.get(index).getAnalysiscPic_One();
            analysis = questionList.get(index).getAnalysis();
            analysis2 = questionList.get(index).getAnalysisc_One();
            eprone = questionList.get(index).getEprone();
        }
        if (optionsAnswerList != null && optionsAnswerList.size() > 0) {
            rightAnswer = optionsAnswerList.get(index).getTrue_options();
            userOption = optionsAnswerList.get(index).getUser_answer();
        }


        //TODO 错误率提示
        if (!TextUtils.isEmpty(eprone)) {
            mAnalysisErrorRateQuestion.setText(eprone);
            mAnalysisErrorRateQuestion.setVisibility(mAnalysisErrorRateQuestion.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
        } else {
            mAnalysisErrorRateQuestion.setVisibility(mAnalysisErrorRateQuestion.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
        }

        //TODO 正确答案
        if (!TextUtils.isEmpty(rightAnswer)) {
            //正确答案
            mAnalysisTureQuestion.setText(rightAnswer);
        } else {
            mAnalysisTureQuestion.setText(getResources().getString(R.string.holder_nodata));
        }
        //TODO 用户答案
        if (!TextUtils.isEmpty(userOption)) {
            //用户答案
            mAnalysisFalseQuestion.setText(userOption);
            if (rightAnswer.equals(userOption)) {//做对了
                mAnalysisFalseQuestion.setTextColor(ContextCompat.getColor(getContext(), R.color.color_F99111));
            } else {//做错了
                mAnalysisFalseQuestion.setTextColor(ContextCompat.getColor(getContext(), R.color.color_E84342));
            }
        } else {
            mAnalysisFalseQuestion.setText(getResources().getString(R.string.holder_notmake));
            mAnalysisFalseQuestion.setTextColor(ContextCompat.getColor(getContext(), R.color.color_F99111));
        }

        //TODO 解析文字描述一
        if (!TextUtils.isEmpty(analysis)) {
            //解析题干
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
        //TODO 解析图片描述一
        if (!TextUtils.isEmpty(analysisPic)) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.icon_default)
                    .error(R.mipmap.image_loaderror)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            GlideUtils.load(getContext(), analysisPic, mAnalysisImageQuestion, requestOptions);
        } else {
            mAnalysisImageQuestion.setVisibility(View.GONE);
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

        //TODO 解析文字描述二
        if (!TextUtils.isEmpty(analysis2)) {
            //解析题干
            mAnalysisContent2Question.setText(analysis2);
        } else {
            mAnalysisContent2Question.setVisibility(View.GONE);
        }
        //TODO 解析图片描述二
        if (!TextUtils.isEmpty(analysisPic2)) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.icon_default)
                    .error(R.mipmap.image_loaderror)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            GlideUtils.load(getContext(), analysisPic2, mAnalysisImage2Question, requestOptions);
        } else {
            mAnalysisImage2Question.setVisibility(View.GONE);
        }
        TransferConfig config2 = TransferConfig.build()
                .setMissPlaceHolder(R.mipmap.icon_default)
                .setErrorPlaceHolder(R.mipmap.icon_default)
                .setProgressIndicator(new ProgressPieIndicator())
                .setIndexIndicator(new NumberIndexIndicator())
                .setJustLoadHitImage(true)
                .bindImageView(mAnalysisImage2Question, analysisPic2);
        mAnalysisImage2Question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferee.apply(config2).show();
            }
        });

    }

    /**
     * Description:QuestionChoiceItemFragment
     * Time:2019-5-21 上午 11:55
     * Detail:TODO 设置题干内容
     */
    private void initTopicData() {
        if (questionList != null && questionList.size() > 0) {
            DoProblemsBean.DataBean.TopicsBean topicsBean = questionList.get(index);
            if (topicsBean.getTopic() != null && topicsBean.getTopic().size() > 0) {
                List<String> topicList = topicsBean.getTopic();
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
                    .placeholder(R.mipmap.icon_default)
                    .error(R.mipmap.image_loaderror)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            GlideUtils.load(getContext(), topicImage1, mImage1Question, requestOptions);
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
                    .placeholder(R.mipmap.icon_default)
                    .error(R.mipmap.image_loaderror)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            GlideUtils.load(getContext(), topicImage2, mImage2Question, requestOptions);
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
                if (questionChoiceListener != null) {
                    questionChoiceListener.choiceDeleteCurrentQuestion();
                }
                break;
            case R.id.btn_collection:
                //TODO 19/05/28 取消收藏==移除当前错题
                if (questionChoiceListener != null) {
                    questionChoiceListener.choiceCancelCurrentQuestion();
                }
                break;
            default:
                break;
        }
    }
}
