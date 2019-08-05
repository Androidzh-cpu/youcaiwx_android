package com.ucfo.youcai.utils.netutils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import java.io.Closeable;
import java.io.IOException;

/**
 * Author:29117
 * Time: 2019-3-28.  上午 10:48
 * Email:2911743255@qq.com
 * ClassName: CloseUtils
 * Package: com.ucfo.youcai.utils.netutils
 * Description:
 * Detail:
 */
public final class CloseUtils {

    private CloseUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 关闭 IO
     *
     * @param closeables closeables
     */
    public static void closeIO(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 安静关闭 IO
     *
     * @param closeables closeables
     */
    public static void closeIOQuietly(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     */
    public static void hidekeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}