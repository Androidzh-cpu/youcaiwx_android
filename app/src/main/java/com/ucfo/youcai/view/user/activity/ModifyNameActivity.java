package com.ucfo.youcai.view.user.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ucfo.youcai.R;
import com.ucfo.youcai.base.BaseActivity;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.address.StateStatusBean;
import com.ucfo.youcai.entity.user.UserInfoBean;
import com.ucfo.youcai.presenter.presenterImpl.user.UserInfoPresenter;
import com.ucfo.youcai.presenter.view.user.IUserInfoView;
import com.ucfo.youcai.utils.RegexUtil;
import com.ucfo.youcai.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcai.utils.systemutils.StatusbarUI;
import com.ucfo.youcai.utils.toastutils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-6-11 上午 10:56
 * Package: com.ucfo.youcai.view.user
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
        // TODO: add setContentView(...) invocation
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
        userNickname.addTextChangedListener(textWatcher);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        //状态栏白色,字体黑色
        StatusbarUI.setStatusBarUIMode(this, Color.TRANSPARENT, true);
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

    //TODO 输入文字监听
    private TextWatcher textWatcher = new TextWatcher() {
        private int maxLen = 10; // 最大输入字符
        private CharSequence beforeSeq; // 保存修改前的值
        private int afterStart;
        private int afterCount;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (s.length() + (after - count) > maxLen) {
                beforeSeq = s.subSequence(start, start + count);
                ToastUtil.showBottomShortText(context, getResources().getString(R.string.mine_maxEdittor10));
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (count > before && s.length() > maxLen) { //如果字符数增加时，且当前字符数超过限制了, 保存原串用于还原
                afterStart = start;
                afterCount = count;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > maxLen) {
                try {
                    s.replace(afterStart, afterStart + afterCount, beforeSeq);
                } catch (IndexOutOfBoundsException e) {
                    String message = e.getMessage();
                }
            }
        }
    };


}
