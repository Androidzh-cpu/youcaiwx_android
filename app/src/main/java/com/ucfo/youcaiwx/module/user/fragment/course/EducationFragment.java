package com.ucfo.youcaiwx.module.user.fragment.course;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
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
 * Time: 2019-12-27.  下午 2:44
 * Package: com.ucfo.youcaiwx.module.user.fragment.course
 * FileName: EducationFragment
 * Description:TODO 我的后续教育
 */
public class EducationFragment extends BaseFragment implements IMineCourseView, View.OnClickListener {
    private Button mLookCourseBtn;
    private LinearLayout linearLayout;
    private ShimmerRecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
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
        recyclerView = (ShimmerRecyclerView) itemView.findViewById(R.id.recyclerview);
        refreshLayout = (SmartRefreshLayout) itemView.findViewById(R.id.refreshlayout);
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
    }

    @Override
    protected void onLazyLoadOnce() {
        super.onLazyLoadOnce();
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

                if (refreshLayout != null) {
                    refreshLayout.setVisibility(View.VISIBLE);
                }
                if (linearLayout != null) {
                    linearLayout.setVisibility(View.GONE);
                }
            } else {
                if (refreshLayout != null) {
                    refreshLayout.setVisibility(View.GONE);
                }
                if (linearLayout != null) {
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        } else {
            if (refreshLayout != null) {
                refreshLayout.setVisibility(View.GONE);
            }
            if (linearLayout != null) {
                linearLayout.setVisibility(View.INVISIBLE);
            }
        }
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
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
    public void getMineWatchRecord(MineWatchRecordBean data) {
        //TODO nothing
    }

    @Override
    public void showLoading() {
        //TODO nothing
    }

    @Override
    public void showLoadingFinish() {
        //TODO nothing
    }

    @Override
    public void showError() {
        if (refreshLayout != null) {
            refreshLayout.setVisibility(View.GONE);
        }
        if (linearLayout != null) {
            linearLayout.setVisibility(View.INVISIBLE);
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
