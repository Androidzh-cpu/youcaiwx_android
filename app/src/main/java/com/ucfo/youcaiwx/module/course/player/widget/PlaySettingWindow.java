package com.ucfo.youcaiwx.module.course.player.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.widget.customview.SwitchView;

/**
 * Author:29117
 * Time: 2019-4-15.  上午 10:17
 * Email:2911743255@qq.com
 * ClassName: PlaySettingWindow
 */
public class PlaySettingWindow {
    private boolean continuousPlayState;
    private Context context;
    private float currentScreenSize;
    private RadioGroup rgScreenSize;
    private final PopupWindow popupWindow;
    private final SwitchView crirlePlayBtn;

    public void setCurrentScreenSize(float currentScreenSize) {
        this.currentScreenSize = currentScreenSize;
        //设置当前选中的视频的比例
        if (rgScreenSize != null) {
            if (currentScreenSize == Constant.PERCENT_50) {
                //半屏
                rgScreenSize.check(R.id.rb_screensize_50);
            } else if (currentScreenSize == Constant.PERCENT_75) {
                //四分之三屏
                rgScreenSize.check(R.id.rb_screensize_75);
            } else if (currentScreenSize == Constant.PERCENT_100) {
                //原始比例
                rgScreenSize.check(R.id.rb_screensize_100);
            } else {
                rgScreenSize.check(R.id.rb_screensize_100);
            }
        }
    }

    public PlaySettingWindow(Context context, int height, boolean continuousPlay) {
        this.context = context;
        this.continuousPlayState = continuousPlay;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_alivc_showmore, null);
        rgScreenSize = view.findViewById(R.id.rg_screensize);
        crirlePlayBtn = view.findViewById(R.id.switch_cricleplay);

        popupWindow = new PopupWindow(view, height * 2 / 3, height);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.argb(200, 0, 0, 0)));
        //阻止导航栏弹出
        popupWindow.setClippingEnabled(false);
        crirlePlayBtn.setOpened(continuousPlayState);//设置选中状态
    }

    /**
     * 尺寸调节
     */
    public void setScreenSizeCheckLister(RadioGroup.OnCheckedChangeListener listener) {
        rgScreenSize.setOnCheckedChangeListener(listener);
    }

    /**
     * 是否连续播放
     */
    public void setSwitchViewCheckLister(SwitchView.OnStateChangedListener listener) {
        crirlePlayBtn.setOnStateChangedListener(listener);
    }

    public void setPopupWindowDissmissLister(PopupWindow.OnDismissListener listener) {
        popupWindow.setOnDismissListener(listener);
    }

    /*
     * 指定位置显示
     */
    public void showAsDropDown(View parent) {
        popupWindow.setAnimationStyle(R.style.PopupWindowRightFade);
        popupWindow.showAtLocation(parent, Gravity.RIGHT | Gravity.CENTER, 0, 0);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
    }

    public void dissMiss() {
        popupWindow.dismiss();
    }
}
