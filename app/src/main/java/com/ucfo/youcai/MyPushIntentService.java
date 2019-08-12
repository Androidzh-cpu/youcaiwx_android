package com.ucfo.youcai;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.utils.LogUtils;
import com.ucfo.youcai.view.login.LoginActivity;
import com.ucfo.youcai.view.main.activity.WebActivity;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

import java.util.Map;

/**
 * Author: AND
 * Time: 2019-8-11.  下午 2:00
 * Package: com.ucfo.youcai
 * FileName: MyPushIntentService
 * Description:TODO 友盟推送服务
 */
public class MyPushIntentService extends UmengMessageService {
    private String messageType = "";
    private String parameter;

    @Override
    public void onMessage(Context context, Intent intent) {
        try {
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));
            LogUtils.e("MyPushIntentService------ummessage:" + message);

            Map<String, String> extra = msg.extra;
            LogUtils.e("MyPushIntentService------msg.extra:" + extra);
            Intent intentAct = new Intent();
            if (extra != null) {
                messageType = extra.get("jumptype");
                if (messageType.equals("1000")) {
                    //强制下线
                    intentAct.setClass(context, LoginActivity.class);
                } else if (messageType.equals("newsMessage")) {
                    //资讯详情 传 id
                    parameter = extra.get("webUrl");
                    String title = extra.get("title");
                    intentAct.putExtra(Constant.WEB_URL, parameter);
                    intentAct.putExtra(Constant.WEB_TITLE, title);
                    intentAct.setClass(context, WebActivity.class);
                } else if (messageType.equals("arenaQueDetail")) {
                    //题库答疑详情 传 id
                    parameter = extra.get("id");
                } else if (messageType.equals("courseQueDetail")) {
                    //课程答疑详情 传 id
                    parameter = extra.get("id");
                } else if (messageType.equals("messageDetail")) {
                    //系统消息列表页 id
                }
                intentAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            //展示通知
            showNotifications(context, msg, intentAct);
            // 对完全自定义消息的处理方式，点击或者忽略
            boolean isClickOrDismissed = true;
            if (isClickOrDismissed) {
                //完全自定义消息的点击统计
                UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
            } else {
                //完全自定义消息的忽略统计
                UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
            }

        } catch (Exception e) {
            //UmLog.e(TAG, e.getMessage());
            LogUtils.e("MyPushIntentService----------Exception: " + e.getMessage());
        }
    }


    /**
     * 自定义通知布局
     */
    public void showNotifications(Context context, UMessage msg, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LogUtils.e("MyPushIntentService------Android8.0+通知");
            String channelId = "subscribe";
            String channelName = "优财公告";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription("优财公告");
            channel.enableLights(true);//角标通知
            channel.setLightColor(Color.RED);//小红点颜色
            channel.setShowBadge(true);//是否在久按桌面图标时显示此渠道的通知
            //通知管理器
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            //创建通知渠道
            notificationManager.createNotificationChannel(channel);

            PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            //创建通知栏
            Notification notification = new Notification.Builder(this)
                    .setChannelId(channelId)
                    .setContentTitle(msg.title)
                    .setContentText(msg.text)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.app_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.app_icon))
                    .setColor(Color.parseColor("#41b5ea"))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .setContentIntent(contentIntent)
                    .build();
            notificationManager.notify(1, notification);
        } else {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(msg.title)
                    .setContentText(msg.text)
                    .setTicker(msg.ticker)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.app_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.app_icon))
                    .setColor(Color.RED)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
            mNotificationManager.notify(2, builder.build());
        }
    }
}
