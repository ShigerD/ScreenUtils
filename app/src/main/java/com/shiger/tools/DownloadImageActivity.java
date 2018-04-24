package com.shiger.tools;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.shiger.tools.Utils.ImageDownloadTool;
import com.shiger.tools.view.ApplyLockDialog;
import com.shiger.tools.view.AvaiableLockDialog;
import com.shiger.tools.view.CommomDialog;
import com.shiger.tools.view.DownLockSusDialog;

import java.io.IOException;
import java.io.OutputStream;

import static java.security.AccessController.getContext;

/**
 * Created by Shiger on 3/13/18.
 * e-mail : tigerwithwings@foxmail.com
 */

public class DownloadImageActivity extends Activity implements ImageDownloadTool.OnImageDownloadedListener {

    private String TAG = this.getClass().getSimpleName();

    public static final String SD_DIR_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/webPic";
    private ImageView mImageView;

    private ImageDownloadTool mImageDownloadTool;

    private final int MSG_DOWNLOAD_FINISHED = 0;

    public static final String BTMUSIC_ACTION_MEDIA_BUTTON = "android.intent.btmusic.MEDIA_BUTTON";
    private final String MEDIA_CMD = "media_cmd";
    public static final int MEDIA_CONTROL_PLAY_PAUSE = 3;
    public static final int MEDIA_BTMUSIC_CONTROL_NEXT = 4;
    public static final int MEDIA_BTMUSIC_CONTROL_UP = 5;

    public static final int ACTION_PLAY = 1;
    public static final int ACTION_PAUSE = 2;
    public static final int ACTION_PLAY_PAUSE = 3;
    public static final int ACTION_NEXT = 4;
    public static final int ACTION_PREV = 5;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_DOWNLOAD_FINISHED:
                    showDownLockSus();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image);
        //image downloa test
    /*    mImageView = (ImageView) findViewById(R.id.download_image);
        mImageDownloadTool = new ImageDownloadTool();
        mImageDownloadTool.setmOnImageDownloadedListener(this);
        mImageDownloadTool.downloadImage("http://news.sjzcity.com/uploadpic/20111078522767.jpg");
        */
        String id = Build.ID;
        Log.d(TAG, "Build.ID--" + id);
        //kill package test
//        killPackageName(getApplicationContext(),"com.clw.btmusic");
//        startMediaService();
//        startMediaService(BTMUSIC_ACTION_MEDIA_BUTTON, MEDIA_CMD
//                , MEDIA_BTMUSIC_CONTROL_NEXT);

        startMediaService(ACTION_PAUSE);
    }

    @Override
    public void onDownloadFinish() {
        mImageView.setImageBitmap(mImageDownloadTool.getImage());
        mHandler.sendEmptyMessage(MSG_DOWNLOAD_FINISHED);
    }

    private void startMediaService(String action, String key, int value) {
        Intent mIntnetMedia = new Intent();
        mIntnetMedia.setAction(action);
        mIntnetMedia.setPackage("com.clw.btmusic");
        mIntnetMedia.putExtra(key, value);
        Log.d(TAG,"key = " + key + " value = " + value);
        startService(mIntnetMedia);

       /* try {
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }*/
    }

    private void startMediaService(int value) {
        String action = "android.intent.btmusic.MEDIA_BUTTON";
        String key = "media_cmd";
        Intent mIntnetMedia = new Intent();
        mIntnetMedia.setPackage("com.clw.btmusic");
        mIntnetMedia.setAction(action);
        mIntnetMedia.putExtra(key, value);
        startService(mIntnetMedia);
    }

    public static boolean killPackageName(Context context, String packageName) {
        OutputStream out = null;
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            am.killBackgroundProcesses(packageName);
            Process process = Runtime.getRuntime().exec("su");
            String cmd = "am force-stop " + packageName + " \n";
            out = process.getOutputStream();
            out.write(cmd.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }


    private void showDialog() {
        CommomDialog commomDialog = new CommomDialog(this, R.style.dialog, "是否开启降地锁？",
                new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        Log.d(TAG, "confirm---" + confirm);
                        if (confirm) {
                            Toast.makeText(getApplicationContext(), "点击确定", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
        commomDialog.setmTitle("提示信息");
        commomDialog.setNegativeButton("是");
        commomDialog.setPositiveButton("否");
        commomDialog.show();

/*       //test
        Dialog dialog = new Dialog(getApplicationContext());
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setTitle("title");
        dialog.show();*/
    }

    private void showApplyLockDialog() {
        ApplyLockDialog applyLockDialog = new ApplyLockDialog(getApplicationContext()
                , R.style.dialog);
//        applyLockDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        applyLockDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);  //TYPE_TOAST
        applyLockDialog.setmTitle("车位申请中...");
        applyLockDialog.show();
    }

    private void showAvailabeLockDialog() {
        AvaiableLockDialog avaiableLockDialog = new AvaiableLockDialog(getApplicationContext()
                , R.style.dialog);
//        applyLockDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        avaiableLockDialog.setmTitle("选择可用车位");
        avaiableLockDialog.show();
    }

    private void showDownLockSus(){
        DownLockSusDialog downLockSusDialog = new DownLockSusDialog(getApplicationContext()
        ,R.style.dialog);
        downLockSusDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);  //TYPE_TOAST
        downLockSusDialog.setAvialabeLock(12);
        downLockSusDialog.setChooseLockId(3);
        downLockSusDialog.show();
    }
}
