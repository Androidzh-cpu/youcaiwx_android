package com.ucfo.youcai.widget.tab;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Author:29117
 * Time: 2019-3-29.  下午 4:07
 * Email:2911743255@qq.com
 * ClassName: UnreadMsgUtils
 * Package: com.ucfo.youcai.utils.tab
 * Description:
 * Detail:
 * * 未读消息提示View,显示小红点或者带有数字的红点:
 * <p>
 * 数字一位,圆
 * <p>
 * 数字两位,圆角矩形,圆角是高度的一半
 * <p>
 * 数字超过两位,显示99+
 */
public class UnreadMsgUtils {

    public static void show(MsgView msgView, int num) {

        if (msgView == null) {

            return;

        }

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) msgView.getLayoutParams();

        DisplayMetrics dm = msgView.getResources().getDisplayMetrics();

        msgView.setVisibility(View.VISIBLE);

        if (num <= 0) {//圆点,设置默认宽高

            msgView.setStrokeWidth(0);

            msgView.setText("");



            lp.width = (int) (5 * dm.density);

            lp.height = (int) (5 * dm.density);

            msgView.setLayoutParams(lp);

        } else {

            lp.height = (int) (18 * dm.density);

            if (num > 0 && num < 10) {//圆

                lp.width = (int) (18 * dm.density);

                msgView.setText(num + "");

            } else if (num > 9 && num < 100) {//圆角矩形,圆角是高度的一半,设置默认padding

                lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT;

                msgView.setPadding((int) (6 * dm.density), 0, (int) (6 * dm.density), 0);

                msgView.setText(num + "");

            } else {//数字超过两位,显示99+

                lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT;

                msgView.setPadding((int) (6 * dm.density), 0, (int) (6 * dm.density), 0);

                msgView.setText("99+");

            }

            msgView.setLayoutParams(lp);

        }

    }



    public static void setSize(MsgView rtv, int size) {

        if (rtv == null) {

            return;

        }

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rtv.getLayoutParams();

        lp.width = size;

        lp.height = size;

        rtv.setLayoutParams(lp);

    }
}
