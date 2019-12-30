package com.ucfo.youcaiwx.module.user.fragment.course;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.user.MineCPECourseAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.user.MineCPECourseBean;
import com.ucfo.youcaiwx.entity.user.MineCourseBean;
import com.ucfo.youcaiwx.module.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.module.home.cpe.CPECourseActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.MineCoursePresenter;
import com.ucfo.youcaiwx.presenter.view.user.IMineCourseView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-12-27.  下午 2:44
 * Package: com.ucfo.youcaiwx.module.user.fragment.course
 * FileName: EducationFragment
 * Description:TODO 我的后续教育
 */
public class EducationFragment extends BaseFragment implements IMineCourseView, View.OnClickListener {
    private Button mLookCourseBtn;
    private LinearLayout linearLayout;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private LoadingLayout loadingLayout;

    private SharedPreferencesUtils sharedPreferencesUtils;
    private int user_id;

    private MineCoursePresenter mineCoursePresenter;
    private List<MineCPECourseBean.DataBean> list;
    private MineCPECourseAdapter courseAdapter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_minecourse;
    }

    @Override
    protected void initView(View itemView) {
        mLookCourseBtn = (Button) itemView.findViewById(R.id.btn_lookCourse);
        mLookCourseBtn.setOnClickListener(this);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_holder);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerview);
        refreshLayout = (SmartRefreshLayout) itemView.findViewById(R.id.refreshlayout);
        loadingLayout = (LoadingLayout) itemView.findViewById(R.id.loadinglayout);
    }

    @Override
    protected void initData() {
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(getContext());
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        list = new ArrayList<>();

        mineCoursePresenter = new MineCoursePresenter(this);

        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
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

    @Override
    protected void onVisibleToUser() {
        super.onVisibleToUser();
        loadMineCourse();
    }

    private void loadMineCourse() {
        if (mineCoursePresenter != null) {
            mineCoursePresenter.getMineCPECourseList(user_id);
        }
    }

    @Override
    public void getMineCourse(MineCourseBean data) {
        //TODO nothing
    }

    @Override
    public void getMineCPECourse(MineCPECourseBean data) {
        if (data != null) {
            if (data.getData() != null && data.getData().size() > 0) {
                List<MineCPECourseBean.DataBean> beanList = data.getData();
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
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
        }
    }

    private void showContent() {
        if (linearLayout != null) {
            linearLayout.setVisibility(View.GONE);
        }
        if (recyclerView != null) {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    //没有课程,显示空页面
    private void showEmpty() {
        if (linearLayout != null) {
            linearLayout.setVisibility(View.VISIBLE);
        }
        if (recyclerView != null) {
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void initAdapter() {
        if (courseAdapter == null) {
            courseAdapter = new MineCPECourseAdapter(getContext(), list);
            recyclerView.setAdapter(courseAdapter);
        } else {
            courseAdapter.notifyChange(list);
        }

        courseAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                MineCPECourseBean.DataBean bean = list.get(position);
                String courseIamge = bean.getApp_img();//TODO 课程封面
                String coursePackageId = bean.getPackage_id();//TODO  课程包ID(课程包内含多们课程)
                if (TextUtils.isEmpty(coursePackageId)) {
                    showToast(getResources().getString(R.string.miss_params));
                    return;
                }
                bundle.putString(Constant.COURSE_COVER_IMAGE, courseIamge);//封面
                bundle.putString(Constant.COURSE_SOURCE, Constant.WATCH_EDUCATION_CPE);//播放源
                bundle.putInt(Constant.COURSE_PACKAGE_ID, Integer.parseInt(coursePackageId));//课程包ID
                startActivity(VideoPlayPageActivity.class, bundle);
            }
        });
    }

    @Override
    public void showLoading() {
        //TODO nothing
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
                startActivity(CPECourseActivity.class);
                break;
            default:
                break;
        }
    }
}
