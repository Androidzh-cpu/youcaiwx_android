package com.ucfo.youcai.utils.baseadapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author:29117
 * Time: 2019-3-18.  上午 11:03
 * Email:2911743255@qq.com
 * ClassName: SpacesItemDecorationEntrust
 * Package: com.ucfo.youcai.utils.baseadapter
 * Description:
 * Detail:
 */
public abstract class SpacesItemDecorationEntrust {

    //color的传入方式是resouce.getcolor
    protected Drawable mDivider;

    protected int leftRight;

    protected int topBottom;

    public SpacesItemDecorationEntrust(int leftRight, int topBottom, int mColor) {
        this.leftRight = leftRight;
        this.topBottom = topBottom;
        if (mColor != 0) {
            mDivider = new ColorDrawable(mColor);
        }
    }

    abstract void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state);

    abstract void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state);
}
