package com.ucfo.youcaiwx.module.learncenter;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.learncenter.UnFinishLearnPlan2Adapter;
import com.ucfo.youcaiwx.adapter.learncenter.UnFinishLearnPlanAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.learncenter.LearncenterHomeBean;
import com.ucfo.youcaiwx.entity.learncenter.StudyClockInBean;
import com.ucfo.youcaiwx.entity.learncenter.UnFinishPlanBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.learncenter.LearncenterHomePresenter;
import com.ucfo.youcaiwx.presenter.view.learncenter.ILearncenterHomeView;
import com.ucfo.youcaiwx.utils.baseadapter.OnItemClickListener;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.module.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-7-17 上午 9:53
 * FileName: UnFinishedPlanActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 未完成学习计划
 */
public class UnFinishedPlanActivity extends BaseActivity implements ILearncenterHomeView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.listView)
    RecyclerView listView;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.showline)
    View showline;
    private UnFinishedPlanActivity context;
    private int user_id;
    private LearncenterHomePresenter learncenterHomePresenter;
    private List<UnFinishPlanBean.DataBean> list;
    private UnFinishLearnPlanAdapter unFinishLearnPlanAdapter;
    private UnFinishLearnPlan2Adapter learnPlan2Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        learncenterHomePresenter.getUnFinishPlanList(user_id);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_un_finished_plan;
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
        titlebarMidtitle.setText(getResources().getString(R.string.learncenter_unfinishedPlan));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showline.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<>();
        context = this;
        user_id = SharedPreferencesUtils.getInstance(context).getInt(Constant.USER_ID, 0);

        learncenterHomePresenter = new LearncenterHomePresenter(this);

        //learncenterHomePresenter.getUnFinishPlanList(user_id);
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                learncenterHomePresenter.getUnFinishPlanList(user_id);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void learncenterHome(LearncenterHomeBean result) {
        //TODO nothing
    }

    @Override
    public void studyClockInResult(StudyClockInBean result) {
        //TODO nothing
    }

    @Override
    public void getUnFinishPlan(UnFinishPlanBean result) {
        //测试数据
        String s = "{\"code\":200,\"msg\":\"操作成功\",\"data\":[{\"package_id\":1,\"plan_id\":1,\"plan_name\":\"中文Part-1学习计划\",\"video\":[{\"video_name\":\"2018年7月考期考后盘点P1-杨晔\",\"id\":1},{\"video_name\":\"2018年7月考期考后盘点P2-杨晔\",\"id\":2},{\"video_name\":\"2018年7月考期考后盘点P3-杨晔\",\"id\":2},{\"video_name\":\"2018年7月考期考后盘点P4-杨晔\",\"id\":2},{\"video_name\":\"2018年7月考期考后盘点P5-杨晔\",\"id\":2},{\"video_name\":\"2018年7月考期考后盘点P6-杨晔\",\"id\":2}]},{\"package_id\":1,\"plan_id\":1,\"plan_name\":\"中文Part-2学习计划\",\"video\":[{\"video_name\":\"2018年7月考期考后盘点P1-杨晔\",\"id\":1},{\"video_name\":\"2018年7月考期考后盘点P2-杨晔\",\"id\":2},{\"video_name\":\"2018年7月考期考后盘点P3-杨晔\",\"id\":2},{\"video_name\":\"2018年7月考期考后盘点P4-杨晔\",\"id\":2},{\"video_name\":\"2018年7月考期考后盘点P5-杨晔\",\"id\":2},{\"video_name\":\"2018年7月考期考后盘点P6-杨晔\",\"id\":2}]}]}";
        //result = new Gson().fromJson(s,UnFinishPlanBean.class);
        if (result != null) {
            if (result.getData() != null) {
                List<UnFinishPlanBean.DataBean> data = result.getData();
                list.clear();
                list.addAll(data);
                initAdapter();
                if (loadinglayout != null) {
                    loadinglayout.showContent();
                }
            } else {
                if (loadinglayout != null) {
                    loadinglayout.showEmpty();
                }
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
    }

    private void initAdapter() {
        if (learnPlan2Adapter == null) {
            learnPlan2Adapter = new UnFinishLearnPlan2Adapter(list, context);
        } else {
            learnPlan2Adapter.notifyDataSetChanged();
        }
        listView.setAdapter(learnPlan2Adapter);
        learnPlan2Adapter.setItemClick(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.COURSE_PACKAGE_ID, list.get(position).getPackage_id());//课程包ID
                bundle.putInt(Constant.COURSE_BUY_STATE, 1);//购买状态
                startActivity(VideoPlayPageActivity.class, bundle);
            }
        });
       /* if (unFinishLearnPlanAdapter == null) {
            unFinishLearnPlanAdapter = new UnFinishLearnPlanAdapter(list, this);
        } else {
            unFinishLearnPlanAdapter.notifyDataSetChanged();
        }
        listView.setAdapter(unFinishLearnPlanAdapter);
        for (int i = 0; i < list.size(); i++) {
            listView.expandGroup(i);
        }
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.COURSE_PACKAGE_ID, list.get(groupPosition).getPackage_id());//课程包ID
                bundle.putInt(Constant.COURSE_BUY_STATE, 1);//购买状态
                startActivity(VideoPlayPageActivity.class, bundle);
                return true;
            }
        });*/
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showLoadingFinish() {

    }

    @Override
    public void showError() {
        if (loadinglayout != null) {
            loadinglayout.showError();
        }
    }
}
