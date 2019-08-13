package com.ucfo.youcai;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.aliyun.vodplayer.downloader.AliyunDownloadConfig;
import com.aliyun.vodplayer.downloader.AliyunDownloadManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.QbSdk;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.utils.JsonUtil;
import com.ucfo.youcai.utils.LogUtils;
import com.ucfo.youcai.utils.netutils.OKHttpUpdateHttpService;
import com.ucfo.youcai.view.course.player.download.Common;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.UpdateError;
import com.xuexiang.xupdate.listener.OnUpdateFailureListener;
import com.xuexiang.xupdate.utils.UpdateUtils;

import org.litepal.LitePal;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Author:AND
 * Time: 2019-3-5.  下午 5:40
 * Email:2911743255@qq.com
 * ClassName: UcfoApplication
 * Package: com.ucfo.youcai.common
 * Description:
 * Detail:程序清单
 */

public class UcfoApplication extends Application {
    public static Context context;
    private static UcfoApplication instance;
    // IWXAPI 是第三方app和微信通信的openApi接口
    public static IWXAPI api;
    public static AliyunDownloadManager downloadManager;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);//启用矢量图兼容
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
                layout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
                layout.setEnableAutoLoadMore(false);//是否启用列表惯性滑动到底部时自动加载更多

                layout.setPrimaryColorsId(R.color.colorWhite, android.R.color.black);//背景色和字体颜色
                layout.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
                layout.setReboundDuration(400);//回弹动画时长（毫秒）
            }
        });
        //设置全局的Header构建器 TODO 1.0.5和1.0.4不一样
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context).setDrawableSize(20).setFinishDuration(1000);
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context).setDrawableSize(20).setFinishDuration(1000);
            }
        });
    }

    private Common commenUtils;

    public static Context getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        if (instance == null) {
            instance = this;
        }
        initOk();//ok
        regToWx();//微信
        initLogger();//日志库
        initUpdate();//版本更新初始化
        initWebview();//X5webview内核初始化
        intiDownloadConfig();//初始化下载设置
        initUmeng();
    }

    private void initLogger() {
    /*Logger.init("==Blogs==")//设置日志库tag标签
            .hideThreadInfo()
            .methodCount(3)// 决定打印多少行（每一行代表一个方法）默认：2
            .logLevel(BuildConfig.LOG_DEBUG ? LogLevel.FULL : LogLevel.NONE)//根据是否是debug模式显示日志
            .methodOffset(2);*/
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)      //是否显示线程信息 默认显示
                .methodCount(2)             //展示方法的行数 默认是2
                .methodOffset(7)            //内部方法调用向上偏移的行数 默认是0
                .tag("==Blogs==")          //自定义全局tag 默认：PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    private void intiDownloadConfig() {
        LitePal.initialize(this);
        commenUtils = Common.getInstance(getApplicationContext()).copyAssetsToSD("encrypt", "aliyun");
        commenUtils.setFileOperateCallback(new Common.FileOperateCallback() {
            @Override
            public void onSuccess() {
                LogUtils.e("copyAssetsToSD------onSuccess()");
                AliyunDownloadConfig downloadConfig = new AliyunDownloadConfig();
                downloadConfig.setSecretImagePath(Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.ENCRYPTED_PATH);
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.CACHE_PATH);
                if (!file.exists()) {
                    file.mkdir();
                }
                downloadConfig.setDownloadDir(file.getAbsolutePath());
                //设置同时下载个数
                downloadConfig.setMaxNums(2);
                //获取AliyunDownloadManager对象
                downloadManager = AliyunDownloadManager.getInstance(getApplicationContext());
                downloadManager.setDownloadConfig(downloadConfig);
            }

            @Override
            public void onFailed(String error) {
                LogUtils.e("copyAssetsToSD------onFailed()" + error);
            }
        });

    }

    private void initUmeng() {
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, "5d521d4e3fc195b523000353", "umeng_test", UMConfigure.DEVICE_TYPE_PHONE, "d9a3baa0dff24082751e60940cdb94f3");
        //获取推送代理，这个代理可以帮我们去执行诸如点击事件，样式不同的通知栏等操作
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setResourcePackageName("com.ucfo.youcaiwx");
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String s) {
                LogUtils.e("==UMConfigure==       deviceToken: " + s);
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtils.e("==UMConfigure==       onFailure:" + s + "   s1:" + s1);
            }
        });
        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Log.e("==UMConfigure==", "click");
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        String processName = getApplicationInfo().processName;
        LogUtils.e("==UMConfigure==       processName:" + processName);
    }

    /**
     * Detail:要使你的程序启动后微信终端能响应你的程序，必须在代码中向微信终端注册你的id。
     */
    private void regToWx() {
        //TODO 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constant.WEIXIN_KEY, true);
        // 将该app注册到微信
        api.registerApp(Constant.WEIXIN_KEY);
    }

    private void initWebview() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.e("x5內核onViewInitFinished is " + arg0);
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(this, cb);
    }

    private void initOk() {
        //okGo网络框架初始化和全局配置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));//使用数据库保持cookie，如果cookie不过期，则一直有效
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            private StringBuilder mMessage = new StringBuilder();

            @Override
            public void log(String message) {
                //LogUtils.e("OKHttp:" + message);
                // 请求或者响应开始
                if (message.startsWith("--> POST")) {
                    mMessage.setLength(0);
                }
                // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
                if ((message.startsWith("{") && message.endsWith("}")) || (message.startsWith("[") && message.endsWith("]"))) {
                    message = JsonUtil.formatJson(message);
                }
                mMessage.append(message.concat("\n"));
                // 请求或者响应结束，打印整条日志
                if (message.startsWith("<-- END HTTP")) {
                    LogUtils.w("OKHTTP: " + mMessage.toString());
                }
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//设置日志级别

        if (BuildConfig.DEBUG) {//TODO 正式版不打印日志
            builder.addInterceptor(loggingInterceptor);//添加日志拦截器
        }

        //超时时间设置，默认60秒
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        HttpHeaders headers = new HttpHeaders();//TODO 设置请求头  header不支持中文，不允许有特殊字符
        HttpParams params = new HttpParams();//TODO 设置请求参数 param支持中文,直接传,不要自己编码

        OkGo.getInstance().init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(headers)                      //全局公共头
                .addCommonParams(params);                       //全局公共参数

    }

    private void initUpdate() {
        XUpdate.get()
                .debug(false) //开启debug模式，可用于问题的排查
                .isWifiOnly(false)     //默认设置只在wifi下检查版本更新
                .isGet(true)          //默认设置使用get请求检查版本
                .isAutoMode(false)    //默认设置非自动模式，可根据具体使用配置
                .param(Constant.VERSION_CODE, UpdateUtils.getVersionCode(this)) //设置默认公共请求参数
                .param(Constant.APP_KEY, getPackageName())
                .setOnUpdateFailureListener(new OnUpdateFailureListener() { //设置版本更新出错的监听
                    @Override
                    public void onFailure(UpdateError error) {
                        LogUtils.e("UpdateError" + error.toString());
                    }
                })
                .setIUpdateHttpService(new OKHttpUpdateHttpService()) //这个必须设置！实现网络请求功能。
                .init(this);   //这个必须初始化
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    /*@Override
    public void onTerminate() {
        super.onTerminate();
        if (commenUtils != null) {
            commenUtils.onDestroy();
            commenUtils = null;
        }
    }*/
}
