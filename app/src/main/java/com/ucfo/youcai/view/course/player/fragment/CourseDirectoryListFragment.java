package com.ucfo.youcai.view.course.player.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.ucfo.youcai.R;
import com.ucfo.youcai.adapter.course.CourseDirWindowAdapter;
import com.ucfo.youcai.adapter.course.CoursePackageListAdapter;
import com.ucfo.youcai.base.BaseFragment;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.course.CourseDirBean;
import com.ucfo.youcai.presenter.presenterImpl.course.CourseDirPresenter;
import com.ucfo.youcai.presenter.view.course.ICourseDirView;
import com.ucfo.youcai.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcai.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcai.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcai.utils.systemutils.DensityUtil;
import com.ucfo.youcai.utils.toastutils.ToastUtil;
import com.ucfo.youcai.view.course.player.DownloadDirectoryActivity;
import com.ucfo.youcai.view.course.player.VideoPlayPageActivity;
import com.ucfo.youcai.view.login.LoginActivity;
import com.ucfo.youcai.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author:29117
 * Time: 2019-4-3.  上午 10:02
 * Email:2911743255@qq.com
 * ClassName: CourseDirectoryListFragment
 * Package: com.ucfo.youcai.view.course.player.fragment
 * Description:TODO 课程目录
 * Detail:TODO fragment_coursedirectorylist
 */
public class CourseDirectoryListFragment extends BaseFragment implements ICourseDirView {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    Unbinder unbinder;
    @BindView(R.id.layout_main)
    ConstraintLayout layoutMain;
    LoadingLayout loadinglayout;

    private VideoPlayPageActivity videoPlayPageActivity;
    private Context context;
    private int course_packageId;
    private int user_id;
    private CourseDirPresenter courseDirPresenter;
    private List<CourseDirBean.DataBean> coursePackageList;
    private List<CourseDirBean.DataBean.SectionBean> sectionBeanList;
    private CoursePackageListAdapter coursePackageListAdapter;
    private PopupWindow courseDirWindow;
    private CourseDirWindowAdapter courseDirWindowAdapter;
    private int currentPlayCourseIndex = -1, currentClickCourseIndex = -1;//当前播放的课程所属套餐索引值;  当前点击的课程所属套餐索引值
    private int groupIndex = -1, sonIndex = -1, course_buy_state;
    private boolean login_status;

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

    /**
     * Description:CourseDirectoryListFragment
     * Time:2019-4-22   下午 1:57
     * Detail: 获取弹框状态
     */
    public boolean coursWindowisShow() {
        if (courseDirWindow != null) {
            if (courseDirWindow.isShowing()) {
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
        if (courseDirWindow != null) {
            if (courseDirWindow.isShowing()) {
                courseDirWindow.dismiss();
            }
        }
    }

    @Override
    protected void initView(View view) {
        context = getActivity();
        loadinglayout = view.findViewById(R.id.loadinglayout);

        user_id = SharedPreferencesUtils.getInstance(getActivity()).getInt(Constant.USER_ID, 0);
        login_status = SharedPreferencesUtils.getInstance(getActivity()).getBoolean(Constant.LOGIN_STATUS, false);

        FragmentActivity activity = getActivity();
        if (activity instanceof VideoPlayPageActivity) {
            videoPlayPageActivity = (VideoPlayPageActivity) getActivity();
        }

        course_packageId = videoPlayPageActivity.getCourse_packageId();

        coursePackageList = new ArrayList<>();
        sectionBeanList = new ArrayList<>();

    }

    @Override
    protected void initData() {
        courseDirPresenter = new CourseDirPresenter(this);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity());
        layoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager3.setReverseLayout(false);
        recyclerview.setLayoutManager(layoutManager3);

        int topBottom = DensityUtil.dip2px(getActivity(), 4);
        recyclerview.addItemDecoration(new SpacesItemDecoration(0, topBottom, Color.TRANSPARENT));
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                courseDirPresenter.getCourseDirData(course_packageId, user_id);
            }
        });

        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseDirPresenter.getCourseDirData(course_packageId, user_id);
            }
        });

    }

    @Override
    protected void onLazyLoadOnce() {
        super.onLazyLoadOnce();
        //获取课程目录
        courseDirPresenter.getCourseDirData(course_packageId, user_id);
    }

    @Override
    protected void onInvisibleToUser() {
        super.onInvisibleToUser();
        if (courseDirWindow != null) {
            if (courseDirWindow.isShowing()) {
                courseDirWindow.dismiss();
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
            coursePackageList.clear();
            coursePackageList.addAll(data);

            initAdapter(coursePackageList);//设置课程套餐适配器

            loadinglayout.showContent();
        } else {
            loadinglayout.showEmpty();
        }

        refreshlayout.finishLoadMore();//结束加载动画
        refreshlayout.finishRefresh();
    }

    /**
     * Description:CourseDirectoryListFragment
     * Time:2019-4-16   下午 1:40
     * Detail: TODO 课程套餐适配器
     */
    private void initAdapter(List<CourseDirBean.DataBean> coursePackageLists) {
        if (coursePackageListAdapter == null) {
            coursePackageListAdapter = new CoursePackageListAdapter(coursePackageLists, getActivity());
        } else {
            coursePackageListAdapter.notifyDataSetChanged();
        }
        recyclerview.setAdapter(coursePackageListAdapter);
        coursePackageListAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                List<CourseDirBean.DataBean.SectionBean> section = coursePackageLists.get(i).getSection();
                String is_zhengke = coursePackageLists.get(i).getIs_zhengke();
                int parseInt = Integer.parseInt(is_zhengke);
                CourseDirBean.DataBean dataBean = coursePackageLists.get(i);//当前课程信息

                currentClickCourseIndex = i;//当前课程点击位置

                showCourseDirWindow(dataBean, section, parseInt);
            }
        });
    }

    /**
     * Description:CourseDirectoryListFragment
     * Time:2019-4-16   上午 11:49
     * Detail:TODO 点击课程包,弹出对应课程播放列表
     */
    private void showCourseDirWindow(CourseDirBean.DataBean dataBean, List<CourseDirBean.DataBean.SectionBean> beanList, int course_un_con) {
        sectionBeanList.clear();
        sectionBeanList.addAll(beanList);

        course_buy_state = videoPlayPageActivity.getCourse_buy_state();

        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_coursedir_window, null);
        courseDirWindow = new PopupWindow(contentView);
        courseDirWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);//TODO 设置宽高
        if (course_buy_state == 1) {
            courseDirWindow.setHeight(layoutMain.getMeasuredHeight());
        } else {
            courseDirWindow.setHeight(layoutMain.getMeasuredHeight() + DensityUtil.dip2px(getActivity(), 45));
        }
        courseDirWindow.setFocusable(false);//区域外点击不消失
        courseDirWindow.setOutsideTouchable(false);////区域外点击不消失
        courseDirWindow.setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));//设置背景
        courseDirWindow.setSplitTouchEnabled(true);//多点触控
        //courseDirWindow.setAnimationStyle(R.style.MaterialDialogSheetAnimation);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            courseDirWindow.setAttachedInDecor(true);//NavigationBar重叠
        }
        courseDirWindow.setClippingEnabled(true);
        if (courseDirWindow.isShowing()) {//判断是否显示中
            courseDirWindow.dismiss();//消失
        } else {
            initDirView(contentView, dataBean, sectionBeanList, course_un_con);
        }

    }

    /**
     * Description:CourseDirectoryListFragment
     * Time:2019-4-16   下午 1:04
     * Detail: 课程目录数据处理
     */
    private void initDirView(View contentView, CourseDirBean.DataBean dataBean, List<CourseDirBean.DataBean.SectionBean> sectionList, int course_un_con) {
        TextView courseName = (TextView) contentView.findViewById(R.id.course_name);
        TextView courseTeacherName = (TextView) contentView.findViewById(R.id.course_teacher_name);
        ImageView courseDownload = (ImageView) contentView.findViewById(R.id.course_down_btn);
        courseDownload.setClickable(true);
        RoundTextView courseCloseBtn = (RoundTextView) contentView.findViewById(R.id.course_closebtn);
        ExpandableListView courseTree = (ExpandableListView) contentView.findViewById(R.id.course_dirlist);
        courseName.setText(dataBean.getName());//课程名称
        courseTeacherName.setText(String.valueOf(getResources().getString(R.string.holder_teacher) + dataBean.getTeacher_name()));//讲师名称
        if (courseDirWindowAdapter == null) {
            courseDirWindowAdapter = new CourseDirWindowAdapter(getActivity(), sectionList);
        } else {
            courseDirWindowAdapter.notifyDataSetChanged();
        }
        courseTree.setGroupIndicator(null);//去掉系统默认的一级列表指示器
        courseTree.setAdapter(courseDirWindowAdapter);//添加适配器
        //如果当前点击的套餐列表的索引值==当前播放的课程套餐的索引,可以展开对应的一级目录和二级目录
        if (currentPlayCourseIndex == currentClickCourseIndex) {
            courseTree.expandGroup(groupIndex);
            courseTree.setSelectedGroup(groupIndex);
            courseTree.setSelectedChild(groupIndex, sonIndex, true);
        }
        courseDirWindow.showAtLocation(layoutMain, Gravity.BOTTOM, 0, 0);
        courseDirWindow.update();
        courseCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseDirWindow.dismiss();
            }
        });
        courseDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!login_status) {
                    startActivity(LoginActivity.class, null);
                } else {
                    if (course_buy_state == 1) {
                        Bundle bundle = new Bundle();
                        String teacher_name = dataBean.getTeacher_name();
                        String name = dataBean.getName();
                        bundle.putString(Constant.TITLE, name);
                        bundle.putString(Constant.TEACHER_NAME, teacher_name);
                        bundle.putInt(Constant.PACKAGE_ID, course_packageId);
                        bundle.putInt(Constant.PAGE, currentClickCourseIndex);
                        startActivity(DownloadDirectoryActivity.class, bundle);
                        videoPlayPageActivity.pause();
                    } else {
                        ToastUtil.showBottomShortText(videoPlayPageActivity, getResources().getString(R.string.course_bugCourse));
                    }
                }
            }
        });
        //TODO 小节视频点击事件,点击之后播放视频
        courseTree.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (!login_status) {
                    startActivity(LoginActivity.class, null);
                } else {
                    String vid = sectionList.get(groupPosition).getVideo().get(childPosition).getVideoId();
                    int video_id = sectionList.get(groupPosition).getVideo().get(childPosition).getId();
                    int course_id = sectionList.get(groupPosition).getVideo().get(childPosition).getCourse_id();
                    int section_id = sectionList.get(groupPosition).getVideo().get(childPosition).getSection_id();
                    String video_name = sectionList.get(groupPosition).getVideo().get(childPosition).getVideo_name();

                    videoPlayPageActivity.changePlayVidSource(vid, video_id, course_id, section_id);//TODO 调用播放器,切换视频
                    videoPlayPageActivity.setPlayVideoName(video_name);//切换提问标题
                    videoPlayPageActivity.setCourse_un_con(course_un_con);//是否是正课标识

                    groupIndex = groupPosition;
                    sonIndex = childPosition;
                    currentPlayCourseIndex = currentClickCourseIndex;

                    courseDirWindowAdapter.setSelectPosition(groupIndex, sonIndex, currentPlayCourseIndex, currentClickCourseIndex);//设置选中的位置

                    coursePackageListAdapter.notifyDataSetChanged();//课程包列表刷新
                    courseDirWindowAdapter.notifyDataSetChanged();//课程播放目录列表刷新
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
        if (groupIndex <= section.size() - 1) {
            ToastUtil.showBottomLongText(getActivity(), getResources().getString(R.string.course_changing));
            //TODO 获取切换视频所需信息
            CourseDirBean.DataBean.SectionBean.VideoBean videoBean = section.get(groupIndex).getVideo().get(sonIndex);
            String vid = videoBean.getVideoId();
            int video_id = videoBean.getId();
            int course_id = videoBean.getCourse_id();
            int section_id = videoBean.getSection_id();

            videoPlayPageActivity.changePlayVidSource(vid, video_id, course_id, section_id);//TODO 调用播放器,切换视频

            if (courseDirWindowAdapter != null) {
                courseDirWindowAdapter.notifyDataSetChanged();
            }
            courseDirWindowAdapter.setSelectPosition(groupIndex, sonIndex, currentPlayCourseIndex, currentClickCourseIndex);//设置选中的位置
            coursePackageListAdapter.notifyDataSetChanged();//列表刷新
            courseDirWindowAdapter.notifyDataSetChanged();//列表刷新
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
        loadinglayout.showError();
    }
}
