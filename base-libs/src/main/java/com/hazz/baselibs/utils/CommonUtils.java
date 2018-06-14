package com.hazz.baselibs.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.Toast;

import com.hazz.baselibs.app.BaseApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 常用的方法 工具
 */
public class CommonUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue) {
        final float scale = BaseApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Show message
     *
     * @param activity Activity
     * @param msg message
     */
    public static void showMessage(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }



    /**
     * 泛型转换工具方法 eg:object ==> map<String, String>
     *
     * @param object Object
     * @param <T> 转换得到的泛型对象
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object) {
        return (T) object;
    }

    /**
     * 获取Drawable
     * @param resid resid
     * @return Drawable
     */
    public static Drawable getDrawable(int resid) {
        return getResource().getDrawable(resid);
    }

    /**
     * 获取颜色
     * @param resid resid
     * @return int
     */
    public static int getColor(int resid) {
        return getResource().getColor(resid);
    }

    /**
     * 获取resources
     * @return Resources
     */
    public static Resources getResource() {
        return BaseApplication.getContext().getResources();
    }

    /**
     * 获取数组
     * @param resId
     * @return
     */
    public static String[] getStringArray(int resId) {
        return getResource().getStringArray(resId);
    }

    /**
     * 获取string字符串
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return getResource().getString(resId);
    }

    /**
     * 获取Dimens
     * @param resId resId
     * @return float
     */
    public static float getDimens(int resId) {
        return getResource().getDimension(resId);
    }
}