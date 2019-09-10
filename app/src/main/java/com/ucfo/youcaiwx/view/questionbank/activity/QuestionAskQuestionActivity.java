package com.ucfo.youcaiwx.view.questionbank.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.answer.ImagePickerAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.UploadFileBean;
import com.ucfo.youcaiwx.entity.answer.AnswerKnowListBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.answer.AnswerAskPresenter;
import com.ucfo.youcaiwx.presenter.presenterImpl.upload.IUploadFileView;
import com.ucfo.youcaiwx.presenter.presenterImpl.upload.UploadFilePresenter;
import com.ucfo.youcaiwx.presenter.view.answer.IAnswerAskQuestionView;
import com.ucfo.youcaiwx.utils.glideutils.MiniSizeFilter;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.view.course.player.utils.TimeFormater;
import com.ucfo.youcaiwx.widget.flowlayout.FlowLayout;
import com.ucfo.youcaiwx.widget.flowlayout.TagAdapter;
import com.ucfo.youcaiwx.widget.flowlayout.TagFlowLayout;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Author: AND
 * Time: 2019-5-31 下午 3:27
 * FileName: QuestionAskQuestionActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 题库答疑,提问问题
 */
public class QuestionAskQuestionActivity extends BaseActivity implements IAnswerAskQuestionView, IUploadFileView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.ask_edittext_content)
    EditText askEdittextContent;
    @BindView(R.id.ask_checkphoto)
    Button askCheckphoto;
    @BindView(R.id.ask_imagelist)
    RecyclerView askImagelist;
    @BindView(R.id.ask_content_number)
    TextView askContentNumber;
    @BindView(R.id.ask_videotime)
    TextView askVideotime;
    @BindView(R.id.flowlayout)
    TagFlowLayout flowlayout;
    @BindView(R.id.ask_submit)
    Button askSubmit;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private QuestionAskQuestionActivity context;
    private ArrayList<String> imageList, resultImageList;
    public static final int REQUEST_CODE_CHOOSE = 100;
    private int MAX_IMAGECOUNT = 3;
    private ImagePickerAdapter imagePickerAdapter;
    private AnswerAskPresenter answerAskPresenter;
    private Bundle bundle;
    private int question_id, user_id, fileUploadFlag = 0, course_id;
    private UploadFilePresenter uploadFilePresenter;
    private String askContent, tipsText;
    private int coursePackageId, courseCourseId, courseVideoId, courseSectionId, courseVideoTime;
    private String type;
    private String video_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_question_ask_question;
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

    @SuppressLint("CheckResult")
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        context = QuestionAskQuestionActivity.this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);//用户ID

        //提问问题
        answerAskPresenter = new AnswerAskPresenter(this);
        uploadFilePresenter = new UploadFilePresenter(this);//上传文件
    }

    @Override
    protected void initData() {
        super.initData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        askImagelist.setLayoutManager(linearLayoutManager);
        askImagelist.setHasFixedSize(true);
        askImagelist.setItemAnimator(new DefaultItemAnimator());
        imageList = new ArrayList<>();
        resultImageList = new ArrayList<>();

        bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getString(Constant.TYPE, Constant.TYPE_QUESTION_ASK);//提问类型,默认题库提问
            //TODO 题库答疑需要获取题目的ID
            question_id = bundle.getInt(Constant.QUESTION_ID, 0);
            course_id = bundle.getInt(Constant.COURSE_ID, 0);
            //TODO 课程提问需要 课程包ID,课程ID,视频vio,章节ID,视频节点
            coursePackageId = bundle.getInt(Constant.PACKAGE_ID, 0);//TODO 课程包
            courseCourseId = bundle.getInt(Constant.COURSE_ID, 0);//TODO 课程
            courseVideoId = bundle.getInt(Constant.VIDEO_ID, 0);//TODO 视频vid
            courseSectionId = bundle.getInt(Constant.SECTION_ID, 0);//TODO 章
            courseVideoTime = bundle.getInt(Constant.VIDEO_TIME, 0);//TODO 视频播放节点
            video_title = bundle.getString(Constant.VIDEO_TITLE, "");//TODO 视频播放标题
        } else {
            finish();
        }
        //TODO 提问类型,分为课程和题库
        if (TextUtils.equals(type, Constant.TYPE_COURSE_ASK)) {//课程
            titlebarMidtitle.setText(getResources().getString(R.string.answer_ask));

            askVideotime.setText(TimeFormater.formatMs(courseVideoTime));
            List<String> dataData = new ArrayList<>();
            dataData.add(video_title);
            TagAdapter<String> tagAdapter = new TagAdapter<String>(dataData) {
                @Override
                public View getView(FlowLayout parent, int j, String string) {
                    TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_tagflowlayout, flowlayout, false);
                    textView.setText(string);
                    textView.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_rectangle_corners_blue));
                    textView.setTextColor(ContextCompat.getColor(context, R.color.color_0267FF));
                    return textView;
                }
            };
            flowlayout.setAdapter(tagAdapter);

        } else if (TextUtils.equals(type, Constant.TYPE_QUESTION_ASK)) {//题库
            titlebarMidtitle.setText(getResources().getString(R.string.answer_title_askQuestions2));
            tipsText = getResources().getString(R.string.net_loadingtext);
            answerAskPresenter.getKnowList(question_id);
        }
        //输入字数限制
        askEdittextContent.addTextChangedListener(textWatcher);
        askEdittextContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            if (data != null) {
                askImagelist.setVisibility(askImagelist.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
                List<String> list = Matisse.obtainPathResult(data);
                imageList.addAll(list);
                if (imageList.size() <= 3) {//选择的小于3张
                    MAX_IMAGECOUNT = MAX_IMAGECOUNT - list.size();
                }
                if (imagePickerAdapter == null) {
                    imagePickerAdapter = new ImagePickerAdapter(context, imageList);
                } else {
                    imagePickerAdapter.notifyDataSetChanged();
                }
                askImagelist.setAdapter(imagePickerAdapter);
                imagePickerAdapter.setOnItemClickListener(new ImagePickerAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                    }

                    @Override
                    public void onItemDeleteClick(View view, int position) {
                        imageList.remove(position);
                        MAX_IMAGECOUNT++;
                        imagePickerAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                askImagelist.setVisibility(askImagelist.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
            }
        }
    }

    @OnClick({R.id.ask_checkphoto, R.id.ask_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ask_checkphoto:
                //TODO 选择图片
                SoulPermission.getInstance().checkAndRequestPermissions(
                        Permissions.build(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        new CheckRequestPermissionsListener() {
                            @Override
                            public void onAllPermissionOk(Permission[] allPermissions) {
                                checkPhoto();
                            }

                            @Override
                            public void onPermissionDenied(Permission[] refusedPermissions) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(QuestionAskQuestionActivity.this, R.style.WhiteDialogStyle);
                                builder.setTitle(getResources().getString(R.string.explication));
                                builder.setMessage(getResources().getString(R.string.permission_sdcard2));
                                builder.setPositiveButton(getResources().getString(R.string.donner), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SoulPermission.getInstance().goApplicationSettings();
                                    }
                                });
                                builder.create();
                                builder.show();
                            }
                        });
                break;
            case R.id.ask_submit:
                //TODO 发表问题
                askContent = askEdittextContent.getText().toString().trim();
                if (TextUtils.isEmpty(askContent)) {//输入为空
                    ToastUtil.showBottomShortText(context, getResources().getString(R.string.answer_hinttext));
                    return;
                }
                if (askContent.length() < 5) {//长度不够
                    ToastUtil.showBottomShortText(context, getResources().getString(R.string.answer_hinttext2));
                    return;
                }
                if (!TextUtils.isEmpty(askContent)) {
                    if (imageList != null && imageList.size() > 0) {
                        //TODO 有图的情况下先上传图片再提问
                        File file = new File(imageList.get(0));
                        luban(file, 0);
                    } else {
                        //TODO 没有图片,直接提问
                        tipsText = getResources().getString(R.string.answer_loading);
                        if (TextUtils.equals(type, Constant.TYPE_COURSE_ASK)) {//课程
                            answerAskPresenter.userAskQuestion(user_id, courseVideoId, courseSectionId, courseCourseId,
                                    coursePackageId, askContent, courseVideoTime, null);
                        } else if (TextUtils.equals(type, Constant.TYPE_QUESTION_ASK)) {//题库
                            answerAskPresenter.qustionAskQuestion(user_id, course_id, question_id, askContent, null);
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    //选择相册
    private void checkPhoto() {
        if (imageList.size() < 3) {
            Matisse.from(this)
                    .choose(MimeType.of(MimeType.JPEG, MimeType.PNG), false)
                    .countable(true)
                    .maxSelectable(MAX_IMAGECOUNT)
                    .capture(true) //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvide
                    .captureStrategy(new CaptureStrategy(true, "PhotoProvider"))//参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                    .gridExpectedSize(DensityUtil.dip2px(this, 120))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .theme(R.style.Matisse_Dracula)
                    .imageEngine(new GlideEngine())
                    .autoHideToolbarOnSingleTap(true)
                    .maxOriginalSize(10)
                    .addFilter(new MiniSizeFilter(320, 320, (int) (3 * Filter.K * Filter.K))) // 控制宽高为320*320 以上，大小为 3M 以下
                    .forResult(REQUEST_CODE_CHOOSE);//请求码
        } else {
            ToastUtil.showBottomShortText(context, getResources().getString(R.string.answer_title_maxChoice3));
        }
    }

    //TODO 鲁班压缩
    private void luban(File file, int index) {
        Luban.with(this).load(file).ignoreBy(100).setTargetDir(getPath())
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        uploadFilePresenter.upLoadFile(file, index);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();
    }

    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + Constant.LUBAN_PATH;
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    @Override
    public void getKnowList(AnswerKnowListBean data) {
        if (data != null) {
            List<String> dataData = data.getData();
            TagAdapter<String> tagAdapter = new TagAdapter<String>(dataData) {
                @Override
                public View getView(FlowLayout parent, int j, String string) {
                    TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_tagflowlayout, flowlayout, false);
                    textView.setText(string);
                    int i = j % 3;
                    switch (i) {
                        case 0://余数为0
                            textView.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_rectangle_corners_blue));
                            textView.setTextColor(ContextCompat.getColor(context, R.color.color_0267FF));
                            break;
                        case 1://余数为1
                            textView.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_rectangle_corners_red));
                            textView.setTextColor(ContextCompat.getColor(context, R.color.color_E84342));
                            break;
                        case 2://余数为2
                            textView.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_rectangle_corners_orange));
                            textView.setTextColor(ContextCompat.getColor(context, R.color.color_F99111));
                            break;
                        default:
                            break;
                    }
                    return textView;
                }
            };
            flowlayout.setAdapter(tagAdapter);
        }
    }

    @Override
    public void askQuestionResult(int code) {
        if (code == 200) {
            askEdittextContent.clearFocus();
            askEdittextContent.setText("");
            ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Success));
            finish();
        } else {
            ToastUtil.showBottomShortText(context, getResources().getString(R.string.answer_request_error));
        }
    }

    /**
     * Description:QuestionAskQuestionActivity
     * Time:2019-9-10 下午 1:50
     * Detail:TODO 图片压缩后并上传成功
     */
    @Override
    public void resultUploadFile(UploadFileBean data, int index) {
        if (data != null) {
            if (data.getCode() == 200) {
                resultImageList.add(data.getData().getImage_url().trim());
            } else {
                ToastUtil.showCenterLongText(context, data.getMsg());//提示上传错误信息
            }
        } else {
            //提示上传错误信息
            ToastUtil.showCenterLongText(context, getResources().getString(R.string.file_uploaderror));
        }
        index++;// 0-1 1-2 2-3 3-4
        toNextImage(index);
    }

    /**
     * 继续压缩下一张图片
     */
    private void toNextImage(int position) {
        if (position < imageList.size()) {//1<3  2<3  3==3,
            File file = new File(imageList.get(position));
            luban(file, position);
        } else {
            tipsText = getResources().getString(R.string.answer_loading);
            if (TextUtils.equals(type, Constant.TYPE_COURSE_ASK)) {//课程
                answerAskPresenter.userAskQuestion(user_id, courseVideoId, courseSectionId, courseCourseId, coursePackageId, askContent, courseVideoTime, resultImageList);
            } else if (TextUtils.equals(type, Constant.TYPE_QUESTION_ASK)) {//题库
                answerAskPresenter.qustionAskQuestion(user_id, course_id, question_id, askContent, resultImageList);
            }
        }
    }

    @Override
    public void showLoading() {
        setProcessLoading(tipsText, true);
    }

    @Override
    public void showLoadingFinish() {
        dismissPorcess();
    }

    @Override
    public void showError() {

    }

    @Override
    public void startUploadFile() {
        tipsText = getResources().getString(R.string.answer_loading);
        setProcessLoading(tipsText, true);
    }

    @Override
    public void errorUploadFile() {
        dismissPorcess();
    }


    //TODO 输入文字监听
    private TextWatcher textWatcher = new TextWatcher() {
        private int maxLen = 200; // 最大输入字符

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            askContentNumber.setText(String.format("%s/" + maxLen, s.length()));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
