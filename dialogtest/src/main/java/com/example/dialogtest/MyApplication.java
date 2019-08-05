package com.example.dialogtest;

import android.app.Application;

import org.litepal.LitePal;

/**
 * Author: AND
 * Time: 2019-7-8.  上午 10:47
 * Package: com.example.dialogtest
 * FileName: MyApplication
 * Description:TODO
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化
        LitePal.initialize(this);
    }
}
