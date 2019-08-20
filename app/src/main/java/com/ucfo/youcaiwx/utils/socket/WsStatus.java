package com.ucfo.youcaiwx.utils.socket;

/**
 * Author: AND
 * Time: 2019-6-28.  下午 2:10
 * Package: com.ucfo.youcai.utils.socket
 * FileName: WsStatus
 * Description:TODO
 * Detail:TODO
 */
public class WsStatus {
    public final static int CONNECTED = 1;

    public final static int CONNECTING = 0;

    public final static int RECONNECT = 2;

    public final static int DISCONNECTED = -1;

    class CODE {
        public final static int NORMAL_CLOSE = 1000;

        public final static int ABNORMAL_CLOSE = 1001;
    }

    class TIP {
        public final static String NORMAL_CLOSE = "normal close";

        public final static String ABNORMAL_CLOSE = "abnormal close";
    }
}
