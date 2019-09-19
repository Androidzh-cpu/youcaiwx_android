package com.ucfo.youcaiwx.module.learncenter;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.learncenter.AddLearnPlanCourseAdapter;
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
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-7-17 上午 9:16
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
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_add_learning_plan;
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(0, DensityUtil.dp2px(10), ContextCompat.getColor(context, R.color.colorWhite));
        recyclerview.addItemDecoration(spacesItemDecoration);
        recyclerview.setLayoutManager(linearLayoutManager);
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
            if (TextUtils.equals(state, String.valueOf(1))) {
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
            if (result.getData() != null && result.getData().size() > 0) {
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
