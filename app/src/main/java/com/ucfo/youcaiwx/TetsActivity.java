package com.ucfo.youcaiwx;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.ucfo.youcaiwx.base.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: AND
 * Time: 2019-12-2 下午 2:02
 * Package: com.ucfo.youcaiwx
 * FileName: TetsActivity
 * ORG: www.youcaiwx.com
 * Description:我也不知道为啥我会弄出这个页面,闹着玩?
 */
public class TetsActivity extends BaseActivity implements View.OnClickListener {
    private CircleImageView mUserphotoMainIv;
    private Button mCamera;
    private Button mAlbum;

    @Override
    protected int setContentView() {
        return R.layout.activity_tets;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //ButterKnife.bind(this);
        mUserphotoMainIv = (CircleImageView) findViewById(R.id.iv_userphoto_main);
        mCamera = (Button) findViewById(R.id.camera);
        mCamera.setOnClickListener(this);
        mAlbum = (Button) findViewById(R.id.album);
        mAlbum.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

        Bitmap bitmap = null;
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", "description");

        // 其次把文件插入到系统图库
        File file = new File("");
        String path = file.getAbsolutePath();
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(), path, file.getName(), file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        sendBroadcast(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera:
                // TODO 19/12/16
                break;
            case R.id.album:
                // TODO 19/12/16
                break;
            default:
                break;
        }
    }
}
