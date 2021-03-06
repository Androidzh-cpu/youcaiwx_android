package com.ucfo.youcaiwx.module.answer;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.answer.AnsweringCourseDetailAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.answer.AnsweringCourseDetailsBean;
import com.ucfo.youcaiwx.entity.answer.AnsweringQuestionDetailsBean;
import com.ucfo.youcaiwx.module.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.module.course.player.utils.TimeFormater;
import com.ucfo.youcaiwx.module.questionbank.activity.ComplainActivity;
import com.ucfo.youcaiwx.module.questionbank.activity.QuestionAskQuestionActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.answer.AnsweringCloselyDetailPresenter;
import com.ucfo.youcaiwx.presenter.view.answer.IAnsweringCloselyDetailView;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-11-12 上午 10:09
 * Package: com.ucfo.youcaiwx.module.answer
 * FileName: AnsweringCourseActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 课程答疑(追问版)
 */
public class AnsweringCourseActivity extends BaseActivity implements IAnsweringCloselyDetailView, View.OnClickListener {
    private TextView titlebarMidtitle;
    private TextView titlebarRighttitle;
    private Toolbar titlebarToolbar;
    private View showline;
    private TextView txtTitle;
    private LinearLayout topTitle;
    private LoadingLayout loadinglayout;
    private Button btnNext;
    private RecyclerView recyclerview;

    private int user_id;
    private AnsweringCloselyDetailPresenter answeringCloselyDetailPresenter;
    private Bundle bundle;
    private String type;
    private int answer_id, answer_replystatus;
    private List<AnsweringCourseDetailsBean.DataBean.ReplyBean> list;
    private AnsweringCourseDetailsBean.DataBean.TitleBean titleBean;
    private AnsweringCourseDetailAdapter courseDetailAdapter;
    private SharedPreferencesUtils sharedPreferencesUtils;

    @Override
    protected void onResume() {
        super.onResume();
        loadNet();
    }

    private void loadNet() {
        //根据传递的问答ID获取问答详情
        if (answeringCloselyDetailPresenter != null) {
            answeringCloselyDetailPresenter.getCourseAnswerDetail(answer_id, user_id);
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_answering_course;
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

    @Override
    protected void initView(Bundle savedInstanceState) {
        titlebarMidtitle = (TextView) findViewById(R.id.titlebar_midtitle);
        titlebarRighttitle = (TextView) findViewById(R.id.titlebar_righttitle);
        titlebarToolbar = (Toolbar) findViewById(R.id.titlebar_toolbar);
        showline = (View) findViewById(R.id.showline);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        topTitle = (LinearLayout) findViewById(R.id.top_Title);
        topTitle.setOnClickListener(this);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
        loadinglayout = (LoadingLayout) findViewById(R.id.loadinglayout);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initData() {
        super.initData();
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(this);
        user_id = Integer.parseInt(sharedPreferencesUtils.getString(Constant.USER_ID, "0"));
        //注册网络
        answeringCloselyDetailPresenter = new AnsweringCloselyDetailPresenter();
        answeringCloselyDetailPresenter.setAnsweringCloselyDetailView(this);

        list = new ArrayList<>();

        bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getString(Constant.TYPE, Constant.MESSAGE_ANSWER);
            answer_id = bundle.getInt(Constant.ANSWER_ID, 0);//获取传递的问答ID
            answer_replystatus = bundle.getInt(Constant.STATUS, 0);//获取传递的问答状态

            if (TextUtils.equals(type, Constant.MESSAGE_ANSWER)) {
                topTitle.setVisibility(View.GONE);
            } else {
                topTitle.setVisibility(View.VISIBLE);
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNet();
            }
        });
    }

    @Override
    public void initQuestionAnsweringDetail(AnsweringQuestionDetailsBean data) {
        //TODO nothing
    }

    /**
     * 课程答疑详情
     */
    @Override
    public void initCourseAnsweringDetail(AnsweringCourseDetailsBean data) {
        if (data != null) {
            AnsweringCourseDetailsBean.DataBean dataBean = data.getData();
            if (dataBean != null) {
                List<AnsweringCourseDetailsBean.DataBean.ReplyBean> beanList = dataBean.getReply();
                AnsweringCourseDetailsBean.DataBean.TitleBean titleBean = dataBean.getTitle();
                list.clear();
                list.addAll(beanList);
                initTopTitle(titleBean);
                initAdapter();

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
     * 顶部标题栏
     */
    private void initTopTitle(AnsweringCourseDetailsBean.DataBean.TitleBean bean) {
        if (bean != null) {
            this.titleBean = bean;
            String title = bean.getTitle();
            String videoTime = bean.getVideo_time();
            String formatMs = "";
            if (!TextUtils.isEmpty(videoTime)) {
                formatMs = TimeFormater.formatSeconds(Integer.parseInt(videoTime));
            }
            String finalString = String.valueOf(title + "  " + formatMs);
            txtTitle.setText(finalString);
        }
    }

    /**
     * 适配器
     */
    private void initAdapter() {
        if (courseDetailAdapter == null) {
            courseDetailAdapter = new AnsweringCourseDetailAdapter(list, this);
            recyclerview.setAdapter(courseDetailAdapter);
        } else {
            courseDetailAdapter.notifyChange(list);
        }
        //投诉
        courseDetailAdapter.setComplainClick(new AnsweringCourseDetailAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                AnsweringCourseDetailsBean.DataBean.ReplyBean bean = list.get(position);
                if (TextUtils.equals(bean.getUser_self(), String.valueOf("1"))) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.ANSWER_ID, String.valueOf(answer_id));
                    bundle.putString(Constant.ANSWER_TYPE, Constant.ANSWER_TYPE_COURSE);
                    startActivity(ComplainActivity.class, bundle);
                }
            }
        });
        if (!list.isEmpty()) {
            AnsweringCourseDetailsBean.DataBean.ReplyBean bean = list.get(0);
            if (TextUtils.equals(bean.getUser_self(), String.valueOf("1"))) {
                //用户的答疑
                String isClose = bean.getIs_close();
                if (TextUtils.equals(isClose, String.valueOf("1"))) {
                    btnNext.setVisibility(View.VISIBLE);
                } else {
                    btnNext.setVisibility(View.GONE);
                }
            } else {
                //他人答疑不可追问
                btnNext.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showLoading() {

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
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.top_Title:
                String packageId = titleBean.getPackage_id();
                String sectionId = titleBean.getSection_id();
                String videoId = titleBean.getVideo_id();
                String courseId = titleBean.getCourse_id();
                String isPurchase = titleBean.getIs_purchase();
                if (TextUtils.isEmpty(packageId) || TextUtils.isEmpty(sectionId) || TextUtils.isEmpty(videoId) || TextUtils.isEmpty(courseId) || TextUtils.isEmpty(isPurchase)) {
                    ToastUtil.showBottomShortText(this, getResources().getString(R.string.miss_params));
                    return;
                }
                bundle.putInt(Constant.COURSE_PACKAGE_ID, Integer.parseInt(packageId));//包
                bundle.putInt(Constant.COURSE_BUY_STATE, Integer.parseInt(isPurchase));//购买状态
                bundle.putString(Constant.COURSE_SOURCE, Constant.WATCH_ANSWERDETAILED);//播放源
                bundle.putInt(Constant.COURSE_UN_CON, 1);
                bundle.putInt(Constant.SECTION_ID, Integer.parseInt(sectionId));//章
                bundle.putString(Constant.COURSE_ALIYUNVID, titleBean.getVideoId());//阿里VID
                bundle.putInt(Constant.VIDEO_ID, Integer.parseInt(videoId));//小节ID
                bundle.putInt(Constant.COURSE_ID, Integer.parseInt(courseId));//课ID
                startActivity(VideoPlayPageActivity.class, bundle);
                break;
            case R.id.btn_next:
                //TODO 追问
                bundle.putString(Constant.TYPE, Constant.TYPE_TRACE);
                bundle.putString(Constant.ANSWER_ID, String.valueOf(answer_id));
                bundle.putString(Constant.ANSWER_TYPE, Constant.ANSWER_TYPE_COURSE);
                startActivity(QuestionAskQuestionActivity.class, bundle);
                break;
            default:
                //TODO nothing
                break;
        }
    }
}
