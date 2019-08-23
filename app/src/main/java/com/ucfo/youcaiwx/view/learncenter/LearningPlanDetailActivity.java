package com.ucfo.youcaiwx.view.learncenter;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.learncenter.LearnPlanDetailCourseAdapter;
import com.ucfo.youcaiwx.adapter.learncenter.LearnPlanDetailDateAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.learncenter.LearnPlanDetailBean;
import com.ucfo.youcaiwx.entity.learncenter.LearnPlanDetailVideoBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.learncenter.LearnPlanDetailPresenter;
import com.ucfo.youcaiwx.presenter.view.learncenter.ILearnPlanDetailView;
import com.ucfo.youcaiwx.utils.baseadapter.CenterLayoutManager;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.baseadapter.OnItemClickListener;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.view.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.view.questionbank.activity.TESTMODEActivity;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.dialog.ExitPlanDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-17 上午 10:31
 * FileName: LearningPlanDetailActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 学习计划详情
 */
public class LearningPlanDetailActivity extends BaseActivity implements ILearnPlanDetailView {
    TextView titlebarMidtitle;
    TextView titlebarRighttitle;
    Toolbar titlebarToolbar;
    LoadingLayout loadinglayout;
    TextView textMonth;
    TextView englishMonth;
    RecyclerView calendarListview;
    ImageView cover;
    View showline;
    RecyclerView listView;
    LinearLayout holderLinearlayout;

    private Bundle bundle;
    private int user_id, course_id, plan_id, type;
    private String overFlag;
    private LearnPlanDetailPresenter planDetailPresenter;
    private LearningPlanDetailActivity context;
    private List<LearnPlanDetailBean.DataBean.DateBean> list;
    private List<LearnPlanDetailVideoBean.DataBean.VideoBean> videoList;
    private LearnPlanDetailDateAdapter dateAdapter;
    private LearnPlanDetailCourseAdapter learnPlanDetailCourseAdapter;
    private CenterLayoutManager gridLayoutManager;
    private int currentDay = 0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.with(getApplicationContext()).pauseRequests();
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_leaning_plan_detail;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(titlebarToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        showline.setVisibility(View.GONE);
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.icon_more);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        titlebarRighttitle.setCompoundDrawables(drawable, null, null, null);
        titlebarRighttitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ExitPlanDialog(LearningPlanDetailActivity.this).builder()
                        .setAssistantMsg(getResources().getString(R.string.learncenter_exitMessage))
                        .setMsg(getResources().getString(R.string.learncenter_exitTitle))
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .setNegativeButton(getResources().getString(R.string.thinkAgain), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.exit), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setProcessLoading(null, true);
                                planDetailPresenter.exitStudyPlan(user_id, plan_id, type);
                            }
                        }).show();
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        titlebarMidtitle = (TextView) findViewById(R.id.titlebar_midtitle);
        titlebarRighttitle = (TextView) findViewById(R.id.titlebar_righttitle);
        titlebarToolbar = (Toolbar) findViewById(R.id.titlebar_toolbar);
        showline = (View) findViewById(R.id.showline);
        cover = (ImageView) findViewById(R.id.cover);
        textMonth = (TextView) findViewById(R.id.text_month);
        englishMonth = (TextView) findViewById(R.id.english_month);
        holderLinearlayout = (LinearLayout) findViewById(R.id.holder_linearlayout);
        calendarListview = (RecyclerView) findViewById(R.id.calendar_listview);
        listView = (RecyclerView) findViewById(R.id.listView);
        loadinglayout = (LoadingLayout) findViewById(R.id.loadinglayout);

        context = this;
        user_id = SharedPreferencesUtils.getInstance(context).getInt(Constant.USER_ID, 0);
        planDetailPresenter = new LearnPlanDetailPresenter(this);

        gridLayoutManager = new CenterLayoutManager(context);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        calendarListview.setLayoutManager(gridLayoutManager);
        calendarListview.setNestedScrollingEnabled(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(false);
        listView.setLayoutManager(layoutManager);
        listView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<>();
        videoList = new ArrayList<>();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            course_id = bundle.getInt(Constant.COURSE_ID, 0);//课程ID
            plan_id = bundle.getInt(Constant.ID, 0);//学习计划ID
            type = bundle.getInt(Constant.TYPE, 0);//TODO 1:0元体验  2:正课
            overFlag = bundle.getString(Constant.OVER);//TODO 1:0元体验  2:正课
            String title = bundle.getString(Constant.TITLE, getResources().getString(R.string.default_title));
            titlebarMidtitle.setText(title);

            planDetailPresenter.getLearnPlanDetailList(user_id, course_id, plan_id, type);
        } else {
            loadinglayout.showEmpty();
        }
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planDetailPresenter.getLearnPlanDetailList(user_id, course_id, plan_id, type);
            }
        });
        //计划已过期
        if (TextUtils.equals(overFlag, String.valueOf(2))) {
            new ExitPlanDialog(LearningPlanDetailActivity.this).builder()
                    .setAssistantMsg(getResources().getString(R.string.learncenter_exit2Message))
                    .setMsg(getResources().getString(R.string.learncenter_exit2Title))
                    .setCancelable(false)
                    .setCanceledOnTouchOutside(false)
                    .setNegativeButtonVisibility(false)
                    .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setProcessLoading(null, true);
                            planDetailPresenter.exitStudyPlan(user_id, plan_id, type);
                        }
                    }).show();

        }
    }

    @Override
    public void exitPlanResult(int code) {
        if (code == 1) {
            finish();
        } else {
            ToastUtil.showBottomShortText(this, getResources().getString(R.string.operation_Error));
        }
    }

    @Override
    public void getPlanDetailList(LearnPlanDetailBean result) {
        if (result != null) {
            if (result.getData() != null) {
                String advert = result.getData().getAdvert();
                if (!this.isFinishing()) {
                    Glide.with(this).load(advert).asBitmap().skipMemoryCache(true).placeholder(R.mipmap.banner_default)
                            .error(R.mipmap.image_loaderror).skipMemoryCache(true).into(cover);
                }
                if (result.getData().getDate() != null && result.getData().getDate().size() > 0) {
                    List<LearnPlanDetailBean.DataBean.DateBean> dateBeanList = result.getData().getDate();
                    list.clear();
                    list.addAll(dateBeanList);
                    holderLinearlayout.setVisibility(View.VISIBLE);
                    initAdapter();
                } else {
                    holderLinearlayout.setVisibility(View.GONE);
                }
            } else {
                holderLinearlayout.setVisibility(View.GONE);
            }
        } else {
            holderLinearlayout.setVisibility(View.GONE);
        }
    }

    /**
     * 日期计划
     */
    private void initAdapter() {
        if (dateAdapter == null) {
            dateAdapter = new LearnPlanDetailDateAdapter(context, list);
        } else {
            dateAdapter.notifyDataSetChanged();
        }
        calendarListview.setAdapter(dateAdapter);
        dateAdapter.setItemClick(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //滑动至当天计划
                gridLayoutManager.smoothScrollToPosition(calendarListview, new RecyclerView.State(), position);
                LearnPlanDetailBean.DataBean.DateBean bean = list.get(position);
                //设置日期
                englishMonth.setText(bean.getYmonth());
                textMonth.setText(String.valueOf(bean.getMonth()));
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    if (i == position) {
                        list.get(i).setChecked(true);
                    } else {
                        list.get(i).setChecked(false);
                    }
                }
                dateAdapter.notifyDataSetChanged();
                currentDay = bean.getDays();
                //是否是休息日
                if (bean.getIs_rest() == 1) {
                    //TODO 获取计划详情  1工作日,2休息日
                    planDetailPresenter.getLearnPlanDetailVideoList(user_id, course_id, plan_id, type, bean.getSameday(), currentDay);
                } else {
                    loadinglayout.showEmpty();
                }
            }
        });
        boolean flag = true;
        //获取当天计划详情
        int size = list.size();
        for (int i = 0; i < size; i++) {
            int sameday = list.get(i).getSameday();
            if (sameday == 1) {
                //是否是当天计划
                gridLayoutManager.smoothScrollToPosition(calendarListview, new RecyclerView.State(), i);
                list.get(i).setChecked(true);
                dateAdapter.notifyDataSetChanged();
                //设置日期
                LearnPlanDetailBean.DataBean.DateBean bean = list.get(i);
                englishMonth.setText(bean.getYmonth());
                textMonth.setText(String.valueOf(bean.getMonth()));
                //当天日期
                currentDay = list.get(i).getDays();
                //是否是休息日
                if (bean.getIs_rest() == 1) {
                    //TODO 获取计划详情
                    planDetailPresenter.getLearnPlanDetailVideoList(user_id, course_id, plan_id, type, bean.getSameday(), currentDay);
                } else {
                    loadinglayout.showEmpty();
                }

                flag = false;
                break;
            }
        }
        if (flag) {
            list.get(0).setChecked(true);
            dateAdapter.notifyDataSetChanged();
            int sameday = list.get(0).getSameday();
            currentDay = list.get(0).getDays();
            planDetailPresenter.getLearnPlanDetailVideoList(user_id, course_id, plan_id, type, sameday, currentDay);
        }
    }

    @Override
    public void getPlanDetailVideoList(LearnPlanDetailVideoBean result) {
        if (result != null) {
            if (result.getData() != null) {
                if (result.getData().getVideo().size() > 0) {
                    List<LearnPlanDetailVideoBean.DataBean.VideoBean> video = result.getData().getVideo();
                    videoList.clear();
                    videoList.addAll(video);
                    initVideoAdapter();
                } else {
                    loadinglayout.showEmpty();
                }
            } else {
                loadinglayout.showEmpty();
            }
        } else {
            loadinglayout.showEmpty();
        }
    }

    //TODO 视频列表适配器
    private void initVideoAdapter() {
        if (videoList.size() > 0) {
            if (learnPlanDetailCourseAdapter == null) {
                learnPlanDetailCourseAdapter = new LearnPlanDetailCourseAdapter(context, videoList);
            } else {
                learnPlanDetailCourseAdapter.notifyDataSetChanged();
            }
            listView.setAdapter(learnPlanDetailCourseAdapter);
            learnPlanDetailCourseAdapter.setItemClick(new LearnPlanDetailCourseAdapter.OnItemClickListener() {
                @Override
                public void setOnNoteClick(int position) {
                    String sameday = videoList.get(position).getSameday();
                    if (TextUtils.equals(sameday, String.valueOf(1))) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.TITLE, videoList.get(position).getVideo_name());
                        bundle.putString(Constant.URL, videoList.get(position).getHandouts());
                        startActivity(LoadPdfActivity.class, bundle);
                    }
                }

                @Override
                public void setOnExerciseClick(int position) {
                    LearnPlanDetailVideoBean.DataBean.VideoBean videoBean = videoList.get(position);
                    String sameday = videoBean.getSameday();
                    if (TextUtils.equals(sameday, String.valueOf(1))) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constant.COURSE_ID, course_id);
                        bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_E);
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_14);
                        bundle.putString(Constant.KNOW_ID, videoBean.getKnow_id());
                        bundle.putString(Constant.VIDEO_NAME, videoBean.getVideo_name());
                        startActivity(TESTMODEActivity.class, bundle);
                    }
                }
            });
            learnPlanDetailCourseAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String sameday = videoList.get(position).getSameday();
                    if (TextUtils.equals(sameday,String.valueOf(1))) {
                        LearnPlanDetailVideoBean.DataBean.VideoBean videoBean = videoList.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constant.COURSE_PACKAGE_ID, videoBean.getPackage_id());//包
                        bundle.putInt(Constant.COURSE_BUY_STATE, 1);
                        bundle.putString(Constant.COURSE_SOURCE, Constant.WATCH_LEARNPLAN);
                        bundle.putInt(Constant.COURSE_UN_CON, videoBean.getIs_zhengke());
                        bundle.putString(Constant.COURSE_VIDEOID, videoBean.getVideoId());//阿里VID
                        bundle.putInt(Constant.COURSE_ID, Integer.parseInt(videoBean.getCourse_id()));//课ID
                        bundle.putString(Constant.TITLE, videoBean.getVideo_name());//视频标题
                        bundle.putInt(Constant.SECTION_ID, videoBean.getSection_id());//章
                        bundle.putInt(Constant.VIDEO_ID, videoBean.getVideo_id());//小节ID
                        bundle.putInt(Constant.PLAN_ID, plan_id);//计划
                        bundle.putInt(Constant.DAYS, currentDay);//计划天数
                        startActivity(VideoPlayPageActivity.class, bundle);
                    }
                }
            });
            loadinglayout.showContent();
        } else {
            loadinglayout.showEmpty();
        }
    }

    @Override
    public void showLoading() {
        loadinglayout.showLoading();
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
