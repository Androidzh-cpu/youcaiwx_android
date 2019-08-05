package com.example.dialogtest;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.dialogtest.customview.HorizontalProgressBar;

public class Main3Activity extends AppCompatActivity {

    private HorizontalProgressBar mProgressbar;
    private TextView mTextview;
    private SeekBar mSeekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();
        initData();
    }

    private void initData() {
        mProgressbar.setProgressWithAnimation(50);
        mProgressbar.setProgressTips(getResources().getString(R.string.progressbarIndetior, String.valueOf(5)));


        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTextview.setText(String.valueOf(progress));
                int quotaWidth = mTextview.getWidth();
                final Drawable thumb = seekBar.getThumb();
                //获取thumb的位置
                final Rect bounds = thumb.getBounds();
                //thumb的位置为canvas的相对位置，减去thumb和textview的差值的一半，另外还要加上SeekBar相对屏幕距离
                mTextview.setX((thumb.getIntrinsicWidth() - quotaWidth) / 2 + bounds.left + seekBar.getX());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void initView() {
        mProgressbar = (HorizontalProgressBar) findViewById(R.id.progressbar);
        mTextview = (TextView) findViewById(R.id.textview);
        mSeekbar = (SeekBar) findViewById(R.id.seekbar);
    }
}
