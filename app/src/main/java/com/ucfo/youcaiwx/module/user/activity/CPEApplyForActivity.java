package com.ucfo.youcaiwx.module.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-12-31 下午 2:50
 * Package: com.ucfo.youcaiwx.module.user.activity
 * FileName: CPEApplyForActivity
 * ORG: www.youcaiwx.com
 * Description:CPE申请玩意
 */
public class CPEApplyForActivity extends BaseActivity {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.showline)
    View showline;
    @BindView(R.id.image_icon)
    ImageView imageIcon;
    @BindView(R.id.text_addProveInfo)
    TextView txtAddProveInfo;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_vipNumber)
    TextView txtVipNumber;
    @BindView(R.id.txt_idNumber)
    TextView txtIdNumber;
    @BindView(R.id.btn_proveInfo)
    LinearLayout btnProveInfo;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.txt_point)
    TextView txtPoint;
    @BindView(R.id.btn_next)
    RoundTextView btnNext;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.linear_name_vip)
    LinearLayout linearNameVip;
    @BindView(R.id.linear_idnum)
    LinearLayout linearIdnum;

    @Override
    protected int setContentView() {
        return R.layout.activity_apply_for;
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
        titlebarMidtitle.setText(getResources().getString(R.string.CPEApplyFor));
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


        loadinglayout.showContent();
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.REQUEST_PROVEINFO:
                //TODO 选取信息
                if (resultCode == Constant.REQUEST_PROVEINFO) {
                    if (data.getExtras() != null) {
                        Bundle bundle = data.getExtras();
                        String editName = bundle.getString(Constant.EDUCATION_EDIT_NAME);
                        String editIDNum = bundle.getString(Constant.EDUCATION_EDIT_IDNUM);
                        String editVIP = bundle.getString(Constant.EDUCATION_EDIT_VIP);

                        txtName.setText(editName);
                        txtIdNumber.setText(editIDNum);
                        txtVipNumber.setText(editVIP);

                        //添加地址隐藏
                        setBtnProveInfo(false);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 添加地址按钮处理
     */
    private void setBtnProveInfo(boolean flag) {
        if (flag) {
            //添加地址可见
            imageIcon.setVisibility(View.VISIBLE);
            txtAddProveInfo.setVisibility(View.VISIBLE);

            linearIdnum.setVisibility(View.GONE);
            linearNameVip.setVisibility(View.GONE);
        } else {
            //添加地址不可见

            imageIcon.setVisibility(View.GONE);
            txtAddProveInfo.setVisibility(View.GONE);

            linearIdnum.setVisibility(View.VISIBLE);
            linearNameVip.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.btn_proveInfo, R.id.btn_next})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.btn_proveInfo:
                //TODO 添加证明信息
                Intent intent = new Intent(this, CPEProveInformationActivity.class);
                //TODO 添加地址
                bundle.putBoolean(Constant.EDUCATION_EDIT, true);
                bundle.putString(Constant.EDUCATION_EDIT_NAME, txtName.getText().toString().trim());
                bundle.putString(Constant.EDUCATION_EDIT_IDNUM, txtIdNumber.getText().toString().trim());
                bundle.putString(Constant.EDUCATION_EDIT_VIP, txtVipNumber.getText().toString().trim());
                intent.putExtras(bundle);
                startActivityForResult(intent, Constant.REQUEST_PROVEINFO);
                break;
            case R.id.btn_next:
                //TODO 下一步
                break;
            default:
                //TODO nothing
                break;
        }
    }
}
