package com.ucfo.youcaiwx.module.course.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aliyun.vodplayer.downloader.AliyunDownloadManager;
import com.aliyun.vodplayer.downloader.AliyunDownloadMediaInfo;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.UcfoApplication;
import com.ucfo.youcaiwx.adapter.download.DownloadingAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.download.AlivcDownloadMediaInfo;
import com.ucfo.youcaiwx.utils.LogUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.module.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.module.course.player.download.DownloadSaveInfoUtil;
import com.ucfo.youcaiwx.module.course.player.download.StorageQueryUtil;
import com.ucfo.youcaiwx.module.user.activity.OfflineCourseActivity;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.dialog.AlertDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author: AND
 * Time: 2019-7-2.  下午 1:50
 * FileName: DownloadCompletesFragment
 * Description:TODO 下载完成
 */
public class DownloadCompletesFragment extends BaseFragment {
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.sdcard_totalSpace)
    TextView sdcardTotalSpace;
    @BindView(R.id.sdcard_residueSpace)
    TextView sdcardResidueSpace;
    @BindView(R.id.linear_sdcardSpace)
    LinearLayout linearSdcardSpace;
    Unbinder unbinder;
    @BindView(R.id.btn_checkAll)
    Button btnCheckAll;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.linear_edittor)
    LinearLayout linearEdittor;
    private ArrayList<AlivcDownloadMediaInfo> alivcDownloadMediaInfos;
    private ArrayList<AliyunDownloadMediaInfo> aliyunDownloadMediaInfoArrayList;

    private DownloadingAdapter downloadingAdapter;
    private OfflineCourseActivity offlineCourseActivity;
    private AliyunDownloadManager downloadManager;
    private DownloadSaveInfoUtil downloadSaveInfoUtil;
    private boolean editStatus = false, isCheckAll = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        if (rootView != null) {
            unbinder = ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_downloadcompleted;
    }

    @Override
    protected void initView(View view) {
        FragmentActivity activity = getActivity();
        if (activity instanceof OfflineCourseActivity) {
            offlineCourseActivity = (OfflineCourseActivity) activity;
        }
        initSdcardSpace();
    }

    @Override
    protected void initData() {
        alivcDownloadMediaInfos = new ArrayList<>();
        aliyunDownloadMediaInfoArrayList = new ArrayList<>();

        initDownloadConfig();//init the download config before scan sdcard
    }

    //TODO initialize the config
    private void initDownloadConfig() {
        downloadManager = UcfoApplication.downloadManager;
        downloadSaveInfoUtil = new DownloadSaveInfoUtil(downloadManager.getSaveDir());
        AliyunDownloadManager.enableNativeLog();
    }

    @Override
    protected void onVisibleToUser() {
        super.onVisibleToUser();
        initDownloadingAdapter();
    }

    //TODO 手机可用空间大小
    private void initSdcardSpace() {
        String availSize = StorageQueryUtil.getAvailSize(getActivity());
        String totalSize = StorageQueryUtil.getTotalSize(getActivity());
        sdcardTotalSpace.setText(totalSize);
        sdcardResidueSpace.setText(availSize);
    }

    //TODO 编辑视频
    public void editVideoList(boolean Status) {
        editStatus = Status;
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

    private void initDownloadingAdapter() {
        alivcDownloadMediaInfos.clear();
        aliyunDownloadMediaInfoArrayList.clear();

        aliyunDownloadMediaInfoArrayList.addAll(downloadSaveInfoUtil.getAlivcDownloadeds());

        for (AliyunDownloadMediaInfo info : aliyunDownloadMediaInfoArrayList) {
            File downloadFile = new File(info.getSavePath());
            if (downloadFile.exists()) {
                if (info.getStatus() == AliyunDownloadMediaInfo.Status.Complete) {
                    AlivcDownloadMediaInfo alivcDownloadMediaInfo = new AlivcDownloadMediaInfo();
                    alivcDownloadMediaInfo.setCheckedState(false);
                    alivcDownloadMediaInfo.setEditState(false);
                    alivcDownloadMediaInfo.setAliyunDownloadMediaInfo(info);
                    alivcDownloadMediaInfos.add(alivcDownloadMediaInfo);
                }
            }
        }
        if (alivcDownloadMediaInfos.size() > 0) {
            if (loadinglayout != null) {
                loadinglayout.showContent();
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }

        if (downloadingAdapter == null) {
            downloadingAdapter = new DownloadingAdapter(alivcDownloadMediaInfos, getActivity());
        } else {
            downloadingAdapter.notifyDataSetChanged();
        }
        listView.setAdapter(downloadingAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AliyunDownloadMediaInfo info = alivcDownloadMediaInfos.get(position).getAliyunDownloadMediaInfo();
                if (info.getStatus() == AliyunDownloadMediaInfo.Status.Complete) {//下载完成
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.COURSE_SOURCE, Constant.LOCAL_CACHE);
                    bundle.putString(Constant.LOCAL_PLAYURL, info.getSavePath());
                    bundle.putString(Constant.TITLE, info.getTitle());
                    startActivity(VideoPlayPageActivity.class, bundle);
                }
            }
        });
    }

    public void outputLog(String text) {
        LogUtils.e("HavedDownload-----" + text);
    }

    @SuppressLint("StringFormatMatches")
    @OnClick({R.id.btn_checkAll, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_checkAll://全选
                isCheckAll = !isCheckAll;
                for (AlivcDownloadMediaInfo info : alivcDownloadMediaInfos) {
                    info.setEditState(true);
                    info.setCheckedState(isCheckAll);
                }
                downloadingAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_delete://删除选中项
                ArrayList<AlivcDownloadMediaInfo> alivcDownloadCheckList = new ArrayList<>();
                for (AlivcDownloadMediaInfo alivcDownloadMediaInfo : alivcDownloadMediaInfos) {
                    if (alivcDownloadMediaInfo.isCheckedState()) {
                        alivcDownloadCheckList.add(alivcDownloadMediaInfo);
                    }
                }
                new AlertDialog(offlineCourseActivity).builder()
                        .setMsg(getResources().getString(R.string.download_deleteComfirm, alivcDownloadCheckList.size()))
                        .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (alivcDownloadCheckList != null && alivcDownloadCheckList.size() > 0) {
                                    for (Iterator<AlivcDownloadMediaInfo> it = alivcDownloadMediaInfos.iterator(); it.hasNext(); ) {
                                        AlivcDownloadMediaInfo val = it.next();
                                        if (val.isCheckedState()) {
                                            it.remove();
                                        }
                                    }

                                    for (int i = 0; i < alivcDownloadCheckList.size(); i++) {
                                        AlivcDownloadMediaInfo info = alivcDownloadCheckList.get(i);
                                        downloadSaveInfoUtil.deleteInfo(info.getAliyunDownloadMediaInfo());
                                        downloadManager.removeDownloadMedia(info.getAliyunDownloadMediaInfo());
                                    }

                                    notifyDataSetChanged();
                                    showDownloadContentView();
                                } else {
                                    ToastUtil.showBottomShortText(context, "没有删除的视频选项...");
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
        }
    }

    public void notifyDataSetChanged() {
        if (downloadingAdapter != null) {
            downloadingAdapter.notifyDataSetChanged();
        }
        if (alivcDownloadMediaInfos.size() > 0) {
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
     * 根据是否有数据判断是否显示downloadEmptyView
     */
    public void showDownloadContentView() {
        if (alivcDownloadMediaInfos.size() > 0) {
            if (loadinglayout != null) {
                loadinglayout.showContent();
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
    }

}
