package com.ucfo.youcaiwx.module.user.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.callbcak.CheckRequestPermissionListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.UploadFileBean;
import com.ucfo.youcaiwx.entity.address.StateStatusBean;
import com.ucfo.youcaiwx.entity.user.UserInfoBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.upload.IUploadFileView;
import com.ucfo.youcaiwx.presenter.presenterImpl.upload.UploadFilePresenter;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.UserInfoPresenter;
import com.ucfo.youcaiwx.presenter.view.user.IUserInfoView;
import com.ucfo.youcaiwx.utils.glideutils.GlideEngine;
import com.ucfo.youcaiwx.utils.glideutils.GlideImageLoader;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.glideutils.MiniSizeFilter;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.dialog.TakePhotoDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Author: AND
 * Time: 2019-6-10 下午 3:01
 * FileName: PersonnelSettingActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 个人设置
 */
public class PersonnelSettingActivity extends BaseActivity implements IUserInfoView, IUploadFileView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.user_icon)
    CircleImageView userIcon;
    @BindView(R.id.btn_userIcon)
    LinearLayout btnUserIcon;
    @BindView(R.id.user_nickname)
    TextView userNickname;
    @BindView(R.id.btn_userNickname)
    LinearLayout btnUserNickname;
    @BindView(R.id.radiobtn_man)
    RadioButton radiobtnMan;
    @BindView(R.id.radiobtn_woman)
    RadioButton radiobtnWoman;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.btn_userAddress)
    LinearLayout btnUserAddress;
    @BindView(R.id.btn_phone)
    LinearLayout btnPhone;
    private PersonnelSettingActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private UserInfoPresenter userInfoPresenter;
    private String username;
    private Transferee transferee;
    private int REQUEST_CODE_CHOOSE101 = 101, REQUEST_CODE_CHOOSE102 = 102, user_id, type;
    private ImagePicker imagePicker;
    private UploadFilePresenter uploadFilePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        userInfoPresenter.getUserInfo(user_id);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_personnel_setting;
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
        titlebarMidtitle.setText(getResources().getString(R.string.mine_personnelsetting));
        titlebarRighttitle.setVisibility(View.GONE);
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
    protected void initData() {
        super.initData();
        context = PersonnelSettingActivity.this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        userInfoPresenter = new UserInfoPresenter(this);
        uploadFilePresenter = new UploadFilePresenter(this);
        transferee = Transferee.getDefault(context);
    }

    @OnClick({R.id.btn_userIcon, R.id.btn_userNickname, R.id.btn_userAddress, R.id.radiobtn_man, R.id.radiobtn_woman, R.id.btn_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_userIcon://TODO 头像
                SoulPermission.getInstance().checkAndRequestPermission(Manifest.permission.CAMERA, new CheckRequestPermissionListener() {
                    @Override
                    public void onPermissionOk(Permission permission) {
                        takePhoto();
                    }

                    @Override
                    public void onPermissionDenied(Permission permission) {
                        //用户第一次拒绝了权限且没有勾选"不再提示"的情况下这个值为true，此时告诉用户为什么需要这个权限。
                        if (permission.shouldRationale()) {
                            new AlertDialog.Builder(context)
                                    .setTitle(getResources().getString(R.string.explication))
                                    .setMessage(getResources().getString(R.string.permission_camera))
                                    .setPositiveButton(getResources().getString(R.string.donner), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //用户确定以后，重新执行请求原始流程
                                            SoulPermission.getInstance().goApplicationSettings();
                                        }
                                    }).create().show();
                        } else {
                            ToastUtil.showBottomShortText(context, getResources().getString(R.string.permission_explication));
                        }
                    }
                });
                break;
            case R.id.btn_userNickname://TODO 昵称
                Bundle bundle = new Bundle();
                bundle.putString(Constant.NICKNAME, userNickname.getText().toString());
                startActivity(ModifyNameActivity.class, bundle);
                break;
            case R.id.btn_userAddress://TODO 我的地址
                startActivity(UserAddressActivity.class, null);
                break;
            case R.id.radiobtn_man://todo 男
                type = 3;
                userInfoPresenter.retoucheInfo(user_id, type, String.valueOf(1));
                break;
            case R.id.radiobtn_woman://todo 女
                type = 3;
                userInfoPresenter.retoucheInfo(user_id, type, String.valueOf(2));
                break;
            case R.id.btn_phone://todo 手机
                /*Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constant.INDEX, 1);
                startActivity(intent);*/
                break;
            default:
                break;
        }
    }

    //TODO 调试
    private void takePhoto() {
        new TakePhotoDialog(context).builder()
                .setCanceledOnTouchOutside(true)
                .setCancelable(true)
                .setCaremaButtonClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imagePicker = ImagePicker.getInstance();
                        imagePicker.setImageLoader(new GlideImageLoader());
                        imagePicker.setShowCamera(true);
                        imagePicker.setCrop(false);
                        Intent intent = new Intent(PersonnelSettingActivity.this, ImageGridActivity.class);
                        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                        startActivityForResult(intent, REQUEST_CODE_CHOOSE101);
                    }
                })
                .setPhotoButtonClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Matisse.from(context)
                                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG), false)
                                .countable(true)
                                .maxSelectable(1)
                                .gridExpectedSize(DensityUtil.dip2px(context, 120))
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                .thumbnailScale(0.85f)
                                .theme(R.style.Matisse_Dracula)
                                .imageEngine(new GlideEngine())
                                .addFilter(new MiniSizeFilter(320, 320, (int) (3 * Filter.K * Filter.K)))
                                .forResult(REQUEST_CODE_CHOOSE102);
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {//相机
            if (data != null) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    File file = new File(images.get(0).path);
                    luban(file);
                }
            } else {
                Toast.makeText(this, getResources().getString(R.string.holder_nodata), Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQUEST_CODE_CHOOSE102 && resultCode == RESULT_OK) {//调用相册
            List<Uri> uris = Matisse.obtainResult(data);
            startPhotoZoom(uris.get(0));
        }
        if (requestCode == 99 && resultCode == RESULT_OK) {//系统剪裁
            File file = null;//图片地址
            try {
                file = new File(new URI(uriClipUri.toString()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            uploadFilePresenter.upLoadFile(file);
        }
    }

    private Uri uriClipUri;

    public void startPhotoZoom(Uri uri) {
        //com.android.camera.action.CROP，这个action是调用系统自带的图片裁切功能
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");//裁剪的图片uri和图片类型
        intent.putExtra("crop", "circle");//设置允许裁剪，如果不设置，就会跳过裁剪的过程，还可以设置putExtra("crop", "circle")
        intent.putExtra("aspectX", 1);//裁剪框的 X 方向的比例,需要为整数
        intent.putExtra("aspectY", 1);//裁剪框的 Y 方向的比例,需要为整数
        intent.putExtra("outputX", 600);//返回数据的时候的X像素大小。
        intent.putExtra("outputY", 600);//返回数据的时候的Y像素大小。
        intent.putExtra("scale", true);// 不知道有啥用。可能会保存一个比例值 需要相关文档啊
        //uriClipUri为Uri类变量，实例化uriClipUri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //如果是7.0的相册
            //设置裁剪的图片地址Uri
            uriClipUri = Uri.parse("file://" + "/" + getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/" + "clip.jpg");
        } else {
            uriClipUri = Uri.parse("file://" + "/" + getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/" + "clip.jpg");
        }
        //Android 对Intent中所包含数据的大小是有限制的，一般不能超过 1M，否则会使用缩略图 ,所以我们要指定输出裁剪的图片路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriClipUri);
        intent.putExtra("return-data", false);//是否将数据保留在Bitmap中返回
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//输出格式，一般设为Bitmap格式及图片类型
        intent.putExtra("noFaceDetection", true);//人脸识别功能
        startActivityForResult(intent, 99);//裁剪完成的标识
    }

    private void luban(File file) {
        Luban.with(this)
                .load(file)
                .ignoreBy(200)
                .setTargetDir(getPath())
                .setFocusAlpha(false)
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
    public void getUserInfo(UserInfoBean bean) {
        if (bean != null) {
            if (bean.getData() != null) {
                UserInfoBean.DataBean dataBean = bean.getData();
                String head = dataBean.getHead();
                username = dataBean.getUsername();
                String mobile = dataBean.getMobile();
                int sex = dataBean.getSex();
                if (TextUtils.isEmpty(head)) {
                    userIcon.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_headdefault));
                } else {
                    RequestOptions requestOptions = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.mipmap.banner_default)
                            .error(R.mipmap.image_loaderror)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                    GlideUtils.load(context, head, userIcon, requestOptions);

                    TransferConfig headConfig = TransferConfig.build()
                            .setMissPlaceHolder(R.mipmap.banner_default)
                            .setErrorPlaceHolder(R.mipmap.banner_default)
                            .setProgressIndicator(new ProgressPieIndicator())
                            .setIndexIndicator(new NumberIndexIndicator())
                            .setJustLoadHitImage(true)
                            .bindImageView(userIcon, head);
                    userIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            transferee.apply(headConfig).show();
                        }
                    });
                }

                if (!TextUtils.isEmpty(username)) {//todo 昵称
                    userNickname.setText(username);
                } else {
                    userNickname.setText("");
                }
                if (!TextUtils.isEmpty(mobile)) {//todo 手机
                    userPhone.setText(mobile);
                } else {
                    userPhone.setText("");
                }
                switch (sex) {
                    case 1://man
                        radiobtnMan.setChecked(true);
                        break;
                    case 2://woman
                        radiobtnWoman.setChecked(true);
                        break;
                    default://default
                        radiobtnMan.setChecked(false);
                        radiobtnWoman.setChecked(false);
                        break;
                }
            }
        }
    }

    @Override
    public void retouceResult(StateStatusBean result) {
        if (result != null) {
            if (result.getData() != null) {
                int state = result.getData().getState();
                switch (state) {
                    case 1:
                        ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Success));
                        if (type == 1) {
                            userInfoPresenter.getUserInfo(user_id);
                        }
                        break;
                    case 0:
                    default:
                        ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
                        break;
                }
            } else {
                ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
            }
        } else {
            ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
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

    }

    @Override
    public void startUploadFile() {
        setProcessLoading(null, true);
    }

    @Override
    public void errorUploadFile() {
        dismissPorcess();
    }

    @Override
    public void resultUploadFile(UploadFileBean data, int index) {
        if (data != null) {
            if (data.getCode() == 200) {
                String newHeadUrl = data.getData().getImage_url();
                type = 1;
                userInfoPresenter.retoucheInfo(user_id, type, newHeadUrl);
            } else {
                ToastUtil.showCenterLongText(context, data.getMsg());//提示上传错误信息
            }
        } else {
            ToastUtil.showCenterLongText(context, getResources().getString(R.string.file_uploaderror));//提示上传错误信息
        }

    }
}
