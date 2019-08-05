package com.ucfo.youcai.view.course.player.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.ucfo.youcai.R;
import com.ucfo.youcai.widget.customview.SwitchView;

/**
 * Author:29117
 * Time: 2019-4-15.  上午 10:17
 * Email:2911743255@qq.com
 * ClassName: PlaySettingWindow
 * Package: com.ucfo.youcai.view.course.player.widget
 * Description:TODO
 * Detail:TODO
 */
public class PlaySettingWindow {
    private boolean continuousPlayState;
    private Context context;
    private double currentScreenSize;
    private RadioGroup rgScreenSize;
    private final PopupWindow popupWindow;
    private final SwitchView crirlePlayBtn;

    public PlaySettingWindow(Context context, int height, double currentScreenSize, boolean continuousPlay) {
        this.context = context;
        this.currentScreenSize = currentScreenSize;
        this.continuousPlayState = continuousPlay;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_alivc_showmore, null);
        rgScreenSize = view.findViewById(R.id.rg_screensize);
        crirlePlayBtn = view.findViewById(R.id.switch_cricleplay);

        popupWindow = new PopupWindow(view, height * 2 / 3, height);
        //popupWindow.setBackgroundDrawable(new ColorDrawable(Color.argb(178, 0, 0, 0)));
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.argb(200, 0, 0, 0)));
        popupWindow.setClippingEnabled(false);//阻止导航栏弹出
        crirlePlayBtn.setOpened(continuousPlayState);//设置选中状态
        //设置当前选中的视频的比例
        if (currentScreenSize == 0.5) {
            rgScreenSize.check(R.id.rb_screensize_50);
        } else if (currentScreenSize == 0.75) {
            rgScreenSize.check(R.id.rb_screensize_75);
        } else if (currentScreenSize == 1) {
            rgScreenSize.check(R.id.rb_screensize_100);
        } else {
            rgScreenSize.check(R.id.rb_screensize_100);
        }
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
