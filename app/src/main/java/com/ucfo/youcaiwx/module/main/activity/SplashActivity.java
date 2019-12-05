package com.ucfo.youcaiwx.module.main.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.LinearLayout;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.StatusBarUtil;
import com.ucfo.youcaiwx.widget.dialog.AgreementDialog;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-9-9 下午 5:41
 * Package: com.ucfo.youcaiwx.view.main.activity
 * FileName: SplashActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 启动页
 */
public class SplashActivity extends BaseActivity {
    private static final int MESSAGE_INTENT = 1001;
    @BindView(R.id.welcome)
    LinearLayout welcome;
    @BindView(R.id.image_view)
    AppCompatImageView imageView;
    private Handler handler = null;
    private Timer timer = null;
    private AgreementDialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //WHETHER_READ_AGREEMENT
        boolean whether = SharedPreferencesUtils.getInstance(SplashActivity.this).getBoolean(Constant.WHETHER_READ_AGREEMENT, false);
        if (whether) {
            countdown();
        } else {
            dialog = new AgreementDialog(this).builder();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setAgreementClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.WEB_URL, ApiStores.USER_REGISTER_ARGUMENT);
                    bundle.putString(Constant.WEB_TITLE, getResources().getString(R.string.mine_userAgreement));
                    startActivity(WebActivity.class, bundle);
                }
            });
            dialog.setNegativeButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            dialog.setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferencesUtils.getInstance(SplashActivity.this).putBoolean(Constant.WHETHER_READ_AGREEMENT, true);

                    //是否是第一次登录
                    boolean isFirst = SharedPreferencesUtils.getInstance(SplashActivity.this).getBoolean(Constant.FIRST_LOGIN, false);
                    if (isFirst) {
                        //TODO 非第一次登录,直接进入主界面
                        startActivity(MainActivity.class);
                        finish();
                    } else {
                        //TODO 第一次登录,进入引导页
                        startActivity(GuideActivity.class);
                        finish();
                    }
                }
            });
            dialog.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        handler = null;
    }

    @Override
    protected void initToolbar() {
        StatusBarUtil.immersive(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        super.initData();
        showRandomWelcome();
    }

    @SuppressLint("HandlerLeak")
    private void countdown() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == MESSAGE_INTENT) {
                    boolean isFirst = SharedPreferencesUtils.getInstance(SplashActivity.this).getBoolean(Constant.FIRST_LOGIN, false);
                    if (isFirst) {
                        //TODO 非第一次登录,直接进入主界面
                        startActivity(MainActivity.class);
                        finish();
                    } else {
                        //TODO 第一次登录,进入引导页
                        startActivity(GuideActivity.class);
                        finish();
                    }
                }
            }
        };

        if (null == timer) {
            timer = new Timer();
        }
        timer.schedule(refreshTimerTask(), 1000);
    }

    private void showRandomWelcome() {
        imageView.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.splash_img));
    }

    private TimerTask refreshTimerTask() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (handler == null) {
                    return;
                }
                Message message = new Message();
                message.what = MESSAGE_INTENT;
                handler.sendMessage(message);
            }
        };
        return timerTask;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
