package com.ucfo.youcai.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucfo.youcai.R;

/**
 * Author: AND
 * Time: 2019-7-1.  上午 10:49
 * Package: com.ucfo.youcai.widget.dialog
 * FileName: ShareDialog
 * Description:TODO 分享dialog
 */
public class ShareDialog {
    private Context context;
    private Dialog dialog;
    private DisplayMetrics display;
    private TextView btnCancel;
    private LinearLayout btnFriend, btnCircle;

    public ShareDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            display = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(display);
        }
    }

    public ShareDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_shared, null);

        btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        btnFriend = (LinearLayout) view.findViewById(R.id.btn_friend);
        btnCircle = (LinearLayout) view.findViewById(R.id.btn_circle);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.MaterialDialogSheet);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false); // 外部点击取消
        dialog.setCancelable(true);
        // 设置宽度为屏宽, 靠近屏幕底部
        Window window = dialog.getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        /*WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);*/
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.getDecorView().setPadding(0, 0, 0, 0);
        return this;
    }

    public ShareDialog setCircleToFriendButton(final View.OnClickListener listener) {
        btnCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public ShareDialog setFriendButton(final View.OnClickListener listener) {
        btnFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public void show() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
