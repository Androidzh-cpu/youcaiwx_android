package com.ucfo.youcai.widget.dialog;

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
import com.ucfo.youcai.R;

/**
 * Author:29117
 * Time: 2019-4-28.  下午 1:43
 * Email:2911743255@qq.com
 * ClassName: TestModeIconDialog
 * Package: com.ucfo.youcai.widget.dialog
 * Description:TODO  做题页带有icon的弹框
 * Detail:TODO
 */
public class TestModeIconDialog {
    private Context context;
    private Dialog dialog;
    private RoundLinearLayout lLayout_bg;
    private ImageView image_Icon;
    private TextView txt_msg;
    private RoundTextView btn_neg;
    private RoundTextView btn_pos;
    private View img_line;
    private DisplayMetrics display;
    private boolean showMsg = false;

    public TestModeIconDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            display = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(display);
        }
    }

    public TestModeIconDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_testmodeicon, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (RoundLinearLayout) view.findViewById(R.id.lLayout_bg);
        image_Icon = (ImageView) view.findViewById(R.id.image_icon);
        txt_msg = (TextView) view.findViewById(R.id.txt_msg);
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

    /**
     * Description:TestModeIconDialog
     * Time:2019-4-28   下午 2:01
     * Detail: 设置logo
     */
    public TestModeIconDialog setIcon(int resId) {
        if (resId == 0) {
            image_Icon.setImageResource(R.mipmap.app_icon);
        } else {
            image_Icon.setImageResource(resId);
        }
        return this;
    }

    public TestModeIconDialog setMsg(String msg) {
        showMsg = true;
        if (TextUtils.isEmpty(msg)) {
            txt_msg.setText("内容");
        } else {
            txt_msg.setText(msg);
        }
        return this;
    }


    public TestModeIconDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    //按空白处不能取消
    public TestModeIconDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public TestModeIconDialog setPositiveButton(String text, final View.OnClickListener listener) {
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

    public TestModeIconDialog setNegativeButton(String text, final View.OnClickListener listener) {
        if (TextUtils.isEmpty(text)) {
            btn_neg.setText(context.getResources().getString(R.string.cancel));
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
        return this;
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

}
