package com.ucfo.youcaiwx.utils.pay;

import android.content.Context;

import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-9-25.  下午 5:46
 * Package: com.ucfo.youcaiwx.utils.pay
 * FileName: PayListenerUtil
 * Description:TODO 通过管理类管理接口
 */
public class PayListenerUtil {
    private static PayListenerUtil instance;
    private Context mContext;

    private final static ArrayList<PayResultListener> resultList = new ArrayList<>();

    private PayListenerUtil(Context context) {
        this.mContext = context;
        //TODO
    }

    public synchronized static PayListenerUtil getInstance(Context context) {
        if (instance == null) {
            instance = new PayListenerUtil(context);
        }
        return instance;
    }

    public void addListener(PayResultListener listener) {
        if (!resultList.contains(listener)) {
            resultList.add(listener);
        }
    }

    public void removeListener(PayResultListener listener) {
        if (resultList.contains(listener)) {
            resultList.remove(listener);
        }
    }

    public void addSuccess() {
        for (PayResultListener listener : resultList) {
            listener.onPaySuccess();
        }
    }

    public void addCancel() {
        for (PayResultListener listener : resultList) {
            listener.onPayCancel();
        }
    }

    public void addError() {
        for (PayResultListener listener : resultList) {
            listener.onPayError();
        }
    }
}
