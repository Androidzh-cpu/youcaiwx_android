package com.ucfo.youcaiwx;

import android.os.Bundle;
import android.widget.Button;

import com.ucfo.youcaiwx.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: AND
 * Time: 2019-12-2 下午 2:02
 * Package: com.ucfo.youcaiwx
 * FileName: TetsActivity
 * ORG: www.youcaiwx.com
 * Description:我也不知道为啥我会弄出这个页面,闹着玩?
 */
public class TetsActivity extends BaseActivity {
    @BindView(R.id.iv_userphoto_main)
    CircleImageView ivUserphotoMain;
    @BindView(R.id.camera)
    Button camera;
    @BindView(R.id.album)
    Button album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_tets;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //ButterKnife.bind(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

}
