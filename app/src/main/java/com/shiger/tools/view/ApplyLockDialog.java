package com.shiger.tools.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.shiger.tools.R;

public class ApplyLockDialog extends Dialog implements View.OnClickListener{
    private TextView mContentTxt;
    private TextView mTitleTxt;
    private TextView mSubmitTxt;
    private TextView mCancelTxt;

    private Context mContext;
    private String mContent;
    private OnCloseListener mListener;
    private String mPositiveName;
    private String mNegativeName;
    private String mTitle;

    public ApplyLockDialog(Context context) {
        super(context);
        this.mContext = context;
    }


    public ApplyLockDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public ApplyLockDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.mContent = content;
    }

    public ApplyLockDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.mContent = content;
        this.mListener = listener;
    }

    protected ApplyLockDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public ApplyLockDialog setmTitle(String mTitle){
        this.mTitle = mTitle;
        return this;
    }

    public ApplyLockDialog setPositiveButton(String name){
        this.mPositiveName = name;
        return this;
    }

    public ApplyLockDialog setNegativeButton(String name){
        this.mNegativeName = name;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_lock_apply);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView(){
        mContentTxt = (TextView)findViewById(R.id.content);
        mTitleTxt = (TextView)findViewById(R.id.title);

        if(!TextUtils.isEmpty(mTitle)){
            mTitleTxt.setText(mTitle);
        }

    }

    @Override
    public void onClick(View v) {

    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm);
    }
}