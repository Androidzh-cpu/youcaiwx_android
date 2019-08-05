package com.example.dialogtest.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

/**
 * Author: AND
 * Time: 2019-7-29.  下午 6:48
 * Package: com.example.dialogtest.customview
 * FileName: TextSeekBar
 * Description:TODO
 * Detail:TODO
 */
@SuppressLint("AppCompatCustomView")
public class TextSeekBar extends SeekBar {
    /**
     * 文本的颜色
     */
    private int mTitleTextColor = Color.TRANSPARENT;
    /**
     * 文本的大小
     */
    private float mTitleTextSize = 18;
    /**
     * 文字的内容
     */
    private String mTitleText;
    /**
     * 文字距离进度条的距离
     */
    float mTextY = 18;
    /**
     * 得到拖快的宽度
     */
    private int mThumbWidth;
    /**
     * 得到进度条的宽度
     */
    private int mSeekWidth;
    /**
     * 画文字画笔
     */
    private Paint mPaint;

    /**
     * 文本偏差值
     */
    private int mDeviationValue = 0;

    public TextSeekBar(Context context) {
        super(context);
        initView();
    }

    public TextSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public TextSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        initPaint();
    }


    private void initPaint() {
        this.mPaint = new Paint();
        // 设置抗锯齿
        this.mPaint.setAntiAlias(true);
        this.mPaint.setTextSize(mTitleTextSize);
        // 这里设置40是因为拖快的大小是80
        setPadding(40, 0, 40, 0);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            // 得到拖快的宽
            Rect rectThumb = getThumb().getBounds();
            mThumbWidth = rectThumb.width();
            // 得到进度条的宽
            Rect rectSeek = this.getProgressDrawable().getBounds();
            mSeekWidth = rectSeek.width();
        }
    }

    /**
     * 设置文本偏差值,用来设置显示实际内容和显示内容之间的差
     */
    public void setTextDeviation(int value) {
        mDeviationValue = value;
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 得到写的内容
        mTitleText = getProgress() + mDeviationValue + "";
        // 文字的宽
        float numTextWidth = mPaint.measureText(mTitleText);

        float text_x = mSeekWidth * getProgress() / getMax() + (mThumbWidth - numTextWidth) / 2;

        this.mPaint.setColor(mTitleTextColor);

        canvas.drawText(mTitleText, text_x, mTextY, mPaint);// 画文字
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTitleTextColor = Color.WHITE;
                break;
            case MotionEvent.ACTION_MOVE:
                mTitleTextColor = Color.WHITE;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mTitleTextColor = Color.TRANSPARENT;
                break;

            default:
                break;
        }
        invalidate();
        return super.onTouchEvent(event);
    }
}
