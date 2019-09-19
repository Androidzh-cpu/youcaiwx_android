package com.ucfo.youcaiwx.module.course.player.download;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import java.io.File;

/**
 * Author: AND
 * Time: 2019-7-4.  上午 10:58
 * FileName: StorageQueryUtil
 * Description:TODO 手机存储空间
 */
public class StorageQueryUtil {

    /**
     * 获取存储设备总存储空间
     */
    public static String getTotalSize(Context context) {
        File path = Environment.getDataDirectory();
        // 创建StatFs对象
        StatFs stat = new StatFs(path.getPath());
        // 获取每个存储快的大小
        long blockSize = stat.getBlockSize();
        // 获取所有的存储块
        long blockCount = stat.getBlockCount();
        // 获取内部存储的总大小
        long totalSize = blockCount * blockSize;
        // 将long类型转为字符串
        String totalStr = Formatter.formatFileSize(context, totalSize);
        return totalStr;
    }

    /**
     * 获取可用存储空间
     */
    public static String getAvailSize(Context context) {
        File path = Environment.getDataDirectory();
        // 创建StatFs对象
        StatFs stat = new StatFs(path.getPath());
        // 获取每个存储快的大小
        long blockSize = stat.getBlockSize();
        // 获取可用的存储块
        long availableBlocks = stat.getAvailableBlocks();
        // 获取内部存储的可用大小
        long availSize = availableBlocks * blockSize;
        // 将long类型转为字符串
        String availStr = Formatter.formatFileSize(context, availSize);
        return availStr;
    }
}
