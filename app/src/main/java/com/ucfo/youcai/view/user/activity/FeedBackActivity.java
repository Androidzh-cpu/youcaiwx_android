package com.ucfo.youcai.view.user.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;
import com.ucfo.youcai.R;
import com.ucfo.youcai.adapter.answer.ImagePickerAdapter;
import com.ucfo.youcai.base.BaseActivity;
import com.ucfo.youcai.common.ApiStores;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.UploadFileBean;
import com.ucfo.youcai.presenter.presenterImpl.upload.IUploadFileView;
import com.ucfo.youcai.presenter.presenterImpl.upload.UploadFilePresenter;
import com.ucfo.youcai.utils.glideutils.MiniSizeFilter;
import com.ucfo.youcai.utils.systemutils.DensityUtil;
import com.ucfo.youcai.utils.systemutils.StatusbarUI;
import com.ucfo.youcai.utils.toastutils.ToastUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.json.JSONException;
import org.json.JSONObject;

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
 * Time: 2019-6-27 上午 9:38
 * Package: com.ucfo.youcai.view.user.activity
 * FileName: FeedBackActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 意见反馈
 */

public class FeedBackActivity extends BaseActivity implements IUploadFileView {

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
    @BindView(R.id.edit_connect)
    EditText editConnect;
    @BindView(R.id.ask_submit)
    Button askSubmit;
    private FeedBackActivity context;
    private ArrayList<String> imageList, resultImageList;
    public static final int REQUEST_CODE_CHOOSE = 100;
    private int MAX_IMAGECOUNT = 3, fileUploadFlag = 0;
    private ImagePickerAdapter imagePickerAdapter;
    private UploadFilePresenter uploadFilePresenter;
    private String askContent, askConnect, tipsText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
        titlebarMidtitle.setText(getResources().getString(R.string.mine_feedback));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        editConnect.setInputType(InputType.TYPE_CLASS_PHONE);//电话
        editConnect.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);//邮件
    }

    @Override
    protected void initData() {
        super.initData();
        context = FeedBackActivity.this;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        askImagelist.setLayoutManager(linearLayoutManager);
        askImagelist.setHasFixedSize(true);
        askImagelist.setItemAnimator(new DefaultItemAnimator());
        imageList = new ArrayList<>();
        resultImageList = new ArrayList<>();

        uploadFilePresenter = new UploadFilePresenter(this);//上传文件
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

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
                SoulPermission.getInstance().checkAndRequestPermissions(
                        Permissions.build(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        new CheckRequestPermissionsListener() {
                            @Override
                            public void onAllPermissionOk(Permission[] allPermissions) {
                                checkPhoto();
                            }

                            @Override
                            public void onPermissionDenied(Permission[] refusedPermissions) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.WhiteDialogStyle);
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
                askContent = askEdittextContent.getText().toString().trim();
                askConnect = editConnect.getText().toString().trim();
                if (TextUtils.isEmpty(askContent)) {
                    ToastUtil.showBottomShortText(context, getResources().getString(R.string.mine_feedbackToast1));
                    return;
                }
                if (TextUtils.isEmpty(askConnect)) {
                    ToastUtil.showBottomShortText(context, getResources().getString(R.string.mine_feedbackToast2));
                    return;
                }

                if (!TextUtils.isEmpty(askContent)) {
                    if (imageList != null && imageList.size() > 0) {//TODO 先上传图片再提问
                        if (imageList.size() == 1) {//TODO  单张上传
                            File file = new File(imageList.get(0));
                            luban(file);
                        } else {//TODO 循环上传图片
                            for (int i = 0; i < imageList.size(); i++) {
                                File file = new File(imageList.get(i));
                                luban(file);
                            }
                        }
                    } else {//TODO 直接提问
                        tipsText = getResources().getString(R.string.answer_loading);
                        commitFeedback(null, askContent, askConnect);
                    }
                }
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
                    .addFilter(new MiniSizeFilter(320, 320, (int) (3 * Filter.K * Filter.K)))
                    .forResult(REQUEST_CODE_CHOOSE);//请求码
        } else {
            ToastUtil.showBottomShortText(context, getResources().getString(R.string.answer_title_maxChoice3));
        }
    }

    //TODO 鲁班压缩
    private void luban(File file) {
        Luban.with(this)
                .load(file)
                .ignoreBy(100)
                .setTargetDir(getPath())
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
                        uploadFilePresenter.upLoadFile(file);
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
    public void startUploadFile() {
        tipsText = getResources().getString(R.string.answer_loading);
        setProcessLoading(tipsText, true);
    }

    @Override
    public void errorUploadFile() {
        dismissPorcess();
    }

    @Override
    public void resultUploadFile(UploadFileBean data) {
        if (data != null) {
            if (data.getCode() == 200) {
                resultImageList.add(data.getData().getImage_url().trim());
                fileUploadFlag++;
                if (fileUploadFlag == imageList.size()) {//上传成功张数等于上传图片集合的情况下走提问
                    tipsText = getResources().getString(R.string.answer_loading);
                    commitFeedback(resultImageList, askContent, askConnect);
                }
            } else {
                ToastUtil.showCenterLongText(context, data.getMsg());//提示上传错误信息
            }
        } else {
            ToastUtil.showCenterLongText(context, getResources().getString(R.string.file_uploaderror));//提示上传错误信息
        }

    }

    public void commitFeedback(ArrayList<String> imageList, String content, String connect) {
        String questionImage = "";
        if (imageList != null && imageList.size() > 0) {//有图片切不止一张
            for (String item : imageList) {
                // 把列表中的每条数据用逗号分割开来，然后拼接成字符串
                questionImage += item + ",";
            }
            // 去掉最后一个逗号
            questionImage = questionImage.substring(0, questionImage.length() - 1);
        }
        OkGo.<String>post(ApiStores.USER_FEEDBACK)
                .params(Constant.CONTENT, content)
                .params("image", questionImage)
                .params("information", connect)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        setProcessLoading(tipsText, true);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        askQuestionResult(-1);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissPorcess();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (body != null && !body.equals("")) {
                            try {
                                JSONObject object = new JSONObject(body);
                                int code = object.optInt(Constant.CODE);//状态码
                                if (code == 200) {
                                    askQuestionResult(code);
                                } else {
                                    askQuestionResult(-1);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            askQuestionResult(-1);
                        }
                    }
                });

    }

    public void askQuestionResult(int code) {
        if (code == 200) {
            ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Success));
            finish();
        } else {
            ToastUtil.showBottomShortText(context, getResources().getString(R.string.answer_request_error));
        }
    }
}
