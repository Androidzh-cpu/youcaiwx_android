package com.ucfo.youcaiwx.module.questionbank.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.roundview.RoundTextView;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.questionbank.DoProblemsAnswerBean;
import com.ucfo.youcaiwx.entity.questionbank.DoProblemsBean;
import com.ucfo.youcaiwx.module.questionbank.activity.TESTMODEActivity;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-5-17.  下午 4:10
 * FileName: QuestionDiscussItemFragment
 * Description:TODO  论述题做题界
 */
@SuppressLint("ValidFragment")
public class QuestionDiscussItemFragment extends BaseFragment implements View.OnClickListener {
    ////////////////////////////////////////////////////////////////////TODO 又是一个分水岭@_@ ///////////////////////////////////////////////////////////////////////////////////////
    private int index, allIndex, plateId;//当前索引 , 全部的页面数量 , 板块ID
    private String EXERCISE_TYPE;//卷子的模式
    private TextView mCurrentIndexQuestion, mCountIndexQuestion, mContent1Question, mContent2Question, mContent3Question, mAnalysisContentQuestion;
    private ImageView mImage1Question, mImage2Question, mAnalysisImageQuestion;
    private EditText mElaborationEt;
    private RoundTextView mCheckAnalysisQuestion;
    private LinearLayout mAnalysisAreaQuestion;
    private boolean analysisStatus = false;//解析是否可见的状态
    private TESTMODEActivity testmodeActivity;
    private ArrayList<DoProblemsBean.DataBean.TopicsBean> questionList;
    private ArrayList<DoProblemsAnswerBean> optionsAnswerList;
    private Transferee transferee;
    private NestedScrollView nestedScrollView;
    private String userInputString;
    private boolean discuss_analysis;
    private ImageView mCollectionBtn;
    private TextView mAnalysisContent2Question;
    private ImageView mAnalysisImage2Question;

    public QuestionDiscussItemFragment() {
    }

    public QuestionDiscussItemFragment(int index, String type, int allIndex, int plate_id) {
        this.index = index;
        this.allIndex = allIndex;
        this.plateId = plate_id;
        this.EXERCISE_TYPE = type;//传递的type来识别，此处接口只是选择题模型，A是考试模式，B是练习模式 D解析模式
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_discussquestionitem;
    }

    @Override
    protected void initView(View itemView) {
        nestedScrollView = (NestedScrollView) itemView.findViewById(R.id.nestedscrollview);
        mCurrentIndexQuestion = (TextView) itemView.findViewById(R.id.question_current_index);
        mCountIndexQuestion = (TextView) itemView.findViewById(R.id.question_count_index);

        //题干
        mContent1Question = (TextView) itemView.findViewById(R.id.question_content1);
        mImage1Question = (ImageView) itemView.findViewById(R.id.question_image1);
        mContent2Question = (TextView) itemView.findViewById(R.id.question_content2);
        mImage2Question = (ImageView) itemView.findViewById(R.id.question_image2);
        mContent3Question = (TextView) itemView.findViewById(R.id.question_content3);

        //做大区域
        mElaborationEt = (EditText) itemView.findViewById(R.id.et_elaboration);

        //查看解析按钮
        mCheckAnalysisQuestion = (RoundTextView) itemView.findViewById(R.id.question_check_analysis);
        mCheckAnalysisQuestion.setOnClickListener(this);

        //解析内容
        mAnalysisContentQuestion = (TextView) itemView.findViewById(R.id.question_analysis_content);
        mAnalysisImageQuestion = (ImageView) itemView.findViewById(R.id.question_analysis_image);
        mAnalysisContent2Question = (TextView) itemView.findViewById(R.id.question_analysis_content2);
        mAnalysisImage2Question = (ImageView) itemView.findViewById(R.id.question_analysis_image2);

        //解析区域
        mAnalysisAreaQuestion = (LinearLayout) itemView.findViewById(R.id.question_analysis_area);

        //收藏按钮
        mCollectionBtn = (ImageView) itemView.findViewById(R.id.btn_collection);
        mCollectionBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        //这是当前索引值和全部页面数量
        mCurrentIndexQuestion.setText(String.valueOf(index + 1));
        mCountIndexQuestion.setText(String.valueOf(allIndex));


        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity instanceof TESTMODEActivity) {
            testmodeActivity = (TESTMODEActivity) fragmentActivity;
            questionList = testmodeActivity.getQuestionList();
            optionsAnswerList = testmodeActivity.getOptionsAnswerList();
            discuss_analysis = testmodeActivity.getDiscussAnalysis();
        }

        //TODO 判断移除错题和收藏
        if (plateId == Constant.PLATE_13) {//我的收藏查看试题
            mCollectionBtn.setVisibility(View.VISIBLE);
        } else {
            mCollectionBtn.setVisibility(View.GONE);
        }

        //TODO 图片预览初始化
        transferee = Transferee.getDefault(testmodeActivity);

        //TODO 设置题干
        initTopicData();

        //TODO 设置解析
        initAnalysis();

        //TODO 设置edittext内容
        if (!TextUtils.isEmpty(optionsAnswerList.get(index).getUser_answer())) {
            mElaborationEt.setText(optionsAnswerList.get(index).getUser_answer());
        }

        //TODO 判断是否是论述题解析模式
        if (discuss_analysis) {
            mAnalysisAreaQuestion.setVisibility(View.VISIBLE);
            //输入框不可编辑
            mElaborationEt.setKeyListener(null);
            //mElaborationEt.setText(questionList.get(index).getDiscuss_useranswer());不要从题干中获取用户答案,因为这时候的用户答案是空的
            mElaborationEt.setText(optionsAnswerList.get(index).getUser_answer());

            analysisStatus = false;
            mCheckAnalysisQuestion.setText(getResources().getString(R.string.question_tips_uncheckanalysis));
            mCheckAnalysisQuestion.setClickable(false);
        } else {
            //TODO 开始做题
            mElaborationEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // 输入前的监听
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // 输入的内容变化的监听
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // 输入后的监听
                    userInputString = mElaborationEt.getText().toString().trim();
                    if (!TextUtils.isEmpty(userInputString)) {
                        optionsAnswerList.get(index).setUser_answer(userInputString);//设置用户答案
                    } else {
                        optionsAnswerList.get(index).setUser_answer("");
                    }
                }
            });
        }
    }

    //TODO 设置解析
    private void initAnalysis() {
        //解析图片
        String analysisPic = questionList.get(index).getAnalysisPic();
        String analysisPic2 = questionList.get(index).getAnalysiscPic_One();
        //解析文字
        String analysis = questionList.get(index).getAnalysis();
        String analysis2 = questionList.get(index).getAnalysisc_One();

        //TODO 文字描述一
        if (!TextUtils.isEmpty(analysis)) {
            String holder = getResources().getString(R.string.holder_analysis);
            String text = String.valueOf(holder + analysis);
            SpannableString spannableString = new SpannableString(text);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF333333"));
            /*AbsoluteSizeSpan ab = new AbsoluteSizeSpan(14, true);
            spannableString.setSpan(ab, 0, holder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, holder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //粗体
            spannableString.setSpan(colorSpan, 0, holder.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            mAnalysisContentQuestion.setText(spannableString);

        } else {
            mAnalysisContentQuestion.setVisibility(View.GONE);
        }

        //TODO 图片描述一
        if (!TextUtils.isEmpty(analysisPic)) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.icon_default)
                    .error(R.mipmap.image_loaderror)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            GlideUtils.load(testmodeActivity, analysisPic, mAnalysisImageQuestion, requestOptions);

        } else {
            mAnalysisImageQuestion.setVisibility(View.GONE);
        }
        TransferConfig config = TransferConfig.build()
                .setMissPlaceHolder(R.mipmap.icon_default)
                .setErrorPlaceHolder(R.mipmap.icon_default)
                .setProgressIndicator(new ProgressPieIndicator())
                .setIndexIndicator(new NumberIndexIndicator())
                .setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
                .setJustLoadHitImage(true)
                .bindImageView(mAnalysisImageQuestion, analysisPic);
        mAnalysisImageQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferee.apply(config).show();
            }
        });

        //TODO 1.0.2增加多解析

        //TODO 文字描述二
        if (!TextUtils.isEmpty(analysis2)) {
            mAnalysisContent2Question.setText(analysis2);
        } else {
            mAnalysisContent2Question.setVisibility(View.GONE);
        }

        //TODO 图片描述二
        if (!TextUtils.isEmpty(analysisPic2)) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.icon_default)
                    .error(R.mipmap.image_loaderror)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            GlideUtils.load(testmodeActivity, analysisPic2, mAnalysisImage2Question, requestOptions);
        } else {
            mAnalysisImage2Question.setVisibility(View.GONE);
        }
        TransferConfig config2 = TransferConfig.build()
                .setMissPlaceHolder(R.mipmap.icon_default)
                .setErrorPlaceHolder(R.mipmap.icon_default)
                .setProgressIndicator(new ProgressPieIndicator())
                .setIndexIndicator(new NumberIndexIndicator())
                .setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
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
     * Description:QuestionDiscussItemFragment
     * Time:2019-5-23 上午 10:13
     * Detail:TODO 设置题干
     */
    private void initTopicData() {
        List<String> topicList = questionList.get(index).getTopic();
        if (topicList != null && topicList.size() > 0) {
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
            GlideUtils.load(testmodeActivity, topicImage1, mImage1Question, requestOptions);
        }
        TransferConfig config = TransferConfig.build()
                .setMissPlaceHolder(R.mipmap.icon_default)
                .setErrorPlaceHolder(R.mipmap.icon_default)
                .setProgressIndicator(new ProgressPieIndicator())
                .setIndexIndicator(new NumberIndexIndicator())
                .setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
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
            GlideUtils.load(testmodeActivity, topicImage2, mImage2Question, requestOptions);
        }
        TransferConfig config = TransferConfig.build()
                .setMissPlaceHolder(R.mipmap.icon_default)
                .setErrorPlaceHolder(R.mipmap.icon_default)
                .setProgressIndicator(new ProgressPieIndicator())
                .setIndexIndicator(new NumberIndexIndicator())
                .setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
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
            case R.id.question_check_analysis:// TODO 19/05/17   查看解析
                if (analysisStatus) {//已打开,点击关闭解析
                    mCheckAnalysisQuestion.setText(getResources().getString(R.string.question_tips_checkanalysis));
                    mAnalysisAreaQuestion.setVisibility(mAnalysisAreaQuestion.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
                    analysisStatus = !analysisStatus;
                } else {//未打开状态,点击打开解析
                    mCheckAnalysisQuestion.setText(getResources().getString(R.string.question_tips_uncheckanalysis));
                    mAnalysisAreaQuestion.setVisibility(mAnalysisAreaQuestion.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
                    analysisStatus = !analysisStatus;
                }
                break;
            case R.id.btn_collection:// TODO 19/06/20   取消收藏
                testmodeActivity.cancelCurrentQuestion();
                break;
            default:
                break;
        }
    }
}
