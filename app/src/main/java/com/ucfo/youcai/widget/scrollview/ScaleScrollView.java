package com.ucfo.youcai.widget.scrollview;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Author:AND
 * Time: 2019-3-6.  上午 9:52
 * Email:2911743255@qq.com
 * ClassName: ScaleScrollView
 * Package: com.ucfo.youcai.utils.scrollview
 * Description:todo 实现页面顶部下拉、底部上拉 沿着Y轴缩放效果
 * Detail:
 */
public class ScaleScrollView extends ScrollView {

    float lastX;
    float lastY;
    String TAG = "ScaleScrollView";
    boolean isScale = false;
    float mScale = 1.0f;
    float scaleRatio = 0.7f;


    public ScaleScrollView(Context context, AttributeSet attrs) {

        super(context, attrs);

    }

    @Override

    public boolean dispatchTouchEvent(MotionEvent ev) {

        float x = ev.getX();

        float y = ev.getY();

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:

                lastX = x;

                lastY = y;

                break;

            case MotionEvent.ACTION_MOVE:


                if (getScrollY() == 0 && y - lastY > 0) {

                    //向下拉动

                    float distance = y - lastY;

                    int height = getHeight();

                    mScale = 1 + distance * scaleRatio / height;

                    setPivotY(0f);

                    setPivotX(getWidth() / 2);

                    ViewCompat.setScaleY(this, mScale);

                    isScale = true;

                } else {

                    //滑动到最底部 向上拉

                    int childHeight = getChildAt(0).getHeight();

                    int height = getHeight();

                    if (getScrollY() >= childHeight - height && y - lastY < 0) {

                        float distance = y - lastY;

                        mScale = 1 - scaleRatio * distance / height;

                        setPivotY(getHeight());

                        setPivotX(getWidth() / 2);

                        ViewCompat.setScaleY(this, mScale);

                        isScale = true;

                    }

                }

                break;

            case MotionEvent.ACTION_UP:

                if (isScale) {

                    ObjectAnimator animator = ObjectAnimator.ofFloat(this, "scaleY", mScale, 1.0f);

                    animator.setDuration(300);

                    animator.start();

                    isScale = false;

                }

                break;

        }

        return super.dispatchTouchEvent(ev);

    }


}


