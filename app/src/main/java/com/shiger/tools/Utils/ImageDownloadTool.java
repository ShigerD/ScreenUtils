package com.shiger.tools.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Shiger on 3/13/18.
 * e-mail : tigerwithwings@foxmail.com
 */

public class ImageDownloadTool {

    private Bitmap mBitmap;

    public interface OnImageDownloadedListener {
        public void onDownloadFinish();
    }

    public ImageDownloadTool() {

    }

    private OnImageDownloadedListener mOnImageDownloadedListener;

    public void setmOnImageDownloadedListener(OnImageDownloadedListener onImageDownloadedListener) {
        mOnImageDownloadedListener = onImageDownloadedListener;
    }

    public void downloadImage(String url){
        new ImageDownloadTask().execute(url);
    }

    public Bitmap getImage(){
        return mBitmap;
    }

    /**
     * 异步线程下载图片
     */
    class ImageDownloadTask extends AsyncTask<String, Integer, Void> {

        protected Void doInBackground(String... params) {
            mBitmap = getImageInputStream((String) params[0]);
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
           /* Message message = new Message();
            message.what = 0x123;
            handler.sendMessage(message);*/
           if (mOnImageDownloadedListener!=null){
               mOnImageDownloadedListener.onDownloadFinish();
           }
        }

    }

    /**
     * 获取网络图片
     *
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
    public Bitmap getImageInputStream(String imageurl) {
        URL url;
        HttpURLConnection connection = null;
        Bitmap bitmap = null;
        try {
            url = new URL(imageurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(6000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 保存位图到本地
     *
     * @param bitmap
     * @param path   本地路径
     * @return void
     */
    public void savaImage(Bitmap bitmap, String path) {
        File file = new File(path);
        FileOutputStream fileOutputStream = null;
        //文件夹不存在，则创建它
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            fileOutputStream = new FileOutputStream(path + "/" + System.currentTimeMillis() + ".png");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
