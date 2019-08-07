package com.ucfo.youcai.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ucfo.youcai.R;
import com.ucfo.youcai.entity.pay.InvoiceInfoBean;
import com.ucfo.youcai.utils.RegexUtil;
import com.ucfo.youcai.utils.toastutils.ToastUtil;

import java.util.Objects;

/**
 * Author: AND
 * Time: 2019-8-2.  下午 2:32
 * Package: com.ucfo.youcai.widget.dialog
 * FileName: InvoiceActiveDialog
 * Description:TODO 添加发票
 */
public class InvoiceActiveDialog extends DialogFragment implements View.OnClickListener {

    private TextView mInvoiceinfomationBtn;
    private ImageView mExitBtn;
    private RadioButton mCommonRadiobtn;
    private RadioButton mSpecialRadiobtn;
    private RadioGroup mTypeRadiogroup;
    private RadioButton mPersonRadiobtn;
    private RadioButton mCompanyRadiobtn;
    private RadioGroup mFormRadiogroup;
    private EditText mPersonNameEdit;
    private LinearLayout mPersonLinear;
    private EditText mCompanyNameEdit;
    private EditText mCompanyIdentificationnumberEdit;
    private LinearLayout mCompanyLinear;
    private LinearLayout mInvoicecommonLinear;
    private EditText mSpecialNameEdit;
    private EditText mSpecialNumberEdit;
    private EditText mSpecialAddressEdit;
    private EditText mSpecialPhoneEdit;
    private EditText mSpecialBankEdit;
    private EditText mSpecialBankAccountEdit;
    private LinearLayout mInvoicespecialLinear;
    private Button mNextBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AlertDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_invoice, container, false);
        initView(v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window != null ? window.getAttributes() : null;
        params.gravity = Gravity.CENTER | Gravity.BOTTOM;
        window.setAttributes(params);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }*/
    }

    private void initData() {
        //普通发票,增值税专用发票切换
        mTypeRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radiobtn_common:
                        //TODO 普通
                        mInvoicecommonLinear.setVisibility(View.VISIBLE);
                        mInvoicespecialLinear.setVisibility(View.GONE);
                        break;
                    case R.id.radiobtn_special:
                        //TODO 增值税
                        mInvoicespecialLinear.setVisibility(View.VISIBLE);
                        mInvoicecommonLinear.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });
        //个人企业发票切换
        mFormRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radiobtn_person:
                        //TODO 个人
                        mPersonLinear.setVisibility(View.VISIBLE);
                        mCompanyLinear.setVisibility(View.GONE);
                        break;
                    case R.id.radiobtn_company:
                        //TODO 企业
                        mCompanyLinear.setVisibility(View.VISIBLE);
                        mPersonLinear.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initView(@NonNull final View itemView) {
        mInvoiceinfomationBtn = (TextView) itemView.findViewById(R.id.btn_invoiceinfomation);
        mInvoiceinfomationBtn.setOnClickListener(this);
        mExitBtn = (ImageView) itemView.findViewById(R.id.btn_exit);
        mExitBtn.setOnClickListener(this);
        mCommonRadiobtn = (RadioButton) itemView.findViewById(R.id.radiobtn_common);
        mSpecialRadiobtn = (RadioButton) itemView.findViewById(R.id.radiobtn_special);
        mTypeRadiogroup = (RadioGroup) itemView.findViewById(R.id.radiogroup_type);
        mPersonRadiobtn = (RadioButton) itemView.findViewById(R.id.radiobtn_person);
        mCompanyRadiobtn = (RadioButton) itemView.findViewById(R.id.radiobtn_company);
        mFormRadiogroup = (RadioGroup) itemView.findViewById(R.id.radiogroup_form);
        mPersonNameEdit = (EditText) itemView.findViewById(R.id.edit_personName);
        mPersonLinear = (LinearLayout) itemView.findViewById(R.id.linear_person);
        mCompanyNameEdit = (EditText) itemView.findViewById(R.id.edit_companyName);
        mCompanyIdentificationnumberEdit = (EditText) itemView.findViewById(R.id.edit_companyIdentificationnumber);
        mCompanyLinear = (LinearLayout) itemView.findViewById(R.id.linear_company);
        mInvoicecommonLinear = (LinearLayout) itemView.findViewById(R.id.linear_invoicecommon);
        mSpecialNameEdit = (EditText) itemView.findViewById(R.id.edit_specialName);
        mSpecialNumberEdit = (EditText) itemView.findViewById(R.id.edit_specialNumber);
        mSpecialAddressEdit = (EditText) itemView.findViewById(R.id.edit_specialAddress);
        mSpecialPhoneEdit = (EditText) itemView.findViewById(R.id.edit_specialPhone);
        mSpecialBankEdit = (EditText) itemView.findViewById(R.id.edit_specialBank);
        mSpecialBankAccountEdit = (EditText) itemView.findViewById(R.id.edit_specialBankAccount);
        mInvoicespecialLinear = (LinearLayout) itemView.findViewById(R.id.linear_invoicespecial);
        mNextBtn = (Button) itemView.findViewById(R.id.btn_next);
        mNextBtn.setOnClickListener(this);
    }

    private DialogInterface.OnDismissListener mOnClickListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        this.mOnClickListener = listener;
    }

    private InvoiceInfoBean invoiceInfoBean;

    public InvoiceInfoBean getInvoiceInfoBean() {
        return invoiceInfoBean;
    }

    public void setInvoiceInfoBean(InvoiceInfoBean bean) {
        this.invoiceInfoBean = bean;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnClickListener != null) {
            mOnClickListener.onDismiss(dialog);
        }
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_invoiceinfomation:
                // TODO 19/08/02 发票须知
                new InvoiceInfomationDialog(getActivity()).builder().setCancelable(false).setCanceledOnTouchOutside(false).setNegativeButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
                break;
            case R.id.btn_exit:
                // TODO 19/08/02 退出按钮
                dismiss();
                break;
            case R.id.btn_next:
                // TODO 19/08/02  确定
                String personName = mPersonNameEdit.getText().toString().trim();
                String companyName = mCompanyNameEdit.getText().toString().trim();
                String companyNumber = mCompanyIdentificationnumberEdit.getText().toString().trim();
                String specialName = mSpecialNameEdit.getText().toString().trim();
                String specialNumber = mSpecialNumberEdit.getText().toString().trim();
                String specialAddress = mSpecialAddressEdit.getText().toString().trim();
                String specialPhone = mSpecialPhoneEdit.getText().toString().trim();
                String sepcialBank = mSpecialBankEdit.getText().toString().trim();
                String specialBankAccount = mSpecialBankAccountEdit.getText().toString().trim();

                InvoiceInfoBean invoiceInfoBean = new InvoiceInfoBean();
                if (mCommonRadiobtn.isChecked()) {
                    //TODO 普通发票
                    if (mPersonRadiobtn.isChecked()) {
                        //TODO 个人发票
                        if (TextUtils.isEmpty(personName)) {
                            toastInfo(getResources().getString(R.string.invoice_editTips1));
                            return;
                        }
                        invoiceInfoBean.setPersonName(personName);
                        invoiceInfoBean.setInvoiceType(1);
                        invoiceInfoBean.setInvoiceForm(1);
                        setInvoiceInfoBean(invoiceInfoBean);
                        dismiss();
                    } else {
                        //TODO 企业发票
                        if (TextUtils.isEmpty(companyName)) {
                            toastInfo(getResources().getString(R.string.invoice_editTips2));
                            return;
                        }
                        if (TextUtils.isEmpty(companyNumber)) {
                            toastInfo(getResources().getString(R.string.invoice_editTips3));
                            return;
                        }
                        boolean numFlag = RegexUtil.checkDigit(companyNumber) && companyNumber.length() == 18;
                        boolean engelishNum = RegexUtil.isContainAll(companyNumber) && companyNumber.length() == 18;
                        if (!(numFlag || engelishNum)) {
                            toastInfo(getResources().getString(R.string.invoice_editTips8));
                            return;
                        }
                        invoiceInfoBean.setCompanyName(companyName);
                        invoiceInfoBean.setCompanyNnumber(companyNumber);
                        invoiceInfoBean.setInvoiceType(1);
                        invoiceInfoBean.setInvoiceForm(2);
                        setInvoiceInfoBean(invoiceInfoBean);
                        dismiss();
                    }
                } else {
                    //TODO 增值税发票
                    if (TextUtils.isEmpty(specialName)) {
                        toastInfo(getResources().getString(R.string.invoice_editTips2));
                        return;
                    }
                    if (TextUtils.isEmpty(specialNumber)) {
                        toastInfo(getResources().getString(R.string.invoice_editTips3));
                        return;
                    }
                    boolean numFlag = RegexUtil.checkDigit(specialNumber) && specialNumber.length() == 18;
                    boolean engelishNum = RegexUtil.isContainAll(specialNumber) && specialNumber.length() == 18;
                    if (!(numFlag || engelishNum)) {
                        toastInfo(getResources().getString(R.string.invoice_editTips8));
                        return;
                    }
                    if (TextUtils.isEmpty(specialAddress)) {
                        toastInfo(getResources().getString(R.string.invoice_editTips4));
                        return;
                    }
                    if (TextUtils.isEmpty(specialPhone)) {
                        toastInfo(getResources().getString(R.string.invoice_editTips5));
                        return;
                    }
                    if (!RegexUtil.checkMobile(specialPhone)) {
                        toastInfo(getResources().getString(R.string.invoice_editTips9));
                        return;
                    }
                    if (TextUtils.isEmpty(sepcialBank)) {
                        toastInfo(getResources().getString(R.string.invoice_editTips6));
                        return;
                    }
                    if (TextUtils.isEmpty(specialBankAccount)) {
                        toastInfo(getResources().getString(R.string.invoice_editTips7));
                        return;
                    }
                    if (!RegexUtil.checkBankCard(specialBankAccount)) {
                        toastInfo(getResources().getString(R.string.invoice_editTips10));
                        return;
                    }

                    invoiceInfoBean.setSpecialName(specialName);
                    invoiceInfoBean.setSpecialNumber(specialNumber);
                    invoiceInfoBean.setSpecialAddress(specialAddress);
                    invoiceInfoBean.setSpecialPhone(specialPhone);
                    invoiceInfoBean.setSpecialBank(sepcialBank);
                    invoiceInfoBean.setSpecialBankNum(specialBankAccount);
                    invoiceInfoBean.setInvoiceType(2);

                    setInvoiceInfoBean(invoiceInfoBean);
                    dismiss();
                }
                break;
            default:
                break;
        }
    }

    public void toastInfo(String info) {
        ToastUtil.showBottomShortText(getActivity(), info);
    }
}
