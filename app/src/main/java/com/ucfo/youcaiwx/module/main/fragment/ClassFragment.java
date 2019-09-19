package com.ucfo.youcaiwx.module.main.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.MovieAdapter;
import com.ucfo.youcaiwx.base.BaseFragment;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.MovieBean;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.module.main.activity.MainActivity;
import com.ucfo.youcaiwx.module.main.activity.WebActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author:AND
 * Time: 2019-3-12.  上午 10:17
 * Email:2911743255@qq.com
 * ClassName: ClassFragment
 * Description:TODO 首页 - 课程中心
 * Detail:
 */
public class ClassFragment extends BaseFragment {

    public static final String TAG = "ClassFragment";
    @BindView(R.id.floating_btn)
    FloatingActionButton floatingBtn;
    Unbinder unbinder;
    @BindView(R.id.movielist)
    RecyclerView movielist;
    @BindView(R.id.movie_image_view)
    ImageView movieImageView;
    @BindView(R.id.titlebar2)
    Toolbar titlebar2;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbarlayout;
    private MainActivity context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

    public static ClassFragment newInstance(String content, String tab) {
        ClassFragment newFragment = new ClassFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        bundle.putString("tab", tab);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    protected void initView(View view) {
        context = (MainActivity) getActivity();


        context.setSupportActionBar(titlebar2);
        ActionBar supportActionBar = context.getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle("豆瓣强势来袭");

        titlebar2.setTitle("第二个豆瓣");
        titlebar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showCenterShortText(context, "你若安好");
            }
        });

    }

    @Override
    protected void initData() {

        loadNetData();

    }

    private void loadNetData() {
        OkGo.<String>post(ApiStores.DOUBAN_API)
                .params("start", "0")
                .params("count", "20")
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response.code() == 200) {
                            String body = response.body();
                            MovieBean movieBean = new Gson().fromJson(body, MovieBean.class);
                            List<MovieBean.SubjectsBean> subjects = movieBean.getSubjects();
                            initAdapter(subjects);
                        }
                    }
                });
    }

    private void initAdapter(List<MovieBean.SubjectsBean> subjects) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        movielist.setLayoutManager(linearLayoutManager);
        MovieAdapter movieAdapter = new MovieAdapter(subjects, context);

        movielist.addItemDecoration(new SpacesItemDecoration(0, 10, Color.TRANSPARENT));
        movielist.setAdapter(movieAdapter);

        movieAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String alt = subjects.get(position).getAlt();
                Intent intent = new Intent(getActivity(), WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.WEB_URL, alt);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_class;
    }

    @OnClick(R.id.floating_btn)
    public void onViewClicked() {

    }
}
