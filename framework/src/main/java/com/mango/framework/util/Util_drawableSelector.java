package com.mango.framework.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;

import com.mango.framework.R;


/**
 * Created by yx on 2015/11/10.
 * 替代xml里写selector，实现按钮点击效果
 * 最简单的使用示例：view.setBackground(Util_drawableSelector.getPressedSelector("#64c308",dp2Px(25),dp2Px(200),dp2Px(45)));
 */
public class Util_drawableSelector {
    public static Drawable getDrawable(Context context, int drawable, int w, int h) {
        Drawable d = context.getResources().getDrawable(drawable);
        d.setBounds(0, 0, w, h);
        return d;
    }

    public static GradientDrawable getCornerRecDrawable(int solidColor, int strokeWidth, int strokeColor, float radius) {
        return getCornerRecDrawable(solidColor, strokeWidth, "#" + Integer.toHexString(strokeColor), radius);
    }

    public static GradientDrawable getCornerRecDrawable(int solidColor, int strokeWidth, String strokeColor, float radius) {
        return getCornerRecDrawable(solidColor, strokeWidth, strokeColor, radius, 0, 0);
    }

    public static GradientDrawable getCornerRecDrawable(int solidColor, float radius, int width) {
        return getCornerRecDrawable(solidColor, 0, "", radius, width, (int) (2 * radius));
    }

    public static GradientDrawable getCornerRecDrawable(int solidColor, int strokeWidth, String strokeColor, float radius, int width, int height) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setSize(width, height);
        //设置形状
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        //填充颜色
        gradientDrawable.setColor(solidColor);
        //设置边框线宽,颜色
        if (strokeWidth > 0)
            gradientDrawable.setStroke(strokeWidth, Color.parseColor(strokeColor));
        //圆角半径
        if (radius > 0)
            gradientDrawable.setCornerRadius(radius);
        //大小
        return gradientDrawable;
    }

    public static GradientDrawable getGradientDrawable(int[] colors, GradientDrawable.Orientation orientation, float gradientRadius, float radius, int width, int height) {
        GradientDrawable gradientDrawable = new GradientDrawable(orientation, colors);//设置渐变色方向、渐变色
        gradientDrawable.setGradientRadius(gradientRadius);// 渐变色的半径 最好大一点效果明显
        gradientDrawable.setSize(width, height);
        gradientDrawable.setShape(GradientDrawable.RECTANGLE); //设置形状
        //圆角半径
        if (radius > 0)
            gradientDrawable.setCornerRadius(radius);
        //大小
        return gradientDrawable;
    }

    public static GradientDrawable getCornerGradientDrawable(int[] colors, int width, int height) {
        return getGradientDrawable(colors, GradientDrawable.Orientation.LEFT_RIGHT, width / 2, height / 2, width, height);
    }

    /**
     * 生成按下50%透明度的背景色的Selector
     *
     * @param solidColor,如"#cccccc"
     * @param pressedTransparency   不透明度	16进制值
     *                              100%	FF
     *                              95%	F2
     *                              90%	E6
     *                              85%	D9
     *                              80%	CC
     *                              75%	BF
     *                              70%	B3
     *                              65%	A6
     *                              60%	99
     *                              55%	8C
     *                              50%	80
     *                              45%	73
     *                              40%	66
     *                              35%	59
     *                              30%	4D
     *                              25%	40
     *                              20%	33
     *                              15%	26
     *                              10%	1A
     *                              5%	0D
     *                              0%	00
     * @param strokeWidth
     * @param strokeColor
     * @param radius
     * @param width
     * @param height
     * @return
     */
    public static StateListDrawable getPressedSelector(String solidColor, int strokeWidth, String strokeColor, float radius, String pressedTransparency, int width, int height) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        if (TextUtils.isEmpty(solidColor)) {
            solidColor = "#00000000";//透明
        }
        GradientDrawable normal = getCornerRecDrawable(Color.parseColor(solidColor), strokeWidth, strokeColor, radius, width, height);
        String solidColor_suffix = solidColor.split("#")[1];
        if (solidColor_suffix.length() == 8) {//有透明度
            solidColor_suffix = solidColor_suffix.substring(2, solidColor_suffix.length());
        }
        String pressedColor = "#" + pressedTransparency + solidColor_suffix;
        GradientDrawable pressed = getCornerRecDrawable(Color.parseColor(pressedColor), strokeWidth, strokeColor, radius, width, height);
        return getPressedSelector(normal, pressed, null);
    }

    /**
     * 生成按下50%透明度的背景色的Selector
     *
     * @param solidColor,如"#cccccc"
     * @param strokeWidth
     * @param strokeColor
     * @param radius
     * @return
     */
    public static StateListDrawable getPressedSelector(String solidColor, float radius, int height, int strokeWidth, String strokeColor) {
        return getPressedSelector(solidColor, strokeWidth, strokeColor, radius, "80", 2 * height, height);
    }

    /**
     */
    public static StateListDrawable getPressedSelector(int solidColor, float radius, int height) {
        return getPressedSelector("#" + Integer.toHexString(solidColor), radius, height);
    }

    /**
     */
    public static StateListDrawable getPressedSelector(String solidColor, float radius, int height) {
        return getPressedSelector(solidColor, 0, solidColor, radius, "80", 2 * height, height);
    }


    /**
     */
    public static StateListDrawable getCornerSolidSelector(String solidColor, float radius) {
        return getPressedSelector(solidColor, 0, solidColor, radius, "80", (int) (4 * radius), (int) (2 * radius));
    }

    /**
     */
    public static StateListDrawable getRectangleSelector(String solidColor, int height) {
        return getPressedSelector(solidColor, 0, solidColor, 0, "80", 2 * height, height);
    }

    /**
     * 边框
     */
    public static StateListDrawable getCornerStrokeSelector(String strokeColor, int strokeWidthPx, float radius) {
        return getPressedSelector("", strokeWidthPx, strokeColor, radius, "11", (int) (4 * radius), (int) (2 * radius));
    }

    public static StateListDrawable getPressedSelector(int solidColor, int pressedSolidColor, int strokeWidth, String strokeColor, float radius, int width, int height) {
        GradientDrawable normal = getCornerRecDrawable(solidColor, strokeWidth, strokeColor, radius, width, height);
        GradientDrawable pressed = getCornerRecDrawable(pressedSolidColor, strokeWidth, strokeColor, radius, width, height);
        return getPressedSelector(normal, pressed, null);
    }

    public static StateListDrawable getPressedSelector(Drawable normal, Drawable pressed) {
        return getPressedSelector(normal, pressed, null);
    }

    /**
     * 获取按下/抬起的selector
     *
     * @param normal
     * @param pressed
     * @return
     */
    public static StateListDrawable getPressedSelector(Drawable normal, Drawable pressed, Drawable disabled) {
        if (pressed != null && normal != null) {
            StateListDrawable stateListDrawable = new StateListDrawable();
            //注意:-负号表示该属性值为false
            addDrawableState(stateListDrawable, new int[]{-android.R.attr.state_enabled}, disabled);
            addDrawableState(stateListDrawable, new int[]{android.R.attr.state_pressed}, pressed);
            addDrawableState(stateListDrawable, new int[]{}, normal);
            return stateListDrawable;
        }
        return null;
    }


    public static StateListDrawable getCheckedSelector(Drawable normal, Drawable checked) {
        return getCheckedSelector(normal, checked, null);
    }

    /**
     * 获取选中/未选中的selector
     *
     * @param normal
     * @param checked
     * @return
     */
    public static StateListDrawable getCheckedSelector(Drawable normal, Drawable checked, Drawable disabled) {
        if (checked != null && normal != null) {
            StateListDrawable stateListDrawable = new StateListDrawable();
            //注意:-负号表示该属性值为false
            addDrawableState(stateListDrawable, new int[]{-android.R.attr.state_enabled}, disabled);
            addDrawableState(stateListDrawable, new int[]{android.R.attr.state_checked}, checked);
            addDrawableState(stateListDrawable, new int[]{}, normal);
            return stateListDrawable;
        }
        return null;
    }


    /**
     * 为DrawableList添加指定状态下的Drawable
     *
     * @param listDrawable
     * @param stateSet
     * @param drawable
     */
    public static void addDrawableState(StateListDrawable listDrawable, int[] stateSet, Drawable drawable) {
        if (listDrawable != null && stateSet != null && drawable != null) {
            listDrawable.addState(stateSet, drawable);
        }
    }


    /**
     * ========================================================
     * http://www.jianshu.com/p/8c479ed24ca8
     * using the DrawableCompat in support v4 library
     *   textview.setCompoundDrawables不生效。需要 textview.setBackground
     * ========================================================
     */
    /**
     * @param context
     * @param originDrawable
     * @return
     */
    public static Drawable tintColorfulImg(Context context, Drawable originDrawable) {
        return tint(originDrawable,
                context.getResources().getColorStateList(R.color.selector_color_img), PorterDuff.Mode.SRC_ATOP);
    }

    public static Drawable tint(Drawable originDrawable, int color) {
        return tint(originDrawable, ColorStateList.valueOf(color));
    }

    public static Drawable tint(Drawable originDrawable, int color, PorterDuff.Mode tintMode) {
        return tint(originDrawable, ColorStateList.valueOf(color), tintMode);
    }

    public static Drawable tint(Drawable originDrawable, ColorStateList colorStateList) {
        return tint(originDrawable, colorStateList, null);
    }

    public static Drawable tint(Drawable originDrawable, ColorStateList colorStateList, PorterDuff.Mode tintMode) {
        Drawable tintDrawable = DrawableCompat.wrap(originDrawable);
        tintDrawable.mutate();//避免相同icon重用出现问题
        if (tintMode != null) {
            DrawableCompat.setTintMode(tintDrawable, tintMode);
        }
        DrawableCompat.setTintList(tintDrawable, colorStateList);
        return tintDrawable;
    }

    public static Drawable tint(Drawable originDrawable, ColorStateList colorStateList, PorterDuff.Mode tintMode, boolean isMutate) {
        Drawable tintDrawable = tint(originDrawable, colorStateList, tintMode);
        if (isMutate)
            tintDrawable.mutate();//避免相同icon重用时出现问题
        return tintDrawable;
    }
}
