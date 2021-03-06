package com.ucfo.youcaiwx;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.module.login.LoginActivity;
import com.ucfo.youcaiwx.utils.LogUtils;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

import java.util.Map;

/**
 * Author: AND
 * Time: 2019-8-11.  下午 2:00
 * FileName: UmengPushIntentService
 * Description:友盟消息自定义处理
 */
public class UmengPushIntentService extends UmengMessageService {
    private String messageType = "";
    private Map<String, String> extra = null;

    public UmengPushIntentService() {
    }

    @Override
    public void onMessage(Context context, Intent intent) {
        try {
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));
            LogUtils.e("Umeng:--------------onMessage:" + message);

            Intent intentAct = new Intent();

            if (msg != null) {
                extra = msg.extra;
            }
            if (extra != null) {
                messageType = extra.get(Constant.TYPE);
                if (TextUtils.equals(messageType, Constant.UMENG_MESSAGE_FORCE)) {
                    intentAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intentAct.setClass(context, LoginActivity.class);
                    startActivity(intentAct);
                }
            }
            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
        } catch (Exception e) {
            LogUtils.e("UmengPushIntentService----------Exception: " + e.getMessage());
        }
    }

}
