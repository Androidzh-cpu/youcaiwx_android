package com.ucfo.youcai.utils.glideutils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Author:AND
 * Time: 2019-2-13.  下午 2:54
 * Email:2911743255@qq.com
 * ClassName: GlideRoundTransform
 * Package: com.study.studyapp.utils.glideutils
 * Description:TODO 将图片转换为圆角图片,角度可自定义
 * Detail:
 * TODO 例如:
 * Glide.with(context)
 * .load(coverURL)
 * .asBitmap()
 * .placeholder(R.mipmap.banner_default)
 * .transform(new CenterCrop(context), new GlideRoundTransform(context, 4))
 * .error(R.mipmap.image_loaderror)
 * .dontAnimate()
 * .skipMemoryCache(false)
 * .diskCacheStrategy(DiskCacheStrategy.ALL)
 * .priority(Priority.HIGH)
 * .into(holder.mCourseImageItem);
 */


public class GlideRoundTransform extends BitmapTransformation {

    private static float radius = 0f;

    public GlideRoundTransform(Context context) {
        this(context, 4);
    }

    public GlideRoundTransform(Context context, int dp) {
        super(context);
        radius = Resources.getSystem().getDisplayMetrics().density * dp;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) {
            return null;
        }
//返回一个正好匹配给定宽、高和配置的只包含透明像素的Bitmap
        // 如果BitmapPool中找不到这样的Bitmap，就返回null
        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        //当返回null 时,创建给定宽、高和配置的新位图
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        // 设置shader
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        //抗锯齿
        paint.setAntiAlias(true);
        //画矩形
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        //绘制圆角矩形   (RectF对象,x方向上的圆角半径,y方向上的圆角半径,绘制时所使用的画笔)
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName() + Math.round(radius);
    }
}
