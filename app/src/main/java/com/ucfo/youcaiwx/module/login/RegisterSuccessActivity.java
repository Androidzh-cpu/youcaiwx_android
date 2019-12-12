package com.ucfo.youcaiwx.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.module.main.activity.MainActivity;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;

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

    @Override
    protected int setContentView() {
        return R.layout.activity_login_success;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);

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
