package com.ucfo.youcaiwx.module.course.player.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
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
import com.ucfo.youcaiwx.module.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.AppUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.customview.NoScrollWebView;
import com.ucfo.youcaiwx.widget.shimmer.ShimmerRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author:29117
 * Time: 2019-4-3.  上午 9:56
 * Email:2911743255@qq.com
 * ClassName: CourseIntroductionFragment
 * Description:TODO 课程简介
 */
public class CourseIntroductionFragment extends BaseFragment {
    private NoScrollWebView webView;
    private LoadingLayout loadinglayout;
    private TextView courseName;
    private RoundTextView coursePrice;
    private TextView courseCount;
    private TextView courseTeacher;
    private TextView courseTime;
    private TextView courseDetail;
    private TextView courseIntegral;
    private ShimmerRecyclerView recyclerviewTeacher;

    private int course_packageId;
    private List<CourseIntroductionBean.DataBean.TeacehrListBean> teacehrListBeanList;
    private CourseTeacherAdapter courseTeacherAdapter;
    private Dialog teacherDialog;
    private int user_id;
    private String courseSource = "";

    private CourseIntroductionListener CourseIntroductionListener;

    public interface CourseIntroductionListener {

        int introducationGetCoursePackageId();

        String introducationGetCourse_Source();

        void introducationSetCourseBuyState(int courseBuyState);

        void introducationSetCourse_PackagePrice(String price);

        void introducationSetCourse_Cover(String courseCover);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CourseIntroductionListener) {
            CourseIntroductionListener = (CourseIntroductionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement CourseIntroductionListener");
        }
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
    public void onDetach() {
        super.onDetach();
        CourseIntroductionListener = null;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_courseintroduction;
    }

    @Override
    protected void initView(View itemView) {
        courseName = (TextView) itemView.findViewById(R.id.course_name);
        coursePrice = (RoundTextView) itemView.findViewById(R.id.course_price);
        courseTeacher = (TextView) itemView.findViewById(R.id.course_teacher);
        courseTime = (TextView) itemView.findViewById(R.id.course_time);
        courseCount = (TextView) itemView.findViewById(R.id.course_count);
        courseDetail = (TextView) itemView.findViewById(R.id.course_detail);
        courseIntegral = (TextView) itemView.findViewById(R.id.course_integral);
        recyclerviewTeacher = (ShimmerRecyclerView) itemView.findViewById(R.id.recyclerview_teacher);
        webView = (NoScrollWebView) itemView.findViewById(R.id.webview);
        loadinglayout = (LoadingLayout) itemView.findViewById(R.id.loadinglayout);

        if (getActivity() != null) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
        setDefaultWebSettings(webView);
    }

    @Override
    protected void initData() {
        teacehrListBeanList = new ArrayList<>();

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerviewTeacher.setLayoutManager(layoutManager2);
        recyclerviewTeacher.setItemAnimator(new DefaultItemAnimator());
        int topBottom = DensityUtil.dp2px(1);
        int leftright = DensityUtil.dp2px(12);
        int color = ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.color_E6E6E6);
        recyclerviewTeacher.addItemDecoration(new SpacesItemDecoration(leftright, topBottom, color));
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
        user_id = SharedPreferencesUtils.getInstance(getContext()).getInt(Constant.USER_ID, 0);
        if (CourseIntroductionListener != null) {
            course_packageId = CourseIntroductionListener.introducationGetCoursePackageId();
            loadCourseInfo(course_packageId, user_id);
        }
    }

    /**
     * Description:CourseIntroductionFragment
     * Time:2019-4-3   下午 3:40
     * Detail:TODO 获取课程简介信息
     */
    private void loadCourseInfo(int coursePackageId, int userId) {
        String id;
        if (userId == 0) {
            id = "";
        } else {
            id = String.valueOf(userId);
        }
        String courseSource = getCourseSource();
        String url = "";
        String idName = "";
        if (TextUtils.equals(courseSource, Constant.WATCH_EDUCATION_CPE)) {
            //TODO 后续教育课程简介
            url = ApiStores.EDUCATION_COURSE_INTORDUCTION;
            idName = Constant.PACKAGE_ID;
        } else {
            url = ApiStores.COURSE_INTORDUCTION;

            idName = Constant.ID;
        }
        OkGo.<String>post(url)
                .params(idName, coursePackageId)
                .params(Constant.USER_ID, id)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (loadinglayout != null) {
                            loadinglayout.showError();
                        }
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        loadinglayout.showLoading();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (!TextUtils.equals(body, "")) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(body);
                                int code = jsonObject.optInt(Constant.CODE);
                                if (code == 200) {
                                    Gson gson = new Gson();
                                    CourseIntroductionBean courseIntroductionBean = gson.fromJson(response.body(), CourseIntroductionBean.class);
                                    getCourseInfo(courseIntroductionBean);
                                } else {
                                    if (loadinglayout != null) {
                                        loadinglayout.showEmpty();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (loadinglayout != null) {
                                loadinglayout.showEmpty();
                            }
                        }
                    }
                });
    }

    /**
     * yuan
     */
    private String getCourseSource() {
        String string = "";
        if (CourseIntroductionListener != null) {
            string = CourseIntroductionListener.introducationGetCourse_Source();
        } else if (getActivity() != null) {
            FragmentActivity activity = getActivity();
            if (activity instanceof VideoPlayPageActivity) {
                VideoPlayPageActivity videoPlayPageActivity = (VideoPlayPageActivity) getActivity();
                if (videoPlayPageActivity.isDestroyed() || videoPlayPageActivity.isFinishing()) {

                } else {
                    string = videoPlayPageActivity.introducationGetCourse_Source();
                }
            }
        }
        return string;
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
            List<CourseIntroductionBean.DataBean.TeacehrListBean> teacehrList = data.getTeacehr_list();
            teacehrListBeanList.clear();
            teacehrListBeanList.addAll(teacehrList);
            if (courseTeacherAdapter == null) {
                courseTeacherAdapter = new CourseTeacherAdapter(teacehrListBeanList, getContext());
                recyclerviewTeacher.setAdapter(courseTeacherAdapter);
            } else {
                courseTeacherAdapter.notifyDataSetChanged();
            }
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
            String joinNum = data.getJoin_num();//参加人数
            String studyDays = data.getStudy_days();//课时
            String teacherName = data.getTeacher_name();//讲师名字
            String isPurchase = data.getIs_purchase();//课程是否购买
            String appImg = data.getApp_img();//封面图
            String userstatus = data.getUserstatus();//后续教育是否购买
            String cpeIntegral = data.getCpe_integral();//积分
            //TODO 课程购买状态
            String courseSource = getCourseSource();
            if (TextUtils.equals(courseSource, Constant.WATCH_EDUCATION_CPE)) {
                // 后续教育
                courseTime.setVisibility(View.GONE);
                courseIntegral.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(userstatus)) {
                    if (CourseIntroductionListener != null) {
                        CourseIntroductionListener.introducationSetCourseBuyState(2);
                    }
                } else {
                    if (CourseIntroductionListener != null) {
                        CourseIntroductionListener.introducationSetCourseBuyState(Integer.parseInt(userstatus));
                    }
                }
            } else {
                //一般般啦
                courseTime.setVisibility(View.VISIBLE);
                courseIntegral.setVisibility(View.GONE);
                if (TextUtils.isEmpty(isPurchase)) {
                    if (CourseIntroductionListener != null) {
                        CourseIntroductionListener.introducationSetCourseBuyState(2);
                    }
                } else {
                    if (CourseIntroductionListener != null) {
                        CourseIntroductionListener.introducationSetCourseBuyState(Integer.parseInt(isPurchase));
                    }
                }
            }
            //TODO 课程购买价格
            if (CourseIntroductionListener != null) {
                CourseIntroductionListener.introducationSetCourse_PackagePrice(price);
            }
            //TODO 设置课程封面
            if (CourseIntroductionListener != null) {
                CourseIntroductionListener.introducationSetCourse_Cover(appImg);
            }
            //TODO 课程简介主要内容(本来是一个H5,人家就换成一张图了
            webView.loadUrl(briefImg);
            if (!TextUtils.isEmpty(name)) {
                courseName.setText(name);
            }
            if (!TextUtils.isEmpty(description)) {
                courseDetail.setText(description);
            }
            //***元钱,块钱
            if (!TextUtils.isEmpty(price)) {
                coursePrice.setText(String.format("%s%s", getResources().getString(R.string.RMB), price));
            }
            //***老师
            if (!TextUtils.isEmpty(teacherName)) {
                courseTeacher.setText((getResources().getString(R.string.teacher, teacherName)));
            }
            //***人参加
            courseCount.setText(getResources().getString(R.string.people, joinNum));
            //***天有效
            courseTime.setText(getResources().getString(R.string.orderForm_endtime2, studyDays));
            //***积分
            courseIntegral.setText(getResources().getString(R.string.event_point, cpeIntegral));
            if (loadinglayout != null) {
                loadinglayout.showContent();
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
    }

    /**
     * Description:WebActivity
     * Time:2019-3-22   下午 2:56
     * Detail:TODO 参考考拉的配置
     */
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
        webSetting.setAppCachePath(Objects.requireNonNull(getActivity()).getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(Objects.requireNonNull(getActivity()).getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(getActivity().getDir("geolocation", 0).getPath());
        //允许WebView使用File协议
        webSetting.setAllowFileAccess(true);
        //不保存密码
        webSetting.setSavePassword(false);
        //设置UA
        webSetting.setUserAgentString(webSetting.getUserAgentString() + " youcaiApp/" + AppUtils.getAppVersionName(Objects.requireNonNull(getActivity())));
        //自动加载图片
        webSetting.setLoadsImagesAutomatically(true);
    }

    private void teacherDialog(CourseIntroductionBean.DataBean.TeacehrListBean teacehrListBean) {
        teacherDialog = new Dialog(getContext(), R.style.TeacherDialog);
        View contentView = LayoutInflater.from(Objects.requireNonNull(getContext())).inflate(R.layout.dialog_teacher_detail, null);
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

        String teacherTitle = teacehrListBean.getTeacher_title();
        String pictrue = teacehrListBean.getPictrue();
        String longevity = teacehrListBean.getLongevity();
        String teacherName = teacehrListBean.getTeacher_name();

        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.image_loaderror);
        GlideUtils.load(getContext(), pictrue, mIconTeacher, requestOptions);
        mtitleTeacher.setText(teacherTitle);
        mDetailTeacher.setText(longevity);
        mNameTeacher.setText(teacherName);
    }
}


