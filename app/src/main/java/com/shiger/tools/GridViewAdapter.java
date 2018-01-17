package com.shiger.tools;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class GridViewAdapter  extends BaseAdapter {

    private static final String TAG = "GridViewAdapter";
    private Context mContext;
    private LayoutInflater mInflater;
    private int mItemLayoutResource;
    private List<FunInfos> mFunInfosList;
    private OnStartActivtyInterface mOnStartActivtyInterface;

    public GridViewAdapter(Context context , List<FunInfos> funInfosList , OnStartActivtyInterface onStartActivtyInterface) {
        mItemLayoutResource = R.layout.grid_item;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        mFunInfosList = funInfosList;
        mOnStartActivtyInterface = onStartActivtyInterface;
        Log.d(TAG, "-GridViewAdapter-");
    }

    @Override
    public int getCount() {
        Log.d(TAG, "- getCount- size" + mFunInfosList.size());
        return mFunInfosList.size();
    }

    @Override
    public Object getItem(int position) {

            return mFunInfosList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "-getView- posion = "+ position);
        View view = null;
        ViewHolder holder = null;

        final FunInfos record = (FunInfos)getItem(position);

        if (convertView == null || convertView.getTag() == null) {
            convertView = mInflater.inflate(mItemLayoutResource, null);
            holder = getViewHolder();
            bindView(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        view = convertView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext,record.mFunTargrtActiv,Toast.LENGTH_LONG).show();
                mOnStartActivtyInterface.startSomeActivity(record.mFunTargrtActiv);
            }
        });
        holder.textView.setText(record.mFunName);
        return view;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView textView;

    }

    public interface OnStartActivtyInterface {
        public void startSomeActivity( String cls);
    }

    public ViewHolder getViewHolder() {
        return new ViewHolder();
    }

    public void bindView(ViewHolder viewHolder, View convertView) {
        viewHolder.textView = (TextView) convertView.findViewById(R.id.grid_view_text);
    }
}
