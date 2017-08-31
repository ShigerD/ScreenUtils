package com.android.tiger.screenmanager;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;

/**
 * Created by yangcanhu on 8/16/17.
 */

public class ScreenBrightnessActivity extends Activity implements SeekBar.OnSeekBarChangeListener{
    private String TAG= ScreenBrightnessActivity.class.getSimpleName();

    private SeekBar mSeekbar;

    private int seekbarPosition;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_screen_brightness);


        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams params = getWindow().getAttributes();  //获取对话框当前的参数值
        params.height = (int) (display.getHeight() * 0.15);   //高度设置为屏幕的1.0
        params.width = (int) (display.getWidth() * 1.0);    //宽度设置为屏幕的0.8
        params.alpha = 0.5f;      //设置本身透明度
        params.dimAmount = 0.5f;      //设置黑暗度
        getWindow().setAttributes(params);

        mSeekbar =(SeekBar)findViewById(R.id.seekbar);
        mSeekbar.setOnSeekBarChangeListener(this);
        mSeekbar.setMax(255);
        int brightness=getScreenBrightness();
        mSeekbar.setProgress(brightness);


//        int brightMode=getScreenMode();
//        int brightness=getScreenBrightness();
//        setScreenBrightness(1);
//        saveScreenBrightness(1);
//        getScreenBrightness();
    }

    /**
     * 获得当前屏幕亮度的模式
     * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
     * SCREEN_BRIGHTNESS_MODE_MANUAL=0  为手动调节屏幕亮度
     */
    private int getScreenMode(){
        int screenMode=0;
        try{
            screenMode = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
            Log.w(TAG,"brightMode--"+screenMode);
        }
        catch (Exception localException){

        }
        return screenMode;
    }
    /**
     * 获得当前屏幕亮度值  0--255
     */
    private int getScreenBrightness(){
        int screenBrightness=255;
        try{
            screenBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            Log.w(TAG,"brightness--"+screenBrightness);
        }
        catch (Exception localException){

        }
        return screenBrightness;
    }
    /**
     * 设置当前屏幕亮度的模式
     * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
     * SCREEN_BRIGHTNESS_MODE_MANUAL=0  为手动调节屏幕亮度
     */
    private void setScreenMode(int paramInt){
        try{
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, paramInt);
        }catch (Exception localException){
            localException.printStackTrace();
        }
    }
    /**
     * 设置当前屏幕亮度值  0--255
     */
    private void saveScreenBrightness(int paramInt){
        try{
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, paramInt);
        }
        catch (Exception localException){
            localException.printStackTrace();
        }
    }
    /**
     * 保存当前的屏幕亮度值，并使之生效
     */
    private void setScreenBrightness(int paramInt){
        Window localWindow = getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
        float f = paramInt / 255.0F;
        localLayoutParams.screenBrightness = f;
        localWindow.setAttributes(localLayoutParams);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.w(TAG,"seek-int-"+progress);
        setScreenBrightness(progress);
        saveScreenBrightness(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        this.seekbarPosition=seekBar.getProgress();
    }

    @Override
    protected void onResume() {
        Log.e(TAG,"onResume--seekbarPosition"+this.seekbarPosition);
//        int brightness=getScreenBrightness();
        mSeekbar.setProgress(this.seekbarPosition);
        super.onResume();
    }
}
