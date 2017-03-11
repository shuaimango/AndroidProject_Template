package com.mango.framework.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import java.lang.reflect.Field;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @content 获取设备的相关信息
 */
public class Util_device {
    public static final String TAG = "Util_device";

    public static int getStatusBarHeight(Context context) {
        int mStatusBarHeightPx = 0;
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            mStatusBarHeightPx = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return mStatusBarHeightPx;
    }

    public static int getVersionCode(Context sContext) {
        PackageManager manager = sContext.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(sContext.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取屏幕方向： 1 、横屏 ORIENTATION_LANDSCAPE 2 、竖屏 ORIENTATION_PORTRAIT
     *
     * @return
     */
    public static int getScreenOrientation(Context sContext) {
        Configuration config = sContext.getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏
            return 1;
        } else if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 竖屏
            return 2;
        }
        return 0;
    }

    public static int dp2Px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int[] getScreenWH(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return  new int[]{ dm.widthPixels, dm.heightPixels};
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float px2Dp(Context context, int pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return ((float) pxValue / scale + 0.5f);
    }

    /**
     * 手机IMEI号
     */
    public static String getDeviceId(Context sContext) {
        String deviceId = "000000000000";
        try {
            TelephonyManager tm = (TelephonyManager) sContext.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceId;
    }

    /**
     * @return
     * @content 返回设备mac地址
     */
    public static String getMacAddress(Context sContext) {
        if (sContext == null) {
            return "";
        }
        //6.0及以上不能用老的方式获取mac地址,否则将一直返回02:00:00:00:00:00
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                while (interfaces.hasMoreElements()) {
                    NetworkInterface iF = interfaces.nextElement();

                    byte[] addr = iF.getHardwareAddress();
                    if (addr == null || addr.length == 0) {
                        continue;
                    }

                    StringBuilder buf = new StringBuilder();
                    for (byte b : addr) {
                        buf.append(String.format("%02X:", b));
                    }
                    if (buf.length() > 0) {
                        buf.deleteCharAt(buf.length() - 1);
                    }
                    String mac = buf.toString();
                    Log.d(TAG, "interfaceName=" + iF.getName() + ", mac=" + mac);
                    return mac;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        } else {
            WifiManager wifi = (WifiManager) sContext.getSystemService(Context.WIFI_SERVICE);
            if (wifi == null) {
                return "";
            }
            WifiInfo info = wifi.getConnectionInfo();
            if (info == null) {
                return "";
            }
            return info.getMacAddress();
        }
    }

    /**
     * @return
     * @content 获得语言编码
     */
    public static String getLanguage(Context sContext) {
        return sContext.getResources().getConfiguration().locale.getLanguage();
    }

    /**
     * @return
     * @content 获得国家编码
     */
    public static String getCountry(Context sContext) {
        return sContext.getResources().getConfiguration().locale.getCountry();
    }

    /**
     * @return
     * @content 获取android的版本
     * @author ShuLQ
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * @param context
     * @return
     * @content 获取联网类型
     * @author ShuLQ
     */
    public static String getNetType(Context sContext) {
        ConnectivityManager connectionManager = (ConnectivityManager) sContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            return networkInfo.getTypeName();
        }
        return null;
    }

    /**
     * 获取联网类型 2G 3G 4G WIFI UNKNOWN NONE
     *
     * @return
     */
    public static String getNetTypeInChina(Context sContext) {
        ConnectivityManager cm = (ConnectivityManager) sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return "WIFI";
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {
                    case 1:
                    case 2:
                    case 4:
                        return "2G";
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 12:
                    case 15:
                        return "3G";
                    case 13:
                        return "4G";
                    default:
                        return "UNKNOWN";
                }
            } else {
                return "UNKNOWN";
            }
        }
        return "";
    }

    /**
     * 设备运营商
     *
     * @return
     */
    public static String getOperators(Context sContext) {
        TelephonyManager tm = (TelephonyManager) sContext.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            String imsi = tm.getSubscriberId();
            // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
            if (imsi == null) {
                return "NONE";
            }
            if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                return "移动";
            } else if (imsi.startsWith("46001")) {
                return "联通";
            } else if (imsi.startsWith("46003")) {
                return "电信";
            } else {
                return "UNKNOWN";
            }
        } else {
            return "NONE";
        }
    }

    /**
     * 设备运营商制式
     *
     * @return
     */
    public static String getNetworkOperatorName(Context sContext) {
        TelephonyManager tm = (TelephonyManager) sContext.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getNetworkOperatorName();
    }

    /**
     * 获取手机信息
     */
    public static String getPhoneInfo(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String mtyb = Build.BRAND;// 手机品牌
        String mtype = Build.MODEL; // 手机型号
        int sdk_int = Build.VERSION.SDK_INT; // 系统版本
        String imei = tm.getDeviceId();
        String imsi = tm.getSubscriberId();
        String numer = tm.getLine1Number(); // 手机号码
        String serviceName = tm.getSimOperatorName(); // 运营商
        return "系统版本: " + sdk_int + "  " + "品牌: " + mtyb + "  " + "型号: "
                + mtype + "  " + "版本: Android " + Build.VERSION.RELEASE + "  "
                + "IMEI: " + imei + "  " + "IMSI: " + imsi + "  "
                + "手机号码: "
                + numer + "  " + "运营商: " + serviceName;
    }
}
