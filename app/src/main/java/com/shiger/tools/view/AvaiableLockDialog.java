package com.shiger.tools.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.shiger.tools.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class AvaiableLockDialog extends Dialog implements View.OnClickListener{

    private final String TAG = this.getClass().getSimpleName();

    private TextView mTitleTxt;
    private View mSubmit;
    private View mCancle;

    private Context mContext;
    private String mContent;
    private OnCloseListener mListener;
    private String mPositiveName;
    private String mNegativeName;
    private String mTitle;
    private EasyPickerView mEasyPickerViewLockNum;


    public AvaiableLockDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public AvaiableLockDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public AvaiableLockDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.mContent = content;
    }

    public AvaiableLockDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.mContent = content;
        this.mListener = listener;
    }

    protected AvaiableLockDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public AvaiableLockDialog setmTitle(String mTitle){
        this.mTitle = mTitle;
        return this;
    }

    public AvaiableLockDialog setPositiveButton(String name){
        this.mPositiveName = name;
        return this;
    }

    public AvaiableLockDialog setNegativeButton(String name){
        this.mNegativeName = name;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_lock_avaiable);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView(){
        mTitleTxt = (TextView)findViewById(R.id.title);
        mSubmit = (View) findViewById(R.id.submit);
        mSubmit.setOnClickListener(this);
        mCancle = (View) findViewById(R.id.cancel_img);
        mCancle.setOnClickListener(this);
        initPickerView();
        if(!TextUtils.isEmpty(mTitle)){
            mTitleTxt.setText(mTitle);
        }
    }

    private void initPickerView() {
        mEasyPickerViewLockNum = (EasyPickerView) findViewById(R.id.car_num);
        final ArrayList<String> hDataList = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            hDataList.add("" + i);

        mEasyPickerViewLockNum.setDataList(hDataList);
        mEasyPickerViewLockNum.setOnScrollChangedListener(new EasyPickerView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int curIndex) {
                Log.d(TAG, "--onScrollChanged-- " + curIndex);

            }

            @Override
            public void onScrollFinished(int curIndex) {
                Log.d(TAG, "--onScrollFinished--" + curIndex);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_img:
                if(mListener != null){
                    mListener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.submit:
                if(mListener != null){
                    mListener.onClick(this, true);
                }
                this.dismiss();

                break;
        }
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm);
    }
}