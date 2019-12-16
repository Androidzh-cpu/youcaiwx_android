package com.ucfo.youcaiwx.module.course.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aliyun.vodplayer.downloader.AliyunDownloadInfoListener;
import com.aliyun.vodplayer.downloader.AliyunDownloadManager;
import com.aliyun.vodplayer.downloader.AliyunDownloadMediaInfo;
import com.aliyun.vodplayer.downloader.AliyunRefreshStsCallback;
import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.UcfoApplication;
import com.ucfo.youcaiwx.adapter.download.DownloadingAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.download.AlivcDownloadMediaInfo;
import com.ucfo.youcaiwx.entity.download.DataBaseCourseListBean;
import com.ucfo.youcaiwx.entity.download.DataBaseSectioinListBean;
import com.ucfo.youcaiwx.entity.download.DataBaseVideoListBean;
import com.ucfo.youcaiwx.entity.download.GetVideoStsBean;
import com.ucfo.youcaiwx.entity.download.PreparedDownloadInfoBean;
import com.ucfo.youcaiwx.module.course.player.download.DownloadDataProvider;
import com.ucfo.youcaiwx.module.course.player.download.StorageQueryUtil;
import com.ucfo.youcaiwx.module.course.player.utils.ErrorInfo;
import com.ucfo.youcaiwx.module.course.player.utils.NetWatchdog;
import com.ucfo.youcaiwx.module.user.activity.OfflineCourseActivity;
import com.ucfo.youcaiwx.utils.LogUtils;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.dialog.AlertDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-2.  下午 1:51
 * FileName: DownloadingFragment
 * Description:TODO 正在下载中的视频
 * detail:TODO 准备下载和开始下载为相互独立的功能，在用户获取到需要下载的Datasource便可开始下载
 */
public class DownloadingFragment extends BaseFragment implements View.OnClickListener {
    public OfflineCourseActivity offlineCourseActivity;

    private ListView listView;
    private LoadingLayout loadinglayout;
    private TextView sdcardTotalSpace;
    private TextView sdcardResidueSpace;
    private LinearLayout linearSdcardSpace;
    private Button btnCheckAll;
    private Button btnDelete;
    private LinearLayout linearEdittor;

    private ArrayList<AlivcDownloadMediaInfo> alivcDownloadingMediaInfos;
    private List<AliyunDownloadMediaInfo> allDownloadMediaInfo;

    private AliyunDownloadManager downloadManager;
    private AliyunVidSts mVidSts;
    private DownloadDataProvider downloadDataProvider;

    private DownloadingAdapter downloadingAdapter;
    private boolean editStatus = false, isCheckAll = false;
    private MyDownloadInfoListener myDownloadInfoListener;
    private ArrayList<PreparedDownloadInfoBean> offlineCourseActivityParcelableArrayList;
    private NetWatchdog mNetWatchdog;
    private boolean downloadWifi;
    private ErrorInfo currentError = ErrorInfo.Normal;
    private Gson gson;

    @Override
    public void onResume() {
        super.onResume();
        if (mNetWatchdog != null) {
            mNetWatchdog.startWatch();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mNetWatchdog != null) {
            mNetWatchdog.stopWatch();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mNetWatchdog != null) {
            mNetWatchdog.stopWatch();
        }
        mNetWatchdog = null;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_downloading;
    }

    @Override
    protected void initView(View itemView) {
        listView = (ListView) itemView.findViewById(R.id.listView);
        loadinglayout = (LoadingLayout) itemView.findViewById(R.id.loadinglayout);
        sdcardTotalSpace = (TextView) itemView.findViewById(R.id.sdcard_totalSpace);
        sdcardResidueSpace = (TextView) itemView.findViewById(R.id.sdcard_residueSpace);
        linearSdcardSpace = (LinearLayout) itemView.findViewById(R.id.linear_sdcardSpace);
        btnCheckAll = (Button) itemView.findViewById(R.id.btn_checkAll);
        btnCheckAll.setOnClickListener(this);
        btnDelete = (Button) itemView.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(this);
        linearEdittor = (LinearLayout) itemView.findViewById(R.id.linear_edittor);


        FragmentActivity activity = getActivity();
        if (activity instanceof OfflineCourseActivity) {
            offlineCourseActivity = (OfflineCourseActivity) activity;
        }
        initSdcardSpace();
        initNetWatchdog();
        offlineCourseActivityParcelableArrayList = offlineCourseActivity.getParcelableArrayList();
    }

    @Override
    protected void initData() {
        downloadWifi = SharedPreferencesUtils.getInstance(getActivity()).getBoolean(Constant.DOWNLOAD_WIFI, false);
        gson = new Gson();
    }

    @Override
    protected void onLazyLoadOnce() {
        super.onLazyLoadOnce();
        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), new CheckRequestPermissionsListener() {
                    @Override
                    public void onAllPermissionOk(Permission[] allPermissions) {
                    }

                    @Override
                    public void onPermissionDenied(Permission[] refusedPermissions) {
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity(), R.style.WhiteDialogStyle);
                        builder.setTitle(getActivity().getResources().getString(R.string.explication));
                        builder.setMessage(getActivity().getResources().getString(R.string.permission_sdcard));
                        builder.setCancelable(false);
                        builder.setPositiveButton(getActivity().getResources().getString(R.string.donner),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SoulPermission.getInstance().goApplicationSettings();
                                    }
                                });
                        builder.create();
                        builder.show();
                    }
                });


        initDownloadConfig();

        if (offlineCourseActivityParcelableArrayList != null && offlineCourseActivityParcelableArrayList.size() > 0) {
            String vid = offlineCourseActivityParcelableArrayList.get(0).getVid();
            loadSTSData(vid, 0);
        }
        initDownloadingAdapter();
    }

    /**
     * 视频下载配置
     */
    private void initDownloadConfig() {
        LitePal.getDatabase();
        alivcDownloadingMediaInfos = new ArrayList<>();
        allDownloadMediaInfo = new ArrayList<>();
        alivcDownloadingMediaInfos.clear();
        allDownloadMediaInfo.clear();

        downloadManager = UcfoApplication.downloadManager;
        downloadDataProvider = DownloadDataProvider.getSingleton(UcfoApplication.getInstance());

        downloadManager.setRefreshStsCallback(new MyRefreshStsCallback());// 更新sts回调
        downloadManager.setDownloadInfoListener(new MyDownloadInfoListener());// 视频下载的回调  注意在不需要的时候，调用remove，移除监听。

        allDownloadMediaInfo.addAll(downloadDataProvider.getAllDownloadMediaInfo());

        if (allDownloadMediaInfo != null && allDownloadMediaInfo.size() > 0) {
            int size = allDownloadMediaInfo.size();
            for (int i = 0; i < size; i++) {
                AliyunDownloadMediaInfo info = allDownloadMediaInfo.get(i);
                AliyunDownloadMediaInfo.Status status = info.getStatus();
                if (status != AliyunDownloadMediaInfo.Status.Complete) {
                    downloadManager.stopDownloadMedia(info);

                    AlivcDownloadMediaInfo alivcDownloadMediaInfo = new AlivcDownloadMediaInfo();
                    alivcDownloadMediaInfo.setEditState(false);
                    alivcDownloadMediaInfo.setCheckedState(false);
                    alivcDownloadMediaInfo.setAliyunDownloadMediaInfo(info);
                    alivcDownloadingMediaInfos.add(alivcDownloadMediaInfo);
                }
            }

        }
        AliyunDownloadManager.enableNativeLog();
    }

    /**
     * 初始化网络监听
     */
    private void initNetWatchdog() {
        mNetWatchdog = new NetWatchdog(getActivity());
        mNetWatchdog.setNetChangeListener(new MyNetChangeListener());
        mNetWatchdog.setNetConnectedListener(new MyNetConnectedListener());
    }

    /**
     * 手机可用空间大小
     */
    private void initSdcardSpace() {
        String availSize = StorageQueryUtil.getAvailSize(getActivity());
        String totalSize = StorageQueryUtil.getTotalSize(getActivity());
        sdcardTotalSpace.setText(totalSize);
        sdcardResidueSpace.setText(availSize);
    }

    /**
     * 编辑视频
     */
    public void editVideoList(boolean status) {
        editStatus = status;
        if (editStatus) {//编辑中
            if (downloadingAdapter != null) {
                downloadingAdapter.notifychange(editStatus);
            }
            linearEdittor.setVisibility(View.VISIBLE);
            linearSdcardSpace.setVisibility(View.GONE);
        } else {//取消编辑
            if (downloadingAdapter != null) {
                downloadingAdapter.notifychange(editStatus);
            }
            linearEdittor.setVisibility(View.GONE);
            linearSdcardSpace.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置下载中适配器
     */
    private void initDownloadingAdapter() {
        if (alivcDownloadingMediaInfos.size() > 0) {
            if (loadinglayout != null) {
                loadinglayout.showContent();
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
        if (downloadingAdapter == null) {
            downloadingAdapter = new DownloadingAdapter(alivcDownloadingMediaInfos, getActivity());
            listView.setAdapter(downloadingAdapter);
        } else {
            downloadingAdapter.setData(alivcDownloadingMediaInfos);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (editStatus) {//TODO Editting
                    AlivcDownloadMediaInfo mediaInfo = alivcDownloadingMediaInfos.get(position);//check this item
                    mediaInfo.setCheckedState(!mediaInfo.isCheckedState());
                    downloadingAdapter.notifyDataSetChanged();
                    checkAndUnCheck(alivcDownloadingMediaInfos);
                } else {//TODO nothing to do
                    AliyunDownloadMediaInfo info = alivcDownloadingMediaInfos.get(position).getAliyunDownloadMediaInfo();
                    AliyunDownloadMediaInfo.Status status = info.getStatus();
                    if (status == AliyunDownloadMediaInfo.Status.Start) {//staring
                        if (downloadManager != null) {
                            downloadManager.stopDownloadMedia(info);
                        }
                        info.setStatus(AliyunDownloadMediaInfo.Status.Stop);
                    } else if (status == AliyunDownloadMediaInfo.Status.Error || status == AliyunDownloadMediaInfo.Status.Wait || status == AliyunDownloadMediaInfo.Status.Stop) {//waitting
                        if (downloadManager != null) {
                            downloadManager.startDownloadMedia(info);
                        }
                        info.setStatus(AliyunDownloadMediaInfo.Status.Start);
                    }
                    notifyDataSetChanged();
                }
            }
        });
    }

    private void checkAndUnCheck(ArrayList<AlivcDownloadMediaInfo> mediaInfos) {
        boolean flag = false;
        for (int i = 0; i < mediaInfos.size(); i++) {
            boolean checked = mediaInfos.get(i).isCheckedState();
            if (checked) {
                flag = true;
                break;
            }
        }
        if (flag) {
            btnDelete.setBackgroundColor(ContextCompat.getColor(context, R.color.color_0267FF));
            btnCheckAll.setText(getResources().getString(R.string.cancelCheckAll));
        } else {
            btnDelete.setBackgroundColor(ContextCompat.getColor(context, R.color.color_DCDCDC));
            btnCheckAll.setText(getResources().getString(R.string.checkAll));
        }
    }

    /**
     * 获取STS信息(链接网络)
     */
    private void loadSTSData(String vid, int position) {
        OkGo.<String>post(ApiStores.COURSE_GETVIDEO_STS)
                .params(Constant.COURSE_VIDEOID, vid)//阿里库里的vid
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        getDownVideoSts(null, position);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int code = jsonObject.optInt(Constant.CODE);//获取接口返回状态
                            if (code == 200) {
                                Gson gson = new Gson();
                                GetVideoStsBean getVideoStsBean = gson.fromJson(body, GetVideoStsBean.class);
                                getDownVideoSts(getVideoStsBean, position);
                            } else {
                                getDownVideoSts(null, position);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 根据获取的石头是信息添加视频下载信息
     */
    private void getDownVideoSts(GetVideoStsBean data, int position) {
        if (data != null) {
            //以下参数使用STS方式下载的用户使用
            mVidSts = new AliyunVidSts();
            //TODO 替换成真实的要下载的视频VID
            mVidSts.setVid(offlineCourseActivityParcelableArrayList.get(position).getVid());
            mVidSts.setAcId(data.getData().getAccessKeyId());
            mVidSts.setAkSceret(data.getData().getKeySecret());
            mVidSts.setSecurityToken(data.getData().getSecurityToken());
            if (downloadManager != null) {
                downloadManager.prepareDownloadMedia(mVidSts);
            }
        }
        position++;//0 and 1  size=2
        if (position < offlineCourseActivityParcelableArrayList.size()) {
            String vid = offlineCourseActivityParcelableArrayList.get(position).getVid();
            loadSTSData(vid, position);
        }
    }

    /**
     * 添加一个视频到下载队列
     */
    private void addNewInfo(AliyunDownloadMediaInfo info) {
        if (hasAdded(info)) {
            return;
        }
        if (downloadManager != null && info != null) {
            downloadManager.addDownloadMedia(info);
            downloadManager.startDownloadMedia(info);
        }

        AlivcDownloadMediaInfo alivcDownloadMediaInfo = new AlivcDownloadMediaInfo();
        alivcDownloadMediaInfo.setEditState(false);
        alivcDownloadMediaInfo.setCheckedState(false);
        alivcDownloadMediaInfo.setAliyunDownloadMediaInfo(info);
        alivcDownloadingMediaInfos.add(alivcDownloadMediaInfo);

        notifyDataSetChanged();
        showDownloadContentView();

        saveInfoToDB(info);//保存到数据库
    }

    //保存下载的数据到数据库
    private void saveInfoToDB(AliyunDownloadMediaInfo info) {
        DataBaseCourseListBean dataBaseCourseListBean = new DataBaseCourseListBean();
        DataBaseSectioinListBean dataBaseSectioinListBean = new DataBaseSectioinListBean();
        DataBaseVideoListBean dataBaseVideoListBean = new DataBaseVideoListBean();

        String vid = info.getVid();
        if (offlineCourseActivityParcelableArrayList != null && offlineCourseActivityParcelableArrayList.size() > 0) {
            int size = offlineCourseActivityParcelableArrayList.size();
            for (int i = 0; i < size; i++) {
                PreparedDownloadInfoBean bean = offlineCourseActivityParcelableArrayList.get(i);
                String vid2 = bean.getVid();
                if (vid.equals(vid2)) {
                    List<DataBaseCourseListBean> courseListBeanList = LitePal.where("courseId = ?", bean.getCourseId()).find(DataBaseCourseListBean.class);
                    if (courseListBeanList != null && courseListBeanList.size() > 0) {
                        //TODO DB已有该课程
                        List<DataBaseSectioinListBean> sectioinListBeanList = LitePal.where("sectionId = ?", bean.getSectionId()).find(DataBaseSectioinListBean.class);
                        if (sectioinListBeanList != null && sectioinListBeanList.size() > 0) {
                            //DB已有该章节,直接添加新的视频到对应章节
                            List<DataBaseVideoListBean> videoListBeans = LitePal.where("vid = ?", bean.getVid()).find(DataBaseVideoListBean.class);
                            if (videoListBeans != null && videoListBeans.size() > 0) {
                                //已有该视频
                            } else {
                                //添加新视频
                                dataBaseVideoListBean.setStatus(0);
                                dataBaseVideoListBean.setVideoDuration(bean.getVideoDuration());
                                dataBaseVideoListBean.setVideoId(bean.getVideoId());
                                dataBaseVideoListBean.setVideoName(bean.getVideoName());
                                dataBaseVideoListBean.setVid(bean.getVid());
                                dataBaseVideoListBean.setSectionId(bean.getSectionId());
                                dataBaseVideoListBean.setCourseId(bean.getCourseId());
                                dataBaseVideoListBean.save();
                            }
                        } else {
                            //在改课程目录下添加新的章节
                            dataBaseSectioinListBean.setCourseId(bean.getCourseId());
                            dataBaseSectioinListBean.setSectionId(bean.getSectionId());
                            dataBaseSectioinListBean.setSectionName(bean.getSectionName());
                            dataBaseSectioinListBean.save();
                            List<DataBaseVideoListBean> videoListBeans = LitePal.where("vid = ?", bean.getVid()).find(DataBaseVideoListBean.class);
                            if (videoListBeans != null && videoListBeans.size() > 0) {
                                //已有该视频
                            } else {
                                //添加新视频
                                dataBaseVideoListBean.setVideoDuration(bean.getVideoDuration());
                                dataBaseVideoListBean.setVideoId(bean.getVideoId());
                                dataBaseVideoListBean.setVideoName(bean.getVideoName());
                                dataBaseVideoListBean.setVid(bean.getVid());
                                dataBaseVideoListBean.setSectionId(bean.getSectionId());
                                dataBaseVideoListBean.setCourseId(bean.getCourseId());
                                dataBaseVideoListBean.save();
                            }
                        }
                    } else {
                        //TODO 数据库不存在该数据
                        dataBaseCourseListBean.setCourseId(bean.getCourseId());
                        dataBaseCourseListBean.setCourseTitle(bean.getCourseName());
                        dataBaseCourseListBean.setTeacherName(bean.getTeacherName());
                        //添加课程
                        dataBaseCourseListBean.save();

                        dataBaseSectioinListBean.setCourseId(bean.getCourseId());
                        dataBaseSectioinListBean.setSectionId(bean.getSectionId());
                        dataBaseSectioinListBean.setSectionName(bean.getSectionName());
                        //添加章节
                        dataBaseSectioinListBean.save();

                        dataBaseVideoListBean.setStatus(0);
                        dataBaseVideoListBean.setVid(bean.getVid());
                        dataBaseVideoListBean.setVideoDuration(bean.getVideoDuration());
                        dataBaseVideoListBean.setVideoId(bean.getVideoId());
                        dataBaseVideoListBean.setVideoName(bean.getVideoName());
                        dataBaseVideoListBean.setSectionId(bean.getSectionId());
                        dataBaseVideoListBean.setCourseId(bean.getCourseId());
                        //添加视频
                        dataBaseVideoListBean.save();
                    }
                    break;
                }
            }
        }
    }

    /**
     * 修改数据库信息(添加保存地址)
     */
    private void updateInfoToDB(AliyunDownloadMediaInfo info) {
        DataBaseVideoListBean bean = new DataBaseVideoListBean();
        bean.setStatus(1);
        bean.setSaveDir(info.getSavePath());
        bean.updateAll("vid = ?", info.getVid());
    }

    /**
     * 判断是否已经存在
     */
    private boolean hasAdded(AliyunDownloadMediaInfo info) {
        if (alivcDownloadingMediaInfos != null) {
            for (AlivcDownloadMediaInfo downloadMediaInfo : alivcDownloadingMediaInfos) {
                if (info.getFormat().equals(downloadMediaInfo.getAliyunDownloadMediaInfo().getFormat()) &&
                        info.getQuality().equals(downloadMediaInfo.getAliyunDownloadMediaInfo().getQuality()) &&
                        info.getVid().equals(downloadMediaInfo.getAliyunDownloadMediaInfo().getVid()) &&
                        info.isEncripted() == downloadMediaInfo.getAliyunDownloadMediaInfo().isEncripted()) {
                    ToastUtil.showBottomShortText(getActivity(), context.getResources().getString(R.string.alivc_video_downloading_tips));
                    return true;
                }
            }
        }
        return false;
    }

    //视频下载的回调  注意在不需要的时候，调用remove，移除监听。
    public class MyDownloadInfoListener implements AliyunDownloadInfoListener {
        @Override
        public void onPrepared(List<AliyunDownloadMediaInfo> infos) {//TODO 当调用downloadManager.prepareDownloadMedia(vidSts);后该方法起作用
            AliyunDownloadMediaInfo info = infos.get(0);
            for (int i = 0; i < infos.size(); i++) {
                if (infos.get(i).getQuality().equals(IAliyunVodPlayer.QualityValue.QUALITY_LOW)) {
                    info = infos.get(i);
                    break;
                }
            }
            outputLog("onPrepared----" + gson.toJson(info));
            File downloadFile = new File(info.getSavePath());
            if (downloadFile.exists()) {
                /*ToastUtil.showBottomShortText(context, getString(R.string.demo_downloaded, infos.get(0).getTitle()));return;*/
            }
            //添加至下载队列
            addNewInfo(info);
        }

        @Override
        public void onStart(AliyunDownloadMediaInfo info) {//TODO 开始下载
            outputLog("onStart--------" + info.getTitle());
            if (!downloadDataProvider.hasAdded(info)) {
                downloadDataProvider.addDownloadMediaInfo(info);
            }
            notifyDataSetChanged();
            showDownloadContentView();
        }

        @Override
        public void onProgress(AliyunDownloadMediaInfo info, int percent) {//TODO 下载进度更新回调
            outputLog("onProgress---percent:" + percent);
            updateInfo(info);
        }

        @Override
        public void onStop(AliyunDownloadMediaInfo info) {//TODO 下载停止时回调
            outputLog("onStop---" + info.getStatus());
            updateInfo(info);
        }

        @Override
        public void onCompletion(AliyunDownloadMediaInfo info) {//TODO 下载完成时回调
            outputLog("onCompletion---Title:" + info.getTitle() + "   status:" + info.getStatus() + "  vid:" + info.getVid());
            //修改数据库保存信息
            updateInfoToDB(info);
            if (info.getStatus() == AliyunDownloadMediaInfo.Status.Complete) {
                for (AlivcDownloadMediaInfo mediaInfo : alivcDownloadingMediaInfos) {
                    if (mediaInfo.getAliyunDownloadMediaInfo().getVid().equals(info.getVid())) {
                        alivcDownloadingMediaInfos.remove(mediaInfo);
                        break;
                    }
                }
                downloadDataProvider.addDownloadMediaInfo(info);//添加到队列?
                notifyDataSetChanged();
                //
                showDownloadContentView();
            }
        }

        @Override
        public void onError(AliyunDownloadMediaInfo info, int code, String msg, String requestId) {//TODO 下载错误时回调
            outputLog("onError---code:" + code + "      msg:" + msg + "     requestId:" + requestId);
            notifyDataSetChanged();
        }

        @Override
        public void onWait(AliyunDownloadMediaInfo outMediaInfo) {//TODO 下载等待时回调
            outputLog("onWait--------" + outMediaInfo.getStatus());
            notifyDataSetChanged();
        }

        @Override
        public void onM3u8IndexUpdate(AliyunDownloadMediaInfo outMediaInfo, int index) {
            outputLog("onM3u8IndexUpdate---" + index);
            notifyDataSetChanged();
        }
    }

    //设置vids 过期的回调，目的是当vids过期之后能够重新请求vids的信息，防止下载失败，
    private class MyRefreshStsCallback implements AliyunRefreshStsCallback {
        @Override
        public AliyunVidSts refreshSts(String vid, String quality, String format, String title, boolean encript) {
            //NOTE: 注意：这个不能启动线程去请求。因为这个方法已经在线程中调用了。
            outputLog("refreshSts---vid:" + vid + "       quality:" + quality + "     defaultTitle:" + title);
            GetVideoStsBean.DataBean dataBean = null;
            try {
                okhttp3.Response response = OkGo.get(ApiStores.COURSE_GETVIDEO_STS).tag(this).params(Constant.COURSE_VIDEOID, vid).execute();
                String string = response.body().string();
                JSONObject jsonObject = new JSONObject(string);
                int i = jsonObject.optInt(Constant.CODE);
                if (i != 200) {
                    return null;
                }
                GetVideoStsBean stsBean = new Gson().fromJson(string, GetVideoStsBean.class);
                dataBean = stsBean.getData();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (dataBean != null) {
                mVidSts = new AliyunVidSts();
                mVidSts.setVid(vid);
                mVidSts.setAcId(dataBean.getAccessKeyId());
                mVidSts.setAkSceret(dataBean.getKeySecret());
                mVidSts.setSecurityToken(dataBean.getSecurityToken());
                mVidSts.setQuality(quality);
            } else {
                return null;
            }
            return mVidSts;
        }
    }

    //更新item的值
    public void updateInfo(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        AlivcDownloadMediaInfo tmpInfo = null;
        if (alivcDownloadingMediaInfos != null) {
            for (AlivcDownloadMediaInfo info : alivcDownloadingMediaInfos) {
                if (info.getAliyunDownloadMediaInfo().getVid().equals(aliyunDownloadMediaInfo.getVid()) &&
                        info.getAliyunDownloadMediaInfo().getQuality().equals(aliyunDownloadMediaInfo.getQuality()) &&
                        info.getAliyunDownloadMediaInfo().getFormat().equals(aliyunDownloadMediaInfo.getFormat())) {
                    tmpInfo = info;
                    break;
                }
            }
        }
        if (tmpInfo != null) {
            tmpInfo.getAliyunDownloadMediaInfo().setProgress(aliyunDownloadMediaInfo.getProgress());
            tmpInfo.getAliyunDownloadMediaInfo().setStatus(aliyunDownloadMediaInfo.getStatus());
        }
        notifyDataSetChanged();
    }

    //log日志
    public void outputLog(String log) {
        LogUtils.e("SafeDownload--" + log);
    }

    /**
     * 根据是否有数据判断是否显示downloadEmptyView
     */
    public void showDownloadContentView() {
        if (alivcDownloadingMediaInfos != null && alivcDownloadingMediaInfos.size() > 0) {
            if (loadinglayout != null) {
                loadinglayout.showContent();
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
    }

    /**
     * 刷新数据
     */
    public void notifyDataSetChanged() {
        if (downloadingAdapter != null) {
            downloadingAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_checkAll://全选
                isCheckAll = !isCheckAll;
                if (alivcDownloadingMediaInfos != null) {
                    for (AlivcDownloadMediaInfo alivcDownloadMediaInfo : alivcDownloadingMediaInfos) {
                        alivcDownloadMediaInfo.setCheckedState(isCheckAll);
                    }
                }
                notifyDataSetChanged();
                if (isCheckAll) {//全选
                    btnDelete.setBackgroundColor(ContextCompat.getColor(context, R.color.color_0267FF));
                    btnCheckAll.setText(getResources().getString(R.string.cancelCheckAll));
                } else {//取消全选
                    btnDelete.setBackgroundColor(ContextCompat.getColor(context, R.color.color_DCDCDC));
                    btnCheckAll.setText(getResources().getString(R.string.checkAll));
                }
                break;
            case R.id.btn_delete://删除
                ArrayList<AlivcDownloadMediaInfo> alivcDownloadMediaInfos = new ArrayList<>();
                if (alivcDownloadingMediaInfos != null) {
                    for (AlivcDownloadMediaInfo alivcDownloadMediaInfo : alivcDownloadingMediaInfos) {
                        if (alivcDownloadMediaInfo.isCheckedState()) {
                            alivcDownloadMediaInfos.add(alivcDownloadMediaInfo);
                        }
                    }
                }
                int size = alivcDownloadMediaInfos.size();
                new AlertDialog(getActivity()).builder()
                        .setMsg(getResources().getString(R.string.download_deleteComfirm, String.valueOf(size)))
                        .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (alivcDownloadMediaInfos.size() > 0) {
                                    for (Iterator<AlivcDownloadMediaInfo> it = alivcDownloadingMediaInfos.iterator(); it.hasNext(); ) {
                                        AlivcDownloadMediaInfo val = it.next();
                                        if (val.isCheckedState()) {
                                            it.remove();
                                        }
                                    }
                                    for (AlivcDownloadMediaInfo mediaInfo : alivcDownloadMediaInfos) {
                                        String vid = mediaInfo.getAliyunDownloadMediaInfo().getVid();
                                        LitePal.deleteAll(DataBaseVideoListBean.class, "vid = ?", vid);
                                    }
                                    downloadDataProvider.deleteAllDownloadInfo(alivcDownloadMediaInfos);
                                    notifyDataSetChanged();
                                    showDownloadContentView();
                                } else {
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
                        .setCanceledOnTouchOutside(false)
                        .show();
                break;
            default:
                break;
        }
    }

    /**
     * 断网/连网监听
     */
    private class MyNetConnectedListener implements NetWatchdog.NetConnectedListener {
        //网络已连接
        @Override
        public void onReNetConnected(boolean isReconnect) {//重新连接
            LogUtils.e("网络状态---------------isReconnect: " + isReconnect);
            currentError = ErrorInfo.Normal;
            if (isReconnect) {
                if (alivcDownloadingMediaInfos != null && alivcDownloadingMediaInfos.size() > 0) {
                    int unCompleteDownload = 0;
                    for (AliyunDownloadMediaInfo mediaInfo : downloadDataProvider.getAllDownloadMediaInfo()) {
                        if (mediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Stop) {
                            unCompleteDownload++;
                        }
                    }
                    if (unCompleteDownload > 0) {
                        ToastUtil.showBottomShortText(getActivity(), getResources().getString(R.string.alivc_video_download_startdownwithhand));
                    }
                }
            }
        }

        //网络连接中断
        @Override
        public void onNetUnConnected() {
            currentError = ErrorInfo.UnConnectInternet;
            LogUtils.e("网络状态---------------onNetUnConnected");
            if (alivcDownloadingMediaInfos != null && alivcDownloadingMediaInfos.size() > 0) {
                downloadManager.stopDownloadMedias(downloadDataProvider.getAllDownloadMediaInfo());

                //遍历下载中的视频,修改状态为停止
                for (AlivcDownloadMediaInfo mediaInfo : alivcDownloadingMediaInfos) {
                    AliyunDownloadMediaInfo info = mediaInfo.getAliyunDownloadMediaInfo();
                    info.setStatus(AliyunDownloadMediaInfo.Status.Stop);
                }
                notifyDataSetChanged();
            }
        }
    }

    private class MyNetChangeListener implements NetWatchdog.NetChangeListener {
        @Override
        public void onWifiTo4G() {//数据网络
            LogUtils.e("网络状态--------------------onWifiTo4G");
        }

        @Override
        public void on4GToWifi() {//WIFI状态
            LogUtils.e("网络状态--------------------on4GToWifi");
        }

        @Override
        public void onNetDisconnected() {//网络断开。由于安卓这块网络切换的时候，有时候也会先报断开。所以这个回调是不准确的。
        }
    }

}
