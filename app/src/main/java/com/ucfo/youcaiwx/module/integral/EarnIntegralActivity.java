package com.ucfo.youcaiwx.module.integral;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.integral.EarnIntegralAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.integral.EarnIntegralBean;
import com.ucfo.youcaiwx.module.user.activity.PersonnelSettingActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.integral.IntegralPresenter;
import com.ucfo.youcaiwx.presenter.view.integral.IIntegralEarnView;
import com.ucfo.youcaiwx.utils.baseadapter.OnItemClickListener;
import com.ucfo.youcaiwx.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-9-19 上午 10:08
 * Package: com.ucfo.youcaiwx.module.integral
 * FileName: EarnIntegralActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 赚积分
 */
public class EarnIntegralActivity extends BaseActivity implements IIntegralEarnView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.showline)
    View showline;
    @BindView(R.id.recyclerview_day)
    RecyclerView recyclerviewDay;
    @BindView(R.id.recyclerview_new)
    RecyclerView recyclerviewNew;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    private IntegralPresenter integralPresenter;
    private int user_id;
    private List<EarnIntegralBean.DataBean.DailyBean> dailyBeanList;
    private List<EarnIntegralBean.DataBean.NoviceBean> noviceBeanList;
    private EarnIntegralAdapter dailyAdapter, noviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        integralPresenter.earnIntegral(user_id);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_earn_integral;
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
        titlebarMidtitle.setText(getResources().getString(R.string.integral_gainIntegral));
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
        int topBottom = DensityUtil.dp2px(0.5F);
        int leftRight = DensityUtil.dp2px(12);
        recyclerviewNew.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom, ContextCompat.getColor(this, R.color.color_E6E6E6)));
        recyclerviewDay.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom, ContextCompat.getColor(this, R.color.color_E6E6E6)));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerviewNew.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerviewDay.setLayoutManager(linearLayoutManager2);
    }

    @Override
    protected void initData() {
        super.initData();
        dailyBeanList = new ArrayList<>();
        noviceBeanList = new ArrayList<>();

        SharedPreferencesUtils sharedPreferencesUtils = SharedPreferencesUtils.getInstance(this);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);

        integralPresenter = new IntegralPresenter(this);

        loadinglayout.setOnErrorInflateListener(new LoadingLayout.OnInflateListener() {
            @Override
            public void onInflate(View inflated) {
                integralPresenter.earnIntegral(user_id);
            }
        });
    }

    @Override
    public void earnItegral(EarnIntegralBean data) {
        if (data != null) {
            EarnIntegralBean.DataBean dataData = data.getData();
            List<EarnIntegralBean.DataBean.DailyBean> daily = dataData.getDaily();
            List<EarnIntegralBean.DataBean.NoviceBean> novice = dataData.getNovice();

            dailyBeanList.clear();
            dailyBeanList.addAll(daily);
            noviceBeanList.clear();
            noviceBeanList.addAll(novice);

            initAdapter();
            loadinglayout.showContent();
        } else {
            loadinglayout.showEmpty();
        }
    }

    private void initAdapter() {
        if (dailyAdapter == null) {
            dailyAdapter = new EarnIntegralAdapter(1, this, dailyBeanList, null);
        } else {
            dailyAdapter.notifyDataSetChanged();
        }
        recyclerviewDay.setAdapter(dailyAdapter);
        if (noviceAdapter == null) {
            noviceAdapter = new EarnIntegralAdapter(2, this, null, noviceBeanList);
        } else {
            noviceAdapter.notifyDataSetChanged();
        }
        recyclerviewNew.setAdapter(noviceAdapter);
        noviceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                EarnIntegralBean.DataBean.NoviceBean bean = noviceBeanList.get(position);
                String count = bean.getCount();
                String number = bean.getNumber();
                switch (bean.getId()) {
                    case 5:
                        //编辑昵称
                        if (!TextUtils.equals(count, number)) {
                            startActivity(PersonnelSettingActivity.class, null);
                        }
                        break;
                    case 6:
                        //编辑头像
                        if (!TextUtils.equals(count, number)) {
                            startActivity(PersonnelSettingActivity.class, null);
                        }
                        break;
                    case 7:
                        //关注公众号
                        if (!TextUtils.equals(count, number)) {
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void showLoading() {
        loadinglayout.showLoading();
    }

    @Override
    public void showLoadingFinish() {
    }

    @Override
    public void showError() {
        loadinglayout.showError();
    }
}
