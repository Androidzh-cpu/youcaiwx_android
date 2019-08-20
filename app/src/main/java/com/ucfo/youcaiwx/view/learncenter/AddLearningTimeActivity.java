package com.ucfo.youcaiwx.view.learncenter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.learncenter.AddLearnPlanTimeAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.address.StateStatusBean;
import com.ucfo.youcaiwx.entity.learncenter.AddLearnPlanCheckCourseBean;
import com.ucfo.youcaiwx.entity.learncenter.AddLearnPlanCheckTimeBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.learncenter.AddLearnPlanPresenter;
import com.ucfo.youcaiwx.presenter.view.learncenter.IAddlearnPlanView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.utils.systemutils.StatusbarUI;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-7-17 上午 9:26
 * FileName: AddLearningTimeActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 添加考试时间
 */
public class AddLearningTimeActivity extends BaseActivity implements IAddlearnPlanView {

    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    private AddLearningTimeActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int user_id;
    private List<AddLearnPlanCheckTimeBean.DataBean> list;
    private AddLearnPlanPresenter addLearnPlanPresenter;
    private Bundle bundle;
    private int course_id;
    private int type;
    private AddLearnPlanTimeAdapter timeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_add_learning_time;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        StatusbarUI.setStatusBarUIMode(this, Color.TRANSPARENT, true);
        setSupportActionBar(titlebarToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        titlebarMidtitle.setText(getResources().getString(R.string.learncenter_addPlan));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        context = this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<>();
        addLearnPlanPresenter = new AddLearnPlanPresenter(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            course_id = bundle.getInt(Constant.COURSE_ID, 0);
            type = bundle.getInt(Constant.TYPE, 1);//	TODO 学习计划类型  1:0元体验 2:购买课程

            addLearnPlanPresenter.checkCourseExamTime(user_id, course_id, type);
        } else {
            loadinglayout.showEmpty();
        }
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLearnPlanPresenter.checkCourseExamTime(user_id, course_id, type);
            }
        });
    }


    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        //TODO 下一步
        int selectPosition = timeAdapter.getSelectPosition();
        if (selectPosition >= 0) {
            addLearnPlanPresenter.addLearnPlan(user_id, course_id, type, list.get(selectPosition).getTest_time());
        } else {
            ToastUtil.showBottomShortText(this, getResources().getString(R.string.learncenter_checkTime));
        }
    }

    @Override
    public void getCheckCourseList(AddLearnPlanCheckCourseBean result) {
        //TODO nothing
    }

    @Override
    public void addLearnPlanResult(StateStatusBean data) {
        if (data != null) {
            int state = data.getData().getState();
            if (state == 1) {
                startActivity(AddLearningPlanCompleteActivity.class, null);
            } else {
                ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
            }
        } else {
            ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
        }
    }

    @Override
    public void getCheckTimeList(AddLearnPlanCheckTimeBean result) {
        if (result != null) {
            if (result.getData() != null && result.getData().size() > 0) {
                List<AddLearnPlanCheckTimeBean.DataBean> data = result.getData();
                list.clear();
                list.addAll(data);
                initAdapter();
                loadinglayout.showContent();
            } else {
                loadinglayout.showEmpty();
            }
        } else {
            loadinglayout.showEmpty();
        }
    }

    private void initAdapter() {
        GridLayoutManager gridLayoutManager;
        if (list.size() == 1) {
            gridLayoutManager = new GridLayoutManager(this, 1);
        } else {
            gridLayoutManager = new GridLayoutManager(this, 2);
        }
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(DensityUtil.dp2px(48), DensityUtil.dp2px(34), ContextCompat.getColor(context, R.color.colorWhite));
        recyclerview.addItemDecoration(spacesItemDecoration);
        recyclerview.setLayoutManager(gridLayoutManager);

        if (timeAdapter == null) {
            timeAdapter = new AddLearnPlanTimeAdapter(list, context);
        } else {
            timeAdapter.notifyDataSetChanged();
        }
        recyclerview.setAdapter(timeAdapter);
        timeAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                timeAdapter.setSelectPosition(position);
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
}
