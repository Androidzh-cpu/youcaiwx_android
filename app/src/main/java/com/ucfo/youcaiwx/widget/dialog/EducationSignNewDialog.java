package com.ucfo.youcaiwx.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;

import java.util.Random;

/**
 * Author: AND
 * Time: 2020-5-14.  下午 4:27
 * Package: com.ucfo.youcaiwx.widget.dialog
 * FileName: EducationSignNewDialog
 * Description:TODO 后续教育签到新的需求弹框
 */
public class EducationSignNewDialog {
    private Context context;
    private Dialog dialog;
    private DisplayMetrics display;
    private ImageView imageView;
    private ImageView btnExit;
    private TextView textPoint;

    public EducationSignNewDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            display = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(display);
        }
    }

    public EducationSignNewDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_education_sign_new, null);
        imageView = view.findViewById(R.id.imageview);
        textPoint = view.findViewById(R.id.text_point);
        btnExit = view.findViewById(R.id.btn_exit);

        //设置随机背景
        Random random = new Random();
        int i = random.nextInt(3);
        switch (i) {
            case 0:
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_education_01));
                break;
            case 1:
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_education_02));
                break;
            case 2:
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_education_03));
                break;
            default:
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_education_01));
                break;
        }

        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        return this;
    }

    //设置分数
    public EducationSignNewDialog setPoint(String point) {
        if (!TextUtils.isEmpty(point)) {
            textPoint.setText(point);
        }
        return this;
    }

    public EducationSignNewDialog setCancelable(boolean flag) {
        dialog.setCancelable(flag);
        return this;
    }

    public EducationSignNewDialog setCanceledOnTouchOutside(boolean flag) {
        dialog.setCanceledOnTouchOutside(flag);
        return this;
    }

    /**
     * 点击事件
     */
    public EducationSignNewDialog setExitButton(final View.OnClickListener listener) {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public EducationSignNewDialog setDissmissListener(final Dialog.OnDismissListener listener) {
        if (dialog != null) {
            dialog.setOnDismissListener(listener);
        }
        return this;
    }

    public void show() {
        dialog.show();
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
