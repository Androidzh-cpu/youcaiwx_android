package com.ucfo.youcaiwx.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundLinearLayout;
import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;

/**
 * Author: AND
 * Time: 2019-12-4.  下午 4:08
 * Package: com.ucfo.youcaiwx.widget.dialog
 * FileName: AgreementDialog
 * Description:TODO 用户协议
 */
public class AgreementDialog {
    private Context context;
    private Dialog dialog;
    private RoundLinearLayout lLayout_bg;
    private TextView txtAgreement;
    private TextView txtPrivacyagreement;
    private RoundTextView btn_neg;
    private RoundTextView btn_pos;
    private View img_line;
    private DisplayMetrics display;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;

    public AgreementDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            display = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(display);
        }
    }

    public AgreementDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_agreement, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = view.findViewById(R.id.lLayout_bg);
        txtAgreement = view.findViewById(R.id.txt_agreement);
        txtPrivacyagreement = view.findViewById(R.id.txt_Privacyagreement);
        btn_neg = view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = (View) view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display.widthPixels * 0.75), LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    //自从听了这首歌，玩LOL有了操作，真的，那天在中路遇到feke的劫，说实话他的劫挺厉害的，我的奶妈都有点打不过他，
    // 但在我要第八次在塔下强杀他的时候，他们的辅助突然出来给我虚弱，就在这时候我把脚放了下去，开始用手玩，就这样拿了双杀，突然他们打野也过来，
    // 我急忙的睁开了眼睛，拿了三杀。ADC又从草丛里出来，
    // 我又快速的打开显示器，就这样拿了四杀。上单又不知道什么时候传送到了我后面，我急忙的推开了我身边的女人，就这样五杀了。团灭了SKT
    public AgreementDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public AgreementDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public boolean isShow() {
        if (dialog != null) {
            boolean showing = dialog.isShowing();
            return showing;
        }
        return false;
    }

    public void dismiss() {
        if (dialog != null) {
            boolean showing = dialog.isShowing();
            if (showing) {
                dialog.dismiss();
            }
        }
    }

    public AgreementDialog setAgreementClickListener(final View.OnClickListener listener) {
        txtAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public AgreementDialog setPrivacyagreementClickListener(final View.OnClickListener listener) {
        txtPrivacyagreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public AgreementDialog setPositiveButton(final View.OnClickListener listener) {
        showPosBtn = true;
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public AgreementDialog setNegativeButton(final View.OnClickListener listener) {
        showNegBtn = true;
        btn_neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    private void setLayout() {
        if (!showPosBtn && !showNegBtn) {
            btn_pos.setText("Confirm");
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_neg.setVisibility(View.VISIBLE);
            img_line.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }
}
