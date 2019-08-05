package com.ucfo.youcai.widget.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;

/**
 * Author:29117
 * Time: 2019-4-4.  下午 2:51
 * Email:2911743255@qq.com
 * ClassName: NoScrollWebView
 * Package: com.ucfo.youcai.utils.customview
 * Description:TODO 不会滑动的webview
 * Detail:TODO
 */
public class NoScrollWebView extends WebView {
    public NoScrollWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NoScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollWebView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
