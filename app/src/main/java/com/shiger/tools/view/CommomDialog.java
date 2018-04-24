package com.shiger.tools.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.shiger.tools.R;

public class CommomDialog extends Dialog implements View.OnClickListener{
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

    public CommomDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public CommomDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.mContent = content;
    }

    public CommomDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.mContent = content;
        this.mListener = listener;
    }

    protected CommomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public CommomDialog setmTitle(String mTitle){
        this.mTitle = mTitle;
        return this;
    }

    public CommomDialog setPositiveButton(String name){
        this.mPositiveName = name;
        return this;
    }

    public CommomDialog setNegativeButton(String name){
        this.mNegativeName = name;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView(){
        mContentTxt = (TextView)findViewById(R.id.content);
        mTitleTxt = (TextView)findViewById(R.id.title);
        mSubmitTxt = (TextView)findViewById(R.id.submit);
        mSubmitTxt.setOnClickListener(this);
        mCancelTxt = (TextView)findViewById(R.id.cancel);
        mCancelTxt.setOnClickListener(this);

        mContentTxt.setText(mContent);
        if(!TextUtils.isEmpty(mPositiveName)){
            mSubmitTxt.setText(mPositiveName);
        }

        if(!TextUtils.isEmpty(mNegativeName)){
            mCancelTxt.setText(mNegativeName);
        }

        if(!TextUtils.isEmpty(mTitle)){
            mTitleTxt.setText(mTitle);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                if(mListener != null){
                    mListener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.submit:
                if(mListener != null){
                    mListener.onClick(this, true);
                }
                break;
        }
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm);
    }
}