package com.ucfo.youcaiwx.module.user.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.answer.CourseAnswerListAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.answer.AnswerListDataBean;
import com.ucfo.youcaiwx.module.answer.AnsweringCourseActivity;
import com.ucfo.youcaiwx.module.answer.AnsweringQuestionActivity;
import com.ucfo.youcaiwx.module.user.activity.MineAnswerQuestionActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.MineAnswerPresenter;
import com.ucfo.youcaiwx.presenter.view.user.IMineAnswerView;
import com.ucfo.youcaiwx.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-6-18.  下午 7:18
 * FileName: FragmentMineAnswer
 * Description:TODO 课程答疑
 */
public class FragmentMineAnswer extends BaseFragment implements IMineAnswerView {
    private RecyclerView recyclerview;
    private LoadingLayout loadinglayout;
    private SmartRefreshLayout refreshlayout;

    private MineAnswerQuestionActivity mineAnswerQuestionActivity;
    private int user_id;
    private ArrayList<AnswerListDataBean.DataBean> list;
    private MineAnswerPresenter mineAnswerPresenter;
    private int pageIndex = 1;
    private int type = 1;

    private CourseAnswerListAdapter courseAnswerListAdapter;

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_courseanswer;
    }

    @Override
    protected void initView(View itemView) {
        recyclerview = (RecyclerView) itemView.findViewById(R.id.recyclerview);
        loadinglayout = (LoadingLayout) itemView.findViewById(R.id.loadinglayout);
        refreshlayout = (SmartRefreshLayout) itemView.findViewById(R.id.refreshlayout);


        FragmentActivity activity = getActivity();
        if (activity instanceof MineAnswerQuestionActivity) {
            mineAnswerQuestionActivity = (MineAnswerQuestionActivity) getActivity();
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        int topBottom = DensityUtil.dip2px(getActivity(), 6);
        recyclerview.addItemDecoration(new SpacesItemDecoration(0, topBottom, Color.TRANSPARENT));
        recyclerview.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        user_id = SharedPreferencesUtils.getInstance(mineAnswerQuestionActivity).getInt(Constant.USER_ID, 0);
        list = new ArrayList<>();

        mineAnswerPresenter = new MineAnswerPresenter(this);
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mineAnswerPresenter.getMineAnswerList(type, user_id, 10, pageIndex);
            }
        });
        refreshlayout.setDisableContentWhenRefresh(true);
        refreshlayout.setDisableContentWhenLoading(true);
        refreshlayout.setEnableAutoLoadMore(false);
        refreshlayout.setEnableNestedScroll(true);
        refreshlayout.setEnableOverScrollBounce(true);
        refreshlayout.setEnableLoadMore(true);
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                mineAnswerPresenter.getMineAnswerList(type, user_id, 10, pageIndex);
            }
        });
        refreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex += 1;
                mineAnswerPresenter.getMineAnswerList(type, user_id, 10, pageIndex);
            }
        });
    }

    @Override
    protected void onLazyLoadOnce() {
        super.onLazyLoadOnce();
        mineAnswerPresenter.getMineAnswerList(type, user_id, 10, pageIndex);
    }

    @Override
    public void getMineAnswerList(AnswerListDataBean dataBean) {
        if (dataBean != null) {//TODO 分页加载或者初次加载都成功情况下
            if (dataBean.getData() != null && dataBean.getData().size() > 0) {
                List<AnswerListDataBean.DataBean> beanList = dataBean.getData();
                if (pageIndex == 1) {//初次加载或刷新操作
                    list.clear();
                    list.addAll(beanList);
                } else {//加载更多
                    if (beanList.size() > 0) {
                        list.addAll(beanList);
                    }
                }
                initAdapter();
                if (loadinglayout != null) {
                    loadinglayout.showContent();
                }
            } else {//TODO 加载的数据为0的情况
                if (pageIndex == 1) {//初次加载或刷新
                    if (list != null && list.size() > 0) {
                        if (courseAnswerListAdapter != null) {
                            courseAnswerListAdapter.notifyDataSetChanged();
                        }
                    } else {
                        if (loadinglayout != null) {
                            loadinglayout.showEmpty();
                        }
                    }
                } else {//架子啊更多
                    if (list != null && list.size() > 0) {
                        if (courseAnswerListAdapter != null) {
                            courseAnswerListAdapter.notifyDataSetChanged();
                        }
                        ToastUtil.showBottomShortText(context, getResources().getString(R.string.noMoreData));
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

    private void initAdapter() {
        if (courseAnswerListAdapter == null) {
            courseAnswerListAdapter = new CourseAnswerListAdapter(list, getActivity(), type);
            recyclerview.setAdapter(courseAnswerListAdapter);
        } else {
            courseAnswerListAdapter.notifyChange(list);
        }
        courseAnswerListAdapter.setItemClick(new CourseAnswerListAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                if (!fastClick(2000)) {
                    if (type == 1) {//课程
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constant.ANSWER_ID, list.get(position).getId());
                        bundle.putInt(Constant.STATUS, list.get(position).getReply_status());
                        bundle.putString(Constant.TYPE, Constant.MINE_ANSWER);
                        //startActivity(CourseAnswerDetailActivity.class, bundle);
                        startActivity(AnsweringCourseActivity.class, bundle);
                    } else {//题库
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constant.ANSWER_ID, list.get(position).getId());
                        bundle.putInt(Constant.STATUS, list.get(position).getReply_status());
                        bundle.putString(Constant.TYPE, Constant.MINE_ANSWER);
                        //startActivity(QuestionAnswerDetailActivity.class, bundle);
                        startActivity(AnsweringQuestionActivity.class, bundle);
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
