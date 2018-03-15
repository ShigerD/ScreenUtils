package com.shiger.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.shiger.tools.Utils.ImageDownloadTool;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image);
        mImageView = (ImageView) findViewById(R.id.image);

        mImageDownloadTool = new ImageDownloadTool();
        mImageDownloadTool.setmOnImageDownloadedListener(this);
        mImageDownloadTool.downloadImage("http://news.sjzcity.com/uploadpic/20111078522767.jpg");

    }

    @Override
    public void onDownloadFinish() {
        mImageView.setImageBitmap(mImageDownloadTool.getImage());

        CommomDialog commomDialog = new CommomDialog(this, R.style.dialog, "您确定删除此信息？",
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
        commomDialog.show();

    }

}
