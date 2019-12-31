package com.ucfo.youcaiwx.module.user.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
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
    TextView textAddProveInfo;
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

    @Override
    protected int setContentView() {
        return R.layout.activity_apply_for;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.btn_proveInfo, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_proveInfo:
                //TODO 添加证明信息
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
