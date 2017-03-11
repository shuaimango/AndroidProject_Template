package com.mango.framework.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;

import com.mango.framework.util.Util_input;
import com.mango.framework.util.Util_skipPage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Set;


public abstract class BaseActivity extends AppCompatActivity implements IPageInit {
    private static final String TAG = "BaseActivity";
    private ViewStub viewStub_titlebar;
    public Dialog loadingDialog;// http://blog.csdn.net/yihongyuelan/article/details/9829313// 创建状态栏的管理实例
    protected Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initViews(getContentView());
//        viewStub_titlebar = (ViewStub) findViewById(R.id.viewStub_titlebar);
//        if (viewStub_titlebar != null) {
//                viewStub_titlebar.setLayoutResource(R.layout.titlebar_common);
//                viewStub_titlebar.inflate();
//                viewStub_titlebar.setVisibility(View.VISIBLE);
//        }
        handleIntent(getIntent());
        initVariables(savedInstanceState);
        setViews(getContentView());
        loadData();
    }

    @Override
    public void loadData() {

    }

    protected void handleIntent(Intent intent) {
        Uri uri = intent.getData();//获得Uri全部路径
        if (uri == null) {
            extras = intent.getExtras();
            if(extras==null)
                extras = new Bundle();
        } else {
            extras = new Bundle();
            Log.e(TAG, uri.toString());
            Set<String> queryParameterNames = uri.getQueryParameterNames();
            //获得迭代器
            Iterator it = queryParameterNames.iterator();
            //判断是否还有元素可以迭代
            try {
                while (it.hasNext()) {
                    //返回迭代的下一个元素。
                    String key = (String) it.next();
//                    extras.putString(key, uri.getQueryParameter(key));
                    extras.putString(key, URLDecoder.decode(uri.getQueryParameter(key), "UTF-8"));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onBackPressed() {
        Util_input.hideKeyboardFromActivity(getActivity());
        Util_skipPage.fragmentBack(getActivity());
    }

    public void showLoadingDialog(final boolean isCancel) {
        try {
            if (loadingDialog == null) {
//                loadingDialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
//                View view = View.inflate(getActivity(), R.layout.layout_loading_dlg, null);
//                loadingDialog.setContentView(view);
//                ImageView iv_loading = (ImageView) loadingDialog.findViewById(R.id.iv);// 得到加载view
//                iv_loading.setBackgroundResource(R.drawable.loading_frame);
//                AnimationDrawable animationDrawable = (AnimationDrawable) iv_loading.getBackground();
//                animationDrawable.start();
//                loadingDialog.setCancelable(isCancel);// 是否可以用“返回键”取消
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (isCancel) {
//                            loadingDialog.cancel();
//                        }
//                    }
//                });
//                // 设置位置
//                Window dialogWindow = loadingDialog.getWindow();
//                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                dialogWindow.setGravity(Gravity.TOP);
//                lp.y = Util_device.dp2Px(this, 100); // 距离顶部的距离
//                dialogWindow.setAttributes(lp);
            }
            loadingDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error error) {
            error.printStackTrace();
        }
    }

    public void dismissLoadingDialog() {
        try {
            if (loadingDialog != null) {
                loadingDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            dismissLoadingDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    protected BaseActivity getActivity() {
        return this;
    }

    /**
     * 用于点击空白处，隐藏输入法 当Activity中含有scrollview控件时，则需给scrollview添加OnTouchListener事件；
     * 当不包含scrollview控件时，则重写Activity的onTouchEvent方法
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                if (getCurrentFocus() != null
                        && getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public View getContentView() {
        return getWindow().getDecorView().findViewById(android.R.id.content);
    }

}
