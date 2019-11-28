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
import android.widget.EditText;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.login.LoginBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.login.LoginPresenter;
import com.ucfo.youcaiwx.presenter.presenterImpl.login.WXLoginPresenter;
import com.ucfo.youcaiwx.presenter.view.login.ILoginView;
import com.ucfo.youcaiwx.utils.ActivityUtil;
import com.ucfo.youcaiwx.utils.AsteriskPasswordTransformationMethod;
import com.ucfo.youcaiwx.utils.RegexUtil;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.AppUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.module.main.activity.MainActivity;
import com.umeng.message.PushAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:LoginActivity
 * Time:2019-3-20   上午 9:10
 * Detail:TODO 登录
 */
public class LoginActivity extends BaseActivity implements ILoginView {

    @BindView(R.id.titlebar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.titlebar_midtitle)
    TextView tvTitle;
    @BindView(R.id.titlebar_righttitle)
    TextView tvSubTitle;
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.tv_forgetpass)
    TextView tvForgetpass;
    @BindView(R.id.wx_login)
    Button wxLogin;
    @BindView(R.id.sms_login)
    Button smsLogin;
    @BindView(R.id.other_login)
    TextView otherLogin;
    private LoginActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private boolean passWrodFlag = false;
    private LoginPresenter loginPresenter;
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
        return R.layout.activity_login;
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
        tvTitle.setVisibility(View.GONE);
        tvSubTitle.setText(getResources().getString(R.string.login_register));
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
        //注册网络
        loginPresenter = new LoginPresenter(this);
        //微信注册扥路
        wxLoginPresenter = new WXLoginPresenter(this);

    }

    @Override
    protected void initData() {
        super.initData();
        androidid = AppUtils.getAndroidID(this);

        etconfig();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void etconfig() {
        etLoginPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());//隐藏密码
        etLoginPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Drawable drawable = etLoginPassword.getCompoundDrawables()[2];// et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                //如果右边没有图片，不再处理
                if (drawable == null) {
                    return false;
                }
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                if (etLoginPassword.getText().toString().trim().length() == 0) {
                    return false;
                }
                if (event.getX() > etLoginPassword.getWidth() - etLoginPassword.getPaddingRight() - drawable.getIntrinsicWidth()) {
                    if (passWrodFlag) {
                        etLoginPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());//隐藏密码
                        Drawable drawable1 = ContextCompat.getDrawable(context, R.mipmap.icon_pass);
                        Drawable drawable2 = ContextCompat.getDrawable(context, R.mipmap.icon_watch_closed);
                        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                        etLoginPassword.setCompoundDrawables(drawable1, null, drawable2, null);
                        passWrodFlag = false;
                    } else {
                        etLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//显示密码
                        Drawable drawable1 = ContextCompat.getDrawable(context, R.mipmap.icon_pass);
                        Drawable drawable2 = ContextCompat.getDrawable(context, R.mipmap.icon_watch_open);
                        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                        drawable2.setBounds(0, 0, drawable2.getMinimumWidth() + 15, drawable2.getMinimumHeight() + 15);
                        etLoginPassword.setCompoundDrawables(drawable1, null, drawable2, null);
                        passWrodFlag = true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.titlebar_righttitle, R.id.login_btn, R.id.tv_forgetpass, R.id.wx_login, R.id.sms_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titlebar_righttitle://TODO 注册
                startActivity(RegisterActivity.class);
                break;
            case R.id.login_btn://TODO 登录
                String mobile = etLoginPhone.getText().toString().trim();
                String password = etLoginPassword.getText().toString().trim();
                if (TextUtils.isEmpty(mobile)) {
                    toastInfo(getResources().getString(R.string.register_mobile_tip));
                    return;
                }
                if (!RegexUtil.checkMobile(mobile)) {
                    toastInfo(getResources().getString(R.string.login_mobile_iserror));
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    toastInfo(getResources().getString(R.string.login_password_isempty));
                    return;
                }
                if (password.length() < 6) {
                    toastInfo(getResources().getString(R.string.register_password_error));
                    return;
                }
                String registrationId = PushAgent.getInstance(this).getRegistrationId();
                //请求网络,准备登录
                loginPresenter.commonLogin(mobile, password, androidid, registrationId);
                break;
            case R.id.tv_forgetpass://TODO 忘记密码
                startActivity(CompleteAndForgetActivity.class);
                break;
            case R.id.wx_login://TODO 微信登录
                wxLoginPresenter.wxLogin();
                break;
            case R.id.sms_login://TODO 短信登录
                startActivity(SMSLoginActivity.class);
                break;
            default:
                break;
        }
    }


    @Override
    public void loginResult(LoginBean data) {
        //TODO 登录结果
        if (data != null) {
            int code = data.getCode();
            if (code == 200) {
                //TODO 登录成功
                LoginBean.DataBean dataData = data.getData();
                String id = dataData.getId();
                String userStatus = dataData.getUser_status();

                sharedPreferencesUtils.putInt(Constant.USER_ID, Integer.parseInt(id));
                sharedPreferencesUtils.putInt(Constant.USER_STATUS, Integer.parseInt(userStatus));
                sharedPreferencesUtils.putBoolean(Constant.LOGIN_STATUS, true);

                toastInfo(getResources().getString(R.string.login_success));

                etLoginPassword.setText("");
                etLoginPhone.setText("");
                etLoginPhone.clearFocus();
                etLoginPassword.clearFocus();

                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                ActivityUtil.getInstance().finishToActivity(MainActivity.class, true);
            } else {
                String msg = data.getMsg();
                toastInfo(msg);
            }
        } else {
            toastInfo(getResources().getString(R.string.login_error));
        }
    }


    public void toastInfo(String info) {
        ToastUtil.showCenterShortText(this, info);
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

    @Override
    public void sendCodeSuccess(int code, String msg) {
        //TODO 验证码
    }

    @Override
    public void sendVoiceCodeSuccess(int code, String msg) {
        //TODO 验证码
    }
}
