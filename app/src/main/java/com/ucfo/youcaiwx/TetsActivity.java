package com.ucfo.youcaiwx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ucfo.youcaiwx.utils.systemutils.StatusBarUtil;

/**
 * Author: AND
 * Time: 2019-12-2 下午 2:02
 * Package: com.ucfo.youcaiwx
 * FileName: TetsActivity
 * ORG: www.youcaiwx.com
 * Description:我也不知道为啥我会弄出这个页面,闹着玩?
 */
public class TetsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_learncenter);
        StatusBarUtil.immersive(this);
        //阿斯顿福建凹设计费
    }

}
