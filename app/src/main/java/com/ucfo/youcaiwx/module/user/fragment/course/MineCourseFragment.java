package com.ucfo.youcaiwx.module.user.fragment.course;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.user.MineCourseAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.user.MineCPECourseBean;
import com.ucfo.youcaiwx.entity.user.MineCourseBean;
import com.ucfo.youcaiwx.entity.user.MineWatchRecordBean;
import com.ucfo.youcaiwx.module.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.MineCoursePresenter;
import com.ucfo.youcaiwx.presenter.view.user.IMineCourseView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.widget.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-12-27.  下午 2:43
 * Package: com.ucfo.youcaiwx.module.user.fragment.course
 * FileName: MineCourseFragment
 * Description:TODO 我的课程
 */
public class MineCourseFragment extends BaseFragment implements IMineCourseView, View.OnClickListener {

    private Button mLookCourseBtn;
    private LinearLayout linearHolder;
    private ShimmerRecyclerView recyclerview;
    private SmartRefreshLayout refreshlayout;
    private MineCourseAdapter courseAdapter;

    private ArrayList<MineCourseBean.DataBean> list;
    private MineCoursePresenter mineCoursePresenter;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int user_id;

    @Override
    protected int setContentView() {
        return R.layout.fragment_minecourse;
    }

    @Override
    protected void initView(View itemView) {
        mLookCourseBtn = (Button) itemView.findViewById(R.id.btn_lookCourse);
        mLookCourseBtn.setOnClickListener(this);
        linearHolder = (LinearLayout) itemView.findViewById(R.id.linear_holder);
        recyclerview = (ShimmerRecyclerView) itemView.findViewById(R.id.recyclerview);
        refreshlayout = (SmartRefreshLayout) itemView.findViewById(R.id.refreshlayout);
    }

    @Override
    protected void initData() {
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(getContext());
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setNestedScrollingEnabled(false);

        list = new ArrayList<>();

        mineCoursePresenter = new MineCoursePresenter(this);
        refreshlayout.setEnableLoadMore(false);
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mineCoursePresenter.getMineCourseList(user_id);
            }
        });
    }

    @Override
    protected void onLazyLoadOnce() {
        super.onLazyLoadOnce();
        mineCoursePresenter.getMineCourseList(user_id);
    }

    @Override
    public void getMineCourse(MineCourseBean data) {
        if (data != null) {
            if (data.getData() != null && data.getData().size() > 0) {
                if (refreshlayout != null) {
                    refreshlayout.setVisibility(View.VISIBLE);
                }
                if (linearHolder != null) {
                    linearHolder.setVisibility(View.GONE);
                }

                List<MineCourseBean.DataBean> beanList = data.getData();
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.clear();
                list.addAll(beanList);

                initAdapter();
            } else {
                if (refreshlayout != null) {
                    refreshlayout.setVisibility(View.GONE);
                }
                if (linearHolder != null) {
                    linearHolder.setVisibility(View.VISIBLE);
                }
            }
        } else {
            if (refreshlayout != null) {
                refreshlayout.setVisibility(View.GONE);
            }
            if (linearHolder != null) {
                linearHolder.setVisibility(View.INVISIBLE);
            }
        }
        if (refreshlayout != null) {
            refreshlayout.finishRefresh();
            refreshlayout.finishLoadMore();
        }
    }

    @Override
    public void getMineCPECourse(MineCPECourseBean data) {
        //TODO nothing
    }

    @Override
    public void getMineWatchRecord(MineWatchRecordBean data) {
        //TODO nothing
    }

    private void initAdapter() {
        if (courseAdapter == null) {
            courseAdapter = new MineCourseAdapter(getContext(), list);
            recyclerview.setAdapter(courseAdapter);
        } else {
            courseAdapter.notifyChange(list);
        }

        courseAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                MineCourseBean.DataBean bean = list.get(position);
                String courseIamge = bean.getApp_img();//TODO 课程封面
                int coursePackageId = bean.getPackage_id();//TODO  课程包ID(课程包内含多们课程)
                bundle.putString(Constant.COURSE_COVER_IMAGE, courseIamge);//封面
                bundle.putInt(Constant.COURSE_PACKAGE_ID, coursePackageId);//课程包ID
                bundle.putInt(Constant.COURSE_BUY_STATE, 1);//课程是否购买
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
        if (refreshlayout != null) {
            refreshlayout.setVisibility(View.GONE);
        }
        if (linearHolder != null) {
            linearHolder.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lookCourse:
                // TODO 19/12/27
                break;
            default:
                break;
        }
    }
}
