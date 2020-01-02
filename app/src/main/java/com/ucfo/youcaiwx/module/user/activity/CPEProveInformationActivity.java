package com.ucfo.youcaiwx.module.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.utils.RegexUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2020-1-2 上午 10:53
 * Package: com.ucfo.youcaiwx.module.user.activity
 * FileName: CPEProveInformationActivity
 * ORG: www.youcaiwx.com
 * Description:证明信息填写
 */
public class CPEProveInformationActivity extends BaseActivity {

    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.showline)
    View showline;
    @BindView(R.id.edit_Name)
    EditText editName;
    @BindView(R.id.edit_idnumber)
    EditText editIdnumber;
    @BindView(R.id.edit_vip)
    EditText editVip;
    private String resultEditName;
    private String resultEditIDNum;
    private String resultEditVip;

    @Override
    protected int setContentView() {
        return R.layout.activity_cpeprove_information;
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
        titlebarMidtitle.setText(getResources().getString(R.string.proveinformation));
        titlebarRighttitle.setText(getResources().getString(R.string.save));
        titlebarRighttitle.setTextColor(ContextCompat.getColor(this, R.color.color_0267FF));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //姓名
            resultEditName = bundle.getString(Constant.EDUCATION_EDIT_NAME, "");
            //身份证号
            resultEditIDNum = bundle.getString(Constant.EDUCATION_EDIT_IDNUM, "");
            //会员号
            resultEditVip = bundle.getString(Constant.EDUCATION_EDIT_VIP, "");
        }

        //设置编辑信息
        if (!TextUtils.isEmpty(resultEditName)) {
            editName.setText(resultEditName);
        }
        if (!TextUtils.isEmpty(resultEditIDNum)) {
            editIdnumber.setText(resultEditIDNum);
        }
        if (!TextUtils.isEmpty(resultEditVip)) {
            editVip.setText(resultEditVip);
        }
    }

    @OnClick(R.id.titlebar_righttitle)
    public void onViewClicked() {
        String edittextName = editName.getText().toString().trim();
        String edittextID = editIdnumber.getText().toString().trim();
        String edittextVIP = editVip.getText().toString().trim();
        if (TextUtils.isEmpty(edittextName)) {
            showToast(getResources().getString(R.string.cpe_tips_name));
            return;
        }
        if (TextUtils.isEmpty(edittextID)) {
            showToast(getResources().getString(R.string.cpe_tips_idnum));
            return;
        }
        if (TextUtils.isEmpty(edittextVIP)) {
            showToast(getResources().getString(R.string.cpe_tips_vip));
            return;
        }
        if (!RegexUtil.isLegalName(edittextName)) {
            showToast(getResources().getString(R.string.cpe_tips_name_error));
            return;
        }
        if (!RegexUtil.checkIdCard(edittextID)) {
            showToast(getResources().getString(R.string.cpe_tips_idnum_error));
            return;
        }


        //信息保存
        Intent intent = new Intent();
        intent.putExtra(Constant.EDUCATION_EDIT_NAME, edittextName);
        intent.putExtra(Constant.EDUCATION_EDIT_IDNUM, edittextID);
        intent.putExtra(Constant.EDUCATION_EDIT_VIP, edittextVIP);
        setResult(Constant.REQUEST_PROVEINFO, intent);
        finish();
    }
}
