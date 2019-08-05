package com.ucfo.youcai.view.learncenter;

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

import com.ucfo.youcai.R;
import com.ucfo.youcai.adapter.learncenter.AddLearnPlanCourseAdapter;
import com.ucfo.youcai.base.BaseActivity;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.address.StateStatusBean;
import com.ucfo.youcai.entity.learncenter.AddLearnPlanCheckCourseBean;
import com.ucfo.youcai.entity.learncenter.AddLearnPlanCheckTimeBean;
import com.ucfo.youcai.presenter.presenterImpl.learncenter.AddLearnPlanPresenter;
import com.ucfo.youcai.presenter.view.learncenter.IAddlearnPlanView;
import com.ucfo.youcai.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcai.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcai.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcai.utils.systemutils.DensityUtil;
import com.ucfo.youcai.utils.systemutils.StatusbarUI;
import com.ucfo.youcai.utils.toastutils.ToastUtil;
import com.ucfo.youcai.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-7-17 上午 9:16
 * Package: com.ucfo.youcai.view.learncenter
 * FileName: AddLearningPlanActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 添加学习计划--选择课程
 */
public class AddLearningPlanActivity extends BaseActivity implements IAddlearnPlanView {

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
    private AddLearningPlanActivity context;
    private int user_id;
    private AddLearnPlanPresenter learnPlanPresenter;
    private List<AddLearnPlanCheckCourseBean.DataBean> list;
    private AddLearnPlanCourseAdapter addLearnPlanCourseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_add_learning_plan;
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(DensityUtil.dp2px(10), DensityUtil.dp2px(34), ContextCompat.getColor(context, R.color.colorWhite));
        recyclerview.addItemDecoration(spacesItemDecoration);
        recyclerview.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<>();
        user_id = SharedPreferencesUtils.getInstance(context).getInt(Constant.USER_ID, 0);
        learnPlanPresenter = new AddLearnPlanPresenter(this);

        learnPlanPresenter.checkCourse(user_id);
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                learnPlanPresenter.checkCourse(user_id);
            }
        });
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        //TODO 下一步
        int selectPosition = addLearnPlanCourseAdapter.getSelectPosition();
        if (selectPosition >= 0) {
            String state = list.get(selectPosition).getState();
            if (state.equals("1")) {
                ToastUtil.showBottomShortText(this, getResources().getString(R.string.learncenter_haveCheckCourse));
            } else {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.COURSE_ID, list.get(selectPosition).getCourse_id());
                bundle.putInt(Constant.TYPE, 2);//非零元体验
                startActivity(AddLearningTimeActivity.class, bundle);
            }
        } else {
            ToastUtil.showBottomShortText(this, getResources().getString(R.string.learncenter_checkCourse));
        }
    }

    @Override
    public void getCheckCourseList(AddLearnPlanCheckCourseBean result) {
        if (result != null) {
            if (result.getData() != null && result.getData().size() > 1) {
                List<AddLearnPlanCheckCourseBean.DataBean> beanList = result.getData();
                list.clear();
                list.addAll(beanList);
                initAdapter();
                loadinglayout.showContent();
            } else {
                loadinglayout.showEmpty();
            }
        } else {
            loadinglayout.showEmpty();
        }
    }

    @Override
    public void getCheckTimeList(AddLearnPlanCheckTimeBean result) {
        //TODO nothing
    }

    @Override
    public void addLearnPlanResult(StateStatusBean data) {
        //TODO nothing
    }

    private void initAdapter() {
        if (addLearnPlanCourseAdapter == null) {
            addLearnPlanCourseAdapter = new AddLearnPlanCourseAdapter(list, context);
        } else {
            addLearnPlanCourseAdapter.notifyDataSetChanged();
        }
        recyclerview.setAdapter(addLearnPlanCourseAdapter);
        addLearnPlanCourseAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                addLearnPlanCourseAdapter.setSelectPosition(position);
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showLoadingFinish() {

    }

    @Override
    public void showError() {
        loadinglayout.showEmpty();
    }
}
