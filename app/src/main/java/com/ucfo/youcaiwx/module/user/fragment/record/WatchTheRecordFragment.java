package com.ucfo.youcaiwx.module.user.fragment.record;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.user.MineWatchRecordAdapter;
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
 * Time: 2019-12-30.  上午 11:00
 * Package: com.ucfo.youcaiwx.module.user.fragment.record
 * FileName: WatchTheRecordFragment
 * Description:TODO 我的观看记录
 */
public class WatchTheRecordFragment extends BaseFragment implements IWatchTheRecordView {
    private RecyclerView recyclerview;
    private LoadingLayout loadinglayout;
    private SmartRefreshLayout refreshlayout;

    private List<MineWatchRecordBean.DataBean> list;
    private int user_id;
    private MineWatchRecordAdapter mineWatchRecordAdapter;
    private WatchTheRecordPresenter watchTheRecordPresenter;


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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setNestedScrollingEnabled(false);

        list = new ArrayList<>();
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
            watchTheRecordPresenter.getMineWatcheRecordList(user_id);
        }
    }

    private void initadapter() {
        if (mineWatchRecordAdapter == null) {
            mineWatchRecordAdapter = new MineWatchRecordAdapter(list, getContext());
            recyclerview.setAdapter(mineWatchRecordAdapter);
        } else {
            mineWatchRecordAdapter.notifyChange(list);
        }
        mineWatchRecordAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MineWatchRecordBean.DataBean bean = list.get(position);
                if (!fastClick(1000)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.COURSE_PACKAGE_ID, bean.getPackage_id());//包
                    bundle.putInt(Constant.COURSE_BUY_STATE, bean.getIs_purchase());//已购买状态
                    bundle.putString(Constant.COURSE_SOURCE, Constant.WATCH_RECORD);//播放源
                    bundle.putInt(Constant.COURSE_UN_CON, bean.getIs_zhengke());
                    bundle.putInt(Constant.SECTION_ID, bean.getSection_id());//章
                    bundle.putString(Constant.COURSE_VIDEOID, bean.getVideoId());//阿里VID
                    bundle.putInt(Constant.VIDEO_ID, bean.getVideo_id());//小节ID
                    bundle.putInt(Constant.COURSE_ID, bean.getCourse_id());//课ID
                    startActivity(VideoPlayPageActivity.class, bundle);
                }
            }
        });
    }

    @Override
    public void getMineWatchRecord(MineWatchRecordBean data) {
        if (data != null) {
            if (data.getData() != null && data.getData().size() > 0) {
                List<MineWatchRecordBean.DataBean> beanList = data.getData();
                list.clear();
                list.addAll(beanList);

                initadapter();

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

    @Override
    public void getMineCPEWatchRecord(MineCPEWatchRecordBean data) {
        //TODO nothing
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
