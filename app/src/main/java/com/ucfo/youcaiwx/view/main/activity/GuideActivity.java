package com.ucfo.youcaiwx.view.main.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.widget.banner.BGABanner;
import com.ucfo.youcaiwx.widget.banner.BGALocalImageSize;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-9-9 下午 5:42
 * Package: com.ucfo.youcaiwx.view.main.activity
 * FileName: GuideActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 引导页
 */
public class GuideActivity extends BaseActivity {
    @BindView(R.id.banner_guide_background)
    BGABanner bannerGuideBackground;
    @BindView(R.id.banner_guide_foreground)
    BGABanner bannerGuideForeground;
    @BindView(R.id.tv_guide_skip)
    TextView tvGuideSkip;
    @BindView(R.id.btn_guide_enter)
    Button btnGuideEnter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
        bannerGuideBackground.setBackgroundResource(android.R.color.white);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_guide;
    }

    @Override
    protected void setListener() {
        super.setListener();

        /**
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */
        bannerGuideForeground.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

                SharedPreferencesUtils.getInstance(GuideActivity.this).putBoolean(Constant.FIRST_LOGIN, true);
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        // 设置数据源
        bannerGuideBackground.setData(localImageSize, ImageView.ScaleType.FIT_XY,
                R.color.colorWhite, R.color.colorWhite, R.color.colorWhite, R.color.colorWhite);

        bannerGuideForeground.setData(localImageSize, ImageView.ScaleType.FIT_XY,
                R.mipmap.guide_01, R.mipmap.guide_02, R.mipmap.guide_03, R.mipmap.guide_04);
    }
}
