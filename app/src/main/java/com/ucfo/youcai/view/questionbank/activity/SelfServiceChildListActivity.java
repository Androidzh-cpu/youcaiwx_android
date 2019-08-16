package com.ucfo.youcai.view.questionbank.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ucfo.youcai.R;
import com.ucfo.youcai.adapter.questionbank.QuestionSelfHelpChildListAdapter;
import com.ucfo.youcai.base.BaseActivity;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.questionbank.QuestionBankHightErrorBean;
import com.ucfo.youcai.entity.questionbank.QuestionErrorCenterBean;
import com.ucfo.youcai.entity.questionbank.QuestionKnowLedgeChildListBean;
import com.ucfo.youcai.entity.questionbank.QuestionKnowledgeListBean;
import com.ucfo.youcai.presenter.presenterImpl.questionbank.QuestionBankKnowledgePresenter;
import com.ucfo.youcai.presenter.view.questionbank.IQuestionBankKonwledgeView;
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
 * Time: 2019-5-9 下午 5:57
 * Package: com.ucfo.youcai.view.questionbank.activity
 * FileName: SelfServiceChildListActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 自主练习三级页面
 */
public class SelfServiceChildListActivity extends BaseActivity implements IQuestionBankKonwledgeView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    private ArrayList<QuestionKnowLedgeChildListBean.DataBean> list;
    private ArrayList<Integer> checkList;
    private SelfServiceChildListActivity context;
    private QuestionBankKnowledgePresenter questionBankKnowledgePresenter;
    private Bundle bundle;
    private int number, knob_id, section_id, course_id, plate_id;//上一页的做题数,知识点id,章的id,专业,板块
    private int checkNumber = 0;
    private boolean msgBoolean = false;
    private QuestionSelfHelpChildListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_self_service_child_list;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        context = this;
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<>();
        checkList = new ArrayList<>();
        //注册网络
        questionBankKnowledgePresenter = new QuestionBankKnowledgePresenter(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            section_id = bundle.getInt(Constant.SECTION_ID, 0);
            course_id = bundle.getInt(Constant.COURSE_ID, 0);
            number = bundle.getInt(Constant.NUMBER, 0);
            knob_id = bundle.getInt(Constant.KNOB_ID, 0);
            plate_id = bundle.getInt(Constant.PLATE_ID, 0);

            questionBankKnowledgePresenter.getKnowledgeChildList(course_id, section_id,knob_id);
        } else {
            loadinglayout.showEmpty();
        }
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        //状态栏白色,字体黑色
        StatusbarUI.setStatusBarUIMode(this, Color.TRANSPARENT, true);
        setSupportActionBar(titlebarToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        titlebarRighttitle.setVisibility(View.GONE);
        titlebarMidtitle.setText(getResources().getString(R.string.question_title_writes_really));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void getKnowledgeChildList(QuestionKnowLedgeChildListBean questionKnowLedgeChildListBean) {
        if (questionKnowLedgeChildListBean != null) {
            if (questionKnowLedgeChildListBean.getData() != null && questionKnowLedgeChildListBean.getData().size() > 0) {
                List<QuestionKnowLedgeChildListBean.DataBean> beanList = questionKnowLedgeChildListBean.getData();
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

    private void initAdapter() {
        if (listAdapter == null) {
            listAdapter = new QuestionSelfHelpChildListAdapter(list, this);
        } else {
            listAdapter.notifyDataSetChanged();
        }
        listView.setAdapter(listAdapter);
    }

    @Override
    public void getHighFrequencyWrongTopic(QuestionBankHightErrorBean
                                                   questionBankHightErrorBean) {
        //TODO thing
    }

    @Override
    public void getErrorCenterSectionList(QuestionErrorCenterBean questionErrorCenterBean) {
        //TODO nothing
    }

    @Override
    public void getKnowledgeList(QuestionKnowledgeListBean data) {
        //TODO thing
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

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        checkList.clear();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getChecked()) {
                String id = list.get(i).getId();
                checkList.add(Integer.parseInt(id));
            }
        }

        if (checkList.size() > 0) {//已选中知识点
            String string = checkList.toString().trim();
            String substring = string.substring(1, string.length() - 1);

            Bundle bundle = new Bundle();
            bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_E);
            bundle.putInt(Constant.COURSE_ID, course_id);
            bundle.putInt(Constant.SECTION_ID, section_id);
            bundle.putInt(Constant.PLATE_ID, plate_id);
            bundle.putString(Constant.KNOW_ID, substring);
            bundle.putInt(Constant.NUM, number);
            bundle.putInt(Constant.KNOB_ID, knob_id);

            startActivity(TESTMODEActivity.class, bundle);
        } else {
            ToastUtil.showBottomShortText(context, "请选择知识点");
        }
    }
}
