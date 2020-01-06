package com.ucfo.youcaiwx.module.user.fragment.course;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.ucfo.youcaiwx.module.course.CourseListActivity;
import com.ucfo.youcaiwx.module.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.MineCoursePresenter;
import com.ucfo.youcaiwx.presenter.view.user.IMineCourseView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

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
    private RecyclerView recyclerview;
    private SmartRefreshLayout refreshlayout;
    private LoadingLayout loadingLayout;

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
        recyclerview = (RecyclerView) itemView.findViewById(R.id.recyclerview);
        refreshlayout = (SmartRefreshLayout) itemView.findViewById(R.id.refreshlayout);
        loadingLayout = (LoadingLayout) itemView.findViewById(R.id.loadinglayout);
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
                loadMineCourse();
            }
        });
        loadingLayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMineCourse();
            }
        });
    }

    private void loadMineCourse() {
        if (mineCoursePresenter != null) {
            mineCoursePresenter.getMineCourseList(user_id);
        }
    }

    @Override
    protected void onVisibleToUser() {
        super.onVisibleToUser();
        loadMineCourse();
    }

    @Override
    public void getMineCourse(MineCourseBean data) {
        if (data != null) {
            if (data.getData() != null && data.getData().size() > 0) {
                List<MineCourseBean.DataBean> beanList = data.getData();
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.clear();
                list.addAll(beanList);

                initAdapter();

                showContent();
            } else {
                showEmpty();
            }
        } else {
            showEmpty();
        }
        //结束动画
        if (refreshlayout != null) {
            refreshlayout.finishRefresh();
        }
    }

    private void showContent() {
        if (linearHolder != null) {
            linearHolder.setVisibility(View.GONE);
        }
        if (recyclerview != null) {
            recyclerview.setVisibility(View.VISIBLE);
        }
    }

    //没有课程,显示空页面
    private void showEmpty() {
        if (linearHolder != null) {
            linearHolder.setVisibility(View.VISIBLE);
        }
        if (recyclerview != null) {
            recyclerview.setVisibility(View.GONE);
        }
    }

    @Override
    public void getMineCPECourse(MineCPECourseBean data) {
        //TODO nothing
    }

    private void initAdapter() {
        if (courseAdapter == null) {
            courseAdapter = new MineCourseAdapter(getContext(), list);
            recyclerview.setAdapter(courseAdapter);
        } else {
            courseAdapter.notifyChange(list);
        }
        /**
         * 跳转至播放器
         */
        courseAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (fastClick(2000)) {
                    return;
                }
                MineCourseBean.DataBean bean = list.get(position);
                //TODO 课程封面
                String courseIamge = bean.getApp_img();
                //TODO  课程包ID(课程包内含多们课程)
                int coursePackageId = bean.getPackage_id();

                Bundle bundle = new Bundle();
                bundle.putString(Constant.COURSE_COVER_IMAGE, courseIamge);
                bundle.putInt(Constant.COURSE_PACKAGE_ID, coursePackageId);
                startActivity(VideoPlayPageActivity.class, bundle);
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showLoadingFinish() {
        if (loadingLayout != null) {
            loadingLayout.showContent();
        }
    }

    @Override
    public void showError() {
        if (loadingLayout != null) {
            loadingLayout.showError();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lookCourse:
                // TODO 19/12/27
                startActivity(CourseListActivity.class);
                break;
            default:
                break;
        }
    }
}
