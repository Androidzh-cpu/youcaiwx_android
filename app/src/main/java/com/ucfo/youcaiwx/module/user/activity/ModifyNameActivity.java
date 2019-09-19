package com.ucfo.youcaiwx.module.user.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.address.StateStatusBean;
import com.ucfo.youcaiwx.entity.user.UserInfoBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.UserInfoPresenter;
import com.ucfo.youcaiwx.presenter.view.user.IUserInfoView;
import com.ucfo.youcaiwx.utils.RegexUtil;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-6-11 上午 10:56
 * FileName: ModifyNameActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 修改昵称
 */
public class ModifyNameActivity extends BaseActivity implements IUserInfoView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.user_nickname)
    EditText userNickname;
    private Bundle bundle;
    private String nickname;
    private ModifyNameActivity context;
    private UserInfoPresenter userInfoPresenter;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_modify_name;
    }

    @Override
    protected void initData() {
        super.initData();
        context = this;
        userInfoPresenter = new UserInfoPresenter(this);
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            nickname = bundle.getString(Constant.NICKNAME);

            if (!TextUtils.isEmpty(nickname)) {
                userNickname.setText(nickname);
            }
        } else {
            finish();
        }
        //输入字数限制
        userNickname.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
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
        titlebarMidtitle.setText(getResources().getString(R.string.mine_modifrName));
        titlebarRighttitle.setText(getResources().getString(R.string.confirm));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick(R.id.titlebar_righttitle)
    public void onViewClicked() {
        String trim = userNickname.getText().toString().trim();
        if (nickname.equals(trim)) {
            ToastUtil.showBottomShortText(context, getResources().getString(R.string.mine_modifyNameTips3));
            return;
        }
        if (trim.length() < 4 || trim.length() > 10) {
            ToastUtil.showBottomShortText(context, getResources().getString(R.string.mine_modifyNameTips1));
            return;
        }
        if (RegexUtil.isConSpeCharacters(trim)) {
            ToastUtil.showBottomShortText(context, getResources().getString(R.string.mine_modifyNameTips2));
            return;
        }
        userInfoPresenter.retoucheInfo(user_id, 2, trim);
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

}
