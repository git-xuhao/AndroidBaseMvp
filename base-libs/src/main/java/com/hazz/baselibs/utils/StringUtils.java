package com.hazz.baselibs.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class StringUtils {

    /**
     * is null or its length is 0 or it is made by space
     * <p>
     * <pre>
     * isBlank(null) = true;
     * isBlank(&quot;&quot;) = true;
     * isBlank(&quot;  &quot;) = true;
     * isBlank(&quot;a&quot;) = false;
     * isBlank(&quot;a &quot;) = false;
     * isBlank(&quot; a&quot;) = false;
     * isBlank(&quot;a b&quot;) = false;
     * </pre>
     *
     * @param str
     * @return if string is null or its size is 0 or it is made by space, return true, else return false.
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * is null or its length is 0
     * <p>
     * <pre>
     * isEmpty(null) = true;
     * isEmpty(&quot;&quot;) = true;
     * isEmpty(&quot;  &quot;) = false;
     * </pre>
     *
     * @param str
     * @return if string is null or its size is 0, return true, else return false.
     */
    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    /**
     * compare two string
     *
     * @param actual
     * @param expected
     * @return boolean
     */
    public static boolean isEquals(String actual, String expected) {
        return actual == expected || (actual == null ? expected == null : actual.equals(expected));
    }

    /**
     * null Object to empty string
     * <p>
     * <pre>
     * nullStrToEmpty(null) = &quot;&quot;;
     * nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
     * nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
     * </pre>
     *
     * @param str
     * @return String
     */
    public static String nullStrToEmpty(Object str) {
        return (str == null ? "" : (str instanceof String ? (String) str : str.toString()));
    }

    /**
     * capitalize first letter
     * <p>
     * <pre>
     * capitalizeFirstLetter(null)     =   null;
     * capitalizeFirstLetter("")       =   "";
     * capitalizeFirstLetter("2ab")    =   "2ab"
     * capitalizeFirstLetter("a")      =   "A"
     * capitalizeFirstLetter("ab")     =   "Ab"
     * capitalizeFirstLetter("Abc")    =   "Abc"
     * </pre>
     *
     * @param str
     * @return String
     */
    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str : new StringBuilder(str.length())
                .append(Character.toUpperCase(c)).append(str.substring(1)).toString();
    }

    /**
     * encoded in utf-8
     * <p>
     * <pre>
     * utf8Encode(null)        =   null
     * utf8Encode("")          =   "";
     * utf8Encode("aa")        =   "aa";
     * utf8Encode("啊啊啊啊")   = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
     * </pre>
     *
     * @param str
     * @return String
     * @throws UnsupportedEncodingException if an error occurs
     */
    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    /**
     * encoded in utf-8, if exception, return defultReturn
     *
     * @param str
     * @param defultReturn
     * @return String
     */
    public static String utf8Encode(String str, String defultReturn) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return str;
    }

    /**
     * get innerHtml from href
     * <p>
     * <pre>
     * getHrefInnerHtml(null)                                  = ""
     * getHrefInnerHtml("")                                    = ""
     * getHrefInnerHtml("mp3")                                 = "mp3";
     * getHrefInnerHtml("&lt;a innerHtml&lt;/a&gt;")                    = "&lt;a innerHtml&lt;/a&gt;";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com"&gt;innerHtml&lt;/a&gt;")               = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com" title="baidu"&gt;innerHtml&lt;/a&gt;") = "innerHtml";
     * getHrefInnerHtml("   &lt;a&gt;innerHtml&lt;/a&gt;  ")                           = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                      = "innerHtml";
     * getHrefInnerHtml("jack&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                  = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml1&lt;/a&gt;&lt;a&gt;innerHtml2&lt;/a&gt;")        = "innerHtml2";
     * </pre>
     *
     * @param href
     * @return <ul>
     * <li>if href is null, return ""</li>
     * <li>if not match regx, return source</li>
     * <li>return the last string that match regx</li>
     * </ul>
     */
    public static String getHrefInnerHtml(String href) {
        if (isEmpty(href)) {
            return "";
        }

        String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
        Pattern hrefPattern = Pattern.compile(hrefReg, Pattern.CASE_INSENSITIVE);
        Matcher hrefMatcher = hrefPattern.matcher(href);
        if (hrefMatcher.matches()) {
            return hrefMatcher.group(1);
        }
        return href;
    }

    /**
     * process special char in html
     * <p>
     * <pre>
     * htmlEscapeCharsToString(null) = null;
     * htmlEscapeCharsToString("") = "";
     * htmlEscapeCharsToString("mp3") = "mp3";
     * htmlEscapeCharsToString("mp3&lt;") = "mp3<";
     * htmlEscapeCharsToString("mp3&gt;") = "mp3\>";
     * htmlEscapeCharsToString("mp3&amp;mp4") = "mp3&mp4";
     * htmlEscapeCharsToString("mp3&quot;mp4") = "mp3\"mp4";
     * htmlEscapeCharsToString("mp3&lt;&gt;&amp;&quot;mp4") = "mp3\<\>&\"mp4";
     * </pre>
     *
     * @param source
     * @return String
     */
    public static String htmlEscapeCharsToString(String source) {
        return StringUtils.isEmpty(source) ? source : source.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }

    /**
     * transform half width char to full width char
     * <p>
     * <pre>
     * fullWidthToHalfWidth(null) = null;
     * fullWidthToHalfWidth("") = "";
     * fullWidthToHalfWidth(new String(new char[] {12288})) = " ";
     * fullWidthToHalfWidth("！＂＃＄％＆) = "!\"#$%&";
     * </pre>
     *
     * @param s
     * @return String
     */
    public static String fullWidthToHalfWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == 12288) {
                source[i] = ' ';
                // } else if (source[i] == 12290) {
                // source[i] = '.';
            } else if (source[i] >= 65281 && source[i] <= 65374) {
                source[i] = (char) (source[i] - 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * transform full width char to half width char
     * <p>
     * <pre>
     * halfWidthToFullWidth(null) = null;
     * halfWidthToFullWidth("") = "";
     * halfWidthToFullWidth(" ") = new String(new char[] {12288});
     * halfWidthToFullWidth("!\"#$%&) = "！＂＃＄％＆";
     * </pre>
     *
     * @param s
     * @return String
     */
    public static String halfWidthToFullWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == ' ') {
                source[i] = (char) 12288;
                // } else if (source[i] == '.') {
                // source[i] = (char)12290;
            } else if (source[i] >= 33 && source[i] <= 126) {
                source[i] = (char) (source[i] + 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * 手机号码合法性检查
     *
     * @param value 字符串
     * @return 手机号码是否合法
     */
    public static boolean isPhone(String value) {
        return !Pattern.compile("^1[3|4|5|7|8]\\d{9}$").matcher(value).matches();
    }

    /**
     * 字符串位数范围检查
     *
     * @param value 字符串
     * @return 是否属于所给范围内
     */
    public static Boolean isCount(String value, int min, int max) {
        if (value == null) {
            return true;
        }
        if (value.length() < min) {
            return true;
        }

        if (value.length() > max) {
            return true;
        }
        return false;
    }

    /**
     * 邮箱地址合法性检查
     *
     * @param value 字符串
     * @return 邮箱地址是否合法
     */
    public static boolean isEmail(String value) {
        return Pattern
                .compile("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$",
                        Pattern.CASE_INSENSITIVE).matcher(value).matches();
    }

    // 身份证
    private static final String codeAndCity[][] = {{"11", "北京"}, {"12", "天津"}, {"13", "河北"}, {"14", "山西"},
            {"15", "内蒙古"}, {"21", "辽宁"}, {"22", "吉林"}, {"23", "黑龙江"}, {"31", "上海"}, {"32", "江苏"},
            {"33", "浙江"}, {"34", "安徽"}, {"35", "福建"}, {"36", "江西"}, {"37", "山东"}, {"41", "河南"},
            {"42", "湖北"}, {"43", "湖南"}, {"44", "广东"}, {"45", "广西"}, {"46", "海南"}, {"50", "重庆"},
            {"51", "四川"}, {"52", "贵州"}, {"53", "云南"}, {"54", "西藏"}, {"61", "陕西"}, {"62", "甘肃"},
            {"63", "青海"}, {"64", "宁夏"}, {"65", "新疆"}, {"71", "台湾"}, {"81", "香港"}, {"82", "澳门"},
            {"91", "国外"}};
    // 身份证 加权因子 常数
    private final static int[] wi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
    // 身份证校验码
    private final static int[] vi = {1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2};

    /**
     * 身份证号码检查
     */
    public static boolean idCard(String idCard) {
        // 身份证号码非空
        if (!isEmpty(idCard)) {
            return !idCardCheck(idCard);
        } else {
            return true;
        }
    }

    /**
     * 身份证检查
     */
    private static boolean idCardCheck(String IDnumber) {
        boolean result = IDnumber.matches("[0-9]{15}|[0-9]{17}[0-9X]");
        if (result) {
            // 地区验证=true
            if (cityCodeCheck(IDnumber)) {
                // 15位转18位
                if (IDnumber.length() == 15) {
                    IDnumber = IDnumber15To18(IDnumber);
                }
                // 出生日期验证=true
                if (birthdayCheck(IDnumber)) {
                    // 校验身份证的校验码
                    return verify(IDnumber);
                } else
                    return false;
            } else
                return false;
        } else
            return false;
    }

    /**
     * 地区验证(身份证)
     */
    private static boolean cityCodeCheck(String IDnumber) {
        boolean result = false;
        String provinceid = IDnumber.substring(0, 2);
        for (int i = 0; i < codeAndCity.length; i++) {
            if (codeAndCity[i][0].equals(provinceid)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 15位转18位 (身份证)
     */
    private static String IDnumber15To18(String IDnumber) {
        StringBuffer eighteen = new StringBuffer(IDnumber);
        eighteen = eighteen.insert(6, "19");
        IDnumber = eighteen.toString();
        // 计算最后一位校验值
        String remain = getVerify(IDnumber);
        return (IDnumber + remain);
    }

    /**
     * 计算最后一位校验值
     */
    private static String getVerify(String eighteen) {
        int remain = 0;
        if (eighteen.length() == 18) {
            eighteen = eighteen.substring(0, 17);
        }
        if (eighteen.length() == 17) {
            int sum = 0;
            int[] ai = new int[18];
            for (int i = 0; i < 17; i++) {
                String k = eighteen.substring(i, i + 1);
                ai[i] = Integer.valueOf(k);
            }
            for (int i = 0; i < 17; i++) {
                sum += wi[i] * ai[i];
            }
            remain = sum % 11;
        }
        return remain == 2 ? "X" : String.valueOf(vi[remain]);
    }

    /**
     * 出生日期验证(身份证)
     */
    private static boolean birthdayCheck(String IDnumber) {
        boolean result = false;
        int year, month, date;
        year = Integer.parseInt(IDnumber.substring(6, 10));
        month = Integer.parseInt(IDnumber.substring(10, 12));
        date = Integer.parseInt(IDnumber.substring(12, 14));
        switch (month) {
            case 2:
                result = (date >= 1) && (year % 4 == 0 ? date <= 29 : date <= 28);
                break;
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                result = (date >= 1) && (date <= 31);
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                result = (date >= 1) && (date <= 31);
                break;
            default:
                result = false;
                break;
        }
        return result;
    }

    /**
     * 身份证的校验码验证
     */
    private static boolean verify(String IDnumber) {
        // 获取第18位
        String verify = IDnumber.substring(17, 18);
        // 验证最后一位校验值
        if (verify.equals(getVerify(IDnumber))) {
            return true;
        }
        return false;
    }
}
