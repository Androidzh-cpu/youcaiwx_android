package com.ucfo.youcaiwx.module.course.player.fragment;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.course.CourseTeacherAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.course.CourseIntroductionBean;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.AppUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.module.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.customview.NoScrollWebView;
import com.ucfo.youcaiwx.widget.shimmer.ShimmerRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author:29117
 * Time: 2019-4-3.  上午 9:56
 * Email:2911743255@qq.com
 * ClassName: CourseIntroductionFragment
 * Description:TODO 课程简介
 */
public class CourseIntroductionFragment extends BaseFragment {
    @BindView(R.id.webview)
    NoScrollWebView webView;
    Unbinder unbinder;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.course_name)
    TextView courseName;
    @BindView(R.id.course_price)
    RoundTextView coursePrice;
    @BindView(R.id.course_count)
    TextView courseCount;
    @BindView(R.id.course_teacher)
    TextView courseTeacher;
    @BindView(R.id.course_time)
    TextView courseTime;
    @BindView(R.id.course_detail)
    TextView courseDetail;
    @BindView(R.id.recyclerview_teacher)
    ShimmerRecyclerView recyclerviewTeacher;
    private int course_packageId;
    private List<CourseIntroductionBean.DataBean.TeacehrListBean> teacehrListBeanList;
    private CourseTeacherAdapter courseTeacherAdapter;
    private Dialog teacherDialog;
    private int user_id;
    private VideoPlayPageActivity videoPlayPageActivity;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
    protected void initView(View view) {
        FragmentActivity activity = getActivity();
        if (activity instanceof VideoPlayPageActivity) {
            videoPlayPageActivity = (VideoPlayPageActivity) getActivity();
        }
        videoPlayPageActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setDefaultWebSettings(webView);
    }

    @Override
    protected void initData() {
        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity instanceof VideoPlayPageActivity) {
            videoPlayPageActivity = (VideoPlayPageActivity) fragmentActivity;
        }

        teacehrListBeanList = new ArrayList<>();

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(videoPlayPageActivity);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerviewTeacher.setLayoutManager(layoutManager2);
        recyclerviewTeacher.setItemAnimator(new DefaultItemAnimator());
        int topBottom = DensityUtil.dip2px(videoPlayPageActivity, 1);
        int leftright = DensityUtil.dip2px(videoPlayPageActivity, 12);
        recyclerviewTeacher.addItemDecoration(new SpacesItemDecoration(leftright, topBottom, ContextCompat.getColor(context, R.color.color_E6E6E6)));
        recyclerviewTeacher.setNestedScrollingEnabled(false);
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCourseInfo(course_packageId, user_id);
            }
        });

    }

    /**
     * Description:CourseIntroductionFragment
     * Time:2019-4-4   下午 4:15
     * Detail:尽在用户第一次可见是加载数据
     */
    @Override
    protected void onLazyLoadOnce() {
        super.onLazyLoadOnce();
        course_packageId = videoPlayPageActivity.getCoursePackageId();
        user_id = SharedPreferencesUtils.getInstance(videoPlayPageActivity).getInt(Constant.USER_ID, 0);

        loadCourseInfo(course_packageId, user_id);
    }

    /**
     * Description:CourseIntroductionFragment
     * Time:2019-4-3   下午 3:40
     * Detail:TODO 获取课程简介信息
     */
    private void loadCourseInfo(int course_packageId, int user_id) {
        String id;
        if (user_id == 0) {
            id = "";
        } else {
            id = String.valueOf(user_id);
        }
        OkGo.<String>post(ApiStores.COURSE_INTORDUCTION)
                .params(Constant.ID, course_packageId)
                .params(Constant.USER_ID, id)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        loadinglayout.showError();
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        loadinglayout.showLoading();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (!body.equals("")) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(body);
                                int code = jsonObject.optInt(Constant.CODE);
                                if (code == 200) {
                                    Gson gson = new Gson();
                                    CourseIntroductionBean courseIntroductionBean = gson.fromJson(response.body(), CourseIntroductionBean.class);
                                    getCourseInfo(courseIntroductionBean);
                                } else {
                                    loadinglayout.showEmpty();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loadinglayout.showEmpty();
                        }
                    }
                });
    }

    /*
    TODO 获取到课程详细信息
    * */
    private void getCourseInfo(CourseIntroductionBean courseIntroductionBean) {
        if (courseIntroductionBean != null) {
            CourseIntroductionBean.DataBean data = courseIntroductionBean.getData();
            if (teacehrListBeanList == null) {
                teacehrListBeanList = new ArrayList<>();
            }
            List<CourseIntroductionBean.DataBean.TeacehrListBean> teacehr_list = data.getTeacehr_list();
            teacehrListBeanList.clear();
            teacehrListBeanList.addAll(teacehr_list);

            if (courseTeacherAdapter == null) {
                courseTeacherAdapter = new CourseTeacherAdapter(teacehrListBeanList, videoPlayPageActivity);
            } else {
                courseTeacherAdapter.notifyDataSetChanged();
            }
            recyclerviewTeacher.setAdapter(courseTeacherAdapter);
            courseTeacherAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    CourseIntroductionBean.DataBean.TeacehrListBean teacehrListBean = teacehrListBeanList.get(position);
                    teacherDialog(teacehrListBean);
                }
            });

            String briefImg = data.getBrief_img();//课程简介图
            String name = data.getName();//课程名字
            String description = data.getDescription();//描述
            String price = data.getPrice();//价格
            int joinNum = data.getJoin_num();//参加人数
            int studyDays = data.getStudy_days();//课时
            String teacherName = data.getTeacher_name();//讲师名字
            String isPurchase = data.getIs_purchase();//课程是否购买
            String appImg = data.getApp_img();
            if (TextUtils.isEmpty(isPurchase)) {//TODO 课程购买状态
                videoPlayPageActivity.setCourseBuyState(2);
            } else {
                videoPlayPageActivity.setCourseBuyState(Integer.parseInt(isPurchase));
            }
            videoPlayPageActivity.setCourse_PackagePrice(price);//TODO 课程购买价格
            videoPlayPageActivity.setCourse_Cover(appImg);

            webView.loadUrl(briefImg);
            if (!TextUtils.isEmpty(name)) {
                courseName.setText(name);
            }
            if (!TextUtils.isEmpty(description)) {
                courseDetail.setText(description);
            }
            if (!TextUtils.isEmpty(price)) {
                coursePrice.setText(String.valueOf(getResources().getString(R.string.RMB) + price));
            }
            if (!TextUtils.isEmpty(teacherName)) {
                courseTeacher.setText(String.valueOf(getResources().getString(R.string.holder_teacher) + teacherName));
            }
            courseCount.setText(String.valueOf(joinNum + getResources().getString(R.string.people)));
            //courseTime.setText(String.valueOf(getResources().getString(R.string.holder_courseTime) + studyDays + getResources().getString(R.string.mine_Course_holder2)));
            courseTime.setText(String.valueOf(String.valueOf(getResources().getString(R.string.orderForm_endtime2, String.valueOf(studyDays)))));
            loadinglayout.showContent();
        } else {
            loadinglayout.showEmpty();
        }

    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_courseintroduction;
    }

    /**
     * Description:WebActivity
     * Time:2019-3-22   下午 2:56
     * Detail:TODO 参考考拉的配置
     */
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
        webSetting.setAppCachePath(videoPlayPageActivity.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(getActivity().getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(getActivity().getDir("geolocation", 0).getPath());
        //允许WebView使用File协议
        webSetting.setAllowFileAccess(true);
        //不保存密码
        webSetting.setSavePassword(false);
        //设置UA
        webSetting.setUserAgentString(webSetting.getUserAgentString() + " youcaiApp/" + AppUtils.getAppVersionName(videoPlayPageActivity));
        //自动加载图片
        webSetting.setLoadsImagesAutomatically(true);
    }

    private void teacherDialog(CourseIntroductionBean.DataBean.TeacehrListBean teacehrListBean) {
        teacherDialog = new Dialog(videoPlayPageActivity, R.style.TeacherDialog);
        View contentView = LayoutInflater.from(videoPlayPageActivity).inflate(R.layout.dialog_teacher_detail, null);
        teacherDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        contentView.setLayoutParams(layoutParams);
        teacherDialog.getWindow().setGravity(Gravity.CENTER);
        teacherDialog.getWindow().setWindowAnimations(R.style.Theme_AppCompat_Dialog);
        teacherDialog.setCanceledOnTouchOutside(true);
        teacherDialog.setCancelable(true);
        teacherDialog.show();
        initteacherView(contentView, teacehrListBean);
        contentView.findViewById(R.id.dialog_close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacherDialog.dismiss();
            }
        });
    }

    private void initteacherView(View contentView, CourseIntroductionBean.DataBean.TeacehrListBean teacehrListBean) {
        CircleImageView mIconTeacher = (CircleImageView) contentView.findViewById(R.id.teacher_icon);
        TextView mtitleTeacher = (TextView) contentView.findViewById(R.id.teacher_title);
        TextView mNameTeacher = (TextView) contentView.findViewById(R.id.teacher_name);
        TextView mDetailTeacher = (TextView) contentView.findViewById(R.id.teacher_detail);
        ImageView contentViewViewById = contentView.findViewById(R.id.teacher_back);
        mIconTeacher.bringToFront();
        contentViewViewById.bringToFront();

        String teacher_title = teacehrListBean.getTeacher_title();
        String pictrue = teacehrListBean.getPictrue();
        String longevity = teacehrListBean.getLongevity();
        String teacher_name = teacehrListBean.getTeacher_name();

        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.banner_default)
                .error(R.mipmap.image_loaderror)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        GlideUtils.load(context, pictrue, mIconTeacher, requestOptions);
        mtitleTeacher.setText(teacher_title);
        mDetailTeacher.setText(longevity);
        mNameTeacher.setText(teacher_name);
    }

}


