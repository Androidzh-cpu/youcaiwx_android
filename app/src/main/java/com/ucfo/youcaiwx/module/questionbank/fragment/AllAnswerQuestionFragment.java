package com.ucfo.youcaiwx.module.questionbank.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hitomi.tilibrary.transfer.Transferee;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.answer.QuestionAnswerListAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.answer.QuestionAnswerDetailBean;
import com.ucfo.youcaiwx.entity.answer.QuestionAnswerListBean;
import com.ucfo.youcaiwx.module.answer.AnsweringQuestionActivity;
import com.ucfo.youcaiwx.module.questionbank.activity.QuestionAnswerActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.answer.QuestionAnswerPresenter;
import com.ucfo.youcaiwx.presenter.view.answer.IQuestionAnswerView;
import com.ucfo.youcaiwx.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-5-30.  下午 2:00
 * FileName: AllAnswerQuestionFragment
 * Description:TODO 全部答疑
 */
public class AllAnswerQuestionFragment extends BaseFragment implements IQuestionAnswerView {
    private RecyclerView recyclerview;
    private LoadingLayout loadinglayout;
    private SmartRefreshLayout refreshlayout;

    private QuestionAnswerActivity questionAnswerActivity;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int user_id, question_id;
    private QuestionAnswerPresenter questionAnswerPresenter;
    private ArrayList<QuestionAnswerListBean.DataBean> list;
    private QuestionAnswerListAdapter questionAnswerListAdapter;
    private Transferee transferee;

    @Override
    protected int setContentView() {
        return R.layout.fragment_allanswerquestion;
    }

    @Override
    protected void initView(View view) {
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        refreshlayout = (SmartRefreshLayout) view.findViewById(R.id.refreshlayout);
        loadinglayout = (LoadingLayout) view.findViewById(R.id.loadinglayout);

        if (getContext() != null) {
            transferee = Transferee.getDefault(context);
        } else {
            transferee = Transferee.getDefault(getActivity());
        }

        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity instanceof QuestionAnswerActivity) {
            questionAnswerActivity = (QuestionAnswerActivity) fragmentActivity;
            question_id = questionAnswerActivity.getQuestion_id();
        }
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(getContext());
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        //注册业务层
        questionAnswerPresenter = new QuestionAnswerPresenter(this);
        //重试按钮
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionAnswerPresenter.getQuestionAnswerList(user_id, question_id, 1);
            }
        });

        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                questionAnswerPresenter.getQuestionAnswerList(user_id, question_id, 1);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(false);
        recyclerview.setLayoutManager(layoutManager);
        int topBottom = DensityUtil.dip2px(questionAnswerActivity, 5);
        recyclerview.addItemDecoration(new SpacesItemDecoration(0, topBottom, Color.TRANSPARENT));
        recyclerview.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
    }

    @Override
    protected void onVisibleToUser() {
        super.onVisibleToUser();
        //TODO  获取问答列表
        if (questionAnswerPresenter != null) {
            questionAnswerPresenter.getQuestionAnswerList(user_id, question_id, 1);
        }
    }

    //TODO 获取答疑列表
    @Override
    public void getAnswerList(QuestionAnswerListBean data) {
        if (data != null) {
            if (data.getData() != null && data.getData().size() > 0) {
                List<QuestionAnswerListBean.DataBean> dataBeanList = data.getData();

                list.clear();
                list.addAll(dataBeanList);

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
        if (refreshlayout != null) {
            refreshlayout.finishRefresh();
            refreshlayout.finishLoadMore();
        }
    }

    @Override
    public void getQuestionAnswerDetail(QuestionAnswerDetailBean bean) {
        //TODO nothing
    }

    //TODO 设置适配器
    private void initAdapter() {
        if (questionAnswerListAdapter == null) {
            if (transferee == null) {
                transferee = Transferee.getDefault(getContext());
            }
            questionAnswerListAdapter = new QuestionAnswerListAdapter(list, getContext(), transferee);
            recyclerview.setAdapter(questionAnswerListAdapter);
        } else {
            questionAnswerListAdapter.notifyChange(list);
        }
        //查看详情点击事件
        questionAnswerListAdapter.setItemClick(new QuestionAnswerListAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.ANSWER_ID, list.get(position).getId());
                bundle.putInt(Constant.STATUS, list.get(position).getReply_status());
                bundle.putString(Constant.TYPE, Constant.QUESTION_ANSWER);
                startActivity(AnsweringQuestionActivity.class, bundle);
            }
        });
    }

    @Override
    public void showLoading() {
        setProcessLoading(null, true);
    }

    @Override
    public void showLoadingFinish() {
        dismissPorcessDelayed(300);
    }

    @Override
    public void showError() {
        if (loadinglayout != null) {
            loadinglayout.showError();
        }
    }

}
