package com.ucfo.youcaiwx.widget.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Author: AND
 * Time: 2019-5-20.  下午 7:23
 * FileName: NestedGridView
 * Description:TODO 禁止滑动gridlistview
 */
public class NestedGridView extends GridView {

    public NestedGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedGridView(Context context) {
        super(context);
    }

    public NestedGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
