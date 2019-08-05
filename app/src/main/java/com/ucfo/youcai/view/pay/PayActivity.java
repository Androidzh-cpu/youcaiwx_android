package com.ucfo.youcai.view.pay;

import android.os.Bundle;

import com.ucfo.youcai.R;
import com.ucfo.youcai.base.BaseActivity;

public class PayActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_pay;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
