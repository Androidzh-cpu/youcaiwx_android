package com.ucfo.youcaiwx.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundLinearLayout;
import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;

/**
 * Author: AND
 * Time: 2019-8-1.  下午 2:42
 * FileName: ExitPlanDialog
 * Description:TODO 退出学习计划
 */
public class ExitPlanDialog {
    private Context context;
    private Dialog dialog;
    private RoundLinearLayout lLayout_bg;
    private ImageView image_Icon;
    private TextView txt_msg, txt_assistantmsg;
    private RoundTextView btn_neg;
    private RoundTextView btn_pos;
    private View img_line;
    private DisplayMetrics display;

    public ExitPlanDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            display = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(display);
        }
    }

    public ExitPlanDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_exitplanicon, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (RoundLinearLayout) view.findViewById(R.id.lLayout_bg);
        image_Icon = (ImageView) view.findViewById(R.id.image_icon);
        txt_msg = (TextView) view.findViewById(R.id.txt_msg);
        txt_assistantmsg = (TextView) view.findViewById(R.id.txt_assistantmsg);
        btn_neg = (RoundTextView) view.findViewById(R.id.btn_neg);
        btn_pos = (RoundTextView) view.findViewById(R.id.btn_pos);
        img_line = (View) view.findViewById(R.id.img_line);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display.widthPixels * 0.66), LinearLayout.LayoutParams.WRAP_CONTENT));

        return this;
    }

    public ExitPlanDialog setIcon(int resId) {
        if (resId == 0) {
            image_Icon.setImageResource(R.mipmap.app_icon);
        } else {
            image_Icon.setImageResource(resId);
        }
        return this;
    }

    public ExitPlanDialog setMsg(String msg) {
        if (TextUtils.isEmpty(msg)) {
            txt_msg.setText("内容");
        } else {
            txt_msg.setText(msg);
        }
        return this;
    }

    public ExitPlanDialog setAssistantMsg(String msg) {
        if (TextUtils.isEmpty(msg)) {
            txt_assistantmsg.setText("内容");
        } else {
            txt_assistantmsg.setText(msg);
        }
        return this;
    }


    public ExitPlanDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ExitPlanDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public ExitPlanDialog setPositiveButton(String text, final View.OnClickListener listener) {
        if (TextUtils.isEmpty(text)) {
            btn_pos.setText(context.getResources().getString(R.string.confirm));
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public ExitPlanDialog setNegativeButton(String text, final View.OnClickListener listener) {
        if (TextUtils.isEmpty(text)) {
            btn_neg.setText(context.getResources().getString(R.string.cancel));
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public ExitPlanDialog setNegativeButtonVisibility(boolean flag) {
        if (!flag) {
            btn_neg.setVisibility(View.GONE);
            img_line.setVisibility(View.GONE);
        }
        return this;
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

}
