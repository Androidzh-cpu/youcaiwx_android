package com.ucfo.youcaiwx.module.course.player;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Display;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alivc.player.AliyunErrorCode;
import com.alivc.player.VcPlayerLog;
import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.AliyunMediaInfo;
import com.aliyun.vodplayer.media.AliyunPlayAuth;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.androidkun.xtablayout.XTabLayout;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.roundview.RoundTextView;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.CheckRequestPermissionListener;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.course.CourseSocketBean;
import com.ucfo.youcaiwx.entity.course.GetVideoPlayAuthBean;
import com.ucfo.youcaiwx.module.course.player.adapter.CommonTabAdapter;
import com.ucfo.youcaiwx.module.course.player.courseinterface.ViewAction;
import com.ucfo.youcaiwx.module.course.player.fragment.CourseAnswerQuestionFragment;
import com.ucfo.youcaiwx.module.course.player.fragment.CourseDirectoryListFragment;
import com.ucfo.youcaiwx.module.course.player.fragment.CourseIntroductionFragment;
import com.ucfo.youcaiwx.module.course.player.utils.ErrorInfo;
import com.ucfo.youcaiwx.module.course.player.utils.NetWatchdog;
import com.ucfo.youcaiwx.module.course.player.utils.ScreenUtils;
import com.ucfo.youcaiwx.module.course.player.utils.TimeFormater;
import com.ucfo.youcaiwx.module.course.player.widget.AliyunScreenMode;
import com.ucfo.youcaiwx.module.course.player.widget.GestureDialogManager;
import com.ucfo.youcaiwx.module.course.player.widget.GestureView;
import com.ucfo.youcaiwx.module.course.player.widget.PlaySettingWindow;
import com.ucfo.youcaiwx.module.course.player.widget.QualityItem;
import com.ucfo.youcaiwx.module.course.player.widget.QualityListviewWindow;
import com.ucfo.youcaiwx.module.course.player.widget.SpeedListviewWindow;
import com.ucfo.youcaiwx.module.login.LoginActivity;
import com.ucfo.youcaiwx.module.pay.CommitOrderActivity;
import com.ucfo.youcaiwx.module.questionbank.activity.QuestionAskQuestionActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.course.CoursePlayPresenter;
import com.ucfo.youcaiwx.presenter.presenterImpl.integral.EarnIntegralPresenter;
import com.ucfo.youcaiwx.presenter.view.course.ICoursePlayerView;
import com.ucfo.youcaiwx.utils.CallUtils;
import com.ucfo.youcaiwx.utils.LogUtils;
import com.ucfo.youcaiwx.utils.ShareUtils;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.utils.systemutils.StatusBarUtil;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingView;
import com.ucfo.youcaiwx.widget.customview.SwitchView;
import com.ucfo.youcaiwx.widget.dialog.ShareDialog;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import static com.ucfo.youcaiwx.module.course.player.courseinterface.ViewAction.HideType.Normal;

/**
 * Author:29117
 * Time: 2019-4-3.  上午 9:56
 * Email:2911743255@qq.com
 * ClassName: VideoPlayPageActivity
 * Description:课程播放页
 * Detail:TODO  courseUnCon: 1正课  2非正课
 * TODO courseBuyState: 1购买  2未购买
 */
public class VideoPlayPageActivity extends AppCompatActivity implements SurfaceHolder.Callback, ICoursePlayerView {
    @BindView(R.id.course_coverimage)
    ImageView courseCoverimage;
    @BindView(R.id.tablayout)
    XTabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.btn_call)
    Button btnCall;
    @BindView(R.id.btn_pay)
    Button btnPay;
    @BindView(R.id.surfaceview)
    SurfaceView surfaceview;//播放器
    @BindView(R.id.player_loadingview)
    LoadingView playerLoadingview;//播放器加载进度view
    @BindView(R.id.player_tipstextview)
    TextView playerTipsview;//提示信息
    @BindView(R.id.linear_pay_course)
    ConstraintLayout linearPayCourse;
    @BindView(R.id.player_back)
    ImageView playerBack;
    @BindView(R.id.player_videotitle)
    TextView playerVideotitle;
    @BindView(R.id.player_share)
    ImageButton playerShare;
    @BindView(R.id.player_topliner)
    LinearLayout playerTopliner;
    @BindView(R.id.player_btn)
    ImageView playerBtn;
    @BindView(R.id.player_currentduration)
    TextView playerCurrentduration;
    @BindView(R.id.player_seekprogress)
    SeekBar playerSeekprogress;
    @BindView(R.id.player_totalduration)
    TextView playerTotalduration;
    @BindView(R.id.player_fullscreen)
    ImageView playerFullscreen;
    @BindView(R.id.player_bottomliner)
    LinearLayout playerBottomliner;
    @BindView(R.id.player_relativelayout)
    RelativeLayout playerRelativelayout;
    @BindView(R.id.player_quality)
    TextView playerQuality;
    @BindView(R.id.player_speed)
    TextView playerSpeed;
    @BindView(R.id.player_handouts)
    TextView playerHandouts;
    @BindView(R.id.player_setting)
    ImageView playerSetting;
    @BindView(R.id.player_toprightlinear)
    LinearLayout playerToprightlinear;
    @BindView(R.id.pdfView)
    PDFView pdfView;
    @BindView(R.id.player_collect)
    ImageView playerCollect;
    @BindView(R.id.player_pdfpage)
    RoundTextView playerPDFPage;
    @BindView(R.id.player_askquestion)
    TextView playerAskQuestion;
    @BindView(R.id.player_exitPdf)
    ImageView playerExitPdf;
    @BindView(R.id.playerLockedscreen)
    ImageView playerLockedscreen;
    @BindView(R.id.text_danmucontent)
    TextView danmuTextcontent;
    @BindView(R.id.corse_placeholder)
    LinearLayout corsePlaceholder;
    @BindView(R.id.gestureview)
    GestureView mGestureView;

    private ArrayList<String> titlesList;
    private ArrayList<Fragment> fragmentArrayList;
    private Bundle bundle;
    //是否正课  课程包ID  课程购买状态 用户ID
    private int courseUnCon, coursePackageId, courseBuyState, userId;
    //课程封面  课程价格 阿里视频video,课程来源
    private String courseCoverimageUrl, course_PackagePrice, currentVid, course_Source;
    //TODO 当前播放视频的信息,供收藏,播放使用
    private int currentCourseID, currentSectionID, currentVideoID, currentVideoCollectState;
    private VideoPlayPageActivity videoPlayPageActivity;
    private Context context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    //是否登录, 是否连续播放   PDF状态
    private boolean login_status, continuousPlay = false, pdfStatus = false;
    private AliyunVodPlayer aliyunVodPlayer;
    private SurfaceHolder surfaceHolder;
    //视频详细媒体信息
    private AliyunMediaInfo mAliyunMediaInfo;
    //用来记录前后台切换时的状态，以供恢复。
    private IAliyunVodPlayer.PlayerState mPlayerState;
    //播放是否完成
    private boolean isCompleted = false;
    //播放是否准备完成
    private boolean isPrepared = false;
    //是不是在seek中
    private boolean inSeek = false;
    //当前屏幕模式  TODO 默认小屏模式
    private AliyunScreenMode mCurrentScreenMode = AliyunScreenMode.Small;
    //整体缓冲进度
    private int mCurrentBufferPercentage = 0;
    //seekbar拖动状态
    private boolean isSeekbarTouching = false;
    //视频播放状态
    private PlayState mPlayState = PlayState.NotPlaying;
    //视频缓冲进度
    private int mVideoBufferPosition;
    //当前的清晰度
    private String mCurrentQuality;
    //网络状态监听
    private NetWatchdog mNetWatchdog;
    //目前支持的几种播放方式
    private AliyunPlayAuth mAliyunPlayAuth;
    private ErrorInfo currentError = ErrorInfo.Normal;

    private static final int WHAT_HIDE = 0;
    //当前亮度和音量
    private int currentScreenBrigtness, currentVolume;
    //当前画面比例
    private double currentScreenSize = 1;
    //默认倍速播放
    private float currentSpeed = 1.0f;
    //清晰度选择
    private QualityListviewWindow qualityListviewWindow;
    //手势操作框辅助工具
    private GestureDialogManager mGestureDialogManager;
    //倍速播放列表
    private SpeedListviewWindow speedListviewWindow;
    private WindowManager windowManager;
    private PlaySettingWindow playSettingWindow;
    private CourseIntroductionFragment courseIntroductionFragment;
    private CourseDirectoryListFragment courseDirectoryListFragment;
    private CourseAnswerQuestionFragment courseAnswerQuestionFragment;
    private CoursePlayPresenter coursePlayPresenter;
    private int watch_time = 0, learnPlanid = 0, learnDays = 0;
    //播放时的视频名称,从课程目录获取
    private String playVideoName;
    private WebSocket mWebSocket = null;
    private Timer mTimer;
    private TimerTask timerTask;
    private int socketPeriodTime = Constant.SOCKET_TIME, freeTime = Constant.FREE_TIME;
    //5秒后隐藏
    private static final int DELAY_TIME = Constant.DELAY_TIME;
    //是否锁定全屏
    private boolean mIsFullScreenLocked = false;
    private boolean download_wifi, look_wifi;
    private AliyunPlayAuth currentaAliyunPlayAuth;
    private boolean pdfDownloadStatus = false, pdfExists = false;
    private String freeWatchTips;

    //开始计时消息标志
    private static final int START = 0;
    //进度更新计时器
    private ProgressUpdateTimer mProgressUpdateTimer = new ProgressUpdateTimer();
    //控制菜单计时器
    private HideHandler mHideHandler = new HideHandler();
    //TODO 解决bug,进入播放界面快速切换到其他界面,播放器仍然播放视频问题
    private VodPlayerLoadEndHandler vodPlayerLoadEndHandler = new VodPlayerLoadEndHandler(this);

    /**
     * 判断VodePlayer 是否加载完成
     */
    private Map<AliyunMediaInfo, Boolean> hasLoadEnd = new HashMap<>();

    /*****************************************************TODO start 正计时  **************************/
    private long CountDownInterval = 1 * 1000;//TODO 倒计时时间间隔(默认1秒 单位:seconds )
    private Handler grandTotalTimer = new Handler();//grand total
    private int grandTotalSeconds = 0;//TODO 正计时时间 单位:seconds
    private Runnable grandTotalRunnable = new Runnable() {
        @Override
        public void run() {
            if (grandTotalTimer != null) {
                grandTotalTimer.postDelayed(this, CountDownInterval);
            }
            if (aliyunVodPlayer != null) {
                if (aliyunVodPlayer.getPlayerState() == IAliyunVodPlayer.PlayerState.Started) {
                    grandTotalSeconds++;
                    if (grandTotalSeconds >= Constant.INTEGRAL_TIME) {
                        grandTotalTimer.removeCallbacks(grandTotalRunnable); // 停止倒计时
                        //累计学习完成,领取积分
                        EarnIntegralPresenter.getInstance().earnIntegralForTask(Constant.INTEGRAL_STUDY, userId);
                        //本地记录清零
                        SharedPreferencesUtils.getInstance(VideoPlayPageActivity.this).putInt(Constant.INTEGRAL_COUNTER, 0);
                    }
                }
            }
        }
    };
    private Random random;
    /*****************************************************TODO end 正计时  *************************/

    /**
     * 播放进度更新计时器
     */
    @SuppressLint("HandlerLeak")
    private class ProgressUpdateTimer extends Handler {
        @Override
        public void handleMessage(Message msg) {
            handleProgressUpdateMessage(msg);
            super.handleMessage(msg);
        }
    }

    /**
     * 隐藏菜单计时器
     */
    @SuppressLint("HandlerLeak")
    private class HideHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            setLayoutVisibility(false, true);
            super.handleMessage(msg);
        }
    }

    /**
     * 当VodPlayer 没有加载完成的时候,调用onStop 去暂停视频,
     * 会出现暂停失败的问题。
     */
    private static class VodPlayerLoadEndHandler extends Handler {

        private WeakReference<VideoPlayPageActivity> weakReference;

        private boolean intentPause;

        VodPlayerLoadEndHandler(VideoPlayPageActivity videoPlayPageActivity) {
            weakReference = new WeakReference<>(videoPlayPageActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                intentPause = true;
            }
            if (msg.what == 1) {
                VideoPlayPageActivity videoPlayPageActivity = weakReference.get();
                if (videoPlayPageActivity != null && intentPause) {
                    videoPlayPageActivity.onStop();
                    intentPause = false;
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play_page);
        StatusBarUtil.immersive(this);
        ButterKnife.bind(this);

        SoulPermission.getInstance().checkAndRequestPermissions(Permissions.build(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), new CheckRequestPermissionsListener() {
            @Override
            public void onAllPermissionOk(Permission[] allPermissions) {
            }

            @Override
            public void onPermissionDenied(Permission[] refusedPermissions) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayPageActivity.this, R.style.WhiteDialogStyle);
                builder.setTitle(getResources().getString(R.string.explication));
                builder.setMessage(getResources().getString(R.string.permission_sdcard));
                builder.setPositiveButton(getResources().getString(R.string.donner), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SoulPermission.getInstance().goApplicationSettings();
                    }
                });
                builder.create();
                builder.show();
            }
        });
        //初始化VIEW
        initView();
        //初始化数据
        initData();
        //创建tablayout
        initTablayout();
        //阿里云播放器初始化
        initAliyunVideoPlayer();
        //注册网络状态监听
        initNetWatchdog();
        //播放来源视频设置
        initSourseType();
        //socket开启时机
        initSocket();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        resumeIntegralCounter();
    }

    /**
     * 在activity调用onResume的时候调用。 解决home回来后，画面方向不对的问题
     */
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

        //TODO 新增屏幕竖屏锁定
        if (mIsFullScreenLocked) {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                changeScreenMode(AliyunScreenMode.Small);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                changeScreenMode(AliyunScreenMode.Full);
            }
        }

        //横竖屏切换
        updatePlayerViewMode();
        //开始监听
        if (mNetWatchdog != null) {
            mNetWatchdog.startWatch();
        }
        //onStop中记录下来的状态，在这里恢复使用
        resumePlayerState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 暂停播放器的操作
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (!(hasLoadEnd != null && hasLoadEnd.size() > 0)) {
            vodPlayerLoadEndHandler.sendEmptyMessage(0);
            return;
        }
        if (mNetWatchdog != null) {
            mNetWatchdog.stopWatch();
        }
        //保存播放器的状态，供resume恢复使用。
        savePlayerState();
        //停止计时
        stopIntegralCounter();
    }

    @Override
    protected void onDestroy() {
        stop();//停止播放
        if (aliyunVodPlayer != null) {
            aliyunVodPlayer.release();
        }
        stopProgressUpdateTimer();
        mProgressUpdateTimer.removeCallbacksAndMessages(null);
        mProgressUpdateTimer = null;
        vodPlayerLoadEndHandler.removeCallbacksAndMessages(null);
        vodPlayerLoadEndHandler = null;

        if (mNetWatchdog != null) {
            mNetWatchdog.stopWatch();
            mNetWatchdog = null;
        }

        mGestureView = null;
        if (aliyunVodPlayer != null) {
            aliyunVodPlayer = null;
        }
        mGestureDialogManager = null;
        mAliyunMediaInfo = null;

        super.onDestroy();

        deletePdfFile();

        if (hasLoadEnd != null) {
            hasLoadEnd.clear();
        }

        if (mWebSocket != null) {
            mWebSocket.close(1001, "客户端主动关闭连接");
            mWebSocket.cancel();
            mWebSocket = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
            mTimer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

        //傻逼一样的东西,智障,脑残,狗东西,脑袋让自己给踢了
        if (animation != null) {
            animation.cancel();
        }
    }

    /**
     * 让home键无效
     *
     * @param keyCode 按键
     * @param event   事件
     * @return 是否处理。
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mIsFullScreenLocked && (keyCode != KeyEvent.KEYCODE_HOME)) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * socket开启时机
     */
    private void initSocket() {
        if (login_status) {
            //登录后择机开启socket
            boolean equals = TextUtils.equals(course_Source, Constant.LOCAL_CACHE);
            if (!equals) {
                //线上视频开启socket
                initWsClient(ApiStores.SOCKET);
                //看课30分钟自动加积分
                startIntegralCounter();
            }
        }
    }

    //每秒发送一条消息
    private void startTask() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (mWebSocket == null) {
                    return;
                }
                if (aliyunVodPlayer == null) {
                    return;
                }
                IAliyunVodPlayer.PlayerState playerState = aliyunVodPlayer.getPlayerState();
                if (playerState != IAliyunVodPlayer.PlayerState.Started) {
                    return;
                }
                sendSocketMessage();
            }
        };
        mTimer.schedule(timerTask, 0, socketPeriodTime);
    }

    /**
     * 初始化okhttp socket配置
     */
    private void initWsClient(String wsUrl) {
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)//允许失败重试
                .readTimeout(60, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(60, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(60, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        Request request = new Request.Builder().url(wsUrl).build();
        //建立连接
        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                mWebSocket = webSocket;
                output("client onOpen");
                //开启消息定时发送
                startTask();
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                output("client onMessage:" + text);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                output("client onClosing            code:" + code + " reason:" + reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                //打印一些内容
                output("client onClosed             code:" + code + " reason:" + reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                //出现异常会进入此回调
                output("client onFailure                throwable:" + t);
            }
        });
    }

    //TODO 视频来源设置
    private void initSourseType() {
        if (TextUtils.equals(course_Source, Constant.COLLECTION)) {
            //TODO 我的收藏(只有课程答疑tab,可以提问)
            int sectionId = bundle.getInt(Constant.SECTION_ID, 0);//章
            String vid = bundle.getString(Constant.COURSE_VIDEOID, "");//阿里VID
            int videoId = bundle.getInt(Constant.VIDEO_ID, 0);//小节ID
            int courseId = bundle.getInt(Constant.COURSE_ID, 0);//课ID

            setCourseUnCon(courseUnCon);//正课标识

            changePlayVidSource(vid, videoId, courseId, sectionId);//切换视频播放源
        } else if (TextUtils.equals(course_Source, Constant.WATCH_RECORD)) {
            //TODO 观看记录(直接横屏播放,可提问问题)
            int sectionId = bundle.getInt(Constant.SECTION_ID, 0);//章
            String vid = bundle.getString(Constant.COURSE_VIDEOID, "");//阿里VID
            int videoId = bundle.getInt(Constant.VIDEO_ID, 0);//小节ID
            int courseId = bundle.getInt(Constant.COURSE_ID, 0);//课ID

            setCourseUnCon(courseUnCon);//正课标识
            changePlayVidSource(vid, videoId, courseId, sectionId);//切换视频播放源

            AliyunScreenMode targetMode;
            if (mCurrentScreenMode == AliyunScreenMode.Small) {
                targetMode = AliyunScreenMode.Full;
            } else {
                targetMode = AliyunScreenMode.Small;
            }
            changeScreenMode(targetMode);
        } else if (TextUtils.equals(course_Source, Constant.LOCAL_CACHE)) {
            //TODO 本地缓存视频(横屏播放,不能收藏,不能提问,不能切换清晰度)
            String string = bundle.getString(Constant.LOCAL_PLAYURL, "");
            String title = bundle.getString(Constant.TITLE, getResources().getString(R.string.default_title));//视频标题
            setPlayVideoName(title);//切换提问标题

            AliyunLocalSource.AliyunLocalSourceBuilder asb = new AliyunLocalSource.AliyunLocalSourceBuilder();
            asb.setSource(string);
            AliyunLocalSource localSource = asb.build();
            aliyunVodPlayer.prepareAsync(localSource);

            AliyunScreenMode targetMode;
            if (mCurrentScreenMode == AliyunScreenMode.Small) {
                targetMode = AliyunScreenMode.Full;
            } else {
                targetMode = AliyunScreenMode.Small;
            }
            changeScreenMode(targetMode);
        } else if (TextUtils.equals(course_Source, Constant.WATCH_LEARNPLAN)) {
            //TODO 学习中心计划观课(横屏播放,可以提问)
            int section_id = bundle.getInt(Constant.SECTION_ID, 0);//章
            String vid = bundle.getString(Constant.COURSE_VIDEOID, "");//阿里VID
            int video_id = bundle.getInt(Constant.VIDEO_ID, 0);//小节ID
            int course_id = bundle.getInt(Constant.COURSE_ID, 0);//课ID
            learnPlanid = bundle.getInt(Constant.PLAN_ID, 0);//计划
            learnDays = bundle.getInt(Constant.DAYS, 0);//计划天数

            setCourseUnCon(courseUnCon);//正课标识
            changePlayVidSource(vid, video_id, course_id, section_id);//切换视频播放源

            AliyunScreenMode targetMode;
            if (mCurrentScreenMode == AliyunScreenMode.Small) {
                targetMode = AliyunScreenMode.Full;
            } else {
                targetMode = AliyunScreenMode.Small;
            }
            changeScreenMode(targetMode);//TODO 横竖屏切换
        } else if (TextUtils.equals(course_Source, Constant.WATCH_ANSWERDETAILED)) {
            //TODO 课程答疑查看视频(横屏播放,可以提问)
            int sectionId = bundle.getInt(Constant.SECTION_ID, 0);//章
            String vid = bundle.getString(Constant.COURSE_VIDEOID, "");//阿里VID
            int videoId = bundle.getInt(Constant.VIDEO_ID, 0);//小节ID
            int courseId = bundle.getInt(Constant.COURSE_ID, 0);//课ID

            changePlayVidSource(vid, videoId, courseId, sectionId);//切换视频播放源


            AliyunScreenMode targetMode;
            if (mCurrentScreenMode == AliyunScreenMode.Small) {
                targetMode = AliyunScreenMode.Full;
            } else {
                targetMode = AliyunScreenMode.Small;
            }
            changeScreenMode(targetMode);//TODO 横竖屏切换
        }

        //播放器菜单设置
        controlMenuUI();
    }

    /**
     * 控制器菜单设置
     */
    private void controlMenuUI() {
        if (TextUtils.equals(course_Source, Constant.COLLECTION)) {
            //TODO 我的收藏(只有课程答疑tab,可以提问)
        } else if (TextUtils.equals(course_Source, Constant.WATCH_RECORD)) {
            //TODO 观看记录(直接横屏播放,可提问问题)
            //全屏
            playerFullscreen.setVisibility(View.GONE);
            //设置
            playerSetting.setVisibility(View.GONE);
        } else if (TextUtils.equals(course_Source, Constant.LOCAL_CACHE)) {
            //TODO 本地缓存视频(横屏播放,不能收藏,不能提问,不能切换清晰度)
            //全屏
            playerFullscreen.setVisibility(View.GONE);
            //设置
            playerSetting.setVisibility(View.GONE);
            //提问
            playerAskQuestion.setVisibility(View.GONE);
            //收藏
            playerCollect.setVisibility(View.GONE);
            //pdf讲义插件
            pdfView.setVisibility(View.GONE);
            //pdf讲义切换按钮
            playerHandouts.setVisibility(View.GONE);
            //清晰度切换
            playerQuality.setVisibility(View.GONE);
            //课程封面  100+79+18
            courseCoverimage.setVisibility(View.GONE);
        } else if (TextUtils.equals(course_Source, Constant.WATCH_LEARNPLAN)) {
            //TODO 学习中心计划观课(横屏播放,可以提问)
        } else if (TextUtils.equals(course_Source, Constant.WATCH_ANSWERDETAILED)) {
            //TODO 课程答疑查看视频(横屏播放,可以提问)
        }
    }

    /**
     * Description:VideoPlayPageActivity
     * Time:2019-4-22   下午 1:59
     * Detail: 返回按键监听
     */
    @Override
    public void onBackPressed() {
        /**
         * 观看记录,本地播放,学习计划,课程答疑  直接切换小屏幕退出播放页
         */
        if (exitDirectly()) {
            return;
        }
        /**
         * 点播模式下 先干掉目录弹框 , 小屏模式下交由系统处理,横屏模式下切换小屏
         */
        if (courseDirectoryListFragment != null) {
            if (courseDirectoryListFragment.coursWindowisShow()) {
                courseDirectoryListFragment.dismissWindow();
            } else {
                if (AliyunScreenMode.Small == mCurrentScreenMode) {
                    super.onBackPressed();
                } else if (AliyunScreenMode.Full == mCurrentScreenMode) {
                    mCurrentScreenMode = AliyunScreenMode.Small;
                    changeScreenMode(mCurrentScreenMode);
                }
            }
        } else {
            /**
             * 小屏模式下交由系统处理,横屏模式下切换小屏
             */
            if (AliyunScreenMode.Small == mCurrentScreenMode) {
                super.onBackPressed();
            } else if (AliyunScreenMode.Full == mCurrentScreenMode) {
                mCurrentScreenMode = AliyunScreenMode.Small;
                changeScreenMode(mCurrentScreenMode);
            }
        }
    }

    /**
     * 观看记录,本地播放,学习计划,课程答疑  下直接退出
     */
    private boolean exitDirectly() {
        if (TextUtils.equals(course_Source, Constant.WATCH_RECORD) ||
                TextUtils.equals(course_Source, Constant.LOCAL_CACHE) ||
                TextUtils.equals(course_Source, Constant.WATCH_LEARNPLAN) ||
                TextUtils.equals(course_Source, Constant.WATCH_ANSWERDETAILED)) {
            changeScreenMode(AliyunScreenMode.Small);
            finish();
            return true;
        }
        return false;
    }

    /**
     * 这函数,检测系统屏幕横竖变化
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //
        updatePlayerViewMode();
        //重新设置surfaceview尺寸
        setSurfaceViewLayout(currentScreenSize);
    }

    /**
     * 初始化网络监听
     */
    private void initNetWatchdog() {
        if (!TextUtils.equals(course_Source, Constant.LOCAL_CACHE)) {
            //ToastUtil.showBottomShortText(this,"非本地视频" );
            Context context = VideoPlayPageActivity.this;
            mNetWatchdog = new NetWatchdog(context);
            mNetWatchdog.setNetChangeListener(new MyNetChangeListener(this));
            mNetWatchdog.setNetConnectedListener(new MyNetConnectedListener());
        } else {
            //ToastUtil.showBottomShortText(this,"本地视频" );
        }
    }

    /**
     * 开启底层日志
     */
    public void enableNativeLog() {
        if (aliyunVodPlayer != null) {
            aliyunVodPlayer.enableNativeLog();
        }
    }

    /**
     * 获取底层的一些debug信息
     */
    public void getAllDebugInfo() {
        if (aliyunVodPlayer != null) {
            Map<String, String> debugInfo = aliyunVodPlayer.getAllDebugInfo();
            LogUtils.e("getAllDebugInfo--------" + new Gson().toJson(debugInfo));
        }
    }

    /**
     * 关闭底层日志
     */
    public void disableNativeLog() {
        if (aliyunVodPlayer != null) {
            aliyunVodPlayer.disableNativeLog();
        }
    }

    /**
     * 设置循环播放
     */
    public void setCirclePlay(boolean circlePlay) {
        if (aliyunVodPlayer != null) {
            aliyunVodPlayer.setCirclePlay(circlePlay);
        }
    }

    /**
     * 是否处于播放状态：start或者pause了
     */
    public boolean isPlaying() {
        if (aliyunVodPlayer != null) {
            return aliyunVodPlayer.isPlaying();
        }
        return false;
    }

    /**
     * 保存当前的状态，供恢复使用
     */
    private void savePlayerState() {
        if (aliyunVodPlayer == null) {
            return;
        }
        mPlayerState = aliyunVodPlayer.getPlayerState();
        //然后再暂停播放器
        //如果希望后台继续播放，不需要暂停的话，可以注释掉pause调用。
        pause();
    }

    /**
     * Activity回来后，恢复之前的状态
     */
    private void resumePlayerState() {
        if (aliyunVodPlayer == null) {
            return;
        }
        if (mPlayerState == IAliyunVodPlayer.PlayerState.Paused) {
            pause();
        } else if (mPlayerState == IAliyunVodPlayer.PlayerState.Started) {
            if (course_Source.equals(Constant.LOCAL_CACHE)) {
                reTry();
            } else {
                start();
            }
        }
    }

    /**
     * 停止播放
     */
    public void stop() {
/*
        if (aliyunVodPlayer != null) {
            aliyunVodPlayer.stop();
        }
        //更新播放按钮状态
        updatePlayStateBtn(PlayState.NotPlaying);
*/

        //分割线
        Boolean hasLoadedEnd = null;
        AliyunMediaInfo mediaInfo = null;
        if (aliyunVodPlayer != null && hasLoadEnd != null) {
            mediaInfo = aliyunVodPlayer.getMediaInfo();
            hasLoadedEnd = hasLoadEnd.get(mediaInfo);
        }

        if (aliyunVodPlayer != null && hasLoadedEnd != null) {
            aliyunVodPlayer.stop();
            //更新播放按钮状态
            updatePlayStateBtn(PlayState.NotPlaying);
        }
        if (hasLoadEnd != null) {
            hasLoadEnd.remove(mediaInfo);
        }
    }

    /**
     * 开始播放
     */
    public void start() {
        if (aliyunVodPlayer == null) {
            return;
        }
        updatePlayStateBtn(PlayState.Playing);//更新播放按钮状态

        IAliyunVodPlayer.PlayerState playerState = aliyunVodPlayer.getPlayerState();
        if (playerState == IAliyunVodPlayer.PlayerState.Paused || playerState == IAliyunVodPlayer.PlayerState.Prepared || aliyunVodPlayer.isPlaying()) {
            aliyunVodPlayer.start();
        }
        mGestureView.setHideType(ViewAction.HideType.Normal);
        mGestureView.show();
    }

    /**
     * 重试播放，会从当前位置开始播放
     */
    public void reTry() {
        isCompleted = false;
        inSeek = false;

        int currentPosition = Integer.parseInt(String.valueOf(aliyunVodPlayer.getCurrentPosition()));

        reset();

        if (mGestureView != null) {
            mGestureView.reset();
        }

        if (aliyunVodPlayer != null) {
            playerLoadingview.setVisibility(View.VISIBLE);
            //seek到当前的位置再播放
            /*
                isLocalSource()判断不够,有可能是sts播放,也有可能是url播放,还有可能是sd卡的视频播放,
                如果是后两者,需要走if,否则走else
             */
            if (TextUtils.equals(course_Source, Constant.LOCAL_CACHE)) {
                //prepareAuth(currentaAliyunPlayAuth);
            } else {
                prepareAuth(currentaAliyunPlayAuth);
            }
            aliyunVodPlayer.seekTo(currentPosition);
        }
    }

    /**
     * 暂停播放
     */
    public void pause() {
        if (aliyunVodPlayer == null) {
            return;
        }

        //更新播放按钮状态
        updatePlayStateBtn(PlayState.NotPlaying);

        //更新播放状态
        IAliyunVodPlayer.PlayerState playerState = aliyunVodPlayer.getPlayerState();
        if (playerState == IAliyunVodPlayer.PlayerState.Started || aliyunVodPlayer.isPlaying()) {
            aliyunVodPlayer.pause();
        }
    }

    /**
     * 获取视频播放状态
     */
    public IAliyunVodPlayer.PlayerState getAliyunPlayerState() {
        if (aliyunVodPlayer == null) {
            return null;
        }
        return aliyunVodPlayer.getPlayerState();
    }

    /**
     * 重置。包括一些状态值，view的状态等
     */
    private void reset() {
        isCompleted = false;
        inSeek = false;
        if (mGestureView != null) {
            mGestureView.reset();
        }
        stop();
    }

    /**
     * 重播，将会从头开始播放
     */
    public void rePlay() {
        if (aliyunVodPlayer != null) {
            //重播是从头开始播
            aliyunVodPlayer.replay();
        }
    }

    /**
     * 清空之前设置的播放源
     */
    private void clearAllSource() {
        mAliyunPlayAuth = null;
    }

    /**
     * 通过playAuth prepare  播放凭证播放视频
     */
    private void prepareAuth(AliyunPlayAuth aliyunPlayAuth) {
        mAliyunPlayAuth = aliyunPlayAuth;
        aliyunVodPlayer.prepareAsync(mAliyunPlayAuth);//播放器开始准备播放
    }

    /**
     * 切换播放状态。点播播放按钮之后的操作
     */
    private void switchPlayerState() {
        IAliyunVodPlayer.PlayerState playerState = aliyunVodPlayer.getPlayerState();
        if (playerState == IAliyunVodPlayer.PlayerState.Started) {
            pause();
        } else if (playerState == IAliyunVodPlayer.PlayerState.Paused || playerState == IAliyunVodPlayer.PlayerState.Prepared) {
            start();
        }
    }

    /**
     * 更新播放按钮状态
     */
    private void updatePlayStateBtn(PlayState state) {
        if (state == PlayState.NotPlaying) {
            playerBtn.setImageResource(R.drawable.icon_pause);
        } else if (state == PlayState.Playing) {
            playerBtn.setImageResource(R.drawable.icon_play);
        }
    }

    /**
     * TODO 实时处理进度更新消息的线程
     */
    private void handleProgressUpdateMessage(Message msg) {
        if (msg.what == START) {
            if (aliyunVodPlayer != null && !inSeek) {
                playerTotalduration.setText(TimeFormater.formatMs(mAliyunMediaInfo.getDuration()));//控制器设置总时长
                playerCurrentduration.setText(TimeFormater.formatMs(aliyunVodPlayer.getCurrentPosition()));//控制器设置当前时长
                playerSeekprogress.setMax(mAliyunMediaInfo.getDuration());//进度条设置总时长
                if (isSeekbarTouching) {
                    //用户拖动的时候，不去更新进度值，防止跳动。
                } else {
                    mVideoBufferPosition = aliyunVodPlayer.getBufferingPosition();
                    playerSeekprogress.setSecondaryProgress(aliyunVodPlayer.getBufferingPosition());//进度条设置缓存吗进度
                    playerSeekprogress.setProgress(Integer.parseInt(String.valueOf(aliyunVodPlayer.getCurrentPosition())));//进度条设置当前进度
                }
                //免费试看
                freeWatch();

                //请跟我念,傻逼年年有,今年贼你妈多
                SBZHXQ();
            }
            //解决bug：在Prepare中开始更新的时候，不会发送更新消息。
            startProgressUpdateTimer();
        }
    }

    /**
     * 傻逼智障,简直无敌了,开启糊弄大法,忽悠呗,接着忽悠
     */
    private int danmakuTime = 4 * 60;//TODO 弹幕时长(以秒为单位)
    final boolean[] danmuTimeFlag = {true};
    final int[] nextInt = {0, 0};
    private TranslateAnimation animation = null;
    private Animation.AnimationListener onAnimEndListener;

    private void SBZHXQ() {
        if (!TextUtils.equals(course_Source, Constant.LOCAL_CACHE)) {
            if (getCourseBuyState() == Constant.HAVED_BUY) {
                //随机弹幕
                if (aliyunVodPlayer != null) {
                    int round = Math.round(aliyunVodPlayer.getCurrentPosition());
                    int rounds = round / 1000;//当前播放秒数
                    if (rounds != 0) {
                        if (rounds > 60) {
                            //23/4=5.j
                            if (rounds % danmakuTime == 0) {
                                //4分钟节点
                                danmuTimeFlag[0] = true;
                            } else {
                                //非4分钟的节点
                                if (danmuTimeFlag[0]) {
                                    nextInt[0] = random.nextInt(60 * 4 - 1);
                                    danmuTimeFlag[0] = false;
                                    nextInt[1] = nextInt[0] + rounds;
                                }
                                if (rounds == nextInt[1]) {
                                    viewTransView();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void viewTransView() {
        int userID = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        if (userID != 0) {
            int i = random.nextInt(6);
            switch (i) {
                case 0:
                    animation = new TranslateAnimation(0, playerRelativelayout.getWidth(), 0, playerRelativelayout.getHeight());
                    break;
                case 1:
                    animation = new TranslateAnimation(0, playerRelativelayout.getWidth(), playerRelativelayout.getHeight(), 0);
                    break;
                case 2:
                    animation = new TranslateAnimation(0, playerRelativelayout.getWidth(), DensityUtil.dp2px(40), DensityUtil.dp2px(40));
                    break;
                case 3:
                    animation = new TranslateAnimation(0, playerRelativelayout.getWidth(), playerRelativelayout.getHeight() - DensityUtil.dp2px(40), playerRelativelayout.getHeight() - DensityUtil.dp2px(40));
                    break;
                case 4:
                    animation = new TranslateAnimation(playerRelativelayout.getWidth() / 3, playerRelativelayout.getWidth() / 3, 0, playerRelativelayout.getHeight());
                    break;
                case 5:
                    animation = new TranslateAnimation(2 * playerRelativelayout.getWidth() / 3, 2 * playerRelativelayout.getWidth() / 3, 0, playerRelativelayout.getHeight());
                    break;
                default:
                    animation = new TranslateAnimation(0, playerRelativelayout.getWidth(), 0, playerRelativelayout.getHeight());
                    break;
            }

            String s = String.valueOf(System.currentTimeMillis() + userID);
            danmuTextcontent.setText(s);
            danmuTextcontent.setVisibility(View.VISIBLE);
            if (Constant.ISTEST_ENVIRONMENT) {
                danmuTextcontent.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            }
            animation.setDuration(3000);
            animation.setFillAfter(false);
            danmuTextcontent.startAnimation(animation);
            onAnimEndListener = new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    danmuTextcontent.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            };
            animation.setAnimationListener(onAnimEndListener);
        }
    }

    /**
     * 试听时间设置
     */
    private void freeWatch() {
        if (getCourseBuyState() == Constant.HAVED_BUY || TextUtils.equals(course_Source, Constant.LOCAL_CACHE)
                || TextUtils.equals(course_Source, Constant.WATCH_LEARNPLAN) || TextUtils.equals(course_Source, Constant.WATCH_ANSWERDETAILED)) {
            //已购买
        } else {
            //未购买,试看指定时间

            //总的剩余时间
            int millis = Integer.parseInt(String.valueOf(aliyunVodPlayer.getCurrentPosition() / 1000));
            if (millis >= freeTime) {
                playerTipsview.setVisibility(playerTipsview.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
                playerTipsview.setText(freeWatchTips);
                stop();
                stopProgressUpdateTimer();
                //背景设置为黑色的
                courseCoverimage.setColorFilter(Color.BLACK);
                //背景图可见
                courseCoverimage.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 开始进度条更新计时器
     */
    private void startProgressUpdateTimer() {
        if (mProgressUpdateTimer != null) {
            mProgressUpdateTimer.removeMessages(START);
            mProgressUpdateTimer.sendEmptyMessageDelayed(START, 1000);
        } else {
            mProgressUpdateTimer = new ProgressUpdateTimer();
            mProgressUpdateTimer.removeMessages(START);
            mProgressUpdateTimer.sendEmptyMessageDelayed(START, 1000);
        }
    }

    /**
     * 停止进度条更新计时器
     */
    private void stopProgressUpdateTimer() {
        if (mProgressUpdateTimer != null) {
            mProgressUpdateTimer.removeMessages(START);
        } else {
            mProgressUpdateTimer = new ProgressUpdateTimer();
            mProgressUpdateTimer.removeMessages(START);
        }
    }

    /**
     * 延时隐藏控制栏
     */
    private void hideDelayed() {
        if (mHideHandler != null) {
            mHideHandler.removeMessages(WHAT_HIDE);
            mHideHandler.sendEmptyMessageDelayed(WHAT_HIDE, DELAY_TIME);
        }
    }

    /**
     * @param flag      显示状态
     * @param isDisplay 是否延迟消失
     */
    public void setLayoutVisibility(boolean flag, boolean isDisplay) {
        if (mIsFullScreenLocked) {
            if (isDisplay) {
                hideDelayed();
            }
            if (flag) {
                playerLockedscreen.setVisibility(View.VISIBLE);
            } else {
                playerLockedscreen.setVisibility(View.GONE);
            }
            return;
        }

        if (aliyunVodPlayer == null) {
            return;
        }
        if (aliyunVodPlayer.getDuration() <= 0) {
            return;
        }
        mHideHandler.removeMessages(WHAT_HIDE);

        if (isDisplay) {
            //延迟消失
            hideDelayed();
        }

        if (flag) {
            //显示控制栏
            if (playerBottomliner.getVisibility() != View.VISIBLE) {
                playerBottomliner.setVisibility(View.VISIBLE);
                playerBottomliner.startAnimation(getTranslateAnimation(0.0f, 0.0f, playerBottomliner.getHeight(), 0.0f, true));
            }
            if (playerTopliner.getVisibility() != View.VISIBLE) {
                playerTopliner.setBackgroundColor(Color.parseColor("#99000000"));
                playerTopliner.setVisibility(View.VISIBLE);
                playerTopliner.startAnimation(getTranslateAnimation(0.0f, 0.0f, -1 * playerTopliner.getHeight(), 0.0f, true));
            } else {
                playerTopliner.setBackgroundColor(Color.parseColor("#99000000"));
            }
            //全屏模式下才能可见
            if (mCurrentScreenMode == AliyunScreenMode.Full) {
                playerLockedscreen.setVisibility(View.VISIBLE);
            }
        } else {
            //隐藏控制栏
            if (playerTopliner.getVisibility() != View.GONE) {
                //可见状态
                playerTopliner.startAnimation(getTranslateAnimation(0.0f, 0.0f, 0.0f, -1 * playerTopliner.getHeight(), false));
                playerTopliner.setVisibility(View.GONE);
            }
            if (playerBottomliner.getVisibility() != View.GONE) {
                //可见状态
                playerBottomliner.startAnimation(getTranslateAnimation(0.0f, 0.0f, 0.0f, playerBottomliner.getHeight(), false));
                playerBottomliner.setVisibility(View.GONE);
            }

            playerLockedscreen.setVisibility(View.GONE);

            //弹出的播放器倍速,画质菜单隐藏
            if (qualityListviewWindow != null) {
                qualityListviewWindow.dismiss();
            }
            if (speedListviewWindow != null) {
                speedListviewWindow.dismiss();
            }
        }
    }

    /**
     * 创建顶部菜单和底部菜单的隐藏与消失动画
     */
    private TranslateAnimation getTranslateAnimation(float fromX, float toX, float fromY, float toY, boolean isFillAfter) {
        TranslateAnimation animation = new TranslateAnimation(fromX, toX, fromY, toY);
        animation.setFillAfter(isFillAfter);
        animation.setDuration(200);
        return animation;
    }

    /**
     * 锁定屏幕
     */
    private void lockScreen(boolean lockScreen) {
        setLayoutVisibility(false, false);
        mIsFullScreenLocked = lockScreen;
        if (mIsFullScreenLocked) {
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_icon_locked2);
            playerLockedscreen.setImageDrawable(drawable);

            setLayoutVisibility(false, false);
            ToastUtil.showBottomShortText(this, getResources().getString(R.string.course_lockedScreen));
        } else {
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_icon_unlocked2);
            playerLockedscreen.setImageDrawable(drawable);

            setLayoutVisibility(true, true);
        }
        if (mGestureView != null) {
            mGestureView.setScreenLockStatus(mIsFullScreenLocked);
        }
    }

    /**
     * 清晰度切换
     */
    private void setCurrentQuality(String finalQuality) {
        mCurrentQuality = finalQuality;
        playerQuality.setText(QualityItem.getItem(this, mCurrentQuality, false).getName());//设置清晰度按钮
    }

    /**
     * 切换讲义
     */
    public void loadingCurrentVideoPDF(String pdfUrl, String currentVid) {
        deletePdfFile();//删除本地文件
        //String mDestFileName = currentVid + pdfUrl.substring(pdfUrl.lastIndexOf("."), pdfUrl.length());//设置下载到本地的文件名
        String mDestFileName = currentVid;//以视频库的id作为文件名
        String localPath = null;
        File externalFilesDir = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//API19以上在应用存储空间里存储
            externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        } else {
            //低于API19则存储在自定义文件夹下
            externalFilesDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.PDF_PATH);
        }
        if (externalFilesDir != null) {
            localPath = externalFilesDir.getAbsolutePath();
        }
        String finalLocalPath = localPath;
        OkGo.<File>get(pdfUrl).tag(context).execute(new FileCallback(finalLocalPath, mDestFileName) { //文件下载时指定下载的路径以及下载的文件的名
            @Override
            public void onStart(com.lzy.okgo.request.base.Request<File, ? extends com.lzy.okgo.request.base.Request> request) {
                super.onStart(request);
                pdfDownloadStatus = true;
            }

            @Override
            public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                if (response.isSuccessful()) {
                    File file = new File(finalLocalPath, mDestFileName);
                    if (file.exists()) {
                        pdfView.fromFile(file)
                                .defaultPage(0) //设置默认显示第0页
                                .enableSwipe(true) //是否允许翻页，默认是允许翻页
                                .enableAnnotationRendering(true)// 渲染风格（就像注释，颜色或表单）
                                .onPageChange(new OnPageChangeListener() {
                                    @Override
                                    public void onPageChanged(int page, int pageCount) {
                                        page = page + 1;
                                        String strings = String.valueOf(page);
                                        String string2 = String.valueOf(pageCount);
                                        SpannableString spannableString = new SpannableString(strings + "/" + string2);
                                        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#1874FD"));
                                        spannableString.setSpan(colorSpan, 0, strings.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                                        playerPDFPage.setText(spannableString);
                                    }
                                })
                                .swipeHorizontal(false)//pdf文档翻页是否是垂直翻页，默认是左右滑动翻页
                                .enableAntialiasing(true)// 改善低分辨率屏幕上的渲染
                                .spacing(10)// 页面间的间距。定义间距颜色，设置背景视图
                                .autoSpacing(false)
                                .pageFitPolicy(FitPolicy.WIDTH)
                                .pageSnap(true) // snap pages to screen boundaries
                                .pageFling(false) // make a fling change only a single page like ViewPager
                                .nightMode(false)// toggle night mode夜间模式
                                .load();
                        pdfDownloadStatus = false;
                    }
                }
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<File> response) {
                super.onError(response);
                pdfDownloadStatus = false;
            }
        });
    }

    /**
     * Description:VideoPlayPageActivity
     * Time:2019-4-24   下午 2:01
     * Detail: 删除本地PDF文件(产品要对此负责)
     */
    public void deletePdfFile() {
        try {
            File externalFilesDir = null;
            String localPath = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//API19以上存放在应用内部文件夹
                externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            } else {//低于API19则删除自定义文件夹下的pdf文件
                externalFilesDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.PDF_PATH);
            }
            if (externalFilesDir != null) {
                localPath = externalFilesDir.getAbsolutePath();
            }
            File file = new File(Objects.requireNonNull(localPath));
            if (file.exists()) {//判断文件是否存在
                //如果是文件，遍历删除
                if (file.isDirectory()) {
                    File[] childFile = file.listFiles();
                    for (File f : childFile) {
                        f.delete();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 视频播放横竖屏切换
     */
    public void updatePlayerViewMode() {
        if (playerRelativelayout != null) {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                //TODO 转为竖屏了
                //显示状态栏
                this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                playerRelativelayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                //设置view的布局，宽高之类
                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams) playerRelativelayout.getLayoutParams();
                aliVcVideoViewLayoutParams.height = (int) (ScreenUtils.getWidth(this) * 9.0f / 16);
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                //TODO 转到横屏了
                //隐藏状态栏
                if (!isStrangePhone()) {
                    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    playerRelativelayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
                //设置view的布局，宽高
                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams) playerRelativelayout.getLayoutParams();
                aliVcVideoViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            }
        }
        /**
         * 播放器菜单的设置弹框和课程播放目录弹框必须要被干掉
         */
        if (playSettingWindow != null) {
            playSettingWindow.dissMiss();
        }
        if (courseDirectoryListFragment != null) {
            if (courseDirectoryListFragment.coursWindowisShow()) {
                courseDirectoryListFragment.dismissWindow();
            }
        }
    }

    /**
     * 特殊机型处理(咱也不知道,咱也不敢问,直接照搬阿里爸爸的代码吧)
     */
    public boolean isStrangePhone() {
        boolean strangePhone = "mx5".equalsIgnoreCase(Build.DEVICE)
                || "Redmi Note2".equalsIgnoreCase(Build.DEVICE)
                || "Z00A_1".equalsIgnoreCase(Build.DEVICE)
                || "hwH60-L02".equalsIgnoreCase(Build.DEVICE)
                || "hermes".equalsIgnoreCase(Build.DEVICE)
                || ("V4".equalsIgnoreCase(Build.DEVICE) && "Meitu".equalsIgnoreCase(Build.MANUFACTURER))
                || ("m1metal".equalsIgnoreCase(Build.DEVICE) && "Meizu".equalsIgnoreCase(Build.MANUFACTURER));
        VcPlayerLog.e("lfj1115 ", " Build.Device = " + Build.DEVICE + " , isStrange = " + strangePhone);
        return strangePhone;
    }

    /**
     * 改变屏幕模式：小屏或者全屏。 (这骚操作其实就是控制菜单的大小屏按钮控制的)
     */
    public void changeScreenMode(AliyunScreenMode targetMode) {
        AliyunScreenMode finalScreenMode = targetMode;

        if (mIsFullScreenLocked) {
            finalScreenMode = AliyunScreenMode.Full;
        }

        //这里可能会对模式做一些修改
        if (targetMode != mCurrentScreenMode) {
            mCurrentScreenMode = finalScreenMode;
        }
        if (finalScreenMode == AliyunScreenMode.Full) {
            playerFullscreen.setImageResource(R.drawable.icon_verticalscreen);
            //不是固定竖屏播放。
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            playerControllerView(mCurrentScreenMode);

            playerTopliner.setPadding(DensityUtil.dp2px(15), DensityUtil.dp2px(8), DensityUtil.dp2px(15), DensityUtil.dp2px(8));
        } else if (finalScreenMode == AliyunScreenMode.Small) {
            playerFullscreen.setImageResource(R.drawable.icon_fullscreen);
            //不是固定竖屏播放。
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            playerControllerView(mCurrentScreenMode);

            //重新设置菜单栏的高度和填充距离
            int statusBarHeight = StatusBarUtil.getStatusBarHeight(this);
            playerTopliner.setPadding(DensityUtil.dp2px(15), statusBarHeight, DensityUtil.dp2px(15), DensityUtil.dp2px(2));
        }
    }

    /**
     * 根据当前屏幕状态控制播放器的操作
     */
    private void playerControllerView(AliyunScreenMode targetMode) {
        if (AliyunScreenMode.Small == targetMode) {
            //TODO 切换为小屏
            playerQuality.setVisibility(playerQuality.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);//TODO 清晰度按钮控制
            playerSpeed.setVisibility(playerSpeed.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);//TODO 倍速按钮控制
            playerShare.setVisibility(playerShare.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);//TODO 分享按钮控制

            playerToprightlinear.setVisibility(playerToprightlinear.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);//TODO 竖屏时的控制菜单
            playerAskQuestion.setVisibility(playerAskQuestion.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);//TODO 竖屏时隐藏提问按钮
            playerPDFPage.setVisibility(playerPDFPage.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);//TODO PDF页数显示器
            pdfView.setVisibility(pdfView.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);//PDF文件隐藏
            playerExitPdf.setVisibility(playerExitPdf.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);//PDF退出按钮隐藏
            playerLockedscreen.setVisibility(View.GONE);//锁屏按钮干掉
            playerHandouts.setText(getResources().getString(R.string.notes));

            //手势恢复
            mGestureView.setHideType(Normal);
            mGestureView.show();
        } else if (AliyunScreenMode.Full == targetMode) {
            //TODO 切换为横屏
            playerQuality.setVisibility(playerQuality.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
            playerSpeed.setVisibility(playerSpeed.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
            playerLockedscreen.setVisibility(View.VISIBLE);//锁屏按钮可见

            playerShare.setVisibility(playerShare.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);//TODO 分享按钮控制

            playerToprightlinear.setVisibility(playerToprightlinear.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);//TODO 横屏时的提问按钮
            if (getCourseUnCon() == Constant.HAVED_BUY) {//TODO 正课
                playerAskQuestion.setVisibility(playerAskQuestion.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
            } else {
                playerAskQuestion.setVisibility(playerAskQuestion.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
            }
        }
    }

    /**
     * 切换视频播放资源
     * 点击视频列表, 切换播放的视频
     * TODO 5.3 准备播放 NOTE:注意过期时间。特别是重播的时候，可能已经过期。所以重播的时候最好重新请求一次服务器。
     */
    public void changePlayVidSource(String vid, int video_id, int course_id, int section_id) {
        clearAllSource();//清除资源
        reset();//重置

        currentVid = vid;//当前播放的阿里视频VID
        currentCourseID = course_id;//当前播放的课程ID
        currentSectionID = section_id;//当前播放的课程章ID
        currentVideoID = video_id;//当前播放的后台库视频ID

        //获取对应视频答疑列表
        if (courseAnswerQuestionFragment != null) {
            courseAnswerQuestionFragment.getAnswerListData(coursePackageId, currentCourseID, currentSectionID, currentVideoID);
        }
        //获取播放凭证
        coursePlayPresenter.getVideoPlayAuthor(currentVid, currentVideoID, currentCourseID, currentSectionID, userId, coursePackageId);
    }

    /**
     * Description:VideoPlayPageActivity
     * Time:2019-4-24   上午 10:51
     * Detail:当前视频播放结束, 播放下一个视频
     */
    private void onNext() {
        if (currentError == ErrorInfo.UnConnectInternet) {
            // 此处需要判断网络和播放类型
            // 网络资源, 播放完自动波下一个, 无网状态提示ErrorTipsView
            // 本地资源, 播放完需要重播, 显示Replay, 此处不需要处理
            //toastInfo("4014, -1, 当前网络不可用");
            toastInfo("4014, -1, Net Error");
            return;
        }
        if (courseDirectoryListFragment != null) {
            courseDirectoryListFragment.onNextVideoPlay();
        }
    }

    /**
     * Description:VideoPlayPageActivity
     * Time:2019-4-11   上午 9:03
     * Detail:TODO  初始化阿里云播放器
     */
    private void initAliyunVideoPlayer() {
        //TODO 播放器初始化  在需要使用播放器SDK的activity里面引入添加如下初始化方法：
        aliyunVodPlayer = new AliyunVodPlayer(this);
        aliyunVodPlayer.setVideoScalingMode(IAliyunVodPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        setCirclePlay(false);//设置循环播放 default false
        if (Constant.ISTEST_ENVIRONMENT) {
            //打印底层日日志
            enableNativeLog();
        } else {
            //正式模式干掉日志
            disableNativeLog();
        }

        //设置播放缓存
        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.PLAYER_CACHE_PATH;
        setPlayingCache(true, sdDir, 60 * 60 /*时长, s */, 300 /*大小，MB*/);

        //TODO 播放器加载进度监听
        aliyunVodPlayer.setOnLoadingListener(new IAliyunVodPlayer.OnLoadingListener() {
            @Override
            public void onLoadStart() {
                /**
                 * 诱导性加载菊花 _(:3」∠❀)_菊花碎了一地
                 */
                playerLoadingview.setVisibility(View.VISIBLE);
                playerTipsview.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadEnd() {
                /**
                 * 诱导性加载菊花 _(:3」∠❀)_菊花都碎了一地
                 */
                playerLoadingview.setVisibility(View.GONE);
                playerTipsview.setVisibility(View.GONE);

                hasLoadEnd.put(mAliyunMediaInfo, true);
                vodPlayerLoadEndHandler.sendEmptyMessage(1);
            }

            @Override
            public void onLoadProgress(int i) {
                /**
                 * 加载进度
                 */
                playerTipsview.setText(String.valueOf(getResources().getString(R.string.course_videoLoading) + i + "%"));
            }
        });
        //TODO 播放器播放准备监听
        aliyunVodPlayer.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                if (aliyunVodPlayer == null) {
                    return;
                }
                mAliyunMediaInfo = aliyunVodPlayer.getMediaInfo();
                if (mAliyunMediaInfo == null) {
                    return;
                }
                //防止服务器信息和实际不一致
                long duration = aliyunVodPlayer.getDuration();
                int round = Math.round(duration);
                mAliyunMediaInfo.setDuration(round);
                //使用用户设置的标题(时隔个几个月,居然忘了)
                mAliyunMediaInfo.setTitle(mAliyunMediaInfo.getTitle());
                mAliyunMediaInfo.setPostUrl(mAliyunMediaInfo.getPostUrl());

                mCurrentQuality = aliyunVodPlayer.getCurrentQuality();//获取当前的清晰度
                setCurrentQuality(mCurrentQuality);//设置默认倍速播放

                /**
                 * 解放手势操作
                 */
                mGestureView.setHideType(ViewAction.HideType.Normal);
                mGestureView.show();

                isPrepared = true;

                LogUtils.e("准备完成触发");

                /**
                 * 切换到后台监听的上次退出时的播放位置
                 */
                preparedStart(watch_time);
            }
        });
        //TODO 播放器第一帧监听
        aliyunVodPlayer.setOnFirstFrameStartListener(new IAliyunVodPlayer.OnFirstFrameStartListener() {
            @Override
            public void onFirstFrameStart() {
                LogUtils.e("首帧显示触发");

                //获取所有日志信息
                getAllDebugInfo();

                /**
                 * 开始启动更新进度的定时器
                 */
                startProgressUpdateTimer();

                /**
                 * 首帧显示,控制栏消失
                 */
                setLayoutVisibility(true, true);

                /**
                 * 防止NB的服务器记不住信息,主动发送一条数据记录一下
                 *
                 */
                sendSocketMessageByPort();
            }
        });
        //TODO 播放器播放错误处理
        aliyunVodPlayer.setOnErrorListener(new IAliyunVodPlayer.OnErrorListener() {
            @Override
            public void onError(int errorCode, int arg1, String errorMsg) {
                LogUtils.e("出错时处理:  " + errorMsg + "      errorCode: " + errorCode + "    arg1: " + arg1);
                playerTipsview.setVisibility(View.VISIBLE);
                playerTipsview.setText(String.valueOf(errorMsg + ":" + errorCode));
                if (errorCode == AliyunErrorCode.ALIVC_ERR_INVALID_INPUTFILE.getCode()) {
                    //当播放本地报错4003的时候，可能是文件地址不对，也有可能是没有权限。
                    //如果是没有权限导致的，就做一个权限的错误提示。其他还是正常提示：
                    int storagePermissionRet = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (storagePermissionRet != PackageManager.PERMISSION_GRANTED) {
                        errorMsg = AliyunErrorCode.ALIVC_ERR_NO_STORAGE_PERMISSION.getDescription(getApplicationContext());
                    }
                    LogUtils.e("errorMsg:  " + errorMsg);
                }
                //关闭定时器
                stopProgressUpdateTimer();

                //出错之后解锁屏幕，防止不能做其他操作，比如返回。
                lockScreen(false);
            }
        });
        //TODO 播放完成监听
        aliyunVodPlayer.setOnCompletionListener(new IAliyunVodPlayer.OnCompletionListener() {
            @Override
            public void onCompletion() {
                //播放正常,完成时触发
                inSeek = false;
                //关闭定时器
                stopProgressUpdateTimer();
                /**
                 * 宵禁手势操作,隐藏其他的动作,防止点击界面去进行其他操作
                 */
                mGestureView.hide(ViewAction.HideType.End);
                //状态栏可操作
                setLayoutVisibility(true, false);

                //本地视频,学习中心,观看记录,课程答疑查看视频视频播放完毕后需退出本页面
                if (exitDirectly()) {
                    return;
                }
                if (continuousPlay) {
                    /**
                     * 播放下一个视频, 调用课程播放目录页面切换视频
                     */
                    onNext();
                } else {
                    playerTipsview.setVisibility(View.VISIBLE);
                    playerTipsview.setText(getResources().getString(R.string.course_completed));
                    toastInfo(getResources().getString(R.string.course_completed));
                }
            }
        });
        //TODO seek结束事件
        aliyunVodPlayer.setOnSeekCompleteListener(new IAliyunVodPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete() {
                //seek完成时触发
                inSeek = false;
            }
        });
        //TODO 播放器停止监听
        aliyunVodPlayer.setOnStoppedListner(new IAliyunVodPlayer.OnStoppedListener() {
            @Override
            public void onStopped() {
                //使用stop功能时触发
            }
        });
        //TODO 播放器清晰度变换监听
        aliyunVodPlayer.setOnChangeQualityListener(new IAliyunVodPlayer.OnChangeQualityListener() {
            @Override
            public void onChangeQualitySuccess(String finalQuality) {
                //视频清晰度切换成功后触发
                toastInfo(getResources().getString(R.string.course_qualityLoadingCompleted));

                start();//开始播放

                setCurrentQuality(finalQuality);//设置当前视频清晰度

                startProgressUpdateTimer();//开启计时器
            }

            @Override
            public void onChangeQualityFail(int code, String msg) {
                //视频清晰度切换失败时触发
                toastInfo("错误码:" + code + ",错误信息" + msg);
            }
        });
        //TODO 重播监听
        aliyunVodPlayer.setOnRePlayListener(new IAliyunVodPlayer.OnRePlayListener() {
            @Override
            public void onReplaySuccess() {
                //开始启动更新进度的定时器
                startProgressUpdateTimer();
                /**
                 * 重播不需要解禁手势操作,只需重新显示就可以(一脸懵逼)
                 */
                mGestureView.show();
            }
        });
        //TODO 自动播放监听
        aliyunVodPlayer.setOnAutoPlayListener(new IAliyunVodPlayer.OnAutoPlayListener() {
            @Override
            public void onAutoPlayStarted() {
            }
        });
        //TODO 视频尺寸变化
        aliyunVodPlayer.setOnVideoSizeChangedListener(new IAliyunVodPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(int i, int i1) {
                setSurfaceViewLayout(currentScreenSize);
            }
        });

        aliyunVodPlayer.setDisplay(surfaceview.getHolder());


        initQuality();//TODO 初始化 画质选择

        initSpeedView();//TODO 初始化 倍速选择

        initGestureView();//TODO 初始化手势控制

    }

    /**
     * 设置边播边存
     *
     * @param enable      是否开启。开启之后会根据maxDuration和maxSize决定有无缓存。
     * @param saveDir     保存目录
     * @param maxDuration 单个文件最大时长 秒
     * @param maxSize     所有文件最大大小 MB
     */
    public void setPlayingCache(boolean enable, String saveDir, int maxDuration, long maxSize) {
        if (aliyunVodPlayer != null) {
            aliyunVodPlayer.setPlayingCache(enable, saveDir, maxDuration, maxSize);
        }
    }

    /**
     * 隐藏手势和控制栏
     * 隐藏其他的动作,防止点击界面去进行其他操作
     */
    private void hideGestureAndControlViews() {
        if (mGestureView != null) {
            mGestureView.hide(ViewAction.HideType.End);
        }
    }

    /**
     * Description:VideoPlayPageActivity
     * Time:2019-4-11   下午 5:26Z
     * Detail: TODO 倍速选择
     */
    private void initSpeedView() {
        speedListviewWindow = new SpeedListviewWindow(this);
        speedListviewWindow.setOnSpeedClickListener(new SpeedListviewWindow.OnSpeedClickListener() {
            @Override
            public void onSpeedClick(float speed) {
                currentSpeed = speed;
                /**
                 * 切换视频播放倍速
                 */
                aliyunVodPlayer.setPlaySpeed(currentSpeed);
                /**
                 * 菜单显示指定倍速
                 */
                playerSpeed.setText(getResources().getString(R.string.course_speed, String.valueOf(speed)));
                //菜单延迟消失
                setLayoutVisibility(false, true);
            }
        });
    }

    /**
     * Description:VideoPlayPageActivity
     * Time:2019-4-11   下午 5:26
     * Detail: TODO 画质选择
     */
    private void initQuality() {
        qualityListviewWindow = new QualityListviewWindow(this);
        qualityListviewWindow.setOnQualityClickListener(new QualityListviewWindow.OnQualityClickListener() {
            @Override
            public void onQualityClick(String quality) {
                toastInfo(getResources().getString(R.string.course_qualityLoading));
                playerLoadingview.setVisibility(View.VISIBLE);

                //重新开始更新进度
                stopProgressUpdateTimer();

                aliyunVodPlayer.changeQuality(quality);
            }
        });
    }

    /**
     * 初始化手势控制view()  ,别问,问就是阿里的demo的手势操作代码
     */
    private void initGestureView() {
        mGestureDialogManager = new GestureDialogManager(this);
/*
        //这个方法没有嵌入到布局里,出现了一些BUG,朕只解决了一部分,最终还是干掉吧(1.0.2之后就不再使用这个方法了)
        mGestureView = new GestureView(this);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params2.setMargins(DensityUtil.dip2px(this, 90), 0, 0, 0);
        //添加到布局中
        playerRelativelayout.addView(mGestureView, params2);
*/

        //设置手势监听
        mGestureView.setOnGestureListener(new GestureView.GestureListener() {
            @Override
            public void onHorizontalDistance(float downX, float nowX) {
                //其他手势如果锁住了就不回调了。
                if (mIsFullScreenLocked) {
                    return;
                }

                if (mCurrentScreenMode != AliyunScreenMode.Small) {
                    //水平滑动调节seek。
                    // seek需要在手势结束时操作。
                    long duration = aliyunVodPlayer.getDuration();
                    long position = aliyunVodPlayer.getCurrentPosition();
                    long deltaPosition = 0;

                    if (aliyunVodPlayer.getPlayerState() == IAliyunVodPlayer.PlayerState.Prepared ||
                            aliyunVodPlayer.getPlayerState() == IAliyunVodPlayer.PlayerState.Paused ||
                            aliyunVodPlayer.getPlayerState() == IAliyunVodPlayer.PlayerState.Started) {

                        Display disp = getWindowManager().getDefaultDisplay();
                        int height = disp.getWidth();

                        //在播放时才能调整大小
                        deltaPosition = (long) (nowX - downX) * duration / height;
                    }

                    if (mGestureDialogManager != null) {
                        mGestureDialogManager.showSeekDialog(playerRelativelayout, (int) position);
                        mGestureDialogManager.updateSeekDialog(duration, position, deltaPosition);
                    }
                }
            }

            @Override
            public void onLeftVerticalDistance(float downY, float nowY) {
                //其他手势如果锁住了就不回调了。
                if (mIsFullScreenLocked) {
                    return;
                }

                if (mCurrentScreenMode != AliyunScreenMode.Small) {
                    Display disp = getWindowManager().getDefaultDisplay();
                    int height = disp.getHeight();

                    //左侧上下滑动调节亮度
                    int changePercent = (int) ((nowY - downY) * 100 / height);

                    if (mGestureDialogManager != null) {
                        mGestureDialogManager.showBrightnessDialog(playerRelativelayout);
                        int brightness = mGestureDialogManager.updateBrightnessDialog(changePercent);
                        aliyunVodPlayer.setScreenBrightness(brightness);
                    }
                }
            }

            @Override
            public void onRightVerticalDistance(float downY, float nowY) {
                //其他手势如果锁住了就不回调了。
                if (mIsFullScreenLocked) {
                    return;
                }

                if (mCurrentScreenMode != AliyunScreenMode.Small) {
                    //右侧上下滑动调节音量
                    int changePercent = (int) ((nowY - downY) * 100 / playerRelativelayout.getHeight());
                    int volume = aliyunVodPlayer.getVolume();

                    if (mGestureDialogManager != null) {
                        mGestureDialogManager.showVolumeDialog(playerRelativelayout, volume);
                        int targetVolume = mGestureDialogManager.updateVolumeDialog(changePercent);
                        currentVolume = targetVolume;
                        aliyunVodPlayer.setVolume(targetVolume);//通过返回值改变音量
                    }
                }
            }

            @Override
            public void onGestureEnd() {
                //其他手势如果锁住了就不回调了。
                if (mIsFullScreenLocked) {
                    return;
                }

                //手势结束。
                //seek需要在结束时操作。
                if (mGestureDialogManager != null) {
                    mGestureDialogManager.dismissBrightnessDialog();
                    mGestureDialogManager.dismissVolumeDialog();

                    int seekPosition = mGestureDialogManager.dismissSeekDialog();
                    long duration = aliyunVodPlayer.getDuration();
                    if (seekPosition >= duration) {
                        int i = Long.valueOf(duration).intValue() - 1000;
                        seekPosition = i;
                    }

                    if (seekPosition >= 0) {
                        seekTo(seekPosition);
                        inSeek = true;
                    }
                }
            }

            @Override
            public void onSingleTap() {
                if (mIsFullScreenLocked) {
                    if (playerLockedscreen.getVisibility() != View.VISIBLE) {
                        setLayoutVisibility(true, true);
                    } else {
                        setLayoutVisibility(false, true);
                    }
                } else {
                    //单击事件，显示控制栏
                    if (playerTopliner.getVisibility() != View.VISIBLE) {
                        setLayoutVisibility(true, true);
                    } else {
                        setLayoutVisibility(false, true);
                    }
                }
            }

            @Override
            public void onDoubleTap() {
                //其他手势如果锁住了就不回调了。
                if (mIsFullScreenLocked) {
                    return;
                }

                //双击事件，控制暂停播放
                switchPlayerState();
            }
        });

        //初始化完毕后暂时隐藏手势操作,避免有些SB乱点然后出现意外操作(比如无法返回)
        hideGestureAndControlViews();
    }

    /**
     * Description:VideoPlayPageActivity
     * Time:2019-4-3   上午 11:27
     * Detail:初使数据(接收嗟来之食,然后慢慢消化)
     */
    private void initData() {
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(this);
        userId = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        login_status = sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false);
        download_wifi = sharedPreferencesUtils.getBoolean(Constant.DOWNLOAD_WIFI, false);
        look_wifi = sharedPreferencesUtils.getBoolean(Constant.LOOK_WIFI, false);

        random = new Random();
        /**
         * 用于获取视频播放凭证
         */
        coursePlayPresenter = new CoursePlayPresenter(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            courseCoverimageUrl = bundle.getString(Constant.COURSE_COVER_IMAGE, "");//TODO 封面
            coursePackageId = bundle.getInt(Constant.COURSE_PACKAGE_ID, 0);//TODO 课程包ID
            courseUnCon = bundle.getInt(Constant.COURSE_UN_CON, 2);//TODO 是否是正课1正课2非正课
            courseBuyState = bundle.getInt(Constant.COURSE_BUY_STATE, 2);//TODO 购买状态:1购买2未购买
            course_PackagePrice = bundle.getString(Constant.COURSE_PRICE, "");//TODO 购买价格
            course_Source = bundle.getString(Constant.COURSE_SOURCE, "");//TODO 来源

            //购买状态
            setCourseBuyState(courseBuyState);
            //设置封面
            setCourse_Cover(courseCoverimageUrl);
        }

        // 上报后的Crash会显示视频标签
        CrashReport.setUserSceneTag(context, Constant.BUGLY_TAG_VIDEO);
    }

    /**
     * 累计学习积分(开始计时) 客户想要一个加积分的功能,虽然积分少的可怜
     */
    private void startIntegralCounter() {
        grandTotalSeconds = SharedPreferencesUtils.getInstance(this).getInt(Constant.INTEGRAL_COUNTER, 0);
        if (grandTotalTimer != null) {
            grandTotalTimer.postDelayed(grandTotalRunnable, CountDownInterval);
        }
    }

    /**
     * 积分统计暂停计时
     */
    public void stopIntegralCounter() {
        if (grandTotalTimer != null) {
            grandTotalTimer.removeCallbacks(grandTotalRunnable);
        }
        SharedPreferencesUtils.getInstance(this).putInt(Constant.INTEGRAL_COUNTER, grandTotalSeconds);
    }

    /**
     * 积分统计重新开始计时
     */
    public void resumeIntegralCounter() {
        if (grandTotalTimer != null) {
            grandTotalTimer.postDelayed(grandTotalRunnable, CountDownInterval);
        }
    }

    /**
     * 设置课程播放器封面
     */
    public void setCourse_Cover(String courseCoverUrl) {
        if (TextUtils.isEmpty(courseCoverUrl)) {
            courseCoverimage.setImageDrawable(ContextCompat.getDrawable(this, R.color.color_F2F7FF));
        } else {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.color.color_F2F7FF)
                    .error(R.mipmap.image_loaderror)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            GlideUtils.load(this, courseCoverUrl, courseCoverimage, requestOptions);
        }
    }

    /**
     * Description:VideoPlayPageActivity
     * Time:2019-4-10   上午 10:16
     * Detail:TODO 初始化View
     */
    private void initView() {
        videoPlayPageActivity = VideoPlayPageActivity.this;
        context = this;
        freeWatchTips = getResources().getString(R.string.course_freeWatchCompleted);

        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //重新设置菜单栏的高度
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this);
        playerTopliner.setPadding(DensityUtil.dp2px(15), statusBarHeight, DensityUtil.dp2px(15), DensityUtil.dp2px(2));

        //当前倍速设置文字
        playerSpeed.setText(getResources().getString(R.string.course_speed, String.valueOf(currentSpeed)));

        //TODO 保持屏幕常亮
        surfaceview.setKeepScreenOn(true);
        surfaceHolder = surfaceview.getHolder();
        surfaceHolder.addCallback(this);

        //seekbar的滑动监听
        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    freeWatch();
                    this.progress = (int) (progress * aliyunVodPlayer.getDuration() / seekBar.getMax());
                    //这里是用户拖动，直接设置文字进度就行，
                    playerCurrentduration.setText(TimeFormater.formatMs(aliyunVodPlayer.getCurrentPosition()));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHideHandler.removeMessages(WHAT_HIDE);
                isSeekbarTouching = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isSeekbarTouching = false;
                seekTo(progress);

                mHideHandler.removeMessages(WHAT_HIDE);
                mHideHandler.sendEmptyMessageDelayed(WHAT_HIDE, DELAY_TIME);
            }
        };
        playerSeekprogress.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    /**
     * Description:VideoPlayPageActivity
     * Time:2019-4-3   上午 11:59
     * Detail:TODO  根据课程购买状态和正课费正科创建tablayout
     */
    private void initTablayout() {
        titlesList = new ArrayList<String>();
        fragmentArrayList = new ArrayList<Fragment>();
        if (TextUtils.equals(course_Source, Constant.COLLECTION)) {
            //课程收藏
            courseAnswerQuestionFragment = new CourseAnswerQuestionFragment();
            fragmentArrayList.add(courseAnswerQuestionFragment);
            titlesList.add(getResources().getString(R.string.course_ask));
        } else if (TextUtils.equals(course_Source, Constant.WATCH_RECORD) || TextUtils.equals(course_Source, Constant.WATCH_LEARNPLAN) ||
                TextUtils.equals(course_Source, Constant.LOCAL_CACHE) || TextUtils.equals(course_Source, Constant.WATCH_ANSWERDETAILED)) {
            //TODO 观看记录,学习计划,本地视频,课程答疑
        } else {
            //common视频播放
            courseIntroductionFragment = new CourseIntroductionFragment();
            courseDirectoryListFragment = new CourseDirectoryListFragment();
            courseAnswerQuestionFragment = new CourseAnswerQuestionFragment();

            fragmentArrayList.add(courseIntroductionFragment);
            fragmentArrayList.add(courseDirectoryListFragment);
            fragmentArrayList.add(courseAnswerQuestionFragment);
            titlesList.add(getResources().getString(R.string.course_introduce));
            titlesList.add(getResources().getString(R.string.course_directory));
            titlesList.add(getResources().getString(R.string.course_ask));
        }
        if (fragmentArrayList != null && fragmentArrayList.size() > 0) {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            CommonTabAdapter commonTabAdapter = new CommonTabAdapter(supportFragmentManager, fragmentArrayList, titlesList);
            viewpager.setAdapter(commonTabAdapter);
            viewpager.setOffscreenPageLimit(fragmentArrayList.size());
            tablayout.setupWithViewPager(viewpager);
            tablayout.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(XTabLayout.Tab tab) {
                    int position = tab.getPosition();
                    switch (position) {
                        case 0:
                            updateBuyUI();
                            break;
                        case 1:
                        case 2:
                            if (linearPayCourse.getVisibility() == View.VISIBLE) {
                                linearPayCourse.setVisibility(View.GONE);
                            }
                            break;
                        default:
                            break;
                    }
                    tab.select();
                    viewpager.setCurrentItem(position);
                }

                @Override
                public void onTabUnselected(XTabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(XTabLayout.Tab tab) {

                }
            });
        }
    }

    /**
     * 输出日志
     */
    private void output(String text) {
        LogUtils.e("Socket---" + text);
    }

    /**
     * 向服务端发送消息
     */
    private void sendSocketMessage() {
        int seconds = 0;
        if (aliyunVodPlayer != null) {
            seconds = Integer.parseInt(String.valueOf(aliyunVodPlayer.getCurrentPosition() / 1000));
        }
        if (currentCourseID != 0 && currentSectionID != 0 && currentVideoID != 0 && seconds != 0) {
            CourseSocketBean courseSocketBean = new CourseSocketBean();
            courseSocketBean.setUser_id(userId);//用户id
            courseSocketBean.setPackage_id(coursePackageId);//	课程包id
            courseSocketBean.setCourse_id(currentCourseID);//	专业课id
            courseSocketBean.setSection_id(currentSectionID);//章节id
            courseSocketBean.setVideo_id(currentVideoID);//视频id
            courseSocketBean.setWatch_time(seconds);//	观看截止时间点
            courseSocketBean.setVideo_type(1);//	播放类型1课程视频播放
            if (TextUtils.equals(Constant.WATCH_LEARNPLAN, course_Source)) {//学习中心socket信息
                courseSocketBean.setStatus(2);
                courseSocketBean.setPlan_id(learnPlanid);
                courseSocketBean.setDays(learnDays);
                if (learnDays == 0 || learnPlanid == 0) {
                    //TODO nothing
                } else {
                    String message = new Gson().toJson(courseSocketBean);
                    if (mWebSocket != null) {
                        mWebSocket.send(message);
                    }
                }
            } else {
                courseSocketBean.setStatus(1);//	视频类型1课程视频播放

                String message = new Gson().toJson(courseSocketBean);
                if (mWebSocket != null) {
                    mWebSocket.send(message);
                }
            }
            /*String message = new Gson().toJson(courseSocketBean);
            output("onMessage message: " + message);
            if (mWebSocket != null) {
                mWebSocket.send(message);
            }*/
        }
    }

    private void sendSocketMessageByPort() {
        int seconds = 0;
        if (aliyunVodPlayer != null) {
            seconds = Integer.parseInt(String.valueOf(aliyunVodPlayer.getCurrentPosition() / 1000));
        }
        if (currentCourseID != 0 && currentSectionID != 0 && currentVideoID != 0) {
            CourseSocketBean courseSocketBean = new CourseSocketBean();
            courseSocketBean.setUser_id(userId);//用户id
            courseSocketBean.setPackage_id(coursePackageId);//	课程包id
            courseSocketBean.setCourse_id(currentCourseID);//	专业课id
            courseSocketBean.setSection_id(currentSectionID);//章节id
            courseSocketBean.setVideo_id(currentVideoID);//视频id
            courseSocketBean.setWatch_time(seconds);//	观看截止时间点
            courseSocketBean.setVideo_type(1);//	播放类型1课程视频播放
            if (TextUtils.equals(Constant.WATCH_LEARNPLAN, course_Source)) {
                //学习中心socket信息
                courseSocketBean.setStatus(2);
                courseSocketBean.setPlan_id(learnPlanid);
                courseSocketBean.setDays(learnDays);
            } else {
                //	视频类型1课程视频播放
                courseSocketBean.setStatus(1);
            }

            if (coursePlayPresenter != null) {
                coursePlayPresenter.sendFirstSocket(courseSocketBean);
            }
        }
    }

    /**
     * seek操作
     */
    public void seekTo(int position) {
        if (aliyunVodPlayer == null) {
            return;
        }
        inSeek = true;
        aliyunVodPlayer.seekTo(position);
        start();
    }

    /**
     * 初次播放需要从指定位置播放
     */
    public void preparedStart(int position) {
        if (aliyunVodPlayer == null) {
            return;
        }
        aliyunVodPlayer.seekTo(position);
        start();
    }

    /**
     * Description:VideoPlayPageActivity
     * Time:2019-4-15   上午 9:58
     * Detail:设置弹框
     */
    private void initSettingsDialog() {
        if (playSettingWindow == null) {
            playSettingWindow = new PlaySettingWindow(this, playerRelativelayout.getHeight(), currentScreenSize, continuousPlay);
        }
        playSettingWindow.showAsDropDown(playerRelativelayout);
        setLayoutVisibility(false, false);
        playSettingWindow.setScreenSizeCheckLister(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_screensize_50:
                        currentScreenSize = 0.5;
                        setSurfaceViewLayout(currentScreenSize);
                        break;
                    case R.id.rb_screensize_75:
                        currentScreenSize = 0.75;
                        setSurfaceViewLayout(currentScreenSize);
                        break;
                    case R.id.rb_screensize_100:
                        currentScreenSize = 1;
                        setSurfaceViewLayout(currentScreenSize);
                        break;
                    default:
                        break;
                }
            }
        });

        playSettingWindow.setSwitchViewCheckLister(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                view.toggleSwitch(true); // or false
                continuousPlay = true;
            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.toggleSwitch(false); // or true
                continuousPlay = false;
            }
        });
    }

    //TODO 讲义切换按钮操作
    private void switchPdfHandouts() {
        if (getCourseBuyState() != Constant.HAVED_BUY) {//todo 未购买
            toastInfo(getResources().getString(R.string.course_buyCourse));
        } else {//todo 已购买
            if (pdfExists) {//TODO 讲义存在
                if (pdfDownloadStatus) {//TODO PDF文件下载中
                    toastInfo(getResources().getString(R.string.course_PDFloading));
                } else {//TODO PDF文件下载完毕
                    if (!pdfStatus) {//查看讲义
                        pdfView.setVisibility(View.VISIBLE);
                        playerPDFPage.setVisibility(View.VISIBLE);
                        playerHandouts.setText(getResources().getString(R.string.video));
                        pdfStatus = !pdfStatus;

                        /**
                         * 手势,你被宵禁了
                         */
                        mGestureView.hide(ViewAction.HideType.End);

                        //提问按钮隐藏
                        playerAskQuestion.setVisibility(View.GONE);
                        //PDF退出按钮显示
                        playerExitPdf.setVisibility(View.VISIBLE);

                        setLayoutVisibility(false, false);
                        toastInfo(getResources().getString(R.string.course_PDF_changeToPDF));
                    } else {//退出讲义
                        pdfView.setVisibility(View.GONE);
                        playerPDFPage.setVisibility(View.GONE);
                        playerHandouts.setText(getResources().getString(R.string.notes));
                        pdfStatus = !pdfStatus;
                        /**
                         * 解放手势操作
                         */
                        mGestureView.setHideType(ViewAction.HideType.Normal);
                        mGestureView.show();

                        playerAskQuestion.setVisibility(View.VISIBLE);//提问按钮
                        playerExitPdf.setVisibility(View.GONE);//PDF退出按钮显示

                        setLayoutVisibility(true, true);
                        toastInfo(getResources().getString(R.string.course_PDF_changeToVideo));
                    }
                }
            } else {//TODO 讲义不存在
                toastInfo(getResources().getString(R.string.course_PDFnotExits));
            }
        }
    }

    //TODO 设置购买按钮
    public void updateBuyUI() {
        if (getCourseBuyState() == Constant.HAVED_BUY) {
            if (linearPayCourse.getVisibility() == View.VISIBLE) {
                linearPayCourse.setVisibility(View.GONE);
            }
        } else {
            if (linearPayCourse.getVisibility() == View.GONE) {
                linearPayCourse.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 提问
     */
    private void askCourseQuestion() {
        if (login_status) {
            if (getCourseBuyState() == Constant.HAVED_BUY) {//todo 已购买
                if (getCourseUnCon() == Constant.COURSE_UNCON) {//todo 正课
                    if (getScreenMode() == AliyunScreenMode.Full) {
                        AliyunScreenMode targetMode2;
                        if (mCurrentScreenMode == AliyunScreenMode.Small) {
                            targetMode2 = AliyunScreenMode.Full;
                        } else {
                            targetMode2 = AliyunScreenMode.Small;
                        }
                        changeScreenMode(targetMode2);
                    }
                    int finalPosition = Math.round(aliyunVodPlayer.getCurrentPosition() / 1000);

                    Intent intent = new Intent(videoPlayPageActivity, QuestionAskQuestionActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.TYPE, Constant.TYPE_COURSE_ASK);//TODO 提问类型
                    bundle.putInt(Constant.PACKAGE_ID, coursePackageId);//TODO 课程包ID
                    bundle.putInt(Constant.COURSE_ID, currentCourseID);//TODO 课程ID
                    bundle.putInt(Constant.VIDEO_ID, currentVideoID);//TODO 视频videoid
                    bundle.putInt(Constant.SECTION_ID, currentSectionID);//TODO 章节ID
                    bundle.putInt(Constant.VIDEO_TIME, finalPosition);//TODO 视频节点
                    bundle.putString(Constant.VIDEO_TITLE, playVideoName);//TODO 视频标题
                    intent.putExtras(bundle);
                    startActivity(intent);

                    pause();
                } else {
                    toastInfo(getResources().getString(R.string.course_notAsk));
                }
            } else {
                toastInfo(getResources().getString(R.string.course_buyCourse));
            }
        } else {
            goToLogin();
        }
    }

    /**
     * ganjin入坑
     */
    public void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    //TODO 收藏按钮
    public void switchCollectionStatus() {
        if (!login_status) {
            goToLogin();
            return;
        }
        if (currentVideoCollectState == 1) {
            //取消收藏操作
            coursePlayPresenter.getVideoCollect(coursePackageId, currentCourseID, currentSectionID, currentVideoID, userId, currentVideoCollectState);
        } else {
            //收藏视频需要的条件
            if (getCourseBuyState() != Constant.HAVED_BUY) {
                //未购买
                toastInfo(getResources().getString(R.string.course_buyCourse));
            } else {
                //已购买
                coursePlayPresenter.getVideoCollect(coursePackageId, currentCourseID, currentSectionID, currentVideoID, userId, currentVideoCollectState);
            }
        }
    }

    //TODO 顶部返回按键
    private void topButtonBack() {
        if (getScreenMode() == AliyunScreenMode.Full) {
            if (exitDirectly()) {
            } else {
                AliyunScreenMode targetMode;
                if (mCurrentScreenMode == AliyunScreenMode.Small) {
                    targetMode = AliyunScreenMode.Full;
                } else {
                    targetMode = AliyunScreenMode.Small;
                }
                changeScreenMode(targetMode);
            }
        } else {
            finish();//退出本页面
        }
    }

    //TODO 设置surfaceview的布局
    private void setSurfaceViewLayout(double screenSize) {
        RelativeLayout.LayoutParams params = getScreenSizeParams(screenSize);
        if (params != null) {
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
        }
        surfaceview.setLayoutParams(params);
    }

    /**
     * 这个函数,讲真的,布吉岛
     */
    private RelativeLayout.LayoutParams getScreenSizeParams(double screenSize) {
        int width = 600;
        int height = 400;
        if (mCurrentScreenMode == AliyunScreenMode.Small) {
            width = windowManager.getDefaultDisplay().getWidth();
            height = playerRelativelayout.getLayoutParams().height;
            int width2 = (int) (width * screenSize);
            int height2 = (int) (height * screenSize);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width2, height2);
            return params;
        } else if (mCurrentScreenMode == AliyunScreenMode.Full) {
            width = windowManager.getDefaultDisplay().getWidth();
            height = windowManager.getDefaultDisplay().getHeight();
            width = (int) (width * screenSize);
            height = (int) (height * screenSize);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
            return params;
        }
        return null;
    }

    /**
     * Description:VideoPlayPageActivity
     * Time:2019-4-23   上午 10:26
     * Detail:TODO 获取视频播放凭证之后再播放视频
     */
    @Override
    public void getVideoPlayAuthor(GetVideoPlayAuthBean data) {
        if (data == null) {
            playerTipsview.setText(getResources().getString(R.string.course_getInfoError));
            playerTipsview.setVisibility(View.VISIBLE);
            courseCoverimage.setVisibility(courseCoverimage.getVisibility() == View.VISIBLE ? View.GONE : View.INVISIBLE);
        } else {
            if (data.getCode() == 200) {
                //开始加载,进度
                playerLoadingview.setVisibility(View.VISIBLE);
                playerTipsview.setVisibility(View.GONE);
                //封面隐藏
                courseCoverimage.setVisibility(courseCoverimage.getVisibility() == View.VISIBLE ? View.GONE : View.INVISIBLE);

                //获取播放凭证
                String playAuth = data.getData().getPlayAuth();
                //获取视频时间
                int time = data.getData().getWatch_time();
                if (getCourseBuyState() == Constant.HAVED_BUY) {
                    watch_time = time * 1000;
                }
                //当前播放视频收藏状态
                currentVideoCollectState = data.getData().getCollect();
                if (currentVideoCollectState == 2) {
                    //2 没有收藏
                    playerCollect.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_icon_playunstar));
                } else {
                    playerCollect.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_icon_playstar));
                }
                String handouts = data.getData().getHandouts();
                if (!TextUtils.isEmpty(handouts)) {
                    loadingCurrentVideoPDF(handouts, currentVid);
                    //讲义存在
                    pdfExists = true;
                } else {
                    //讲义不存在
                    pdfExists = false;
                }
                if (!TextUtils.isEmpty(data.getData().getTitle())) {
                    String title = data.getData().getTitle();
                    if (title.endsWith(".mp4")) {
                        title = title.replace(".mp4", "").trim();
                    }
                    setPlayVideoName(title);
                }
                /**
                 * 别问,问就是播放视频的必须要走的几部,安全加密播放
                 */
                AliyunPlayAuth.AliyunPlayAuthBuilder aliyunPlayAuthBuilder = new AliyunPlayAuth.AliyunPlayAuthBuilder();
                aliyunPlayAuthBuilder.setVid(currentVid);
                aliyunPlayAuthBuilder.setPlayAuth(playAuth);
                aliyunPlayAuthBuilder.setQuality(IAliyunVodPlayer.QualityValue.QUALITY_LOW);
                currentaAliyunPlayAuth = aliyunPlayAuthBuilder.build();
                prepareAuth(currentaAliyunPlayAuth);
            }
        }
    }

    /**
     * Description:VideoPlayPageActivity
     * Time:2019-4-23   上午 10:26
     * Detail:TODO 视频收藏结果
     */
    @Override
    public void getVideoCollectResult(int data, int result) {
        switch (data) {
            case 1://收藏接口
                if (result == 1) {//TODO success
                    playerCollect.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_icon_playstar));
                    //设置文字前景色
                    ToastUtil.showBottomLongText(VideoPlayPageActivity.this, getResources().getString(R.string.course_collectionSuccess));
                    currentVideoCollectState = 1;//已收藏
                } else if (result == 2) {//TODO failed
                    playerCollect.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_icon_playunstar));
                    ToastUtil.showBottomLongText(VideoPlayPageActivity.this, getResources().getString(R.string.operation_Error));
                }
                break;
            case 2://取消收藏接口
                if (result == 1) {
                    playerCollect.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_icon_playunstar));

                    ToastUtil.showBottomLongText(VideoPlayPageActivity.this, getResources().getString(R.string.course_collectionCancel));
                    currentVideoCollectState = 2;//已取消收藏  question_tips_collection0
                } else if (result == 2) {
                    playerCollect.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_icon_playunstar));
                    ToastUtil.showBottomLongText(VideoPlayPageActivity.this, getResources().getString(R.string.operation_Error));
                }
                break;
            default:
                break;
        }
    }

    /**
     * Description:VideoPlayPageActivity
     * Time:2019-4-23   上午 10:29
     * Detail: 提示信息
     */
    public void toastInfo(String message) {
        ToastUtil.showBottomLongText(VideoPlayPageActivity.this, message);
    }

    //TODO surfaceview创建
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //LogUtils.e(" surfaceCreated = surfaceHolder = " + surfaceHolder);
        aliyunVodPlayer.setDisplay(holder);
    }

    //TODO surfaceview变更
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        holder.setFixedSize(width, height);
        aliyunVodPlayer.surfaceChanged();
        LogUtils.e(" surfaceChanged surfaceHolder = " + surfaceHolder + " ,  width = " + width + " , height = " + height);
    }

    //TODO surfaceview销毁
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogUtils.e(" surfaceDestroyed = surfaceHolder = " + surfaceHolder);
    }

    /**
     * Description:VideoPlayPageActivity
     * Time:2019-4-22   上午 10:13
     * Detail:TODO  网络转台变换监听事件
     */
    private boolean flowFlag = false;

    private class MyNetChangeListener implements NetWatchdog.NetChangeListener {
        private WeakReference<VideoPlayPageActivity> weakReference;

        MyNetChangeListener(VideoPlayPageActivity videoPlayPageActivity) {
            weakReference = new WeakReference<>(videoPlayPageActivity);
        }

        @Override
        public void onWifiTo4G() {//TODO  WiFi转到4G(当前是数据网络)
            pause();
            //LogUtils.e("onWifiTo4G()------------------" + look_wifi);

            //隐藏其他的动作,防止点击界面去进行其他操作
            if (mGestureView != null) {
                mGestureView.hide(ViewAction.HideType.Normal);
            }

            if (look_wifi) {
                //TODO 仅在WIFI下观看视频,4G无法播放,播放需要同意
                if (flowFlag) {
                    //已经在本次播放允许4G
                    start();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayPageActivity.this, R.style.WhiteDialogStyle);
                    builder.setTitle(context.getResources().getString(R.string.explication));
                    builder.setMessage(context.getResources().getString(R.string.course_consumeFlow));
                    builder.setPositiveButton(context.getResources().getString(R.string.continue_play), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            flowFlag = true;
                        }
                    });
                    builder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.setCancelable(false);
                    builder.create();
                    builder.show();
                }
            } else {
                //TODO 用户已在设置中心允许4g播放
                start();
            }
        }

        @Override
        public void on4GToWifi() {
            //TODO  4G转到WiFi
            //LogUtils.e("网络状态--------------------on4GToWifi");
        }

        @Override
        public void onNetDisconnected() {
            //TODO //网络断开。由于安卓这块网络切换的时候，有时候也会先报断开。所以这个回调是不准确的。
            //LogUtils.e("网络状态--------------------onNetDisconnected");
        }
    }

    /**
     * 断网/连网监听
     */
    private class MyNetConnectedListener implements NetWatchdog.NetConnectedListener {
        @Override
        public void onReNetConnected(boolean isReconnect) {//重新连接
            //LogUtils.e("网络状态---------MyNetConnectedListener---------------onReNetConnected" + isReconnect);
            currentError = ErrorInfo.Normal;
            if (isReconnect) {
                toastInfo(getResources().getString(R.string.NetworkConnected));//管他准不准,先给个提示再说
            }
        }

        @Override
        public void onNetUnConnected() {//网络未连接
            //LogUtils.e("网络状态---------MyNetConnectedListener----------------onNetUnConnected");
            currentError = ErrorInfo.UnConnectInternet;

            toastInfo(getResources().getString(R.string.NetworkDisconnected));//管他准不准,先给个提示再说
        }
    }


    /**
     * 播放状态   1.播放中 2.未播放
     */
    public enum PlayState {
        Playing, NotPlaying
    }

    @OnClick({R.id.player_back, R.id.player_share, R.id.player_btn, R.id.player_fullscreen,
            R.id.btn_call, R.id.btn_pay, R.id.player_quality, R.id.player_speed, R.id.player_handouts,
            R.id.player_setting, R.id.player_collect, R.id.player_askquestion, R.id.player_exitPdf, R.id.playerLockedscreen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.player_back:
                //TODO  顶部返回键
                topButtonBack();
                break;
            case R.id.player_share:
                //TODO 顶部分享按键
                new ShareDialog(this).builder()
                        .setFriendButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //String url = ApiStores.APP_DOWNLOAD_URL;
                                String url = ApiStores.COURSER_SHARE + "?package_id=" + coursePackageId + "&VideoId=" + currentVid;
                                String title = getResources().getString(R.string.app_nameWX);
                                String desc = getResources().getString(R.string.youcaiWXShareDescribe);
                                ShareUtils.getInstance().shareUrlToWx(url, title, desc, SendMessageToWX.Req.WXSceneSession);
                                //设置签到积分
                                EarnIntegralPresenter.getInstance().setIntegralType(Constant.INTEGRAL_SHARE);
                            }
                        })
                        .setCircleToFriendButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //String url = ApiStores.APP_DOWNLOAD_URL;
                                String url = ApiStores.COURSER_SHARE + "?package_id=" + coursePackageId + "&VideoId=" + currentVid;
                                String title = getResources().getString(R.string.app_nameWX);
                                String desc = getResources().getString(R.string.youcaiWXShareDescribe);
                                ShareUtils.getInstance().shareUrlToWx(url, title, desc, SendMessageToWX.Req.WXSceneTimeline);
                                //设置签到积分
                                EarnIntegralPresenter.getInstance().setIntegralType(Constant.INTEGRAL_SHARE);
                            }
                        })
                        .show();
                break;
            case R.id.player_collect:
                //TODO 顶部收藏按键
                switchCollectionStatus();
                break;
            case R.id.player_btn:
                //TODO 播放暂停控制按钮
                switchPlayerState();
                break;
            case R.id.player_fullscreen:
                //TODO 横竖屏切换
                AliyunScreenMode targetMode;
                if (mCurrentScreenMode == AliyunScreenMode.Small) {
                    targetMode = AliyunScreenMode.Full;
                } else {
                    targetMode = AliyunScreenMode.Small;
                }
                changeScreenMode(targetMode);
                break;
            case R.id.player_quality:
                //TODO 画质切换
                if (mAliyunMediaInfo != null) {
                    List<String> qualities = mAliyunMediaInfo.getQualities();//获取清晰度列表
                    if (qualityListviewWindow != null) {
                        qualityListviewWindow.setQuality(qualities, mCurrentQuality);
                        qualityListviewWindow.showAsDropDown(playerQuality);
                    }
                }
                break;
            case R.id.player_speed:
                //TODO 倍速播放切换
                if (speedListviewWindow != null) {
                    speedListviewWindow.setCurrentSpeed(currentSpeed);//当前倍速播放
                    speedListviewWindow.showAsDropDown(playerSpeed);
                    setLayoutVisibility(true, false);
                }
                break;
            case R.id.player_handouts:
                //TODO 讲义切换
                switchPdfHandouts();
                break;
            case R.id.player_setting:
                //TODO 设置
                initSettingsDialog();//设置弹框弹框
                break;
            case R.id.player_askquestion:
                //TODO 提问问题按钮
                askCourseQuestion();//提问问题
                break;
            case R.id.player_exitPdf:
                //TODO 退出PDF
                switchPdfHandouts();
                break;
            case R.id.playerLockedscreen:
                //TODO 锁屏操作
                lockScreen(!mIsFullScreenLocked);
                break;
            case R.id.btn_call:
                //TODO 客服电话
                makeCall();
                break;
            case R.id.btn_pay:
                //TODO 直接购买课程
                Intent intent = new Intent(this, CommitOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.COURSE_PACKAGE_ID, getCoursePackageId());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public int getCoursePackageId() {
        return coursePackageId;
    }

    public AliyunScreenMode getScreenMode() {
        return mCurrentScreenMode;
    }

    public void setPlayVideoName(String playVideoName) {
        this.playVideoName = playVideoName;
        //设置播放标题
        playerVideotitle.setText(playVideoName);
    }

    public int getCourseUnCon() {
        return courseUnCon;
    }

    public void setCourseUnCon(int courseUnCon) {
        this.courseUnCon = courseUnCon;
    }

    public void setCourseBuyState(int courseBuyState) {
        this.courseBuyState = courseBuyState;
        updateBuyUI();
    }

    public int getCourseBuyState() {
        return courseBuyState;
    }

    public void setCourse_PackagePrice(String course_PackagePrice) {
        this.course_PackagePrice = course_PackagePrice;
        //截取.之前的字符串
        String substring = course_PackagePrice.substring(0, course_PackagePrice.indexOf("."));
        btnPay.setText(String.valueOf(getResources().getString(R.string.RMB) + substring + getResources().getString(R.string.course_baoming)));
    }

    public void makeCall() {
        SoulPermission.getInstance()
                .checkAndRequestPermission(Manifest.permission.CALL_PHONE, new CheckRequestPermissionListener() {
                    @Override
                    public void onPermissionOk(Permission permission) {
                        CallUtils.makeCall(VideoPlayPageActivity.this, Constant.SERVICE_NUM);
                    }

                    @Override
                    public void onPermissionDenied(Permission permission) {
                        if (permission.shouldRationale()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayPageActivity.this, R.style.WhiteDialogStyle);
                            builder.setTitle(getResources().getString(R.string.explication));
                            builder.setMessage(getResources().getString(R.string.permission_call));
                            builder.setPositiveButton(getResources().getString(R.string.donner), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //用户确定以后，重新执行请求原始流程
                                    SoulPermission.getInstance().goApplicationSettings();
                                }
                            });
                            builder.create();
                            builder.show();
                        } else {
                            Toast.makeText(VideoPlayPageActivity.this, getResources().getString(R.string.permission_explication), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
