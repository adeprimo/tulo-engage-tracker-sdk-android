package adeprimo.com.tuloengagetracker;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import adeprimo.com.tuloengagetracker.utils.Logger;

final public class Helpers {

    private static final String TAG = Helpers.class.getSimpleName();

    private Helpers() {}

    public static int[] getResolution(Context context) {
        int width = -1, height = -1;
        try {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);

            width = dm.widthPixels;
            height = dm.heightPixels;
        } catch (Exception e) {
            Logger.warning(TAG,"Failed to get resolution", e.getMessage());
        }
        return new int[]{width,height};
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    public static String getAppName(Context context) {
        return context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString();
    }

    public static String getUserAgent(Context context) {
        return String.format("%s/%s (%s; %s %s)", getAppName(context), getAppVersionName(context), Build.MODEL,getOSName(), getOSVersion());
    }

    public static int getAppVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    public static String getLanguage() {
        return Locale.getDefault().toString().replace("_", "-");
    }

    public static int getDepth(Context context) {
        return 32;
    }

    public static String getTimezone() {
        return "" + (0 - TimeZone.getDefault().getOffset(new Date().getTime())) / 1000 / 60;
    }

    public static String getOSName() {
        return "Android";
    }

    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }


    public static String getAPILevel() {
        return String.format("%s", Build.VERSION.SDK_INT);
    }

    public static String getDevice() {
        return Build.MANUFACTURER + " " + Build.MODEL;
    }

    public static String getCountry() {
        return Locale.getDefault().getCountry();
    }

}
