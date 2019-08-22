package com.ucfo.youcaiwx.view.main.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.learncenter.LearnCenterNoticeAdapter;
import com.ucfo.youcaiwx.adapter.learncenter.LearnCenterPlanDetailAdapter;
import com.ucfo.youcaiwx.adapter.learncenter.LearncenterPlanAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.learncenter.LearncenterHomeBean;
import com.ucfo.youcaiwx.entity.learncenter.StudyClockInBean;
import com.ucfo.youcaiwx.entity.learncenter.UnFinishPlanBean;
import com.ucfo.youcaiwx.entity.questionbank.QuestionMyProjectBean;
import com.ucfo.youcaiwx.entity.questionbank.SubjectInfoBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.learncenter.LearncenterHomePresenter;
import com.ucfo.youcaiwx.presenter.presenterImpl.questionbank.QuestionBankHomePresenter;
import com.ucfo.youcaiwx.presenter.view.learncenter.ILearncenterHomeView;
import com.ucfo.youcaiwx.presenter.view.questionbank.IQuestionBankHomeView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.baseadapter.OnItemClickListener;
import com.ucfo.youcaiwx.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.view.learncenter.AddLearningPlanActivity;
import com.ucfo.youcaiwx.view.learncenter.AddLearningTimeActivity;
import com.ucfo.youcaiwx.view.learncenter.LearningPlanDetailActivity;
import com.ucfo.youcaiwx.view.learncenter.UnFinishedPlanActivity;
import com.ucfo.youcaiwx.view.learncenter.dialog.SignInActive;
import com.ucfo.youcaiwx.view.login.LoginActivity;
import com.ucfo.youcaiwx.view.main.activity.MainActivity;
import com.ucfo.youcaiwx.view.main.activity.WebActivity;
import com.ucfo.youcaiwx.view.questionbank.activity.ErrorCenterActivity;
import com.ucfo.youcaiwx.view.user.activity.MineCourseActivity;
import com.ucfo.youcaiwx.view.user.activity.OfflineCourseActivity;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author:AND
 * Time: 2019-3-12.  上午 10:18
 * Email:2911743255@qq.com
 * ClassName: LearnCenterFragment
 * Description:TODO 首页 - 学习中心
 */
public class LearnCenterFragment extends BaseFragment implements ILearncenterHomeView, IQuestionBankHomeView {
    public static final String TAG = "LearnCenterFragment";
    @BindView(R.id.user_icon)
    CircleImageView userIcon;
    @BindView(R.id.user_nickname)
    TextView userNickname;
    @BindView(R.id.user_clockinDay)
    TextView userClockinDay;
    @BindView(R.id.btn_clockin)
    RoundTextView btnClockin;
    @BindView(R.id.user_course)
    TextView userCourse;
    @BindView(R.id.user_errorcenter)
    TextView userErrorcenter;
    @BindView(R.id.user_offline)
    TextView userOffline;
    @BindView(R.id.btn_continueStudy)
    RoundTextView btnContinueStudy;
    @BindView(R.id.linear_continueStudy)
    LinearLayout linearContinueStudy;
    @BindView(R.id.listview_plan)
    ShimmerRecyclerView listviewPlan;
    @BindView(R.id.btn_addlearnplan)
    TextView btnAddlearnplan;
    @BindView(R.id.listview_plandetail)
    ShimmerRecyclerView listviewPlandetail;
    @BindView(R.id.linear_plandetail)
    LinearLayout linearPlandetail;
    @BindView(R.id.listview_notice)
    ShimmerRecyclerView listviewNotice;
    @BindView(R.id.linear_notice)
    LinearLayout linearNotice;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.linear_plan)
    LinearLayout linearPlan;
    Unbinder unbinder;
    private MainActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private boolean login_status;
    private int user_id;
    private LearncenterHomePresenter learncenterHomePresenter;

    private List<LearncenterHomeBean.DataBean.PlanBean> planBeanList;
    private List<LearncenterHomeBean.DataBean.LearnListBean> learnList;
    private List<LearncenterHomeBean.DataBean.NewsBean> newsBeanList;
    private LearncenterPlanAdapter learncenterPlanAdapter;
    private LearnCenterPlanDetailAdapter learnCenterPlanDetailAdapter;
    private LearnCenterNoticeAdapter learnCenterNoticeAdapter;
    private String userBeanHead;
    private QuestionBankHomePresenter questionBankHomePresenter;
    private int currentSubject_id;
    private List<QuestionMyProjectBean.DataBean> projectList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDataInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_learncenter;
    }

    @Override
    protected void initView(View view) {
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
    }

    //获取学习中心首页数据
    public void updateDataInfo() {
        login_status = sharedPreferencesUtils.getBoolean(Constant.LOGIN_STATUS, false);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        learncenterHomePresenter.learncenterHome(user_id);
        currentSubject_id = sharedPreferencesUtils.getInt(Constant.SUBJECT_ID, 0);
        if (currentSubject_id == 0) {
            questionBankHomePresenter.getMyProejctList(user_id);
        }
    }

    @OnClick({R.id.user_icon, R.id.user_nickname, R.id.btn_clockin, R.id.user_course, R.id.user_errorcenter, R.id.user_offline, R.id.btn_continueStudy, R.id.btn_addlearnplan})
    public void onViewClicked(View view) {
        if (login_status) {
            switch (view.getId()) {
                case R.id.user_icon://icon
                case R.id.user_nickname://昵称
                    break;
                case R.id.btn_clockin://学习打卡
                    learncenterHomePresenter.signDayCard(user_id);
                    break;
                case R.id.user_course://我的课程
                    startActivity(MineCourseActivity.class, null);
                    break;
                case R.id.user_errorcenter://错题中心
                    if (currentSubject_id != 0) {
                        int currentSubjectId = sharedPreferencesUtils.getInt(Constant.SUBJECT_ID, 0);
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constant.COURSE_ID, currentSubjectId);
                        startActivity(ErrorCenterActivity.class, bundle);
                    } else {
                        if (projectList != null && projectList.size() > 0) {
                            int currentSubjectId = sharedPreferencesUtils.getInt(Constant.SUBJECT_ID, 0);
                            Bundle bundle = new Bundle();
                            bundle.putInt(Constant.COURSE_ID, currentSubjectId);
                            startActivity(ErrorCenterActivity.class, bundle);
                        } else {
                            ToastUtil.showBottomShortText(getActivity(), getResources().getString(R.string.course_bugBank));
                        }
                    }
                    break;
                case R.id.user_offline://离线课程
                    startActivity(OfflineCourseActivity.class, null);
                    break;
                case R.id.btn_continueStudy://继续学习
                    startActivity(UnFinishedPlanActivity.class, null);
                    break;
                case R.id.btn_addlearnplan://添加学习计划
                    startActivity(AddLearningPlanActivity.class, null);
                    break;
                default:
                    break;
            }
        } else {
            startActivity(LoginActivity.class, null);
        }
    }

    @Override
    public void learncenterHome(LearncenterHomeBean result) {
        if (result != null) {
            initLearnCenter(result);
        } else {
            loadinglayout.showError();
            userInfoClear();
        }
    }

    @Override
    public void studyClockInResult(StudyClockInBean result) {
        if (result != null) {
            if (result.getCode() == 200) {
                if (result.getData() != null) {
                    StudyClockInBean.DataBean data = result.getData();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
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
        Glide.with(context).load(R.mipmap.icon_headdefault).dontAnimate().crossFade().skipMemoryCache(false).into(userIcon);
        userNickname.setText(getResources().getString(R.string.learncenter_login));
        userClockinDay.setText(String.valueOf(0));
    }

    //TODO 初始化学习中心页面
    private void initLearnCenter(LearncenterHomeBean data) {
        loadinglayout.showContent();
        LearncenterHomeBean.DataBean dataData = data.getData();
        if (!login_status) {//未登录
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
        } else {//已登录
            int state = dataData.getState();//TODO 是否有未完成的学习计划    1有未读2已读
            int addlearn = dataData.getAddlearn();//TODO 是否拥有学习计划     1有2没有
            if (dataData.getUser() != null) {
                LearncenterHomeBean.DataBean.UserBean userBean = dataData.getUser();
                int card = userBean.getCard();//打卡天数
                userBeanHead = userBean.getHead();//头像
                String username = userBean.getUsername();//昵称
                if (!TextUtils.isEmpty(username)) {
                    userNickname.setText(username);
                }
                if (!TextUtils.isEmpty(userBeanHead)) {
                    Glide.with(context)
                            .load(userBeanHead)
                            .error(R.mipmap.image_loaderror)
                            .dontAnimate()
                            .crossFade()
                            .skipMemoryCache(false)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .priority(Priority.HIGH)
                            .into(userIcon);
                }
                userClockinDay.setText(String.valueOf(card));
            } else {
                userInfoClear();
            }
            ///////////////////////////////TODO 再丑也要看的分割线///////////////////////////////////////////////
            switch (addlearn) {//TODO 学习计划
                case 1://todo 有学习计划
                    linearPlan.setVisibility(linearPlan.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);//学习计划列表
                    linearPlandetail.setVisibility(linearPlandetail.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);//学习计划详情
                    linearNotice.setVisibility(linearNotice.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);//学习公告
                    break;
                case 2://todo 没有学习计划
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

    //学习公告
    private void initNoticeAdapter() {
        if (learnCenterNoticeAdapter == null) {
            learnCenterNoticeAdapter = new LearnCenterNoticeAdapter(newsBeanList, context);
        } else {
            learnCenterNoticeAdapter.notifyDataSetChanged();
        }
        listviewNotice.setAdapter(learnCenterNoticeAdapter);
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

    //学习计划
    private void initPlanAdapter() {
        if (learncenterPlanAdapter == null) {
            learncenterPlanAdapter = new LearncenterPlanAdapter(planBeanList, context);
        } else {
            learncenterPlanAdapter.notifyDataSetChanged();
        }
        listviewPlan.setAdapter(learncenterPlanAdapter);
        learncenterPlanAdapter.setItemClick(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (login_status) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.COURSE_ID, planBeanList.get(position).getCourse_id());
                    bundle.putInt(Constant.TYPE, planBeanList.get(position).getIs_exper());
                    startActivity(AddLearningTimeActivity.class, bundle);
                } else {
                    startActivity(LoginActivity.class, null);
                }
            }
        });
    }

    //学习计划详情
    private void initPlanDetailAdapter() {
        if (learnCenterPlanDetailAdapter == null) {
            learnCenterPlanDetailAdapter = new LearnCenterPlanDetailAdapter(learnList, context);
        } else {
            learnCenterPlanDetailAdapter.notifyDataSetChanged();
        }
        learnCenterPlanDetailAdapter.setUserBeanHead(userBeanHead);
        listviewPlandetail.setAdapter(learnCenterPlanDetailAdapter);
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
        loadinglayout.showError();
    }

    @Override
    public void getMyProejctList(QuestionMyProjectBean result) {
        if (result != null) {
            if (result.getData() != null && result.getData().size() > 0) {//TODO 购买的有题库
                projectList.clear();
                projectList.addAll(result.getData());
                if (projectList != null && projectList.size() > 0) {//TODO  已购买过的科目
                    currentSubject_id = sharedPreferencesUtils.getInt(Constant.SUBJECT_ID, 0);
                    if (projectList.size() == 1) {//TODO  只有一个题库
                        currentSubject_id = projectList.get(0).getId();//默认选中第一个
                        sharedPreferencesUtils.putInt(Constant.SUBJECT_ID, currentSubject_id);//存放当前的科目ID
                    } else {//TODO  多个题库
                        if (currentSubject_id != 0) {//TODO 本地已存储上次的科目
                            for (int i = 0; i < projectList.size(); i++) {
                                if (currentSubject_id == projectList.get(i).getId()) {
                                    break;
                                }
                            }
                        } else {//TODO 未存储上次的科目
                            currentSubject_id = projectList.get(0).getId();//默认选中第一个
                            sharedPreferencesUtils.putInt(Constant.SUBJECT_ID, currentSubject_id);//存放当前的科目ID
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
}
