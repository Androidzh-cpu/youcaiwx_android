package com.ucfo.youcaiwx.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.text.TextUtils;

import com.ucfo.youcaiwx.utils.netutils.NetTypeCallBack;
import com.ucfo.youcaiwx.utils.netutils.NetworkUtils;

/**
 * Author:29117
 * Time: 2019-3-28.  上午 10:50
 * Email:2911743255@qq.com
 * ClassName: NetworkReceiver
 * Description:注册广播接收网络转台的变化
 * Detail:
 */

public class NetworkReceiver extends BroadcastReceiver {

    private NetTypeCallBack netType;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(intent.getAction(), ConnectivityManager.CONNECTIVITY_ACTION)) {

            //检查网络状态的类型
            netType.onNetChange(NetworkUtils.getNetworkType());
        }
    }

    public void setNetType(NetTypeCallBack netType) {
        this.netType = netType;
    }
}
