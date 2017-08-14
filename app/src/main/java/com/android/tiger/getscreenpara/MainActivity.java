package com.android.tiger.getscreenpara;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;

import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView tv_screen_hight;
    private TextView tv_screen_width;
    private TextView tv_screen_hight_real;
    private TextView tv_screen_width_real;
    private TextView tv_density;
    private TextView tv_densityDpi;
    private TextView tv_sw;

    private int sereenRealHeight = 0;
    float mDensity = 0, mDensityDpi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //沉浸式状态栏
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_main);
        tv_screen_hight = (TextView) findViewById(R.id.para_screen_hignt);
        tv_screen_width = (TextView) findViewById(R.id.para_screen_width);
        tv_screen_hight_real = (TextView) findViewById(R.id.para_screen_hignt_real);
        tv_screen_width_real = (TextView) findViewById(R.id.para_screen_width_real);
        tv_density = (TextView) findViewById(R.id.tv_density);
        tv_densityDpi = (TextView) findViewById(R.id.tv_densityDpi);
        tv_sw = (TextView) findViewById(R.id.tv_sw);
        getDisplayInfomation();
        getDensity();
        String str_sw = getScreenSW() + " ---- " + getxdpi();
        tv_sw.setText(str_sw);
    }

    private String getxdpi() {
        Log.w(TAG, "Density is " + (int) mDensityDpi);
        if ((int) mDensityDpi >= 480) {
            return "xxhdpi";
        } else if ((int) mDensityDpi >= 320) {
            return "xhdpi";
        } else if ((int) mDensityDpi >= 240) {
            return "hdpi";
        } else if ((int) mDensityDpi >= 160) {
            return "mdpi";
        } else {
            return "ldpi";
        }
    }

    public static boolean isInstalled(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> installedList = packageManager.getInstalledPackages(0);
        for (PackageInfo pi : installedList) {
            if (pi.packageName.contains(packageName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<String>();
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        return packageNames.contains(packageName);
    }

    private String getScreenSW() {
        float sw = sereenRealHeight / mDensity;
        return String.valueOf(sw);
    }

    private void getDisplayInfomation() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        Log.w(TAG, "the screen size is " + point.toString());
        tv_screen_width.setText(String.valueOf(point.x));
        tv_screen_hight.setText(String.valueOf(point.y));
        getWindowManager().getDefaultDisplay().getRealSize(point);
        tv_screen_width_real.setText(String.valueOf(point.x));
        tv_screen_hight_real.setText(String.valueOf(point.y));
        sereenRealHeight = point.y;
        Log.w(TAG, "the screen real size is " + point.toString());
    }

    private void getDensity() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        mDensity = displayMetrics.density;
        mDensityDpi = displayMetrics.densityDpi;
        tv_density.setText(String.valueOf(mDensity));
        tv_densityDpi.setText(String.valueOf(mDensityDpi));
        Log.w(TAG, "Density is " + mDensity + " densityDpi is " + mDensityDpi
                + " height: " + displayMetrics.heightPixels + " width: " + displayMetrics.widthPixels);
    }
}
