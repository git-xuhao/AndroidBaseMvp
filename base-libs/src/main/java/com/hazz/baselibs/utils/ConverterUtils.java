package com.hazz.baselibs.utils;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * @desc 类型转换器
 */

public class ConverterUtils {
    /**
     * 从字符串中获取 boolean 型
     */
    public static boolean getBoolean(String args) {
        if (!TextUtils.isEmpty(args)) {
            if (args.equals("true")) {
                return true;
            } else if (args.equals("false")) {
                return false;
            } else if (RegularUtils.isNumeric(args)) {
                return getInteger(args) > 0;
            }
        }
        return false;
    }

    /**
     * 从字符串中获取 int 型，如果字符串为空，则返回0
     */
    public static int getInteger(String args) {
        try {
            if (TextUtils.isEmpty(args)) {
                return 0;
            } else {
                return new BigDecimal(args).intValue();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 从字符串中获取 short 型，如果字符串为空，则返回0
     */
    public static int getShort(String args) {
        try {
            if (TextUtils.isEmpty(args)) {
                return 0;
            } else {
                return new BigDecimal(args).shortValue();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 从字符串中获取 long 型，如果字符串为空，则返回0
     */
    public static long getLong(String args) {
        try {
            if (TextUtils.isEmpty(args)) {
                return 0L;
            } else {
                return new BigDecimal(args).longValue();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 从字符串中获取 float 型，如果字符串为空，则返回0
     */
    public static float getFloat(String args) {
        try {
            if (TextUtils.isEmpty(args)) {
                return 0.0F;
            } else {
                return new BigDecimal(args).floatValue();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0F;
        }
    }

    /**
     * @param args
     *         源字符串
     * @param scale
     *         保留精度
     *
     * @return 从字符串中获取 float 型，如果字符串为空，则返回0
     */
    public static float getFloat(String args, int scale) {
        try {
            if (TextUtils.isEmpty(args)) {
                return 0.0F;
            } else {
                return new BigDecimal(args).setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0F;
        }
    }

    /**
     * 从字符串中获取 double 型，如果字符串为空，则返回0
     */
    public static double getDouble(String args) {
        try {
            if (TextUtils.isEmpty(args)) {
                return 0.0D;
            } else {
                return new BigDecimal(args).doubleValue();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0D;
        }
    }

    /**
     * @param args
     *         源字符串
     * @param scale
     *         保留精度
     *
     * @return 从字符串中获取 double 型，如果字符串为空，则返回0
     */
    public static double getDouble(String args, int scale) {
        try {
            if (TextUtils.isEmpty(args)) {
                return 0.0D;
            } else {
                return new BigDecimal(args).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0D;
        }
    }

    /**
     * 短整型转字节数组
     */
    public static byte[] shortToByte(short number) {
        int    temp = number;
        byte[] b    = new byte[2];
        for (int i = 0; i < b.length; i++) {
            // 将最低位保存在最低位
            b[i] = Integer.valueOf(temp & 0xff).byteValue();
            // 向右移8位
            temp = temp >> 8;
        }
        return b;
    }

    /**
     * 字节数组转短整型
     */
    public static short byteToShort(byte[] b) {
        // 最低位
        short s0 = (short) (b[0] & 0xff);
        short s1 = (short) (b[1] & 0xff);
        s1 <<= 8;
        return (short) (s0 | s1);
    }

    /**
     * 整型转字节数组
     */
    public static byte[] intToByte(int i) {
        byte[] bt = new byte[4];
        bt[0] = (byte) (0xff & i);
        bt[1] = (byte) ((0xff00 & i) >> 8);
        bt[2] = (byte) ((0xff0000 & i) >> 16);
        bt[3] = (byte) ((0xff000000 & i) >> 24);
        return bt;
    }

    /**
     * 字节数组转整型
     */
    public static int byteToInt(byte[] bytes) {
        int num = bytes[0] & 0xFF;
        num |= ((bytes[1] << 8) & 0xFF00);
        num |= ((bytes[2] << 16) & 0xFF0000);
        num |= ((bytes[3] << 24) & 0xFF000000);
        return num;
    }

    /**
     * 长整型转字节数组
     */
    public static byte[] longToByte(long number) {
        long   temp = number;
        byte[] b    = new byte[8];
        for (int i = 0; i < b.length; i++) {
            // 将最低位保存在最低位
            b[i] = Long.valueOf(temp & 0xff).byteValue();
            // 向右移8位
            temp = temp >> 8;
        }
        return b;
    }

    /**
     * 字节数组转长整型
     */
    public static long byteToLong(byte[] b) {
        long s0 = b[0] & 0xff;
        long s1 = b[1] & 0xff;
        long s2 = b[2] & 0xff;
        long s3 = b[3] & 0xff;
        long s4 = b[4] & 0xff;
        long s5 = b[5] & 0xff;
        long s6 = b[6] & 0xff;
        long s7 = b[7] & 0xff;
        s1 <<= 8;
        s2 <<= 16;
        s3 <<= 24;
        s4 <<= 8 * 4;
        s5 <<= 8 * 5;
        s6 <<= 8 * 6;
        s7 <<= 8 * 7;
        return s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
    }

    /**
     * 字节数组转字符串
     */
    public static String byteToString(byte[] b) {
        String strRead = new String(b);
        strRead = String.copyValueOf(strRead.toCharArray(), 0, b.length);
        return strRead;
    }
}

