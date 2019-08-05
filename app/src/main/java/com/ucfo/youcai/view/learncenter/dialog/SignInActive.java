package com.ucfo.youcai.view.learncenter.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.ucfo.youcai.R;
import com.ucfo.youcai.entity.learncenter.StudyClockInBean;
import com.ucfo.youcai.utils.ShareUtils;
import com.ucfo.youcai.utils.glideutils.GlideRoundTransform;
import com.ucfo.youcai.utils.systemutils.AppUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: AND
 * Time: 2019-7-23.  下午 3:32
 * Package: com.ucfo.youcai.view.learncenter.dialog
 * FileName: SignInActive
 * Description:TODO 日签
 */
public class SignInActive extends DialogFragment implements View.OnClickListener {
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
            SimpleTarget<GlideDrawable> simpleTarget = new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
                    mSignRelativelayout.setBackground(resource);
                }
            };
            Glide.with(this)
                    .load(userBean.getImage_url())
                    .placeholder(R.color.colorWhite)
                    .error(R.mipmap.image_loaderror)
                    .dontAnimate()
                    .centerCrop()
                    .transform(new CenterCrop(getActivity()), new GlideRoundTransform(getActivity(), 5))
                    .skipMemoryCache(false)
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(simpleTarget);

            Glide.with(this)
                    .load(userBean.getHead())
                    .error(R.mipmap.icon_headdefault)
                    .dontAnimate()
                    .crossFade()
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .into(mIconUser);

            if (!TextUtils.isEmpty(userBean.getUsername())) {
                mNicknameUser.setText(userBean.getUsername());
            }
            mSignDaysUser.setText(String.valueOf(userBean.getNum()));
        }
        initData();
    }

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
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
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
                Bitmap bitmap = null;
                try {
                    bitmap = AppUtils.captureView(mSignRelativelayout);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                Bitmap zoomImage = AppUtils.zoomImage(bitmap, 720, 1280);
                ShareUtils.getInstance().shareImageToWx2(zoomImage, getResources().getString(R.string.default_title), "学习打卡", SendMessageToWX.Req.WXSceneTimeline);
                dismiss();
            }
        });
        mToweixinShare.setOnClickListener(new View.OnClickListener() {//TODO 分享至微信好友
            @Override
            public void onClick(View v) {
                Bitmap bitmap = null;
                try {
                    bitmap = AppUtils.captureView(mSignRelativelayout);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                Bitmap zoomImage = AppUtils.zoomImage(bitmap, 720, 1280);
                ShareUtils.getInstance().shareImageToWx2(zoomImage, getResources().getString(R.string.default_title), "学习打卡", SendMessageToWX.Req.WXSceneSession);
                dismiss();
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
        mToweixinShare.setOnClickListener(this);
        mTofriendShare = (ImageView) itemView.findViewById(R.id.share_tofriend);
        mTofriendShare.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_toweixin:
                // TODO 19/07/23
                break;
            case R.id.share_tofriend:
                // TODO 19/07/23
                break;
            default:
                break;
        }
    }
}
