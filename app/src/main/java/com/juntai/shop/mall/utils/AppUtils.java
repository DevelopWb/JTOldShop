package com.juntai.shop.mall.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.juntai.shop.mall.MyApp;

/**
 * @aouther Ma
 * @date 2019/3/13
 */
public class AppUtils {
    public final static String SP_KEY_LOGIN = getAppName() + "login";
    public final static String SP_KEY_INFO = getAppName() + "myInfo";
    /**
     * 获取Fileprovider
     * @return
     */
    public static String getFileprovider(){
        return AppUtils.getPackageName(MyApp.app) + ".fileProvider";
    }

    /**
     * 获取应用名称
     * @return
     */
    public static synchronized String getAppName() {
        try {
            PackageManager packageManager = MyApp.app.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    MyApp.app.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return MyApp.app.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取logo
     * @return
     */
    public static synchronized int getAppLogo() {
        try {
            int ic = MyApp.app.getResources().getIdentifier("ic_launcher", "mipmap", MyApp.app.getPackageName());
            return ic;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * [获取应用程序包名信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 打电话
     * @param phoneNum
     */
    public static void callPhone(Context context,String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }
}
