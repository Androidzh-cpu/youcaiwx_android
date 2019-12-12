package com.ucfo.youcaiwx.module.questionbank.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.complain.ComplainTypeBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.complain.ComplainPresenter;
import com.ucfo.youcaiwx.presenter.view.complain.IComplainView;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.flowlayout.FlowLayout;
import com.ucfo.youcaiwx.widget.flowlayout.TagAdapter;
import com.ucfo.youcaiwx.widget.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-11-7 下午 2:53
 * Package: com.ucfo.youcaiwx.module.questionbank.activity
 * FileName: ComplainActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 我要投诉
 */
public class ComplainActivity extends BaseActivity implements IComplainView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.showline)
    View showline;
    @BindView(R.id.ask_edittext_content)
    EditText askEdittextContent;
    @BindView(R.id.text_contentnumber)
    TextView textContentnumber;
    @BindView(R.id.flowlayout)
    TagFlowLayout flowlayout;
    @BindView(R.id.ask_submit)
    Button askSubmit;
    private String answer_id;
    private String answer_type;
    private ComplainPresenter complainPresenter;
    private String questionContent;
    private int user_id;
    private String complainID;

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(titlebarToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        titlebarMidtitle.setText(getResources().getString(R.string.complain_title));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_complain;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initData() {
        super.initData();
        //输入字数限制
        askEdittextContent.addTextChangedListener(textWatcher);
        askEdittextContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Constant.COMPLAIN_MAX_COOUNT)});
        //Edittext通知父控件自己处理自己的滑动事件
        askEdittextContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.ask_edittext_content && canVerticalScroll(askEdittextContent)) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;
            }
        });
        user_id = SharedPreferencesUtils.getInstance(this).getInt(Constant.USER_ID, 0);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //题目ID
            answer_id = bundle.getString(Constant.ANSWER_ID, "");
            answer_type = bundle.getString(Constant.ANSWER_TYPE, "");
        }
        complainPresenter = new ComplainPresenter(this);

        complainPresenter.initComplainType();
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick(R.id.ask_submit)
    public void onViewClicked() {
        //TODO 投诉按钮
        questionContent = askEdittextContent.getText().toString().trim();
        if (TextUtils.isEmpty(questionContent)) {//输入为空
            ToastUtil.showBottomShortText(this, getResources().getString(R.string.answer_hinttext));
            return;
        }
        if (questionContent.length() < Constant.QUESTION_MINICOUNT) {//长度不够
            ToastUtil.showBottomShortText(this, getResources().getString(R.string.answer_hinttext2));
            return;
        }
        if (TextUtils.isEmpty(complainID)) {
            ToastUtil.showBottomShortText(this, getResources().getString(R.string.answer_hinttext3));
            return;
        }
        if (!TextUtils.isEmpty(questionContent)) {
            complainPresenter.initComplain(answer_type, answer_id, complainID, String.valueOf(user_id), questionContent);
        }

    }

    @Override
    public void initComplainType(ComplainTypeBean dataBean) {
        //投诉类别
        if (dataBean != null) {
            List<ComplainTypeBean.DataBean> beanList = dataBean.getData();
            TagAdapter<ComplainTypeBean.DataBean> tagAdapter = new TagAdapter<ComplainTypeBean.DataBean>(beanList) {
                @Override
                public void onSelected(int position, View view) {
                    super.onSelected(position, view);
                    complainID = beanList.get(position).getId();
                    view.setBackground(ContextCompat.getDrawable(ComplainActivity.this, R.drawable.shape_rectangle_corners_darkblue));
                }

                @Override
                public void unSelected(int position, View view) {
                    super.unSelected(position, view);
                    view.setBackground(ContextCompat.getDrawable(ComplainActivity.this, R.drawable.shape_rectangle_solid_darkblue));
                }

                @Override
                public View getView(FlowLayout parent, int position, ComplainTypeBean.DataBean dataBean) {
                    TextView textView = (TextView) LayoutInflater.from(ComplainActivity.this).inflate(R.layout.item_tagflowlayout_select, flowlayout, false);
                    textView.setText(dataBean.getComplain_name());
                    textView.setBackground(ContextCompat.getDrawable(ComplainActivity.this, R.drawable.shape_rectangle_solid_darkblue));
                    return textView;
                }
            };
            flowlayout.setMaxSelectCount(Constant.COMPLAIN_MAX_SELECT);
            flowlayout.setAdapter(tagAdapter);
/*
            flowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {
                    Iterator<Integer> iterator = selectPosSet.iterator();
                    while (iterator.hasNext()) {
                        Integer next = iterator.next();
                        complainID = beanList.get(next).getId();
                    }
                }
            });
*/
        } else {
            flowlayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void complainResult(int status) {
        //投诉结果
        if (status == 1) {
            ToastUtil.showBottomShortText(this, getResources().getString(R.string.operation_Success));
            finish();
        } else {
            ToastUtil.showBottomShortText(this, getResources().getString(R.string.operation_Error));
        }
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

    }

    /**
     * EditText竖直方向是否可以滚动
     *
     * @param //editText需要判断的EditText
     * @return true：可以滚动   false：不可以滚动
     */
    private boolean canVerticalScroll(EditText contentEt) {
        //滚动的距离
        int scrollY = contentEt.getScrollY();
        //控件内容的总高度
        int scrollRange = contentEt.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = contentEt.getHeight() - contentEt.getCompoundPaddingTop() - contentEt.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if (scrollDifference == 0) {
            return false;
        }
        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }

    //TODO 输入文字监听
    private TextWatcher textWatcher = new TextWatcher() {
        private int maxLen = Constant.COMPLAIN_MAX_COOUNT; // 最大输入字符

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            textContentnumber.setText(String.format("%s/" + maxLen, s.length()));
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().isEmpty()) {
                askSubmit.setBackground(ContextCompat.getDrawable(ComplainActivity.this, R.mipmap.icon_btnroundgray));
            } else {
                askSubmit.setBackground(ContextCompat.getDrawable(ComplainActivity.this, R.mipmap.icon_btnroundback));
            }
        }
    };

}
