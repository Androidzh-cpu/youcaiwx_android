package com.ucfo.youcaiwx.module.questionbank.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.questionbank.QuestionOnRecordAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.questionbank.QuestionOnRecordBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.questionbank.QuestionBankRecordPresenter;
import com.ucfo.youcaiwx.presenter.view.questionbank.IQuestionOnRecordView;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:QuestionsOnRecordActivity
 * Time:2019-4-30   上午 9:55
 * Detail: TODO 答题记录
 */
public class QuestionsOnRecordActivity extends BaseActivity implements IQuestionOnRecordView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    private QuestionBankRecordPresenter questionBankRecordPresenter;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int user_id, course_id;
    private List<QuestionOnRecordBean.DataBean> list;
    private QuestionOnRecordAdapter questionOnRecordAdapter;
    private int pageIndex = 1;
    private Bundle bundle;

    @Override
    protected void onResume() {
        super.onResume();
        if (questionBankRecordPresenter != null) {
            questionBankRecordPresenter.getQestionBankRecordData(user_id, course_id, pageIndex);
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
        titlebarMidtitle.setText(getResources().getString(R.string.question_title_record));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        super.initData();
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(this);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);

        list = new ArrayList<>();
        questionBankRecordPresenter = new QuestionBankRecordPresenter(this);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            course_id = bundle.getInt(Constant.COURSE_ID, 0);
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
        //重新加载
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionBankRecordPresenter.getQestionBankRecordData(user_id, course_id, pageIndex);
            }
        });
        //刷新加载事件
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                questionBankRecordPresenter.getQestionBankRecordData(user_id, course_id, pageIndex);
            }
        });
        refreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex += 1;
                questionBankRecordPresenter.getQestionBankRecordData(user_id, course_id, pageIndex);
            }
        });
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_questions_on_record;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    /**
     * Description:QuestionsOnRecordActivity
     * Time:2019-4-30   上午 10:18
     * Detail: TODO 获取答题记录
     */
    @Override
    public void getQuestionRecordData(QuestionOnRecordBean dataBean) {
        if (dataBean != null) {//TODO 分页加载或者初次加载都成功情况下
            if (dataBean.getData() != null && dataBean.getData().size() > 0) {
                List<QuestionOnRecordBean.DataBean> data = dataBean.getData();
                if (pageIndex == 1) {//初次加载或刷新操作
                    list.clear();
                    list.addAll(data);
                } else {//加载更多
                    if (data.size() > 0) {
                        list.addAll(data);
                    }
                }
                initAdapter();
                if (loadinglayout != null) {
                    loadinglayout.showContent();
                }
            } else {//TODO 加载的数据为0的情况
                if (pageIndex == 1) {//初次加载或刷新
                    if (list != null && list.size() > 0) {
                        if (questionOnRecordAdapter != null) {
                            questionOnRecordAdapter.notifyDataSetChanged();
                        }
                    } else {
                        if (loadinglayout != null) {
                            loadinglayout.showEmpty();
                        }
                    }
                } else {//架子啊更多
                    if (list != null && list.size() > 0) {
                        if (questionOnRecordAdapter != null) {
                            questionOnRecordAdapter.notifyDataSetChanged();
                        }
                        showToast(getResources().getString(R.string.noMoreData));
                    } else {
                        if (loadinglayout != null) {
                            loadinglayout.showEmpty();
                        }
                    }
                }
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }

        refreshlayout.finishRefresh();
        refreshlayout.finishLoadMore();
    }

    //初始化适配器
    private void initAdapter() {
        if (questionOnRecordAdapter == null) {
            questionOnRecordAdapter = new QuestionOnRecordAdapter(this, list);
            recyclerview.setAdapter(questionOnRecordAdapter);
        } else {
            questionOnRecordAdapter.notifyChange(list);
        }
        //状态按钮的点击事件
        questionOnRecordAdapter.setItemClick(new QuestionOnRecordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                QuestionOnRecordBean.DataBean bean = list.get(position);
                String state = bean.getState();
                if (!TextUtils.isEmpty(state)) {
                    int parseInt = Integer.parseInt(state);
                    switch (parseInt) {
                        case 1:
                            //TODO  1成绩统计
                            bundle.putInt(Constant.PAPER_ID, bean.getId());
                            bundle.putInt(Constant.COURSE_ID, course_id);
                            startActivity(ResultsStatisticalActivity.class, bundle);
                            break;
                        case 2:
                            //TODO 2继续做题
                            String paperType = bean.getPaper_type();//1练习模式,2考试模式
                            if (TextUtils.equals(paperType, String.valueOf(1))) {
                                bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_P);//练习模式
                            } else {
                                bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_E);//考试模式
                            }
                            if (TextUtils.isEmpty(bean.getPaper_id())) {
                                showToast(getResources().getString(R.string.data_error));
                                return;
                            }
                            bundle.putInt(Constant.PLATE_ID, Constant.PLATE_11);
                            bundle.putInt(Constant.CONTINUE_PLATE, bean.getPlate_id());//继续做题后续操作所需板块
                            bundle.putInt(Constant.COURSE_ID, bean.getCourse_id());//专业
                            bundle.putInt(Constant.SECTION_ID, bean.getSection_id());//章
                            bundle.putInt(Constant.KNOB_ID, Integer.parseInt(bean.getKnob_id()));//节
                            bundle.putString(Constant.KNOW_ID, bean.getKnow_id());//知识点
                            bundle.putInt(Constant.MOCK_ID, Integer.parseInt(bean.getMock_id()));//组卷
                            bundle.putInt(Constant.ID, bean.getId());//试卷ID,用于取题
                            bundle.putInt(Constant.PAPER_ID, Integer.parseInt(bean.getPaper_id()));//试卷ID,用于取题
                            startActivity(TESTMODEActivity.class, bundle);
                            break;
                        case 3://TODO  3查看试题
                            bundle.putInt(Constant.PLATE_ID, Constant.PLATE_12);//做题页板块,主要用于区分功能
                            bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_D);//论述题模式
                            bundle.putInt(Constant.PAPER_ID, bean.getId());//答题记录列表里的ID
                            bundle.putInt(Constant.COURSE_ID, bean.getCourse_id());//专业
                            startActivity(TESTMODEActivity.class, bundle);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
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
