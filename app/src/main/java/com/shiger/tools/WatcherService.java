package com.shiger.tools;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;


/**
 * Created by Shiger on 12/19/17.
 * e-mail : tigerwithwings@foxmail.com
 */

public class WatcherService extends AccessibilityService {
    private String TAG = "WatcherService";


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "-onAccessibilityEvent- event = " + event);
    }

    @Override
    protected void onServiceConnected() {
        Log.d(TAG, "-onServiceConnected-");
        super.onServiceConnected();
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        Log.d(TAG, "-onKeyEvent- event" + event);
        return super.onKeyEvent(event);
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "-onInterrupt-");
    }
}
