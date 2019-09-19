package com.ucfo.youcaiwx.module.course.player;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.aliyun.vodplayer.downloader.AliyunDownloadMediaInfo;
import com.google.gson.Gson;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.UcfoApplication;
import com.ucfo.youcaiwx.adapter.download.DownloadDirAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.course.CourseDirBean;
import com.ucfo.youcaiwx.entity.download.DataBaseVideoListBean;
import com.ucfo.youcaiwx.entity.download.PreparedDownloadInfoBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.course.CourseDirPresenter;
import com.ucfo.youcaiwx.presenter.view.course.ICourseDirView;
import com.ucfo.youcaiwx.utils.LogUtils;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.module.course.player.download.DownloadSaveInfoUtil;
import com.ucfo.youcaiwx.module.user.activity.OfflineCourseActivity;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-7-2 上午 10:21
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
    private Gson gson;
    private String courseId;
    private DownloadSaveInfoUtil downloadSaveInfoUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_download_directory;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
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

            //获取对应课程视频列表
            courseDirPresenter.getCourseDirData(package_id, user_id);
        } else {
            loadinglayout.showEmpty();
        }
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseDirPresenter.getCourseDirData(package_id, user_id);
            }
        });
        downloadSaveInfoUtil = new DownloadSaveInfoUtil(UcfoApplication.downloadManager.getSaveDir());
        List<AliyunDownloadMediaInfo> alivcDownloadeds = downloadSaveInfoUtil.getAlivcDownloadeds();
        LogUtils.e("下载列表--------------" + new Gson().toJson(alivcDownloadeds));
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
        courseId = list.get(currentClickCourseIndex).getCourse_id();
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
                initItemClick(sectionBeanList, groupPosition, childPosition);
                return true;
            }
        });
    }

    /**
     * 列表时间处理
     */
    private void initItemClick(List<CourseDirBean.DataBean.SectionBean> sectionBeanList, int groupPosition, int childPosition) {
        CourseDirBean.DataBean.SectionBean.VideoBean bean = sectionBeanList.get(groupPosition).getVideo().get(childPosition);
        //TODO 获取item视频的vid
        String vid = bean.getVideoId();
        //查询库中是否含有该视频
        List<DataBaseVideoListBean> baseVideoListBeans = LitePal.where("courseId = ? and vid = ?", String.valueOf(courseId), vid).find(DataBaseVideoListBean.class);
        if (baseVideoListBeans != null && baseVideoListBeans.size() > 0) {
            DataBaseVideoListBean videoListBean = baseVideoListBeans.get(0);
            int status = videoListBean.getStatus();
            if (status == 1) {
                ToastUtil.showBottomShortText(context, getResources().getString(R.string.alivc_video_download_finish_tips));
            } else {
                ToastUtil.showBottomShortText(context, getResources().getString(R.string.alivc_video_download_finish_haved));
            }
            bean.setChecked(false);
            downloadDirAdapter.notifyDataSetChanged();
        } else {
            //DB没有存贮该视频
            bean.setChecked(!bean.getChecked());
            downloadDirAdapter.notifyDataSetChanged();

            List<AliyunDownloadMediaInfo> alivcDownloadeds = downloadSaveInfoUtil.getAlivcDownloadeds();
            for (AliyunDownloadMediaInfo info : alivcDownloadeds) {
                String vid2 = info.getVid();
                if (TextUtils.equals(vid, vid2)) {
                    String savePath = info.getSavePath();
                    if (!TextUtils.isEmpty(savePath)) {
                        File file = new File(savePath);
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                    downloadSaveInfoUtil.deleteInfo(info);
                    break;
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
        loadinglayout.showError();
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

}
