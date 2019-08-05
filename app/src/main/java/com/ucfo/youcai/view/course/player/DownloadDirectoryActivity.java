package com.ucfo.youcai.view.course.player;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.ucfo.youcai.R;
import com.ucfo.youcai.adapter.download.DownloadDirAdapter;
import com.ucfo.youcai.base.BaseActivity;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.course.CourseDirBean;
import com.ucfo.youcai.entity.download.PreparedDownloadInfoBean;
import com.ucfo.youcai.presenter.presenterImpl.course.CourseDirPresenter;
import com.ucfo.youcai.presenter.view.course.ICourseDirView;
import com.ucfo.youcai.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcai.utils.systemutils.StatusbarUI;
import com.ucfo.youcai.utils.toastutils.ToastUtil;
import com.ucfo.youcai.view.user.activity.OfflineCourseActivity;
import com.ucfo.youcai.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-7-2 上午 10:21
 * Package: com.ucfo.youcai.view.course.player
 * FileName: DownloadDirectoryActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 课程下载目录
 */
public class DownloadDirectoryActivity extends BaseActivity implements ICourseDirView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.course_name)
    TextView courseName;
    @BindView(R.id.course_teacher_name)
    TextView courseTeacherName;
    @BindView(R.id.listView)
    ExpandableListView listView;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.btn_exit)
    Button btnExit;
    private Bundle bundle;
    private String course_title, course_teachername;
    private DownloadDirectoryActivity context;
    private CourseDirPresenter courseDirPresenter;
    private int package_id, user_id, currentClickCourseIndex;
    private ArrayList<CourseDirBean.DataBean> list;
    private DownloadDirAdapter downloadDirAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_download_directory;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        StatusbarUI.setStatusBarUIMode(this, Color.TRANSPARENT, true);
        setSupportActionBar(titlebarToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        titlebarRighttitle.setVisibility(View.GONE);
        titlebarMidtitle.setText(getResources().getString(R.string.course_download));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        context = this;
        user_id = SharedPreferencesUtils.getInstance(context).getInt(Constant.USER_ID, 0);
        list = new ArrayList<>();
        courseDirPresenter = new CourseDirPresenter(this);
    }

    @Override
    protected void initData() {
        super.initData();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            course_title = bundle.getString(Constant.TITLE, "");
            course_teachername = bundle.getString(Constant.TEACHER_NAME, "");
            package_id = bundle.getInt(Constant.PACKAGE_ID, 0);
            currentClickCourseIndex = bundle.getInt(Constant.PAGE, 0);

            courseName.setText(course_title);
            courseTeacherName.setText(String.valueOf(getResources().getString(R.string.holder_teacher) + "  " + course_teachername));

            courseDirPresenter.getCourseDirData(package_id, user_id);
        } else {
            loadinglayout.showEmpty();
            return;
        }
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseDirPresenter.getCourseDirData(package_id, user_id);
            }
        });
    }

    @OnClick(R.id.btn_exit)
    public void onViewClicked() {
        int resultSize = 0;
        ArrayList<PreparedDownloadInfoBean> preparedDownloadInfoBeanArrayList = new ArrayList<>();

        List<CourseDirBean.DataBean.SectionBean> sectionBeanList = list.get(currentClickCourseIndex).getSection();
        for (int i = 0; i < sectionBeanList.size(); i++) {
            CourseDirBean.DataBean.SectionBean sectionBean = sectionBeanList.get(i);
            List<CourseDirBean.DataBean.SectionBean.VideoBean> videoBeanList = sectionBean.getVideo();
            for (int j = 0; j < videoBeanList.size(); j++) {
                boolean checked = videoBeanList.get(j).getChecked();
                if (checked) {
                    resultSize++;
                    PreparedDownloadInfoBean preparedDownloadInfoBean = new PreparedDownloadInfoBean();
                    preparedDownloadInfoBean.setCourseId(list.get(currentClickCourseIndex).getCourse_id());
                    preparedDownloadInfoBean.setCourseName(list.get(currentClickCourseIndex).getName());
                    preparedDownloadInfoBean.setTeacherName(list.get(currentClickCourseIndex).getTeacher_name());
                    preparedDownloadInfoBean.setSectionId(String.valueOf(sectionBean.getSection_id()));
                    preparedDownloadInfoBean.setSectionName(sectionBean.getSection_name());
                    preparedDownloadInfoBean.setVideoId(String.valueOf(videoBeanList.get(j).getId()));
                    preparedDownloadInfoBean.setVid(videoBeanList.get(j).getVideoId());
                    preparedDownloadInfoBean.setVideoName(videoBeanList.get(j).getVideo_name());
                    preparedDownloadInfoBean.setVideoDuration(videoBeanList.get(j).getVideo_time());
                    preparedDownloadInfoBeanArrayList.add(preparedDownloadInfoBean);
                }
            }
        }
        if (resultSize == 0) {
            ToastUtil.showBottomShortText(context, "请选择需要下载的视频");
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.PAGE, 1);
            bundle.putParcelableArrayList(Constant.DOWNLOADINFO_LIST, preparedDownloadInfoBeanArrayList);
            startActivity(OfflineCourseActivity.class, bundle);
            finish();
        }
    }

    @Override
    public void getCourseDirData(CourseDirBean courseDirBean) {
        if (courseDirBean.getData() != null && courseDirBean.getData().size() != 0) {
            List<CourseDirBean.DataBean> data = courseDirBean.getData();
            list.clear();
            list.addAll(data);
            initAdapter();
            loadinglayout.showContent();
        } else {
            loadinglayout.showEmpty();
        }
    }

    private void initAdapter() {
        List<CourseDirBean.DataBean.SectionBean> sectionBeanList = list.get(currentClickCourseIndex).getSection();
        if (downloadDirAdapter == null) {
            downloadDirAdapter = new DownloadDirAdapter(this, sectionBeanList);
        } else {
            downloadDirAdapter.notifyDataSetChanged();
        }
        listView.setGroupIndicator(null);
        listView.setAdapter(downloadDirAdapter);
        for (int i = 0; i < downloadDirAdapter.getGroupCount(); i++) {
            listView.expandGroup(i);
        }
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                CourseDirBean.DataBean.SectionBean.VideoBean bean = sectionBeanList.get(groupPosition).getVideo().get(childPosition);
                bean.setChecked(!bean.getChecked());
                downloadDirAdapter.notifyDataSetChanged();
                return true;
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
}
