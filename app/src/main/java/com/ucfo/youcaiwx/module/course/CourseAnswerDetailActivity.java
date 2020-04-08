package com.ucfo.youcaiwx.module.course;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.roundview.RoundTextView;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressBarIndicator;
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.answer.PictureAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.answer.AnswerDetailBean;
import com.ucfo.youcaiwx.entity.answer.AnswerListDataBean;
import com.ucfo.youcaiwx.module.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.module.course.player.utils.TimeFormater;
import com.ucfo.youcaiwx.presenter.presenterImpl.answer.CourseCourseAnswerListPresenter;
import com.ucfo.youcaiwx.presenter.view.answer.ICourseAnswerListView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Description:CourseAnswerDetailActivity
 * Time:2019-4-17   下午 3:00
 * Detail: TODO 答疑详情(已弃用)
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
    @BindView(R.id.showline)
    View showline;
    private Context context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int user_id;
    private Bundle bundle;
    private int answer_id, answer_replystatus;
    private String type;
    private CourseCourseAnswerListPresenter courseAnswerListPresenter;
    private LinearLayoutManager layoutManager, layoutManager2;
    private Transferee transferee;
    private AnswerDetailBean answerDetailBean;

    @Override
    protected int setContentView() {
        return R.layout.activity_cours_answer_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);

        context = CourseAnswerDetailActivity.this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        transferee = Transferee.getDefault(CourseAnswerDetailActivity.this);

        layoutManager = new LinearLayoutManager(context);
        answerUserimagelist.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        layoutManager2 = new LinearLayoutManager(context);
        answerTeacherimagelist.setLayoutManager(layoutManager2);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);

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
            type = bundle.getString(Constant.TYPE, Constant.MESSAGE_ANSWER);
            answer_id = bundle.getInt(Constant.ANSWER_ID, 0);//获取传递的问答ID
            answer_replystatus = bundle.getInt(Constant.STATUS, 0);//获取传递的问答状态

            //根据传递的问答ID获取问答详情
            courseAnswerListPresenter.getAnswerDetail(answer_id, user_id);

            switch (answer_replystatus) {
                case 1://1回复
                    answerTeacherreplystatus.setText(getResources().getString(R.string.answer_teacher_reply));
                    break;
                default:
                    break;
            }
            if (TextUtils.equals(type, Constant.MESSAGE_ANSWER)) {
                topLinear.setVisibility(View.GONE);
            } else {
                topLinear.setVisibility(View.VISIBLE);
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseAnswerListPresenter.getAnswerDetail(answer_id, user_id);
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
        if (dataBean != null) {
            this.answerDetailBean = dataBean;
            AnswerDetailBean.DataBeanX data = dataBean.getData();
            AnswerDetailBean.DataBeanX.TitleBean titleBean = data.getTitleBean();
            if (titleBean != null) {
                String title = titleBean.getTitle();
                String videoTime = titleBean.getVideo_time();
                String formatMs = "";
                if (!TextUtils.isEmpty(videoTime)) {
                    formatMs = TimeFormater.formatSeconds(Integer.parseInt(videoTime));
                }
                String finalString = String.valueOf(title + "  " + formatMs);
                answerVideoname.setText(finalString);
            }
            AnswerDetailBean.DataBeanX.DataBean userData = data.getData();
            //TODO 学员问题逻辑处理
            if (userData != null) {
                String createsTime = userData.getCreates_time();//问题提问时间
                String quiz = userData.getQuiz();//学员问题
                String sectionName = userData.getSection_name();//问题所属章节
                String username = userData.getUsername();//学员昵称
                if (TextUtils.isEmpty(userData.getHead())) {
                    RequestOptions requestOptions = new RequestOptions()
                            .placeholder(R.mipmap.icon_default)
                            .error(R.mipmap.image_loaderror)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                    GlideUtils.load(context, userData.getHead(), answerUsericon, requestOptions);
                } else {
                    RequestOptions requestOptions = new RequestOptions()
                            .placeholder(R.mipmap.icon_default)
                            .error(R.mipmap.image_loaderror)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                    GlideUtils.load(context, userData.getHead(), answerUsericon, requestOptions);
                    TransferConfig headConfig = TransferConfig.build()
                            .setMissPlaceHolder(R.mipmap.icon_default)
                            .setErrorPlaceHolder(R.mipmap.icon_default)
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
                /*String topVdeioTitle = sectionName + "  " + formatMs;
                SpannableString spannableString = new SpannableString(topVdeioTitle);
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#999999"));
                AbsoluteSizeSpan ab = new AbsoluteSizeSpan(13, true);
                spannableString.setSpan(ab, topVdeioTitle.length() - formatMs.length(), topVdeioTitle.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(colorSpan, topVdeioTitle.length() - formatMs.length(), topVdeioTitle.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                answerVideoname.setText(spannableString);*/
                if (!TextUtils.isEmpty(username)) {
                    answerUsernickname.setText(username);
                }
                if (!TextUtils.isEmpty(sectionName)) {
                    answerQuestionSectionname.setText(sectionName);
                }
                if (!TextUtils.isEmpty(quiz)) {
                    answerUserquestion.setText(quiz);
                }
                if (!TextUtils.isEmpty(createsTime)) {
                    answerQuestionCreatetime.setText(createsTime);
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
                    //TODO 老师头像
                    answerTeachericon.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_account_btn));
                } else {
                    //TODO 老师头像
                    RequestOptions requestOptions = new RequestOptions()
                            .placeholder(R.mipmap.icon_default)
                            .error(R.mipmap.image_loaderror)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                    GlideUtils.load(context, dataReply.getHead_img(), answerTeachericon, requestOptions);
                    TransferConfig headConfig2 = TransferConfig.build()
                            .setMissPlaceHolder(R.mipmap.icon_default)
                            .setErrorPlaceHolder(R.mipmap.icon_default)
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
            if (loadinglayout != null) {
                loadinglayout.showContent();
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
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
        if (loadinglayout != null) {
            loadinglayout.showError();
        }
    }

    @Override
    public void getAnswerListData(AnswerListDataBean dataBean) {
        //TODO nothing
    }

    @OnClick(R.id.top_linear)
    public void onViewClicked() {
        //查看视频
        AnswerDetailBean.DataBeanX data = answerDetailBean.getData();
        AnswerDetailBean.DataBeanX.TitleBean bean = data.getTitleBean();

        String packageId = bean.getPackage_id();
        String sectionId = bean.getSection_id();
        String videoId = bean.getVideo_id();
        String courseId = bean.getCourse_id();
        if (TextUtils.isEmpty(packageId) || TextUtils.isEmpty(sectionId) || TextUtils.isEmpty(videoId) || TextUtils.isEmpty(courseId)) {
            ToastUtil.showBottomShortText(this, getResources().getString(R.string.miss_params));
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putInt(Constant.COURSE_PACKAGE_ID, Integer.parseInt(packageId));//包
        bundle.putInt(Constant.COURSE_BUY_STATE, bean.getIs_purchase());//购买状态
        bundle.putString(Constant.COURSE_SOURCE, Constant.WATCH_ANSWERDETAILED);//播放源
        bundle.putInt(Constant.COURSE_UN_CON, 1);
        bundle.putInt(Constant.SECTION_ID, Integer.parseInt(sectionId));//章
        bundle.putString(Constant.COURSE_ALIYUNVID, bean.getVideoId());//阿里VID
        bundle.putInt(Constant.VIDEO_ID, Integer.parseInt(videoId));//小节ID
        bundle.putInt(Constant.COURSE_ID, Integer.parseInt(courseId));//课ID
        startActivity(VideoPlayPageActivity.class, bundle);
    }
}
