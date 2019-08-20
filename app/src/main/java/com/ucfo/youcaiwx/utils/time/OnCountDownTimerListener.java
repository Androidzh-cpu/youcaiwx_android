package com.ucfo.youcaiwx.utils.time;

/**
 * Author:29117
 * Time: 2019-4-29.  下午 3:37
 * Email:2911743255@qq.com
 * ClassName: OnCountDownTimerListener
 * Description:TODO 倒计时监听
 */
public interface  OnCountDownTimerListener {

    void onTick(long millisUntilFinished);

    void onFinish();

}
