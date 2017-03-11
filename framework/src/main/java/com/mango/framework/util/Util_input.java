package com.mango.framework.util;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Util_input {

    // 强制开启虚拟键盘
    public static void showKeyboard(final View v) {
        final InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imm.showSoftInput(v,
                        InputMethodManager.SHOW_FORCED);
            }
        }, 300);
    }

    // 如果输入法打开则关闭，如果没打开则打开
    public static void switchKeyboard(View v) {
        final InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 300);
    }

    public static void hideKeyboardFromActivity(final View v) {
        try {
            if (v.getApplicationWindowToken() != null) {
                final InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                }, 300);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭输入法优先使用此方法
     * 需要在dialog.dismiss()之前调用才生效
     *
     * @param v
     */
    public static void hideKeyboard(View v) {
        try {
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideKeyboardFromActivity(Activity activity) {
        try {
            if (activity != null && activity.getCurrentFocus() != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void requestShowKeybord(View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
