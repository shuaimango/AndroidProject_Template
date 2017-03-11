package com.mango.framework.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

import com.mango.framework.R;

public class Util_skipPage {

    public static void webViewBack(Activity activity, WebView webView) {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            activity.finish();
        }
    }

    public static void fragmentBack(Activity activity) {
        if (activity == null) {
            return;
        }
        try {
            Util_input.hideKeyboardFromActivity(activity);
            if (activity.getFragmentManager().getBackStackEntryCount() > 0) {
                activity.getFragmentManager().popBackStack(null, 0);
            } else {
                activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            activity.finish();
        }
    }

    public static void startFragment(Activity activity, String fragmentName) {
        startFragment(activity, fragmentName, null);
    }

    public static void startFragmentNoAnim(Activity activity, String fragmentName, Bundle args) {
        startFragment(activity, fragmentName, args, 0, 0);
    }

    public static void startFragmentFromBottom(Activity activity, String fragmentName, Bundle args) {
        startFragment(activity, fragmentName, args, R.animator.push_bottom_in, R.animator.push_bottom_out);
    }

    public static void startFragment(Activity activity, String fragmentName, Bundle args) {
        startFragment(activity, fragmentName, args, R.animator.push_right_in, R.animator.push_right_out);
    }

    public static void startFragment(Activity activity, String fragmentName, int fragmentId, Bundle args) {
        startFragment(activity, fragmentName, fragmentId, args, R.animator.push_right_in, R.animator.push_right_out);
    }

    public static void startFragment(Activity activity, String fragmentName, Bundle args, int animIn, int animOut) {
        //activity_fragment为默认id
        Resources res = activity.getResources();
        startFragment(activity, fragmentName, res.getIdentifier("activity_fragment", "id", activity.getPackageName()), args, animIn, animOut);
    }


    public static void startFragment(Activity activity, String fragmentName, int fragmentId, Bundle args, int animIn, int animOut) {
        startFragment(activity, fragmentName, args, fragmentId, animIn, animOut, true);
    }

    public static void startFragment(Activity activity, String fragmentName, Bundle args, int fragmentId, int animIn, int animOut, boolean isAddToBackStack) {
        try {
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            Fragment fragment = (Fragment) Class.forName(fragmentName).newInstance();
            if (isAddToBackStack)
                ft.addToBackStack(null);
            if (animIn > 0 && animOut > 0) {
                ft.setCustomAnimations(animIn, animOut, animIn, animOut);
            }
            if (args != null)
                fragment.setArguments(args);
            ft.add(fragmentId, fragment).commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void startActivity(Context context, String className) {
        startActivity(context, className, null);
    }

    public static final void startActivity(Context context, String className, Bundle bundle) {
        context.startActivity(getIntent(context, className, bundle, 0));
    }

    public static final void startActivity(Context context, String className, Bundle bundle, int flag) {
        context.startActivity(getIntent(context, className, bundle, flag));
    }

    public static final void startActivityForResult(Activity activity, String className, Bundle bundle, int requestCode) {
        activity.startActivityForResult(getIntent(activity, className, bundle, 0), requestCode);
    }

    private static Intent getIntent(Context context, String className, Bundle bundle, int flag) {
        Intent intent = null;
        try {
            intent = Util_skipPage.getRightIntent(context);
            intent.setClass(context, Class.forName(className));
            if (bundle != null)
                intent.putExtras(bundle);
            if (flag > 0) {
                intent.addFlags(flag);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return intent;
    }


    public static Intent getRightIntent(Context context) {
        Intent intent = new Intent();
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        return intent;
    }

    public static void toCustomProtocolActivity(Context context, Uri uri) {
        Intent intent = getRightIntent(context);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        context.startActivity(intent);
    }
}
