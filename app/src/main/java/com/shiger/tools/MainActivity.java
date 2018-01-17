package com.shiger.tools;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.shiger.tools.floatwindow.FloatWindowService;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private String TAG = "MainActivity";
    Button btnStartFloatWindow;
    TextView btnStartGetScreenPara, btnStartAppInfo;
    TextView btnStartBright;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
//        getAppDetailSettingIntent(getApplicationContext());
        getRunningActivityName(getApplicationContext());
        Log.e("Main", "test");
//        timer.scheduleAtFixedRate(new RefreshTask(), 0, 500);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.start_appInfo:
                Intent appInfoIntent = new Intent(MainActivity.this, AppInfoActivity.class);
                startActivity(appInfoIntent);
            case R.id.start_float_window:
                Intent service = new Intent(MainActivity.this, FloatWindowService.class);
                startService(service);
//                finish();
                break;
            case R.id.start_get_screen_para:
                Intent getScreenParaIntent = new Intent(MainActivity.this, GetScreenParaAcitivity.class);
                startActivity(getScreenParaIntent);
                break;
            case R.id.start_get_screen_bright:
                Intent intent_bright = new Intent(MainActivity.this, ScreenBrightnessActivity.class);
                startActivity(intent_bright);
                break;
        }
    }

    private void setupView() {
        btnStartFloatWindow = (Button) findViewById(R.id.start_float_window);
        btnStartGetScreenPara = (TextView) findViewById(R.id.start_get_screen_para);
        btnStartBright = (Button) findViewById(R.id.start_get_screen_bright);
        btnStartAppInfo = (TextView) findViewById(R.id.start_appInfo);
        btnStartFloatWindow.setOnClickListener(this);
        btnStartGetScreenPara.setOnClickListener(this);
        btnStartBright.setOnClickListener(this);
        btnStartAppInfo.setOnClickListener(this);
    }

    private void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);
    }

    private class RefreshTask extends TimerTask {

        @Override
        public void run() {
            String packagename = getRunningActivityName(getApplicationContext());
//
        }
    }

    private String getRunningActivityName(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        Log.e("runningActivity", "-" + runningActivity);
        return runningActivity;
//        String mpackageName=null;
//        ActivityManager mActivityManager =(ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
//        if(Build.VERSION.SDK_INT > 20){
//             mpackageName = mActivityManager.getRunningAppProcesses().get(0).processName;
//        }else{
//             mpackageName =  mActivityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
//        }
//        Log.e("getRunningActivityName","-"+mpackageName );
//        return mpackageName;
    }


    public String getActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map<Object, Object> activities = (Map<Object, Object>) activitiesField.get(activityThread);
            if (activities == null)
                return null;
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    Log.d("getActivity()--", activity.getPackageName());
                    return activity.getPackageName();
                }
            }
            return null;
        } catch (Exception e) {
            Log.e("getActivity", "--error");
            return null;
        }

    }
}