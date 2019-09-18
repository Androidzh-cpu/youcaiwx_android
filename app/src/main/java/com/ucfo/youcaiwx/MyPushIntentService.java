package com.ucfo.youcaiwx;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.utils.LogUtils;
import com.ucfo.youcaiwx.module.main.activity.WebActivity;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

import java.util.Map;

/**
 * Author: AND
 * Time: 2019-8-11.  下午 2:00
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
            LogUtils.e("Umeng:--------------onMessage:" + message);

            Map<String, String> extra = msg.extra;
            Gson gson = new Gson();
            String jsonImgList = gson.toJson(extra);
            LogUtils.e("Umeng:--------------onMessage:" + jsonImgList);
            Intent intentAct = new Intent();
            Bundle bundle = new Bundle();
            if (extra != null) {
                messageType = extra.get(Constant.TYPE);
                if (TextUtils.equals(messageType, Constant.UMENG_MESSAGE_INFORMATION)) {
                    intent.setClass(context, WebActivity.class);
                    String webUrl = extra.get(Constant.VALUE);
                    String title = extra.get(Constant.TITLE);
                    bundle.putString(Constant.WEB_URL, webUrl);
                    bundle.putString(Constant.WEB_TITLE, title);
                }
                intentAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtras(bundle);
            }
            //展示通知
            showNotifications(context, msg, intentAct);
            // 对完全自定义消息的处理方式，点击或者忽略
            //完全自定义消息的点击统计
            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);

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
