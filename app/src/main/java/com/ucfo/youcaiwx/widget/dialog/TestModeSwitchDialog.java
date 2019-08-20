package com.ucfo.youcaiwx.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;

/**
 * Author:29117
 * Time: 2019-4-28.  上午 10:16
 * Email:2911743255@qq.com
 * ClassName: TestModeSwitchDialog
 * Description:TODO 考试模式选择框
 * Detail:TODO  分为考试模式和练习模式
 */
public class TestModeSwitchDialog {

    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private Display display;
    private TextView mExamTestmode;
    private TextView mExerciseTestmode;
    private ImageView btn_Close;

    public TestModeSwitchDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            display = windowManager.getDefaultDisplay();
        }
    }

    public TestModeSwitchDialog builder() {
        // 获取Dialog布局
        View itemView = LayoutInflater.from(context).inflate(R.layout.dialog_testmodeswitch, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) itemView.findViewById(R.id.lLayout_bg);
        mExamTestmode = (TextView) itemView.findViewById(R.id.testmode_exam);
        mExerciseTestmode = (TextView) itemView.findViewById(R.id.testmode_exercise);
        btn_Close = (ImageView) itemView.findViewById(R.id.btn_close);
        btn_Close.setClickable(true);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(itemView);

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.83), LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    public TestModeSwitchDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    //按空白处不能取消
    public TestModeSwitchDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }


    /**
     * Description:TestModeSwitchDialog
     * Time:2019-4-28   上午 10:42
     * Detail: 考试模式
     */
    public TestModeSwitchDialog setPositiveButton(final View.OnClickListener listener) {
        mExamTestmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    /**
     * Description:TestModeSwitchDialog
     * Time:2019-4-28   上午 10:42
     * Detail: 练习模式
     */
    public TestModeSwitchDialog setNegativeButton(final View.OnClickListener listener) {
        mExerciseTestmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public void show() {

        btn_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
