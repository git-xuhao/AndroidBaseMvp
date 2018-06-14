package com.hazz.baselibs.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xuhao
 * @desc
 * @date 2017/12/13 上午11:58.
 */

public class RegularUtils {
    // 数字
    private static Pattern numericPattern      = Pattern.compile("^[0-9\\-]+$");
    // float
    private static Pattern floatNumericPattern = Pattern.compile("^[0-9\\-\\.]+$");
    // 字母
    private static Pattern abcPattern          = Pattern.compile("^[a-z|A-Z]+$");
    // 分割字符
    public static final String splitStrPattern     = ",|，|;|；|、|\\.|。|-|_|\\(|\\)|\\[|\\]|\\{|\\}|\\\\|/| |　|\"";

    /**
     * 判断是否数字表示
     *
     * @param src
     *         源字符串
     */
    public static boolean isNumeric(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = numericPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    /**
     * 判断是否纯字母组合
     *
     * @param src
     *         源字符串
     */
    public static boolean isABC(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = abcPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    /**
     * 判断是否浮点数字表示
     *
     * @param src
     *         源字符串
     */
    public static boolean isFloatNumeric(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = floatNumericPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    /**
     * 判断字符串str是否符合正则表达式reg
     */
    public static boolean isMatcher(String str, String reg) {
        Pattern pattern = Pattern.compile(reg);
        Matcher isNum   = pattern.matcher(str);

        return isNum.matches();
    }

    /**
     * 获取符合reg正则表达式的字符串在String中出现的次数
     */
    public static int countSubStrReg(String str, String reg) {
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        int     i = 0;
        while (m.find()) {
            String temp = m.group(0);
            i += temp.length();
        }
        return i;
    }

    /**
     * 判断是否是整数
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否符合邮箱规则
     */
    public static boolean isEmail(String email) {
        if (email == null || email.length() < 1 || email.length() > 256) {
            return false;
        }
        Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        return pattern.matcher(email).matches();
    }

    /**
     * 验证手机格式
     */
    public static boolean isPhone(String phone) {
        /*
         * 电信
         * 中国电信手机号码开头数字
         * 2G/3G号段（CDMA2000网络）133、153、180、181、189
         * 4G号段 177
         *
         * 联通
         * 中国联通手机号码开头数字
         * 2G号段（GSM网络）130、131、132、155、156
         * 3G上网卡145
         * 3G号段（WCDMA网络）185、186
         * 4G号段 176、185[1]
         *
         * 移动
         * 中国移动手机号码开头数字
         * 2G号段（GSM网络）有134x（0-8）、135、136、137、138、139、150、151、152、158、159、182、183、184。
         * 3G号段（TD-SCDMA网络）有157、187、188
         * 3G上网卡 147
         * 4G号段 178
         * 补充 17开头的手机号码都是三大运营商的4g网络手机卡，移动、联通、电信。比如：170、173、174、177是电信的，171、175、176是联通的，172、178是移动。
         *
         * 14号段以前为上网卡专属号段，如中国联通的是145，中国移动的是147等等。
         * 170号段为虚拟运营商专属号段，170号段的 11 位手机号前四位来区分基础运营商，其中 “1700” 为中国电信的转售号码标识，“1705” 为中国移动，“1709” 为中国联通。
         */
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        phone = phone.replaceAll("\\s*", "");
        Pattern regex   = Pattern.compile("^((1[358][0-9])|(14[57])|(17[012345678]))\\d{8}$");
        Matcher matcher = regex.matcher(phone);
        return matcher.matches();
    }
}
