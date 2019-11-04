package com.ucfo.youcaiwx.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;

/**
 * Author: AND
 * Time: 2019-8-2.  上午 11:35
 * FileName: InvoiceInfomationDialog
 * detail:todo 发票须知
 */
public class InvoiceInfomationDialog {

    private Context context;
    private Dialog dialog;
    private RoundTextView btn_neg;
    private DisplayMetrics display;

    public InvoiceInfomationDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            display = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(display);
        }
    }

    public InvoiceInfomationDialog builder() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_invoice_infomation, null);
        btn_neg = (RoundTextView) contentView.findViewById(R.id.btn_neg);
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        return this;
    }

    public InvoiceInfomationDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public InvoiceInfomationDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public InvoiceInfomationDialog setNegativeButton(final View.OnClickListener listener) {
        btn_neg.setOnClickListener(new View.OnClickListener() {
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
