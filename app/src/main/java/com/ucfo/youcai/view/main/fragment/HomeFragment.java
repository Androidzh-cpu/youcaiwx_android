package com.ucfo.youcai.view.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.ucfo.youcai.R;
import com.ucfo.youcai.adapter.home.HomeCourseRecommendAdapter;
import com.ucfo.youcai.adapter.home.HomeLiveAdapter;
import com.ucfo.youcai.adapter.home.HomeNewsAdapter;
import com.ucfo.youcai.base.BaseFragment;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.home.HomeBean;
import com.ucfo.youcai.presenter.presenterImpl.home.HomePresenter;
import com.ucfo.youcai.presenter.view.home.IHomeView;
import com.ucfo.youcai.utils.LogUtils;
import com.ucfo.youcai.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcai.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcai.utils.glideutils.GlideRoundTransform;
import com.ucfo.youcai.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcai.utils.systemutils.AppUtils;
import com.ucfo.youcai.utils.systemutils.DensityUtil;
import com.ucfo.youcai.utils.systemutils.StatusBarUtil;
import com.ucfo.youcai.utils.toastutils.ToastUtil;
import com.ucfo.youcai.view.course.CourseListActivity;
import com.ucfo.youcai.view.course.player.VideoPlayPageActivity;
import com.ucfo.youcai.view.home.ScanActivity;
import com.ucfo.youcai.view.learncenter.AddLearningPlanActivity;
import com.ucfo.youcai.view.learncenter.LoadPdfActivity;
import com.ucfo.youcai.view.login.LoginActivity;
import com.ucfo.youcai.view.main.activity.MainActivity;
import com.ucfo.youcai.view.main.activity.WebActivity;
import com.ucfo.youcai.widget.ZoomOutPageTransformer;
import com.ucfo.youcai.widget.shimmer.ShimmerRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author:AND
 * Time: 2019-3-12.  上午 10:17
 * Email:2911743255@qq.com
 * ClassName: HomeFragment
 * Package: com.ucfo.youcai.view.main.fragment
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

    /**
     * Description:HomeFragment
     * Time:2019-3-28   上午 11:23
     * Detail:快速置顶
     */
    public void refreshToTop() {
        ToastUtil.showCenterShortText(context, "你好");
        scrollView.fling(0);
        scrollView.smoothScrollTo(0, 0);
    }

    public static HomeFragment newInstance(String content, String tab) {
        HomeFragment newFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        bundle.putString("tab", tab);
        newFragment.setArguments(bundle);
        return newFragment;
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

        //注册homepresenter
        homePresenter = new HomePresenter(this);
        //请求首页接口
        homePresenter.getHomeData("home");

        layoutManager();

        bannerIndex.setOnBannerListener(this);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                homePresenter.getHomeData("home");
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                homePresenter.getHomeData("home");
            }
        });

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int titleMeasuredWidth = AppUtils.getViewWidth(titlebarTitle);

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = width / 2 - titleMeasuredWidth / 2 - DensityUtil.dip2px(context, 21);

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    titlebarTitle.setTranslationX(mScrollY - mOffset);
                }
                lastScrollY = scrollY;
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
            switch (view.getId()) {
                case R.id.titlebar_scan://TODO 扫描
                    startActivity(new Intent(context, ScanActivity.class));
                    break;
                case R.id.titlebar_message://TODO 消息
                    break;
                case R.id.icon_live://TODO 直播
                    break;
                case R.id.icon_course://TODO 课程
                    startActivity(new Intent(getActivity(), CourseListActivity.class));
                    break;
                case R.id.icon_face://TODO 面授
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    break;
                case R.id.icon_active://TODO 活动
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.TITLE, "这是个讲义");
                    bundle.putString(Constant.URL, "http://www.youcaiwx.com/Public/Uploads/newtopicpics/2018-11-28/5bfe567be3c3b.pdf");
                    startActivity(LoadPdfActivity.class, bundle);
                    break;
                case R.id.icon_news://TODO 资讯
                    startActivity(AddLearningPlanActivity.class, null);
                    break;
                case R.id.check_more_course://TODO 查看更多课程
                    startActivity(new Intent(getActivity(), CourseListActivity.class));
                    break;
                case R.id.check_more_news://TODO 查看更多资讯
                    break;
            }
        }
    }

    //TODO 顶部banner的点击事件
    @Override
    public void OnBannerClick(int position) {
        String jump_href = bannerList.get(position).getJump_href();
        Intent intent = new Intent(getActivity(), WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.WEB_URL, jump_href);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void getHomeData(HomeBean homeBean) {
        //加载失败,没网状态，走缓存
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
                Glide.with(context).load(data.getImage_href()).transform(new CenterCrop(context), new GlideRoundTransform(context, 4))
                        .dontAnimate()
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.mipmap.banner_default)
                        .placeholder(R.mipmap.banner_default)
                        .into(imageView);
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
        recyclerviewNews.addItemDecoration(new SpacesItemDecoration(0, topBottom, Color.TRANSPARENT));
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
                String jumphref = informationBean.getJumphref();
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
