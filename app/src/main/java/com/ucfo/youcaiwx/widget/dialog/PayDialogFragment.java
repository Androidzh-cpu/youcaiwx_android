package com.ucfo.youcaiwx.widget.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.module.main.activity.WebActivity;

/**
 * Author: AND
 * Time: 2019-11-1.  上午 10:43
 * Package: com.ucfo.youcaiwx.widget.dialog
 * FileName: PayDialogFragment
 * Description:TODO 支付弹窗
 */
public class PayDialogFragment extends DialogFragment implements View.OnClickListener {
    private ImageView mCloseBtn;
    private TextView mWechatPayBtn;
    private TextView mAlipayPay;
    private TextView mPaynoticeBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AlertDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_pay, container, false);
        initView(v);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        super.onStart();
        Dialog dialog = getDialog();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window != null ? window.getAttributes() : null;
        if (params != null) {
            params.gravity = Gravity.CENTER | Gravity.BOTTOM;
        }
        if (window != null) {
            window.setAttributes(params);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setWindowAnimations(R.style.MaterialDialogBottominAnimation);
        }

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public interface PayClickListener {
        void aliPay();

        void wechatPay();
    }

    private PayClickListener mOnClickListener;

    public void setOnPayClickListener(PayClickListener listener) {
        this.mOnClickListener = listener;
    }

    private void initView(@NonNull final View itemView) {
        mCloseBtn = (ImageView) itemView.findViewById(R.id.btn_close);
        mCloseBtn.setOnClickListener(this);
        mWechatPayBtn = (TextView) itemView.findViewById(R.id.btn_WechatPay);
        mWechatPayBtn.setOnClickListener(this);
        mAlipayPay = (TextView) itemView.findViewById(R.id.pay_Alipay);
        mAlipayPay.setOnClickListener(this);
        mPaynoticeBtn = (TextView) itemView.findViewById(R.id.btn_paynotice);
        mPaynoticeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                // TODO 19/11/01
                dismiss();
                break;
            case R.id.btn_WechatPay:
                // TODO 19/11/01
                if (mOnClickListener != null) {
                    mOnClickListener.wechatPay();
                }
                dismiss();
                break;
            case R.id.pay_Alipay:
                // TODO 19/11/01
                if (mOnClickListener != null) {
                    mOnClickListener.aliPay();
                }
                dismiss();
                break;
            case R.id.btn_paynotice:
                //TODO 19/11/01
                Intent intent = new Intent(getActivity(), WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.WEB_TITLE, getResources().getString(R.string.pay_agreement));
                bundle.putString(Constant.WEB_URL, ApiStores.PAY_AGREEMENT);
                intent.putExtras(bundle);
                startActivity(intent);
                dismiss();
                break;
            default:
                break;
        }
    }
}
