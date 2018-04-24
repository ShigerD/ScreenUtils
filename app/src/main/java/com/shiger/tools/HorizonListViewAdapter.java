package com.shiger.tools;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * An array adapter that knows how to render views when given CustomData classes
 */
public class HorizonListViewAdapter extends BaseAdapter {

    private String TAG = "HorizonListViewAdapter";

    private LayoutInflater mInflater;
    private String[] mStringList;
    private Context mContext;
    private OnItemClickLitener mOnItemClickLitener;
    private int mSelectionPosion = -1;
    private int mNormalViewBg, mSelectedViewBg;
    private int mNormalLineBg, mSelectedLineBg;
//    private int mNormalViewBg;

    public HorizonListViewAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);
    }

    public HorizonListViewAdapter(Context context, String[] strings) {
        Log.d(TAG, "HorizonListViewAdapter--");
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);
        mStringList = strings;
        mContext = context;
        Resources resources = mContext.getResources();
        mSelectedLineBg = resources.getColor(R.color.green_line);
        mNormalLineBg = resources.getColor(R.color.grey_line);//
        //
        mSelectedViewBg = resources.getColor(R.color.grey_button_choose);
        mNormalViewBg = resources.getColor(R.color.grey_button_normal);//
    }

    public interface OnItemClickLitener {
        void onItemClick(int position);
    }

    public int getCount() {
        Log.d(TAG, "getCount--" + mStringList.length);
        return mStringList.length;
    }

    public Object getItem(int position) {
        Log.d(TAG, "getItem--" + position);
        return mStringList[position];
    }

    public long getItemId(int position) {
        Log.d(TAG, "getItemId--" + position);
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView--" + position);
        ViewHolder viewHolder = null;

        if (convertView == null || convertView.getTag() == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.horizon_list_view_item, null);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Log.w(TAG, "getView--mSelectionPosion--" + mSelectionPosion);
        viewHolder.textView = (TextView) convertView.findViewById(R.id.horizon_list_text);
        viewHolder.imageViewLine = (ImageView)convertView.findViewById(R.id.image_line);
        viewHolder.textView.setText(mStringList[position]);

        if (position == mSelectionPosion) {
            viewHolder.textView.setBackgroundColor(mSelectedViewBg);
            viewHolder.imageViewLine.setBackgroundColor(mSelectedLineBg);
//            convertView.setBackgroundColor(mSelectedViewBg);
        } else {
            viewHolder.textView.setBackgroundColor(mNormalViewBg);
            viewHolder.imageViewLine.setBackgroundColor(Color.TRANSPARENT);
//            convertView.setBackgroundColor(mNormalViewBg);
        }

//        Bitmap bitmap = miniImageUri(mStringList.get(position), 60);
//        viewHolder.textView.setImageBitmap(bitmap);

        return convertView;
    }

    /**
     * View holder for the views we need access to
     */
    public class ViewHolder {
        public TextView textView;
        public ImageView imageViewLine;
    }

    /**
     * @param position
     */
    public void setSelectPosition(int position) {
        Log.d(TAG, "setSelectPosition--" + position);
        if (!(position < 0 || position > mStringList.length)) {
            mSelectionPosion = position;
//            notifyDataSetChanged();

        }
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

}
