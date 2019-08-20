package com.ucfo.youcaiwx.view.main.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.widget.LinearLayout;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    private static final int MESSAGE_INTENT = 1001;
    @BindView(R.id.welcome)
    LinearLayout welcome;
    private Handler handler = null;
    private Timer timer = null;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
    protected int setContentView() {
        return R.layout.activity_splash;
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void initData() {
        super.initData();

        showRandomWelcome();

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
        welcome.setBackgroundResource(R.mipmap.splash_img);
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
