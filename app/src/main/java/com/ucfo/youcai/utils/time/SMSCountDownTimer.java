package com.ucfo.youcai.utils.time;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucfo.youcai.R;

/**
 * Author:29117
 * Time: 2019-3-26.  上午 11:23
 * Email:2911743255@qq.com
 * ClassName: SMSCountDownTimer
 * Package: com.ucfo.youcai.utils
 * Description:TODO  登录页的倒计时按钮
 * Detail:
 */
public class SMSCountDownTimer extends CountDownTimer {

    private Button button;
    private TextView tvVoiceCode;
    private boolean isChangeColor = true;//默认改变字体颜色

    public void setVoiceLinear(LinearLayout voiceLinear) {
        this.voiceLinear = voiceLinear;
    }

    private LinearLayout voiceLinear;

    public SMSCountDownTimer(long millisInFuture, long countDownInterval, Button button, TextView tvVoiceCode) {
        super(millisInFuture, countDownInterval);
        this.button = button;
        this.tvVoiceCode = tvVoiceCode;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        button.setClickable(false); //设置不可点击
        button.setEnabled(false); //设置不可点击
        button.setText(String.valueOf("剩余" + millisUntilFinished / 1000 + "秒"));  //设置倒计时时间
        button.setBackgroundResource(R.drawable.bg_graylogin_btn);
        button.setTextColor(Color.parseColor("#333333"));
        button.setText(button.getText().toString());
        if (tvVoiceCode != null) {
            tvVoiceCode.setEnabled(false);
            tvVoiceCode.setClickable(false);
        }
    }

    @Override
    public void onFinish() {
        button.setText("获取验证码");
        button.setBackgroundResource(R.drawable.bg_login_btn);
        button.setClickable(true);//重新获得点击
        button.setEnabled(true);
        button.setTextColor(Color.parseColor("#FFFFFF"));  //还原字体颜色

        if (tvVoiceCode != null) {
            tvVoiceCode.setEnabled(true);
            tvVoiceCode.setClickable(true);
        }
        if (voiceLinear != null) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.01f, 1.0f);
            alphaAnimation.setDuration(800);
            alphaAnimation.setFillAfter(true);
            voiceLinear.setAnimation(alphaAnimation);
            alphaAnimation.start();
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    voiceLinear.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    }

    public void isChangeColor(boolean isChangeColor) {
        this.isChangeColor = isChangeColor;
    }
}

