package com.ucfo.youcaiwx.module.integral;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.module.course.player.adapter.CommonTabAdapter;
import com.ucfo.youcaiwx.module.integral.fragment.ExchangeRecordFragment;
import com.ucfo.youcaiwx.module.integral.fragment.IntegralSubsidiaryFragment;
import com.ucfo.youcaiwx.module.integral.fragment.ProductListFragment;
import com.ucfo.youcaiwx.utils.ShareUtils;
import com.ucfo.youcaiwx.widget.dialog.ShareDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-9-19 上午 9:08
 * Package: com.ucfo.youcaiwx.module.integral
 * FileName: MineIntegralActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 我的积分首页
 */
public class MineIntegralActivity extends BaseActivity {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.showline)
    View showline;
    @BindView(R.id.text_remain)
    TextView textRemain;
    @BindView(R.id.xTablayout)
    XTabLayout xTablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.btn_invite)
    Button btnInvite;
    @BindView(R.id.btn_getintegral)
    Button btnGetintegral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_mine_integral;
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
        titlebarMidtitle.setText(getResources().getString(R.string.mine_integral));
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
    protected void initData() {
        super.initData();

        ArrayList<String> titlesList = new ArrayList<String>();
        ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>();

        titlesList.add(getResources().getString(R.string.integral_ProductList));
        titlesList.add(getResources().getString(R.string.integral_exchange));
        titlesList.add(getResources().getString(R.string.integral_subsidiary));

        fragmentArrayList.add(new ProductListFragment());
        fragmentArrayList.add(new ExchangeRecordFragment());
        fragmentArrayList.add(new IntegralSubsidiaryFragment());


        FragmentManager supportFragmentManager = getSupportFragmentManager();
        CommonTabAdapter commonTabAdapter = new CommonTabAdapter(supportFragmentManager, fragmentArrayList, titlesList);
        viewpager.setAdapter(commonTabAdapter);//viewpager设置适配器
        xTablayout.setupWithViewPager(viewpager);//tablayout关联viewpager
    }

    @OnClick({R.id.btn_invite, R.id.btn_getintegral})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_invite:
                //TODO 邀请朋友
                new ShareDialog(this).builder()
                        .setFriendButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String url = ApiStores.APP_DOWNLOAD_URL;
                                String title = getResources().getString(R.string.app_nameWX);
                                String desc = getResources().getString(R.string.youcaiWXShareDescribe);
                                String iamgeurl = ApiStores.LOGO;
                                ShareUtils.getInstance().shareUrlToWx(url, title, desc, iamgeurl, SendMessageToWX.Req.WXSceneSession);
                            }
                        })
                        .setCircleToFriendButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String url = ApiStores.APP_DOWNLOAD_URL;
                                String title = getResources().getString(R.string.app_nameWX);
                                String desc = getResources().getString(R.string.youcaiWXShareDescribe);
                                String iamgeurl = ApiStores.LOGO;
                                ShareUtils.getInstance().shareUrlToWx(url, title, desc, iamgeurl, SendMessageToWX.Req.WXSceneTimeline);
                            }
                        })
                        .show();

                break;
            case R.id.btn_getintegral:
                //TODO 赚积分
                //startActivity(EarnIntegralActivity.class, null);
                break;
        }
    }
}
