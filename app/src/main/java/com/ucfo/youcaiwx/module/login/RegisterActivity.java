package com.ucfo.youcaiwx.module.login;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.login.RegisterBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.register.RegisterPresenter;
import com.ucfo.youcaiwx.presenter.view.register.IRegisterView;
import com.ucfo.youcaiwx.utils.AsteriskPasswordTransformationMethod;
import com.ucfo.youcaiwx.utils.RegexUtil;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.time.SMSCountDownTimer;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.module.main.activity.WebActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:LoginActivity
 * Time:2019-3-20   上午 9:10
 * Detail:TODO 注册
 */
public class RegisterActivity extends BaseActivity implements IRegisterView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_register_phone)
    EditText etRegisterPhone;
    @BindView(R.id.et_register_password)
    EditText etRegisterPassword;
    @BindView(R.id.et_register_confirmpass)
    EditText etRegisterConfirmpass;
    @BindView(R.id.et_register_getcode)
    EditText etRegisterGetcode;
    @BindView(R.id.register_btn)
    Button registerBtn;
    @BindView(R.id.tv_forgetpass)
    TextView tvForgetpass;
    @BindView(R.id.verification_btn)
    Button verificationBtn;
    @BindView(R.id.tv_soundcode)
    TextView tvSoundcode;
    @BindView(R.id.check_argument)
    CheckBox checkArgument;
    @BindView(R.id.tv_argument)
    TextView tvArgument;
    @BindView(R.id.voice_linear)
    LinearLayout voiceLinear;
    private RegisterActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private boolean passWrodFlag = false;
    private boolean confirmPassWrodFlag = false;
    private RegisterPresenter registerPresenter;
    private String mobile;
    private String password;
    private String confirmPassword;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_register;
    }


    @Override
    protected void initData() {
        super.initData();

        editSetting();//TODO 密码输入框处理

    }

    @SuppressLint("ClickableViewAccessibility")
    private void editSetting() {
        etRegisterPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());//隐藏密码
        etRegisterConfirmpass.setTransformationMethod(new AsteriskPasswordTransformationMethod());//隐藏密码
        etRegisterPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = etRegisterPassword.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null) {
                    return false;
                }
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                if (etRegisterPassword.getText().toString().trim().length() == 0) {
                    return false;
                }
                if (event.getX() > etRegisterPassword.getWidth() - etRegisterPassword.getPaddingRight() - drawable.getIntrinsicWidth()) {
                    if (passWrodFlag) {
                        etRegisterPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());//隐藏密码
                        Drawable drawable1 = ContextCompat.getDrawable(context, R.mipmap.icon_pass);
                        Drawable drawable2 = ContextCompat.getDrawable(context, R.mipmap.icon_watch_closed);
                        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                        etRegisterPassword.setCompoundDrawables(drawable1, null, drawable2, null);
                        passWrodFlag = false;
                    } else {
                        etRegisterPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//显示密码
                        Drawable drawable1 = ContextCompat.getDrawable(context, R.mipmap.icon_pass);
                        Drawable drawable2 = ContextCompat.getDrawable(context, R.mipmap.icon_watch_open);
                        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                        drawable2.setBounds(0, 0, drawable2.getMinimumWidth() + 15, drawable2.getMinimumHeight() + 15);
                        etRegisterPassword.setCompoundDrawables(drawable1, null, drawable2, null);
                        passWrodFlag = true;
                    }
                }
                return false;
            }
        });
        etRegisterConfirmpass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = etRegisterConfirmpass.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null) {
                    return false;
                }
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                if (etRegisterConfirmpass.getText().toString().trim().length() == 0) {
                    return false;
                }
                if (event.getX() > etRegisterConfirmpass.getWidth() - etRegisterConfirmpass.getPaddingRight() - drawable.getIntrinsicWidth()) {
                    if (confirmPassWrodFlag) {
                        etRegisterConfirmpass.setTransformationMethod(new AsteriskPasswordTransformationMethod());//隐藏密码
                        Drawable drawable1 = ContextCompat.getDrawable(context, R.mipmap.icon_pass);
                        Drawable drawable2 = ContextCompat.getDrawable(context, R.mipmap.icon_watch_closed);
                        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                        etRegisterConfirmpass.setCompoundDrawables(drawable1, null, drawable2, null);
                        confirmPassWrodFlag = false;
                    } else {
                        etRegisterConfirmpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//显示密码
                        Drawable drawable1 = ContextCompat.getDrawable(context, R.mipmap.icon_pass);
                        Drawable drawable2 = ContextCompat.getDrawable(context, R.mipmap.icon_watch_open);
                        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                        drawable2.setBounds(0, 0, drawable2.getMinimumWidth() + 15, drawable2.getMinimumHeight() + 15);
                        etRegisterConfirmpass.setCompoundDrawables(drawable1, null, drawable2, null);
                        confirmPassWrodFlag = true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        titlebarMidtitle.setVisibility(View.GONE);
        titlebarRighttitle.setText(getResources().getString(R.string.login_login));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
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

        registerPresenter = new RegisterPresenter(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.titlebar_righttitle, R.id.register_btn, R.id.tv_forgetpass, R.id.verification_btn, R.id.tv_soundcode, R.id.tv_argument})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titlebar_righttitle://TODO 普通登录
                finish();
                break;
            case R.id.register_btn://TODO 注册按钮
                mobile = etRegisterPhone.getText().toString().trim();
                password = etRegisterPassword.getText().toString().trim();
                confirmPassword = etRegisterConfirmpass.getText().toString().trim();
                String smsCode = etRegisterGetcode.getText().toString().trim();

                if (TextUtils.isEmpty(mobile)) {//TODO 手机号
                    toastInfo(getResources().getString(R.string.register_mobile_tip));
                    return;
                }
                if (!RegexUtil.checkMobile(mobile)) {//TODO 手机号格式
                    toastInfo(getResources().getString(R.string.login_mobile_iserror));
                    return;
                }
                if (TextUtils.isEmpty(password)) {//TODO 密码
                    toastInfo(getResources().getString(R.string.register_password_tip));
                    return;
                }
                if (password.length() < 6) {//TODO 密码小于6位
                    toastInfo(getResources().getString(R.string.register_password_error));
                    return;
                }
                if (!password.equals(confirmPassword)) {//TODO 两次密码不一致
                    toastInfo(getResources().getString(R.string.register_psdAndconfirmpsd_error));
                    return;
                }
                if (TextUtils.isEmpty(smsCode)) {//TODO 验证码
                    toastInfo(getResources().getString(R.string.register_smscode_error));
                    return;
                }
                if (!checkArgument.isChecked()) {//TODO 用户注册协议
                    toastInfo(getResources().getString(R.string.register_argument_tip));
                    return;
                }
                registerPresenter.accountRegister(mobile, smsCode, password, confirmPassword);

                break;
            case R.id.tv_forgetpass://TODO 忘记密码
                startActivity(new Intent(context, CompleteAndForgetActivity.class));
                break;
            case R.id.verification_btn://TODO 获取短信验证码
                mobile = etRegisterPhone.getText().toString().trim();
                password = etRegisterPassword.getText().toString().trim();
                confirmPassword = etRegisterConfirmpass.getText().toString().trim();
                if (TextUtils.isEmpty(mobile)) {//TODO 手机号
                    toastInfo(getResources().getString(R.string.register_mobile_tip));
                    return;
                }
                if (!RegexUtil.checkMobile(mobile)) {//TODO 手机号格式
                    toastInfo(getResources().getString(R.string.login_mobile_iserror));
                    return;
                }
                if (TextUtils.isEmpty(password)) {//TODO 密码未输入
                    toastInfo(getResources().getString(R.string.register_password_tip));
                    return;
                }
                if (!password.equals(confirmPassword)) {//TODO 两次密码不一致
                    toastInfo(getResources().getString(R.string.register_psdAndconfirmpsd_error));
                    return;
                }
                //TODO  获取sms验证码
                registerPresenter.getVerifyCode(mobile, 1);
                break;
            case R.id.tv_soundcode://TODO 获取语音验证码
                mobile = etRegisterPhone.getText().toString().trim();
                password = etRegisterPassword.getText().toString().trim();
                confirmPassword = etRegisterConfirmpass.getText().toString().trim();
                if (TextUtils.isEmpty(mobile)) {//TODO 手机号
                    toastInfo(getResources().getString(R.string.register_mobile_tip));
                    return;
                }
                if (!RegexUtil.checkMobile(mobile)) {//TODO 手机号格式
                    toastInfo(getResources().getString(R.string.login_mobile_iserror));
                    return;
                }
                if (TextUtils.isEmpty(password)) {//TODO 密码未输入
                    toastInfo(getResources().getString(R.string.register_password_tip));
                    return;
                }
                if (!password.equals(confirmPassword)) {//TODO 两次密码不一致
                    toastInfo(getResources().getString(R.string.register_psdAndconfirmpsd_error));
                    return;
                }
                //TODO  获取语音验证码
                registerPresenter.getVoiceCode(mobile);
                break;
            case R.id.tv_argument://TODO 用户注册协议
                Intent intent = new Intent(context, WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.WEB_URL, ApiStores.USER_REGISTER_ARGUMENT);
                bundle.putString(Constant.WEB_TITLE, getResources().getString(R.string.register_tiptext3));
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void toastInfo(String string) {
        ToastUtil.showCenterShortText(context, string);
    }

    @Override
    public void registerResult(RegisterBean data) {
        if (data != null) {
            int code = data.getCode();
            if (code == 200) {
                etRegisterGetcode.clearFocus();
                etRegisterGetcode.setText("");
                etRegisterConfirmpass.clearFocus();
                etRegisterConfirmpass.setText("");
                etRegisterPassword.clearFocus();
                etRegisterPassword.setText("");
                etRegisterPhone.clearFocus();
                etRegisterPhone.setText("");

                sharedPreferencesUtils.putInt(Constant.USER_ID, Integer.parseInt(data.getData().getId()));//用户ID
                sharedPreferencesUtils.putInt(Constant.USER_STATUS, Integer.parseInt(data.getData().getStatus()));//用户类别信息
                sharedPreferencesUtils.putBoolean(Constant.LOGIN_STATUS, true);//用户登录状态

                //TODO 注册成功,前往欢迎页
                Intent intent = new Intent(context, RegisterSuccessActivity.class);
                startActivity(intent);
                finish();
            } else {
                String msg = data.getMsg();
                toastInfo(msg);
            }
        } else {
            toastInfo(getResources().getString(R.string.register_error));
        }
    }

    @Override
    public void showLoading() {
        setProcessLoading(getResources().getString(R.string.register_registing), true);
    }

    @Override
    public void showLoadingFinish() {
        dismissPorcess();
    }

    @Override
    public void showError() {
        dismissPorcess();
    }

    //验证码已发送
    @Override
    public void sendCodeSuccess(int code, String msg) {
        if (code == -1) {//请求失败,无网络
            toastInfo(getResources().getString(R.string.register_sendcode_error));
        } else if (code == 200) {//发送成功
            toastInfo(getResources().getString(R.string.register_sendcode));
            SMSCountDownTimer SMSCountDownTimer = new SMSCountDownTimer(Constant.SMS_SECOND, 1 * 1000, verificationBtn, tvSoundcode);
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
