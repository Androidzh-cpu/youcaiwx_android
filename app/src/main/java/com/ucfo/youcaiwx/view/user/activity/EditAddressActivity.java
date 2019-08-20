package com.ucfo.youcaiwx.view.user.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.address.AddressDetailBean;
import com.ucfo.youcaiwx.entity.address.AddressListBean;
import com.ucfo.youcaiwx.entity.address.StateStatusBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.UserAddressPresenter;
import com.ucfo.youcaiwx.presenter.view.user.IUserAddressView;
import com.ucfo.youcaiwx.utils.RegexUtil;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.StatusbarUI;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.SwitchView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-6-14 下午 1:49
 * FileName: EditAddressActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 地址编辑,添加页
 */

public class EditAddressActivity extends BaseActivity implements IUserAddressView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.edit_Name)
    EditText editName;
    @BindView(R.id.edit_Phone)
    EditText editPhone;
    @BindView(R.id.edit_Address)
    EditText editAddress;
    @BindView(R.id.btn_switch)
    SwitchView btnSwitch;
    @BindView(R.id.btn_delete)
    TextView btnDelete;
    @BindView(R.id.linear_delete)
    LinearLayout linearDelete;
    private EditAddressActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int user_id, type, default_flag, address_id;
    private Bundle bundle;
    private String userName, userAddress, userPhone;
    private UserAddressPresenter userAddressPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_edit_address;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        StatusbarUI.setStatusBarUIMode(this, Color.TRANSPARENT, true);
        setSupportActionBar(titlebarToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        titlebarRighttitle.setText(getResources().getString(R.string.save));
        titlebarRighttitle.setTextColor(ContextCompat.getColor(this,R.color.color_0267FF));
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

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        context = this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
    }

    @Override
    protected void initData() {
        super.initData();
        userAddressPresenter = new UserAddressPresenter(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getInt(Constant.TYPE, 0);//TODO type:0编辑地址,1添加地址
            address_id = bundle.getInt(Constant.ADDRESS_ID, 0);//TODO 地址的ID

            if (type == 0) {//编辑地址
                titlebarMidtitle.setText(getResources().getString(R.string.mine_editAddress));
                linearDelete.setVisibility(View.VISIBLE);

                userAddressPresenter.getAddressDetail(user_id, address_id);//获取修改地址的详细信息
            } else {//添加地址
                titlebarMidtitle.setText(getResources().getString(R.string.mine_addAddress));
                linearDelete.setVisibility(View.GONE);
            }
        } else {
            finish();
        }
        btnSwitch.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                default_flag = 1;//默认
                view.toggleSwitch(true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                default_flag = 2;//非默认地址
                view.toggleSwitch(false);
            }
        });
    }

    @OnClick({R.id.titlebar_righttitle, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titlebar_righttitle://TODO 保存
                userName = editName.getText().toString().trim();
                userPhone = editPhone.getText().toString().trim();
                userAddress = editAddress.getText().toString().trim();
                if (TextUtils.isEmpty(userName)) {//用户名
                    ToastUtil.showBottomShortText(context, getResources().getString(R.string.mine_addressTips_Name));
                    return;
                }
                if (TextUtils.isEmpty(userPhone)) {//手机号
                    ToastUtil.showBottomShortText(context, getResources().getString(R.string.register_tipphone));
                    return;
                }
                if (!RegexUtil.checkMobile(userPhone)) {//格式验证
                    ToastUtil.showBottomShortText(context, (getResources().getString(R.string.mine_addressTips_Phone)));
                    return;
                }
                if (TextUtils.isEmpty(userAddress)) {//地址
                    ToastUtil.showBottomShortText(context, getResources().getString(R.string.mine_addressTips_Address));
                    return;
                }
                boolean opened = btnSwitch.isOpened();
                if (opened) {
                    default_flag = 1;//默认
                } else {
                    default_flag = 2;//非默认
                }
                if (type == 0) {//编辑地址
                    userAddressPresenter.updateAddress(user_id, address_id, userName, userPhone, userAddress, default_flag);
                } else {//添加地址
                    userAddressPresenter.addAddress(user_id, userName, userPhone, userAddress, default_flag);
                }
                break;
            case R.id.btn_delete://TODO 删除此地址
                userAddressPresenter.deleteAddress(user_id, address_id);
                break;
        }
    }

    //地址添加结果和地址修改结果操作相同
    @Override
    public void addAddressStatus(StateStatusBean data) {
        if (data != null) {
            StateStatusBean.DataBean bean = data.getData();
            if (bean != null) {
                int state = bean.getState();
                if (state == 1) {
                    ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Success));
                    editAddress.setText("");
                    editPhone.setText("");
                    editName.setText("");
                    editAddress.clearFocus();
                    editPhone.clearFocus();
                    editName.clearFocus();

                    finish();
                } else {
                    ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
                }
            } else {
                ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
            }
        } else {
            ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
        }
    }

    @Override
    public void getAddressDetail(AddressDetailBean detailBean) {
        if (detailBean != null) {
            if (detailBean.getData() != null) {
                AddressDetailBean.DataBean data = detailBean.getData();
                String address = data.getAddress();
                String consignee = data.getConsignee();
                int is_default = data.getIs_default();
                String telephone = data.getTelephone();

                editName.setText(consignee);
                editPhone.setText(telephone);
                editAddress.setText(address);
                if (is_default == 1) {
                    btnSwitch.setOpened(true);
                } else {
                    btnSwitch.setOpened(false);
                }
            } else {
                ToastUtil.showBottomShortText(context, getResources().getString(R.string.holder_nodata));
            }
        } else {
            ToastUtil.showBottomShortText(context, getResources().getString(R.string.holder_nodata));
        }
    }

    @Override
    public void deleteAddressStatus(StateStatusBean detailBean) {
        if (detailBean != null) {
            StateStatusBean.DataBean bean = detailBean.getData();
            if (bean != null) {
                int state = bean.getState();
                if (state == 1) {
                    ToastUtil.showBottomShortText(context, getResources().getString(R.string.mine_addressTips_Delete));
                    editAddress.setText("");
                    editPhone.setText("");
                    editName.setText("");
                    editAddress.clearFocus();
                    editPhone.clearFocus();
                    editName.clearFocus();

                    finish();
                } else {
                    ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
                }
            } else {
                ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
            }
        } else {
            ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
        }
    }

    @Override
    public void getUserAddressList(AddressListBean databean) {
        //TODO nothing
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
