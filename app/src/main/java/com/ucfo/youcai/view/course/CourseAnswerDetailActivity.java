package com.ucfo.youcai.view.course;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.roundview.RoundTextView;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressBarIndicator;
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ucfo.youcai.R;
import com.ucfo.youcai.adapter.answer.PictureAdapter;
import com.ucfo.youcai.base.BaseActivity;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.answer.AnswerDetailBean;
import com.ucfo.youcai.entity.answer.AnswerListDataBean;
import com.ucfo.youcai.presenter.presenterImpl.answer.CourseCourseAnswerListPresenter;
import com.ucfo.youcai.presenter.view.answer.ICourseAnswerListView;
import com.ucfo.youcai.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcai.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcai.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcai.utils.systemutils.DensityUtil;
import com.ucfo.youcai.utils.systemutils.StatusbarUI;
import com.ucfo.youcai.view.course.player.utils.TimeFormater;
import com.ucfo.youcai.widget.customview.LoadingLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Description:CourseAnswerDetailActivity
 * Time:2019-4-17   下午 3:00
 * Detail: TODO 答疑详情
 */
public class CourseAnswerDetailActivity extends BaseActivity implements ICourseAnswerListView {

    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.answer_usericon)
    CircleImageView answerUsericon;
    @BindView(R.id.answer_usernickname)
    TextView answerUsernickname;
    @BindView(R.id.answer_userquestion)
    TextView answerUserquestion;
    @BindView(R.id.answer_userimagelist)
    RecyclerView answerUserimagelist;
    @BindView(R.id.answer_question_sectionname)
    RoundTextView answerQuestionSectionname;
    @BindView(R.id.answer_question_createtime)
    TextView answerQuestionCreatetime;
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
    @BindView(R.id.answer_teacherreplystatus)
    TextView answerTeacherreplystatus;
    @BindView(R.id.answer_videoname)
    TextView answerVideoname;
    @BindView(R.id.top_linear)
    LinearLayout topLinear;
    private Context context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int user_id;
    private Bundle bundle;
    private int answer_id, answer_replystatus;
    private String type;
    private CourseCourseAnswerListPresenter courseAnswerListPresenter;
    private LinearLayoutManager layoutManager, layoutManager2;
    private Transferee transferee;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_cours_answer_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        context = CourseAnswerDetailActivity.this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        transferee = Transferee.getDefault(CourseAnswerDetailActivity.this);

        layoutManager = new LinearLayoutManager(context);
        answerUserimagelist.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        int topBottom = DensityUtil.dip2px(context, 4);
        answerUserimagelist.addItemDecoration(new SpacesItemDecoration(topBottom, 0, Color.TRANSPARENT));
        layoutManager2 = new LinearLayoutManager(context);
        answerTeacherimagelist.setLayoutManager(layoutManager2);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        int topBottom2 = DensityUtil.dip2px(context, 4);
        answerTeacherimagelist.addItemDecoration(new SpacesItemDecoration(topBottom2, 0, Color.TRANSPARENT));
        answerTeacherimagelist.setNestedScrollingEnabled(false);
        answerUserimagelist.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        super.initData();
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        //注册网络
        courseAnswerListPresenter = new CourseCourseAnswerListPresenter(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getString(Constant.TYPE,"");
            answer_id = bundle.getInt(Constant.ANSWER_ID, 0);//获取传递的问答ID
            answer_replystatus = bundle.getInt(Constant.QUESTION_STATUS, 0);//获取传递的问答状态

            //根据传递的问答ID获取问答详情
            courseAnswerListPresenter.getAnswerDetail(answer_id);

            switch (answer_replystatus) {
                case 1://1回复
                    answerTeacherreplystatus.setText(getResources().getString(R.string.answer_teacher_reply));
                    break;
                case 2://2未回复
                    break;
            }
            if (type.equals(Constant.MINE_ANSWER)) {
                topLinear.setVisibility(View.GONE);
            }
        } else {
            loadinglayout.showEmpty();
        }

        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseAnswerListPresenter.getAnswerDetail(answer_id);
            }
        });
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        StatusbarUI.setStatusBarUIMode(this, Color.TRANSPARENT, true);
        setSupportActionBar(titlebarToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        titlebarRighttitle.setVisibility(View.GONE);
        titlebarMidtitle.setText(getResources().getString(R.string.answer_detail));
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
     * Description:CourseAnswerDetailActivity
     * Time:2019-4-17   下午 3:23
     * Detail: TODO  获取问答详情
     */
    @Override
    public void getAnswerDetailData(AnswerDetailBean dataBean) {
        if (dataBean.getData() != null) {
            AnswerDetailBean.DataBeanX data = dataBean.getData();

            AnswerDetailBean.DataBeanX.DataBean userData = data.getData();
            //TODO 学员问题逻辑处理
            if (userData != null) {
                String creates_time = userData.getCreates_time();//问题提问时间
                String quiz = userData.getQuiz();//学员问题
                String section_name = userData.getSection_name();//问题所属章节
                String username = userData.getUsername();//学员昵称
                String video_time = userData.getVideo_time();
                String formatMs = "";
                if (!TextUtils.isEmpty(video_time)) {
                    formatMs = TimeFormater.formatMs(Integer.parseInt(video_time));
                }

                if (TextUtils.isEmpty(userData.getHead())) {
                    Glide.with(context).load(userData.getHead()).error(R.mipmap.icon_headdefault).into(answerUsericon);
                } else {
                    Glide.with(context).load(userData.getHead()).error(R.mipmap.icon_headdefault).into(answerUsericon);
                    TransferConfig headConfig = TransferConfig.build()
                            .setMissPlaceHolder(R.mipmap.banner_default)
                            .setErrorPlaceHolder(R.mipmap.banner_default)
                            .setProgressIndicator(new ProgressPieIndicator())
                            .setIndexIndicator(new NumberIndexIndicator())
                            .setJustLoadHitImage(true)
                            .bindImageView(answerUsericon, userData.getHead());
                    answerUsericon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            transferee.apply(headConfig).show();
                        }
                    });
                }

                //TODO 设置顶部章节名称
                String topVdeioTitle = section_name + "    " + formatMs;
                SpannableString spannableString = new SpannableString(topVdeioTitle);
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#999999"));
                AbsoluteSizeSpan ab = new AbsoluteSizeSpan(13, true);
                spannableString.setSpan(ab, topVdeioTitle.length() - video_time.length() - 1, topVdeioTitle.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(colorSpan, topVdeioTitle.length() - video_time.length() - 1, topVdeioTitle.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                answerVideoname.setText(spannableString);

                if (!TextUtils.isEmpty(username)) {
                    answerUsernickname.setText(username);
                }
                if (!TextUtils.isEmpty(section_name)) {
                    answerQuestionSectionname.setText(section_name);
                }
                if (!TextUtils.isEmpty(quiz)) {
                    answerUserquestion.setText(quiz);
                }
                if (!TextUtils.isEmpty(creates_time)) {
                    answerQuestionCreatetime.setText(creates_time);
                }
                if (userData.getQuiz_image() != null && userData.getQuiz_image().size() > 0) {//学员图片处理
                    answerUserimagelist.setVisibility(View.VISIBLE);
                    PictureAdapter pictureAdapter = new PictureAdapter(userData.getQuiz_image(), context);//创建图片列表适配器

                    TransferConfig config = TransferConfig.build()//图片预览先关配置
                            .setThumbnailImageList(userData.getQuiz_image())//预览图
                            .setSourceImageList(userData.getQuiz_image())//图片地址
                            .setProgressIndicator(new ProgressBarIndicator())//加载进度
                            .setIndexIndicator(new NumberIndexIndicator())//指示器
                            .setJustLoadHitImage(true)//是否只加载当前显示在屏幕中的的原图
                            .bindRecyclerView(answerUserimagelist, R.id.iv_image);// RecyclerView 来排列显示图片,图片的ID
                    answerUserimagelist.setAdapter(pictureAdapter);
                    pictureAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            config.setNowThumbnailIndex(position);
                            transferee.apply(config).show();
                        }
                    });
                } else {
                    answerUserimagelist.setVisibility(View.GONE);
                }
            }
            ///////////////////////////////////////////////TODO 大分水岭-讲师回复区域//////////////////////////////////////////////////////////////////////////////////////////////
            if (answer_replystatus == 1) {//TODO 已回复
                answerTeacherLinear.setVisibility(View.VISIBLE);//老师回复区域看见

                AnswerDetailBean.DataBeanX.ReplyBean dataReply = data.getReply();

                if (TextUtils.isEmpty(dataReply.getHead_img())) {
                    Glide.with(context).load(dataReply.getHead_img()).error(R.mipmap.icon_headdefault).into(answerTeachericon);//TODO 老师头像
                } else {
                    Glide.with(context).load(dataReply.getHead_img()).error(R.mipmap.icon_headdefault).into(answerTeachericon);//TODO 老师头像
                    TransferConfig headConfig2 = TransferConfig.build()
                            .setMissPlaceHolder(R.mipmap.banner_default)
                            .setErrorPlaceHolder(R.mipmap.banner_default)
                            .setProgressIndicator(new ProgressPieIndicator())
                            .setIndexIndicator(new NumberIndexIndicator())
                            .setJustLoadHitImage(true)
                            .bindImageView(answerTeachericon, dataReply.getHead_img());
                    answerTeachericon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            transferee.apply(headConfig2).show();
                        }
                    });
                }

                if (!TextUtils.isEmpty(dataReply.getReply_user_name())) {
                    answerTeachernickname.setText(dataReply.getReply_user_name());//讲师昵称
                }
                if (!TextUtils.isEmpty(dataReply.getRepls_time())) {
                    answerQuestionReplytime.setText(dataReply.getRepls_time());//讲师回复时间
                }
                if (!TextUtils.isEmpty(dataReply.getReply_quiz())) {
                    answerTeacherreply.setText(dataReply.getReply_quiz());//讲师回复时间
                }
                if (dataReply.getReply_image() != null && dataReply.getReply_image().size() > 0) {//讲师图片处理
                    answerTeacherimagelist.setVisibility(View.VISIBLE);
                    PictureAdapter pictureAdapter = new PictureAdapter(dataReply.getReply_image(), context);//创建图片列表适配器

                    TransferConfig config = TransferConfig.build()//图片预览先关配置
                            .setThumbnailImageList(dataReply.getReply_image())//预览图
                            .setSourceImageList(dataReply.getReply_image())//图片地址
                            .setProgressIndicator(new ProgressBarIndicator())//加载进度
                            .setIndexIndicator(new NumberIndexIndicator())//指示器
                            .setJustLoadHitImage(true)//是否只加载当前显示在屏幕中的的原图
                            .bindRecyclerView(answerTeacherimagelist, R.id.iv_image);// RecyclerView 来排列显示图片,图片的ID
                    answerTeacherimagelist.setAdapter(pictureAdapter);
                    pictureAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            config.setNowThumbnailIndex(position);
                            transferee.apply(config).show();
                        }
                    });

                } else {
                    answerTeacherimagelist.setVisibility(View.GONE);
                }
            } else {//未回复
                answerTeacherLinear.setVisibility(View.GONE);
            }
            loadinglayout.showContent();
        } else {
            loadinglayout.showEmpty();
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
        loadinglayout.showError();
    }

    @Override
    public void getAnswerListData(AnswerListDataBean dataBean) {
        //TODO nothing
    }


}
