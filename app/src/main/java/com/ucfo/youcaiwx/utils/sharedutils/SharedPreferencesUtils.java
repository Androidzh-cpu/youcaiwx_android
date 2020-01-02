package com.ucfo.youcaiwx.utils.sharedutils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:AND
 * Time: 2019-2-13.  下午 2:06
 * Email:2911743255@qq.com
 * ClassName: SecurePreferencesUtils
 * Package: com.study.studyapp.utils.systemutils
 * Description:加密存储数据
 */
public class SharedPreferencesUtils {
    private static final String LXSP_NAME = "youcaiwx_data";
    private static SharedPreferencesUtils spUtil = null;
    private static SecurePreferences sp = null;

    private SharedPreferencesUtils(Context context) {
        sp = new SecurePreferences(context, "youcaiwx", LXSP_NAME);
    }

    public static SharedPreferencesUtils getInstance(Context context) {
        if (spUtil == null) {
            spUtil = new SharedPreferencesUtils(context);
        }
        return spUtil;
    }

    /**
     * 获取保存的List
     *
     * @param key key
     * @return 对应的Lis集合
     */
    public static <T> List<T> getList(String key, Class<T> cls) {
        List<T> list = new ArrayList<>();
        String json = sp.getString(key, "");
        if (!json.equals("") && json.length() > 0) {
            Gson gson = new Gson();
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (JsonElement elem : array) {
                list.add(gson.fromJson(elem, cls));
            }
        }
        return list;
    }

    /**
     * 用于保存集合
     *
     * @param key  key
     * @param list 集合数据
     * @return 保存结果
     */
    public <T> boolean putListData(String key, List<T> list) {
        boolean result;
        String type = list.get(0).getClass().getSimpleName();
        SharedPreferences.Editor editor = sp.edit();
        JsonArray array = new JsonArray();
        try {
            switch (type) {
                case "Boolean":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Boolean) list.get(i));
                    }
                    break;
                case "Long":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Long) list.get(i));
                    }
                    break;
                case "Float":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Float) list.get(i));
                    }
                    break;
                case "String":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((String) list.get(i));
                    }
                    break;
                case "Integer":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Integer) list.get(i));
                    }
                    break;
                default:
                    Gson gson = new Gson();
                    for (int i = 0; i < list.size(); i++) {
                        JsonElement obj = gson.toJsonTree(list.get(i));
                        array.add(obj);
                    }
                    break;
            }
            editor.putString(key, array.toString());
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    public void clear() {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    public int getInt(final String key, final int defaultValue) {
        //return sp.getInt(key, defaultValue);
        String string = sp.getString(key, String.valueOf(defaultValue));
        return Integer.parseInt(string);
    }

    public void putInt(final String key, final int value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void putInt(final String key, final Integer value) {
        if (null != value) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(key, value);
            editor.commit();
        }
    }

    public long getLong(final String key, final long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public void putLong(final String key, final long value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void putLong(final String key, final Long value) {
        if (null != value) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putLong(key, value);
            editor.commit();
        }
    }

    public float getFloat(final String key, final float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public void putFloat(final String key, final float value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public void putFloat(final String key, final Float value) {
        if (null != value) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putFloat(key, value);
            editor.commit();
        }
    }

    public void putString(final String key, final String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public boolean getBoolean(final String key, final boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public void putBoolean(final String key, final boolean value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putBoolean(final String key, final Boolean value) {
        if (null != value) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }
    }


    public String getString(final String key, final String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public class SharedPreferenceConstant {
        public static final String ACCOUNT = "account";
        public static final String PASSWORD_MD5 = "pwd_md5";
    }


}
