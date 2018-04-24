package com.shiger.tools;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private static final String TAG = "ViewPagerAdapter";
    private Context mContext;
    private int[] mImages;
    private String[] mTitles;
    String[] mValueArry;

    private LayoutInflater mInflater;

    private List<String> mContainList = new ArrayList<String>();

    public ViewPagerAdapter(Context context, int[] images, String[] titles, String[] values) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        mImages = images;
        mTitles = titles;
        mValueArry = values;
    }

    public class ViewHolder {
        public ImageView imageView;
        public TextView title;
        public TextView vaule;
    }


    public interface IOnViewPagerChangedLister {
        void onPageChangeTo(int position);
    }

    private IOnViewPagerChangedLister mIOnViewPagerChangedLister;

    public void setIOnViewPagerChangedLister(IOnViewPagerChangedLister iOnViewPagerChangedLister) {
        mIOnViewPagerChangedLister = iOnViewPagerChangedLister;
    }

    @Override
    public int getCount() {
        Log.d(TAG, "-instantiateItem- mImages.size()" + mImages.length);
        return mImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //getView
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        Log.d(TAG, "-instantiateItem- position" + position);


        if (mIOnViewPagerChangedLister != null) {
            mIOnViewPagerChangedLister.onPageChangeTo(position);
        }

        ViewHolder viewHolder = null;
        viewHolder = new ViewPagerAdapter.ViewHolder();
        View view = mInflater.inflate(R.layout.view_page_item, null);
        viewHolder.imageView = (ImageView) view.findViewById(R.id.image_gif);
        viewHolder.title = (TextView) view.findViewById(R.id.title);
        viewHolder.title.setText(mTitles[position]);
        viewHolder.vaule = (TextView) view.findViewById(R.id.vaule);
        viewHolder.vaule.setText(mValueArry[position]);

        //gif
        Log.d(TAG, "mImages[0]--" + mImages[position]);
        Log.d(TAG, "R.drawable.car_help_image_1--" + R.drawable.car_help_image_1);
        Glide.with(mContext).load(mImages[position]).into(viewHolder.imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
//        super.setPrimaryItem(container,position,object);

//        Log.w(TAG,"-mUriArry-"+mUriArry[0].toString());
    }

    @Override
    public int getItemPosition(Object object) {
        Log.d(TAG, "getItemPosition--" + super.getItemPosition(object));
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d(TAG, "destroyItem--position==" + position);
/*        View v = (View) object;
        if (v == null||container.getChildAt(position)==null){
            return;
        }
        ImageView imageView = (ImageView) container.getChildAt(position);
        imageView.setImageURI(null);
        releaseImageViewResourse(imageView);
//        container.removeView(imageView);
//        container.removeViewAt(position);//result some page missing*/

    }


}
