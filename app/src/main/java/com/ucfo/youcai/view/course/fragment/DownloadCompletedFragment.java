package com.ucfo.youcai.view.course.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aliyun.vodplayer.downloader.AliyunDownloadManager;
import com.aliyun.vodplayer.downloader.AliyunDownloadMediaInfo;
import com.ucfo.youcai.R;
import com.ucfo.youcai.UcfoApplication;
import com.ucfo.youcai.adapter.download.DownloadCompletedCourseListAdapter;
import com.ucfo.youcai.base.BaseFragment;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.download.DataBaseCourseListBean;
import com.ucfo.youcai.entity.download.DataBaseSectioinListBean;
import com.ucfo.youcai.entity.download.DataBaseVideoListBean;
import com.ucfo.youcai.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcai.utils.toastutils.ToastUtil;
import com.ucfo.youcai.view.course.player.DownloadComletedDirActivity;
import com.ucfo.youcai.view.course.player.download.DownloadSaveInfoUtil;
import com.ucfo.youcai.view.course.player.download.StorageQueryUtil;
import com.ucfo.youcai.view.user.activity.OfflineCourseActivity;
import com.ucfo.youcai.widget.customview.LoadingLayout;
import com.ucfo.youcai.widget.dialog.AlertDialog;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author: AND
 * Time: 2019-7-10.  上午 9:08
 * Package: com.ucfo.youcai.view.user.fragment
 * FileName: DownloadCompletedFragment
 * Description:TODO 正式版下载完成页
 */
public class DownloadCompletedFragment extends BaseFragment {
    @BindView(R.id.listView)
    RecyclerView listView;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.sdcard_totalSpace)
    TextView sdcardTotalSpace;
    @BindView(R.id.sdcard_residueSpace)
    TextView sdcardResidueSpace;
    @BindView(R.id.linear_sdcardSpace)
    LinearLayout linearSdcardSpace;
    @BindView(R.id.btn_checkAll)
    Button btnCheckAll;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.linear_edittor)
    LinearLayout linearEdittor;
    Unbinder unbinder;
    private OfflineCourseActivity offlineCourseActivity;
    private DownloadCompletedCourseListAdapter courseListAdapter;
    private List<DataBaseCourseListBean> list;
    private AliyunDownloadManager downloadManager;
    private DownloadSaveInfoUtil downloadSaveInfoUtil;

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

    @Override
    protected int setContentView() {
        return R.layout.fragment_downloadcompleted2;
    }

    @Override
    protected void initView(View view) {
        FragmentActivity activity = getActivity();
        if (activity instanceof OfflineCourseActivity) {
            offlineCourseActivity = (OfflineCourseActivity) activity;
        }
        initSdcardSpace();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(false);
        listView.setLayoutManager(layoutManager);
        listView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        downloadManager = UcfoApplication.downloadManager;
        downloadSaveInfoUtil = new DownloadSaveInfoUtil(downloadManager.getSaveDir());
    }

    @Override
    protected void onVisibleToUser() {
        super.onVisibleToUser();
        updateDataBase();
    }

    //更新本地数据库
    private void updateDataBase() {
        list.clear();
        list.addAll(LitePal.findAll(DataBaseCourseListBean.class));

        if (list != null && list.size() > 0) {
            int size = list.size();
            for (int i = 0; i < size; i++) {//遍历该课程下的视频
                DataBaseCourseListBean courseListBean = list.get(i);
                String courseId = courseListBean.getCourseId();
                List<DataBaseVideoListBean> videoListBeans = LitePal.where("courseId = ? and status = ?", courseId, "1").find(DataBaseVideoListBean.class);
                List<DataBaseVideoListBean> videoNoCompltedBeans = LitePal.where("courseId = ? and status = ?", courseId, "0").find(DataBaseVideoListBean.class);
                if (videoListBeans.size() > 0) {//已下载的数据
                    list.get(i).setCourseDownloadNum(videoListBeans.size());
                } else {//已下载为0
                    if (videoNoCompltedBeans.size() > 0) {//还存在未下载的数据
                        list.remove(courseListBean);
                    } else {
                        LitePal.deleteAll(DataBaseCourseListBean.class, "courseId = ?", courseId);//删除课
                        LitePal.deleteAll(DataBaseSectioinListBean.class, "courseId = ?", courseId);//删除所有章
                        LitePal.deleteAll(DataBaseVideoListBean.class, "courseId = ?", courseId);//删除所有视频
                        list.remove(courseListBean);
                    }
                }
            }

            if (list.size() > 0) {
                if (courseListAdapter == null) {
                    courseListAdapter = new DownloadCompletedCourseListAdapter(list, getActivity());
                } else {
                    courseListAdapter.notifyDataSetChanged();
                }
                listView.setAdapter(courseListAdapter);
                courseListAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (editStatus) {//TODO Editting
                            list.get(position).setChecked(!list.get(position).isChecked());
                            courseListAdapter.notifyDataSetChanged();
                        } else {//TODO nothing to do
                            Bundle bundle = new Bundle();
                            bundle.putString(Constant.COURSE_ID, list.get(position).getCourseId());
                            startActivity(DownloadComletedDirActivity.class, bundle);
                        }
                    }
                });
                loadinglayout.showContent();
            } else {
                loadinglayout.showEmpty();
            }
        } else {
            loadinglayout.showEmpty();
        }
    }

    //TODO 手机可用空间大小
    private void initSdcardSpace() {
        String availSize = StorageQueryUtil.getAvailSize(getActivity());
        String totalSize = StorageQueryUtil.getTotalSize(getActivity());
        sdcardTotalSpace.setText(totalSize);
        sdcardResidueSpace.setText(availSize);
    }

    private boolean editStatus = false, isCheckAll = false;

    //TODO 编辑视频
    public void editVideoList(boolean Status) {
        editStatus = Status;
        if (editStatus) {//编辑中
            if (courseListAdapter != null) {
                courseListAdapter.notifychange(true);
            }
            linearEdittor.setVisibility(View.VISIBLE);
            linearSdcardSpace.setVisibility(View.GONE);
        } else {//取消编辑
            if (courseListAdapter != null) {
                courseListAdapter.notifychange(false);
            }
            linearEdittor.setVisibility(View.GONE);
            linearSdcardSpace.setVisibility(View.VISIBLE);
        }
    }

    private void showContentnView() {
        List<DataBaseCourseListBean> dataBaseCourseListBeans = LitePal.findAll(DataBaseCourseListBean.class);
        if (dataBaseCourseListBeans != null && dataBaseCourseListBeans.size() > 0) {
            loadinglayout.showContent();
        } else {
            loadinglayout.showEmpty();
        }
    }

    @OnClick({R.id.btn_checkAll, R.id.btn_delete})
    public void onViewClicked(View view) {
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
                break;
            case R.id.btn_delete://删除
                deleteMethord();
                break;
        }
    }

    @SuppressLint("StringFormatMatches")
    private void deleteMethord() {//删除方法
        ArrayList<String> arrayList = new ArrayList<>();
        if (courseListAdapter != null) {
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    boolean checked = list.get(i).isChecked();
                    if (checked) {
                        arrayList.add(list.get(i).getCourseId());
                    }
                }
            }
        }
        new AlertDialog(offlineCourseActivity).builder()
                .setMsg(getResources().getString(R.string.download_deleteComfirm, arrayList.size()))
                .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (arrayList.size() > 0) {
                            int size = arrayList.size();
                            for (int i = 0; i < size; i++) {//TODO 遍历选中的专业课
                                String course_id = arrayList.get(i);
                                //查询所有的该课下并已完成的数据
                                List<DataBaseVideoListBean> courseList = LitePal.where("courseId = ? and status = ?", course_id, "1").find(DataBaseVideoListBean.class);
                                for (int j = 0; j < courseList.size(); j++) {//TODO 遍历选中视频
                                    String vid = courseList.get(j).getVid();//获取vid
                                    List<AliyunDownloadMediaInfo> alivcDownloadeds = downloadSaveInfoUtil.getAlivcDownloadeds();
                                    for (AliyunDownloadMediaInfo info : alivcDownloadeds) {
                                        String vid2 = info.getVid();
                                        if (vid.equals(vid2)) {
                                            downloadManager.addDownloadMedia(info);
                                            downloadManager.removeDownloadMedia(info);
                                            downloadSaveInfoUtil.deleteInfo(info);
                                            /*File file = new File(info.getSavePath());
                                            if (file.exists()) {
                                                file.delete();
                                            }*/
                                            break;
                                        }
                                    }
                                }
                                LitePal.deleteAll(DataBaseVideoListBean.class, "courseId = ? and status = ?", course_id, "1");//删除已下载视频
                            }
                            updateDataBase();//更新数据
                            showContentnView();
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
    }
}
