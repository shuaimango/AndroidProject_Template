package com.mango.framework.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util_str {
    //正则表达式：非负整数
    public static final String RE_POSITIVE_INT = "[0-9]+";

    public static int getChineseCount(String str) {
        // 最大英文/数字长度 一个汉字算两个字母
        int count = 0;
        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");// unicode编码，判断是否为汉字
        Matcher m = p.matcher(str);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                count = count + 1;
            }
        }
        return count;
    }

    /*
     * 字符串里是否包含字母
     */
    public static boolean hasAlpha(String s) {
        char[] string = s.toCharArray();
        for (int i = 0; i < string.length; i++) {
            if (Character.isLetter(string[i])) {
                return true;
            }
        }
        return false;
    }


    /**
     * 将指定路径的json格式的文本文件生成一个没有空格 的字符串
     * <p/>
     * 可用于测试接口返回的json（测试数据）
     *
     * @return
     */
    public static String file2StrByTrim(String filePath) {
        String trimStr = "";
        String read;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while ((read = reader.readLine()) != null) {
                read = read.trim();
                trimStr = trimStr + read;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        trimStr = trimStr.replaceAll("\"", "'");
        return trimStr;
    }

    /*
     * 图片uri转成真实路径
     */
    public static String imguriTopath(Uri uri, Activity activity) {
        // 方法一：String[] imgs = { MediaStore.Images.Media.DATA };// 将图片URI转换成存储路径
        // Cursor cursor = activity.managedQuery(uri, imgs, null, null, null);
        // int index =
        // cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        // cursor.moveToFirst();
        // return cursor.getString(index);
        ContentResolver cr = activity.getContentResolver();
        Cursor mCur = cr.query(uri, null, null, null, null);
        /** 得到本地图片库中图片的 id、路径、大小、文件名 */
        // cursor.getString(0),1,2,3
        if (!mCur.moveToFirst()) {
            return "";
        }
        mCur.close();
        return mCur.getString(mCur.getColumnIndex("_data"));
    }

    /***
     * 获取HTML内容
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String getHTML(String url) throws Exception {
        URL uri = new URL(url);
        URLConnection connection = uri.openConnection();
        InputStream in = connection.getInputStream();
        byte[] buf = new byte[1024];
        int length = 0;
        StringBuilder sb = new StringBuilder();
        while ((length = in.read(buf, 0, buf.length)) > 0) {
            sb.append(new String(buf, "UTF-8"));
        }
        in.close();
        return sb.toString();
    }

    public static final boolean isNull(String text) {
        if (text == null) {
            return true;
        }
        String trimStr = text.trim();
        return "".equals(trimStr) || "null".equalsIgnoreCase(trimStr);
    }

    public static final int parseInt(String value) {
        return parseInt(value, 0);
    }

    public static final int parseInt(String value, int defaultValue) {
        int v = defaultValue;
        if(TextUtils.isEmpty(value))
            return v;
        try {
            v = Integer.parseInt(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    public static final long parseLong(String value) {
        return parseLong(value, 0);
    }

    public static final long parseLong(String value, long defaultValue) {
        long v = defaultValue;
        try {
            v = Long.parseLong(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    public static final float parseFloat(String value) {
        return parseFloat(value, 0);
    }

    public static final float parseFloat(String value, float defaultValue) {
        float v = defaultValue;
        try {
            v = Float.parseFloat(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    public static final double parseDouble(String value) {
        return parseDouble(value, 0);
    }


    public static final double parseDouble(String value, double defaultValue) {
        double v = defaultValue;
        try {
            v = Double.parseDouble(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    public static final String getString(String str) {
        return getString(str, "");
    }

    public static final String getString(String str, String def) {
        return TextUtils.isEmpty(str) ? def : str;
    }

    public static final String getUrlWithParams(String url, String... params) {
        return getUriWithParams(url, params).toString();
    }

    public static final Uri getUriWithParams(String url, String... params) {
        Uri uri = Uri.parse(url);
        if (uri != null && params != null && params.length > 1) {
            Uri.Builder builder = uri.buildUpon();
            for (int i = 0; i < params.length; i += 2) {
                if (!TextUtils.isEmpty(params[i]) && !TextUtils.isEmpty(params[i + 1])) {
                    builder.appendQueryParameter(params[i], params[i + 1]);
                }
            }
            return builder.build();
        }
        return uri;
    }

    public static <T> String getUriWithParamsStr(String url, String... params) {
        return getUriWithParams(url, params).toString();
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static final String getQueryParam(String url, String param) {
        try {
            Uri uri = Uri.parse(url);
            return uri.getQueryParameter(param);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static final int getQueryIntParam(String url, String param) {
        return getQueryIntParam(url, param, 0);
    }

    public static final int getQueryIntParam(String url, String param, int def) {
        Uri uri = Uri.parse(url);
        return parseInt(uri.getQueryParameter(param), def);
    }

    public static boolean equalsNotNull(String str1, String str2) {
        return str1 != null && str1.equals(str2);
    }

    public static boolean equalsNotEmpty(String str1, String str2) {
        return !TextUtils.isEmpty(str1) && str1.equals(str2);
    }

    public static boolean contains(String src, String containerStr) {
        return src != null && containerStr != null && src.contains(containerStr);
    }

    public static boolean isEmptyByTrim(String str) {
        return TextUtils.isEmpty(str) || TextUtils.isEmpty(str.trim());
    }

    public static Uri parseUri(String uri) {
        try {
            return Uri.parse(uri);
        } catch (Exception e) {
            e.printStackTrace();
            return Uri.EMPTY;
        }
    }

    /**
     * 对Unicode字符串解码
     *
     * @param utfString
     * @return
     */
    public static String decodeUnicode(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;
        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
            }
        }
        sb.append(utfString.substring(pos));
        return sb.toString();
    }


    /**
     * 包含中文时，判断一个字符串的长度
     *
     * @param src
     * @return
     */
    public static int getLengthContainsChinese(CharSequence src) {
        String srcStr = src.toString();
        return srcStr.length() + getChineseCount(srcStr);
    }

    public static boolean equalsByTrim(String s1, String s2) {
        s1 = s1 != null ? s1.trim() : s1;
        s2 = s2 != null ? s2.trim() : s2;
        return equals(s1, s2);
    }

    public static boolean equals(String s1, String s2) {
        return s1 != null && s1.equals(s2);
    }

    public static String add(String value, int add) {
        return String.valueOf(parseInt(value) + add);
    }

    public static String getFriendlyNum(int num, int max) {
        if (num <= 0) {
            return "";
        } else if (num > max) {
            return max + "+";
        } else {
            return num + "";
        }
    }
}
