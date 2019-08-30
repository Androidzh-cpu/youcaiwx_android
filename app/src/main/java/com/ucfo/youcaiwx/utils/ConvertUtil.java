package com.ucfo.youcaiwx.utils;

import android.text.TextUtils;

/**
 * Author: AND
 * Time: 2019-5-24.  下午 4:00
 * FileName: ConvertUtil
 * Description:TODO String类型转换为float、double和int的工具类
 */

public class ConvertUtil {
    //把String转化为float
    public static float convertToFloat(String number, float defaultValue) {
        if (TextUtils.isEmpty(number)) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(number);
        } catch (Exception e) {
            return defaultValue;
        }
    }


    //把String转化为double

    public static double convertToDouble(String number, double defaultValue) {

        if (TextUtils.isEmpty(number)) {

            return defaultValue;

        }

        try {

            return Double.parseDouble(number);

        } catch (Exception e) {

            return defaultValue;

        }


    }


    //把String转化为int

    public static int convertToInt(String number, int defaultValue) {

        if (TextUtils.isEmpty(number)) {

            return defaultValue;

        }

        try {

            return Integer.parseInt(number);

        } catch (Exception e) {

            return defaultValue;

        }

    }


}

