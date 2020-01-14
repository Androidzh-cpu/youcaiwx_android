package com.ucfo.youcaiwx.utils.contextutils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Author: AND
 * Time: 2019-11-29.  上午 10:31
 * Package: com.ucfo.youcaiwx.utils.contextutils
 * FileName: ContextProvider
 */
public class ContextProvider {
    @SuppressLint("StaticFieldLeak")
    private static volatile ContextProvider instance;
    private Context mContext;

    private ContextProvider(Context context) {
        mContext = context;
    }

    /**
     * 获取实例
     */
    public static ContextProvider get() {
        if (instance == null) {
            synchronized (ContextProvider.class) {
                if (instance == null) {
                    //重点在这里，ContextProvider.context获取Context
                    if (ApplicationContextProvider.mContext == null) {
                        throw new IllegalStateException("context == null");
                    }
                    instance = new ContextProvider(ApplicationContextProvider.mContext);
                }
            }
        }
        return instance;
    }

    /**
     * 获取上下文
     */
    public Context getContext() {
        return mContext;
    }

    public Application getApplication() {
        return (Application) mContext.getApplicationContext();
    }
}
