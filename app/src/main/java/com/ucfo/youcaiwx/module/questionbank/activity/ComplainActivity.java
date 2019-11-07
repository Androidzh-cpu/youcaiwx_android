package com.ucfo.youcaiwx.module.questionbank.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.widget.flowlayout.TagFlowLayout;

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
public class ComplainActivity extends BaseActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @OnClick(R.id.ask_submit)
    public void onViewClicked() {
        //TODO 投诉按钮
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

        }
    };

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

}
