package com.ucfo.youcai;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ucfo.youcai.utils.systemutils.StatusBarUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TetsActivity extends AppCompatActivity {
    CircleImageView ivUserPhoto;
    private static ArrayList<String> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_learncenter);
        StatusBarUtil.immersive(this);
    }

}
