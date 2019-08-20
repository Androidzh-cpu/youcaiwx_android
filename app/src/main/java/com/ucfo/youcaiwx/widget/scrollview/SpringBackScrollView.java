package com.ucfo.youcaiwx.widget.scrollview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Author:AND
 * Time: 2019-3-6.  上午 9:45
 * Email:2911743255@qq.com
 * ClassName: SpringBackScrollView
 * Description:TODO 有回弹效果的ScrollView
 * Detail:
 */
public class SpringBackScrollView extends ScrollView {


    private float mDownY;
    private float mFirstY;

    // 子View
    private View childView;

    // 初始的位置
    private Rect normal = new Rect();

    private Handler mHandler = new Handler();

    private int speed = 30;

    private boolean isPull;

    public SpringBackScrollView(Context context) {
        super(context);
        init();
    }

    private void init() {
        // 禁用下拉到两端发荧光的效果
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public SpringBackScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 设置回弹的速度。值越大,速度越快。默认为30。
     */
    public void setSpringBackSpeed(int speed) {
        if (speed <= 0) {
            throw new RuntimeException("speed 不能小于或者等于0");
        }
        this.speed = speed;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onFinishInflate() {
        childView = getChildAt(0);
        if (childView != null) {
            normal.set(childView.getLeft(), childView.getTop(), childView.getRight(), childView.getBottom());
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (childView != null) {
            handleScrollTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    public void handleScrollTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                mFirstY = ev.getY();
                break;

            case MotionEvent.ACTION_UP:
                springBackLocation();
                break;

            case MotionEvent.ACTION_MOVE:
                // 移除滑动的消息队列
                mHandler.removeCallbacksAndMessages(null);

                final float preY = mDownY;
                final float nowY = ev.getY();

                isPull = nowY - mFirstY > 0;

                int deltaY = (int) ((preY - nowY) / 2.5);

                mDownY = nowY;

                // 当滚动到最上或者最下时就不会再滚动，这时移动布局
                if (isNeedMove()) {
                    // 保存正常的布局位置
                    if (normal.isEmpty()) {
                        normal.set(childView.getLeft(), childView.getTop(), childView.getRight(), childView.getBottom());
                        return;
                    }
                    // 移动布局
                    childView.layout(childView.getLeft(), childView.getTop() - deltaY,
                            childView.getRight(), childView.getBottom() - deltaY);
                }

                break;

            default:
                break;
        }

    }

    /**
     * 回弹到原始位置
     */
    public void springBackLocation() {
        final int nowTop = childView.getTop();
        final int nowBottom = childView.getBottom();
        final int originTop = normal.top;
        final int originBottom = normal.bottom;

        Log.i("nsz", "nowTop:" + nowTop + " nowBottom:" + nowBottom
                + " originTop:" + originTop + " originBottom:" + originBottom);

        // 下拉回弹
        if (isPull) {
            int moveTop = nowTop;
            int moveBottom = nowBottom;
            int duration = 0;

            while (moveTop >= originTop) {
                moveTop -= speed;
                moveBottom -= speed;
                duration += 10;
                final int offTop = moveTop;
                final int offBottom = moveBottom;

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (offTop <= originTop || offBottom <= originBottom) {
                            childView.layout(normal.left, normal.top, normal.right, normal.bottom);
                        } else {
                            childView.layout(normal.left, offTop, normal.right, offBottom);
                        }
                    }

                }, duration);
            }

        }
        // 上拉回弹
        else {
            int moveTop = nowTop;
            int moveBottom = nowBottom;
            int duration = 0;

            while (moveTop <= originTop) {
                moveTop += speed;
                moveBottom += speed;
                duration += 10;
                final int offTop = moveTop;
                final int offBottom = moveBottom;

                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (offTop >= originTop || offBottom >= originBottom) {
                            childView.layout(normal.left, normal.top, normal.right, normal.bottom);
                        } else {
                            childView.layout(normal.left, offTop, normal.right, offBottom);
                        }
                    }

                }, duration);

            }

        }

    }

    /**
     * 是否需要移动布局
     */
    public boolean isNeedMove() {
        // 注意：慎重选择
        // 子View的margin和自己的padding对移动有影响,所以子View最好不要设置marginTop和marginBottom。
        // 如果设置了，对判断滑动到底部有些不准确，需要加上下面注释掉margin值，但是不同的机器，测试出有点不一样。

        // 获取到子View的margin值
        // LayoutParams params = (LayoutParams) childView.getLayoutParams();
        // int topMargin = params.topMargin;
        // int bottomMargin = params.bottomMargin;
        // int offset = childView.getHeight() - getHeight() + getPaddingBottom() + getPaddingTop() + topMargin + bottomMargin;
        int offset = childView.getHeight() - getHeight() + getPaddingBottom() + getPaddingTop();
        int scrollY = getScrollY();

        if (scrollY == 0) {
            return true;
        } else if (scrollY == offset) {
            return true;
        }

        return false;
    }

}
