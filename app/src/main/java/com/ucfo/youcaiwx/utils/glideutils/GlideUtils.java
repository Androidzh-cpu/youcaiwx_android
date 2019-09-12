package com.ucfo.youcaiwx.utils.glideutils;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Author:AND
 * Time: 2019-2-13.  下午 2:29
 * Email:2911743255@qq.com
 * ClassName: GlideUtils
 * Package: com.study.studyapp.utils.glideutils
 * Description:glide工具类
 * Detail: 自定义了模块或者Generated API后对该app帮助不大,故采用此种简单方法
 * <p>
 * note:Glide4.7加载图片RoundedCorners跟CenterCrop冲突问题解决
 * 假如先调用了centercrop()然后transform后会覆盖掉之前的centercrop
 * 建议
 * Glide.with(mContext)
 * .load(item.getImgUrl())
 * .apply(new RequestOptions()
 * .transforms(new CenterCrop(), new RoundedCorners(4)))
 * .into(imagView)
 */

public class GlideUtils {

    public static void load(Context context, String url, ImageView imageView, RequestOptions options) {
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

}
