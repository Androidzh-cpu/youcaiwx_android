package com.ucfo.youcaiwx.module.course.player.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.answer.CourseAnswerListAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.answer.AnswerDetailBean;
import com.ucfo.youcaiwx.entity.answer.AnswerListDataBean;
import com.ucfo.youcaiwx.module.course.CourseAnswerDetailActivity;
import com.ucfo.youcaiwx.module.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.module.login.LoginActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.answer.CourseCourseAnswerListPresenter;
import com.ucfo.youcaiwx.presenter.view.answer.ICourseAnswerListView;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author:29117
 * Time: 2019-4-3.  上午 9:58
 * Email:2911743255@qq.com
 * ClassName: CourseAnswerQuestionFragment
 * Description:TODO 课程答疑列表
 */
public class CourseAnswerQuestionFragment extends BaseFragment implements ICourseAnswerListView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    Unbinder unbinder;
    private Context context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int user_id;
    private CourseCourseAnswerListPresenter courseAnswerListPresenter;
    private VideoPlayPageActivity videoPlayPageActivity;
    private List<AnswerListDataBean.DataBean> answerList;
    private int coursePackageId = -1, courseCourseid = 0, courseSectionid = 0, courseVideoid = 0;
    private CourseAnswerListAdapter courseAnswerListAdapter;
    private boolean loginstatus;
    private int courseBuyState;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loginstatus = sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        //用户课程购买状态
        courseBuyState = videoPlayPageActivity.getCourseBuyState();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void initView(View view) {
        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity instanceof VideoPlayPageActivity) {
            videoPlayPageActivity = (VideoPlayPageActivity) fragmentActivity;
        }
        context = getActivity();
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(videoPlayPageActivity);
        loginstatus = sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity());
        layoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager3.setReverseLayout(false);
        recyclerview.setLayoutManager(layoutManager3);
        int topBottom = DensityUtil.dip2px(getActivity(), 6);
        //recyclerview.addItemDecoration(new SpacesItemDecoration(0, 0, Color.TRANSPARENT));
        recyclerview.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        answerList = new ArrayList<>();
        courseAnswerListPresenter = new CourseCourseAnswerListPresenter(this);
        coursePackageId = videoPlayPageActivity.getCoursePackageId();//课程包ID

        refreshlayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        refreshlayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        refreshlayout.setEnableAutoLoadMore(false);//是否启用列表惯性滑动到底部时自动加载更多
        refreshlayout.setEnableNestedScroll(true);//是否启用嵌套滚动
        refreshlayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                courseAnswerListPresenter.getAnswerListData(courseVideoid, courseSectionid, courseCourseid, coursePackageId);
            }
        });
        refreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                courseAnswerListPresenter.getAnswerListData(courseVideoid, courseSectionid, courseCourseid, coursePackageId);
            }
        });
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseAnswerListPresenter.getAnswerListData(courseVideoid, courseSectionid, courseCourseid, coursePackageId);
            }
        });
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_courseanswerquestion;
    }

    @Override
    protected void onVisibleToUser() {
        super.onVisibleToUser();
        courseAnswerListPresenter.getAnswerListData(courseVideoid, courseSectionid, courseCourseid, coursePackageId);
    }

    /**
     * Description:CourseAnswerQuestionFragment
     * Time:2019-4-22   下午 3:19
     * Detail:TODO 获取对应视频答疑列表  package_id=1&course_id=3&section_id=1&video_id=1
     */
    public void getAnswerListData(int coursepackageId, int coursecourseid, int coursesectionid, int video_id) {
        courseCourseid = coursecourseid;
        coursePackageId = coursepackageId;
        courseSectionid = coursesectionid;
        courseVideoid = video_id;

        if (courseAnswerListPresenter != null) {
            courseAnswerListPresenter.getAnswerListData(video_id, coursesectionid, coursecourseid, coursepackageId);
        } else {
            courseAnswerListPresenter = new CourseCourseAnswerListPresenter(this);
            courseAnswerListPresenter.getAnswerListData(video_id, coursesectionid, coursecourseid, coursepackageId);
        }
    }

    /**
     * Description:CourseAnswerQuestionFragment
     * Time:2019-4-16   下午 4:03
     * Detail: TODO 获取问答列表
     */
    @Override
    public void getAnswerListData(AnswerListDataBean result) {
        if (result != null && result.getData().size() != 0) {
            List<AnswerListDataBean.DataBean> data = result.getData();
            answerList.clear();
            answerList.addAll(data);
            initAdapter(answerList);
            loadinglayout.showContent();
        } else {
            loadinglayout.showEmpty();
        }
        refreshlayout.finishRefresh();
        refreshlayout.finishLoadMore();
    }


    /**
     * Description:CourseAnswerQuestionFragment
     * Time:2019-4-16   下午 6:41
     * Detail: 问答列表
     */
    private void initAdapter(List<AnswerListDataBean.DataBean> data) {
        if (courseAnswerListAdapter == null) {
            courseAnswerListAdapter = new CourseAnswerListAdapter(data, context, 0);
        } else {
            courseAnswerListAdapter.notifyDataSetChanged();
        }
        recyclerview.setAdapter(courseAnswerListAdapter);
        //查看详情点击事件
        courseAnswerListAdapter.setItemClick(new CourseAnswerListAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                if (loginstatus) {//TODO 已登录
                    if (courseBuyState == 1) {//TODO 已购买
                        if (!fastClick(1000)) {
                            Bundle bundle = new Bundle();
                            bundle.putInt(Constant.ANSWER_ID, data.get(position).getId());
                            bundle.putInt(Constant.STATUS, data.get(position).getReply_status());
                            bundle.putString(Constant.TYPE, Constant.MINE_ANSWER);
                            startActivity(CourseAnswerDetailActivity.class, bundle);
                        }
                    } else {
                        ToastUtil.showBottomShortText(context, getResources().getString(R.string.course_bugCourse));
                    }
                } else {//TODO 未登录
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
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
        loadinglayout.showError();
    }

    @Override
    public void getAnswerDetailData(AnswerDetailBean dataBean) {
        //TODO nothing
    }
}
