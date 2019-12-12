package com.ucfo.youcaiwx;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.shimmer.ShimmerRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: AND
 * Time: 2019-12-2 下午 2:02
 * Package: com.ucfo.youcaiwx
 * FileName: TetsActivity
 * ORG: www.youcaiwx.com
 * Description:我也不知道为啥我会弄出这个页面,闹着玩?
 */
public class TetsActivity extends BaseActivity {


    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.user_icon)
    CircleImageView userIcon;
    @BindView(R.id.user_nickname)
    TextView userNickname;
    @BindView(R.id.user_clockinDay)
    TextView userClockinDay;
    @BindView(R.id.btn_clockin)
    RoundTextView btnClockin;
    @BindView(R.id.holder_linearlayout)
    LinearLayout holderLinearlayout;
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
    RecyclerView listviewPlan;
    @BindView(R.id.linear_plan)
    LinearLayout linearPlan;
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

    @Override
    protected int setContentView() {
        return R.layout.fragment_learncenter;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

}
