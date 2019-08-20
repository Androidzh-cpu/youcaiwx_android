package com.ucfo.youcai.view.user.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcai.R;
import com.ucfo.youcai.base.BaseActivity;
import com.ucfo.youcai.common.ApiStores;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.utils.ActivityUtil;
import com.ucfo.youcai.utils.DataCleanManager;
import com.ucfo.youcai.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcai.utils.systemutils.AppUtils;
import com.ucfo.youcai.utils.systemutils.StatusbarUI;
import com.ucfo.youcai.utils.toastutils.ToastUtil;
import com.ucfo.youcai.view.main.activity.MainActivity;
import com.ucfo.youcai.view.main.activity.WebActivity;
import com.ucfo.youcai.widget.customview.SwitchView;
import com.ucfo.youcai.widget.dialog.AlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-6-20 下午 2:49
 * Package: com.ucfo.youcai.view.user.activity
 * FileName: SettingActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 设置页
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.btn_updatePassword)
    LinearLayout btnUpdatePassword;
    @BindView(R.id.btn_wifiDownload)
    SwitchView btnWifiDownload;
    @BindView(R.id.btn_wifiLook)
    SwitchView btnWifiLook;
    @BindView(R.id.text_version)
    TextView textVersion;
    @BindView(R.id.btn_currentVersion)
    LinearLayout btnCurrentVersion;
    @BindView(R.id.text_cache)
    TextView textCache;
    @BindView(R.id.btn_clearCache)
    LinearLayout btnClearCache;
    @BindView(R.id.btn_userArgeement)
    LinearLayout btnUserArgeement;
    @BindView(R.id.btn_exit)
    Button btnExit;
    private SettingActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private String totalCacheSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

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
        titlebarMidtitle.setText(getResources().getString(R.string.setting));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        context = this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);

        initVersion();

        initCatchState();

        initSetting();
    }

    private void initSetting() {
        boolean download_wifi = sharedPreferencesUtils.getBoolean(Constant.DOWNLOAD_WIFI, false);
        boolean look_wifi = sharedPreferencesUtils.getBoolean(Constant.LOOK_WIFI, false);
        btnWifiDownload.setOpened(download_wifi);
        btnWifiLook.setOpened(look_wifi);

        btnWifiDownload.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                view.setOpened(true);
                sharedPreferencesUtils.putBoolean(Constant.DOWNLOAD_WIFI, true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.setOpened(false);
                sharedPreferencesUtils.putBoolean(Constant.DOWNLOAD_WIFI, false);
            }
        });
        btnWifiLook.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                view.setOpened(true);
                sharedPreferencesUtils.putBoolean(Constant.LOOK_WIFI, true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.setOpened(false);
                sharedPreferencesUtils.putBoolean(Constant.LOOK_WIFI, false);
            }
        });
    }

    //版本
    private void initVersion() {
        String appVersionName = AppUtils.getAppVersionName(context);
        textVersion.setText(appVersionName);
    }

    //缓存状态
    private void initCatchState() {
        try {
            totalCacheSize = DataCleanManager.getTotalCacheSize(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        textCache.setText(totalCacheSize);
    }

    @OnClick({R.id.btn_updatePassword, R.id.btn_currentVersion, R.id.btn_clearCache, R.id.btn_userArgeement, R.id.btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_updatePassword://TODO 修改密码
                startActivity(ModifyPasswordActivity.class, null);
                break;
            case R.id.btn_currentVersion://TODO 版本更新
                break;
            case R.id.btn_clearCache://TODO 清除缓存
                new AlertDialog(context).builder()
                        .setMsg(getResources().getString(R.string.mine_cleanCacheConfirm))
                        .setNegativeButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.clean_empty), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DataCleanManager.clearAllCache(context);
                                initCatchState();
                            }
                        }).show();
                break;
            case R.id.btn_userArgeement://TODO 用户协议
                Bundle bundle = new Bundle();
                bundle.putString(Constant.WEB_URL, ApiStores.USER_REGISTER_ARGUMENT);
                bundle.putString(Constant.WEB_TITLE, getResources().getString(R.string.mine_userAgreement));
                startActivity(WebActivity.class, bundle);
                break;
            case R.id.btn_exit://TODO 退出登录
                exitLogin();
                break;
            default:
                break;
        }
    }

    //TODO 退出登录
    private void exitLogin() {
        int userid = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        OkGo.<String>post(ApiStores.LOGIN_EXITLOGIN)
                .params(Constant.USER_ID, userid)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        setProcessLoading(null, true);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (body != null && !body.equals("")) {
                            try {
                                JSONObject object = new JSONObject(response.body());
                                int code = object.optInt(Constant.CODE);//状态码
                                if (code == 200) {
                                    exitLoginSuccess();
                                } else {
                                    exitLoginError();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            exitLoginError();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        exitLoginError();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissPorcess();
                    }
                });
    }

    public void exitLoginError() {
        ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
    }

    public void exitLoginSuccess() {
        setProcessLoading(null, false);
        sharedPreferencesUtils.remove(Constant.USER_ID);
        sharedPreferencesUtils.remove(Constant.LOGIN_STATUS);
        sharedPreferencesUtils.remove(Constant.SUBJECT_ID);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        ActivityUtil.getInstance().finishToActivity(MainActivity.class, true);
                        dismissPorcess();
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1000);
    }
}
