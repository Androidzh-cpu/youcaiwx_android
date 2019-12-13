package com.ucfo.youcaiwx.module.course.player.fragment;

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
import com.ucfo.youcaiwx.module.answer.AnsweringCourseActivity;
import com.ucfo.youcaiwx.module.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.module.login.LoginActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.answer.CourseCourseAnswerListPresenter;
import com.ucfo.youcaiwx.presenter.view.answer.ICourseAnswerListView;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
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
    RecyclerView recyclerview;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    Unbinder unbinder;

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
        if (rootView != null) {
            unbinder = ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoPlayPageActivity == null) {
            FragmentActivity fragmentActivity = getActivity();
            if (fragmentActivity instanceof VideoPlayPageActivity) {
                videoPlayPageActivity = (VideoPlayPageActivity) fragmentActivity;
            }
        }
        if (sharedPreferencesUtils == null) {
            sharedPreferencesUtils = SharedPreferencesUtils.getInstance(videoPlayPageActivity);
        }
        loginstatus = sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false);
        user_id = Integer.parseInt(sharedPreferencesUtils.getString(Constant.USER_ID, "0"));
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
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);

        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity instanceof VideoPlayPageActivity) {
            videoPlayPageActivity = (VideoPlayPageActivity) fragmentActivity;
        }
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(videoPlayPageActivity);
        loginstatus = sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity());
        layoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager3);
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
                if (courseAnswerListPresenter != null) {
                    courseAnswerListPresenter.getAnswerListData(courseVideoid, courseSectionid, courseCourseid, coursePackageId, user_id);
                }
            }
        });
        refreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (courseAnswerListPresenter != null) {
                    courseAnswerListPresenter.getAnswerListData(courseVideoid, courseSectionid, courseCourseid, coursePackageId, user_id);
                }
            }
        });
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (courseAnswerListPresenter != null) {
                    courseAnswerListPresenter.getAnswerListData(courseVideoid, courseSectionid, courseCourseid, coursePackageId, user_id);
                }
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
        if (courseAnswerListPresenter != null) {
            courseAnswerListPresenter.getAnswerListData(courseVideoid, courseSectionid, courseCourseid, coursePackageId, user_id);
        }
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
            courseAnswerListPresenter.getAnswerListData(video_id, coursesectionid, coursecourseid, coursepackageId, user_id);
        } else {
            courseAnswerListPresenter = new CourseCourseAnswerListPresenter(this);
            courseAnswerListPresenter.getAnswerListData(video_id, coursesectionid, coursecourseid, coursepackageId, user_id);
        }
    }

    /**
     * Description:CourseAnswerQuestionFragment
     * Time:2019-4-16   下午 4:03
     * Detail: TODO 获取问答列表
     */
    @Override
    public void getAnswerListData(AnswerListDataBean result) {
        if (result != null && result.getData().size() > 0) {
            List<AnswerListDataBean.DataBean> data = result.getData();
            if (answerList == null) {
                answerList = new ArrayList<>();
            }
            answerList.clear();
            answerList.addAll(data);
            initAdapter();
            if (loadinglayout != null) {
                loadinglayout.showContent();
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
        refreshlayout.finishRefresh();
        refreshlayout.finishLoadMore();
    }


    /**
     * Description:CourseAnswerQuestionFragment
     * Time:2019-4-16   下午 6:41
     * Detail: 问答列表
     */
    private void initAdapter() {
        if (courseAnswerListAdapter == null) {
            courseAnswerListAdapter = new CourseAnswerListAdapter(answerList, videoPlayPageActivity, 0);
            recyclerview.setAdapter(courseAnswerListAdapter);
        } else {
            courseAnswerListAdapter.notifyChange(answerList);
        }
        //查看详情点击事件
        courseAnswerListAdapter.setItemClick(new CourseAnswerListAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                if (loginstatus) {
                    //TODO 已登录 奢靡啊
                    if (courseBuyState == Constant.HAVED_BUY) {
                        //TODO 已购买
                        if (!fastClick(1000)) {
                            Bundle bundle = new Bundle();
                            bundle.putInt(Constant.ANSWER_ID, answerList.get(position).getId());
                            bundle.putInt(Constant.STATUS, answerList.get(position).getReply_status());
                            bundle.putString(Constant.TYPE, Constant.MINE_ANSWER);
                            startActivity(AnsweringCourseActivity.class, bundle);
                        }
                    } else {
                        ToastUtil.showBottomShortText(context, getResources().getString(R.string.course_buyCourse));
                    }
                } else {
                    //TODO 未登录
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
        if (loadinglayout != null) {
            loadinglayout.showError();
        }
    }

    @Override
    public void getAnswerDetailData(AnswerDetailBean dataBean) {
        //TODO nothing
    }
}
