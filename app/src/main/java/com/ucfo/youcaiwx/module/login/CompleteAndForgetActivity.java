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
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.login.WXCompletedBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.login.WXLoginPresenter;
import com.ucfo.youcaiwx.presenter.presenterImpl.register.ForgetPwdPresenter;
import com.ucfo.youcaiwx.presenter.view.register.IForgetPwdView;
import com.ucfo.youcaiwx.utils.AsteriskPasswordTransformationMethod;
import com.ucfo.youcaiwx.utils.RegexUtil;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.AppUtils;
import com.ucfo.youcaiwx.utils.time.SMSCountDownTimer;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:CompleteAndForgetActivity
 * Time:2019-3-22   上午 9:58
 * Detail:TODO 微信信息补全和忘记密码
 */
public class CompleteAndForgetActivity extends BaseActivity implements IForgetPwdView, WXLoginPresenter.IWXLoginView {

    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_verificationcode)
    EditText etVerificationcode;
    @BindView(R.id.verification_btn)
    Button verificationBtn;
    @BindView(R.id.tv_soundcode)
    TextView tvSoundcode;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.voice_linear)
    LinearLayout voiceLinear;
    private CompleteAndForgetActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private Bundle bundle;
    private String type;
    private boolean passWrodFlag = false;
    private String mobile, password, mobileCode;
    private ForgetPwdPresenter forgetPwdPresenter;
    private WXLoginPresenter wxLoginPresenter;
    private String unioid, openid, userName;
    private String headImageUrl;
    private int sex;
    private String androidid;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_complete_and_forget;
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
        titlebarRighttitle.setVisibility(View.GONE);
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

        forgetPwdPresenter = new ForgetPwdPresenter(this);
        wxLoginPresenter = new WXLoginPresenter(this, this);

        Intent intent = getIntent();
        bundle = intent.getExtras();
        if (bundle == null) {
            //TODO 默认此页面处理忘记密码类型
            type = Constant.TYPE_FORGET;
        } else {
            openid = bundle.getString(Constant.OPENID, "");
            unioid = bundle.getString(Constant.UNIONID, "");
            userName = bundle.getString(Constant.USER_NAME, "");
            headImageUrl = bundle.getString(Constant.HEAD, "");
            sex = bundle.getInt(Constant.SEX, 0);

            type = bundle.getString(Constant.TYPE, Constant.TYPE_FORGET);//TODO 默认此页面处理忘记密码类型
        }
        if (TextUtils.equals(type, Constant.TYPE_FORGET)) {//忘记密码
            SpannableString s = new SpannableString(getResources().getString(R.string.new_password));//这里输入自己想要的提示文字
            etPassword.setHint(s);
        } else {//完善信息
            SpannableString s = new SpannableString(getResources().getString(R.string.register_tipconfirmpass));//这里输入自己想要的提示文字
            etPassword.setHint(s);
        }
    }

    @Override
    protected void initData() {
        super.initData();

        editSetting();//TODO 密码输入框处理

    }

    @SuppressLint("ClickableViewAccessibility")
    private void editSetting() {
        etPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());//隐藏密码
        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = etPassword.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null) {
                    return false;
                }
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                if (etPassword.getText().toString().trim().length() == 0) {
                    return false;
                }
                if (event.getX() > etPassword.getWidth() - etPassword.getPaddingRight() - drawable.getIntrinsicWidth()) {
                    if (passWrodFlag) {
                        etPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());//隐藏密码
                        Drawable drawable1 = ContextCompat.getDrawable(context, R.mipmap.icon_pass);
                        Drawable drawable2 = ContextCompat.getDrawable(context, R.mipmap.icon_watch_closed);
                        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                        etPassword.setCompoundDrawables(drawable1, null, drawable2, null);
                        passWrodFlag = false;
                    } else {
                        etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//显示密码
                        Drawable drawable1 = ContextCompat.getDrawable(context, R.mipmap.icon_pass);
                        Drawable drawable2 = ContextCompat.getDrawable(context, R.mipmap.icon_watch_open);

                        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                        drawable2.setBounds(0, 0, drawable2.getMinimumWidth() + 15, drawable2.getMinimumHeight() + 15);
                        etPassword.setCompoundDrawables(drawable1, null, drawable2, null);
                        passWrodFlag = true;
                    }
                }
                return false;
            }
        });
        ;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.verification_btn, R.id.tv_soundcode, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.verification_btn:
                //TODO  获取短信验证码
                mobile = etPhone.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(mobile)) {//TODO 手机号
                    toastInfo(getResources().getString(R.string.register_mobile_tip));
                    return;
                }
                if (!RegexUtil.checkMobile(mobile)) {//TODO 手机号格式
                    toastInfo(getResources().getString(R.string.login_mobile_iserror));
                    return;
                }
                if (TextUtils.isEmpty(password)) {//TODO 密码为空
                    toastInfo(getResources().getString(R.string.register_password_tip));
                    return;
                }
                if (password.length() < 6) {//TODO 密码长度不对
                    toastInfo(getResources().getString(R.string.register_password_error));
                    return;
                }
                if (TextUtils.equals(type, Constant.TYPE_FORGET)) {
                    //忘记密码
                    //TODO  获取sms验证码
                    setProcessLoading(null, true);
                    forgetPwdPresenter.getVerifyCode(mobile);
                } else {
                    //TODO  完善信息验证码
                    setProcessLoading(null, true);
                    forgetPwdPresenter.getWXVerifyCode(mobile, 2);
                }
                break;
            case R.id.tv_soundcode:
                //TODO  获取 语音验证码
                mobile = etPhone.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(mobile)) {//TODO 手机号
                    toastInfo(getResources().getString(R.string.register_mobile_tip));
                    return;
                }
                if (!RegexUtil.checkMobile(mobile)) {//TODO 手机号格式
                    toastInfo(getResources().getString(R.string.login_mobile_iserror));
                    return;
                }
                if (TextUtils.isEmpty(password)) {//TODO 密码为空
                    toastInfo(getResources().getString(R.string.register_password_tip));
                    return;
                }
                if (password.length() < 6) {//TODO 密码长度不对
                    toastInfo(getResources().getString(R.string.register_password_error));
                    return;
                }
                //TODO  获取语音验证码
                setProcessLoading(null, true);
                forgetPwdPresenter.getVoiceCode(mobile);
                break;
            case R.id.btn_confirm:
                //TODO  确认按钮
                mobile = etPhone.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                mobileCode = etVerificationcode.getText().toString().trim();
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
                if (password.length() < 6) {//TODO 密码
                    toastInfo(getResources().getString(R.string.register_password_error));
                    return;
                }
                if (TextUtils.isEmpty(mobileCode)) {//TODO 验证码
                    toastInfo(getResources().getString(R.string.register_smscode_error));
                    return;
                }
                //处理不同页面的逻辑
                confirmBtnEventClick(mobile, mobileCode, password);
                break;
            default:
                break;
        }
    }

    private void confirmBtnEventClick(String mobile, String mobileCode, String password) {
        if (TextUtils.equals(type, Constant.TYPE_FORGET)) {
            //忘记密码
            //开始重置密码
            forgetPwdPresenter.resetPassWord(mobile, mobileCode, password);
        } else {//完善信息
            androidid = AppUtils.getAndroidID(this);

            wxLoginPresenter.wxLoginCompletedInfo(mobile, mobileCode, password, unioid, openid, userName, androidid, sex, headImageUrl);
        }
    }

    private void toastInfo(String string) {
        ToastUtil.showCenterShortText(context, string);
    }

    @Override
    public void registerResult(int code, String msg) {
        if (code == 200) {
            etPhone.clearFocus();
            etPhone.setText("");
            etVerificationcode.clearFocus();
            etVerificationcode.setText("");
            etPassword.clearFocus();
            etPassword.setText("");

            toastInfo(getResources().getString(R.string.reset_success));
            finish();
        } else if (code == -1) {
            toastInfo(getResources().getString(R.string.reset_error));
        } else {
            toastInfo(msg);
        }
    }

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

    @Override
    public void showLoading() {
        setProcessLoading(getResources().getString(R.string.reset_resting), true);
    }

    @Override
    public void showLoadingFinish() {
        dismissPorcess();
    }

    @Override
    public void showError() {
        dismissPorcess();
    }

    @Override
    public void wxLoginCompleted(WXCompletedBean result) {
        if (result != null) {
            WXCompletedBean.DataBean data = result.getData();
            int user_id = data.getId();
            int status = data.getStatus();
            sharedPreferencesUtils.putInt(Constant.USER_ID, user_id);
            sharedPreferencesUtils.putInt(Constant.USER_STATUS, status);
            sharedPreferencesUtils.putBoolean(Constant.LOGIN_STATUS, true);//用户登录状态
            Intent intent = new Intent(context, RegisterSuccessActivity.class);
            startActivity(intent);
            finish();
        } else {
            toastInfo(getResources().getString(R.string.completed_error));
        }
    }

    @Override
    public void startWxCompletedLoading() {
        setProcessLoading(getResources().getString(R.string.completed_resting), true);
    }

    @Override
    public void finishWxCompletedLoading() {
        dismissPorcess();
    }
}
