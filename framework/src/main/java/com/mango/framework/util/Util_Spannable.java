package com.mango.framework.util;

import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

/**
 * 用于设置文字的前景色、背景色、Typeface、粗体、斜体、字号、超链接、删除线、下划线、上下标等
 */
public class Util_Spannable {

    public static SpannableString parseToSpan(CharSequence content) {
        SpannableString spannableString = null;
        if (content != null && content instanceof SpannableString) {
            spannableString = (SpannableString) content;
        } else {
            spannableString = new SpannableString(content);
        }
        return spannableString;
    }

    /**
     * 改变字符串中某一段文字的字号和颜色
     */
    public static SpannableString setTextSizeAndColor(CharSequence content, int startIndex,
                                                      int endIndex, int fontSize, int color) {
        if (TextUtils.isEmpty(content) || fontSize <= 0
                || startIndex >= endIndex || startIndex < 0
                || endIndex > content.length()) {
            return null;
        }
        SpannableString spannableString = parseToSpan(content);
        spannableString.setSpan(new AbsoluteSizeSpan(fontSize), startIndex,
                endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(color),
                startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 改变字符串中某一段文字的字号
     */
    public static SpannableString setTextSize(CharSequence content, int startIndex,
                                              int endIndex, int fontSize) {
        if (TextUtils.isEmpty(content) || fontSize <= 0
                || startIndex >= endIndex || startIndex < 0
                || endIndex > content.length()) {
            return null;
        }
        SpannableString spannableString = parseToSpan(content);
        spannableString.setSpan(new AbsoluteSizeSpan(fontSize), startIndex,
                endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static SpannableString setTextSub(CharSequence content, int startIndex,
                                             int endIndex) {
        SpannableString spannableString = parseToSpan(content);
        if (TextUtils.isEmpty(content) || startIndex < 0
                || endIndex > content.length() || startIndex >= endIndex) {
            return spannableString;
        }
        spannableString.setSpan(new SubscriptSpan(), startIndex, endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static SpannableString setTextSuper(CharSequence content, int startIndex,
                                               int endIndex) {
        SpannableString spannableString = parseToSpan(content);
        if (TextUtils.isEmpty(content) || startIndex < 0
                || endIndex > content.length() || startIndex >= endIndex) {
            return spannableString;
        }
        spannableString.setSpan(new SuperscriptSpan(), startIndex, endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    /**
     * 加删除线
     */
    public static SpannableString setTextStrikethrough(CharSequence content,
                                                       int startIndex, int endIndex) {
        SpannableString spannableString = parseToSpan(content);
        if (TextUtils.isEmpty(content) || startIndex < 0
                || endIndex > content.length() || startIndex >= endIndex) {
            return spannableString;
        }
        spannableString.setSpan(new StrikethroughSpan(), startIndex, endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static SpannableString setTextUnderline(CharSequence content,
                                                   int startIndex, int endIndex) {
        SpannableString spannableString = parseToSpan(content);
        if (TextUtils.isEmpty(content) || startIndex < 0
                || endIndex > content.length() || startIndex >= endIndex) {
            return spannableString;
        }
        spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static SpannableString setTextBold(CharSequence content, int startIndex,
                                              int endIndex) {
        SpannableString spannableString = parseToSpan(content);
        if (TextUtils.isEmpty(content) || startIndex < 0
                || endIndex > content.length() || startIndex >= endIndex) {
            return spannableString;
        }
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static SpannableString setTextItalic(CharSequence content, int startIndex,
                                                int endIndex) {
        SpannableString spannableString = parseToSpan(content);
        if (TextUtils.isEmpty(content) || startIndex < 0
                || endIndex > content.length() || startIndex >= endIndex) {
            return spannableString;
        }
        spannableString.setSpan(
                new StyleSpan(android.graphics.Typeface.ITALIC), startIndex,
                endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static SpannableString setTextBoldItalic(CharSequence content,
                                                    int startIndex, int endIndex) {
        SpannableString spannableString = parseToSpan(content);
        if (TextUtils.isEmpty(content) || startIndex < 0
                || endIndex > content.length() || startIndex >= endIndex) {
            return spannableString;
        }
        spannableString.setSpan(new StyleSpan(
                        android.graphics.Typeface.BOLD_ITALIC), startIndex, endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static SpannableString setTextClick(CharSequence content, int startIndex, int endIndex, ClickableSpan clickableSpan) {
        SpannableString spannableString = parseToSpan(content);
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString setTextForeground(CharSequence content, int foregroundColor) {
        return setTextForeground(content, 0, content.length(), foregroundColor);
    }

    public static SpannableString setTextForeground(CharSequence content,
                                                    int startIndex, int endIndex, int foregroundColor) {
        SpannableString spannableString = parseToSpan(content);
        if (TextUtils.isEmpty(content) || startIndex < 0 || endIndex > content.length() || startIndex >= endIndex) {
            return spannableString;
        }
        spannableString.setSpan(new ForegroundColorSpan(foregroundColor),
                startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }


    public static SpannableString setTextBackground(CharSequence content,
                                                    int startIndex, int endIndex, int backgroundColor) {
        SpannableString spannableString = parseToSpan(content);
        if (TextUtils.isEmpty(content) || startIndex < 0
                || endIndex > content.length() || startIndex >= endIndex) {
            return spannableString;
        }
        spannableString.setSpan(new BackgroundColorSpan(backgroundColor),
                startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    /**
     * 设置文本的超链接
     *
     * @param content    需要处理的文本
     * @param startIndex
     * @param endIndex   被处理文本中需要处理字串的开始和结束索引
     * @param url        文本对应的链接地址，需要注意格式： （1）电话以"tel:"打头，比如"tel:02355692427"
     *                   （2）邮件以"mailto:"打头，比如"mailto:zmywly8866@gmail.com"
     *                   （3）短信以"sms:"打头，比如"sms:02355692427"
     *                   （4）彩信以"mms:"打头，比如"mms:02355692427"
     *                   （5）地图以"geo:"打头，比如"geo:68.426537,68.123456"
     *                   （6）网络以"http://"打头，比如"http://www.google.com"
     */
    public static SpannableString setTextURL(CharSequence content, int startIndex,
                                             int endIndex, String url) {
        SpannableString spannableString = parseToSpan(content);
        if (TextUtils.isEmpty(content) || startIndex < 0
                || endIndex > content.length() || startIndex >= endIndex) {
            return spannableString;
        }
        spannableString.setSpan(new URLSpan(url), startIndex, endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static SpannableString setTextImg(CharSequence content, int startIndex,
                                             int endIndex, Drawable drawable) {
        SpannableString spannableString = parseToSpan(content);
        if (TextUtils.isEmpty(content) || startIndex < 0
                || endIndex > content.length() || startIndex >= endIndex) {
            return spannableString;
        }
        spannableString.setSpan(new ImageSpan(drawable), startIndex, endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }


    public static void setSpan(SpannableString ss, Object what, int start, int end) {
        setSpan(ss, what, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    /**
     * @param ss
     * @param what
     * @param start
     * @param flags
     */
    public static void setSpan(SpannableString ss, Object what, int start, int end, int flags) {
        ss.setSpan(what, start, end, flags);
    }
}