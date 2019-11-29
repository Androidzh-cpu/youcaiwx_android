package com.ucfo.youcaiwx.utils.contextutils;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Author: AND
 * Time: 2019-11-29.  上午 10:29
 * Package: com.ucfo.youcaiwx.utils.contextutils
 * FileName: ApplicationContextProvider
 * Description:TODO 全局无侵入获取context
 */
public class ApplicationContextProvider extends ContentProvider {
    @SuppressLint("StaticFieldLeak")
    static Context mContext;

    @Override
    public boolean onCreate() {
        mContext = getContext();
        //初始化全局Context提供者
        ContextProvider.get().getContext();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
