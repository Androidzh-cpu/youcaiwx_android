package com.ucfo.youcaiwx.module.course.player.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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
import com.ucfo.youcaiwx.module.login.LoginActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.course.CourseDirPresenter;
import com.ucfo.youcaiwx.presenter.view.course.ICourseDirView;
import com.ucfo.youcaiwx.utils.LogUtils;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:29117
 * Time: 2019-4-3.  上午 10:02
 * Email:2911743255@qq.com
 * ClassName: CourseDirectoryListFragment
 * Description:TODO 课程目录
 */
public class CourseDirectoryListFragment extends BaseFragment implements ICourseDirView {
    private RecyclerView recyclerview;
    private SmartRefreshLayout refreshlayout;
    private ConstraintLayout layoutMain;
    private LoadingLayout loadinglayout;

    private int coursePackageId;
    private int userId;
    private CourseDirPresenter courseDirPresenter;
    private List<CourseDirBean.DataBean> coursePackageList;
    private List<CourseDirBean.DataBean.SectionBean> sectionBeanList;
    private CoursePackageListAdapter coursePackageListAdapter;
    private PopupWindow courseDirectoryPopupWindow;
    private CourseDirWindowAdapter courseDirWindowAdapter;
    //当前播放的课程所属套餐索引值;  当前点击的课程所属套餐索引值
    private int currentPlayCourseIndex = -1, currentClickCourseIndex = -1;
    //          父列表索引 ,     二级列表索引 ,    课程购买状态 ,    是否是正课标识
    private int groupIndex = -1, sonIndex = -1, courseBuyState, courseUnCon;
    private boolean loginStatus;
    private String courseSource;

    private View contentView;
    private String teacherName;
    private String courseName;

    private CourseDirectoryListener courseDirectoryListener;

    /**
     * 调用宿主
     */
    public interface CourseDirectoryListener {
        void onPausePlay();

        void directorySetCourseUnCon(int courseUnCon);

        void directoryChangePlayVidSource(String vid, int videoId, int course_id, int section_id, String education_videoId);

        void directoryAllVideoPlayComplted();

        int directoryGetCoursePackageId();

        int directoryGetCourseBuyState();

        String directoryGetCourse_Source();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CourseDirectoryListener) {
            courseDirectoryListener = (CourseDirectoryListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement CourseDirectoryListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        courseDirectoryListener = null;
    }

    /**
     * Description:CourseDirectoryListFragment
     * Time:2019-4-22   下午 1:57
     * Detail: 获取弹框状态
     */
    public boolean courseDirectoryWindowISShow() {
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
    public void dismissCourseDirectoryWindow() {
        if (courseDirectoryPopupWindow != null) {
            if (courseDirectoryPopupWindow.isShowing()) {
                courseDirectoryPopupWindow.dismiss();
            }
        }
    }

    /**
     * 显示弹窗
     */
    public void showCourseDirectoryWindow() {
        if (courseDirectoryPopupWindow != null) {
            if (layoutMain != null) {
                courseDirectoryPopupWindow.showAtLocation(layoutMain, Gravity.BOTTOM, 0, 0);
                courseDirectoryPopupWindow.update();
            } else {
                courseDirectoryPopupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
                courseDirectoryPopupWindow.update();
            }
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_coursedirectorylist;
    }

    @Override
    protected void initView(View view) {
        loadinglayout = view.findViewById(R.id.loadinglayout);
        recyclerview = view.findViewById(R.id.recyclerview);
        refreshlayout = view.findViewById(R.id.refreshlayout);
        layoutMain = view.findViewById(R.id.layout_main);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        int topBottom = DensityUtil.dp2px(2);
        recyclerview.addItemDecoration(new SpacesItemDecoration(0, topBottom, Color.TRANSPARENT));
    }

    @Override
    protected void initData() {
        coursePackageList = new ArrayList<>();
        sectionBeanList = new ArrayList<>();
        //登录状态
        userId = SharedPreferencesUtils.getInstance(getContext()).getInt(Constant.USER_ID, 0);
        loginStatus = SharedPreferencesUtils.getInstance(getContext()).getBoolean(Constant.LOGIN_STATUS, false);
        //初始化
        courseDirPresenter = new CourseDirPresenter(this);
        //刷新和重试
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
     * 获取视频播放目录
     */
    private void loadCourseDirectoryList() {
        //获取课程目录
        if (courseDirPresenter == null) {
            courseDirPresenter = new CourseDirPresenter(this);
        }
        //获取套餐ID
        coursePackageId = courseDirectoryListener.directoryGetCoursePackageId();
        //获取视频播放源
        courseSource = courseDirectoryListener.directoryGetCourse_Source();
        //获取该套餐下视频列表
        courseDirPresenter.getCourseDirData(coursePackageId, userId, courseSource);
    }

    @Override
    protected void onInvisibleToUser() {
        super.onInvisibleToUser();
        dismissCourseDirectoryWindow();
    }

    /**
     * Description:CourseDirectoryListFragment
     * Time:2019-4-16   上午 10:04
     * Detail: TODO 获取课程包列表,包含课程目录
     */
    @Override
    public void getCourseDirData(CourseDirBean courseDirBean) {
        if (courseDirBean.getData() != null && courseDirBean.getData().size() > 0) {
            List<CourseDirBean.DataBean> data = courseDirBean.getData();
            if (coursePackageList == null) {
                coursePackageList = new ArrayList<>();
            }
            coursePackageList.clear();
            coursePackageList.addAll(data);

            //设置课程套餐适配器
            initAdapter();

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
        }
    }

    /**
     * Description:CourseDirectoryListFragment
     * Time:2019-4-16   下午 1:40
     * Detail: TODO 课程套餐适配器
     */
    private void initAdapter() {
        if (coursePackageListAdapter == null) {
            coursePackageListAdapter = new CoursePackageListAdapter(coursePackageList, getContext());
            recyclerview.setAdapter(coursePackageListAdapter);
        } else {
            coursePackageListAdapter.notifyChange(coursePackageList);
        }
        //展开播放目录
        coursePackageListAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                List<CourseDirBean.DataBean.SectionBean> section = coursePackageList.get(position).getSection();
                if (sectionBeanList == null) {
                    sectionBeanList = new ArrayList<CourseDirBean.DataBean.SectionBean>();
                }
                sectionBeanList.clear();
                sectionBeanList.addAll(section);

                CourseDirBean.DataBean bean = coursePackageList.get(position);
                courseName = bean.getName();
                teacherName = bean.getTeacher_name();
                String isZhengke = bean.getIs_zhengke();
                //非CPE课程判断是否是正课
                if (!TextUtils.equals(courseSource, Constant.WATCH_EDUCATION_CPE)) {
                    if (TextUtils.isEmpty(isZhengke)) {
                        showToast(getResources().getString(R.string.miss_params));
                        return;
                    }
                    courseUnCon = Integer.parseInt(isZhengke);
                }
                //当前课程点击位置
                currentClickCourseIndex = position;
                //弹出对应播放目录
                showCourseDirWindow();
            }
        });
    }

    /**
     * Description:CourseDirectoryListFragment
     * Time:2019-4-16   上午 11:49
     * Detail:TODO 点击课程包,弹出对应课程播放列表
     */
    private void showCourseDirWindow() {
        //获取套餐购买状态
        courseBuyState = courseDirectoryListener.directoryGetCourseBuyState();
        //绘制目录布局
        if (contentView == null) {
            contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_coursedir_window, null);
        }
        //创建弹窗
        courseDirectoryPopupWindow = createPopupWindow(contentView);

        if (courseDirectoryWindowISShow()) {
            dismissCourseDirectoryWindow();
        } else {
            showCourseDirectoryWindow(contentView);
        }
    }

    //创建popupwindow
    private PopupWindow createPopupWindow(View contentView) {
        PopupWindow popupWindow = new PopupWindow(contentView);
        //TODO 设置宽高
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        int measuredHeight = 0;
        if (layoutMain != null) {
            measuredHeight = layoutMain.getMeasuredHeight();
        }
        if (measuredHeight == 0) {
            measuredHeight = DensityUtil.dp2px(80);
        }
        popupWindow.setHeight(measuredHeight);
        //区域外点击不消失
        popupWindow.setFocusable(false);
        //区域外点击不消失
        popupWindow.setOutsideTouchable(false);
        //设置可以点击
        popupWindow.setTouchable(true);
        //注意  要是点击外部空白处弹框小时 必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        popupWindow.setSplitTouchEnabled(true);//多点触控
        popupWindow.setAnimationStyle(R.style.Widget_AppCompat_PopupWindow);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            popupWindow.setAttachedInDecor(true);//NavigationBar重叠
        }
        popupWindow.setClippingEnabled(true);
        return popupWindow;
    }

    /**
     * Description:CourseDirectoryListFragment
     * Time:2019-4-16   下午 1:04
     * Detail: 课程目录数据处理
     */
    private void showCourseDirectoryWindow(View contentView) {
        TextView coursename = (TextView) contentView.findViewById(R.id.course_name);
        TextView courseTeacherName = (TextView) contentView.findViewById(R.id.course_teacher_name);
        ImageView courseDownload = (ImageView) contentView.findViewById(R.id.course_down_btn);
        courseDownload.setClickable(true);
        RoundTextView courseCloseBtn = (RoundTextView) contentView.findViewById(R.id.course_closebtn);
        ExpandableListView expandableListView = (ExpandableListView) contentView.findViewById(R.id.course_dirlist);

        //CPE下载要挂掉
        if (TextUtils.equals(courseSource, Constant.WATCH_EDUCATION_CPE)) {
            courseDownload.setVisibility(View.GONE);
        }
        //设置课程名和姓名
        if (!TextUtils.isEmpty(courseName)) {
            coursename.setText(courseName);
        }
        if (!TextUtils.isEmpty(teacherName)) {
            courseTeacherName.setText(getResources().getString(R.string.teacher, teacherName));
        }

        if (courseDirWindowAdapter == null) {
            courseDirWindowAdapter = new CourseDirWindowAdapter(getContext(), sectionBeanList);
        }
        courseDirWindowAdapter.notifyChange(sectionBeanList);
        expandableListView.setAdapter(courseDirWindowAdapter);
        courseDirWindowAdapter.setSelectPosition(currentPlayCourseIndex, currentClickCourseIndex);

        //如果当前点击的套餐列表的索引值==当前播放的课程套餐的索引,可以展开对应的一级目录和二级目录
        if (currentPlayCourseIndex == currentClickCourseIndex) {
            expandableListView.expandGroup(groupIndex);
            expandableListView.setSelectedGroup(groupIndex);
            expandableListView.setSelectedChild(groupIndex, sonIndex, true);
        }
        //弹出目录窗口
        showCourseDirectoryWindow();
        //弹窗消失
        courseCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissCourseDirectoryWindow();
            }
        });
        //TODO 下载事件
        courseDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loginStatus) {
                    startActivity(LoginActivity.class);
                } else {
                    if (courseBuyState == Constant.HAVED_BUY) {
                        //暂停播放
                        courseDirectoryListener.onPausePlay();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.TITLE, courseName);
                        bundle.putString(Constant.TEACHER_NAME, teacherName);
                        bundle.putInt(Constant.PACKAGE_ID, coursePackageId);
                        bundle.putInt(Constant.PAGE, currentClickCourseIndex);
                        startActivity(DownloadDirectoryActivity.class, bundle);
                    } else {
                        showToast(getResources().getString(R.string.course_buyCourse));
                    }
                }
            }
        });
        //TODO 小节视频点击事件,点击之后播放视频
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (!loginStatus) {
                    startActivity(LoginActivity.class);
                } else {
                    if (TextUtils.equals(courseSource, Constant.WATCH_EDUCATION_CPE)) {
                        if (courseBuyState != Constant.HAVED_BUY) {
                            showToast(getResources().getString(R.string.not_apply));
                            return false;
                        }
                    }
                    String vid = sectionBeanList.get(groupPosition).getVideo().get(childPosition).getVideoId();
                    String video_id = sectionBeanList.get(groupPosition).getVideo().get(childPosition).getVideo_id();
                    int videoId = sectionBeanList.get(groupPosition).getVideo().get(childPosition).getId();
                    int courseId = sectionBeanList.get(groupPosition).getVideo().get(childPosition).getCourse_id();
                    int sectionId = sectionBeanList.get(groupPosition).getVideo().get(childPosition).getSection_id();

                    //TODO 调用播放器,切换视频
                    courseDirectoryListener.directoryChangePlayVidSource(vid, videoId, courseId, sectionId, video_id);
                    courseDirectoryListener.directorySetCourseUnCon(courseUnCon);

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
            List<CourseDirBean.DataBean.SectionBean> sectionBeanList = coursePackageList.get(currentPlayCourseIndex).getSection();//获取套餐中当前播放的课程目录
            if (sectionBeanList != null && sectionBeanList.size() > 0) {
                //判断一级列表是否越界
                if (groupIndex == sectionBeanList.size() - 1) {
                    List<CourseDirBean.DataBean.SectionBean.VideoBean> beanList = sectionBeanList.get(groupIndex).getVideo();
                    if (beanList != null && beanList.size() > 0) {
                        if (sonIndex == beanList.size() - 1) {
                            //所有视频都已播放完毕,不在播放下一个
                            courseDirectoryListener.directoryAllVideoPlayComplted();
                            return;
                        }
                    }
                }
                //课程目录不为空
                for (int i = groupIndex; i < sectionBeanList.size(); i++) {
                    //目录二级播放列表
                    List<CourseDirBean.DataBean.SectionBean.VideoBean> sonVideoList = sectionBeanList.get(i).getVideo();
                    //如果有二级列表，子角标++
                    if (sonVideoList != null && sonVideoList.size() > 0) {
                        //子角标变更
                        if (sonIndex < sonVideoList.size() - 1) {
                            ++sonIndex;
                            break;
                        } else {
                            ++groupIndex;
                            sonIndex = 0;
                            break;
                        }
                    } else {
                        //没有，父角标++
                        if (groupIndex != sectionBeanList.size() - 1) {
                            ++groupIndex;
                        }
                        sonIndex = 0;
                        break;
                    }
                }
            }
            if (sectionBeanList != null && groupIndex <= sectionBeanList.size() - 1) {
                showToast(getResources().getString(R.string.course_changing));
                //TODO 获取切换视频所需信息
                CourseDirBean.DataBean.SectionBean.VideoBean videoBean = sectionBeanList.get(groupIndex).getVideo().get(sonIndex);
                String vid = videoBean.getVideoId();
                String video_id = videoBean.getVideo_id();
                int videoId = videoBean.getId();
                int courseId = videoBean.getCourse_id();
                int sectionId = videoBean.getSection_id();
                //切换视频
                courseDirectoryListener.directoryChangePlayVidSource(vid, videoId, courseId, sectionId, video_id);
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

    private void logger() {
        LogUtils.e("CourseDirectory:                    groupIndex: " + groupIndex + "  sonIndex: " + sonIndex
                + "    currentClickCourseIndex: " + currentClickCourseIndex + "   currentPlayCourseIndex: " + currentPlayCourseIndex);
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
