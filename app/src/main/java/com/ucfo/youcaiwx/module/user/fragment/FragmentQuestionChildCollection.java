package com.ucfo.youcaiwx.module.user.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.user.MineQuestionCollectionListAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.user.MineCourseCollectionDirBean;
import com.ucfo.youcaiwx.entity.user.MineQuestionCollectionListBean;
import com.ucfo.youcaiwx.entity.user.MineCourseBean;
import com.ucfo.youcaiwx.entity.user.MineCourseChildListBean;
import com.ucfo.youcaiwx.entity.user.ProjectListBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.MineCollectionPresenter;
import com.ucfo.youcaiwx.presenter.view.user.IMineCollectionView;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.module.questionbank.activity.KnowledgeChildListActivity;
import com.ucfo.youcaiwx.module.user.activity.MineCollectionActivity;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author: AND
 * Time: 2019-6-19.  下午 2:34
 * FileName: FragmentQuestionChildCollection
 * Description:TODO 题库收藏列表页
 */
public class FragmentQuestionChildCollection extends BaseFragment implements IMineCollectionView {
    @BindView(R.id.listView)
    ExpandableListView listView;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    Unbinder unbinder;
    private int course_id;
    private MineCollectionActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int user_id;
    private MineCollectionPresenter mineCollectionPresenter;
    private ArrayList<MineQuestionCollectionListBean.DataBean> list;
    private MineQuestionCollectionListAdapter listAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        if (rootView != null) {
            unbinder = ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setId(int course_id) {
        this.course_id = course_id;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_questionchildcollection;
    }

    @Override
    protected void initView(View view) {
        FragmentActivity activity = getActivity();
        if (activity instanceof MineCollectionActivity) {
            context = (MineCollectionActivity) activity;
        }
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
    }

    @Override
    protected void onLazyLoadOnce() {
        super.onLazyLoadOnce();
        mineCollectionPresenter.getMineQuestioinCollectionList(user_id, course_id);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        mineCollectionPresenter = new MineCollectionPresenter(this);
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mineCollectionPresenter.getMineQuestioinCollectionList(user_id, course_id);
            }
        });
    }

    @Override
    public void getPorjectList(ProjectListBean data) {
        //TODO nothing
    }

    @Override
    public void getMineCoursePackageCollectionList(MineCourseBean data) {
        //TODO nothing
    }

    @Override
    public void getMineQuestioinCollectionList(MineQuestionCollectionListBean data) {
        if (data != null) {
            if (data.getData().size() > 0 && data.getData() != null) {
                List<MineQuestionCollectionListBean.DataBean> dataData = data.getData();
                list.clear();
                list.addAll(dataData);
                ininAdapter();
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

    @Override
    public void getMineCourseCollectionChildList(MineCourseChildListBean data) {
        //TODO nothing
    }

    @Override
    public void getCollectionDirList(MineCourseCollectionDirBean data) {
        //TODO nothing
    }

    private void ininAdapter() {
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        } else {
            listAdapter = new MineQuestionCollectionListAdapter(list, context);
        }
        listView.setAdapter(listAdapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                final Bundle bundle = new Bundle();
                MineQuestionCollectionListBean.DataBean.KnobBean bean = list.get(groupPosition).getKnob().get(childPosition);
                int sectionId = list.get(groupPosition).getSection_id();
                int knobId = bean.getKnob_id();

                bundle.putInt(Constant.COURSE_ID, course_id);
                bundle.putInt(Constant.SECTION_ID, sectionId);
                bundle.putInt(Constant.KNOB_ID, knobId);
                bundle.putInt(Constant.PLATE_ID, Constant.PLATE_13);
                startActivity(KnowledgeChildListActivity.class, bundle);
                return true;
            }
        });
    }

    @Override
    public void showLoading() {
        //setProcessLoading(null, true);
    }

    @Override
    public void showLoadingFinish() {
        //dismissPorcess();
    }

    @Override
    public void showError() {
        if (loadinglayout != null) {
            loadinglayout.showError();
        }
    }

}
