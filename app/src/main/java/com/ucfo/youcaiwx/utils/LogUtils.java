package com.ucfo.youcaiwx.utils;

import android.os.Environment;

import com.orhanobut.logger.Logger;
import com.ucfo.youcaiwx.common.Constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author:AND
 * Time: 2019-3-5.  下午 5:37
 * Email:2911743255@qq.com
 * ClassName: LogUtils
 * Description:TODO log工具
 */
public class LogUtils {
    private static boolean sIsDebug = Constant.ISTEST_ENVIRONMENT;
    //private static boolean sIsDebug = true;
    private static String sPath; // SDCard路径
    private static FileOutputStream sOutputStream;

    static {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            sPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WriteLogFile/";
            File filePath = new File(sPath);
            filePath.mkdirs();
            File file = new File(sPath, "log.txt");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                sOutputStream = new FileOutputStream(file, true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setDebug(boolean debug) {
        sIsDebug = debug;
    }

    public static void d(String log) {
        if (sIsDebug) {
            Logger.d(log);
        }
    }

    public static void e(String log) {
        if (sIsDebug) {
            Logger.e(log);
            save2Sd(log);
        }
    }

    public static void v(String log) {
        if (sIsDebug) {
            Logger.v(log);
        }
    }

    public static void i(String log) {
        if (sIsDebug) {
            Logger.i(log);
        }
    }

    public static void w(String log) {
        if (sIsDebug) {
            Logger.w(log);
        }
    }

    public static void w(String tag, String log) {
        if (sIsDebug) {
            Logger.t(tag).w(log);
        }
    }

    /**
     * 将错误信息保存到SD卡中去。
     */
    public static void save2Sd(String msg) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd : HHmm");
        String time = sdf.format(date);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            if (sOutputStream != null) {
                try {
                    sOutputStream.write(time.getBytes());
                    sOutputStream.write(msg.getBytes());
                    sOutputStream.write("\r\n".getBytes());
                    sOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
