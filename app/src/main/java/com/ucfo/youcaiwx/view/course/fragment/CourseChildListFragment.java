package com.ucfo.youcaiwx.view.course.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.course.CourseChildListApapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.course.CourseDataListBean;
import com.ucfo.youcaiwx.entity.course.CourseSubjectsBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.course.CourseListPresenter;
import com.ucfo.youcaiwx.presenter.view.course.ICourseListView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.view.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author:29117
 * Time: 2019-3-29.  下午 4:41
 * Email:2911743255@qq.com
 * ClassName: CourseChildListFragment
 * Description:TODO 课程分类子页面
 */

public class CourseChildListFragment extends BaseFragment implements ICourseListView {
    @BindView(R.id.recyclerview)
    ShimmerRecyclerView recyclerview;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    Unbinder unbinder;
    //当前页面页面索引
    private int position;
    //索引id
    private int id;
    private Context context;
    private CourseListPresenter courseListPresenter;
    private List<CourseDataListBean.DataBean> courseList;
    private CourseChildListApapter courseChildListApapter;
    private LinearLayoutManager linearLayoutManager;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int user_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void initView(View view) {
        context = getActivity();
    }

    @Override
    protected void initData() {
        courseList = new ArrayList<>();
        //注册网络
        courseListPresenter = new CourseListPresenter(this);
        //recyclerview添加布局样式
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setNestedScrollingEnabled(false);
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                courseListPresenter.getCourseDataList(id, user_id);
            }
        });
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseListPresenter.getCourseDataList(id, user_id);
            }
        });
    }

    //用户可见时加载一次
    @Override
    protected void onLazyLoadOnce() {
        super.onLazyLoadOnce();
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        courseListPresenter.getCourseDataList(id, user_id);
    }

    /**
     * 标题的索引值
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * 标题对应的ID
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_courselist;
    }

    @Override
    public void getCourseSubject(CourseSubjectsBean data) {
        //TODO 课程筛选
    }

    /**
     * Description:CourseChildListFragment
     * Time:2019-4-1   下午 5:23
     * Detail:TODO 获取课程列表
     */
    @Override
    public void getCourseDataList(CourseDataListBean result) {
        if (result != null && result.getData().size() != 0) {
            List<CourseDataListBean.DataBean> data = result.getData();
            if (courseList == null) {
                courseList = new ArrayList<>();
            }
            courseList.clear();
            courseList.addAll(data);

            initAdapter(courseList);

            loadinglayout.showContent();
        } else {
            loadinglayout.showEmpty();
        }

        refreshlayout.finishLoadMore();
        refreshlayout.finishRefresh();
    }

    private void initAdapter(List<CourseDataListBean.DataBean> courseList) {
        if (courseChildListApapter == null) {
            courseChildListApapter = new CourseChildListApapter(courseList, context);
        } else {
            courseChildListApapter.notifyDataSetChanged();
        }
        recyclerview.setAdapter(courseChildListApapter);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        courseChildListApapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!fastClick(1000)) {
                    starPlayActivity(courseList, position);
                }
            }
        });
    }

    /**
     * Description:CourseChildListFragment
     * Time:2019-4-3   上午 10:26
     * Detail:跳转至课程播放页
     */
    private void starPlayActivity(List<CourseDataListBean.DataBean> list, int position) {
        Bundle bundle = new Bundle();
        CourseDataListBean.DataBean bean = list.get(position);

        String course_iamge = bean.getApp_img();//TODO 课程封面
        int coursePackageId = bean.getId();//TODO  课程包ID(课程包内含多们课程)
        int is_purchase = bean.getIs_purchase();//TODO 是否购买1购买2未购买"

        bundle.putString(Constant.COURSE_COVER_IMAGE, course_iamge);//封面
        bundle.putInt(Constant.COURSE_PACKAGE_ID, coursePackageId);//课程包ID
        bundle.putInt(Constant.COURSE_BUY_STATE, is_purchase);//购买状态
        bundle.putString(Constant.COURSE_PRICE, bean.getPrice());//课程包价格
        startActivity(VideoPlayPageActivity.class, bundle);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void showLoadingFinish() {
    }

    @Override
    public void showError() {
        loadinglayout.showError();
    }
}
