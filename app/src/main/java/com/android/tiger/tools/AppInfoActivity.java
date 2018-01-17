package com.android.tiger.tools;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;


public class AppInfoActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    private String TAG = "MainActivityappinfo";
    ListView mAppInfoListView = null;
    List<AppInfo> mAppInfos = null;
    AppInfosListViewAdapter mInfosAdapter = null;
    DynamicReceiver dynamicReceiver = new DynamicReceiver();
    Switch mAppFiliterSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appinfo);
        //setup view
        mAppInfoListView = (ListView) this.findViewById(R.id.appinfo_list);
        mAppFiliterSwitch = (Switch) findViewById(R.id.list_switch);
        mAppFiliterSwitch.setOnCheckedChangeListener(this);
        registerDynamicReceiver();
        //init
        mAppInfos = getAppsInfo();
        updateUI(mAppInfos);

        // 发送更新系统图库广播
//        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
//                Uri.fromFile(new File("/sdcard/Pictures"))));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d(TAG, "-onCheckedChanged-" + " buttonView = " + buttonView + " isChecked = " + isChecked);
        switch (buttonView.getId()) {
            case R.id.list_switch:
                if (isChecked) {
                    mAppInfos = getPackageInfos();
                    updateUI(mAppInfos);
                    Log.d(TAG, "-onCheckedChanged-" + isChecked);
                } else {
                    Log.d(TAG, "-onCheckedChanged-" + isChecked);
                    mAppInfos = getAppsInfo();
                    updateUI(mAppInfos);
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dynamicReceiver);
    }

    public void updateUI(List<AppInfo> appInfos) {
        if (null != appInfos) {
            mInfosAdapter = new AppInfosListViewAdapter(getApplication(), appInfos);
            mAppInfoListView.setAdapter(mInfosAdapter);
        }
    }

    List<AppInfo> getAppsInfo() {
        List<AppInfo> appInfos = new ArrayList<AppInfo>();
        PackageManager packageManager = getApplication().getPackageManager();
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> apps = packageManager.queryIntentActivities(mainIntent, 0);
        Log.d(TAG, "queryIntentActivities got " + apps.size() + " apps");
        for (ResolveInfo app : apps) {
            String appName = app.activityInfo.loadLabel(packageManager).toString();
            String packageName = app.activityInfo.packageName;
            Drawable drawable = app.loadIcon(packageManager);
            AppInfo appInfo = new AppInfo(appName, packageName, drawable);
            appInfos.add(appInfo);
            int sourceWidth = drawable.getIntrinsicWidth();
            int sourceHeight = drawable.getIntrinsicHeight();
            Log.d(TAG, appName + "--sourceWidth=" + sourceWidth + " sourceHeight=" + sourceHeight);
        }
        return appInfos;
    }

    public List<AppInfo> getPackageInfos() {
        PackageManager packageManager = getApplication().getPackageManager();
        List<PackageInfo> packgeInfos = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        mAppInfos = new ArrayList<AppInfo>();
        for (PackageInfo packgeInfo : packgeInfos) {
            int appNumber0 = packgeInfo.applicationInfo.flags;
            int appNumber1 = packgeInfo.applicationInfo.FLAG_SYSTEM;
            int appNumber = packgeInfo.applicationInfo.flags & packgeInfo.applicationInfo.FLAG_SYSTEM;
            String appName = packgeInfo.applicationInfo.loadLabel(packageManager).toString();
            String packageName = packgeInfo.packageName;
            Drawable drawable = packgeInfo.applicationInfo.loadIcon(packageManager);
            AppInfo appInfo = new AppInfo(appName, packageName, drawable);
            Log.d(TAG, " appNumber=" + appNumber + " appNumber0=" + appNumber0 + " appNumber1=" + appNumber1);
            mAppInfos.add(appInfo);
        }
        return mAppInfos;
    }

    private void registerDynamicReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PACKAGE_ADDED");
        filter.addAction("android.intent.action.PACKAGE_REMOVED");
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        registerReceiver(dynamicReceiver, filter);
    }


    class DynamicReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.w(TAG, "DynamicReceiver-onReceive--");
            Log.w(TAG, "DynamicReceiver-onReceive--" + intent.getDataString());
//             Toast.makeText(context,"onReceive",Toast.LENGTH_SHORT).show();
        }
    }

}

