package com.ucfo.youcai.widget.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.tencent.smtt.export.external.interfaces.ConsoleMessage;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.ucfo.youcai.R;
import com.ucfo.youcai.utils.systemutils.DensityUtil;

/**
 * Author:29117
 * Time: 2019-3-27.  下午 4:02
 * Email:2911743255@qq.com
 * ClassName: TencentWebview
 * Package: com.ucfo.youcai.utils.customview
 * Description:TODO  带有加载进度的webview
 * Detail:
 */
public class TencentWebview extends WebView {
    private ContentLoadingProgressBar progressView;//进度条
    private Context context;

    public TencentWebview(Context context) {
        this(context, null);
    }

    public TencentWebview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TencentWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @SuppressLint("WrongConstant")
    private void init() {
        //初始化进度条
        progressView = new ContentLoadingProgressBar(context);
        progressView.setScrollBarStyle(android.R.attr.progressBarStyleHorizontal);
        progressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(context, 3)));

        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.web_progress_bar_states);
        progressView.setProgressDrawable(drawable);


        //把进度条加到Webview中
        addView(progressView);
        //初始化设置
        setWebChromeClient(new MyWebCromeClient());
    }

    private class MyWebCromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                //加载完毕进度条消失
                progressView.setVisibility(View.GONE);
            } else {
                //更新进度
                if (progressView.getVisibility() == GONE)
                    progressView.setVisibility(VISIBLE);
                progressView.setProgress(newProgress);

            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return super.onConsoleMessage(consoleMessage);
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }

        @Override
        public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
            return super.onJsBeforeUnload(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public void onCloseWindow(WebView window) {
            super.onCloseWindow(window);
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }
    }

}
