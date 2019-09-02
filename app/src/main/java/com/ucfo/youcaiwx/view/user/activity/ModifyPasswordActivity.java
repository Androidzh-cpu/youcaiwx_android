package com.ucfo.youcaiwx.view.user.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.address.StateStatusBean;
import com.ucfo.youcaiwx.entity.user.UserInfoBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.UserInfoPresenter;
import com.ucfo.youcaiwx.presenter.view.user.IUserInfoView;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-6-20 下午 5:52
 * FileName: ModifyPasswordActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 修改密码
 */

public class ModifyPasswordActivity extends BaseActivity implements IUserInfoView {

    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.edit_oldPassword)
    EditText editOldPassword;
    @BindView(R.id.edit_newPassword)
    EditText editNewPassword;
    @BindView(R.id.edit_newPasswordAgain)
    EditText editNewPasswordAgain;
    @BindView(R.id.btn_save)
    Button btnSave;
    private ModifyPasswordActivity context;
    private UserInfoPresenter userInfoPresenter;
    private String oldPassword, newPassword, newPasswordAgain;
    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_modify_password;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

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
        titlebarRighttitle.setVisibility(View.GONE);
        titlebarMidtitle.setText(getResources().getString(R.string.mine_updatePWD));
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
        userInfoPresenter = new UserInfoPresenter(this);
        user_id = SharedPreferencesUtils.getInstance(context).getInt(Constant.USER_ID, 0);
    }

    @Override
    public void getUserInfo(UserInfoBean bean) {
        //TODO nothing
    }

    @Override
    public void retouceResult(StateStatusBean result) {
        if (result != null) {
            if (result.getData() != null) {
                int state = result.getData().getState();
                switch (state) {
                    case 1:
                        ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Success));
                        finish();
                        break;
                    case 0:
                    default:
                        ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
                        break;
                }
            } else {
                ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
            }
        } else {
            ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
        }
    }

    @Override
    public void showLoading() {
        setProcessLoading(null, true);
    }

    @Override
    public void showLoadingFinish() {
        dismissPorcess();
    }

    @Override
    public void showError() {

    }

    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        oldPassword = editOldPassword.getText().toString().trim();
        newPassword = editNewPassword.getText().toString().trim();
        newPasswordAgain = editNewPasswordAgain.getText().toString().trim();
        if (TextUtils.isEmpty(oldPassword)) {//TODO 密码未输入
            toastInfo(getResources().getString(R.string.register_password_tip));
            return;
        }
        if (TextUtils.isEmpty(newPassword)) {//TODO 新密码
            toastInfo(getResources().getString(R.string.mine_inputNewPassword));
            return;
        }
        if (TextUtils.isEmpty(newPasswordAgain)) {//TODO 确认新密码
            toastInfo(getResources().getString(R.string.mine_inputNewPasswordConfirm));
            return;
        }
        if (!newPassword.equals(newPasswordAgain)) {//TODO 两次密码不一致
            toastInfo(getResources().getString(R.string.register_psdAndconfirmpsd_error));
            return;
        }
        userInfoPresenter.modifyPassword(user_id, oldPassword, newPassword, newPasswordAgain);
    }

    public void toastInfo(String text) {
        ToastUtil.showBottomShortText(context, text);
    }
}
