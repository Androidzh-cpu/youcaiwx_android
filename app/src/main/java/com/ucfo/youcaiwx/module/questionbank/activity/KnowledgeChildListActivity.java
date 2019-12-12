package com.ucfo.youcaiwx.module.questionbank.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.questionbank.QuestionKnowledgeChildListAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.questionbank.QuestionBankHightErrorBean;
import com.ucfo.youcaiwx.entity.questionbank.QuestionErrorCenterBean;
import com.ucfo.youcaiwx.entity.questionbank.QuestionKnowLedgeChildListBean;
import com.ucfo.youcaiwx.entity.questionbank.QuestionKnowledgeListBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.questionbank.QuestionBankKnowledgePresenter;
import com.ucfo.youcaiwx.presenter.view.questionbank.IQuestionBankKonwledgeView;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.dialog.TestModeSwitchDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-5-8 下午 2:03
 * FileName: KnowledgeChildListActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 知识点三级列表
 */
public class KnowledgeChildListActivity extends BaseActivity implements IQuestionBankKonwledgeView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.btn_look_analysis)
    Button btnLookAnalysis;
    @BindView(R.id.btn_exercise)
    Button btnExercise;
    @BindView(R.id.holder_linearlayout)
    LinearLayout holderLinearlayout;
    private Bundle bundle;
    private int knob_id, num, section_id, plate_id, course_id;
    private QuestionBankKnowledgePresenter questionBankKnowledgePresenter;
    private ArrayList<QuestionKnowLedgeChildListBean.DataBean> list;
    private QuestionKnowledgeChildListAdapter listAdapter;
    private KnowledgeChildListActivity context;
    private int user_id;

    @Override
    protected int setContentView() {
        return R.layout.activity_knowledge_child_list;
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
        list = new ArrayList<>();
        questionBankKnowledgePresenter = new QuestionBankKnowledgePresenter(this);

        context = this;
        user_id = SharedPreferencesUtils.getInstance(context).getInt(Constant.USER_ID, 0);


        bundle = getIntent().getExtras();
        if (bundle != null) {
            plate_id = bundle.getInt(Constant.PLATE_ID, 0);//板块ID
            section_id = bundle.getInt(Constant.SECTION_ID, 0);//章ID
            knob_id = bundle.getInt(Constant.KNOB_ID, 0);//知识点二级列表id
            num = bundle.getInt(Constant.NUM, 0);//做题数
            course_id = bundle.getInt(Constant.COURSE_ID, 0);//专业ID

            loadNetData();
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }

        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNetData();
            }
        });
    }

    public void loadNetData() {
        switch (plate_id) {
            case Constant.PLATE_13://收藏夹知识点
                questionBankKnowledgePresenter.getQuestionCollectionList(user_id, course_id, section_id, knob_id);
                break;
            case Constant.PLATE_4://高频错题知识点
                questionBankKnowledgePresenter.getHighFrequencyWrongChildList(course_id, section_id, knob_id);
                break;
            case Constant.PLATE_7://错题中心知识点
                questionBankKnowledgePresenter.getErrorCenterKnowList(course_id, user_id, section_id, knob_id);
                break;
            default://知识点联系
                questionBankKnowledgePresenter.getKnowledgeChildList(course_id, section_id, knob_id);
                break;
        }
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
        titlebarMidtitle.setText(getResources().getString(R.string.question_title_choiceKnowledge));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void getKnowledgeList(QuestionKnowledgeListBean data) {
        //TODO nothing
    }

    @Override
    public void getKnowledgeChildList(QuestionKnowLedgeChildListBean questionKnowLedgeChildListBean) {
        if (questionKnowLedgeChildListBean != null) {
            if (questionKnowLedgeChildListBean.getData() != null && questionKnowLedgeChildListBean.getData().size() > 0) {
                List<QuestionKnowLedgeChildListBean.DataBean> data = questionKnowLedgeChildListBean.getData();
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

    @Override
    public void getHighFrequencyWrongTopic(QuestionBankHightErrorBean questionBankHightErrorBean) {
        //TODO nothing
    }

    @Override
    public void getErrorCenterSectionList(QuestionErrorCenterBean questionErrorCenterBean) {
        //TODO nothing
    }

    private void initAdapter() {
        if (listAdapter == null) {
            listAdapter = new QuestionKnowledgeChildListAdapter(list, this);
        } else {
            listAdapter.notifyDataSetChanged();
        }
        listAdapter.selectedItemPosition(-1);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listAdapter.selectedItemPosition(position);
                //相当于调用getView()重新绘制条目
                listAdapter.notifyDataSetChanged();

                QuestionKnowLedgeChildListBean.DataBean dataBean = list.get(position);
                final Bundle bundle = new Bundle();
                bundle.putInt(Constant.COURSE_ID, course_id);
                bundle.putInt(Constant.SECTION_ID, section_id);
                bundle.putInt(Constant.KNOB_ID, knob_id);
                switch (plate_id) {
                    case Constant.PLATE_1://TODO 知识点练习
                        new TestModeSwitchDialog(context).builder()
                                .setCancelable(true)
                                .setCanceledOnTouchOutside(false)
                                .setNegativeButton(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //练习模式
                                        bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_P);
                                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_1);
                                        bundle.putString(Constant.KNOW_ID, dataBean.getId());
                                        bundle.putInt(Constant.NUM, num);
                                        startActivity(TESTMODEActivity.class, bundle);
                                    }
                                })
                                .setPositiveButton(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //考试模式
                                        bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_E);
                                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_1);
                                        bundle.putString(Constant.KNOW_ID, dataBean.getId());
                                        bundle.putInt(Constant.NUM, num);
                                        startActivity(TESTMODEActivity.class, bundle);
                                    }
                                }).show();
                        break;
                    case Constant.PLATE_4://TODO 系统高频错题
                        bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_E);
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_4);
                        bundle.putString(Constant.KNOW_ID, dataBean.getId());
                        startActivity(TESTMODEActivity.class, bundle);
                        break;
                    case Constant.PLATE_7://TODO 错题中心  不代表解析和做题
                        btnExercise.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                        btnExercise.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
                        btnLookAnalysis.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                        btnLookAnalysis.setBackgroundColor(ContextCompat.getColor(context, R.color.color_E3EEFF));
                        break;
                    case Constant.PLATE_13://TODO 题库收藏,查看我的收藏试题
                        bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_A);
                        bundle.putInt(Constant.PLATE_ID, Constant.PLATE_13);
                        bundle.putString(Constant.KNOW_ID, dataBean.getId());
                        bundle.putString(Constant.TITLE, dataBean.getKnow_name());
                        startActivity(TESTMODEActivity.class, bundle);
                        break;
                    default:
                        break;
                }
            }
        });
        if (plate_id == Constant.PLATE_7) {
            //错题中心的知识点列表
            holderLinearlayout.setVisibility(View.VISIBLE);
        }
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

    @OnClick({R.id.btn_look_analysis, R.id.btn_exercise})
    public void onViewClicked(View view) {
        int selectedPosition = listAdapter.getSelectedPosition();
        final Bundle bundle = new Bundle();
        bundle.putInt(Constant.COURSE_ID, course_id);
        bundle.putInt(Constant.SECTION_ID, section_id);
        switch (view.getId()) {
            case R.id.btn_look_analysis:
                //查看解析
                if (selectedPosition >= 0) {
                    String id = list.get(selectedPosition).getId();
                    bundle.putString(Constant.KNOW_ID, id);
                    bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_A);
                    bundle.putInt(Constant.PLATE_ID, Constant.PLATE_7);
                    startActivity(TESTMODEActivity.class, bundle);
                }
                break;
            case R.id.btn_exercise:
                //去做题
                if (selectedPosition >= 0) {
                    String id = list.get(selectedPosition).getId();
                    bundle.putString(Constant.KNOW_ID, id);
                    bundle.putInt(Constant.KNOB_ID, knob_id);
                    bundle.putString(Constant.EXERCISE_TYPE, Constant.EXERCISE_E);
                    bundle.putInt(Constant.PLATE_ID, Constant.PLATE_8);
                    startActivity(TESTMODEActivity.class, bundle);
                }
                break;
            default:
                break;
        }
    }
}
