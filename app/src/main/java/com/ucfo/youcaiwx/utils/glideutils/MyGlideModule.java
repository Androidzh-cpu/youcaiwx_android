package com.ucfo.youcaiwx.utils.glideutils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Author: AND
 * Time: 2019-9-16.  下午 2:42
 * Package: com.ucfo.youcaiwx.utils.glideutils
 * FileName: MyGlideModule
 * Description:glide自定义模块
 */
public class MyGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
    }
}
