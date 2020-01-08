package com.ucfo.youcaiwx.module.user.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;
import com.tencent.bugly.crashreport.CrashReport;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.download.PreparedDownloadInfoBean;
import com.ucfo.youcaiwx.module.course.fragment.DownloadCompletedFragment;
import com.ucfo.youcaiwx.module.course.fragment.DownloadingFragment;
import com.ucfo.youcaiwx.module.course.player.adapter.CommonTabAdapter;

import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-7-2 下午 1:46
 * FileName: OfflineCourseActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 课程离线缓存
 */
public class OfflineCourseActivity extends BaseActivity implements View.OnClickListener, DownloadingFragment.DownloadingListener {
    private static String preparedVid;
    private XTabLayout xTablayout;
    private Toolbar titlebarToolbar;
    private ViewPager viewpager;
    private TextView titlebarRighttitle;

    private int page;
    private OfflineCourseActivity context;
    //private DownloadCompletesFragment downloadCompletesFragment;
    private boolean editStatus = false;
    private ArrayList<PreparedDownloadInfoBean> parcelableArrayList;
    private DownloadCompletedFragment downloadCompletedFragment;
    private DownloadingFragment downloadingFragment;

    @Override
    protected void initView(Bundle savedInstanceState) {
        xTablayout = (XTabLayout) findViewById(R.id.xTablayout);
        titlebarRighttitle = (TextView) findViewById(R.id.titlebar_righttitle);
        titlebarRighttitle.setOnClickListener(this);
        titlebarToolbar = (Toolbar) findViewById(R.id.titlebar_toolbar);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_offline_course;
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
            page = bundle.getInt(Constant.PAGE, 0);
            parcelableArrayList = bundle.getParcelableArrayList(Constant.DOWNLOADINFO_LIST);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //TODO 6.0以上
            checkPermission();
        } else {
            //TODO 6.0以下
            initTablayout();
        }
        // 上报后的Crash会显示该标签
        CrashReport.setUserSceneTag(this, Constant.BUGLY_TAG_CACHE);
    }

    private void checkPermission() {
        int storagePermissionRet = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int storagePermissionRet2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (storagePermissionRet == PackageManager.PERMISSION_GRANTED && storagePermissionRet2 == PackageManager.PERMISSION_GRANTED) {
            //文件读写权限已打开
            initTablayout();
        } else {
            //未打开就申请
            SoulPermission.getInstance().checkAndRequestPermissions(Permissions.build(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    new CheckRequestPermissionsListener() {
                        @Override
                        public void onAllPermissionOk(Permission[] allPermissions) {
                            initTablayout();
                        }

                        @Override
                        public void onPermissionDenied(Permission[] refusedPermissions) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(OfflineCourseActivity.this, R.style.WhiteDialogStyle);
                            builder.setTitle(OfflineCourseActivity.this.getResources().getString(R.string.explication));
                            builder.setMessage(OfflineCourseActivity.this.getResources().getString(R.string.permission_sdcard));
                            builder.setCancelable(false);
                            builder.setPositiveButton(OfflineCourseActivity.this.getResources().getString(R.string.donner),
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
        }
    }

    private void initTablayout() {
        ArrayList<String> titlesList = new ArrayList<String>();
        ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>();
        downloadCompletedFragment = new DownloadCompletedFragment();
        downloadingFragment = new DownloadingFragment();
        fragmentArrayList.add(downloadCompletedFragment);
        fragmentArrayList.add(downloadingFragment);
        titlesList.add(getResources().getString(R.string.downloadCompleted));
        titlesList.add(getResources().getString(R.string.downloading));
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        CommonTabAdapter commonTabAdapter = new CommonTabAdapter(supportFragmentManager, fragmentArrayList, titlesList);
        viewpager.setAdapter(commonTabAdapter);
        viewpager.setOffscreenPageLimit(fragmentArrayList.size());
        viewpager.setCurrentItem(page);
        xTablayout.setupWithViewPager(viewpager);
    }

    //供fragment获取下载信息
    public ArrayList<PreparedDownloadInfoBean> getParcelableArrayList() {
        if (parcelableArrayList == null) {
            return new ArrayList<>();
        }
        return parcelableArrayList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebar_righttitle:
                if (downloadingFragment != null && downloadCompletedFragment != null) {
                    if (downloadingFragment.isAdded() && downloadCompletedFragment.isAdded()) {
                        if (downloadingFragment.getUserVisibleHint()) {
                            editStatus = !editStatus;
                            downloadingFragment.editVideoList(editStatus);
                        } else if (downloadCompletedFragment.getUserVisibleHint()) {
                            editStatus = !editStatus;
                            downloadCompletedFragment.editVideoList(editStatus);
                        }
                    }
                }
                break;
            default:
                //TODO nothing
                break;
        }
    }
}
