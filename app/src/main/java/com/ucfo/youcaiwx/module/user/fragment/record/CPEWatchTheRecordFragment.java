package com.ucfo.youcaiwx.module.user.fragment.record;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.user.MineCPEWatchRecordAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.user.MineCPEWatchRecordBean;
import com.ucfo.youcaiwx.entity.user.MineWatchRecordBean;
import com.ucfo.youcaiwx.module.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.WatchTheRecordPresenter;
import com.ucfo.youcaiwx.presenter.view.user.IWatchTheRecordView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-12-30.  上午 11:01
 * Package: com.ucfo.youcaiwx.module.user.fragment.record
 * FileName: CPEWatchTheRecordFragment
 * Description:后续教育的观看记录(拓展性一点都没有,也是服了)
 */
public class CPEWatchTheRecordFragment extends BaseFragment implements IWatchTheRecordView {

    private RecyclerView recyclerview;
    private LoadingLayout loadinglayout;
    private SmartRefreshLayout refreshlayout;

    private List<MineCPEWatchRecordBean.DataBean> list;
    private int user_id;
    private WatchTheRecordPresenter watchTheRecordPresenter;
    private MineCPEWatchRecordAdapter mineCPEWatchRecordAdapter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_watchtherecord;
    }

    @Override
    protected void initView(View itemView) {
        recyclerview = (RecyclerView) itemView.findViewById(R.id.recyclerview);
        loadinglayout = (LoadingLayout) itemView.findViewById(R.id.loadinglayout);
        refreshlayout = (SmartRefreshLayout) itemView.findViewById(R.id.refreshlayout);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setNestedScrollingEnabled(false);

        user_id = SharedPreferencesUtils.getInstance(getContext()).getInt(Constant.USER_ID, 0);

        watchTheRecordPresenter = new WatchTheRecordPresenter(this);

        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadNeTData();
            }
        });
    }

    @Override
    protected void onLazyLoadOnce() {
        super.onLazyLoadOnce();
        loadNeTData();
    }

    private void loadNeTData() {
        if (watchTheRecordPresenter != null) {
            watchTheRecordPresenter.getMineCPEWatcheRecordList(user_id);
        }
    }

    @Override
    public void getMineWatchRecord(MineWatchRecordBean data) {
        //TODO nothing
    }

    @Override
    public void getMineCPEWatchRecord(MineCPEWatchRecordBean data) {
        if (data != null) {
            if (data.getData() != null && data.getData().size() > 0) {
                List<MineCPEWatchRecordBean.DataBean> beanList = data.getData();
                list.clear();
                list.addAll(beanList);

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
        refreshlayout.finishRefresh();
        refreshlayout.finishLoadMore();
    }

    private void initAdapter() {
        if (mineCPEWatchRecordAdapter == null) {
            mineCPEWatchRecordAdapter = new MineCPEWatchRecordAdapter(list, getContext());
            recyclerview.setAdapter(mineCPEWatchRecordAdapter);
        } else {
            mineCPEWatchRecordAdapter.notifyChange(list);
        }
        mineCPEWatchRecordAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MineCPEWatchRecordBean.DataBean bean = list.get(position);
                if (fastClick(1000)) {
                    return;
                }
                String packageId = bean.getPackage_id();
                String sectionId = bean.getSection_id();
                String videoId = bean.getVideo_id();
                String courseId = bean.getCourse_id();
                String isPurchase = bean.getIs_purchase();
                if (TextUtils.isEmpty(packageId) || TextUtils.isEmpty(videoId) || TextUtils.isEmpty(courseId) || TextUtils.isEmpty(isPurchase)) {
                    showToast(getResources().getString(R.string.miss_params));
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString(Constant.COURSE_SOURCE, Constant.WATCH_RECORD);
                bundle.putString(Constant.COURSE_SOURCE_DEPUTY, Constant.WATCH_CPE_RECORD);
                bundle.putInt(Constant.COURSE_PACKAGE_ID, Integer.parseInt(bean.getPackage_id()));
                bundle.putInt(Constant.COURSE_BUY_STATE, Integer.parseInt(isPurchase));
                bundle.putInt(Constant.COURSE_UN_CON, Integer.parseInt("2"));
                bundle.putInt(Constant.SECTION_ID, Integer.parseInt(sectionId));
                bundle.putString(Constant.COURSE_VIDEOID, bean.getVideoId());
                bundle.putInt(Constant.VIDEO_ID, Integer.parseInt(videoId));
                bundle.putInt(Constant.COURSE_ID, Integer.parseInt(courseId));
                startActivity(VideoPlayPageActivity.class, bundle);
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
