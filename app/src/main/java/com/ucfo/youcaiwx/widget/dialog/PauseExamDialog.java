package com.ucfo.youcaiwx.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;

/**
 * Author:29117
 * Time: 2019-4-28.  上午 11:18
 * Email:2911743255@qq.com
 * ClassName: PauseExamDialog
 * Description:TODO  暂停考试全屏弹框
 */
public class PauseExamDialog {

    private Context context;
    private Dialog dialog;
    private ConstraintLayout lLayout_bg;
    private DisplayMetrics display;
    private RoundTextView mGoexamBtn;

    public PauseExamDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            display = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(display);
        }
    }

    public PauseExamDialog builder() {
        View itemView = LayoutInflater.from(context).inflate(R.layout.dialog_pauseexam, null);
        lLayout_bg = (ConstraintLayout) itemView.findViewById(R.id.lLayout_bg);
        mGoexamBtn = (RoundTextView) itemView.findViewById(R.id.btn_goexam);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(itemView);
        //设置window背景，默认的背景会有Padding值，不能全屏。当然不一定要是透明，你可以设置其他背景，替换默认的背景即可。
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return this;
    }


    public PauseExamDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    //按空白处不能取消
    public PauseExamDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * Description:TestModeSwitchDialog
     * Time:2019-4-28   上午 10:42
     * Detail: 考试模式
     */
    public PauseExamDialog setPauseButton(final View.OnClickListener listener) {
        mGoexamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public void show() {
        //沉浸式状态栏适配
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        dialog.show();
    }

}
