package com.ucfo.youcaiwx.view.course.player.utils;

/**
 * Author:29117
 * Time: 2019-4-10.  下午 6:27
 * Email:2911743255@qq.com
 * ClassName: TimeFormater
 * Description:TODO 时间格式化工具类
 */
public class TimeFormater {
    /**
     * 格式化毫秒数为 xx:xx:xx这样的时间格式。
     *
     * @param ms 毫秒数
     * @return 格式化后的字符串
     */
    public static String formatMs(long ms) {
        int seconds = (int) (ms / 1000);
        int finalSec = seconds % 60;
        int finalMin = seconds / 60 % 60;
        int finalHour = seconds / 3600;

        StringBuilder msBuilder = new StringBuilder("");
        if (finalHour > 9) {
            msBuilder.append(finalHour).append(":");
        } else if (finalHour > 0) {
            msBuilder.append("0").append(finalHour).append(":");
        }

        if (finalMin > 9) {
            msBuilder.append(finalMin).append(":");
        } else if (finalMin > 0) {
            msBuilder.append("0").append(finalMin).append(":");
        } else {
            msBuilder.append("00").append(":");
        }

        if (finalSec > 9) {
            msBuilder.append(finalSec);
        } else if (finalSec > 0) {
            msBuilder.append("0").append(finalSec);
        } else {
            msBuilder.append("00");
        }

        return msBuilder.toString();
    }

    /**
     * Description:TimeFormater
     * Time:2019-5-6 下午 2:59
     * Detail:TODO 做题页面计时器
     */
    public static String formatSeconds(int seconds) {
        StringBuffer sb = new StringBuffer();
        sb.append('0').append((seconds / 60) / 60).append(':');
        if ((seconds / 60) % 60 < 10) {
            sb.append('0').append((seconds / 60) % 60).append(':');
        } else {
            sb.append((seconds / 60) % 60).append(':');
        }
        if (seconds % 60 > 9) {
            sb.append(seconds % 60);
        } else {
            sb.append('0').append(seconds % 60);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        long j = 120000;
        int i = (int) j;
        System.out.print("Hello World" + i);
    }

}
