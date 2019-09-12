package com.example.dialogtest.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 * Author: AND
 * Time: 2019-9-12.  下午 2:51
 * Package: com.example.dialogtest.utils
 * FileName: MyGlideModule
 * Description:TODO glide自定义模块
 */
public class MyGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context));
        RequestOptions requestOptions = new RequestOptions()
                .skipMemoryCache(true) //不使用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        builder.setDefaultRequestOptions(requestOptions);
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

    }
}
