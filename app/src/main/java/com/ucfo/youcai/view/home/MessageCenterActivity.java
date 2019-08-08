package com.ucfo.youcai.view.home;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucfo.youcai.R;
import com.ucfo.youcai.base.BaseActivity;
import com.ucfo.youcai.utils.sharedutils.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-8-8 上午 10:32
 * Package: com.ucfo.youcai.view.home
 * FileName: MessageCenterActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 消息中心
 */

public class MessageCenterActivity extends BaseActivity {

    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.showline)
    View showline;
    @BindView(R.id.notice)
    ImageView notice;
    @BindView(R.id.text_noticeContent)
    TextView textNoticeContent;
    @BindView(R.id.ben_notice)
    LinearLayout benNotice;
    @BindView(R.id.notification)
    ImageView notification;
    @BindView(R.id.text_notificationContent)
    TextView textNotificationContent;
    @BindView(R.id.ben_notifation)
    LinearLayout benNotifation;
    private MessageCenterActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_message_center;
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

        titlebarMidtitle.setText(getResources().getString(R.string.message_center));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
    }

    @OnClick({R.id.ben_notice, R.id.ben_notifation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ben_notice:
                //网校公告
                startActivity(AnnouncementCenterActivity.class, null);
                break;
            case R.id.ben_notifation:
                //系统消息
                startActivity(NotificationCenterActivity.class, null);
                break;
            default:
                break;
        }
    }
}
