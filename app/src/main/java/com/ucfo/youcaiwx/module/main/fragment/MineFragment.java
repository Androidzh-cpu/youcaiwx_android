package com.ucfo.youcaiwx.module.main.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.address.StateStatusBean;
import com.ucfo.youcaiwx.entity.user.UserInfoBean;
import com.ucfo.youcaiwx.module.integral.MineIntegralActivity;
import com.ucfo.youcaiwx.module.login.LoginActivity;
import com.ucfo.youcaiwx.module.main.activity.MainActivity;
import com.ucfo.youcaiwx.module.main.activity.WebActivity;
import com.ucfo.youcaiwx.module.user.activity.CPEApplyForActivity;
import com.ucfo.youcaiwx.module.user.activity.FeedBackActivity;
import com.ucfo.youcaiwx.module.user.activity.MineAnswerQuestionActivity;
import com.ucfo.youcaiwx.module.user.activity.MineCollectionActivity;
import com.ucfo.youcaiwx.module.user.activity.MineCouponsActivity;
import com.ucfo.youcaiwx.module.user.activity.MineCourseActivity;
import com.ucfo.youcaiwx.module.user.activity.MineEventActivity;
import com.ucfo.youcaiwx.module.user.activity.MineOrderFormActivity;
import com.ucfo.youcaiwx.module.user.activity.OfflineCourseActivity;
import com.ucfo.youcaiwx.module.user.activity.PersonnelSettingActivity;
import com.ucfo.youcaiwx.module.user.activity.SettingActivity;
import com.ucfo.youcaiwx.module.user.activity.WatchTheRecordActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.integral.EarnIntegralPresenter;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.UserInfoPresenter;
import com.ucfo.youcaiwx.presenter.view.user.IUserInfoView;
import com.ucfo.youcaiwx.utils.ShareUtils;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.StatusBarUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.dialog.AlertDialog;
import com.ucfo.youcaiwx.widget.dialog.ShareDialog;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author:AND
 * Time: 2019-3-12.  上午 10:19
 * Email:2911743255@qq.com
 * ClassName: MineFragment
 * Description:TODO 首页 -  个人中心
 */
public class MineFragment extends BaseFragment implements IUserInfoView, View.OnClickListener {
    public static final String TAG = "MineFragment";

    private TextView titlebarTitle;
    private ImageView titlebarSetting;
    private CircleImageView userIcon;
    private TextView userNickname;
    private ImageView userSex;
    private LinearLayout btnUserInfo;
    private TextView userIntegral;
    private LinearLayout btnUserIntegral;
    private TextView userBalance;
    private LinearLayout btnUserBalance;
    private TextView userCoupons;
    private ImageView userCouponsMsg;
    private LinearLayout btnUserCoupons;
    private TextView userCourse;
    private TextView userCollection;
    private TextView userCPE;
    private TextView userOffline;
    private TextView userOrder;
    private TextView userAnswer;
    private TextView userEvent;
    private TextView userRecord;
    private LinearLayout btnWxRemind;
    private LinearLayout btnRecommendfriend;
    private LinearLayout btnCall;
    private LinearLayout btnFeedback;
    private LinearLayout btnAbout;
    private View statusbarView;
    private LoadingLayout loadinglayout;

    private int mOffset = 0, mScrollY = 0, user_id;
    private UserInfoPresenter userInfoPresenter;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private boolean loginstatus;

    public static MineFragment newInstance(String content, String tab) {
        MineFragment newFragment = new MineFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        bundle.putString("tab", tab);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View itemView) {
        statusbarView = (View) itemView.findViewById(R.id.statusbar_view);
        titlebarTitle = (TextView) itemView.findViewById(R.id.titlebar_title);
        titlebarSetting = (ImageView) itemView.findViewById(R.id.titlebar_setting);
        userIcon = (CircleImageView) itemView.findViewById(R.id.user_icon);
        userNickname = (TextView) itemView.findViewById(R.id.user_nickname);
        userSex = (ImageView) itemView.findViewById(R.id.user_sex);
        btnUserInfo = (LinearLayout) itemView.findViewById(R.id.btn_userInfo);
        btnUserInfo.setOnClickListener(this);
        userIntegral = (TextView) itemView.findViewById(R.id.user_integral);
        btnUserIntegral = (LinearLayout) itemView.findViewById(R.id.btn_user_integral);
        btnUserIntegral.setOnClickListener(this);
        userBalance = (TextView) itemView.findViewById(R.id.user_balance);
        btnUserBalance = (LinearLayout) itemView.findViewById(R.id.btn_user_balance);
        btnUserBalance.setOnClickListener(this);
        userCoupons = (TextView) itemView.findViewById(R.id.user_coupons);
        userCouponsMsg = (ImageView) itemView.findViewById(R.id.user_couponsMsg);
        btnUserCoupons = (LinearLayout) itemView.findViewById(R.id.btn_user_coupons);
        btnUserCoupons.setOnClickListener(this);
        userCourse = (TextView) itemView.findViewById(R.id.user_course);
        userCourse.setOnClickListener(this);
        userCollection = (TextView) itemView.findViewById(R.id.user_collection);
        userCPE = (TextView) itemView.findViewById(R.id.user_cpeapplyfor);
        userCPE.setOnClickListener(this);
        userCollection.setOnClickListener(this);
        userOffline = (TextView) itemView.findViewById(R.id.user_offline);
        userOffline.setOnClickListener(this);
        userOrder = (TextView) itemView.findViewById(R.id.user_order);
        userOrder.setOnClickListener(this);
        userAnswer = (TextView) itemView.findViewById(R.id.user_answer);
        userEvent = (TextView) itemView.findViewById(R.id.user_event);
        userEvent.setOnClickListener(this);
        userAnswer.setOnClickListener(this);
        userRecord = (TextView) itemView.findViewById(R.id.user_record);
        userRecord.setOnClickListener(this);
        btnWxRemind = (LinearLayout) itemView.findViewById(R.id.btn_wxRemind);
        btnWxRemind.setOnClickListener(this);
        btnRecommendfriend = (LinearLayout) itemView.findViewById(R.id.btn_recommendfriend);
        btnRecommendfriend.setOnClickListener(this);
        btnCall = (LinearLayout) itemView.findViewById(R.id.btn_call);
        btnCall.setOnClickListener(this);
        btnFeedback = (LinearLayout) itemView.findViewById(R.id.btn_feedback);
        btnFeedback.setOnClickListener(this);
        btnAbout = (LinearLayout) itemView.findViewById(R.id.btn_about);
        btnAbout.setOnClickListener(this);
        loadinglayout = (LoadingLayout) itemView.findViewById(R.id.loadinglayout);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) statusbarView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = StatusBarUtil.getStatusBarHeight(getContext());
        statusbarView.setLayoutParams(layoutParams);
    }

    @Override
    protected void initData() {
        //设置中心
        titlebarSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SettingActivity.class, null);
            }
        });
        //意见反馈
        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(FeedBackActivity.class);
            }
        });

        userInfoPresenter = new UserInfoPresenter(this);
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(getContext());

        loadUserData();

        if (loadinglayout != null) {
            loadinglayout.setRetryListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadUserData();
                }
            });
        }
    }

    /**
     * 个人信息
     */
    private void loadUserData() {
        if (sharedPreferencesUtils == null) {
            sharedPreferencesUtils = SharedPreferencesUtils.getInstance(getContext());
        }
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        loginstatus = sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false);

        if (loginstatus) {
            if (userInfoPresenter != null) {
                userInfoPresenter.getUserInfo(user_id);
            }
        } else {
            if (isAdded()) {
                updateUnLoginUi();
                showContent();
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            loadUserData();
        }
    }

    @Override
    public void onClick(View view) {
        boolean loginStatus = sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false);
        if (loginStatus) {
            Bundle bundle = new Bundle();
            switch (view.getId()) {
                case R.id.btn_userInfo:
                    //TODO 个人设置中心
                    startActivity(PersonnelSettingActivity.class, null);
                    break;
                case R.id.btn_user_integral:
                    //TODO 积分
                    startActivity(MineIntegralActivity.class, null);
                    break;
                case R.id.btn_user_balance:
                    //TODO 余额
                    noDev();
                    break;
                case R.id.btn_user_coupons:
                    //TODO 优惠券
                    startActivity(MineCouponsActivity.class, null);
                    break;
                case R.id.user_course:
                    //TODO 我的课程
                    startActivity(MineCourseActivity.class, null);
                    break;
                case R.id.user_collection:
                    //TODO 我的收藏
                    startActivity(MineCollectionActivity.class, null);
                    break;
                case R.id.user_cpeapplyfor:
                    //TODO cpe学分报告
                    startActivity(CPEApplyForActivity.class);
                    break;
                case R.id.user_event:
                    //TODO 我的活动
                    startActivity(MineEventActivity.class);
                    break;
                case R.id.user_offline:
                    //TODO 离线课程
                    startActivity(OfflineCourseActivity.class, null);
                    break;
                case R.id.user_order:
                    //TODO 我的订单
                    startActivity(MineOrderFormActivity.class, null);
                    break;
                case R.id.user_answer:
                    //TODO 我的答疑
                    startActivity(MineAnswerQuestionActivity.class, null);
                    break;
                case R.id.user_record:
                    //TODO 观看记录
                    startActivity(WatchTheRecordActivity.class, null);
                    break;
                case R.id.btn_wxRemind:
                    //TODO 学习提醒
                    /*String certificateSHA1Fingerprint = SHA1Utils.getCertificateSHA1Fingerprint(getActivity());
                    LogUtils.e("设备唯一标识----------------:" + certificateSHA1Fingerprint);*/
                    noDev();
                    break;
                case R.id.btn_recommendfriend:
                    //TODO 推荐给好友
                    shareToFriend();
                    break;
                case R.id.btn_call:
                    //TODO 电话
                    call();
                    break;
                case R.id.btn_about:
                    //TODO 关于
                    bundle.putString(Constant.WEB_TITLE, getResources().getString(R.string.mine_about));
                    bundle.putString(Constant.WEB_URL, ApiStores.USER_ABOUT_YOUCAI);
                    startActivity(WebActivity.class, bundle);
                    break;
                default:
                    break;

            }
        } else {//未登录,去登录页
            if (getContext() != null) {
                startActivity(LoginActivity.class);
            }
        }
    }

    /**
     * 打电话
     */
    private void call() {
        if (getActivity() != null) {
            FragmentActivity activity = getActivity();
            if (activity instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) activity;
                mainActivity.makeCall();
            }
        }
    }

    /**
     * 分享给朋友
     */
    private void shareToFriend() {
        new ShareDialog(getContext()).builder()
                .setFriendButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = ApiStores.APP_DOWNLOAD_URL;
                        String title = getResources().getString(R.string.app_nameWX);
                        String desc = getResources().getString(R.string.youcaiWXShareDescribe);
                        ShareUtils.getInstance().shareUrlToWx(url, title, desc, SendMessageToWX.Req.WXSceneSession);

                        //设置签到积分
                        EarnIntegralPresenter.getInstance().setIntegralType(Constant.INTEGRAL_SHARE);
                    }
                })
                .setCircleToFriendButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = ApiStores.APP_DOWNLOAD_URL;
                        String title = getResources().getString(R.string.app_nameWX);
                        String desc = getResources().getString(R.string.youcaiWXShareDescribe);
                        ShareUtils.getInstance().shareUrlToWx(url, title, desc, SendMessageToWX.Req.WXSceneTimeline);

                        //设置签到积分
                        EarnIntegralPresenter.getInstance().setIntegralType(Constant.INTEGRAL_SHARE);
                    }
                })
                .show();
    }

    @Override
    public void getUserInfo(UserInfoBean bean) {
        if (bean != null) {
            UserInfoBean.DataBean data = bean.getData();
            if (data != null) {
                if (isAdded()) {
                    updateLoginUi(data);
                    showContent();
                }
            }
        }
    }

    private void showContent() {
        if (loadinglayout != null) {
            loadinglayout.showContent();
        }
    }

    /**
     * 暂未开发
     */
    private void noDev() {
        new AlertDialog(Objects.requireNonNull(getContext())).builder().setCancelable(false).setCanceledOnTouchOutside(false)
                .setMsg("暂未开发")
                .setNegativeButton(null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton(null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }

    /**
     * 设置未登录个人信息
     */
    public void updateUnLoginUi() {
        userBalance.setText(String.format("%s%s", "0", getResources().getString(R.string.mine_yuan)));
        userCoupons.setText(String.format("%s%s", "0", getResources().getString(R.string.mine_zhang)));
        userIntegral.setText(String.valueOf(0));
        userNickname.setText(getResources().getString(R.string.mine_tologin));
        userNickname.setCompoundDrawables(null, null, null, null);
        userSex.setVisibility(userSex.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
        userCouponsMsg.setVisibility(userCouponsMsg.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
        userIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.icon_account_btn));
    }

    /**
     * 设置登录个人信息
     */
    public void updateLoginUi(UserInfoBean.DataBean dataBean) {
        String head = dataBean.getHead();
        String balance = dataBean.getBalance();
        String username = dataBean.getUsername();
        int sex = dataBean.getSex();
        String coupon = dataBean.getCoupon();
        String isRead = dataBean.getIs_read();
        String integral = dataBean.getIntegral();
        if (TextUtils.isEmpty(head)) {
            userIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.icon_account_btn));
        } else {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.icon_account_btn)
                    .error(R.mipmap.icon_account_btn)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            GlideUtils.load(getContext(), head, userIcon, requestOptions);
        }
        if (!TextUtils.isEmpty(username)) {//todo 昵称
            userNickname.setText(username);
        } else {
            userNickname.setText("");
        }
        if (!TextUtils.isEmpty(balance)) {//todo 余额
            userBalance.setText(String.format("%s%s", balance, getResources().getString(R.string.mine_yuan)));
        } else {
            userBalance.setText(String.format("%s%s", "0", getResources().getString(R.string.mine_yuan)));
        }
        userIntegral.setText(integral);//todo 积分
        userCoupons.setText(String.format("%s%s", coupon, getResources().getString(R.string.mine_zhang)));//todo 优惠券

        switch (sex) {
            case 1://man
                Drawable drawableMan = ContextCompat.getDrawable(getContext(), R.mipmap.icon_sex_man);
                userSex.setVisibility(View.VISIBLE);
                userSex.setImageDrawable(drawableMan);
                break;
            case 2://woman
                Drawable drawableWoman = ContextCompat.getDrawable(getContext(), R.mipmap.icon_sex_woman);
                userSex.setVisibility(View.VISIBLE);
                userSex.setImageDrawable(drawableWoman);
                break;
            default:
                break;
        }
        if (!TextUtils.isEmpty(isRead)) {
            int i = Integer.parseInt(isRead);
            switch (i) {
                case 2:
                    //未查看
                    userCouponsMsg.setVisibility(userCouponsMsg.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
                    break;
                case 1:
                    //查看
                default:
                    userCouponsMsg.setVisibility(userCouponsMsg.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
                    break;
            }
        }
    }

    @Override
    public void retouceResult(StateStatusBean result) {
        //TODO nothing
    }

    @Override
    public void showLoading() {
        //TODO nothing
    }

    @Override
    public void showLoadingFinish() {
        //TODO nothing
    }

    @Override
    public void showError() {
        if (isAdded()) {
            showToast(getResources().getString(R.string.miss_request));
            showContent();
        }
    }
}
