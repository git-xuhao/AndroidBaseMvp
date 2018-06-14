package com.hazz.baselibs.utils;

import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * @author xuhao
 * @desc
 * @date 2017/12/12 下午5:56.
 */

public class LogUtils {

    private static boolean debug = true;

    private LogUtils() {
    }

    public static void init(String tag, boolean appDebug) {
        debug = appDebug;
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder().tag(tag).build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override public boolean isLoggable(int priority, String tag) {
                return debug;
            }
        });
        t(tag);
    }




    public static void t(String tag) {
        if (debug) {
            Logger.t(tag);
        }
    }

    public static void log(int priority, String tag, String message, Throwable throwable) {
        if (debug) {
            Logger.log(priority, tag, message, throwable);
        }
    }

    public static void d(String message, Object... args) {
        if (debug) {
            Logger.d(message, args);
        }
    }

    public static void d(Object object) {
        if (debug) {
            Logger.d(object);

        }
    }


    /**
     * 截断输出日志
     * @param msg
     */
    public static void debug(String msg) {
        if (msg == null || msg.length() == 0)
            return;

        int segmentSize = 3 * 1024;
        long length = msg.length();
        if (length <= segmentSize ) {// 长度小于等于限制直接打印
            Logger.e(msg);
        }else {



            while (msg.length() > segmentSize ) {// 循环分段打印日志
                String logContent = msg.substring(0, segmentSize );
                msg = msg.replace(logContent, "");
                Logger.e(logContent);
            }
            Logger.e(msg);// 打印剩余日志
        }
    }

    public static void e(String message, Object... args) {
        if (debug) {
            Logger.e(message, args);
        }
    }

    public static void e(Throwable throwable, String message, Object... args) {
        if (debug) {
            Logger.e(throwable, message, args);
        }
    }

    public static void i(String message, Object... args) {
        if (debug) {
            Logger.i(message, args);
        }
    }

    public static void v(String message, Object... args) {
        if (debug) {
            Logger.v(message, args);
        }
    }

    public static void w(String message, Object... args) {
        if (debug) {
            Logger.w(message, args);
        }
    }

    public static void wtf(String message, Object... args) {
        if (debug) {
            Logger.wtf(message, args);
        }
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json) {
        if (debug) {
            Logger.json(json);
        }
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    public static void xml(String xml) {
        if (debug) {
            Logger.xml(xml);
        }
    }

    public static void d(String tag, String msg) {
        if (debug) {
            Log.d(tag, msg);
        }
    }
    public static void i(String tag, String msg) {
        if (debug) {
            Log.i(tag, msg);
        }
    }
    public static void v(String tag, String msg) {
        if (debug) {
            Log.v(tag, msg);
        }
    }
}
