package com.ucfo.youcaiwx.module.user.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.xtablayout.XTabLayout;
import com.ucfo.youcaiwx.R;
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
import com.ucfo.youcaiwx.module.course.player.adapter.CommonTabAdapter;
import com.ucfo.youcaiwx.module.user.activity.MineCollectionActivity;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author: AND
 * Time: 2019-6-18.  下午 4:35
 * FileName: FragmentQuestionCollection
 * Description:TODO 题库收藏
 */
public class FragmentQuestionCollection extends BaseFragment implements IMineCollectionView {
    @BindView(R.id.xTablayout)
    XTabLayout xTablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    Unbinder unbinder;
    private MineCollectionActivity context;
    private MineCollectionPresenter mineCollectionPresenter;
    private int user_id;
    private ArrayList<String> titlesList;
    private ArrayList<Fragment> fragmentArrayList;

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

    @Override
    protected int setContentView() {
        return R.layout.fragment_questioncollection;
    }

    @Override
    protected void initView(View view) {
        FragmentActivity activity = getActivity();
        if (activity instanceof MineCollectionActivity) {
            context = (MineCollectionActivity) activity;
        }
        user_id = SharedPreferencesUtils.getInstance(context).getInt(Constant.USER_ID, 0);
        mineCollectionPresenter = new MineCollectionPresenter(this);
    }

    @Override
    protected void initData() {
        titlesList = new ArrayList<>();
        fragmentArrayList = new ArrayList<>();
    }

    @Override
    protected void onLazyLoadOnce() {
        super.onLazyLoadOnce();
        mineCollectionPresenter.getProjectList(user_id);
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mineCollectionPresenter.getProjectList(user_id);
            }
        });
    }

    @Override
    public void getPorjectList(ProjectListBean data) {
        if (data != null) {
            if (data.getData().size() > 0 && data.getData() != null) {
                initTablayout(data.getData());
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

    private void initTablayout(List<ProjectListBean.DataBean> data) {
        for (int i = 0; i < data.size(); i++) {
            titlesList.add(data.get(i).getName().trim());
            FragmentQuestionChildCollection fragmentQuestionChildCollection = new FragmentQuestionChildCollection();
            fragmentQuestionChildCollection.setId(data.get(i).getCourse_id());
            fragmentArrayList.add(fragmentQuestionChildCollection);
        }
        FragmentManager childFragmentManager = getChildFragmentManager();
        CommonTabAdapter commonTabAdapter = new CommonTabAdapter(childFragmentManager, fragmentArrayList, titlesList);
        viewpager.setAdapter(commonTabAdapter);//viewpager设置适配器
        xTablayout.setupWithViewPager(viewpager);
        if (data.size() == 1) {
            xTablayout.setTabMode(XTabLayout.MODE_FIXED);
        } else {
            xTablayout.setTabMode(XTabLayout.MODE_SCROLLABLE);
        }
    }

    @Override
    public void getMineQuestioinCollectionList(MineQuestionCollectionListBean data) {
        //TODO nothing
    }

    @Override
    public void getMineCourseCollectionChildList(MineCourseChildListBean data) {
        //TODO nothing
    }

    @Override
    public void getCollectionDirList(MineCourseCollectionDirBean data) {
        //TODO nothing
    }

    @Override
    public void getMineCoursePackageCollectionList(MineCourseBean data) {
        //TODO nothing
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
}
