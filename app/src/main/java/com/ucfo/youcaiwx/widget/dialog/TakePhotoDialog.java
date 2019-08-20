package com.ucfo.youcaiwx.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;

/**
 * Author: AND
 * Time: 2019-6-11.  下午 4:21
 * FileName: TakePhotoDialog
 * Description:TODO 选择照片
 */
public class TakePhotoDialog {
    private Context context;
    private Dialog dialog;
    private Display display;
    private boolean showMsg = false;
    private LinearLayout lLayout_bg;
    private TextView btn_takeCarama, btn_takePhoto;

    public TakePhotoDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            display = windowManager.getDefaultDisplay();
        }
    }

    public TakePhotoDialog builder() {
        // 获取Dialog布局
        View itemView = LayoutInflater.from(context).inflate(R.layout.dialog_takephoto, null);

        lLayout_bg = (LinearLayout) itemView.findViewById(R.id.lLayout_bg);
        btn_takeCarama = (TextView) itemView.findViewById(R.id.btn_takeCarama);
        btn_takePhoto = (TextView) itemView.findViewById(R.id.btn_takePhoto);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(itemView);
        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.83), LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    public TakePhotoDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    //按空白处不能取消
    public TakePhotoDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * Description:TakePhotoDialog
     * Time:2019-4-28   上午 10:42
     * Detail: 选择相机
     */
    public TakePhotoDialog setCaremaButtonClick(final View.OnClickListener listener) {
        btn_takeCarama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    /**
     * Description:TakePhotoDialog
     * Time:2019-4-28   上午 10:42
     * Detail: 选择相册
     */
    public TakePhotoDialog setPhotoButtonClick(final View.OnClickListener listener) {
        btn_takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
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
