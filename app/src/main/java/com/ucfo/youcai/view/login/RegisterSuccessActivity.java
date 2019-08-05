package com.ucfo.youcai.view.login;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ucfo.youcai.R;
import com.ucfo.youcai.base.BaseActivity;
import com.ucfo.youcai.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcai.utils.systemutils.StatusbarUI;
import com.ucfo.youcai.view.main.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:RegisterSuccessActivity
 * Time:2019-3-22   上午 9:33
 * Detail:TODO 注册成功
 */
public class RegisterSuccessActivity extends BaseActivity {

    @BindView(R.id.btn_tosart)
    Button btnTosart;
    private RegisterSuccessActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_login_success;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        StatusbarUI.setStatusBarUIMode(this, Color.TRANSPARENT, true);
        context = this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
    }

    @OnClick({R.id.btn_tosart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_tosart://TODO 开始学习
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
        }
    }
}
