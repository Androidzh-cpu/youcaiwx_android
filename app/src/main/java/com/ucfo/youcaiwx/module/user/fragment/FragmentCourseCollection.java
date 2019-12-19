package com.ucfo.youcaiwx.module.user.fragment;

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
import com.ucfo.youcaiwx.adapter.user.MineCourseAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.user.MineCourseBean;
import com.ucfo.youcaiwx.entity.user.MineCourseChildListBean;
import com.ucfo.youcaiwx.entity.user.MineCourseCollectionDirBean;
import com.ucfo.youcaiwx.entity.user.MineQuestionCollectionListBean;
import com.ucfo.youcaiwx.entity.user.ProjectListBean;
import com.ucfo.youcaiwx.module.user.activity.CourseCollectionChildActivity;
import com.ucfo.youcaiwx.module.user.activity.MineCollectionActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.MineCollectionPresenter;
import com.ucfo.youcaiwx.presenter.view.user.IMineCollectionView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-6-18.  下午 4:34
 * FileName: FragmentCourseCollection
 * Description:TODO 课程收藏-课程包列表
 */
public class FragmentCourseCollection extends BaseFragment implements IMineCollectionView {
    private RecyclerView recyclerview;
    private LoadingLayout loadinglayout;
    private SmartRefreshLayout refreshlayout;

    private int pageIndex = 1, user_id;
    private MineCollectionActivity mineCollectionActivity;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private MineCollectionPresenter mineCollectionPresenter;
    private ArrayList<MineCourseBean.DataBean> list;
    private MineCourseAdapter mineCourseAdapter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_coursecollection;
    }

    @Override
    protected void initView(View itemView) {
        recyclerview = (RecyclerView) itemView.findViewById(R.id.recyclerview);
        loadinglayout = (LoadingLayout) itemView.findViewById(R.id.loadinglayout);
        refreshlayout = (SmartRefreshLayout) itemView.findViewById(R.id.refreshlayout);

        loadinglayout.setEmptyImage(R.mipmap.icon_nodata);
        loadinglayout.setEmptyText(getResources().getString(R.string.mine_collection_coursenodata));

        FragmentActivity activity = getActivity();
        if (activity instanceof MineCollectionActivity) {
            mineCollectionActivity = (MineCollectionActivity) activity;
        }

        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(mineCollectionActivity);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mineCollectionActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        mineCollectionPresenter = new MineCollectionPresenter(this);

        //mineCollectionPresenter.getMineCoursePackageCollectionList(user_id, pageIndex);
        //设置重新加载事件
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mineCollectionPresenter.getMineCoursePackageCollectionList(user_id, pageIndex);
            }
        });
        refreshlayout.autoRefresh();
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                mineCollectionPresenter.getMineCoursePackageCollectionList(user_id, pageIndex);
            }
        });
        refreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex += 1;
                mineCollectionPresenter.getMineCoursePackageCollectionList(user_id, pageIndex);
            }
        });
    }

    @Override
    public void getPorjectList(ProjectListBean data) {
        //TODO nothing
    }

    @Override
    public void getMineCoursePackageCollectionList(MineCourseBean dataBean) {
        if (dataBean != null) {//TODO 分页加载或者初次加载都成功情况下
            if (dataBean.getData() != null && dataBean.getData().size() > 0) {
                List<MineCourseBean.DataBean> dataBeanList = dataBean.getData();
                if (pageIndex == 1) {//初次加载或刷新操作
                    list.clear();
                    list.addAll(dataBeanList);
                } else {//加载更多
                    if (dataBeanList.size() > 0) {
                        list.addAll(dataBeanList);
                    }
                }
                initAdapter();
                if (loadinglayout != null) {
                    loadinglayout.showContent();
                }
            } else {//TODO 加载的数据为0的情况
                if (pageIndex == 1) {//初次加载或刷新
                    if (list != null && list.size() > 0) {
                        if (mineCourseAdapter != null) {
                            mineCourseAdapter.notifyDataSetChanged();
                        }
                    } else {
                        if (loadinglayout != null) {
                            loadinglayout.showEmpty();
                        }
                    }
                } else {//架子啊更多
                    if (list != null && list.size() > 0) {
                        if (mineCourseAdapter != null) {
                            mineCourseAdapter.notifyDataSetChanged();
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
        if (refreshlayout != null) {
            refreshlayout.finishRefresh();
            refreshlayout.finishLoadMore();
        }
    }

    @Override
    public void getMineQuestioinCollectionList(MineQuestionCollectionListBean data) {
        //TODO nothing
    }

    @Override
    public void getMineCourseCollectionChildList(MineCourseChildListBean data) {
        //TODO nothing
    }

    @Override
    public void getCollectionDirList(MineCourseCollectionDirBean data) {
        //TODO nothing
    }

    //TODO 设置适配器
    private void initAdapter() {
        if (mineCourseAdapter == null) {
            mineCourseAdapter = new MineCourseAdapter(getActivity(), list);
            recyclerview.setAdapter(mineCourseAdapter);
        } else {
            mineCourseAdapter.notifyChange(list);
        }
        mineCourseAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!fastClick(2000)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.PACKAGE_ID, list.get(position).getPackage_id());
                    startActivity(CourseCollectionChildActivity.class, bundle);
                }
            }
        });
    }

    @Override
    public void showLoading() {
        //setProcessLoading(null, true);
    }

    @Override
    public void showLoadingFinish() {
        //dismissPorcess();
    }

    @Override
    public void showError() {
        if (loadinglayout != null) {
            loadinglayout.showError();
        }
    }
}
