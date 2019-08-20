package com.ucfo.youcaiwx.utils.socket;

import okhttp3.WebSocket;
import okio.ByteString;

/**
 * Author: AND
 * Time: 2019-6-28.  下午 2:11
 * FileName: IWsManager
 */
public interface IWsManager {
    WebSocket getWebSocket();

    void startConnect();

    void stopConnect();

    boolean isWsConnected();

    int getCurrentStatus();

    void setCurrentStatus(int currentStatus);

    boolean sendMessage(String msg);

    boolean sendMessage(ByteString byteString);
}
