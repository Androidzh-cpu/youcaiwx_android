package com.ucfo.youcaiwx.module.course.player.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.course.CourseDirWindowAdapter;
import com.ucfo.youcaiwx.adapter.course.CoursePackageListAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.course.CourseDirBean;
import com.ucfo.youcaiwx.module.course.player.DownloadDirectoryActivity;
import com.ucfo.youcaiwx.module.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.module.login.LoginActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.course.CourseDirPresenter;
import com.ucfo.youcaiwx.presenter.view.course.ICourseDirView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:29117
 * Time: 2019-4-3.  上午 10:02
 * Email:2911743255@qq.com
 * ClassName: CourseDirectoryListFragment
 * Description:TODO 课程目录
 * Detail:TODO fragment_coursedirectorylist
 */
public class CourseDirectoryListFragment extends BaseFragment implements ICourseDirView {

    private RecyclerView recyclerview;
    private SmartRefreshLayout refreshlayout;
    private ConstraintLayout layoutMain;
    private LoadingLayout loadinglayout;

    private VideoPlayPageActivity videoPlayPageActivity;
    private int coursePackageId;
    private int userId;
    private CourseDirPresenter courseDirPresenter;
    private List<CourseDirBean.DataBean> coursePackageList;
    private List<CourseDirBean.DataBean.SectionBean> sectionBeanList;
    private CoursePackageListAdapter coursePackageListAdapter;
    private PopupWindow courseDirectoryPopupWindow;
    private CourseDirWindowAdapter courseDirWindowAdapter;
    private int currentPlayCourseIndex = -1, currentClickCourseIndex = -1;//当前播放的课程所属套餐索引值;  当前点击的课程所属套餐索引值
    //父列表索引 , 二级列表索引 , 课程购买状态 , 是否是正课标识
    private int groupIndex = -1, sonIndex = -1, courseBuyState, courseUnCon;
    private boolean loginStatus;
    private LayoutInflater layoutInflater;
    private String courseSource;

    /**
     * Description:CourseDirectoryListFragment
     * Time:2019-4-22   下午 1:57
     * Detail: 获取弹框状态
     */
    public boolean coursWindowisShow() {
        if (courseDirectoryPopupWindow != null) {
            if (courseDirectoryPopupWindow.isShowing()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Description:CourseDirectoryListFragment
     * Time:2019-4-22   下午 1:57
     * Detail: 获取弹框取消
     */
    public void dismissWindow() {
        if (courseDirectoryPopupWindow != null) {
            if (courseDirectoryPopupWindow.isShowing()) {
                courseDirectoryPopupWindow.dismiss();
            }
        }
    }

    @Override
    protected void initView(View view) {
        loadinglayout = view.findViewById(R.id.loadinglayout);
        recyclerview = view.findViewById(R.id.recyclerview);
        refreshlayout = view.findViewById(R.id.refreshlayout);
        layoutMain = view.findViewById(R.id.layout_main);

        userId = SharedPreferencesUtils.getInstance(getContext()).getInt(Constant.USER_ID, 0);
        loginStatus = SharedPreferencesUtils.getInstance(getContext()).getBoolean(Constant.LOGIN_STATUS, false);

        FragmentActivity activity = getActivity();
        if (activity instanceof VideoPlayPageActivity) {
            videoPlayPageActivity = (VideoPlayPageActivity) getActivity();
        }

        coursePackageId = videoPlayPageActivity.getCoursePackageId();

        coursePackageList = new ArrayList<>();
        sectionBeanList = new ArrayList<>();
    }

    @Override
    protected void initData() {
        courseDirPresenter = new CourseDirPresenter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);

        int topBottom = DensityUtil.dp2px(2);
        recyclerview.addItemDecoration(new SpacesItemDecoration(0, topBottom, Color.TRANSPARENT));
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadCourseDirectoryList();
            }
        });

        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCourseDirectoryList();
            }
        });

    }

    @Override
    protected void onLazyLoadOnce() {
        super.onLazyLoadOnce();
        loadCourseDirectoryList();
    }

    /**
     * 获取视频播放目
     */
    private void loadCourseDirectoryList() {
        //获取课程目录
        if (courseDirPresenter == null) {
            courseDirPresenter = new CourseDirPresenter(this);
        }
        if (videoPlayPageActivity != null) {
            courseSource = videoPlayPageActivity.getCourse_Source();
        }
        courseDirPresenter.getCourseDirData(coursePackageId, userId, courseSource);
    }


    @Override
    protected void onInvisibleToUser() {
        super.onInvisibleToUser();
        if (courseDirectoryPopupWindow != null) {
            if (courseDirectoryPopupWindow.isShowing()) {
                courseDirectoryPopupWindow.dismiss();
            }
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_coursedirectorylist;
    }

    /**
     * Description:CourseDirectoryListFragment
     * Time:2019-4-16   上午 10:04
     * Detail: TODO 获取课程包列表,包含课程目录
     */
    @Override
    public void getCourseDirData(CourseDirBean courseDirBean) {
        if (courseDirBean.getData() != null && courseDirBean.getData().size() != 0) {

            List<CourseDirBean.DataBean> data = courseDirBean.getData();
            if (coursePackageList == null) {
                coursePackageList = new ArrayList<>();
            }
            coursePackageList.clear();
            coursePackageList.addAll(data);

            initAdapter(coursePackageList);//设置课程套餐适配器

            if (loadinglayout != null) {
                loadinglayout.showContent();
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

    /**
     * Description:CourseDirectoryListFragment
     * Time:2019-4-16   下午 1:40
     * Detail: TODO 课程套餐适配器
     */
    private void initAdapter(List<CourseDirBean.DataBean> coursePackageLists) {
        if (coursePackageListAdapter == null) {
            coursePackageListAdapter = new CoursePackageListAdapter(coursePackageLists, getContext());
            recyclerview.setAdapter(coursePackageListAdapter);
        }
        if (coursePackageListAdapter != null) {
            coursePackageListAdapter.notifyChange(coursePackageLists);
        }

        coursePackageListAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                List<CourseDirBean.DataBean.SectionBean> section = coursePackageLists.get(position).getSection();
                String isZhengke = coursePackageLists.get(position).getIs_zhengke();
                if (!TextUtils.equals(courseSource, Constant.WATCH_EDUCATION_CPE)) {
                    if (TextUtils.isEmpty(isZhengke)) {
                        showToast(getResources().getString(R.string.miss_params));
                        return;
                    }
                    courseUnCon = Integer.parseInt(isZhengke);
                }
                //当前课程信息
                CourseDirBean.DataBean dataBean = coursePackageLists.get(position);
                //当前课程点击位置
                currentClickCourseIndex = position;

                showCourseDirWindow(dataBean, section);
            }
        });
    }

    /**
     * Description:CourseDirectoryListFragment
     * Time:2019-4-16   上午 11:49
     * Detail:TODO 点击课程包,弹出对应课程播放列表
     */
    private void showCourseDirWindow(CourseDirBean.DataBean dataBean, List<CourseDirBean.DataBean.SectionBean> beanList) {
        if (sectionBeanList == null) {
            sectionBeanList = new ArrayList<>();
        }
        sectionBeanList.clear();
        sectionBeanList.addAll(beanList);

        if (videoPlayPageActivity != null) {
            courseBuyState = videoPlayPageActivity.getCourseBuyState();
        }
        if (getContext() != null) {
            layoutInflater = LayoutInflater.from(getContext());
        } else {
            Context context = getContext();
            if (context != null) {
                layoutInflater = LayoutInflater.from(context);
            }
        }
        View contentView = layoutInflater.inflate(R.layout.dialog_coursedir_window, null);
        courseDirectoryPopupWindow = new PopupWindow(contentView);
        //TODO 设置宽高
        courseDirectoryPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        int measuredHeight = layoutMain.getMeasuredHeight();
        if (measuredHeight == 0) {
            measuredHeight = DensityUtil.dp2px( 80);
        }
        courseDirectoryPopupWindow.setHeight(measuredHeight);
        courseDirectoryPopupWindow.setFocusable(false);//区域外点击不消失
        courseDirectoryPopupWindow.setOutsideTouchable(false);////区域外点击不消失
        courseDirectoryPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        courseDirectoryPopupWindow.setSplitTouchEnabled(true);//多点触控
        courseDirectoryPopupWindow.setAnimationStyle(R.style.Widget_AppCompat_PopupWindow);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            courseDirectoryPopupWindow.setAttachedInDecor(true);//NavigationBar重叠
        }
        courseDirectoryPopupWindow.setClippingEnabled(true);
        if (courseDirectoryPopupWindow.isShowing()) {
            courseDirectoryPopupWindow.dismiss();
        } else {
            initDirView(contentView, dataBean, sectionBeanList);
        }
    }

    /**
     * Description:CourseDirectoryListFragment
     * Time:2019-4-16   下午 1:04
     * Detail: 课程目录数据处理
     */
    private void initDirView(View contentView, CourseDirBean.DataBean dataBean, List<CourseDirBean.DataBean.SectionBean> sectionList) {
        TextView courseName = (TextView) contentView.findViewById(R.id.course_name);
        TextView courseTeacherName = (TextView) contentView.findViewById(R.id.course_teacher_name);
        ImageView courseDownload = (ImageView) contentView.findViewById(R.id.course_down_btn);
        courseDownload.setClickable(true);
        RoundTextView courseCloseBtn = (RoundTextView) contentView.findViewById(R.id.course_closebtn);
        ExpandableListView courseTree = (ExpandableListView) contentView.findViewById(R.id.course_dirlist);

        if (TextUtils.equals(courseSource, Constant.WATCH_EDUCATION_CPE)) {
            courseDownload.setVisibility(View.GONE);
        }

        courseName.setText(dataBean.getName());//课程名称
        courseTeacherName.setText(String.valueOf(getResources().getString(R.string.holder_teacher) + dataBean.getTeacher_name()));//讲师名称
        if (courseDirWindowAdapter == null) {
            courseDirWindowAdapter = new CourseDirWindowAdapter(getContext(), sectionList);
        }
        courseDirWindowAdapter.notifyDataSetChanged();
        if (courseDirWindowAdapter != null) {
            courseTree.setAdapter(courseDirWindowAdapter);
            courseDirWindowAdapter.setSelectPosition(currentPlayCourseIndex, currentClickCourseIndex);
        }
        //如果当前点击的套餐列表的索引值==当前播放的课程套餐的索引,可以展开对应的一级目录和二级目录
        if (currentPlayCourseIndex == currentClickCourseIndex) {
            courseTree.expandGroup(groupIndex);
            courseTree.setSelectedGroup(groupIndex);
            courseTree.setSelectedChild(groupIndex, sonIndex, true);
        }
        courseDirectoryPopupWindow.showAtLocation(layoutMain, Gravity.BOTTOM, 0, 0);
        courseDirectoryPopupWindow.update();
        courseCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseDirectoryPopupWindow.dismiss();
            }
        });
        //TODO 下载操作
        courseDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loginStatus) {
                    startActivity(LoginActivity.class, null);
                } else {
                    if (courseBuyState == 1) {

                        videoPlayPageActivity.pause();

                        Bundle bundle = new Bundle();
                        String teacherName = dataBean.getTeacher_name();
                        String name = dataBean.getName();
                        bundle.putString(Constant.TITLE, name);
                        bundle.putString(Constant.TEACHER_NAME, teacherName);
                        bundle.putInt(Constant.PACKAGE_ID, coursePackageId);
                        bundle.putInt(Constant.PAGE, currentClickCourseIndex);
                        startActivity(DownloadDirectoryActivity.class, bundle);
                    } else {
                        ToastUtil.showBottomShortText(videoPlayPageActivity, getResources().getString(R.string.course_buyCourse));
                    }
                }
            }
        });
        //TODO 小节视频点击事件,点击之后播放视频
        courseTree.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (!loginStatus) {
                    startActivity(LoginActivity.class, null);
                } else {
                    if (TextUtils.equals(courseSource, Constant.WATCH_EDUCATION_CPE)) {
                        if (courseBuyState != Constant.HAVED_BUY) {
                            showToast(getResources().getString(R.string.not_apply));
                            return false;
                        }
                    }
                    String vid = sectionList.get(groupPosition).getVideo().get(childPosition).getVideoId();
                    String video_id = sectionList.get(groupPosition).getVideo().get(childPosition).getVideo_id();
                    int videoId = sectionList.get(groupPosition).getVideo().get(childPosition).getId();
                    int courseId = sectionList.get(groupPosition).getVideo().get(childPosition).getCourse_id();
                    int sectionId = sectionList.get(groupPosition).getVideo().get(childPosition).getSection_id();

                    //TODO 调用播放器,切换视频
                    if (videoPlayPageActivity != null) {
                        //切换视频
                        videoPlayPageActivity.changePlayVidSource(vid, videoId, courseId, sectionId, video_id);
                        //是否是正课标识
                        videoPlayPageActivity.setCourseUnCon(courseUnCon);
                    }

                    groupIndex = groupPosition;
                    sonIndex = childPosition;
                    currentPlayCourseIndex = currentClickCourseIndex;

                    //设置选中的位置
                    courseDirWindowAdapter.setSelectPosition(groupIndex, sonIndex, currentPlayCourseIndex, currentClickCourseIndex);
                    //课程包列表刷新
                    coursePackageListAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });
    }

    /**
     * Description:CourseDirectoryListFragment
     * Time:2019-4-24   上午 10:58
     * Detail: TODO 上一个视频播放完毕,切换下一个视频
     */
    public void onNextVideoPlay() {
        if (coursePackageList != null && coursePackageList.size() > 0) {
            //1.0.3.2此版本报空指针
            List<CourseDirBean.DataBean.SectionBean> section = coursePackageList.get(currentPlayCourseIndex).getSection();//获取套餐中当前播放的课程目录
            if (section != null && section.size() > 0) {//课程目录不为空
                for (int i = groupIndex; i < section.size(); i++) {
                    List<CourseDirBean.DataBean.SectionBean.VideoBean> beanList = section.get(i).getVideo();//目录子播放列表
                    //如果有子条目，子角标++
                    if (beanList != null && beanList.size() > 0) {
                        //子角标
                        if (sonIndex < beanList.size() - 1) {
                            ++sonIndex;
                            break;
                        } else {
                            ++groupIndex;
                            sonIndex = 0;
                            break;
                        }
                    } else {
                        //没有，父角标++
                        if (groupIndex != section.size() - 1) {
                            ++groupIndex;
                        }
                        sonIndex = 0;
                        break;
                    }
                }
            }
            if (section != null && groupIndex <= section.size() - 1) {
                ToastUtil.showBottomLongText(getContext(), getResources().getString(R.string.course_changing));
                //TODO 获取切换视频所需信息
                CourseDirBean.DataBean.SectionBean.VideoBean videoBean = section.get(groupIndex).getVideo().get(sonIndex);
                String vid = videoBean.getVideoId();
                String video_id = videoBean.getVideo_id();
                int videoId = videoBean.getId();
                int courseId = videoBean.getCourse_id();
                int sectionId = videoBean.getSection_id();

                if (videoPlayPageActivity != null) {
                    //TODO 调用播放器,切换视频
                    videoPlayPageActivity.changePlayVidSource(vid, videoId, courseId, sectionId, video_id);
                }

                if (courseDirWindowAdapter != null) {
                    //设置选中的位置
                    courseDirWindowAdapter.setSelectPosition(groupIndex, sonIndex, currentPlayCourseIndex, currentClickCourseIndex);
                    courseDirWindowAdapter.notifyDataSetChanged();
                }
                if (coursePackageListAdapter != null) {
                    coursePackageListAdapter.notifyDataSetChanged();
                }
            }
        }
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
