package com.mango.framework.util;

import android.text.TextUtils;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util_verify {

    //用于匹配固定电话号码
    private final static String REGEX_FIXEDPHONE = "^(010|02\\d|0[3-9]\\d{2})?\\d{6,8}$";
    private final static String REGEX_PHONE ="^1[34578]\\d{9}$";
    private static final String PATTERN_EMAIL = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    private static final String PATTERN_plateNumber = "^[A-Za-z]{1}[A-Za-z_0-9]{5}$";

    public static boolean isLicensePlateNumber(String str) {
        return isMatch(str, PATTERN_plateNumber);
    }
    /**
  　　* 判断链接是否有效
  　　*/
    public static boolean isUrlValid(String str){
        if(TextUtils.isEmpty(str))
            return false;
    	try {
    		new URL(str);
    		return true;  
		} catch (Exception e) {
			return false;
		}
//    	return false;
    }
    // 判断手机格式是否正确
    public static boolean isMobileNO(String str) {
        return isMatch(str, REGEX_PHONE);
    }
    // 判断座机格式是否正确
    public static boolean isFixMobileNO(String str) {
        return isMatch(str, REGEX_FIXEDPHONE);
    }

    public static boolean isMatch(String str, String regex) {
        if (TextUtils.isEmpty(str))
            return false;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    // 判断email格式是否正确
    public static boolean isEmail(String str) {
        return isMatch(str, PATTERN_EMAIL);
    }

    // 判断是否全是数字
    public static boolean isNumeric(String str) {
        return isMatch(str, "[0-9]*");
    }

    // 判断是否是数字或字母
    public static boolean isPassword(String str) {
        return isMatch(str, "[A-Z,a-z,0-9]*");
    }

    /**
     * 从短信字符窜提取验证码
     * 
     * @param body
     *            短信内容
     * @param YZMLENGTH
     *            验证码的长度 一般6位或者4位
     * @return 接取出来的验证码
     */
    public static String getVerifyCode(String body, int YZMLENGTH) {
        // 首先([a-zA-Z0-9]{YZMLENGTH})是得到一个连续的六位数字字母组合
        // (?<![a-zA-Z0-9])负向断言([0-9]{YZMLENGTH})前面不能有数字
        // (?![a-zA-Z0-9])断言([0-9]{YZMLENGTH})后面不能有数字出现
        Pattern p = Pattern.compile("(?<![a-zA-Z0-9])([a-zA-Z0-9]{" + YZMLENGTH + "})(?![a-zA-Z0-9])");
        Matcher m = p.matcher(body);
        if (m.find()) {
            return m.group(0);
        }
        return null;
    }

}
