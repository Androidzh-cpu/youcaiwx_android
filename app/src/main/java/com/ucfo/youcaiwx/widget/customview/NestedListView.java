package com.ucfo.youcaiwx.widget.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Author: AND
 * Time: 2019-5-17.  下午 3:12
 * FileName: NestedListView
 * Description:TODO NestedScrollView嵌套ListView时只显示一行的解决方法
 */
public class NestedListView extends ListView {
    public NestedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量的大小由一个32位的数字表示，前两位表示测量模式，后30位表示大小，这里需要右移两位才能拿到测量的大小
        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
