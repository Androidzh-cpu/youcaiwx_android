package com.ucfo.youcaiwx.module.course.player;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aliyun.vodplayer.downloader.AliyunDownloadManager;
import com.aliyun.vodplayer.downloader.AliyunDownloadMediaInfo;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.UcfoApplication;
import com.ucfo.youcaiwx.adapter.download.DownloadComletedDirAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.download.DataBaseCourseListBean;
import com.ucfo.youcaiwx.entity.download.DataBaseSectioinListBean;
import com.ucfo.youcaiwx.entity.download.DataBaseVideoListBean;
import com.ucfo.youcaiwx.entity.download.DownloadCompletedDirBean;
import com.ucfo.youcaiwx.utils.LogUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.module.course.player.download.DownloadSaveInfoUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.dialog.AlertDialog;

import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-7-10 上午 11:33
 * FileName: DownloadComletedDirActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 已下载课程列表
 */
public class DownloadComletedDirActivity extends BaseActivity {
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
    @BindView(R.id.btn_checkAll)
    Button btnCheckAll;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.linear_edittor)
    LinearLayout linearEdittor;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    private String courseid;
    private boolean editStatus = false;
    private List<DownloadCompletedDirBean.DataBean> list;
    private DownloadComletedDirAdapter adapter;
    private AliyunDownloadManager downloadManager;
    private DownloadSaveInfoUtil downloadSaveInfoUtil;
    private DownloadComletedDirActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_download_comleted_dir;
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
        titlebarMidtitle.setText(getResources().getString(R.string.download_haveDownload));
        titlebarRighttitle.setText(getResources().getString(R.string.edit));
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
    protected void initData() {
        super.initData();
        context = this;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            courseid = bundle.getString(Constant.COURSE_ID, "0");
            loadinglayout.showContent();
        } else {
            loadinglayout.showEmpty();
        }
        list = new ArrayList<>();

        List<DataBaseCourseListBean> courseListBeans = LitePal.where("courseId = ?", courseid).find(DataBaseCourseListBean.class);
        courseName.setText(courseListBeans.get(0).getCourseTitle());
        courseTeacherName.setText(String.valueOf(getResources().getString(R.string.holder_teacher) + "  " + courseListBeans.get(0).getTeacherName()));

        downloadManager = UcfoApplication.downloadManager;
        downloadSaveInfoUtil = new DownloadSaveInfoUtil(downloadManager.getSaveDir());

        findDataFromDatabase();

        initAdapter();
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new DownloadComletedDirAdapter(list, this);
        } else {
            adapter.notifyDataSetChanged();
        }
        listView.setGroupIndicator(null);
        listView.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            listView.expandGroup(i);
        }

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                DownloadCompletedDirBean.DataBean.SonBean bean = list.get(groupPosition).getSon().get(childPosition);
                if (editStatus) {
                    bean.setChecked(!bean.isChecked());
                    notifyDataSetChanged();
                } else {
                    if (!fastClick(1000)) {
                        if (bean.getStatus() == 1) {
                            File downloadFile = new File(bean.getSaveDir());
                            if (downloadFile.exists()) {
                                Bundle bundle = new Bundle();
                                bundle.putString(Constant.COURSE_SOURCE, Constant.LOCAL_CACHE);
                                bundle.putString(Constant.LOCAL_PLAYURL, bean.getSaveDir());
                                bundle.putString(Constant.TITLE, bean.getVideoName());
                                startActivity(VideoPlayPageActivity.class, bundle);
                            } else {
                                ToastUtil.showBottomShortText(DownloadComletedDirActivity.this, getResources().getString(R.string.alivc_video_download_notfile));
                            }
                        } else {
                            ToastUtil.showBottomShortText(DownloadComletedDirActivity.this, getResources().getString(R.string.alivc_video_downloading_tips));
                        }
                    }
                }
                return true;
            }
        });
    }

    private void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        if (list != null && list.size() > 0) {
            loadinglayout.showContent();
        } else {
            loadinglayout.showEmpty();
        }
    }

    //查询本地数据库信息
    private void findDataFromDatabase() {
        list.clear();

        DownloadCompletedDirBean downloadCompletedDirBean = new DownloadCompletedDirBean();
        List<DownloadCompletedDirBean.DataBean> beanList = downloadCompletedDirBean.getData();

        //TODO 查询该课程下所有的section
        List<DataBaseSectioinListBean> sectioinListBeanList = LitePal.where("courseId = ?", courseid).find(DataBaseSectioinListBean.class);
        if (sectioinListBeanList != null && sectioinListBeanList.size() > 0) {//TODO 有章节
            int sectionSize = sectioinListBeanList.size();
            for (int i = 0; i < sectionSize; i++) {//TODO 循环章节查找视频
                DownloadCompletedDirBean.DataBean dataBean = new DownloadCompletedDirBean.DataBean();//TODO 创建章

                DataBaseSectioinListBean dataBaseSectioinListBean = sectioinListBeanList.get(i);
                String sectionId = dataBaseSectioinListBean.getSectionId();
                String sectionName = dataBaseSectioinListBean.getSectionName();
                String courseId = dataBaseSectioinListBean.getCourseId();
                //TODO 查询该section集合下的所有已完成的视频数据
                List<DataBaseVideoListBean> videoListBeanList = LitePal.where("sectionId = ? and status = ?", sectionId, "1").find(DataBaseVideoListBean.class);
                if (videoListBeanList.size() > 0) {//TODO 判断该章下的已完成的视频数量
                    int videoSize = videoListBeanList.size();
                    List<DownloadCompletedDirBean.DataBean.SonBean> sonBeanList = new ArrayList<>();
                    for (int j = 0; j < videoSize; j++) {//循环该章下属视频列表,添加至List中
                        DataBaseVideoListBean bean = videoListBeanList.get(j);
                        String beanCourseId = bean.getCourseId();
                        String beanSectionId = bean.getSectionId();
                        String beanVideoId = bean.getVideoId();
                        String beanVid = bean.getVid();
                        String beanVideoDuration = bean.getVideoDuration();
                        String beanVideoName = bean.getVideoName();
                        int beanStatus = bean.getStatus();
                        String beanSaveDir = bean.getSaveDir();
                        if (beanStatus == 1) {
                            //TODO 创建视频
                            DownloadCompletedDirBean.DataBean.SonBean sonBean = new DownloadCompletedDirBean.DataBean.SonBean(beanCourseId, beanSectionId, beanVideoId, beanVid, beanVideoName, beanVideoDuration, beanStatus, beanSaveDir);
                            //TODO 添加视频至对应章节下
                            sonBeanList.add(sonBean);
                        }
                    }
                    //TODO 添加视频列表至张目录下
                    dataBean.setCourseId(courseId);//todo 设置数据源
                    dataBean.setSectionName(sectionName);
                    dataBean.setSectionId(sectionId);
                    dataBean.setSon(sonBeanList);

                    beanList.add(dataBean);
                } else {//TODO 章属下没有已完成的视频
                    List<DataBaseVideoListBean> videoListBeans = LitePal.where("sectionId = ? and status = ?", sectionId, "0").find(DataBaseVideoListBean.class);
                    if (videoListBeans.size() > 0) {//有未完成的视频

                    } else {//完成的和未完成的视频都没有,删除该章节
                        LitePal.deleteAll(DataBaseSectioinListBean.class, "courseId = ? and sectionId = ?", courseid, sectionId);
                    }
                }
            }

        }
        list.addAll(beanList);
        outputLog("list :" + list.toString());
    }

    private void outputLog(String text) {
        LogUtils.e("DB 操作: " + text);
    }

    private boolean isCheckAll = false;

    @OnClick({R.id.titlebar_righttitle, R.id.btn_checkAll, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titlebar_righttitle://TODO 编辑
                editStatus = !editStatus;
                if (editStatus) {
                    titlebarRighttitle.setText(getResources().getString(R.string.cancel));
                    linearEdittor.setVisibility(View.VISIBLE);
                } else {
                    titlebarRighttitle.setText(getResources().getString(R.string.edit));
                    linearEdittor.setVisibility(View.GONE);
                }
                if (adapter != null) {
                    adapter.notifychange(editStatus);
                }
                break;
            case R.id.btn_checkAll://TODO 全选
                isCheckAll = !isCheckAll;
                if (list != null && list.size() > 0) {
                    for (DownloadCompletedDirBean.DataBean dataBean : list) {
                        List<DownloadCompletedDirBean.DataBean.SonBean> son = dataBean.getSon();
                        for (DownloadCompletedDirBean.DataBean.SonBean bean : son) {
                            bean.setChecked(isCheckAll);
                        }
                    }
                    notifyDataSetChanged();
                }
                if (isCheckAll) {//全选
                    btnDelete.setBackgroundColor(ContextCompat.getColor(context, R.color.color_0267FF));


                    btnCheckAll.setText(getResources().getString(R.string.cancelCheckAll));
                    titlebarRighttitle.setText(getResources().getString(R.string.cancel));
                } else {//取消全选
                    titlebarRighttitle.setText(getResources().getString(R.string.edit));
                    btnDelete.setBackgroundColor(ContextCompat.getColor(context, R.color.color_DCDCDC));
                    btnCheckAll.setText(getResources().getString(R.string.checkAll));
                }
                break;
            case R.id.btn_delete://TODO 删除
                deleteVideoFromDatabase();
                break;
            default:
                break;
        }
    }

    //删除选中项
    private void deleteVideoFromDatabase() {
        int i = 0;
        for (DownloadCompletedDirBean.DataBean dataBean : list) {
            List<DownloadCompletedDirBean.DataBean.SonBean> sonBeanList = dataBean.getSon();
            for (DownloadCompletedDirBean.DataBean.SonBean sonBean : sonBeanList) {
                boolean checked = sonBean.isChecked();
                if (checked) {
                    i++;
                }
            }
        }
        new AlertDialog(context).builder()
                .setMsg(getResources().getString(R.string.download_deleteComfirm, String.valueOf(i)))
                .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<String> vidList = new ArrayList<>();
                        for (DownloadCompletedDirBean.DataBean dataBean : list) {
                            List<DownloadCompletedDirBean.DataBean.SonBean> sonBeanList = dataBean.getSon();
                            for (DownloadCompletedDirBean.DataBean.SonBean sonBean : sonBeanList) {
                                boolean checked = sonBean.isChecked();
                                if (checked) {//TODO 获取选中的视频
                                    String vid = sonBean.getVid();
                                    vidList.add(vid);
                                }
                            }
                        }
                        int size = vidList.size();
                        if (size > 0) {//TODO 选中视频的VID
                            for (int j = 0; j < vidList.size(); j++) {
                                String vid = vidList.get(j);
                                List<AliyunDownloadMediaInfo> alivcDownloadeds = downloadSaveInfoUtil.getAlivcDownloadeds();
                                for (AliyunDownloadMediaInfo info : alivcDownloadeds) {
                                    String vid2 = info.getVid();
                                    if (vid.equals(vid2)) {
                                        downloadManager.addDownloadMedia(info);
                                        downloadManager.removeDownloadMedia(info);
                                        downloadSaveInfoUtil.deleteInfo(info);
                                        break;
                                    }
                                }
                                //删除数据库文件
                                LitePal.deleteAll(DataBaseVideoListBean.class, "courseId = ? and vid = ?", courseid, vid);
                            }
                            findDataFromDatabase();
                            notifyDataSetChanged();
                        } else {//TODO 没有选中的视频 nothing
                            ToastUtil.showBottomShortText(context, getResources().getString(R.string.no_delete));
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //nothing
                    }
                })
                .setCancelable(false)
                .setCanceledOnTouchOutside(false).show();
    }
}
