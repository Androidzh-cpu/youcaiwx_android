package com.ucfo.youcaiwx.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.flyco.roundview.RoundLinearLayout;
import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;

/**
 * Author: AND
 * Time: 2019-12-23.  下午 1:45
 * Package: com.ucfo.youcaiwx.widget.dialog
 * FileName: InputInformationDialog
 * Description:TODO 填写信息
 */
public class InputInformationDialog {

    private Context context;
    private DisplayMetrics display;
    private Dialog dialog;
    private EditText mNameEdittext;
    private EditText mPhoneEdittext;
    private RoundTextView mNegBtn;
    private View mLineImg;
    private RoundTextView mPosBtn;
    private RoundLinearLayout mLinearlayout;

    public InputInformationDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            display = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(display);
        }
    }

    public InputInformationDialog builder() {
        // 获取Dialog布局
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_input_information, null);
        initView(contentView);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(contentView);
        // 调整dialog背景大小
        mLinearlayout.setLayoutParams(new FrameLayout.LayoutParams((int) (display.widthPixels * 0.75), LinearLayout.LayoutParams.WRAP_CONTENT));

        mNegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissmiss();
            }
        });
        return this;
    }

    public InputInformationDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public InputInformationDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public InputInformationDialog setNegativeButton(final View.OnClickListener listener) {
        mNegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public InputInformationDialog setPositiveButton(final View.OnClickListener listener) {
        mPosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
        return this;
    }

    /**
     * 获取姓名
     */
    public String getEditNameConent() {
        return mNameEdittext.getText().toString().trim();
    }

    /**
     * 获取手机号
     */
    public String getEditPhoneConent() {
        return mPhoneEdittext.getText().toString().trim();
    }

    public void setNameHint() {
        String string = "";
        if (context != null) {
            string = context.getResources().getString(R.string.invoice_editTips1);
        } else {
            string = "请输入姓名";
        }
        mNameEdittext.setHint(string);
    }

    public void setPhoneHint() {
        String string = "";
        if (context != null) {
            string = context.getResources().getString(R.string.register_tipphone);
        } else {
            string = "请输入手机号";
        }
        mPhoneEdittext.setHint(string);
    }

    private void initView(@NonNull final View itemView) {
        mNameEdittext = (EditText) itemView.findViewById(R.id.edittext_name);
        mPhoneEdittext = (EditText) itemView.findViewById(R.id.edittext_phone);
        mNegBtn = (RoundTextView) itemView.findViewById(R.id.btn_neg);
        mLineImg = (View) itemView.findViewById(R.id.img_line);
        mPosBtn = (RoundTextView) itemView.findViewById(R.id.btn_pos);
        mLinearlayout = (RoundLinearLayout) itemView.findViewById(R.id.linearlayout);
    }

    public void show() {
        dialog.show();
    }

    public void dissmiss() {
        dialog.dismiss();
    }
}
