package com.ucfo.youcaiwx.view.course.player.courseinterface;

/**
 * Author:29117
 * Time: 2019-4-11.  下午 5:23
 * Email:2911743255@qq.com
 * ClassName: ViewAction
 */

import com.ucfo.youcaiwx.view.course.player.widget.AliyunScreenMode;

public interface ViewAction {

    /**
     * 隐藏类型
     */
    public enum HideType {
        /**
         * 正常情况下的隐藏
         */
        Normal,
        /**
         * 播放结束的隐藏，比如出错了
         */
        End
    }

    /**
     * 重置
     */
    public void reset();

    /**
     * 显示
     */
    public void show();

    /**
     * 隐藏
     *
     * @param hideType 隐藏类型
     */
    public void hide(HideType hideType);

    /**
     * 设置屏幕全屏情况
     *
     * @param mode {@link AliyunScreenMode#Small}：小屏. {@link AliyunScreenMode#Full}:全屏
     */
    public void setScreenModeStatus(AliyunScreenMode mode);

}
