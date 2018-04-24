package com.shiger.tools;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiger.tools.view.HorizontalListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shiger on 3/26/18.
 * e-mail : tigerwithwings@foxmail.com
 */

public class HorizenLisViewtActcvity extends Activity {

    private final String TAG = this.getClass().getSimpleName();
    private HorizontalListView mHorizontalListView;
    private List<String> mStringList = new ArrayList<>();

    private ImageView mPowerImageView;

    String[] mTitleArray;
    String[] mValueArry;

    ViewPager mViewPager;
    HorizonListViewAdapter mCustomListViewAdapter;

    TextView mPageNumText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizen_listview);

        mTitleArray = getResources().getStringArray(R.array.car_help_title);
        mValueArry = getResources().getStringArray(R.array.car_help_value);//car_help_value
        //
        mPageNumText = (TextView) findViewById(R.id.page_num_text);
        //listview
        mHorizontalListView = (HorizontalListView) findViewById(R.id.horizon_list);
        mCustomListViewAdapter = new HorizonListViewAdapter(getApplicationContext(), mTitleArray);
        mCustomListViewAdapter.setSelectPosition(0);
        mHorizontalListView.setAdapter(mCustomListViewAdapter);
        mHorizontalListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //virepage
        int[] mGifArray = getDrawableTemp(getApplicationContext());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getApplicationContext(),
                mGifArray, mTitleArray, mValueArry);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected-position" + position);
//                mHorizontalListView.setSelection(position);
                mCustomListViewAdapter.setSelectPosition(position);
                mHorizontalListView.setAdapter(mCustomListViewAdapter);
                String pageNum = String.valueOf(position + 1) + "/" + mTitleArray.length;
                mPageNumText.setText(pageNum);
//                mHorizontalListView.scrollTo(position * 50);
//                mHorizontalListView.scrollToPosion(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        Log.d(TAG, "Build.ID == " + Build.ID);

    }

    private int[] getDrawableTemp(Context context) {
        TypedArray ar = context.getResources().obtainTypedArray(R.array.car_help_images);
        int len = ar.length();
        int[] resIds = new int[len];
        for (int i = 0; i < len; i++) {
            resIds[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();
        return resIds;
    }

    private void initHorizonList() {

    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "=onDestroy=");
        super.onDestroy();
    }
}
