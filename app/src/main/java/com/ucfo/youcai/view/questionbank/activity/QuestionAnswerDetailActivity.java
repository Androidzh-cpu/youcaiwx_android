package com.ucfo.youcai.view.questionbank.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressBarIndicator;
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.ucfo.youcai.R;
import com.ucfo.youcai.adapter.answer.PictureAdapter;
import com.ucfo.youcai.base.BaseActivity;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.answer.QuestionAnswerDetailBean;
import com.ucfo.youcai.entity.answer.QuestionAnswerListBean;
import com.ucfo.youcai.presenter.presenterImpl.answer.QuestionAnswerPresenter;
import com.ucfo.youcai.presenter.view.answer.IQuestionAnswerView;
import com.ucfo.youcai.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcai.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcai.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcai.utils.systemutils.DensityUtil;
import com.ucfo.youcai.utils.systemutils.StatusbarUI;
import com.ucfo.youcai.widget.customview.LoadingLayout;
import com.ucfo.youcai.widget.flowlayout.FlowLayout;
import com.ucfo.youcai.widget.flowlayout.TagAdapter;
import com.ucfo.youcai.widget.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: AND
 * Time: 2019-5-31 上午 11:16
 * Package: com.ucfo.youcai.view.questionbank.activity
 * FileName: QuestionAnswerDetailActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 题库答疑详情
 */
public class QuestionAnswerDetailActivity extends BaseActivity implements IQuestionAnswerView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.answer_videoname)
    TextView answerVideoname;
    @BindView(R.id.answer_usericon)
    CircleImageView answerUsericon;
    @BindView(R.id.answer_usernickname)
    TextView answerUsernickname;
    @BindView(R.id.answer_question_createtime)
    TextView answerQuestionCreatetime;
    @BindView(R.id.answer_teacherreplystatus)
    TextView answerTeacherreplystatus;
    @BindView(R.id.answer_userquestion)
    TextView answerUserquestion;
    @BindView(R.id.answer_userimagelist)
    RecyclerView answerUserimagelist;
    @BindView(R.id.flowlayout)
    TagFlowLayout flowlayout;
    @BindView(R.id.answer_teachericon)
    CircleImageView answerTeachericon;
    @BindView(R.id.answer_teachernickname)
    TextView answerTeachernickname;
    @BindView(R.id.answer_question_replytime)
    TextView answerQuestionReplytime;
    @BindView(R.id.answer_teacherreply)
    TextView answerTeacherreply;
    @BindView(R.id.answer_teacherimagelist)
    RecyclerView answerTeacherimagelist;
    @BindView(R.id.answer_teacher_linear)
    LinearLayout answerTeacherLinear;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.answer_user_linear)
    LinearLayout answerUserLinear;

    private Bundle bundle;
    private int answer_id, reply_status, user_id;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private QuestionAnswerPresenter questionAnswerPresenter;
    private QuestionAnswerDetailActivity context;
    private Transferee transferee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_question_answer_detail;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        //状态栏白色,字体黑色
        StatusbarUI.setStatusBarUIMode(this, Color.TRANSPARENT, true);
        setSupportActionBar(titlebarToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        titlebarMidtitle.setText(getResources().getString(R.string.answer_title_questionsDetail));
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

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        context = this;
        transferee = Transferee.getDefault(context);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        int leftright = DensityUtil.dip2px(context, 4);
        answerUserimagelist.addItemDecoration(new SpacesItemDecoration(leftright, 0, Color.TRANSPARENT));
        answerTeacherimagelist.addItemDecoration(new SpacesItemDecoration(leftright, 0, Color.TRANSPARENT));
        answerUserimagelist.setLayoutManager(layoutManager);
        answerTeacherimagelist.setLayoutManager(layoutManager2);
    }

    @Override
    protected void initData() {
        super.initData();
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);

        questionAnswerPresenter = new QuestionAnswerPresenter(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            answer_id = bundle.getInt(Constant.ID, 0);//题目ID
            reply_status = bundle.getInt(Constant.STATUS, 0);//回复状态
            questionAnswerPresenter.getQuestionAnswerDetail(user_id, answer_id);
        } else {
            loadinglayout.showEmpty();
            return;
        }

        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionAnswerPresenter.getQuestionAnswerDetail(user_id, answer_id);
            }
        });

    }

    @Override
    public void getAnswerList(QuestionAnswerListBean data) {
        //TODO nothing
    }

    @Override
    public void getQuestionAnswerDetail(QuestionAnswerDetailBean bean) {
        if (bean != null) {
            if (bean.getData() != null) {
                setDetailInfo(bean);
                loadinglayout.showContent();
            } else {
                loadinglayout.showEmpty();
            }
        } else {
            loadinglayout.showEmpty();
        }
    }

    //TODO 设置答疑详情
    private void setDetailInfo(QuestionAnswerDetailBean bean) {
        QuestionAnswerDetailBean.DataBeanX dataBeanX = bean.getData();

        String question_keyword = dataBeanX.getQuestion_keyword();
        QuestionAnswerDetailBean.DataBeanX.DataBean data = dataBeanX.getData();
        QuestionAnswerDetailBean.DataBeanX.ReplyBean reply = dataBeanX.getReply();
        if (!TextUtils.isEmpty(question_keyword)) {//TODO 标题
            answerVideoname.setText(question_keyword);
        }
        if (data != null) {//提问信息
            switch (reply_status) {//TODO 回复状态
                case 1://1回复
                    answerTeacherreplystatus.setText(getResources().getString(R.string.answer_teacher_reply));
                    break;
                case 2://2未回复
                default:
                    break;
            }

            String head = data.getHead();
            String username = data.getUsername();
            String quiz = data.getQuiz();
            String create_times = data.getCreate_times();

            List<String> quiz_image = data.getQuiz_image();
            List<String> know_name = data.getKnow_name();
            if (TextUtils.isEmpty(head)) {
                Glide.with(context).load(head).error(R.mipmap.icon_headdefault).into(answerUsericon);//TODO 学员头像
            } else {
                Glide.with(context).load(head).error(R.mipmap.icon_headdefault).into(answerUsericon);//TODO 学员头像
                TransferConfig headConfig = TransferConfig.build()
                        .setMissPlaceHolder(R.mipmap.banner_default)
                        .setErrorPlaceHolder(R.mipmap.banner_default)
                        .setProgressIndicator(new ProgressPieIndicator())
                        .setIndexIndicator(new NumberIndexIndicator())
                        .setJustLoadHitImage(true)
                        .bindImageView(answerUsericon, head);
                answerUsericon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        transferee.apply(headConfig).show();
                    }
                });
            }
            if (!TextUtils.isEmpty(username)) {//TODO 学员昵称
                answerUsernickname.setText(username);
            }
            if (!TextUtils.isEmpty(create_times)) {//TODO 学员昵称
                answerQuestionCreatetime.setText(create_times);
            }
            if (!TextUtils.isEmpty(quiz)) {//TODO 问题详情
                answerUserquestion.setText(quiz);
            }
            if (quiz_image != null && quiz_image.size() > 0) {//TODO 学员问题图片处理
                answerUserimagelist.setVisibility(View.VISIBLE);
                PictureAdapter pictureAdapter = new PictureAdapter(quiz_image, context);//创建图片列表适配器
                answerUserimagelist.setAdapter(pictureAdapter);
                TransferConfig config = TransferConfig.build()//图片预览先关配置
                        .setThumbnailImageList(quiz_image)//预览图
                        .setSourceImageList(quiz_image)//图片地址
                        .setMissPlaceHolder(R.mipmap.banner_default)
                        .setErrorPlaceHolder(R.mipmap.banner_default)
                        .setProgressIndicator(new ProgressBarIndicator())//加载进度
                        .setIndexIndicator(new NumberIndexIndicator())//指示器
                        .setJustLoadHitImage(true)//是否只加载当前显示在屏幕中的的原图
                        .setOffscreenPageLimit(quiz_image.size())
                        .bindRecyclerView(answerUserimagelist, R.id.iv_image);// RecyclerView 来排列显示图片,图片的ID
                pictureAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int j) {
                        config.setNowThumbnailIndex(j);
                        transferee.apply(config).show();
                    }
                });
            } else {
                answerUserimagelist.setVisibility(View.GONE);
            }
            if (know_name.size() > 0) {//TODO 标签处理
                TagAdapter<String> tagAdapter = new TagAdapter<String>(know_name) {//流式布局适配器
                    @Override
                    public View getView(FlowLayout parent, int j, String string) {
                        TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_tagflowlayout, flowlayout, false);
                        textView.setText(string);
                        int i = j % 3;
                        switch (i) {
                            case 0://余数为0
                                textView.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_rectangle_corners_blue));
                                textView.setTextColor(ContextCompat.getColor(context, R.color.color_0267FF));
                                break;
                            case 1://余数为1
                                textView.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_rectangle_corners_red));
                                textView.setTextColor(ContextCompat.getColor(context, R.color.color_E84342));
                                break;
                            case 2://余数为2
                                textView.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_rectangle_corners_orange));
                                textView.setTextColor(ContextCompat.getColor(context, R.color.color_F99111));
                                break;
                            default:
                                break;
                        }
                        return textView;
                    }
                };
                flowlayout.setAdapter(tagAdapter);//流式布局添加适配器
            }

        }

        switch (reply_status) {
            case 1://1回复
                answerTeacherLinear.setVisibility(answerTeacherLinear.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
                String head_img = reply.getHead_img();
                String reply_user_name = reply.getReply_user_name();
                String reply_times = reply.getReply_times();
                String reply_quiz = reply.getReply_quiz();
                List<String> reply_image = reply.getReply_image();
                if (TextUtils.isEmpty(head_img)) {
                    Glide.with(context).load(head_img).error(R.mipmap.icon_headdefault).into(answerTeachericon);
                } else {
                    Glide.with(context).load(head_img).error(R.mipmap.icon_headdefault).into(answerTeachericon);
                    TransferConfig headConfig = TransferConfig.build()
                            .setMissPlaceHolder(R.mipmap.banner_default)
                            .setErrorPlaceHolder(R.mipmap.banner_default)
                            .setProgressIndicator(new ProgressPieIndicator())
                            .setIndexIndicator(new NumberIndexIndicator())
                            .setJustLoadHitImage(true)
                            .bindImageView(answerTeachericon, head_img);
                    answerTeachericon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            transferee.apply(headConfig).show();
                        }
                    });
                }

                if (!TextUtils.isEmpty(reply_user_name)) {//TODO 用户昵称
                    answerTeachernickname.setText(reply_user_name);
                }
                if (!TextUtils.isEmpty(reply_times)) {//TODO 老师回复时间
                    answerQuestionReplytime.setText(reply_times);
                }
                if (!TextUtils.isEmpty(reply_quiz)) {//TODO 老师回复内容
                    answerTeacherreply.setText(reply_quiz);
                }
                if (reply_image != null && reply_image.size() > 0) {//TODO 学员问题图片处理
                    answerTeacherimagelist.setVisibility(View.VISIBLE);
                    PictureAdapter pictureAdapter = new PictureAdapter(reply_image, context);//创建图片列表适配器
                    answerTeacherimagelist.setAdapter(pictureAdapter);
                    TransferConfig config = TransferConfig.build()//图片预览先关配置
                            .setThumbnailImageList(reply_image)//预览图
                            .setSourceImageList(reply_image)//图片地址
                            .setMissPlaceHolder(R.mipmap.banner_default)
                            .setErrorPlaceHolder(R.mipmap.banner_default)
                            .setProgressIndicator(new ProgressBarIndicator())//加载进度
                            .setIndexIndicator(new NumberIndexIndicator())//指示器
                            .setJustLoadHitImage(true)//是否只加载当前显示在屏幕中的的原图
                            .setOffscreenPageLimit(reply_image.size())
                            .bindRecyclerView(answerTeacherimagelist, R.id.iv_image);// RecyclerView 来排列显示图片,图片的ID
                    pictureAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int j) {
                            config.setNowThumbnailIndex(j);
                            transferee.apply(config).show();
                        }
                    });
                } else {
                    answerTeacherimagelist.setVisibility(View.GONE);
                }
                break;
            case 2://2未回复
            default:
                answerTeacherLinear.setVisibility(answerTeacherLinear.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
                break;
        }
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
        loadinglayout.showEmpty();
    }
}
