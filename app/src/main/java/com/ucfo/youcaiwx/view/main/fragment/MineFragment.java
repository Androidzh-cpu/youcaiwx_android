package com.ucfo.youcaiwx.view.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.address.StateStatusBean;
import com.ucfo.youcaiwx.entity.user.UserInfoBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.UserInfoPresenter;
import com.ucfo.youcaiwx.presenter.view.user.IUserInfoView;
import com.ucfo.youcaiwx.utils.ShareUtils;
import com.ucfo.youcaiwx.utils.glideutils.GlideRoundTransform;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.StatusBarUtil;
import com.ucfo.youcaiwx.view.login.LoginActivity;
import com.ucfo.youcaiwx.view.main.activity.MainActivity;
import com.ucfo.youcaiwx.view.main.activity.WebActivity;
import com.ucfo.youcaiwx.view.user.activity.FeedBackActivity;
import com.ucfo.youcaiwx.view.user.activity.MineAnswerQuestionActivity;
import com.ucfo.youcaiwx.view.user.activity.MineCollectionActivity;
import com.ucfo.youcaiwx.view.user.activity.MineCouponsActivity;
import com.ucfo.youcaiwx.view.user.activity.MineCourseActivity;
import com.ucfo.youcaiwx.view.user.activity.MineOrderFormActivity;
import com.ucfo.youcaiwx.view.user.activity.OfflineCourseActivity;
import com.ucfo.youcaiwx.view.user.activity.PersonnelSettingActivity;
import com.ucfo.youcaiwx.view.user.activity.SettingActivity;
import com.ucfo.youcaiwx.view.user.activity.WatchTheRecordActivity;
import com.ucfo.youcaiwx.widget.dialog.ShareDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author:AND
 * Time: 2019-3-12.  上午 10:19
 * Email:2911743255@qq.com
 * ClassName: MineFragment
 * Description:TODO 首页 -  个人中心
 */
public class MineFragment extends BaseFragment implements IUserInfoView {
    public static final String TAG = "MineFragment";
    @BindView(R.id.titlebar_title)
    TextView titlebarTitle;
    @BindView(R.id.titlebar_setting)
    ImageView titlebarSetting;
    @BindView(R.id.user_icon)
    CircleImageView userIcon;
    @BindView(R.id.user_nickname)
    TextView userNickname;
    @BindView(R.id.user_sex)
    ImageView userSex;
    @BindView(R.id.btn_userInfo)
    LinearLayout btnUserInfo;
    @BindView(R.id.user_integral)
    TextView userIntegral;
    @BindView(R.id.btn_user_integral)
    LinearLayout btnUserIntegral;
    @BindView(R.id.user_balance)
    TextView userBalance;
    @BindView(R.id.btn_user_balance)
    LinearLayout btnUserBalance;
    @BindView(R.id.user_coupons)
    TextView userCoupons;
    @BindView(R.id.user_couponsMsg)
    ImageView userCouponsMsg;
    @BindView(R.id.btn_user_coupons)
    LinearLayout btnUserCoupons;
    @BindView(R.id.user_course)
    TextView userCourse;
    @BindView(R.id.user_collection)
    TextView userCollection;
    @BindView(R.id.user_offline)
    TextView userOffline;
    @BindView(R.id.user_order)
    TextView userOrder;
    @BindView(R.id.user_answer)
    TextView userAnswer;
    @BindView(R.id.user_record)
    TextView userRecord;
    @BindView(R.id.btn_wxRemind)
    LinearLayout btnWxRemind;
    @BindView(R.id.btn_recommendfriend)
    LinearLayout btnRecommendfriend;
    @BindView(R.id.btn_call)
    LinearLayout btnCall;
    @BindView(R.id.btn_feedback)
    LinearLayout btnFeedback;
    @BindView(R.id.btn_about)
    LinearLayout btnAbout;
    @BindView(R.id.statusbar_view)
    View statusbarView;
    Unbinder unbinder;

    private int mOffset = 0, mScrollY = 0, user_id;
    private MainActivity context;
    private UserInfoPresenter userInfoPresenter;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private boolean loginstatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static MineFragment newInstance(String content, String tab) {
        MineFragment newFragment = new MineFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        bundle.putString("tab", tab);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    protected void initView(View view) {
        context = (MainActivity) getActivity();
        userInfoPresenter = new UserInfoPresenter(this);
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) statusbarView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = StatusBarUtil.getStatusBarHeight(getActivity());
        statusbarView.setLayoutParams(layoutParams);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onVisibleToUser() {
        super.onVisibleToUser();
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        loginstatus = sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false);
        if (loginstatus) {
            userInfoPresenter.getUserInfo(user_id);
        } else {
            updateUnLoginUi();
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_mine;
    }

    @OnClick({R.id.titlebar_setting, R.id.btn_userInfo, R.id.btn_user_integral, R.id.btn_user_balance, R.id.btn_user_coupons, R.id.user_course, R.id.user_collection, R.id.user_offline, R.id.user_order, R.id.user_answer, R.id.user_record, R.id.btn_wxRemind, R.id.btn_recommendfriend, R.id.btn_call, R.id.btn_feedback, R.id.btn_about})
    public void onViewClicked(View view) {
        if (sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false)) {
            Bundle bundle = new Bundle();
            switch (view.getId()) {
                case R.id.titlebar_setting://TODO 设置
                    startActivity(SettingActivity.class, null);
                    break;
                case R.id.btn_userInfo://TODO 个人设置中心
                    startActivity(PersonnelSettingActivity.class, null);
                    break;
                case R.id.btn_user_integral://TODO 积分
                    break;
                case R.id.btn_user_balance://TODO 余额
                    break;
                case R.id.btn_user_coupons://TODO 优惠券
                    startActivity(MineCouponsActivity.class, null);
                    break;
                case R.id.user_course://TODO 我的课程
                    startActivity(MineCourseActivity.class, null);
                    break;
                case R.id.user_collection://TODO 我的收藏
                    startActivity(MineCollectionActivity.class, null);
                    break;
                case R.id.user_offline://TODO 离线课程
                    startActivity(OfflineCourseActivity.class, null);
                    break;
                case R.id.user_order://TODO 我的订单
                    startActivity(MineOrderFormActivity.class, null);
                    break;
                case R.id.user_answer://TODO 我的答疑
                    startActivity(MineAnswerQuestionActivity.class, null);
                    break;
                case R.id.user_record://TODO 观看记录
                    startActivity(WatchTheRecordActivity.class, null);
                    break;
                case R.id.btn_wxRemind://TODO 学习提醒
                    /*String certificateSHA1Fingerprint = SHA1Utils.getCertificateSHA1Fingerprint(getActivity());
                    LogUtils.e("设备唯一标识----------------:" + certificateSHA1Fingerprint);*/
                    break;
                case R.id.btn_recommendfriend://TODO 推荐给好友
                    new ShareDialog(getActivity())
                            .builder()
                            .setFriendButton(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String iamgeurl = "http://s1.dwstatic.com/group1/M00/A3/AE/96a6b6189983ad3eee816d3ce00eaeb9.jpg";
                                    ShareUtils.getInstance().shareImageToWx(iamgeurl, "兔子喝水吗", "原来兔子不喝水", SendMessageToWX.Req.WXSceneSession);
                                }
                            })
                            .setCircleToFriendButton(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String url = "https://www.iconfont.cn/";
                                    String title = "Iconfont";
                                    String desc = "一个开源的标准图库";
                                    String iamgeurl = "http://s1.dwstatic.com/group1/M00/A3/AE/96a6b6189983ad3eee816d3ce00eaeb9.jpg";
                                    ShareUtils.getInstance().shareUrlToWx(url, title, desc, iamgeurl, SendMessageToWX.Req.WXSceneSession);
                                }
                            })
                            .show();
                    break;
                case R.id.btn_call://TODO 打电话
                    MainActivity activity = (MainActivity) getActivity();
                    assert activity != null;
                    activity.makeCall();
                    break;
                case R.id.btn_feedback://TODO 意见反馈
                    startActivity(FeedBackActivity.class, bundle);
                    break;
                case R.id.btn_about://TODO 关于
                    bundle.putString(Constant.WEB_TITLE, getResources().getString(R.string.mine_about));
                    bundle.putString(Constant.WEB_URL, ApiStores.USER_ABOUT_YOUCAI);
                    startActivity(WebActivity.class, bundle);
                    break;
                default:
                    break;

            }

        } else {//未登录,去登录页
            startActivity(new Intent(context, LoginActivity.class));
        }
    }

    @Override
    public void getUserInfo(UserInfoBean bean) {
        if (bean != null) {
            UserInfoBean.DataBean data = bean.getData();
            if (data != null) {
                updateLoginUi(data);
            } else {
                updateUnLoginUi();
            }
        } else {
            updateUnLoginUi();
        }
    }

    //TODO 设置未登录个人信息
    public void updateUnLoginUi() {
        userBalance.setText(String.valueOf(0 + getResources().getString(R.string.mine_yuan)));
        userCoupons.setText(String.valueOf(0 + getResources().getString(R.string.mine_zhang)));
        userIntegral.setText(String.valueOf(0));
        userNickname.setText(getResources().getString(R.string.default_title));
        userSex.setVisibility(userSex.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
        userCouponsMsg.setVisibility(userCouponsMsg.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
        userIcon.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.icon_headdefault));
    }

    //TODO 设置登录个人信息
    public void updateLoginUi(UserInfoBean.DataBean dataBean) {
        String head = dataBean.getHead();
        String balance = dataBean.getBalance();
        String username = dataBean.getUsername();
        int sex = dataBean.getSex();
        int coupon = dataBean.getCoupon();
        int is_read = dataBean.getIs_read();
        int integral = dataBean.getIntegral();
        if (TextUtils.isEmpty(head)) {
            userIcon.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.icon_headdefault));
        } else {
            Glide.with(context)//todo 头像
                    .load(head)
                    .transform(new CenterCrop(context), new GlideRoundTransform(context))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .into(userIcon);
        }
        if (!TextUtils.isEmpty(username)) {//todo 昵称
            userNickname.setText(username);
        } else {
            userNickname.setText("");
        }
        if (!TextUtils.isEmpty(balance)) {//todo 余额
            userBalance.setText(String.valueOf(balance + getResources().getString(R.string.mine_yuan)));
        } else {
            userBalance.setText(String.valueOf(0 + getResources().getString(R.string.mine_yuan)));
        }
        userIntegral.setText(String.valueOf(integral));//todo 积分
        userCoupons.setText(String.valueOf(coupon + getResources().getString(R.string.mine_zhang)));//todo 优惠券

        switch (sex) {
            case 1://man
                userSex.setVisibility(userSex.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
                userSex.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.icon_sex_man));
                break;
            case 2://woman
                userSex.setVisibility(userSex.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
                userSex.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.icon_sex_woman));
                break;
            default://default
                userSex.setVisibility(userSex.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
                break;
        }
        switch (is_read) {
            case 2://未查看
                userCouponsMsg.setVisibility(userCouponsMsg.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
                break;
            case 1://查看
            default:
                userCouponsMsg.setVisibility(userCouponsMsg.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
                break;
        }
    }

    @Override
    public void retouceResult(StateStatusBean result) {
        //TODO nothing
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showLoadingFinish() {

    }

    @Override
    public void showError() {

    }
}