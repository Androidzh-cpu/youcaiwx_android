package com.ucfo.youcaiwx.service;

import com.ucfo.youcaiwx.utils.LogUtils;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Author:29117
 * Time: 2019-3-19.  上午 9:05
 * Email:2911743255@qq.com
 * ClassName: EchoWebSocketListener
 * Description:TODO 使用okhttp创建一个socket用于和服务端保持长连接
 * Detail:
 */
public final class EchoWebSocketListener extends WebSocketListener {

    private WebSocket mSocket;
    private int POSTDELAY = 2000;

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        //TODO 建立连接成功后，发送消息给服务器端
        this.mSocket = webSocket;
        String message = "优财网校android端";
        //TODO socket 发送信息到服务器
        mSocket.send(message);
        output("连接成功！");
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        super.onMessage(webSocket, bytes);
        //如果服务器传递的是byte类型的
        String msg = bytes.utf8();
        String hex = bytes.hex();
        output("receive bytes:" + hex);
        output("receive bytes:" + msg);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        //TODO 获取到服务器发送过来的信息，然后通过handler进行UI线程的操作
        output("receive text:" + text);
        //收到服务器端发送来的信息后，每隔2秒发送一次心跳包
        final String message = "{\"type\":\"heartbeat\",\"user_id\":\"heartbeat\"}";
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mSocket.send(message);
            }
        }, POSTDELAY);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
        output("closed:" + reason);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        super.onClosing(webSocket, code, reason);
        output("closing:" + reason);
    }

    /**
     * 连接失败
     */
    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        output("failure:" + t.getMessage());
    }

    private void output(String text) {
        LogUtils.e("优财网校socket: " + text);
    }

}
