package com.ucfo.youcaiwx.module.login;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.login.LoginBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.login.LoginPresenter;
import com.ucfo.youcaiwx.presenter.presenterImpl.login.WXLoginPresenter;
import com.ucfo.youcaiwx.presenter.view.login.ILoginView;
import com.ucfo.youcaiwx.utils.ActivityUtil;
import com.ucfo.youcaiwx.utils.RegexUtil;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.AppUtils;
import com.ucfo.youcaiwx.utils.time.SMSCountDownTimer;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.module.main.activity.MainActivity;
import com.umeng.message.PushAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:LoginActivity
 * Time:2019-3-20   上午 9:10
 * Detail:TODO 短信登录
 */
public class SMSLoginActivity extends BaseActivity implements ILoginView {

    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.et_login_getcode)
    EditText etLoginGetcode;
    @BindView(R.id.verification_btn)
    Button verificationBtn;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.tv_forgetpass)
    TextView tvForgetpass;
    @BindView(R.id.other_login)
    TextView otherLogin;
    @BindView(R.id.wx_login)
    Button wxLogin;
    @BindView(R.id.account_login)
    Button accountLogin;
    @BindView(R.id.tv_soundcode)
    TextView tvSoundcode;
    @BindView(R.id.voice_linear)
    LinearLayout voiceLinear;
    private SMSLoginActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private LoginPresenter loginPresenter;
    private String mobile;
    private String androidid;
    private WXLoginPresenter wxLoginPresenter;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_smslogin;
    }

    @Override
    protected void initData() {
        super.initData();
        androidid = AppUtils.getAndroidID(this);
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
        titlebarMidtitle.setVisibility(View.GONE);
        titlebarRighttitle.setText(getResources().getString(R.string.login_register));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        context = this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);

        loginPresenter = new LoginPresenter(this);
        wxLoginPresenter = new WXLoginPresenter(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.titlebar_righttitle, R.id.verification_btn, R.id.login_btn, R.id.tv_forgetpass, R.id.wx_login, R.id.account_login, R.id.tv_soundcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titlebar_righttitle://TODO 顶部右侧标题
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.verification_btn://TODO 获取短信验证码
                mobile = etLoginPhone.getText().toString().trim();
                if (TextUtils.isEmpty(mobile)) {//TODO 手机号
                    toastInfo(getResources().getString(R.string.register_mobile_tip));
                    return;
                }
                if (!RegexUtil.checkMobile(mobile)) {//TODO 手机号格式
                    toastInfo(getResources().getString(R.string.login_mobile_iserror));
                    return;
                }
                //TODO  获取sms验证码
                loginPresenter.getVerifyCode(mobile, 2);
                break;
            case R.id.tv_soundcode://TODO 获取语音验证码
                mobile = etLoginPhone.getText().toString().trim();
                if (TextUtils.isEmpty(mobile)) {//TODO 手机号
                    toastInfo(getResources().getString(R.string.register_mobile_tip));
                    return;
                }
                if (!RegexUtil.checkMobile(mobile)) {//TODO 手机号格式
                    toastInfo(getResources().getString(R.string.login_mobile_iserror));
                    return;
                }
                //TODO  获取sms验证码
                loginPresenter.getVoiceCode(mobile);
                break;
            case R.id.login_btn://TODO 登录
                mobile = etLoginPhone.getText().toString().trim();
                String mobileCode = etLoginGetcode.getText().toString().trim();
                if (TextUtils.isEmpty(mobile)) {
                    toastInfo(getResources().getString(R.string.register_mobile_tip));
                    return;
                }
                if (!RegexUtil.checkMobile(mobile)) {
                    toastInfo(getResources().getString(R.string.login_mobile_iserror));
                    return;
                }
                if (TextUtils.isEmpty(mobileCode)) {
                    toastInfo(getResources().getString(R.string.register_smscode_error));
                    return;
                }
                String registrationId = PushAgent.getInstance(this).getRegistrationId();
                //验证通过,开始短信登录
                loginPresenter.smsLogin(mobile, mobileCode, androidid, registrationId);

                break;
            case R.id.tv_forgetpass://TODO 忘记密码
                startActivity(new Intent(context, CompleteAndForgetActivity.class));
                break;
            case R.id.wx_login:// TODO 微信登录
                wxLoginPresenter.wxLogin();
                break;
            case R.id.account_login://TODO 账号登录
                finish();
                break;
            default:
                break;
        }
    }

    public void toastInfo(String info) {
        ToastUtil.showCenterShortText(context, info);
    }

    @Override
    public void loginResult(LoginBean data) {
        if (data != null) {
            int code = data.getCode();
            if (code == 200) {
                //TODO 登录成功
                LoginBean.DataBean dataData = data.getData();
                int id = dataData.getId();
                int user_status = dataData.getUser_status();
                sharedPreferencesUtils.putInt(Constant.USER_STATUS, user_status);
                sharedPreferencesUtils.putInt(Constant.USER_ID, id);
                sharedPreferencesUtils.putBoolean(Constant.LOGIN_STATUS, true);//用户登录状态

                toastInfo(getResources().getString(R.string.login_success));

                etLoginGetcode.setText("");
                etLoginPhone.setText("");
                etLoginPhone.clearFocus();
                etLoginGetcode.clearFocus();

                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                ActivityUtil.getInstance().finishToActivity(MainActivity.class, true);

            } else {
                String msg = data.getMsg();
                toastInfo(msg);
            }
        }
    }


    @Override
    public void showLoading() {
        setProcessLoading(getResources().getString(R.string.login_logining), true);
    }

    @Override
    public void showLoadingFinish() {
        dismissPorcess();
    }

    @Override
    public void showError() {
        dismissPorcess();
    }

    private static int SECOND = 60 * 1000;

    @Override
    public void sendCodeSuccess(int code, String msg) {
        if (code == -1) {//请求失败,无网络
            toastInfo(getResources().getString(R.string.register_sendcode_error));
        } else if (code == 200) {//发送成功
            toastInfo(getResources().getString(R.string.register_sendcode));
            SMSCountDownTimer SMSCountDownTimer = new SMSCountDownTimer(SECOND, 1 * 1000, verificationBtn, tvSoundcode);
            SMSCountDownTimer.setVoiceLinear(voiceLinear);
            SMSCountDownTimer.start();
        } else {//提示接口返回信息
            toastInfo(msg);
        }
    }

    @Override
    public void sendVoiceCodeSuccess(int code, String msg) {
        if (code == -1) {//请求失败,无网络
            toastInfo(getResources().getString(R.string.register_sendcode_error));
        } else if (code == 200) {//发送成功
            toastInfo(getResources().getString(R.string.register_sendcode));
        } else {//提示接口返回信息
            toastInfo(msg);
        }
    }
}
