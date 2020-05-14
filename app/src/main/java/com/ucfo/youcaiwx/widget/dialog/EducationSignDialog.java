package com.ucfo.youcaiwx.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.flyco.roundview.RoundLinearLayout;
import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;

/**
 * Author: AND
 * Time: 2019-12-26.  下午 5:30
 * Package: com.ucfo.youcaiwx.widget.dialog
 * FileName: EducationSignDialog
 * Description:TODO 后续教育签到
 */
public class EducationSignDialog {
    private Context context;
    private Dialog dialog;
    private DisplayMetrics display;
    private RoundTextView mTitleTxt;
    private RoundLinearLayout mBgLLayout;
    private RelativeLayout mRelativelayout;

    public EducationSignDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            display = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(display);
        }
    }

    public EducationSignDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_education_sign, null);
        initView(view);
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        mRelativelayout.setLayoutParams(new FrameLayout.LayoutParams(display.widthPixels, display.heightPixels));
        return this;
    }

    public EducationSignDialog setCancelable(boolean flag) {
        dialog.setCancelable(flag);
        return this;
    }

    public EducationSignDialog setCanceledOnTouchOutside(boolean flag) {
        dialog.setCanceledOnTouchOutside(flag);
        return this;
    }

    private void initView(@NonNull final View itemView) {
        mTitleTxt = (RoundTextView) itemView.findViewById(R.id.txt_title);
        mBgLLayout = (RoundLinearLayout) itemView.findViewById(R.id.lLayout_bg);
        mRelativelayout = (RelativeLayout) itemView.findViewById(R.id.relativelayout);
    }

    /**
     * 设置剩余时间数
     */
    public void setTimeFinished(int timeFinished) {
        mTitleTxt.setText(String.valueOf(timeFinished + "s"));
    }

    public void setTimeFinished(String text) {
        mTitleTxt.setText(text);
    }

    /**
     * 点击事件
     */
    public EducationSignDialog setNegativeButton(final View.OnClickListener listener) {
        mRelativelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public EducationSignDialog setDissmissListener(final Dialog.OnDismissListener listener) {
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
