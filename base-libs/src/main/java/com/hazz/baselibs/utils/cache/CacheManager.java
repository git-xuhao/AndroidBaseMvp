package com.hazz.baselibs.utils.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.hazz.baselibs.utils.LogUtils;

import java.io.Serializable;

/**
 * @author xuhao
 * @desc
 * @date 2018/3/6 下午12:01.
 */

public class CacheManager {

    public static final String TAG="CacheManager";
    private static ACache mACache = null;

    public static void init(Context context) {
        mACache = ACache.get(context);
    }

    public static ACache getInStance() {
        return mACache;
    }

    /**
     * 存重要信息
     * @param key
     * @param value
     * @param saveTime
     */
    public static void putString(String key, String value, int saveTime) {

        if (TextUtils.isEmpty(key)) {
            LogUtils.e(TAG, "key is null");
            return;
        }
        if (TextUtils.isEmpty(value)){
            LogUtils.e(TAG, "value is null");
            return;
        }

        mACache.put(key, value, saveTime);
    }

    /**
     * 存重要信息
     * @param key
     * @param value
     */
    public static void putString(String key, String value) {
        if (TextUtils.isEmpty(key)) {
            LogUtils.e(TAG, "key is null");
            return;
        }
        if (TextUtils.isEmpty(value)){
            LogUtils.e(TAG, "value is null");
            return;
        }

        mACache.put(key, value);
    }

    /**
     * 取重要信息
     * @param key
     * @return
     */
    public static String getString(String key) {
        if (TextUtils.isEmpty(key)) {
            return "";
        }

        String value = mACache.getAsString(key);
        if (TextUtils.isEmpty(value)){
            return "";
        }
        return value;
    }


    /**
     * 存序列化对象
     * @param key
     * @param value
     * @param saveTime
     */
    public static void putObject(String key, Serializable value, int saveTime) {
        if (TextUtils.isEmpty(key)) {
            LogUtils.e(TAG, "key is null");
            return;
        }
        mACache.put(key, value, saveTime);
    }

    public static void putObject(String key, Serializable value) {
        if (TextUtils.isEmpty(key)) {
            LogUtils.e(TAG, "key is null");
            return;
        }
        mACache.put(key, value);
    }

    /**
     * 取序列化对象
     * @param key
     * @return
     */
    public static Object getObject(String key) {
        if (TextUtils.isEmpty(key)) {
            LogUtils.e(TAG, "key is null");
            return null;
        }
        return mACache.getAsObject(key);
    }

    /**
     * 存bitmap
     * @param key
     * @param value
     */
    public static void putBitmap(String key, Bitmap value) {
        if (TextUtils.isEmpty(key)) {
            LogUtils.e(TAG, "key is null");
            return;
        }
        mACache.put(key, value);
    }

    /**
     * 取bitmap
     * @param key
     * @return
     */
    public static Bitmap getBitmap(String key) {
        if (TextUtils.isEmpty(key)) {
            LogUtils.e(TAG, "key is null");
            return null;
        }
        return mACache.getAsBitmap(key);
    }

    /**
     * 移除缓存
     * @param key
     */
    public static void remove(String key) {
        if (TextUtils.isEmpty(key)) {
            LogUtils.e(TAG, "key is null");
            return;
        }
        mACache.remove(key);
    }

    public static void clear() {
        mACache.clear();
    }

}
