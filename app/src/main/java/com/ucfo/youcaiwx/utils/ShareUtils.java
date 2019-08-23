package com.ucfo.youcaiwx.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.model.Response;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.UcfoApplication;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Author: AND
 * Time: 2019-7-1.  下午 3:09
 * FileName: ShareUtils
 * Description:TODO 微信分享
 */
public class ShareUtils {
    public static String title = String.valueOf(UcfoApplication.getInstance().getResources().getString(R.string.app_nameWX));
    public static String desc = String.valueOf(UcfoApplication.getInstance().getResources().getString(R.string.youcaiWXShareDescribe));
    public static String icon = "http://www.youcaiwx.com/Public/Uploads/newtopicpics/2017-12-26/5a41b418a2e32.png";
    public static String url = "http://www.youcaiwx.com/html/activity/activity.html";

    private static ShareUtils instance;
    private Bitmap shareBitmap;

    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
    private static final int THUMB_SIZE = 150; //缩略图大小

    private ShareUtils() {
    }

    public static ShareUtils getInstance() {
        if (instance == null) {
            synchronized (ShareUtils.class) {
                if (instance == null) {
                    instance = new ShareUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 分享文本类型
     * 微信会话或者朋友圈等
     */
    public void shareTextToWx(String text, final int type) {
        if (text == null || text.length() == 0) {
            return;
        }
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = type;

        UcfoApplication.api.sendReq(req);
    }

    /**
     * 分享图片到微信
     */
    public void shareImageToWx(String imgUrl, String title, String desc, int wxSceneSession) {
        OkGo.<Bitmap>get(imgUrl)
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Response<Bitmap> response) {
                        Bitmap thumb = response.body();
                        shareImageToWx2(thumb, title, desc, wxSceneSession);
                    }

                    @Override
                    public void onError(Response<Bitmap> response) {
                        super.onError(response);
                        Bitmap thumb = BitmapFactory.decodeResource(UcfoApplication.getInstance().getResources(), R.mipmap.app_icon);
                        shareImageToWx2(thumb, title, desc, wxSceneSession);
                    }
                });
    }

    public void shareImageToWx2(Bitmap thumb, String title, String desc, int wxSceneSession) {
        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject(thumb);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        msg.title = title;
        msg.description = desc;
        //设置缩略图
        Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, THUMB_SIZE, THUMB_SIZE, true);
        thumb.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = wxSceneSession;
        //调用api接口，发送数据到微信
        UcfoApplication.api.sendReq(req);
    }

    /**
     * 分享音乐
     *
     * @param musicUrl 音乐资源地址
     * @param title    标题
     * @param desc     描述
     */
    public void shareMusicToWx(final String musicUrl, final String title, final String desc, final String iconUrl, final int wxSceneSession) {
        WXMusicObject music = new WXMusicObject();
        music.musicUrl = musicUrl;

        final WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = music;
        msg.title = title;
        msg.description = desc;

        Bitmap bmp = BitmapFactory.decodeResource(UcfoApplication.getInstance().getResources(), R.mipmap.app_icon);
        final Bitmap[] thumbBmp = {Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true)};
        bmp.recycle();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream imageStream = getImageStream(iconUrl);
                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                    thumbBmp[0] = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
                    bitmap.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                msg.thumbData = bmpToByteArray(thumbBmp[0], true);
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("music");
                req.message = msg;
                req.scene = wxSceneSession;
                UcfoApplication.api.sendReq(req);
            }
        }).start();
    }


    /**
     * 分享视频
     *
     * @param videoUrl       视频地址
     * @param title          标题
     * @param desc           描述
     * @param wxSceneSession
     */
    public void shareVideoToWx(String videoUrl, String title, String desc, final String iconUrl, final int wxSceneSession) {
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = videoUrl;

        final WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = title;
        msg.description = desc;
        Bitmap bmp = BitmapFactory.decodeResource(UcfoApplication.getInstance().getResources(), R.mipmap.app_icon);
        final Bitmap[] thumbBmp = {Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true)};
        bmp.recycle();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream imageStream = getImageStream(iconUrl);
                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                    thumbBmp[0] = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
                    bitmap.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                msg.thumbData = bmpToByteArray(thumbBmp[0], true);

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("video");
                req.message = msg;
                req.scene = wxSceneSession;
                UcfoApplication.api.sendReq(req);
            }
        }).start();
    }

    /**
     * 分享url地址
     *
     * @param url            地址
     * @param title          标题
     * @param desc           描述
     * @param wxSceneSession 类型
     */
    public void shareUrlToWx(String url, String title, String desc, final String iconUrl, final int wxSceneSession) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;

        msg.description = desc;
        Bitmap bmp = BitmapFactory.decodeResource(UcfoApplication.getInstance().getResources(), R.mipmap.app_icon);
        Bitmap[] thumbBmp = {Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true)};
        bmp.recycle();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream imageStream = getImageStream(iconUrl);
                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                    thumbBmp[0] = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
                    bitmap.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                msg.thumbData = bmpToByteArray(thumbBmp[0], true);
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("webpage");
                req.message = msg;
                req.scene = wxSceneSession;
                UcfoApplication.api.sendReq(req);
            }
        }).start();
    }


    /**
     * 判断是否安装微信
     */
    public static boolean isWeiXinAppInstall() {
        if (UcfoApplication.api == null) {
            UcfoApplication.api = WXAPIFactory.createWXAPI(UcfoApplication.getInstance(), Constant.WEIXIN_KEY, true);
            UcfoApplication.api.registerApp(Constant.WEIXIN_KEY);
        }
        if (!UcfoApplication.api.isWXAppInstalled()) {
            ToastUtil.showBottomShortText(UcfoApplication.getInstance(), UcfoApplication.getInstance().getResources().getString(R.string.wx_noinstanl));
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否支持分享到朋友圈
     */
    public boolean isWXAppSupportAPI() {
        if (isWeiXinAppInstall()) {
            int wxSdkVersion = UcfoApplication.api.getWXAppSupportAPI();
            if (wxSdkVersion >= TIMELINE_SUPPORTED_VERSION) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public InputStream getImageStream(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return conn.getInputStream();
        }
        return null;
    }

    public Bitmap getImageBitmap(String url) {
        OkGo.<Bitmap>get(url)
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Response<Bitmap> response) {
                        Bitmap thumb = response.body();
                        shareBitmap = thumb;
                    }

                    @Override
                    public void onError(Response<Bitmap> response) {
                        super.onError(response);
                        Bitmap thumb = BitmapFactory.decodeResource(UcfoApplication.getInstance().getResources(), R.mipmap.app_icon);
                        shareBitmap = thumb;
                    }
                });
        return shareBitmap;
    }
}
