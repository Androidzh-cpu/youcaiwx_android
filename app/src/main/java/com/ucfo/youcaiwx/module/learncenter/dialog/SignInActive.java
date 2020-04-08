package com.ucfo.youcaiwx.module.learncenter.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.learncenter.StudyClockInBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.integral.EarnIntegralPresenter;
import com.ucfo.youcaiwx.utils.ShareUtils;
import com.ucfo.youcaiwx.utils.systemutils.AppUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: AND
 * Time: 2019-7-23.  下午 3:32
 * FileName: SignInActive
 * Description:TODO 日签
 */
public class SignInActive extends DialogFragment {
    private StudyClockInBean.DataBean userBean;
    private ImageView mExitBtn;
    private TextView mTodayText;
    private TextView mWeekText;
    private CircleImageView mIconUser;
    private TextView mNicknameUser;
    private TextView mSignDaysUser;
    private RelativeLayout mSignRelativelayout;
    private ImageView mToweixinShare;
    private ImageView mTofriendShare;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AlertDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_sinday, container, false);
        initView(v);
        return v;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String format = sdf.format(date);
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        String format1 = dateFm.format(date);
        mTodayText.setText(format);
        mWeekText.setText(format1);

        if (userBean != null) {
            SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    mSignRelativelayout.setBackground(resource);
                }
            };
            RequestOptions requestOptions = new RequestOptions()
                    .error(R.mipmap.image_loaderror)
                    .transform(new RoundedCorners(DensityUtil.dp2px(4)));
            Glide.with(this)
                    .load(userBean.getImage_url())
                    .apply(requestOptions)
                    .into(simpleTarget);

            RequestOptions requestOptions2 = new RequestOptions()
                    .placeholder(R.mipmap.icon_account_btn)
                    .error(R.mipmap.image_loaderror);
            Glide.with(this)
                    .load(userBean.getHead())
                    .apply(requestOptions2)
                    .into(mIconUser);
            if (!TextUtils.isEmpty(userBean.getUsername())) {
                mNicknameUser.setText(userBean.getUsername());
            }
            mSignDaysUser.setText(String.valueOf(userBean.getNum()));
        }
        initData();
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER | Gravity.BOTTOM;
        window.setAttributes(params);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //沉浸式状态栏适配
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void initData() {
        mExitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mTofriendShare.setOnClickListener(new View.OnClickListener() {//TODO 分享至微信朋友圈
            @Override
            public void onClick(View v) {
                //设置签到积分
                EarnIntegralPresenter.getInstance().setIntegralType(Constant.INTEGRAL_SIGN);

                Bitmap bitmap = null;
                try {
                    bitmap = AppUtils.captureView(mSignRelativelayout);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                if (bitmap != null) {
                    Bitmap zoomImage = AppUtils.zoomImage(bitmap, 720, 1280);
                    ShareUtils.getInstance().shareImageToWx2(zoomImage, getResources().getString(R.string.default_title), "学习打卡", SendMessageToWX.Req.WXSceneTimeline);
                    dismiss();
                }
            }
        });
        mToweixinShare.setOnClickListener(new View.OnClickListener() {//TODO 分享至微信好友
            @Override
            public void onClick(View v) {
                //设置签到积分
                EarnIntegralPresenter.getInstance().setIntegralType(Constant.INTEGRAL_SIGN);

                Bitmap bitmap = null;
                try {
                    bitmap = AppUtils.captureView(mSignRelativelayout);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                if (bitmap != null) {
                    Bitmap zoomImage = AppUtils.zoomImage(bitmap, 720, 1280);
                    ShareUtils.getInstance().shareImageToWx2(zoomImage, getResources().getString(R.string.default_title), "学习打卡", SendMessageToWX.Req.WXSceneSession);
                    dismiss();
                }
            }
        });
    }

    private void initView(@NonNull final View itemView) {
        mExitBtn = (ImageView) itemView.findViewById(R.id.btn_exit);
        mTodayText = (TextView) itemView.findViewById(R.id.text_today);
        mWeekText = (TextView) itemView.findViewById(R.id.text_week);
        mIconUser = (CircleImageView) itemView.findViewById(R.id.user_icon);
        mNicknameUser = (TextView) itemView.findViewById(R.id.user_nickname);
        mSignDaysUser = (TextView) itemView.findViewById(R.id.user_signDays);
        mSignRelativelayout = (RelativeLayout) itemView.findViewById(R.id.relativelayout_sign);
        mToweixinShare = (ImageView) itemView.findViewById(R.id.share_toweixin);
        mTofriendShare = (ImageView) itemView.findViewById(R.id.share_tofriend);
    }

    public void setUserBean(StudyClockInBean.DataBean userBean) {
        this.userBean = userBean;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }
}
