package com.ucfo.youcai.view.course;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.ucfo.youcai.R;
import com.ucfo.youcai.adapter.answer.ImagePickerAdapter;
import com.ucfo.youcai.base.BaseActivity;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.UploadFileBean;
import com.ucfo.youcai.entity.answer.AnswerKnowListBean;
import com.ucfo.youcai.presenter.presenterImpl.answer.AnswerAskPresenter;
import com.ucfo.youcai.presenter.presenterImpl.upload.IUploadFileView;
import com.ucfo.youcai.presenter.presenterImpl.upload.UploadFilePresenter;
import com.ucfo.youcai.presenter.view.answer.IAnswerAskQuestionView;
import com.ucfo.youcai.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcai.utils.systemutils.DensityUtil;
import com.ucfo.youcai.utils.systemutils.StatusbarUI;
import com.ucfo.youcai.utils.toastutils.ToastUtil;
import com.ucfo.youcai.view.course.player.utils.TimeFormater;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Time:2019-4-17   下午 5:45
 * Detail: TODO 课程提问问题
 */
public class CourseAskQuestionActivity extends BaseActivity implements IUploadFileView, IAnswerAskQuestionView {

    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.ask_checkphoto)
    Button askCheckphoto;

    @BindView(R.id.ask_imagelist)
    RecyclerView imageRecyclerview;
    @BindView(R.id.ask_submit)
    RoundTextView askUploadphoto;
    @BindView(R.id.ask_edittext_content)
    EditText askEdittextContent;
    @BindView(R.id.ask_videotime)
    TextView askVideotime;
    @BindView(R.id.ask_content_number)
    TextView askContentNumber;

    private List<String> imageList;
    private ArrayList<String> resultImageList;
    public static final int REQUEST_CODE_CHOOSE = 100;
    private int MAX_IMAGECOUNT = 3;
    private ImagePickerAdapter imagePickerAdapter;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private CourseAskQuestionActivity context;
    private UploadFilePresenter uploadFilePresenter;
    private Bundle bundle;
    private int coursePackageId, courseCourseId, courseVideoId, courseSectionId, userId, courseVideoTime, fileUploadFlag = 0;
    private AnswerAskPresenter answerAskPresenter;
    private String askContent;

    class TextWatcher implements android.text.TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            int num = s.length();
            num = 200 - num;
            askContentNumber.setText(num + "/200");
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @Override
    protected int setContentView() {
        return R.layout.activity_ask_question;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        context = this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        userId = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);//用户ID
        //注册文件上传网络
        uploadFilePresenter = new UploadFilePresenter(this);
        answerAskPresenter = new AnswerAskPresenter(this);

        askEdittextContent.addTextChangedListener(new TextWatcher());
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        super.initData();
        /*RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //申请的权限全部允许
                        } else {
                            //只要有一个权限被拒绝，就会执行
                            finish();
                        }
                    }
                });*/

        imageList = new ArrayList<>();
        resultImageList = new ArrayList<>();
        imageRecyclerview.setLayoutManager(new GridLayoutManager(this, 4));
        imageRecyclerview.setHasFixedSize(true);
        imageRecyclerview.setItemAnimator(new DefaultItemAnimator());

        bundle = getIntent().getExtras();
        if (bundle != null) {
            coursePackageId = bundle.getInt(Constant.PACKAGE_ID, 0);//TODO 课程包ID
            courseCourseId = bundle.getInt(Constant.COURSE_ID, 0);//TODO 课程ID
            courseVideoId = bundle.getInt(Constant.VIDEO_ID, 0);//TODO 视频vio
            courseSectionId = bundle.getInt(Constant.SECTION_ID, 0);//TODO 章节ID
            courseVideoTime = bundle.getInt(Constant.VIDEO_TIME, 0);//TODO 视频节点

            askVideotime.setText(TimeFormater.formatMs(courseVideoTime));
        }
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        StatusbarUI.setStatusBarUIMode(this, Color.TRANSPARENT, true);
        setSupportActionBar(titlebarToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        titlebarRighttitle.setVisibility(View.GONE);
        titlebarMidtitle.setText(getResources().getString(R.string.answer_ask));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            if (data != null) {
                imageRecyclerview.setVisibility(View.VISIBLE);
                List<String> list = Matisse.obtainPathResult(data);
                for (int i = 0; i < list.size(); i++) {
                    imageList.add(list.get(i));
                }
                if (imageList.size() < MAX_IMAGECOUNT) {//选中的图片数量小于最大选择数量,则变更图片最大选择数
                    MAX_IMAGECOUNT = MAX_IMAGECOUNT - imageList.size();
                }
                if (imagePickerAdapter == null) {
                    imagePickerAdapter = new ImagePickerAdapter(context, imageList);
                } else {
                    imagePickerAdapter.notifyDataSetChanged();
                }
                imageRecyclerview.setAdapter(imagePickerAdapter);
                //图片适配器点击事件监听
                imagePickerAdapter.setOnItemClickListener(new ImagePickerAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                    }

                    @Override
                    public void onItemDeleteClick(View view, int position) {
                        imageList.remove(position);
                        imagePickerAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                imageRecyclerview.setVisibility(View.GONE);
            }
        }
    }


    @OnClick({R.id.ask_checkphoto, R.id.ask_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ask_checkphoto://选择图片
                ToastUtil.showBottomShortText(context, "已选张数" + imageList.size());
                if (imageList.size() < 3) {//TODO 已选图片大于3张
                    Matisse.from(this)
                            .choose(MimeType.ofAll())//选择视频和图片
                            .countable(true)//有序选择图片 123456...
                            .maxSelectable(MAX_IMAGECOUNT)//最大选择数量为3
                            .capture(true) //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvide
                            .captureStrategy(new CaptureStrategy(true, "PhotoProvider"))//参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                            .gridExpectedSize(DensityUtil.dip2px(this, 120))
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) //选择方向
                            .thumbnailScale(0.85f) //界面中缩略图的质量
                            .theme(R.style.Matisse_Dracula)//  暗色主题 R.style.Matisse_Dracula
                            .imageEngine(new GlideEngine())//图片加载引擎
                            .forResult(REQUEST_CODE_CHOOSE);//请求码
                } else {
                    ToastUtil.showBottomShortText(context, "最多选择3张图片");
                }
                break;
            case R.id.ask_submit://TODO 提交问题

                askContent = askEdittextContent.getText().toString().trim();

                if (!TextUtils.isEmpty(askContent)) {
                    //加载网络
                    setProcessLoading(getResources().getString(R.string.answer_loading), true);
                    if (imageList != null && imageList.size() > 0) {//TODO 有图片,先上传图片,上传之后再走提问接口
                        if (imageList.size() == 1) {//TODO  单张上传
                            File file = new File(imageList.get(0));
                            uploadFilePresenter.upLoadFile(file);
                        } else {//TODO 多张循环上传
                            for (int i = 0; i < imageList.size(); i++) {
                                File file = new File(imageList.get(i));
                                uploadFilePresenter.upLoadFile(file);
                            }
                        }
                    } else {
                        answerAskPresenter.userAskQuestion(userId, courseVideoId, courseSectionId, courseCourseId, coursePackageId, askContent, courseVideoTime, resultImageList);
                    }
                } else {
                    ToastUtil.showBottomShortText(context, getResources().getString(R.string.answer_hinttext));
                }
                break;
        }
    }

    @Override
    public void startUploadFile() {
    }

    @Override
    public void errorUploadFile() {
    }

    @Override
    public void resultUploadFile(UploadFileBean data) {//TODO  文件上传结果
        if (data != null) {
            int code = data.getCode();
            if (code == 200) {
                ToastUtil.showCenterLongText(context, data.getMsg());//提示上传信息
                resultImageList.add(data.getData().getImage_url().trim());

                fileUploadFlag++;//图片上传成功标识

                if (fileUploadFlag == resultImageList.size()) {//上传成功张数等于上传图片集合的情况下走提问

                    answerAskPresenter.userAskQuestion(userId, courseVideoId, courseSectionId, courseCourseId, coursePackageId, askContent, courseVideoTime, resultImageList);
                }
            } else {
                ToastUtil.showCenterLongText(context, data.getMsg());//提示上传错误信息
            }
        } else {
            ToastUtil.showCenterLongText(context, getResources().getString(R.string.file_uploaderror));//提示上传错误信息
        }
    }

    /**
     * Time:2019-4-19   上午 9:28
     * Detail: TODO  问题上传结果
     */
    @Override
    public void askQuestionResult(int code) {
        if (code == -1) {
            ToastUtil.showBottomShortText(context, getResources().getString(R.string.answer_request_error));
        } else {
            askEdittextContent.clearFocus();
            askEdittextContent.setText("");
        }
    }

    @Override
    public void getKnowList(AnswerKnowListBean data) {
        //TODO nothing
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void showLoadingFinish() {
        dismissPorcess();
    }

    @Override
    public void showError() {
        ToastUtil.showBottomShortText(context, getResources().getString(R.string.answer_request_error));
    }
}
