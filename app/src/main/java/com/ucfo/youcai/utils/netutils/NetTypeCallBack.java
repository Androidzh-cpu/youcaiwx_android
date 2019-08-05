package com.ucfo.youcai.utils.netutils;

/**
 * Author:29117
 * Time: 2019-3-28.  上午 10:42
 * Email:2911743255@qq.com
 * ClassName: NetTypeCallBack
 * Package: com.ucfo.youcai.utils.netutils
 * Description:网络状态变化回调
 * Detail:
 */
public interface NetTypeCallBack {

    /**
     * 网络状态发生改变的时候，调用此接口
     *
     * @param type 当前网络状态
     */
    void onNetChange(NetworkUtils.NetworkType type);
}
