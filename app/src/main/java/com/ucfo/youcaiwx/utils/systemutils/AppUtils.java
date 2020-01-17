package com.ucfo.youcaiwx.utils.systemutils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Author:AND
 * Time: 2019/1/7.  14:28
 * Email:2911743255@qq.com
 * Description:常用的获取系统信息的方法
 */
public class AppUtils {

    private static String deviceid;

    /**
     * TODO 获取App版本号
     *
     * @param context
     * @return
     */
    public static int getAppVersion(Context context) {
        //TODO 获取packagemanager的视力
        PackageManager packageManager = context.getPackageManager();
        try {
            // TODO getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);

            int versionName = packageInfo.versionCode;

            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * TODO 获取App版本名
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        //TODO 获取packagemanager的视力
        PackageManager packageManager = context.getPackageManager();
        try {
            // TODO getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);

            String versionName = packageInfo.versionName;

            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * TODO 获取android版本
     */
    public static String getAndroidVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * TODO 获取设备类型
     */
    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    /**
     * TODO 获取SDK版本
     */

    public static int getSDKVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * TODO 获取应用唯一标识
     */
    public static String getAppID(Context context) {
        if (context == null) {
            return null;
        }
        return context.getApplicationInfo().packageName;
    }

    /**
     * TODO 获取设备的唯一标识
     */
    @SuppressLint("CheckResult")
    public static String getAndroidID(Activity activity) {
        //后期发现的比较合适且唯一的设备表示获取方法
        String deviceId = DeviceIdUtil.getDeviceId(activity);
        //String androidID = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        return deviceId;
    }

    public static void getAndroiodScreenProperty(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)
    }

    public static int getViewWidth(View view) {
        //通过测量获取view宽高
        int viewWidth = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int viewHeight = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(viewWidth, viewHeight);
        int width = view.getMeasuredWidth();
        return width;
    }

    public static int getViewHeight(View view) {
        //通过测量获取view宽高
        int viewWidth = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int viewHeight = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(viewWidth, viewHeight);
        int height = view.getMeasuredHeight();
        return height;
    }

    /**
     * 截取指定View为图片
     */
    public static Bitmap captureView(View view) throws Throwable {
        if (view == null) {
            return null;
        }

        Bitmap bm = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bm));
        return bm;
    }

    /**
     * 压缩图片
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        return Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
    }

    /**
     * 截取除了导航栏之外的整个屏幕
     */
    private Bitmap screenShotWholeScreen(Activity context) {
        View dView = context.getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        return Bitmap.createBitmap(dView.getDrawingCache());
    }
}
