package com.ucfo.youcaiwx.utils.socket;

import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.utils.LogUtils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Author: AND
 * Time: 2019-6-28.  下午 3:01
 * FileName: FirstTest
 */
public class FirstTest {

    private OkHttpClient mOkHttpClient;
    private Request request;
    private boolean isConnect;
    private Timer socketTimer;

    /**
     * Description:MainActivity
     * Time:2019-4-23   上午 10:52
     * Detail: TODO 开始socket服务
     */
    private void startWebSocket() {
        mOkHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)//允许失败重试
                .readTimeout(60, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(60, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(60, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        request = new Request.Builder().url(ApiStores.SOCKET).build();
        EchoWebSocketListener socketListener = new EchoWebSocketListener();
        mOkHttpClient.newWebSocket(request, socketListener);
        mOkHttpClient.dispatcher().executorService().shutdown();//内存不足时释放
    }

    public class EchoWebSocketListener extends WebSocketListener {
        private int POSTDELAY = 1000 * 60;
        private WebSocket mSocket;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            mSocket = webSocket;
            isConnect = response.code() == 101;
            //连接成功后，发送信息
            String msg = "youcaiSocketSendMessage";
            //TODO socket 发送信息到服务器
            mSocket.send(msg);
            output("onOpen successfully " + response.code());
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
            //如果服务器传递的是byte类型的
            String msg = bytes.utf8();
            String hex = bytes.hex();
            output("receive bytes  hex:" + hex + "" +
                    "    utf8:" + msg);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            //TODO 获取到服务器发送过来的信息，然后通过handler进行UI线程的操作
            output("receive onMessage:" + text);
            /*---------------------------------再丑也要看的分割线-------------------------------*/
            //TODO 收到服务器端发送来的信息后，每隔一段时间发送一次心跳包
            if (socketTimer == null) {
                socketTimer = new Timer();
            }
            socketTimer.schedule(new TimerTask() {
                @Override
                public void run() {
//                    sendSocketMessage();
                }
            }, POSTDELAY);

        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            output("onClosed: " + reason);
            mSocket = null;
            isConnect = false;
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
            output("onClosing: " + reason);
            mSocket = null;
            isConnect = false;
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
            output("onFailure: " + t.getMessage());
            isConnect = false;
        }
    }

    private void output(String text) {
        LogUtils.e("socket---" + text);
    }
}
