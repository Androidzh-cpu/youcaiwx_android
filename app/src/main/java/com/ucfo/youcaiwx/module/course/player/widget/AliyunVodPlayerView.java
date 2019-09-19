package com.ucfo.youcaiwx.module.course.player.widget;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alivc.player.AliyunErrorCode;
import com.alivc.player.VcPlayerLog;
import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.AliyunMediaInfo;
import com.aliyun.vodplayer.media.AliyunPlayAuth;
import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:29117
 * Time: 2019-4-9.  下午 5:08
 * Email:2911743255@qq.com
 * ClassName: AliyunVodPlayerView
 */
public class AliyunVodPlayerView extends RelativeLayout {
    private static final String TAG = AliyunVodPlayerView.class.getSimpleName();
    /**
     * 判断VodePlayer 是否加载完成
     */
    private Map<AliyunMediaInfo, Boolean> hasLoadEnd = new HashMap<>();


    private SurfaceView mSurfaceView;
    private AliyunVodPlayer mAliyunVodPlayer;
    private AliyunMediaInfo mAliyunMediaInfo;
    //封面view
    private ImageView mCoverView;
    //对外的各种事件监听
    private IAliyunVodPlayer.OnInfoListener mOutInfoListener = null;
    private IAliyunVodPlayer.OnErrorListener mOutErrorListener = null;
    private IAliyunVodPlayer.OnRePlayListener mOutRePlayListener = null;
    private IAliyunVodPlayer.OnPcmDataListener mOutPcmDataListener = null;
    private IAliyunVodPlayer.OnAutoPlayListener mOutAutoPlayListener = null;
    private IAliyunVodPlayer.OnPreparedListener mOutPreparedListener = null;
    private IAliyunVodPlayer.OnCompletionListener mOutCompletionListener = null;
    private IAliyunVodPlayer.OnSeekCompleteListener mOuterSeekCompleteListener = null;
    private IAliyunVodPlayer.OnChangeQualityListener mOutChangeQualityListener = null;
    private IAliyunVodPlayer.OnFirstFrameStartListener mOutFirstFrameStartListener = null;
    private IAliyunVodPlayer.OnTimeExpiredErrorListener mOutTimeExpiredErrorListener = null;
    private IAliyunVodPlayer.OnUrlTimeExpiredListener mOutUrlTimeExpiredListener = null;
    //目前支持的几种播放方式
    private AliyunPlayAuth mAliyunPlayAuth;
    private AliyunLocalSource mAliyunLocalSource;
    private AliyunVidSts mAliyunVidSts;

    public AliyunVodPlayerView(Context context) {
        super(context);
        initVideoView();
    }

    public AliyunVodPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVideoView();
    }

    public AliyunVodPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVideoView();
    }

    /**
     * 初始化view
     */
    private void initVideoView() {
        //初始化播放用的surfaceView
        initSurfaceView();
        //初始化播放器
        initAliVcPlayer();
        //初始化封面
        initCoverView();
    }

    /**
     * 初始化播放器显示view
     */
    private void initSurfaceView() {
        mSurfaceView = new SurfaceView(getContext().getApplicationContext());
        addSubView(mSurfaceView);

        SurfaceHolder holder = mSurfaceView.getHolder();
        //增加surfaceView的监听
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                VcPlayerLog.d(TAG, " surfaceCreated = surfaceHolder = " + surfaceHolder);
                mAliyunVodPlayer.setDisplay(surfaceHolder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width,
                                       int height) {
                VcPlayerLog.d(TAG,
                        " surfaceChanged surfaceHolder = " + surfaceHolder + " ,  width = " + width + " , height = "
                                + height);
                mAliyunVodPlayer.surfaceChanged();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                VcPlayerLog.d(TAG, " surfaceDestroyed = surfaceHolder = " + surfaceHolder);
            }
        });
    }

    /**
     * addSubView 添加子view到布局中
     *
     * @param view 子view
     */
    private void addSubView(View view) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(view, params);//添加到布局中
    }

    /**
     * 初始化播放器
     */
    private void initAliVcPlayer() {
        mAliyunVodPlayer = new AliyunVodPlayer(getContext());
        mAliyunVodPlayer.enableNativeLog();
        //设置准备回调
        mAliyunVodPlayer.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                if (mAliyunVodPlayer == null) {
                    return;
                }

                mAliyunMediaInfo = mAliyunVodPlayer.getMediaInfo();
                if (mAliyunMediaInfo == null) {
                    return;
                }
                //防止服务器信息和实际不一致
                mAliyunMediaInfo.setDuration((int) mAliyunVodPlayer.getDuration());
                //使用用户设置的标题
                mAliyunMediaInfo.setTitle(getTitle(mAliyunMediaInfo.getTitle()));
                mAliyunMediaInfo.setPostUrl(getPostUrl(mAliyunMediaInfo.getPostUrl()));

                setCoverUri(mAliyunMediaInfo.getPostUrl());
                //准备成功之后可以调用start方法开始播放
                if (mOutPreparedListener != null) {
                    mOutPreparedListener.onPrepared();
                }
            }
        });
        //播放器出错监听
        mAliyunVodPlayer.setOnErrorListener(new IAliyunVodPlayer.OnErrorListener() {
            @Override
            public void onError(int errorCode, int errorEvent, String errorMsg) {
                if (errorCode == AliyunErrorCode.ALIVC_ERR_INVALID_INPUTFILE.getCode()) {
                    //当播放本地报错4003的时候，可能是文件地址不对，也有可能是没有权限。
                    //如果是没有权限导致的，就做一个权限的错误提示。其他还是正常提示：
                    int storagePermissionRet = ContextCompat.checkSelfPermission(
                            AliyunVodPlayerView.this.getContext().getApplicationContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (storagePermissionRet != PackageManager.PERMISSION_GRANTED) {
                        errorMsg = AliyunErrorCode.ALIVC_ERR_NO_STORAGE_PERMISSION.getDescription(getContext());
                    }
                }

                if (mOutErrorListener != null) {
                    mOutErrorListener.onError(errorCode, errorEvent, errorMsg);
                }

            }
        });

        //播放器加载回调
        mAliyunVodPlayer.setOnLoadingListener(new IAliyunVodPlayer.OnLoadingListener() {
            @Override
            public void onLoadStart() {
            }

            @Override
            public void onLoadEnd() {
            }

            @Override
            public void onLoadProgress(int percent) {
            }
        });
        //播放结束
        mAliyunVodPlayer.setOnCompletionListener(new IAliyunVodPlayer.OnCompletionListener() {
            @Override
            public void onCompletion() {

                if (mOutCompletionListener != null) {
                    mOutCompletionListener.onCompletion();
                }
            }
        });
        mAliyunVodPlayer.setOnBufferingUpdateListener(new IAliyunVodPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(int percent) {
            }
        });
        //播放信息监听
        mAliyunVodPlayer.setOnInfoListener(new IAliyunVodPlayer.OnInfoListener() {
            @Override
            public void onInfo(int arg0, int arg1) {
                if (mOutInfoListener != null) {
                    mOutInfoListener.onInfo(arg0, arg1);
                }
            }
        });
        //切换清晰度结果事件
        mAliyunVodPlayer.setOnChangeQualityListener(new IAliyunVodPlayer.OnChangeQualityListener() {
            @Override
            public void onChangeQualitySuccess(String finalQuality) {
                start();
                if (mOutChangeQualityListener != null) {
                    mOutChangeQualityListener.onChangeQualitySuccess(finalQuality);
                }
            }

            @Override
            public void onChangeQualityFail(int code, String msg) {
                if (code == CODE_SAME_QUALITY) {
                    if (mOutChangeQualityListener != null) {
                        mOutChangeQualityListener.onChangeQualitySuccess(mAliyunVodPlayer.getCurrentQuality());
                    }
                } else {
                    stop();
                    if (mOutChangeQualityListener != null) {
                        mOutChangeQualityListener.onChangeQualityFail(code, msg);
                    }
                }
            }
        });
        //重播监听
        mAliyunVodPlayer.setOnRePlayListener(new IAliyunVodPlayer.OnRePlayListener() {
            @Override
            public void onReplaySuccess() {
                if (mOutRePlayListener != null) {
                    mOutRePlayListener.onReplaySuccess();
                }
            }
        });
        //自动播放
        mAliyunVodPlayer.setOnAutoPlayListener(new IAliyunVodPlayer.OnAutoPlayListener() {
            @Override
            public void onAutoPlayStarted() {

                if (mOutAutoPlayListener != null) {
                    mOutAutoPlayListener.onAutoPlayStarted();
                }
            }
        });
        //seek结束事件
        mAliyunVodPlayer.setOnSeekCompleteListener(new IAliyunVodPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete() {
                if (mOuterSeekCompleteListener != null) {
                    mOuterSeekCompleteListener.onSeekComplete();
                }
            }
        });
        //PCM原始数据监听
        mAliyunVodPlayer.setOnPcmDataListener(new IAliyunVodPlayer.OnPcmDataListener() {
            @Override
            public void onPcmData(byte[] data, int size) {
                if (mOutPcmDataListener != null) {
                    mOutPcmDataListener.onPcmData(data, size);
                }
            }
        });
        //第一帧显示
        mAliyunVodPlayer.setOnFirstFrameStartListener(new IAliyunVodPlayer.OnFirstFrameStartListener() {
            @Override
            public void onFirstFrameStart() {

                mCoverView.setVisibility(GONE);
                if (mOutFirstFrameStartListener != null) {
                    mOutFirstFrameStartListener.onFirstFrameStart();
                }
            }
        });
        //url过期监听
        mAliyunVodPlayer.setOnUrlTimeExpiredListener(new IAliyunVodPlayer.OnUrlTimeExpiredListener() {
            @Override
            public void onUrlTimeExpired(String vid, String quality) {
                System.out.println("abc : onUrlTimeExpired");
                if (mOutUrlTimeExpiredListener != null) {
                    mOutUrlTimeExpiredListener.onUrlTimeExpired(vid, quality);
                }
            }
        });

        //请求源过期信息
        mAliyunVodPlayer.setOnTimeExpiredErrorListener(new IAliyunVodPlayer.OnTimeExpiredErrorListener() {
            @Override
            public void onTimeExpiredError() {
                System.out.println("abc : onTimeExpiredError");
                Log.e("radish : ", "onTimeExpiredError: " + mAliyunMediaInfo.getVideoId());
            }
        });

        mAliyunVodPlayer.setDisplay(mSurfaceView.getHolder());
    }


    /**
     * 初始化封面
     */
    private void initCoverView() {
        mCoverView = new ImageView(getContext());
        //这个是为了给自动化测试用的id
        //mCoverView.setId(R.id.custom_id_min);
        addSubView(mCoverView);
    }


    /**
     * 获取从源中设置的标题 。 如果用户设置了标题，优先使用用户设置的标题。 如果没有，就使用服务器返回的标题
     *
     * @param title 服务器返回的标题
     * @return 最后的标题
     */
    private String getTitle(String title) {
        String finalTitle = title;
        if (mAliyunLocalSource != null) {
            finalTitle = mAliyunLocalSource.getTitle();
        } else if (mAliyunPlayAuth != null) {
            finalTitle = mAliyunPlayAuth.getTitle();
        } else if (mAliyunVidSts != null) {
            finalTitle = mAliyunVidSts.getTitle();
        }

        if (TextUtils.isEmpty(finalTitle)) {
            return title;
        } else {
            return finalTitle;
        }
    }


    /**
     * 获取从源中设置的封面 。 如果用户设置了封面，优先使用用户设置的封面。 如果没有，就使用服务器返回的封面
     *
     * @param postUrl 服务器返回的封面
     * @return 最后的封面
     */
    private String getPostUrl(String postUrl) {
        String finalPostUrl = postUrl;
        if (mAliyunLocalSource != null) {
            finalPostUrl = mAliyunLocalSource.getCoverPath();
        } else if (mAliyunPlayAuth != null) {

        }

        if (TextUtils.isEmpty(finalPostUrl)) {
            return postUrl;
        } else {
            return finalPostUrl;
        }
    }

    /**
     * 设置封面信息
     *
     * @param uri url地址
     */
    public void setCoverUri(String uri) {
        if (mCoverView != null && !TextUtils.isEmpty(uri)) {
            Glide.with(getContext()).load(uri).into(mCoverView);
            mCoverView.setVisibility(isPlaying() ? GONE : VISIBLE);
        }
    }

    /**
     * 设置本地播放源
     *
     * @param aliyunLocalSource 本地播放源
     */
    public void setLocalSource(AliyunLocalSource aliyunLocalSource) {
        if (mAliyunVodPlayer == null) {
            return;
        }

        clearAllSource();
        reset();

        mAliyunLocalSource = aliyunLocalSource;
    }

    /**
     * 清空之前设置的播放源
     */
    private void clearAllSource() {
        mAliyunPlayAuth = null;
        mAliyunVidSts = null;
        mAliyunLocalSource = null;
    }

    /**
     * 重置。包括一些状态值，view的状态等
     */
    private void reset() {
        stop();
    }


    /**
     * 是否处于播放状态：start或者pause了
     *
     * @return 是否处于播放状态
     */
    public boolean isPlaying() {
        if (mAliyunVodPlayer != null) {
            return mAliyunVodPlayer.isPlaying();
        }
        return false;
    }

    /**
     * 开始播放
     */
    public void start() {
        mAliyunVodPlayer.start();
        IAliyunVodPlayer.PlayerState playerState = mAliyunVodPlayer.getPlayerState();
        if (playerState == IAliyunVodPlayer.PlayerState.Paused || playerState == IAliyunVodPlayer.PlayerState.Prepared || mAliyunVodPlayer.isPlaying()) {
            mAliyunVodPlayer.start();
        }
    }

    /**
     * 停止播放
     */
    private void stop() {
        Boolean hasLoadedEnd = null;
        AliyunMediaInfo mediaInfo = null;
        if (mAliyunVodPlayer != null && hasLoadEnd != null) {
            mediaInfo = mAliyunVodPlayer.getMediaInfo();
            hasLoadedEnd = hasLoadEnd.get(mediaInfo);
        }

        if (mAliyunVodPlayer != null && hasLoadedEnd != null) {
            mAliyunVodPlayer.stop();

        }
        if (hasLoadEnd != null) {
            hasLoadEnd.remove(mediaInfo);
        }
    }


}
