package com.ucfo.youcaiwx.view.main.fragment;

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
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
import com.ucfo.youcaiwx.view.course.CourseListActivity;
import com.ucfo.youcaiwx.view.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.view.home.InformationActivity;
import com.ucfo.youcaiwx.view.home.MessageCenterActivity;
import com.ucfo.youcaiwx.view.home.ScanActivity;
import com.ucfo.youcaiwx.view.main.activity.MainActivity;
import com.ucfo.youcaiwx.view.main.activity.WebActivity;
import com.ucfo.youcaiwx.widget.ZoomOutPageTransformer;
import com.ucfo.youcaiwx.widget.shimmer.ShimmerRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author:AND
 * Time: 2019-3-12.  上午 10:17
 * Email:2911743255@qq.com
 * ClassName: HomeFragment
 * Description:TODO 首页  - 首页
 * Detail:
 */
public class HomeFragment extends BaseFragment implements OnBannerListener, IHomeView {
    public static final String TAG = "Homefragment";
    @BindView(R.id.titlebar_title)
    TextView titlebarTitle;
    @BindView(R.id.titlebar_scan)
    ImageView titlebarScan;
    @BindView(R.id.titlebar_message)
    ImageView titlebarMessage;
    @BindView(R.id.banner_index)
    Banner bannerIndex;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.hot_filpper)
    ViewFlipper hotFilpper;
    @BindView(R.id.icon_live)
    TextView iconLive;
    @BindView(R.id.icon_course)
    TextView iconCourse;
    @BindView(R.id.icon_face)
    TextView iconFace;
    @BindView(R.id.icon_active)
    TextView iconActive;
    @BindView(R.id.icon_news)
    TextView iconNews;
    @BindView(R.id.recyclerview_live)
    ShimmerRecyclerView recyclerviewLive;
    @BindView(R.id.recyclerview_course)
    ShimmerRecyclerView recyclerviewCourse;
    @BindView(R.id.recyclerview_news)
    ShimmerRecyclerView recyclerviewNews;
    @BindView(R.id.check_more_course)
    TextView checkMoreCourse;
    @BindView(R.id.check_more_news)
    TextView checkMoreNews;
    @BindView(R.id.statusbar_view)
    View statusbarView;
    Unbinder unbinder;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        bannerIndex.stopAutoPlay();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void initView(View view) {
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

        bannerIndex.setOnBannerListener(this);

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
        int width = dm.widthPixels;         // 屏幕宽度（像素）
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
        refreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        refreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        refreshLayout.setEnableAutoLoadMore(false);//是否启用列表惯性滑动到底部时自动加载更多
        refreshLayout.setEnableNestedScroll(true);//是否启用嵌套滚动
        refreshLayout.setEnableOverScrollBounce(true);//是否启用越界回弹

    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_home;
    }

    private long mLastClickTime = 0;
    public static final long TIME_INTERVAL = 2000L;

    @OnClick({R.id.titlebar_scan, R.id.titlebar_message, R.id.icon_live, R.id.icon_course, R.id.icon_face, R.id.icon_active, R.id.icon_news
            , R.id.check_more_course, R.id.check_more_news})
    public void onViewClicked(View view) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastClickTime > TIME_INTERVAL) {
            mLastClickTime = nowTime;
            Bundle bundle = new Bundle();
            switch (view.getId()) {
                case R.id.titlebar_scan://TODO 扫描
                    SoulPermission.getInstance()
                            .checkAndRequestPermission(Manifest.permission.CAMERA, new CheckRequestPermissionListener() {
                                @Override
                                public void onPermissionOk(Permission permission) {
                                    startActivity(new Intent(context, ScanActivity.class));
                                }

                                @Override
                                public void onPermissionDenied(Permission permission) {
                                }
                            });

                    break;
                case R.id.titlebar_message://TODO 消息
                    startActivity(new Intent(context, MessageCenterActivity.class));
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
                    //bundle.putString(Constant.WEB_URL, ApiStores.TEMPORARYNEWS);
                    //bundle.putString(Constant.WEB_TITLE, getResources().getString(R.string.home_news));
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
        String jumpHref = bannerList.get(position).getJump_href();
        Intent intent = new Intent(getActivity(), WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.WEB_URL, jumpHref);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void getHomeData(HomeBean homeBean) {
        if (homeBean == null) {
            String dataJson = sharedPreferencesUtils.getString(Constant.HOME_CACHE, "");
            if (dataJson != null && !dataJson.equals("")) {
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
                    String s = gson.toJson(homeBean);
                    sharedPreferencesUtils.remove(Constant.HOME_CACHE);//清除本地缓存
                    sharedPreferencesUtils.putString(Constant.HOME_CACHE, s);//存储本地缓存
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
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    /**
     * Description:HomeFragment
     * Time:2019/3/14   13:52
     * Detail:TODO banner配置
     */
    private void bannerConfig(List<HomeBean.DataBean.ListpicBean> listpic) {
        if (listpic != null && listpic.size() != 0) {
            bannerIndex.setImages(listpic);//图片地址
        }
        bannerIndex.setPageTransformer(true, new ZoomOutPageTransformer());//设置viewpager的自定义动画
        bannerIndex.setOffscreenPageLimit(2);//预加载数量
        bannerIndex.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                imageView.setPadding(DensityUtil.dip2px(context, 2), 0, DensityUtil.dip2px(context, 2), 0);
                HomeBean.DataBean.ListpicBean data = (HomeBean.DataBean.ListpicBean) path;
                RequestOptions requestOptions = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.mipmap.banner_default)
                        .error(R.mipmap.banner_default)
                        .transform(new RoundedCorners(DensityUtil.dp2px(5)))
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                GlideUtils.load(context, data.getImage_href(), imageView, requestOptions);
            }
        });//图片加载器
        bannerIndex.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//指示器样式
        bannerIndex.setDelayTime(3000);//轮播时间间隔
        bannerIndex.start();

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
            homeLiveAdapter = new HomeLiveAdapter(liveList, context);
        } else {
            homeLiveAdapter.notifyDataSetChanged();
        }
        recyclerviewLive.setItemAnimator(new DefaultItemAnimator());
        recyclerviewLive.setAdapter(homeLiveAdapter);
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
            homeCourseRecommendAdapter = new HomeCourseRecommendAdapter(courseList, context);
        } else {
            homeCourseRecommendAdapter.notifyDataSetChanged();
        }
        recyclerviewCourse.setItemAnimator(new DefaultItemAnimator());
        recyclerviewCourse.setAdapter(homeCourseRecommendAdapter);
        homeCourseRecommendAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                HomeBean.DataBean.CurriculumBean bean = courseList.get(position);
                String course_iamge = bean.getApp_img();//TODO 课程封面
                String coursePackageId = bean.getPackage_id();//TODO  课程包ID(课程包内含多们课程)

                bundle.putString(Constant.COURSE_COVER_IMAGE, course_iamge);//封面
                bundle.putInt(Constant.COURSE_PACKAGE_ID, Integer.parseInt(coursePackageId));//课程包ID
                bundle.putInt(Constant.COURSE_BUY_STATE, 2);//购买状态
                bundle.putString(Constant.COURSE_PRICE, bean.getPrice());//课程包价格
                startActivity(VideoPlayPageActivity.class, bundle);
            }
        });
    }

    private void layoutManager() {
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(context);
        layoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager3.setReverseLayout(false);
        recyclerviewLive.setLayoutManager(layoutManager3);

        int topBottom = DensityUtil.dip2px(context, 10);
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
        } else {
            homeNewsAdapter.notifyDataSetChanged();
        }

        recyclerviewNews.setItemAnimator(new DefaultItemAnimator());
        recyclerviewNews.setAdapter(homeNewsAdapter);
        homeNewsAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                HomeBean.DataBean.InformationBean informationBean = newLists.get(position);
                String title = informationBean.getTitle();
                String jumphref = informationBean.getUrl();
                Intent intent = new Intent(getActivity(), WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.WEB_URL, jumphref);
                bundle.putString(Constant.WEB_TITLE, title);
                intent.putExtras(bundle);
                startActivity(intent);
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
