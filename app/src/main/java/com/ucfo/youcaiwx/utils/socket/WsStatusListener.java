package com.ucfo.youcaiwx.utils.socket;

import okhttp3.Response;
import okio.ByteString;

/**
 * Author: AND
 * Time: 2019-6-28.  下午 2:13
 * Package: com.ucfo.youcai.utils.socket
 * FileName: WsStatusListener
 * Description:TODO
 * Detail:TODO
 */
public abstract class WsStatusListener {
    public void onOpen(Response response) {

    }



    public void onMessage(String text) {

    }



    public void onMessage(ByteString bytes) {

    }



    public void onReconnect() {



    }



    public void onClosing(int code, String reason) {

    }





    public void onClosed(int code, String reason) {

    }



    public void onFailure(Throwable t, Response response) {

    }
}
