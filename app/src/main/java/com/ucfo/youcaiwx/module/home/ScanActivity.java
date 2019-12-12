package com.ucfo.youcaiwx.module.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.callbcak.CheckRequestPermissionListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.utils.LogUtils;
import com.ucfo.youcaiwx.module.main.activity.WebActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Author:AND
 * Time: 2019/1/7.  18:39
 * Email:2911743255@qq.com
 * Description:
 * Detail:TODO 二维码
 */
public class ScanActivity extends BaseActivity implements QRCodeView.Delegate {
    @BindView(R.id.zxingview)
    ZXingView mZXingView;
    @BindView(R.id.light_btn)
    ImageView lightBtn;
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.showline)
    View showline;
    private ScanActivity context;
    private boolean lightFlag = false;

    @Override
    protected void onStart() {
        super.onStart();
        SoulPermission.getInstance()
                .checkAndRequestPermission(Manifest.permission.CAMERA, new CheckRequestPermissionListener() {
                    @Override
                    public void onPermissionOk(Permission permission) {
                        mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
                        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
                        //开启扫描框
                        mZXingView.showScanRect();
                        //延迟1.5秒后开始扫描
                        mZXingView.startSpot();
                    }

                    @Override
                    public void onPermissionDenied(Permission permission) {
                        finish();
                    }
                });
    }

    @Override
    protected void onStop() {
        // 关闭摄像头预览，并且隐藏扫描框
        mZXingView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 销毁二维码扫描控件
        mZXingView.onDestroy();
        super.onDestroy();
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
        titlebarMidtitle.setText(getResources().getString(R.string.home_scan));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showline.setVisibility(View.GONE);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_scan;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @SuppressLint("NewApi")
    @Override
    protected void initData() {
        super.initData();
        //设置二维码的代理
        mZXingView.setDelegate(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);

        context = this;
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        LogUtils.e("onScanQRCodeSuccess:" + result);
        boolean matches = Patterns.WEB_URL.matcher(result).matches();
        vibrate();
        if (matches) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.WEB_URL, result);
            startActivity(WebActivity.class, bundle);
            finish();
        }
        //mZXingView.startSpot(); // 再次开始识别
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(120);
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        LogUtils.e("打开相机出错:");
    }

    @OnClick(R.id.light_btn)
    public void onViewClicked() {
        if (!lightFlag) {
            lightBtn.setImageResource(R.drawable.ic_icon_scan_open);
            mZXingView.openFlashlight();
            lightFlag = true;
        } else {
            lightBtn.setImageResource(R.drawable.ic_icon_scan_close);
            mZXingView.closeFlashlight();
            lightFlag = false;
        }
    }
}
