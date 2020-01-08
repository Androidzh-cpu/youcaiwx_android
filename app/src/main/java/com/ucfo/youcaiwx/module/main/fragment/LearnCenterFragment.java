package com.ucfo.youcaiwx.module.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.learncenter.LearnCenterNoticeAdapter;
import com.ucfo.youcaiwx.adapter.learncenter.LearnCenterPlanDetailAdapter;
import com.ucfo.youcaiwx.adapter.learncenter.LearncenterPlanAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.home.ActiveEventBean;
import com.ucfo.youcaiwx.entity.learncenter.LearncenterHomeBean;
import com.ucfo.youcaiwx.entity.learncenter.StudyClockInBean;
import com.ucfo.youcaiwx.entity.learncenter.UnFinishPlanBean;
import com.ucfo.youcaiwx.entity.questionbank.QuestionMyProjectBean;
import com.ucfo.youcaiwx.entity.questionbank.SubjectInfoBean;
import com.ucfo.youcaiwx.module.learncenter.AddLearningPlanActivity;
import com.ucfo.youcaiwx.module.learncenter.AddLearningTimeActivity;
import com.ucfo.youcaiwx.module.learncenter.LearningPlanDetailActivity;
import com.ucfo.youcaiwx.module.learncenter.UnFinishedPlanActivity;
import com.ucfo.youcaiwx.module.learncenter.dialog.SignInActive;
import com.ucfo.youcaiwx.module.login.LoginActivity;
import com.ucfo.youcaiwx.module.main.activity.MainActivity;
import com.ucfo.youcaiwx.module.main.activity.WebActivity;
import com.ucfo.youcaiwx.module.questionbank.activity.ErrorCenterActivity;
import com.ucfo.youcaiwx.module.user.activity.MineCourseActivity;
import com.ucfo.youcaiwx.module.user.activity.OfflineCourseActivity;
import com.ucfo.youcaiwx.module.user.activity.PersonnelSettingActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.learncenter.LearncenterHomePresenter;
import com.ucfo.youcaiwx.presenter.presenterImpl.questionbank.QuestionBankHomePresenter;
import com.ucfo.youcaiwx.presenter.view.learncenter.ILearncenterHomeView;
import com.ucfo.youcaiwx.presenter.view.questionbank.IQuestionBankHomeView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.baseadapter.OnItemClickListener;
import com.ucfo.youcaiwx.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.dialog.ActiveEventDialog;
import com.ucfo.youcaiwx.widget.shimmer.ShimmerRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author:AND
 * Time: 2019-3-12.  上午 10:18
 * Email:2911743255@qq.com
 * ClassName: LearnCenterFragment
 * Description:TODO 首页 - 学习中心
 */
public class LearnCenterFragment extends BaseFragment implements ILearncenterHomeView, IQuestionBankHomeView, View.OnClickListener {
    public static final String TAG = "LearnCenterFragment";
    private CircleImageView userIcon;
    private TextView userNickname;
    private TextView userClockinDay;
    private RoundTextView btnClockin;
    private TextView userCourse;
    private TextView userErrorcenter;
    private TextView userOffline;
    private RoundTextView btnContinueStudy;
    private LinearLayout linearContinueStudy;
    private RecyclerView listviewPlan;
    private TextView btnAddlearnplan;
    private ShimmerRecyclerView listviewPlandetail;
    private LinearLayout linearPlandetail;
    private ShimmerRecyclerView listviewNotice;
    private LinearLayout linearNotice;
    private LoadingLayout loadinglayout;
    private LinearLayout linearPlan;

    private MainActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private boolean loginStatus;
    private int userId;

    private LearncenterHomePresenter learncenterHomePresenter;
    private QuestionBankHomePresenter questionBankHomePresenter;

    private List<LearncenterHomeBean.DataBean.PlanBean> planBeanList;
    private List<LearncenterHomeBean.DataBean.LearnListBean> learnList;
    private List<LearncenterHomeBean.DataBean.NewsBean> newsBeanList;
    private LearncenterPlanAdapter learncenterPlanAdapter;
    private LearnCenterPlanDetailAdapter learnCenterPlanDetailAdapter;
    private LearnCenterNoticeAdapter learnCenterNoticeAdapter;

    private String userBeanHead;
    private int currentSubjectId;
    private List<QuestionMyProjectBean.DataBean> projectList;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            updateDataInfo();
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_learncenter;
    }

    @Override
    protected void initView(View itemView) {
        userIcon = (CircleImageView) itemView.findViewById(R.id.user_icon);
        userIcon.setOnClickListener(this);
        userNickname = (TextView) itemView.findViewById(R.id.user_nickname);
        userNickname.setOnClickListener(this);
        userClockinDay = (TextView) itemView.findViewById(R.id.user_clockinDay);
        btnClockin = (RoundTextView) itemView.findViewById(R.id.btn_clockin);
        btnClockin.setOnClickListener(this);
        userCourse = (TextView) itemView.findViewById(R.id.user_course);
        userCourse.setOnClickListener(this);
        userErrorcenter = (TextView) itemView.findViewById(R.id.user_errorcenter);
        userErrorcenter.setOnClickListener(this);
        userOffline = (TextView) itemView.findViewById(R.id.user_offline);
        userOffline.setOnClickListener(this);
        btnContinueStudy = (RoundTextView) itemView.findViewById(R.id.btn_continueStudy);
        btnContinueStudy.setOnClickListener(this);
        linearContinueStudy = (LinearLayout) itemView.findViewById(R.id.linear_continueStudy);
        listviewPlan = (RecyclerView) itemView.findViewById(R.id.listview_plan);
        linearPlan = (LinearLayout) itemView.findViewById(R.id.linear_plan);
        btnAddlearnplan = (TextView) itemView.findViewById(R.id.btn_addlearnplan);
        btnAddlearnplan.setOnClickListener(this);
        listviewPlandetail = (ShimmerRecyclerView) itemView.findViewById(R.id.listview_plandetail);
        linearPlandetail = (LinearLayout) itemView.findViewById(R.id.linear_plandetail);
        listviewNotice = (ShimmerRecyclerView) itemView.findViewById(R.id.listview_notice);
        linearNotice = (LinearLayout) itemView.findViewById(R.id.linear_notice);
        loadinglayout = (LoadingLayout) itemView.findViewById(R.id.loadinglayout);

        FragmentActivity activity = getActivity();
        if (activity instanceof MainActivity) {
            context = (MainActivity) activity;
        }
        initListViewLayoutManager();
    }

    private void initListViewLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listviewPlan.setItemAnimator(new DefaultItemAnimator());
        listviewPlan.addItemDecoration(new SpacesItemDecoration(0, DensityUtil.dp2px(10), ContextCompat.getColor(context, R.color.colorWhite)));
        listviewPlan.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(context);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        listviewPlandetail.setItemAnimator(new DefaultItemAnimator());
        listviewPlandetail.addItemDecoration(new SpacesItemDecoration(0, DensityUtil.dp2px(10), ContextCompat.getColor(context, R.color.colorWhite)));
        listviewPlandetail.setLayoutManager(layoutManager2);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(context);
        layoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        listviewNotice.setItemAnimator(new DefaultItemAnimator());
        listviewNotice.addItemDecoration(new SpacesItemDecoration(0, DensityUtil.dp2px(10), ContextCompat.getColor(context, R.color.colorWhite)));
        listviewNotice.setLayoutManager(layoutManager3);
    }

    @Override
    protected void initData() {
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        learncenterHomePresenter = new LearncenterHomePresenter(this);
        questionBankHomePresenter = new QuestionBankHomePresenter(this);
        planBeanList = new ArrayList<>();
        learnList = new ArrayList<>();
        newsBeanList = new ArrayList<>();
        projectList = new ArrayList<>();

        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDataInfo();
            }
        });

        /**
         * 刷新用户数据
         */
        updateDataInfo();
        /**
         * 获取活动
         */
        initActive();
    }

    /**
     * 活动弹窗
     */
    private void initActive() {
        if (sharedPreferencesUtils == null) {
            sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        }
        boolean loginStatus = sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false);
        if (!loginStatus) {
            OkGo.<String>post(ApiStores.ACTIVEEVENT)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            String body = response.body();
                            if (body != null) {
                                try {
                                    JSONObject object = new JSONObject(body);
                                    int optInt = object.optInt(Constant.CODE);
                                    if (optInt == 200) {
                                        ActiveEventBean bean = new Gson().fromJson(body, ActiveEventBean.class);
                                        ActiveEventBean.DataBean data = bean.getData();
                                        int status = data.getStatus();
                                        if (status == 1) {
                                            String imageUrl = data.getImage_url();
                                            if (!TextUtils.isEmpty(imageUrl)) {
                                                activeEvent(imageUrl);
                                            }
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

        }
    }

    /**
     * 弹出活动页
     */
    private void activeEvent(String url) {
        new ActiveEventDialog(getContext()).builder()
                .setCancelable(true)
                .setImageUrl(url)
                .setCanceledOnTouchOutside(true)
                .setNegativeButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                }).show();
    }

    //获取学习中心首页数据
    public void updateDataInfo() {
        if (sharedPreferencesUtils == null) {
            sharedPreferencesUtils = SharedPreferencesUtils.getInstance(getContext());
        }
        loginStatus = sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false);
        userId = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        //刷新学习中心首页数据
        if (learncenterHomePresenter != null) {
            learncenterHomePresenter.learncenterHome(userId);
        }
        currentSubjectId = sharedPreferencesUtils.getInt(Constant.SUBJECT_ID, 0);
        //刷新已购买课程数据
        if (currentSubjectId == 0) {
            questionBankHomePresenter.getMyProejctList(userId);
        }
    }

    @Override
    public void learncenterHome(LearncenterHomeBean result) {
        if (result != null) {
            if (isAdded()) {
                initLearnCenter(result);
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showError();
            }
            if (isAdded()) {
                userInfoClear();
            }
        }
    }

    @Override
    public void studyClockInResult(StudyClockInBean result) {
        if (result != null) {
            if (result.getCode() == 200) {
                if (result.getData() != null) {
                    StudyClockInBean.DataBean data = result.getData();
                    FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    SignInActive signInActive = new SignInActive();
                    signInActive.setUserBean(data);
                    signInActive.show(fragmentTransaction, "sign");
                    int num = data.getNum();
                    userClockinDay.setText(String.valueOf(num));
                } else {
                    ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
                }
            } else {
                String msg = result.getMsg();
                ToastUtil.showBottomShortText(context, msg);
            }
        } else {
            ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
        }
    }

    @Override
    public void getUnFinishPlan(UnFinishPlanBean result) {
        //TODO nothing
    }

    /**
     * 清除用户信息
     */
    private void userInfoClear() {
        userIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.icon_headdefault));
        userNickname.setText(getResources().getString(R.string.learncenter_login));
        userClockinDay.setText(String.valueOf(0));
    }

    //TODO 初始化学习中心页面
    private void initLearnCenter(LearncenterHomeBean data) {
        if (loadinglayout != null) {
            loadinglayout.showContent();
        }
        LearncenterHomeBean.DataBean dataData = data.getData();
        if (!loginStatus) {//未登录
            linearContinueStudy.setVisibility(linearContinueStudy.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);//继续学习
            linearPlandetail.setVisibility(linearPlandetail.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);//学习计划详情
            linearNotice.setVisibility(linearNotice.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);//学习公告
            linearPlan.setVisibility(linearPlan.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);//学习计划列表
            planBeanList.clear();
            planBeanList.addAll(dataData.getPlan());
            if (planBeanList != null && planBeanList.size() > 0) {
                initPlanAdapter();
            }
            userInfoClear();
        } else {
            //已登录

            //TODO 是否有未完成的学习计划    1有未读2已读
            int state = dataData.getState();
            //TODO 是否拥有学习计划     1有2没有
            int addlearn = dataData.getAddlearn();
            if (dataData.getUser() != null) {
                LearncenterHomeBean.DataBean.UserBean userBean = dataData.getUser();
                int card = userBean.getCard();//打卡天数
                userBeanHead = userBean.getHead();//头像
                String username = userBean.getUsername();//昵称
                if (!TextUtils.isEmpty(username)) {
                    userNickname.setText(username);
                }
                if (!TextUtils.isEmpty(userBeanHead)) {
                    RequestOptions requestOptions = new RequestOptions()
                            .placeholder(R.mipmap.icon_headdefault)
                            .error(R.mipmap.image_loaderror)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                    GlideUtils.load(context, userBeanHead, userIcon, requestOptions);
                }
                userClockinDay.setText(String.valueOf(card));
            } else {
                userInfoClear();
            }
            ///////////////////////////////TODO 再丑也要看的分割线///////////////////////////////////////////////
            switch (addlearn) {
                //TODO 学习计划
                case 1:
                    //todo 有学习计划
                    linearPlan.setVisibility(linearPlan.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);//学习计划列表
                    linearPlandetail.setVisibility(linearPlandetail.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);//学习计划详情
                    linearNotice.setVisibility(linearNotice.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);//学习公告
                    break;
                case 2:
                    //todo 没有学习计划
                    linearPlan.setVisibility(linearPlan.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);//学习计划列表
                    linearPlandetail.setVisibility(linearPlandetail.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);//学习计划详情
                    linearNotice.setVisibility(linearNotice.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);//学习公告
                    break;
                default:
                    break;
            }
            switch (state) {//TODO 是否有未完成的学习计划
                case 1://您有一条未读的学习计划哦
                    linearContinueStudy.setVisibility(View.VISIBLE);
                    break;
                case 2://都完成了,NB啊这个人
                    linearContinueStudy.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
            planBeanList.clear();
            learnList.clear();
            newsBeanList.clear();

            planBeanList.addAll(dataData.getPlan());
            learnList.addAll(dataData.getLearnList());
            if (planBeanList != null && planBeanList.size() > 0) {
                initPlanAdapter();
            }
            if (learnList != null && learnList.size() > 0) {
                initPlanDetailAdapter();
            }
            if (dataData.getNews() != null) {
                LearncenterHomeBean.DataBean.NewsBean news = dataData.getNews();
                newsBeanList.add(news);
                initNoticeAdapter();
            }
        }
    }

    /**
     * 学习公告
     */
    private void initNoticeAdapter() {
        if (learnCenterNoticeAdapter == null) {
            learnCenterNoticeAdapter = new LearnCenterNoticeAdapter(newsBeanList, context);
            listviewNotice.setAdapter(learnCenterNoticeAdapter);
        } else {
            learnCenterNoticeAdapter.notifyChange(newsBeanList);
        }
        learnCenterNoticeAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.WEB_TITLE, newsBeanList.get(position).getTitle());
                bundle.putString(Constant.WEB_URL, newsBeanList.get(position).getJumphref());
                startActivity(WebActivity.class, bundle);
            }
        });
    }

    /**
     * 可以参加的学习计划
     */
    private void initPlanAdapter() {
        if (learncenterPlanAdapter == null) {
            learncenterPlanAdapter = new LearncenterPlanAdapter(planBeanList, context);
            listviewPlan.setAdapter(learncenterPlanAdapter);
        } else {
            learncenterPlanAdapter.notifyChange(planBeanList);
        }

        learncenterPlanAdapter.setItemClick(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (loginStatus) {
                    if (planBeanList != null && planBeanList.size() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constant.COURSE_ID, planBeanList.get(position).getCourse_id());
                        bundle.putInt(Constant.TYPE, planBeanList.get(position).getIs_exper());
                        startActivity(AddLearningTimeActivity.class, bundle);
                    }
                } else {
                    startActivity(LoginActivity.class, null);
                }
            }
        });
    }

    /**
     * 已参加的学习计划详情
     */
    private void initPlanDetailAdapter() {
        if (learnCenterPlanDetailAdapter == null) {
            learnCenterPlanDetailAdapter = new LearnCenterPlanDetailAdapter(learnList, context);
            listviewPlandetail.setAdapter(learnCenterPlanDetailAdapter);
        } else {
            learnCenterPlanDetailAdapter.notifyChange(learnList);
        }
        //设置用户头像
        learnCenterPlanDetailAdapter.setUserBeanHead(userBeanHead);

        learnCenterPlanDetailAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LearncenterHomeBean.DataBean.LearnListBean bean = learnList.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.COURSE_ID, bean.getCourse_id());
                bundle.putInt(Constant.ID, bean.getPlan_id());
                bundle.putInt(Constant.TYPE, bean.getIs_exper());//TODO 1:0元体验 2:正课
                bundle.putString(Constant.TITLE, bean.getPlan_name());
                bundle.putString(Constant.OVER, bean.getIs_overdue());
                startActivity(LearningPlanDetailActivity.class, bundle);
            }
        });
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
        if (loadinglayout != null) {
            loadinglayout.showError();
        }
    }

    @Override
    public void getMyProejctList(QuestionMyProjectBean result) {
        if (result != null) {
            if (result.getData() != null && result.getData().size() > 0) {//TODO 购买的有题库
                projectList.clear();
                projectList.addAll(result.getData());
                if (projectList != null && projectList.size() > 0) {//TODO  已购买过的科目
                    currentSubjectId = sharedPreferencesUtils.getInt(Constant.SUBJECT_ID, 0);
                    if (projectList.size() == 1) {//TODO  只有一个题库
                        currentSubjectId = projectList.get(0).getId();//默认选中第一个
                        sharedPreferencesUtils.putInt(Constant.SUBJECT_ID, currentSubjectId);//存放当前的科目ID
                    } else {//TODO  多个题库
                        if (currentSubjectId != 0) {//TODO 本地已存储上次的科目
                            for (int i = 0; i < projectList.size(); i++) {
                                if (currentSubjectId == projectList.get(i).getId()) {
                                    break;
                                }
                            }
                        } else {//TODO 未存储上次的科目
                            currentSubjectId = projectList.get(0).getId();//默认选中第一个
                            sharedPreferencesUtils.putInt(Constant.SUBJECT_ID, currentSubjectId);//存放当前的科目ID
                        }
                    }
                }
            } else {
                projectList.clear();
                sharedPreferencesUtils.remove(Constant.SUBJECT_ID);
            }
        }
    }

    @Override
    public void getSubjectInfoBean(SubjectInfoBean data) {

    }

    @Override
    public void onClick(View view) {
        if (loginStatus) {
            Bundle bundle = new Bundle();
            switch (view.getId()) {
                case R.id.user_icon://defaultIcon
                case R.id.user_nickname://昵称
                    startActivity(PersonnelSettingActivity.class, null);
                    break;
                case R.id.btn_clockin:
                    //学习打卡
                    learncenterHomePresenter.signDayCard(userId);
                    break;
                case R.id.user_course:
                    //我的课程
                    startActivity(MineCourseActivity.class, null);
                    break;
                case R.id.user_errorcenter:
                    //错题中心
                    if (currentSubjectId != 0) {
                        int currentSubjectId = sharedPreferencesUtils.getInt(Constant.SUBJECT_ID, 0);
                        bundle.putInt(Constant.COURSE_ID, currentSubjectId);
                        startActivity(ErrorCenterActivity.class, bundle);
                    } else {
                        if (projectList != null && projectList.size() > 0) {
                            int currentSubjectId = sharedPreferencesUtils.getInt(Constant.SUBJECT_ID, 0);
                            bundle.putInt(Constant.COURSE_ID, currentSubjectId);
                            startActivity(ErrorCenterActivity.class, bundle);
                        } else {
                            ToastUtil.showBottomShortText(getActivity(), getResources().getString(R.string.course_buyBank));
                        }
                    }
                    break;
                case R.id.user_offline:
                    //离线课程
                    startActivity(OfflineCourseActivity.class, null);
                    break;
                case R.id.btn_continueStudy:
                    //继续学习
                    startActivity(UnFinishedPlanActivity.class, null);
                    break;
                case R.id.btn_addlearnplan:
                    //添加学习计划
                    startActivity(AddLearningPlanActivity.class, null);
                    break;
                default:
                    break;
            }
        } else {
            startActivity(LoginActivity.class, null);
        }

        /////////////////
        switch (view.getId()) {
            case R.id.user_course:
                // TODO 19/12/16
                break;
            case R.id.user_errorcenter:
                // TODO 19/12/16
                break;
            case R.id.user_offline:
                // TODO 19/12/16
                break;
            case R.id.btn_addlearnplan:
                // TODO 19/12/16
                break;
            default:
                break;
        }
    }
}
