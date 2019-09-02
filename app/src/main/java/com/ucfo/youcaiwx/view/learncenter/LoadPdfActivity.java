package com.ucfo.youcaiwx.view.learncenter;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-7-16 下午 4:58
 * FileName: LoadPdfActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 讲义加载页面
 */

public class LoadPdfActivity extends BaseActivity {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.pdfView)
    PDFView pdfView;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    private Bundle bundle;
    private String pdfurl;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deletePdfFile();
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_load_pdf;
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

    @Override
    protected void initData() {
        super.initData();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            pdfurl = bundle.getString(Constant.URL, "");
            title = bundle.getString(Constant.TITLE);

            switchPDF(pdfurl);
            if (!TextUtils.isEmpty(title)) {
                titlebarMidtitle.setText(title);
            } else {
                titlebarMidtitle.setText(getResources().getString(R.string.default_title));
            }
        } else {
            loadinglayout.showEmpty();
        }
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchPDF(pdfurl);
            }
        });
    }

    public void switchPDF(String pdfUrl) {
        deletePdfFile();
        long l = System.currentTimeMillis();
        String mDestFileName = String.valueOf(l);
        File externalFilesDir = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {//API19以上存放在应用内部文件夹
            externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        } else {//低于API19则存储在自定义文件夹下
            externalFilesDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.PDF_PATH);
        }
        String localPath = null;
        if (externalFilesDir != null) {
            localPath = externalFilesDir.getAbsolutePath();
        }
        String finalLocalPath = localPath;
        OkGo.<File>get(pdfUrl)
                .tag(this)
                .execute(new FileCallback(finalLocalPath, mDestFileName) { //文件下载时指定下载的路径以及下载的文件的名
                    @Override
                    public void onStart(com.lzy.okgo.request.base.Request<File, ? extends com.lzy.okgo.request.base.Request> request) {
                        super.onStart(request);
                        loadinglayout.showLoading();
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                        if (response.isSuccessful()) {
                            File file = new File(finalLocalPath, mDestFileName);
                            if (file.exists()) {
                                pdfView.fromFile(file)
                                        .defaultPage(0) //设置默认显示第0页
                                        .enableSwipe(true) //是否允许翻页，默认是允许翻页
                                        .enableAnnotationRendering(true)// 渲染风格（就像注释，颜色或表单）
                                        .swipeHorizontal(false)//pdf文档翻页是否是垂直翻页，默认是左右滑动翻页
                                        .enableAntialiasing(true)// 改善低分辨率屏幕上的渲染
                                        .spacing(10)// 页面间的间距。定义间距颜色，设置背景视图
                                        .autoSpacing(false)
                                        .pageFitPolicy(FitPolicy.WIDTH)
                                        .pageSnap(true) // snap pages to screen boundaries
                                        .pageFling(false) // make a fling change only a single page like ViewPager
                                        .nightMode(false)// toggle night mode夜间模式
                                        .load();
                                loadinglayout.showContent();
                            }
                        }
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<File> response) {
                        super.onError(response);
                        loadinglayout.showError();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }

    public void deletePdfFile() {
        try {
            File externalFilesDir = null;
            String localPath = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {//API19以上存放在应用内部文件夹
                externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            } else {//低于API19则存储在自定义文件夹下
                externalFilesDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.PDF_PATH);
            }
            if (externalFilesDir != null) {
                localPath = externalFilesDir.getAbsolutePath();
            }
            File file = new File(Objects.requireNonNull(localPath));

            if (file.exists()) {//判断文件是否存在
                //如果是文件，遍历删除
                if (file.isDirectory()) {
                    File[] childFile = file.listFiles();
                    for (File f : childFile) {
                        f.delete();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
