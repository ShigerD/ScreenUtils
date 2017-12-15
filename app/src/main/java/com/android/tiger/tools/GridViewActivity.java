package com.android.tiger.tools;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends BaseActivity implements View.OnClickListener {


    private static final String TAG = "GridViewActivity";
    public static int[] KEYS;
    public static String[] KEY_NAMES;
    public static String[] ACTIVITY_NAMES;

    private GridView mGridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);
        mGridView = (GridView) findViewById(R.id.grid_view);

        KEYS = getResources().getIntArray(R.array.activity_amounts);
        KEY_NAMES = getResources().getStringArray(R.array.grid_explaines);
        ACTIVITY_NAMES = getResources().getStringArray(R.array.activity_names);
        List<FunInfos> funInfosList = new ArrayList<>();
        for(int i = 0 ;i< KEYS.length;i++){
            FunInfos funInfos = new FunInfos(KEYS[i],KEY_NAMES[i],ACTIVITY_NAMES[i]);
            funInfosList.add(funInfos);
        }
        Log.d(TAG, "KEYS = " + KEYS.length);
        Log.d(TAG, "KEY_NAMES = " + KEY_NAMES.length);
        Log.d(TAG, "funInfosList = " + funInfosList.size());
        GridViewAdapter.OnStartActivtyInterface onStartActivtyInterface = new GridViewAdapter.OnStartActivtyInterface() {
            @Override
            public void startSomeActivity( String cls) {
                Intent intent = null;
                try {
                    intent = new Intent(GridViewActivity.this, Class.forName(cls));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        GridViewAdapter gridViewAdapter = new GridViewAdapter(getApplicationContext(),funInfosList,onStartActivtyInterface);
        mGridView.setAdapter(gridViewAdapter);
    }


    @Override
    public void onClick(View v) {

    }
}
