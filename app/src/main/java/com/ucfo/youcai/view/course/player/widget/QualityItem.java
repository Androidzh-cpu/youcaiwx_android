package com.ucfo.youcai.view.course.player.widget;

import android.content.Context;

import com.aliyun.vodplayer.utils.QualityLanguage;

/**
 * Author:29117
 * Time: 2019-4-11.  下午 1:50
 * Email:2911743255@qq.com
 * ClassName: QualityItem
 * Package: com.ucfo.youcai.view.course.player.widget
 * Description:TODO 清晰度列表的项
 * Detail:TODO
 */
public class QualityItem {

    //原始的清晰度
    private String mQuality;
    //显示的文字
    private String mName;

    private QualityItem(String quality, String name) {
        mQuality = quality;
        mName = name;
    }

    public static QualityItem getItem(Context context, String quality, boolean isMts) {
//mts与其他的清晰度格式不一样，
        if (isMts) {
            //这里是getMtsLanguage
            String name = QualityLanguage.getMtsLanguage(context, quality);
            return new QualityItem(quality, name);
        } else {
            //这里是getSaasLanguage
            String name = QualityLanguage.getSaasLanguage(context, quality);
            return new QualityItem(quality, name);
        }
    }

    /**
     * 获取显示的文字
     *
     * @return 清晰度文字
     */
    public String getName() {
        return mName;
    }


}
