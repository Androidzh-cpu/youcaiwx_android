package com.ucfo.youcaiwx.module.home.event;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.course.CourseTeacherAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.course.CourseIntroductionBean;
import com.ucfo.youcaiwx.entity.home.event.EventDetailedBean;
import com.ucfo.youcaiwx.entity.home.event.EventListBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.home.event.EventPresenter;
import com.ucfo.youcaiwx.presenter.presenterImpl.integral.EarnIntegralPresenter;
import com.ucfo.youcaiwx.presenter.view.home.event.IEventView;
import com.ucfo.youcaiwx.utils.RegexUtil;
import com.ucfo.youcaiwx.utils.ShareUtils;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.AppUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.customview.NoScrollWebView;
import com.ucfo.youcaiwx.widget.dialog.InputInformationDialog;
import com.ucfo.youcaiwx.widget.dialog.ShareDialog;
import com.ucfo.youcaiwx.widget.dialog.TeacherPreviewDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-12-23 下午 4:05
 * Package: com.ucfo.youcaiwx.module.home.event
 * FileName: EventDetailedActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 活动详情页
 */
public class EventDetailedActivity extends BaseActivity implements IEventView {
    @BindView(R.id.imageview)
    ImageView imageview;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.btn_share)
    ImageView btnShare;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_point)
    TextView txtPoint;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.txt_position)
    TextView txtPosition;
    @BindView(R.id.txt_havedJoin)
    TextView txtHavedJoin;
    @BindView(R.id.txt_timeLimit)
    TextView txtTimeLimit;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.webview)
    NoScrollWebView webView;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.btn_next)
    Button btnNext;

    private EventPresenter eventPresenter;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private String user_id;
    private String id;
    private List<CourseIntroductionBean.DataBean.TeacehrListBean> teacehrListBeanList;
    private CourseTeacherAdapter courseTeacherAdapter;
    private String editPhoneConent;
    private String editNameConent;

    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
        webView.resumeTimers();
    }

    @Override
    public void onPause() {
        super.onPause();
        //暂停WebView在后台的所有活动
        webView.onPause();
        //暂停WebView在后台的JS活动
        webView.pauseTimers();
    }

    @Override
    public void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    @Override
    protected void initToolbar() {
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_event_detailed;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setNestedScrollingEnabled(false);
        int topBottom = DensityUtil.dp2px(1);
        int leftright = DensityUtil.dp2px(12);
        int color = ContextCompat.getColor(this, R.color.color_E6E6E6);
        recyclerview.addItemDecoration(new SpacesItemDecoration(leftright, topBottom, color));

        setDefaultWebSettings(webView);
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString(Constant.ID, "");
        }

        teacehrListBeanList = new ArrayList<>();
        eventPresenter = new EventPresenter(this);

        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(this);
        user_id = sharedPreferencesUtils.getString(Constant.USER_ID, "");

        loadNetData();
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNetData();
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void setDefaultWebSettings(WebView webView) {
        WebSettings webSetting = webView.getSettings();
        //5.0以上开启混合模式加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSetting.setSupportMultipleWindows(false);
        webSetting.setGeolocationEnabled(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        //设置自适应屏幕，两者合用
        webSetting.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        webSetting.setUseWideViewPort(true);//将图片调整到适合webview的大小
        //允许js代码
        webSetting.setJavaScriptEnabled(true);
        //允许SessionStorage/LocalStorage存储
        webSetting.setDomStorageEnabled(true);
        //禁用放缩
        webSetting.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSetting.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        //禁用文字缩放
        webSetting.setTextZoom(100);
        //10M缓存，api 18后，系统自动管理。
        webSetting.setAppCacheMaxSize(10 * 1024 * 1024);
        //允许缓存，设置缓存位置
        webSetting.setAppCacheEnabled(true);
        // 设置缓存模式
        webSetting.setCacheMode(WebSettings.LOAD_NORMAL);
        webSetting.setAppCachePath(getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(getDir("geolocation", 0).getPath());
        //允许WebView使用File协议
        webSetting.setAllowFileAccess(true);
        //不保存密码
        webSetting.setSavePassword(false);
        //设置UA
        webSetting.setUserAgentString(webSetting.getUserAgentString() + " youcaiApp/" + AppUtils.getAppVersionName(this));
        //自动加载图片
        webSetting.setLoadsImagesAutomatically(true);
    }

    /**
     * 获取当前活动详情
     */
    private void loadNetData() {
        if (eventPresenter == null) {
            eventPresenter = new EventPresenter(this);
        }
        eventPresenter.initEventDetailed(user_id, id);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick({R.id.btn_back, R.id.btn_share, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_share:
                new ShareDialog(this).builder()
                        .setFriendButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String url = ApiStores.EDUCATION_COURSE_SHARE + "?preview_id=" + id + "&user_id=" + user_id;
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
                                String url = ApiStores.EDUCATION_COURSE_SHARE + "?preview_id=" + id + "&user_id=" + user_id;
                                String title = getResources().getString(R.string.app_nameWX);
                                String desc = getResources().getString(R.string.youcaiWXShareDescribe);
                                ShareUtils.getInstance().shareUrlToWx(url, title, desc, SendMessageToWX.Req.WXSceneTimeline);

                                //设置签到积分
                                EarnIntegralPresenter.getInstance().setIntegralType(Constant.INTEGRAL_SHARE);
                            }
                        })
                        .show();
                break;
            case R.id.btn_next:
                //下一坑
                InputInformationDialog dialog = new InputInformationDialog(this).builder();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setNegativeButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                dialog.setPositiveButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editNameConent = dialog.getEditNameConent();
                        editPhoneConent = dialog.getEditPhoneConent();
                        if (TextUtils.isEmpty(editNameConent)) {
                            dialog.setNameHint();
                            return;
                        }
                        if (TextUtils.isEmpty(editPhoneConent)) {
                            dialog.setPhoneHint();
                            return;
                        }
                        if (!RegexUtil.checkMobile(editPhoneConent)) {
                            showToast(getResources().getString(R.string.login_mobile_iserror));
                            return;
                        }
                        dialog.dissmiss();

                        commitInformation(editNameConent, editPhoneConent);
                    }
                });
                dialog.show();
                break;
            default:
                break;
        }
    }

    /**
     * 提交报名信息
     *
     * @param editNameConent
     * @param editPhoneConent
     */
    private void commitInformation(String editNameConent, String editPhoneConent) {
        eventPresenter.commitEventInformation(user_id, id, editNameConent, editPhoneConent);
    }

    @Override
    public void initEventList(EventListBean bean) {
        //TODO nothing
    }

    @Override
    public void initEventDetailed(EventDetailedBean bean) {
        if (bean != null) {
            initContent(bean);
            loadinglayout.showContent();
        } else {
            loadinglayout.showEmpty();
        }
    }

    @Override
    public void eventApplyResult(int status, String msg) {
        if (status == 1) {
            if (TextUtils.isEmpty(msg)) {
                showToast(getResources().getString(R.string.operation_Success));
            } else {
                showToast(msg);
            }
            loadNetData();
        } else {
            if (TextUtils.isEmpty(msg)) {
                showToast(getResources().getString(R.string.operation_Error));
            } else {
                showToast(msg);
            }
        }
    }

    /**
     * 初始化页面
     */
    private void initContent(EventDetailedBean bean) {
        EventDetailedBean.DataBean data = bean.getData();

        //图片
        String appImg = data.getApp_img();
        String content = data.getContent();
        //活动名
        String name = data.getName();
        //积分
        String cpe = data.getCpe();
        //开始结束时间
        String startTime = data.getStart_time();
        String endTime = data.getEnd_time();
        //活动地址
        String activityAddress = data.getActivity_address();
        //活动人数
        String num = data.getNum();
        //总人数
        String peopleNum = data.getPeople_num();


        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.image_loaderror)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        GlideUtils.load(this, appImg, imageview, requestOptions);
        //content
        webView.loadUrl(content);


        if (!TextUtils.isEmpty(name)) {
            txtTitle.setText(name);
        }
        if (!TextUtils.isEmpty(cpe)) {
            txtPoint.setText(getResources().getString(R.string.event_point, cpe));
        }

        txtTime.setText(String.valueOf(startTime + "-" + endTime));
        if (!TextUtils.isEmpty(activityAddress)) {
            txtPosition.setText(activityAddress);
        }

        txtHavedJoin.setText(getResources().getString(R.string.event_havedJoin, num));
        txtTimeLimit.setText(getResources().getString(R.string.event_timeLimit, peopleNum));

        String timeType = data.getTime_type();
        if (TextUtils.equals(timeType, String.valueOf(1))) {
            //未过期
            String userType = data.getUser_type();
            if (TextUtils.equals(userType, String.valueOf(1))) {
                //未参加
                btnNext.setEnabled(true);
                btnNext.setBackground(ContextCompat.getDrawable(this, R.mipmap.icon_btnbackprimary));
                btnNext.setText(getResources().getString(R.string.event_btn_join));
            } else {
                //已参加
                btnNext.setEnabled(false);
                btnNext.setBackground(ContextCompat.getDrawable(this, R.mipmap.icon_btnbackgray));
                btnNext.setText(getResources().getString(R.string.event_btn_apply));
            }
        } else {
            //活动已过期
            btnNext.setEnabled(false);
            btnNext.setBackground(ContextCompat.getDrawable(this, R.mipmap.icon_btnbackgray));
            btnNext.setText(getResources().getString(R.string.event_btn_pastdue));
        }


        if (data.getTeacehr_list() != null && data.getTeacehr_list().size() > 0) {
            List<EventDetailedBean.DataBean.TeacehrListBean> teacehrList = data.getTeacehr_list();
            for (int i = 0; i < teacehrList.size(); i++) {
                EventDetailedBean.DataBean.TeacehrListBean listBean = teacehrList.get(i);
                String introduce = listBean.getIntroduce();
                String longevity = listBean.getLongevity();
                String teacher_name = listBean.getTeacher_name();
                String teacherTitle = listBean.getTeacher_title();
                String beanPictrue = listBean.getPictrue();
                CourseIntroductionBean.DataBean.TeacehrListBean teacehrListBean = new CourseIntroductionBean.DataBean.TeacehrListBean();
                teacehrListBean.setTeacher_name(teacher_name);
                teacehrListBean.setLongevity(longevity);
                teacehrListBean.setTeacher_title(teacherTitle);
                teacehrListBean.setIntroduce(introduce);
                teacehrListBean.setPictrue(beanPictrue);

                teacehrListBeanList.add(teacehrListBean);
            }
            if (courseTeacherAdapter == null) {
                courseTeacherAdapter = new CourseTeacherAdapter(teacehrListBeanList, this,true);
                recyclerview.setAdapter(courseTeacherAdapter);
            } else {
                courseTeacherAdapter.notifyDataSetChanged();
            }
            courseTeacherAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    courseTeacherAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            CourseIntroductionBean.DataBean.TeacehrListBean teacehrListBean = teacehrListBeanList.get(position);
                            new TeacherPreviewDialog(EventDetailedActivity.this).builder().setEntity(teacehrListBean).show();
                        }
                    });
                }
            });
        }
    }

    @Override
    public void showLoading() {
        setProcessLoading(null, true);
    }

    @Override
    public void showLoadingFinish() {
        dismissPorcess();
    }

    @Override
    public void showError() {
        loadinglayout.showError();
    }

}
