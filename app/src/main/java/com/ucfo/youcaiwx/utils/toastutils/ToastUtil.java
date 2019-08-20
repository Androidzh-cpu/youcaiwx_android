package com.ucfo.youcaiwx.utils.toastutils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ucfo.youcaiwx.R;

/**
 * Author:AND
 * Time: 2019-3-7.  下午 2:59
 * Email:2911743255@qq.com
 * ClassName: ToastUtil
 * Description:TODO toast工具类
 * Detail:
 */
public class ToastUtil extends Toast {

    private static ToastUtil toast;

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     */
    public ToastUtil(Context context) {
        super(context);
    }


    public static void showBottomShortText(Context context, CharSequence text) {
        showToast(context, text, 0, 0);
    }

    /**
     * 显示Toast
     *
     * @param context 上下文
     * @param text    显示的文本
     */
    private static void showToast(Context context, CharSequence text, int position, int time) {
        // 初始化一个新的Toast对象
        initToast(context, text, position);
        // 设置显示时长
        switch (time) {
            case 0:
                toast.setDuration(Toast.LENGTH_SHORT);
                break;
            case 1:
                toast.setDuration(Toast.LENGTH_LONG);
                break;
        }

        // 显示Toast
        toast.show();
    }

    /**
     * 初始化Toast
     *
     * @param context 上下文
     * @param text    显示的文本
     */
    private static void initToast(Context context, CharSequence text, int position) {
        try {
            cancelToast();

            toast = new ToastUtil(context);

            // 获取LayoutInflater对象
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 由layout文件创建一个View对象
            View layout = inflater.inflate(R.layout.view_toast_layout, null);
            layout.getBackground().setAlpha(180);
            // 吐司上的文字
            TextView toast_text = (TextView) layout.findViewById(R.id.message);
            toast_text.setText(text);
            toast.setView(layout);
            //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移180个单位，
            switch (position) {
                case 0:
                    toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 200);
                    break;
                case 1:
                    toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER, 0, 0);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示toast
     */
    @Override
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
        }
    }

    /**
     * 隐藏当前Toast
     */
    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * 当前Toast消失
     */
    public void cancel() {
        try {
            super.cancel();
        } catch (Exception e) {

        }
    }

    public static void showBottomLongText(Context context, CharSequence text) {
        showToast(context, text, 0, 1);
    }

    public static void showCenterShortText(Context context, CharSequence text) {
        showToast(context, text, 1, 0);
    }

    public static void showCenterLongText(Context context, CharSequence text) {
        showToast(context, text, 1, 1);
    }

    /**
     * 显示一个富文本吐司
     *
     * @param context 上下文
     * @param text    显示的文本
     */
    public static void showSpannableText(Context context, CharSequence text) {
        showToast(context, text, 0, 0);
    }
}
