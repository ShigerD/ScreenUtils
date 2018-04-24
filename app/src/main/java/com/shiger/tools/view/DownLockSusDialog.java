package com.shiger.tools.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shiger.tools.R;

public class DownLockSusDialog extends Dialog implements View.OnClickListener {
    private TextView mContentsTxt;
    private TextView mSubmitTxt;
    private TextView mLockNumView;
    private TextView mAvailabeLockTv;

    private Context mContext;

    private String mContents;
    private OnCloseListener mListener;
    private String mPositiveName;
    private String mNegativeName;
    private String mTitle;

    private Button mConfirmBtn;

    private int mChooseLockId = 0;

    private int mAvaiLockNum = 0;

    public DownLockSusDialog(Context context) {
        super(context);
        this.mContext = context;
    }


    public DownLockSusDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_lock_down_sus);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        mConfirmBtn = (Button)findViewById(R.id.submit);
        mConfirmBtn.setOnClickListener(this);
        mContentsTxt = (TextView) findViewById(R.id.content);
        mLockNumView = (TextView) findViewById(R.id.tv_lock_down_num);
        mAvailabeLockTv = (TextView)findViewById(R.id.avai_lock_num);
        if (mChooseLockId != 0) {
            mLockNumView.setText(String.valueOf(mChooseLockId));
            mContents = "已为您降" + String.valueOf(mChooseLockId) + "号地锁";
            mContentsTxt.setText(mContents);

            mAvailabeLockTv.setText(String.valueOf(mAvaiLockNum));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.submit:
                if(mListener != null){
                    mListener.onClick(this, true);
                }
                this.dismiss();

                break;
        }
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setChooseLockId(int mChooseLockId) {
        this.mChooseLockId = mChooseLockId;
    }

    public void setAvialabeLock(int avialabeLock) {
        this.mAvaiLockNum = avialabeLock;
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm);
    }
}