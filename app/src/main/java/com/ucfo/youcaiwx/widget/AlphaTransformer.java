package com.ucfo.youcaiwx.widget;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Author: AND
 * Time: 2020-4-7.  上午 10:54
 * Package: com.ucfo.youcaiwx.widget
 * FileName: AlphaTransformer
 * Description:TODO 透明动画
 */
public class AlphaTransformer implements ViewPager.PageTransformer {
    private final float MINALPHA = 0.2F;

    /**
     * position取值特点：
     * * 假设页面从0～1，则：
     * * 第一个页面position变化为[0,-1]
     * * 第二个页面position变化为[1,0]
     *
     * @param position
     */
    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position < -1 || position > 1) {
            page.setAlpha(MINALPHA);
        } else {
            //不透明->半透明
            if (position < 0) {
                //[0,-1]
                page.setAlpha(MINALPHA + (1 + position) * (1 - MINALPHA));
            } else {
                //[1,0]
                // 半透明->不透明
                page.setAlpha(MINALPHA + (1 - position) * (1 - MINALPHA));
            }
        }
    }
}
