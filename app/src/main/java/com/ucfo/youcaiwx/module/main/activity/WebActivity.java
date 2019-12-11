package com.ucfo.youcaiwx.module.main.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.crash.h5.H5JavaScriptInterface;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.utils.LogUtils;
import com.ucfo.youcaiwx.utils.systemutils.AppUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.customview.TencentWebview;

/**
 * Description:WebActivity
 * Time:2019-3-22   下午 12:01
 * Detail:TODO X5webview H5页面加载
 */
public class WebActivity extends BaseActivity implements View.OnClickListener {

    private String webUrl;
    private WebActivity context;
    private TextView mMidtitleTitlebar;
    private TextView mRighttitleTitlebar;
    private Toolbar mToolbarTitlebar;
    private TencentWebview tencentWebview;
    private String webTitle;
    private ContentLoadingProgressBar progressBar;
    private boolean isLoading = true;//当前页面是否正在加载
    private LoadingLayout mLoadinglayout;
    private boolean isDownload;

    @Override
    protected void onResume() {
        super.onResume();
        tencentWebview.onResume();
        tencentWebview.resumeTimers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //暂停WebView在后台的所有活动
        tencentWebview.onPause();
        //暂停WebView在后台的JS活动
        tencentWebview.pauseTimers();
    }

    @Override
    protected void onDestroy() {
        if (tencentWebview != null) {
            tencentWebview.loadDataWithBaseURL(null, "Loading...", "text/html", "utf-8", null);
            tencentWebview.clearHistory();

            tencentWebview.removeAllViews();
            tencentWebview.destroy();
            tencentWebview = null;
        }
        super.onDestroy();
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_web;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mToolbarTitlebar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbarTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        context = WebActivity.this;

        mMidtitleTitlebar = (TextView) findViewById(R.id.titlebar_midtitle);
        mRighttitleTitlebar = (TextView) findViewById(R.id.titlebar_righttitle);
        mRighttitleTitlebar.setOnClickListener(this);
        mToolbarTitlebar = (Toolbar) findViewById(R.id.titlebar_toolbar);
        tencentWebview = (TencentWebview) findViewById(R.id.webview);
        findViewById(R.id.showline).setVisibility(View.GONE);
        mLoadinglayout = (LoadingLayout) findViewById(R.id.loadinglayout);
    }

    @Override
    protected void initData() {
        super.initData();
        IX5WebViewExtension x5WebViewExtension = tencentWebview.getX5WebViewExtension();
        //如果成功为null表示X5没集成
        LogUtils.e("X5webview-----------:" + x5WebViewExtension);
        //启用硬件加速
        initHardwareAccelerate();
        //webview默认配置
        setDefaultWebSettings(tencentWebview);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            webUrl = bundle.getString(Constant.WEB_URL);//defaultUrl
            webTitle = bundle.getString(Constant.WEB_TITLE);//标题
        } else {
            if(mLoadinglayout != null) {
                mLoadinglayout.showEmpty();
            }
        }
        LogUtils.e("X5webview-----------bundle:" + bundle);
        if (TextUtils.isEmpty(webTitle)) {
            webTitle = getResources().getString(R.string.default_title);
        }
        mMidtitleTitlebar.setText(webTitle);

        if (TextUtils.isEmpty(webUrl)) {
            if(mLoadinglayout != null) {
                mLoadinglayout.showEmpty();
            }
        } else {
            tencentWebview.loadUrl(webUrl);
            //校验链接
            boolean matches = Patterns.WEB_URL.matcher(webUrl).matches();
            if (matches) {
                tencentWebview.loadUrl(webUrl);
            } else {
                //京东支付链接可能验证无法通过
                if (webUrl.contains(ApiStores.PAY_JINGDONG)) {
                    tencentWebview.loadUrl(webUrl);
                } else {
                    ToastUtil.showBottomShortText(this, getResources().getString(R.string.github_qq_browser_urlIllegality));
                    if(mLoadinglayout != null) {
                        mLoadinglayout.showEmpty();
                    }
                }
            }
        }
        //TODO 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        tencentWebview.setWebViewClient(new MyWebviewClient());
        tencentWebview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                if (!isDownload) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse(url);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(uri);
                    startActivity(intent);
                }
                //重置为初始状态
                isDownload = true;
            }
        });
        CrashReport.WebViewInterface webViewInterface = new CrashReport.WebViewInterface() {
            @Override
            public String getUrl() {
                return tencentWebview.getUrl();
            }

            @Override
            public void setJavaScriptEnabled(boolean b) {
                WebSettings webSettings = tencentWebview.getSettings();
                webSettings.setJavaScriptEnabled(b);
            }

            @Override
            public void loadUrl(String s) {
                tencentWebview.loadUrl(s);
            }

            @Override
            public void addJavascriptInterface(H5JavaScriptInterface h5JavaScriptInterface, String s) {
                tencentWebview.addJavascriptInterface(h5JavaScriptInterface, s);
            }

            @Override
            public CharSequence getContentDescription() {
                return tencentWebview.getContentDescription();
            }
        };
        CrashReport.setJavascriptMonitor(webViewInterface, true);

        //重新加载按钮
        mLoadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tencentWebview.reload();
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebar_righttitle:
                // TODO 19/03/22
                break;
            default:
                break;
        }
    }

    /**
     * 返回键监听
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (tencentWebview != null && tencentWebview.canGoBack()) {
                tencentWebview.goBack();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private class MyWebviewClient extends WebViewClient {

        //开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应
        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
        }

        //App里面使用webview控件的时候遇到了诸如404这类的错误的时候，若也显示浏览器里面的那种错误提示页面就显得很丑陋了，
        // 那么这个时候我们的app就需要加载一个本地的错误提示页面，即webview如何加载一个本地的页面
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            if (mLoadinglayout != null) {
                mLoadinglayout.showError();
            }
        }

        //webView默认是不处理https请求的，页面显示空白，需要进行如下设置：
        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            super.onReceivedSslError(webView, sslErrorHandler, sslError);
            sslErrorHandler.proceed();    //表示等待证书响应
            // handler.cancel();      //表示挂起连接，为默认方式
            // handler.handleMessage(null);    //可做其他处理
        }

        //在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
        @Override
        public void onLoadResource(WebView webView, String s) {
            super.onLoadResource(webView, s);
        }

        //在页面加载结束时调用。我们可以关闭loading 条，切换程序动作。
        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
            if (mLoadinglayout != null) {
                mLoadinglayout.showContent();
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtils.d("shouldOverrideUrlLoading--------------defaultUrl:" + url);
            if (!url.startsWith("http")) {
                try {
                    // 以下固定写法,表示跳转到第三方应用
                    final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    isDownload = false;  //该字段是用于判断是否需要跳转浏览器下载
                } catch (Exception e) {
                    // 防止没有安装的情况
                    e.printStackTrace();
                }
                return true;////返回值是true的时候控制去WebView打开
            }
            return false;//为false调用系统浏览器或第三方浏览器
        }
    }

    /**
     * Description:WebActivity
     * Time:2019-3-22   下午 2:56
     * Detail:TODO 参考考拉的配置
     */
    @SuppressLint("SetJavaScriptEnabled")
    public void setDefaultWebSettings(WebView webView) {
        WebSettings webSetting = webView.getSettings();
        //5.0以上开启混合模式加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //设置自适应屏幕，两者合用
        webSetting.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        webSetting.setUseWideViewPort(true);//将图片调整到适合webview的大小
        //允许js代码
        webSetting.setJavaScriptEnabled(true);
        //允许SessionStorage/LocalStorage存储
        webSetting.setDomStorageEnabled(true);
        //禁用放缩
        webSetting.setSupportZoom(true);//手势.点击
        webSetting.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSetting.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        //禁用文字缩放
        webSetting.setTextZoom(100);
        //10M缓存，api 18后，系统自动管理。
        webSetting.setAppCacheMaxSize(10 * 1024 * 1024);
        //允许缓存，设置缓存位置
        webSetting.setAppCacheEnabled(true);
        webSetting.setAppCachePath(getDir("appcache", 0).getPath());
        //允许WebView使用File协议
        webSetting.setAllowFileAccess(true);
        //不保存密码
        webSetting.setSavePassword(false);
        //设置UA
        webSetting.setUserAgentString(webSetting.getUserAgentString() + " youcaiApp/" + AppUtils.getAppVersionName(context));
        //移除部分系统JavaScript接口
        WebActivity.removeJavascriptInterfaces(webView);
        //自动加载图片
        webSetting.setLoadsImagesAutomatically(true);


        webSetting.setSupportMultipleWindows(false);
        webSetting.setGeolocationEnabled(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        webSetting.setPluginsEnabled(true);//支持插件
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);

        // 设置缓存模式
        webSetting.setCacheMode(WebSettings.LOAD_NORMAL);
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0).getPath());


    }


    /**
     * Description:WebActivity
     * Time:2019-3-22   下午 2:57
     * Detail:TODO  如果启用了JavaScript，务必做好安全措施，防止远程执行漏洞
     */
    @TargetApi(11)
    private static void removeJavascriptInterfaces(WebView webView) {
        try {
            if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 17) {
                webView.removeJavascriptInterface("searchBoxJavaBridge_");
                webView.removeJavascriptInterface("accessibility");
                webView.removeJavascriptInterface("accessibilityTraversal");
            }
        } catch (Throwable tr) {
            tr.printStackTrace();
        }
    }

    /**
     * 启用硬件加速
     */
    @SuppressLint("ObsoleteSdkInt")
    private void initHardwareAccelerate() {
        try {
            if (Build.VERSION.SDK_INT >= 11) {
                getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
            String message = e.getMessage();
            LogUtils.e("initHardwareAccelerate--Exception:" + message);
        }
    }

    //这个Chromeclient可以显示在布局中的进度条,不过我写在webview中了,暂时弃用
    private class MyWebChromeClient extends WebChromeClient {
        //每个网页的页面都有一个标题，比如www.baidu.com这个页面的标题即“百度一下，你就知道”，那么如何知道当前webview正在加载的页面的title并进行设置呢？
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                // 网页加载完毕，关闭进度提示
                closeDialog();
                isLoading = true;
            } else {
                if (isLoading) {
                    // 网页正在加载
                    openDialog(newProgress);
                }
                isLoading = false;
            }
        }

        // 关闭进度条
        private void closeDialog() {
            progressBar.hide();
        }

        // 打开进度条，并设置进度条样式
        private void openDialog(int newProgress) {
            progressBar.setProgress(newProgress);
            progressBar.show();
        }

    }


}
