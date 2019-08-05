package com.ucfo.youcai.view.user.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.xtablayout.XTabLayout;
import com.ucfo.youcai.R;
import com.ucfo.youcai.base.BaseFragment;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.user.MineCourseCollectionDirBean;
import com.ucfo.youcai.entity.user.MineQuestionCollectionListBean;
import com.ucfo.youcai.entity.user.MineCourseBean;
import com.ucfo.youcai.entity.user.MineCourseChildListBean;
import com.ucfo.youcai.entity.user.ProjectListBean;
import com.ucfo.youcai.presenter.presenterImpl.user.MineCollectionPresenter;
import com.ucfo.youcai.presenter.view.user.IMineCollectionView;
import com.ucfo.youcai.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcai.view.course.player.adapter.CommonTabAdapter;
import com.ucfo.youcai.view.user.activity.MineCollectionActivity;
import com.ucfo.youcai.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author: AND
 * Time: 2019-6-18.  下午 4:35
 * Package: com.ucfo.youcai.view.user.fragment
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
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
                loadinglayout.showContent();
            } else {
                loadinglayout.showEmpty();
            }
        } else {
            loadinglayout.showEmpty();
        }
    }

    private void initTablayout(List<ProjectListBean.DataBean> data) {
        for (int i = 0; i < data.size(); i++) {
            titlesList.add(data.get(i).getName().trim());//创建标题
            FragmentQuestionChildCollection fragmentQuestionChildCollection = new FragmentQuestionChildCollection();//创建碎片
            fragmentQuestionChildCollection.setId(data.get(i).getCourse_id());
            fragmentArrayList.add(fragmentQuestionChildCollection);
        }
        FragmentManager childFragmentManager = getChildFragmentManager();
        CommonTabAdapter commonTabAdapter = new CommonTabAdapter(childFragmentManager, fragmentArrayList, titlesList);
        viewpager.setAdapter(commonTabAdapter);//viewpager设置适配器
        xTablayout.setupWithViewPager(viewpager);
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
        loadinglayout.showError();
    }
}
