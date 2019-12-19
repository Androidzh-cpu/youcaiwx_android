package com.ucfo.youcaiwx.module.main.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.callbcak.CheckRequestPermissionListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.home.HomeCourseRecommendAdapter;
import com.ucfo.youcaiwx.adapter.home.HomeLiveAdapter;
import com.ucfo.youcaiwx.adapter.home.HomeNewsAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.home.HomeBean;
import com.ucfo.youcaiwx.module.course.CourseListActivity;
import com.ucfo.youcaiwx.module.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.module.home.InformationActivity;
import com.ucfo.youcaiwx.module.home.MessageCenterActivity;
import com.ucfo.youcaiwx.module.home.ScanActivity;
import com.ucfo.youcaiwx.module.login.LoginActivity;
import com.ucfo.youcaiwx.module.main.activity.MainActivity;
import com.ucfo.youcaiwx.module.main.activity.WebActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.home.HomePresenter;
import com.ucfo.youcaiwx.presenter.view.home.IHomeView;
import com.ucfo.youcaiwx.utils.LogUtils;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.AppUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.utils.systemutils.StatusBarUtil;
import com.ucfo.youcaiwx.widget.ZoomOutPageTransformer;
import com.ucfo.youcaiwx.widget.shimmer.ShimmerRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Author:AND
 * Time: 2019-3-12.  上午 10:17
 * Email:2911743255@qq.com
 * ClassName: HomeFragment
 * Description:TODO 主页- 首页
 */
public class HomeFragment extends BaseFragment implements OnBannerListener, IHomeView, View.OnClickListener {
    public static final String TAG = "Homefragment";

    private TextView titlebarTitle;
    private ImageView titlebarScan;
    private ImageView titlebarMessage;
    private Banner banner;
    private SmartRefreshLayout refreshLayout;
    private LinearLayout linearlayout;
    private NestedScrollView scrollView;
    private ViewFlipper hotFilpper;
    private TextView iconLive;
    private TextView iconCourse;
    private TextView iconFace;
    private TextView iconActive;
    private TextView iconNews;
    private ShimmerRecyclerView recyclerviewLive;
    private ShimmerRecyclerView recyclerviewCourse;
    private ShimmerRecyclerView recyclerviewNews;
    private TextView checkMoreCourse;
    private TextView checkMoreNews;
    private View statusbarView;


    private List<HomeBean.DataBean.ListpicBean> bannerList;
    private List<HomeBean.DataBean.HotspotBean> hotspotBeanList;
    private List<HomeBean.DataBean.BroadcastBean> liveList;
    private List<HomeBean.DataBean.CurriculumBean> courseList;
    private List<HomeBean.DataBean.InformationBean> newLists;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private HomePresenter homePresenter;
    private HomeCourseRecommendAdapter homeCourseRecommendAdapter;
    private HomeNewsAdapter homeNewsAdapter;
    private HomeLiveAdapter homeLiveAdapter;
    private int mOffset = 0;
    private int mScrollY = 0;

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View itemView) {
        statusbarView = (View) itemView.findViewById(R.id.statusbar_view);
        titlebarTitle = (TextView) itemView.findViewById(R.id.titlebar_title);
        titlebarScan = (ImageView) itemView.findViewById(R.id.titlebar_scan);
        titlebarScan.setOnClickListener(this);
        titlebarMessage = (ImageView) itemView.findViewById(R.id.titlebar_message);
        titlebarMessage.setOnClickListener(this);
        banner = (Banner) itemView.findViewById(R.id.banner_index);
        hotFilpper = (ViewFlipper) itemView.findViewById(R.id.hot_filpper);
        iconLive = (TextView) itemView.findViewById(R.id.icon_live);
        iconLive.setOnClickListener(this);
        iconCourse = (TextView) itemView.findViewById(R.id.icon_course);
        iconCourse.setOnClickListener(this);
        iconFace = (TextView) itemView.findViewById(R.id.icon_face);
        iconFace.setOnClickListener(this);
        iconActive = (TextView) itemView.findViewById(R.id.icon_active);
        iconActive.setOnClickListener(this);
        iconNews = (TextView) itemView.findViewById(R.id.icon_news);
        iconNews.setOnClickListener(this);
        recyclerviewLive = (ShimmerRecyclerView) itemView.findViewById(R.id.recyclerview_live);
        recyclerviewCourse = (ShimmerRecyclerView) itemView.findViewById(R.id.recyclerview_course);
        checkMoreCourse = (TextView) itemView.findViewById(R.id.check_more_course);
        checkMoreCourse.setOnClickListener(this);
        recyclerviewNews = (ShimmerRecyclerView) itemView.findViewById(R.id.recyclerview_news);
        checkMoreNews = (TextView) itemView.findViewById(R.id.check_more_news);
        checkMoreNews.setOnClickListener(this);
        scrollView = (NestedScrollView) itemView.findViewById(R.id.scrollView);
        refreshLayout = (SmartRefreshLayout) itemView.findViewById(R.id.refreshlayout);
        linearlayout = (LinearLayout) itemView.findViewById(R.id.linearlayout);


        context = (MainActivity) getActivity();
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        recyclerviewLive.setNestedScrollingEnabled(false);
        recyclerviewCourse.setNestedScrollingEnabled(false);
        recyclerviewNews.setNestedScrollingEnabled(false);
        recyclerviewCourse.setFocusable(false);
        recyclerviewNews.setFocusable(false);
        recyclerviewLive.setFocusable(false);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) statusbarView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = StatusBarUtil.getStatusBarHeight(getActivity());
        statusbarView.setLayoutParams(layoutParams);
    }

    @Override
    protected void initData() {
        bannerList = new ArrayList<>();
        hotspotBeanList = new ArrayList<>();
        liveList = new ArrayList<>();
        courseList = new ArrayList<>();
        newLists = new ArrayList<>();

        homePresenter = new HomePresenter(this);
        homePresenter.getHomeData("home");

        layoutManager();

        banner.setOnBannerListener(this);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                homePresenter.getHomeData("home");
            }
        });
        WindowManager wm = (WindowManager) Objects.requireNonNull(getActivity()).getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(dm);
        }
        // 屏幕宽度（像素）
        int width = dm.widthPixels;
        int titleMeasuredWidth = AppUtils.getViewWidth(titlebarTitle);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastscrolly = 0;
            private int h = width / 2 - titleMeasuredWidth / 2 - DensityUtil.dip2px(context, 21);

            @Override
            public void onScrollChange(NestedScrollView v, int scrollx, int scrolly, int oldscrollx, int oldscrolly) {
                if (lastscrolly < h) {
                    scrolly = Math.min(h, scrolly);
                    mScrollY = scrolly > h ? h : scrolly;
                    titlebarTitle.setTranslationX(mScrollY - mOffset);
                }
                lastscrolly = scrolly;
            }
        });
        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            }

            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                mOffset = offset / 2;
            }
        });
        refreshLayout.setDisableContentWhenRefresh(true);
        refreshLayout.setDisableContentWhenLoading(true);
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setEnableNestedScroll(true);
        refreshLayout.setEnableOverScrollBounce(true);
    }

    private long mLastClickTime = 0;
    public static final long TIME_INTERVAL = 1000L;

    @Override
    public void onClick(View view) {
        boolean loginStatus = sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false);
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastClickTime > TIME_INTERVAL) {
            mLastClickTime = nowTime;

            Bundle bundle = new Bundle();
            switch (view.getId()) {
                case R.id.titlebar_scan://TODO 二维码
                    if (loginStatus) {
                        SoulPermission.getInstance()
                                .checkAndRequestPermission(Manifest.permission.CAMERA, new CheckRequestPermissionListener() {
                                    @Override
                                    public void onPermissionOk(Permission permission) {
                                        startActivity(new Intent(getActivity(), ScanActivity.class));
                                    }

                                    @Override
                                    public void onPermissionDenied(Permission permission) {
                                    }
                                });
                    } else {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                    break;
                case R.id.titlebar_message://TODO 消息
                    if (loginStatus) {
                        startActivity(new Intent(getActivity(), MessageCenterActivity.class));
                    } else {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                    break;
                case R.id.icon_live://TODO 直播
                    bundle.putString(Constant.WEB_URL, ApiStores.TEMPORARYLIVE);
                    bundle.putString(Constant.WEB_TITLE, getResources().getString(R.string.home_live));
                    startActivity(WebActivity.class, bundle);
                    break;
                case R.id.icon_course://TODO 课程
                    startActivity(CourseListActivity.class, null);
                    break;
                case R.id.icon_face://TODO 面授
                    bundle.putString(Constant.WEB_URL, ApiStores.TEMPORARFACE);
                    bundle.putString(Constant.WEB_TITLE, getResources().getString(R.string.home_face));
                    startActivity(WebActivity.class, bundle);
                    break;
                case R.id.icon_active://TODO 活动
                    bundle.putString(Constant.WEB_URL, ApiStores.TEMPORARACTIVE);
                    bundle.putString(Constant.WEB_TITLE, getResources().getString(R.string.home_active));
                    startActivity(WebActivity.class, bundle);
                    break;
                case R.id.check_more_course://TODO 查看更多课程
                    startActivity(new Intent(getActivity(), CourseListActivity.class));
                    break;
                case R.id.icon_news://TODO 资讯
                case R.id.check_more_news://TODO 查看更多资讯
                    startActivity(InformationActivity.class, null);
                    break;
                default:
                    break;
            }
        }
    }

    //TODO 顶部banner的点击事件
    @Override
    public void OnBannerClick(int position) {
        if (bannerList != null && bannerList.size() > 0) {
            HomeBean.DataBean.ListpicBean bean = bannerList.get(position);
            String jumpHref = bean.getJump_href();
            String title = bean.getTitle();

            Bundle bundle = new Bundle();
            bundle.putString(Constant.WEB_URL, jumpHref);
            bundle.putString(Constant.WEB_TITLE, title);
            startActivity(WebActivity.class, bundle);
        }
    }

    @Override
    public void getHomeData(HomeBean homeBean) {
        if (homeBean == null) {
            String dataJson = sharedPreferencesUtils.getString(Constant.HOME_CACHE, "");
            if (dataJson != null && !TextUtils.equals(dataJson, "")) {
                Gson gson = new Gson();
                HomeBean cacheBean = gson.fromJson(dataJson, HomeBean.class);
                if (cacheBean != null) {
                    homeBean = cacheBean;
                }
            }
        }
        if (homeBean != null) {
            HomeBean.DataBean data = homeBean.getData();
            if (data != null) {
                try {
                    Gson gson = new Gson();
                    String json = gson.toJson(homeBean);
                    sharedPreferencesUtils.remove(Constant.HOME_CACHE);
                    sharedPreferencesUtils.putString(Constant.HOME_CACHE, json);
                } catch (Throwable throwable) {
                    String message = throwable.getMessage();
                    LogUtils.e(message);
                }
                bannerList.clear();
                hotspotBeanList.clear();
                liveList.clear();
                courseList.clear();
                newLists.clear();

                bannerList.addAll(data.getListpic());
                hotspotBeanList.addAll(data.getHotspot());
                liveList.addAll(data.getBroadcast());
                courseList.addAll(data.getCurriculum());
                newLists.addAll(data.getInformation());

                bannerConfig(bannerList);//开启banner轮播
                startFilpper(hotspotBeanList);//开启消息轮训
                initLiveAdapter(liveList);//直播推荐
                initCourseAdapter(courseList);//课程推荐
                initNewsAdapter(newLists);

                checkMoreCourse.setVisibility(View.VISIBLE);
                checkMoreNews.setVisibility(View.VISIBLE);

                FragmentActivity activity = getActivity();
                if (activity instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) activity;
                    mainActivity.initNewGuide();
                }
            }
        } else {
            bannerConfig(bannerList);
            checkMoreCourse.setVisibility(View.GONE);
            checkMoreNews.setVisibility(View.GONE);
        }
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
        }
    }

    /**
     * Description:HomeFragment
     * Time:2019/3/14   13:52
     * Detail:TODO banner配置
     * 豪华房时代峻峰拉萨的
     */
    private void bannerConfig(List<HomeBean.DataBean.ListpicBean> listpic) {
        if (listpic != null && listpic.size() > 0) {
            banner.setImages(listpic);//图片地址
            //设置viewpager的自定义动画
            banner.setPageTransformer(true, new ZoomOutPageTransformer());
            banner.setOffscreenPageLimit(listpic.size());
            banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    imageView.setPadding(DensityUtil.dip2px(context, 2), 0, DensityUtil.dip2px(context, 2), 0);
                    HomeBean.DataBean.ListpicBean data = (HomeBean.DataBean.ListpicBean) path;
                    RequestOptions requestOptions = new RequestOptions()
                            .placeholder(R.mipmap.icon_default)
                            .error(R.mipmap.image_loaderror)
                            .transform(new CenterCrop(), new RoundedCorners(DensityUtil.dp2px(5)));
                    GlideUtils.load(context, data.getImage_href(), imageView, requestOptions);
                }
            });
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            banner.setIndicatorGravity(Gravity.CENTER_HORIZONTAL);
            banner.setViewPagerIsScroll(true);
            banner.setDelayTime(3000);
            banner.start();
        }
    }

    //开启消息轮训
    private void startFilpper(List<HomeBean.DataBean.HotspotBean> hotspot) {
        for (int i = 0; i < hotspot.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_homt_hotpoint, null);
            TextView textView = view.findViewById(R.id.hot_filpper_text);
            textView.setText(hotspot.get(i).getTitle());
            hotFilpper.addView(view);
        }
        hotFilpper.setFlipInterval(4000);//停留时间
        hotFilpper.setAutoStart(false);//禁止自动轮训
        if (hotspot.size() <= 1) {
            hotFilpper.stopFlipping();
        } else {
            hotFilpper.startFlipping();
        }
        hotFilpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int displayedChild = hotFilpper.getDisplayedChild();//获取到子view的位置==索引值
                View currentView = hotFilpper.getCurrentView();
                String s = hotspot.get(displayedChild).getTitle();
                String jumphref = hotspot.get(displayedChild).getJumphref();
                Intent intent = new Intent(getActivity(), WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.WEB_URL, jumphref);
                bundle.putString(Constant.WEB_TITLE, s);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /**
     * Description:HomeFragment
     * Time:2019/3/14   15:28
     * Detail: 直播推荐
     */
    private void initLiveAdapter(List<HomeBean.DataBean.BroadcastBean> liveList) {
        if (homeLiveAdapter == null) {
            homeLiveAdapter = new HomeLiveAdapter(liveList, getActivity());
            recyclerviewLive.setAdapter(homeLiveAdapter);
        } else {
            homeLiveAdapter.notifyChange(liveList);
        }
        recyclerviewLive.setItemAnimator(new DefaultItemAnimator());
        homeLiveAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.WEB_TITLE, liveList.get(position).getTitle());
                bundle.putString(Constant.WEB_URL, liveList.get(position).getJumplink());
                startActivity(WebActivity.class, bundle);
            }
        });
    }

    /**
     * Description:HomeFragment
     * Time:2019/3/14   17:16
     * Detail: 课程推荐
     */
    private void initCourseAdapter(List<HomeBean.DataBean.CurriculumBean> courseList) {
        //设置适配器
        if (homeCourseRecommendAdapter == null) {
            homeCourseRecommendAdapter = new HomeCourseRecommendAdapter(courseList, getActivity());
            recyclerviewCourse.setAdapter(homeCourseRecommendAdapter);
        } else {
            homeCourseRecommendAdapter.notifyChange(courseList);
        }
        recyclerviewCourse.setItemAnimator(new DefaultItemAnimator());
        homeCourseRecommendAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                HomeBean.DataBean.CurriculumBean bean = courseList.get(position);
                if (!TextUtils.isEmpty(bean.getPackage_id())) {
                    String appImg = bean.getApp_img();//TODO 课程封面
                    String coursePackageId = bean.getPackage_id();//TODO  课程包ID(课程包内含多们课程)
                    String isPurchase = bean.getIs_purchase();
                    int courseBuyState = 2;
                    if (!TextUtils.isEmpty(isPurchase)) {
                        courseBuyState = Integer.parseInt(isPurchase);
                    }
                    bundle.putString(Constant.COURSE_COVER_IMAGE, appImg);//封面
                    bundle.putInt(Constant.COURSE_PACKAGE_ID, Integer.parseInt(coursePackageId));//课程包ID
                    bundle.putInt(Constant.COURSE_BUY_STATE, courseBuyState);//购买状态
                    bundle.putString(Constant.COURSE_PRICE, bean.getPrice());//课程包价格
                    startActivity(VideoPlayPageActivity.class, bundle);
                }
            }
        });
    }

    private void layoutManager() {
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(context);
        layoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager3.setReverseLayout(false);
        recyclerviewLive.setLayoutManager(layoutManager3);

        int topBottom = DensityUtil.dip2px(getActivity(), 10);
        //recyclerviewNews.addItemDecoration(new SpacesItemDecoration(0, topBottom, Color.TRANSPARENT));
        recyclerviewCourse.addItemDecoration(new SpacesItemDecoration(0, topBottom, Color.TRANSPARENT));

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerviewCourse.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(context);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerviewNews.setLayoutManager(layoutManager2);


    }

    /**
     * Description:HomeFragment
     * Time:2019/3/14   19:08
     * Detail: 资讯推荐
     */
    private void initNewsAdapter(List<HomeBean.DataBean.InformationBean> newLists) {
        //设置适配器
        if (homeNewsAdapter == null) {
            homeNewsAdapter = new HomeNewsAdapter(newLists, context);
            recyclerviewNews.setAdapter(homeNewsAdapter);
        } else {
            homeNewsAdapter.notifyChange(newLists);
        }

        recyclerviewNews.setItemAnimator(new DefaultItemAnimator());
        homeNewsAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                HomeBean.DataBean.InformationBean informationBean = newLists.get(position);
                String title = informationBean.getTitle();
                String jumphref = informationBean.getUrl();
                Bundle bundle = new Bundle();
                bundle.putString(Constant.WEB_URL, jumphref);
                bundle.putString(Constant.WEB_TITLE, title);
                startActivity(WebActivity.class, bundle);
            }
        });
    }

    //开始加载
    @Override
    public void showLoading() {
        setProcessLoading(null, true);
    }

    //结束加载
    @Override
    public void showLoadingFinish() {
        dismissPorcess();
    }

    //请求失败
    @Override
    public void showError() {
    }

}
