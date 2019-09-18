package com.ucfo.youcaiwx.utils.glideutils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.zhihu.matisse.engine.ImageEngine;

/**
 * Author: AND
 * Time: 2019-9-14.  下午 9:36
 * Package: com.ucfo.youcaiwx.utils.glideutils
 * FileName: GlideEngine
 * Description:
 * 解决Glide使用版本不一致导致触发的异常，由于Glide4.0之后Api的调用方式有了一些更改，
 * 所以之前的一些Api调用方式则会出错。 关于Glide 4.0之后Api调用方式的改动可以参考官方文档
 */
public class GlideEngine implements ImageEngine {
    /**
     * 普通图片预览图
     */
    @Override
    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeholder)//这里可自己添加占位图
                .override(resize, resize);
        Glide.with(context)
                .asBitmap()  // some .jpeg files are actually gif
                .load(uri)
                .apply(options)
                .into(imageView);
    }

    /**
     * Description:GlideEngine
     * Time:2019-9-18 下午 4:21
     * Detail:TODO gif图预览图
     */
    @Override
    public void loadGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeholder)//这里可自己添加占位图
                .override(resize, resize);
        Glide.with(context)
                .asGif()  // some .jpeg files are actually gif
                .load(uri)
                .apply(options)
                .into(imageView);
    }

    /**
     * Description:GlideEngine
     * Time:2019-9-18 下午 4:21
     * Detail:TODO 图片放大预览
     */
    @Override
    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        RequestOptions options = new RequestOptions()
                .override(resizeX, resizeY)
                .priority(Priority.HIGH);
        Glide.with(context)
                .load(uri)
                .apply(options)
                .into(imageView);
    }

    /**
     * Description:GlideEngine
     * Time:2019-9-18 下午 4:21
     * Detail:TODO  gif图放大预览
     */
    @Override
    public void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        RequestOptions options = new RequestOptions()
                .override(resizeX, resizeY)
                .priority(Priority.HIGH);
        Glide.with(context)
                .asGif()  // some .jpeg files are actually gif
                .load(uri)
                .apply(options)
                .into(imageView);
    }

    @Override
    public boolean supportAnimatedGif() {
        return true;
    }

}
