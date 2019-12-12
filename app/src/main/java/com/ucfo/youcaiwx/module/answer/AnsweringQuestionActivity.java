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
import com.ucfo.youcaiwx.adapter.answer.AnsweringQuestionDetailAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.answer.AnsweringCourseDetailsBean;
import com.ucfo.youcaiwx.entity.answer.AnsweringQuestionDetailsBean;
import com.ucfo.youcaiwx.module.questionbank.activity.ComplainActivity;
import com.ucfo.youcaiwx.module.questionbank.activity.QuestionAnswerActivity;
import com.ucfo.youcaiwx.module.questionbank.activity.QuestionAskQuestionActivity;
import com.ucfo.youcaiwx.module.questionbank.activity.TESTMODEActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.answer.AnsweringCloselyDetailPresenter;
import com.ucfo.youcaiwx.presenter.view.answer.IAnsweringCloselyDetailView;
import com.ucfo.youcaiwx.utils.ActivityUtil;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-11-13 上午 9:34
 * Package: com.ucfo.youcaiwx.module.answer
 * FileName: AnsweringQuestionActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 题库答疑详情(追问)
 */
public class AnsweringQuestionActivity extends BaseActivity implements IAnsweringCloselyDetailView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.showline)
    View showline;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.top_Title)
    LinearLayout topTitle;
    @BindView(R.id.btn_Next)
    Button btnNext;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private int user_id;
    private AnsweringCloselyDetailPresenter answeringCloselyDetailPresenter;
    private List<AnsweringQuestionDetailsBean.DataBean.ReplyBean> list;
    private AnsweringQuestionDetailsBean.DataBean.TopicsBean titleBean;
    private String type;
    private int answer_id;
    private int answer_replystatus;
    private AnsweringQuestionDetailAdapter answeringQuestionDetailAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        loadNetDetail();
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_answering_question;
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
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initData() {
        super.initData();
        user_id = Integer.parseInt(SharedPreferencesUtils.getInstance(this).getString(Constant.USER_ID, "0"));
        answeringCloselyDetailPresenter = new AnsweringCloselyDetailPresenter();
        answeringCloselyDetailPresenter.setAnsweringCloselyDetailView(this);
        list = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
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
                loadNetDetail();
            }
        });
    }

    private void loadNetDetail() {
        if (answeringCloselyDetailPresenter != null) {
            answeringCloselyDetailPresenter.getQuestionAnswerDetail(answer_id, user_id);
        }
    }

    @OnClick({R.id.top_Title, R.id.btn_Next})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.top_Title:
                //查看试题
                if (TextUtils.equals(type, Constant.QUESTION_ANSWER)) {
                    //TODO 题库答疑直接返回试题解析页
                    ActivityUtil.getInstance().finishActivity(QuestionAnswerActivity.class);
                    finish();
                } else {
                    //查看试题
                    String questionId = titleBean.getQuestion_id();
                    if (TextUtils.isEmpty(questionId)) {
                        ToastUtil.showBottomShortText(this, getResources().getString(R.string.miss_params));
                        return;
                    }

                    bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_A);
                    bundle.putInt(Constant.PLATE_ID, Constant.PLATE_16);
                    bundle.putString(Constant.QUESTION_ID, questionId);
                    startActivity(TESTMODEActivity.class, bundle);
                }
                break;
            case R.id.btn_Next:
                //追问
                bundle.putString(Constant.TYPE, Constant.TYPE_TRACE);
                bundle.putString(Constant.ANSWER_ID, String.valueOf(answer_id));
                bundle.putString(Constant.ANSWER_TYPE, Constant.ANSWER_TYPE_QUESTION);
                startActivity(QuestionAskQuestionActivity.class, bundle);
                break;
        }
    }

    @Override
    public void initCourseAnsweringDetail(AnsweringCourseDetailsBean data) {
        //TODO nothing
    }

    @Override
    public void initQuestionAnsweringDetail(AnsweringQuestionDetailsBean data) {
        if (data != null) {
            if (data.getData() != null) {
                AnsweringQuestionDetailsBean.DataBean dataBean = data.getData();
                List<AnsweringQuestionDetailsBean.DataBean.ReplyBean> replyBeanList = dataBean.getReply();
                AnsweringQuestionDetailsBean.DataBean.TopicsBean topicsBean = dataBean.getTopics();

                list.clear();
                list.addAll(replyBeanList);
                initTopTitle(topicsBean);
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

    private void initAdapter() {
        if (null == answeringQuestionDetailAdapter) {
            answeringQuestionDetailAdapter = new AnsweringQuestionDetailAdapter(list, this);
        } else {
            answeringQuestionDetailAdapter.notifyDataSetChanged();
        }
        recyclerview.setAdapter(answeringQuestionDetailAdapter);

        //投诉
        answeringQuestionDetailAdapter.setComplainClick(new AnsweringQuestionDetailAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                AnsweringQuestionDetailsBean.DataBean.ReplyBean replyBean = list.get(position);
                String userSelf = replyBean.getUser_self();
                if (TextUtils.equals(userSelf, String.valueOf("1"))) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.ANSWER_ID, String.valueOf(answer_id));
                    bundle.putString(Constant.ANSWER_TYPE, Constant.ANSWER_TYPE_QUESTION);
                    startActivity(ComplainActivity.class, bundle);
                }
            }
        });
        if (!list.isEmpty()) {
            AnsweringQuestionDetailsBean.DataBean.ReplyBean bean = list.get(0);
            String userSelf = bean.getUser_self();
            if (TextUtils.equals(userSelf, String.valueOf("1"))) {
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

    private void initTopTitle(AnsweringQuestionDetailsBean.DataBean.TopicsBean topicsBean) {
        if (topicsBean != null) {
            this.titleBean = topicsBean;
            String title = topicsBean.getTopic();
            txtTitle.setText(title);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showLoadingFinish() {

    }

    @Override
    public void showError() {
        if (loadinglayout != null) {
            loadinglayout.showError();
        }
    }
}
