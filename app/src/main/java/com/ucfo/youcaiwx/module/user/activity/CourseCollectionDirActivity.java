package com.ucfo.youcaiwx.module.user.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.user.MineCourseCollectionDirAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.user.MineCourseBean;
import com.ucfo.youcaiwx.entity.user.MineCourseChildListBean;
import com.ucfo.youcaiwx.entity.user.MineCourseCollectionDirBean;
import com.ucfo.youcaiwx.entity.user.MineQuestionCollectionListBean;
import com.ucfo.youcaiwx.entity.user.ProjectListBean;
import com.ucfo.youcaiwx.module.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.MineCollectionPresenter;
import com.ucfo.youcaiwx.presenter.view.user.IMineCollectionView;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-9-2 上午 10:12
 * Package: com.ucfo.youcaiwx.view.user.activity
 * FileName: CourseCollectionDirActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 课程收藏播放目录
 */

public class CourseCollectionDirActivity extends BaseActivity implements IMineCollectionView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.course_name)
    TextView courseName;
    @BindView(R.id.course_teacher_name)
    TextView courseTeacherName;
    @BindView(R.id.listView)
    ExpandableListView listView;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;

    private CourseCollectionDirActivity context;
    private int user_id, package_id, course_id, course_un_con;
    private MineCollectionPresenter mineCollectionPresenter;
    private Bundle bundle;
    private ArrayList<MineCourseCollectionDirBean.DataBean.SectionBean> list;
    private MineCourseCollectionDirAdapter collectionDirAdapter;
    private String course_title, course_teachername;

    @Override
    protected void onResume() {
        super.onResume();
        if (mineCollectionPresenter != null) {
            mineCollectionPresenter.getCollectionDirList(user_id, package_id, course_id);
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_mine_course_collection;
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
        titlebarRighttitle.setVisibility(View.GONE);
        titlebarMidtitle.setText(getResources().getString(R.string.mine_collection_course));
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
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {
        super.initData();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            package_id = bundle.getInt(Constant.PACKAGE_ID, 0);
            course_id = bundle.getInt(Constant.COURSE_ID, 0);
            course_title = bundle.getString(Constant.TITLE, "");
            course_teachername = bundle.getString(Constant.TEACHER_NAME, "");
            course_un_con = bundle.getInt(Constant.COURSE_UN_CON, 2);//TODO 是否是正课1正课2非正课

            courseName.setText(course_title);
            courseTeacherName.setText(String.valueOf(getResources().getString(R.string.holder_teacher) + "  " + course_teachername));
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
            return;
        }

        context = this;
        user_id = SharedPreferencesUtils.getInstance(context).getInt(Constant.USER_ID, 0);
        mineCollectionPresenter = new MineCollectionPresenter(this);
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mineCollectionPresenter.getCollectionDirList(user_id, package_id, course_id);
            }
        });
        list = new ArrayList<>();
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
        //TODO nothing
    }

    @Override
    public void getMineCourseCollectionChildList(MineCourseChildListBean data) {
        //TODO nothing
    }

    @Override
    public void getCollectionDirList(MineCourseCollectionDirBean data) {
        if (data != null) {
            List<MineCourseCollectionDirBean.DataBean.SectionBean> section = data.getData().getSection();
            list.clear();
            list.addAll(section);

            initAdapter();

            if (loadinglayout != null) {
                loadinglayout.showContent();
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
    }

    private void initAdapter() {
        if (collectionDirAdapter == null) {
            collectionDirAdapter = new MineCourseCollectionDirAdapter(this, list);
            listView.setAdapter(collectionDirAdapter);
        } else {
            collectionDirAdapter.notifyChange(list);
        }
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                collectionDirAdapter.setSelectPosition(groupPosition, childPosition);
                collectionDirAdapter.notifyDataSetChanged();
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.COURSE_PACKAGE_ID, package_id);//包
                bundle.putInt(Constant.COURSE_BUY_STATE, 1);//已购买状态
                bundle.putString(Constant.COURSE_SOURCE, Constant.COLLECTION);//播放源
                bundle.putInt(Constant.COURSE_UN_CON, course_un_con);
                bundle.putInt(Constant.SECTION_ID, list.get(groupPosition).getSection_id());//章
                bundle.putString(Constant.COURSE_VIDEOID, list.get(groupPosition).getVideo().get(childPosition).getVideoId());//阿里VID
                bundle.putInt(Constant.VIDEO_ID, list.get(groupPosition).getVideo().get(childPosition).getId());//小节ID
                bundle.putInt(Constant.COURSE_ID, course_id);//课ID
                startActivity(VideoPlayPageActivity.class, bundle);
                return true;
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
        if (loadinglayout != null) {
            loadinglayout.showError();
        }
    }
}
