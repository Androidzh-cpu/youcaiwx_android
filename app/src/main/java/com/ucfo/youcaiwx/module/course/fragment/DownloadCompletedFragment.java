package com.ucfo.youcaiwx.module.course.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aliyun.vodplayer.downloader.AliyunDownloadManager;
import com.aliyun.vodplayer.downloader.AliyunDownloadMediaInfo;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.UcfoApplication;
import com.ucfo.youcaiwx.adapter.download.DownloadCompletedCourseListAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.download.DataBaseCourseListBean;
import com.ucfo.youcaiwx.entity.download.DataBaseSectioinListBean;
import com.ucfo.youcaiwx.entity.download.DataBaseVideoListBean;
import com.ucfo.youcaiwx.module.course.player.DownloadComletedDirActivity;
import com.ucfo.youcaiwx.module.course.player.download.DownloadSaveInfoUtil;
import com.ucfo.youcaiwx.module.course.player.download.StorageQueryUtil;
import com.ucfo.youcaiwx.module.user.activity.OfflineCourseActivity;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.dialog.AlertDialog;

import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-10.  上午 9:08
 * FileName: DownloadCompletedFragment
 * Description:TODO 正式版下载完成页
 */
public class DownloadCompletedFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView listView;
    private LoadingLayout loadinglayout;
    private TextView sdcardTotalSpace;
    private TextView sdcardResidueSpace;
    private LinearLayout linearSdcardSpace;
    private Button btnCheckAll;
    private Button btnDelete;
    private LinearLayout linearEdittor;

    private OfflineCourseActivity offlineCourseActivity;
    private DownloadCompletedCourseListAdapter courseListAdapter;
    private List<DataBaseCourseListBean> list;
    private List<DataBaseCourseListBean> finalList;
    private List<DataBaseVideoListBean> dataBaseVideoListBeanList;
    private AliyunDownloadManager downloadManager;
    private DownloadSaveInfoUtil downloadSaveInfoUtil;
    private String downloadSaveDir;

    @Override
    protected int setContentView() {
        return R.layout.fragment_downloadcompleted2;
    }

    @Override
    protected void initView(View itemView) {
        listView = (RecyclerView) itemView.findViewById(R.id.listView);
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        finalList = new ArrayList<>();
        dataBaseVideoListBeanList = new ArrayList<>();

        if (UcfoApplication.downloadManager != null) {
            downloadManager = UcfoApplication.downloadManager;
            downloadSaveDir = downloadManager.getSaveDir();
            if (!TextUtils.isEmpty(downloadSaveDir)) {
                downloadSaveInfoUtil = new DownloadSaveInfoUtil(downloadSaveDir);
            }
        }
    }

    @Override
    protected void onVisibleToUser() {
        super.onVisibleToUser();
        updateDataBase();
    }

    /**
     * 本地实时更新数据库
     */
    private void updateDataBase() {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (finalList == null) {
            finalList = new ArrayList<>();
        }
        list.clear();
        finalList.clear();
        list.addAll(LitePal.findAll(DataBaseCourseListBean.class));

        if (list != null && list.size() > 0) {
            for (DataBaseCourseListBean courseListBean : list) {
                //遍历该课程下的视频
                String courseId = courseListBean.getCourseId();

                List<DataBaseVideoListBean> videoListBeans = LitePal.where("courseId = ? and status = ?", courseId, "1").find(DataBaseVideoListBean.class);
                List<DataBaseVideoListBean> videoNoCompltedBeans = LitePal.where("courseId = ? and status = ?", courseId, "0").find(DataBaseVideoListBean.class);

                if (videoListBeans != null && videoListBeans.size() > 0) {
                    //TODO 数据仓库查询到已下载的视频
                    dataBaseVideoListBeanList.clear();
                    for (int j = 0; j < videoListBeans.size(); j++) {
                        String saveDir = videoListBeans.get(j).getSaveDir();
                        File file = new File(saveDir);
                        if (file.exists()) {
                            dataBaseVideoListBeanList.add(videoListBeans.get(j));
                        } else {
                            LitePal.deleteAll(DataBaseVideoListBean.class, "courseId = ? and vid = ?", courseId, videoListBeans.get(j).getVid());
                        }
                    }
                    courseListBean.setCourseDownloadNum(dataBaseVideoListBeanList.size());

                    finalList.add(courseListBean);
                } else {
                    //TODO 未查询到已下载的视频
                    if (videoNoCompltedBeans != null && videoNoCompltedBeans.size() > 0) {
                        //todo 还存在未下载的数据,那就把
                    } else {
                        LitePal.deleteAll(DataBaseCourseListBean.class, "courseId = ?", courseId);//删除课
                        LitePal.deleteAll(DataBaseSectioinListBean.class, "courseId = ?", courseId);//删除所有章
                        LitePal.deleteAll(DataBaseVideoListBean.class, "courseId = ?", courseId);//删除所有视频
                    }
                }
            }

            /**
             * 设置适配器
             */
            if (finalList != null && finalList.size() > 0) {
                if (courseListAdapter == null) {
                    courseListAdapter = new DownloadCompletedCourseListAdapter(finalList, getContext());
                    listView.setAdapter(courseListAdapter);
                } else {
                    courseListAdapter.notifyChange(finalList);
                }
                courseListAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (editStatus) {
                            //TODO Editting
                            list.get(position).setChecked(!list.get(position).isChecked());
                            courseListAdapter.notifyDataSetChanged();

                            checkAndUnChecked(list);
                        } else {
                            //TODO nothing to do
                            Bundle bundle = new Bundle();
                            bundle.putString(Constant.COURSE_ID, list.get(position).getCourseId());
                            startActivity(DownloadComletedDirActivity.class, bundle);
                        }
                    }
                });

                if (loadinglayout != null) {
                    loadinglayout.showContent();
                }
            } else {
                if (loadinglayout != null) {
                    loadinglayout.showEmpty();
                }
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
    }

    private void checkAndUnChecked(List<DataBaseCourseListBean> beans) {
        if (beans != null && beans.size() > 0) {
            boolean flag = false;
            for (int i = 0; i < beans.size(); i++) {
                boolean checked = beans.get(i).isChecked();
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
    }

    //TODO 手机可用空间大小
    private void initSdcardSpace() {
        String availSize = StorageQueryUtil.getAvailSize(getContext());
        String totalSize = StorageQueryUtil.getTotalSize(getContext());
        sdcardTotalSpace.setText(totalSize);
        sdcardResidueSpace.setText(availSize);
    }

    private boolean editStatus = false, isCheckAll = false;

    //TODO 编辑视频
    public void editVideoList(boolean status) {
        editStatus = status;
        if (editStatus) {
            //编辑中
            if (courseListAdapter != null) {
                courseListAdapter.notifychange(true);
            }
            linearEdittor.setVisibility(View.VISIBLE);
            linearSdcardSpace.setVisibility(View.GONE);
        } else {
            //取消编辑
            if (courseListAdapter != null) {
                courseListAdapter.notifychange(false);
            }
            linearEdittor.setVisibility(View.GONE);
            linearSdcardSpace.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 空数据处理
     */
    private void showContentnView() {
        List<DataBaseCourseListBean> dataBaseCourseListBeans = LitePal.findAll(DataBaseCourseListBean.class);
        if (dataBaseCourseListBeans != null && dataBaseCourseListBeans.size() > 0) {
            if (loadinglayout != null) {
                loadinglayout.showContent();
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_checkAll://全选
                if (courseListAdapter != null) {
                    isCheckAll = !isCheckAll;
                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setChecked(isCheckAll);
                        }
                        courseListAdapter.notifyDataSetChanged();
                    }
                }
                if (isCheckAll) {//全选
                    btnDelete.setBackgroundColor(ContextCompat.getColor(context, R.color.color_0267FF));
                    btnCheckAll.setText(getResources().getString(R.string.cancelCheckAll));
                } else {//取消全选
                    btnDelete.setBackgroundColor(ContextCompat.getColor(context, R.color.color_DCDCDC));
                    btnCheckAll.setText(getResources().getString(R.string.checkAll));
                }
                break;
            case R.id.btn_delete://删除
                deleteMethord();
                break;
            default:
                break;
        }
    }

    @SuppressLint("StringFormatMatches")
    private void deleteMethord() {
        //删除方法
        ArrayList<String> arrayList = new ArrayList<>();
        if (courseListAdapter != null) {
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    boolean checked = list.get(i).isChecked();
                    if (checked) {
                        arrayList.add(list.get(i).getCourseId());
                    }
                }
            }
        }
        new AlertDialog(getContext()).builder()
                .setMsg(getResources().getString(R.string.download_deleteComfirm, arrayList.size()))
                .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (arrayList != null && arrayList.size() > 0) {
                            for (int i = 0; i < arrayList.size(); i++) {
                                //TODO 遍历选中的课
                                String courseId = arrayList.get(i);
                                //查询所有的该课下并已完成的数据
                                List<DataBaseVideoListBean> courseList = LitePal.where("courseId = ? and status = ?", courseId, "1").find(DataBaseVideoListBean.class);
                                for (int j = 0; j < courseList.size(); j++) {
                                    //TODO 遍历选中视频
                                    String vid = courseList.get(j).getVid();
                                    if (downloadSaveInfoUtil != null) {
                                        List<AliyunDownloadMediaInfo> alivcDownloadeds = downloadSaveInfoUtil.getAlivcDownloadeds();
                                        if (alivcDownloadeds != null) {
                                            for (AliyunDownloadMediaInfo info : alivcDownloadeds) {
                                                String vid2 = info.getVid();
                                                if (TextUtils.equals(vid, vid2)) {
                                                    downloadSaveInfoUtil.deleteInfo(info);
                                                    downloadSaveInfoUtil.deleteFile(info);
                                                }
                                            }
                                        }
                                    }
                                }
                                LitePal.deleteAll(DataBaseVideoListBean.class, "courseId = ? and status = ?", courseId, "1");//删除已下载视频
                            }
                            /**
                             * 刷新数据
                             */
                            updateDataBase();
                            showContentnView();
                        } else {
                            ToastUtil.showBottomShortText(context, getResources().getString(R.string.no_delete));
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .show();
    }

}
