package com.android.tiger.tools;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AppInfosAdapter extends BaseAdapter {
    private String TAG = "AppInfosAdapter";
    Context context;
    List<AppInfo> appInfos;

    public AppInfosAdapter(){}

    public AppInfosAdapter(Context context , List<AppInfo> infos ){
        this.context = context;
        this.appInfos = infos;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<AppInfo> getAppInfos() {
        return appInfos;
    }

    public void setAppInfos(List<AppInfo> appInfos) {
        this.appInfos = appInfos;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        int count = 0;
        if(null != appInfos){
            return appInfos.size();
        };
        return count;
    }

    @Override
    public Object getItem(int index) {
        // TODO Auto-generated method stub
        return appInfos.get(index);
    }

    @Override
    public long getItemId(int index) {
        // TODO Auto-generated method stub
        return index;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = null;
        if(null == convertView){
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.listview_item, null);
            viewHolder.appIconImg = (ImageView)convertView.findViewById(R.id.app_icon);
            viewHolder.appNameText = (TextView)convertView.findViewById(R.id.app_info_name);
            viewHolder.appPackageText = (TextView)convertView.findViewById(R.id.app_info_package_name);
            viewHolder.appActivityText = (TextView)convertView.findViewById(R.id.app_info_activity_name);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        if(null != appInfos){
            viewHolder.appIconImg.setBackground(appInfos.get(position).getDrawable());
            viewHolder.appNameText.setText(appInfos.get(position).getAppName());
            viewHolder.appPackageText.setText(appInfos.get(position).getPackageName());
            viewHolder.appActivityText.setText(getLauncherActivityNameByPackageName(context,appInfos.get(position).getPackageName()));
        }

        return convertView;
    }

    private class ViewHolder{
        ImageView appIconImg;
        TextView  appNameText;
        TextView  appPackageText;
        TextView  appActivityText;
    }

    public  String getLauncherActivityNameByPackageName(Context context, String packageName) {
        String className = null;
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);//android.intent.action.MAIN

        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageName);
        List<ResolveInfo> resolveinfoList = context.getPackageManager().queryIntentActivities(resolveIntent, 0);

        if(resolveinfoList.size() == 0){
            resolveIntent = new Intent();
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(packageName);
            resolveinfoList = context.getPackageManager().queryIntentServices(resolveIntent, 0);

            if(resolveinfoList.size() == 0){
                return "";
            }

            ResolveInfo resolveinfo = resolveinfoList.iterator().next();
            if (resolveinfo != null) {
                className = resolveinfo.serviceInfo.name+"  -----is a service";
                return className;
            }
        }



        Log.w(TAG , resolveinfoList.toString());
        try {
            ResolveInfo resolveinfo = resolveinfoList.iterator().next();
            if (resolveinfo != null) {
                className = resolveinfo.activityInfo.name;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return className;
    }
}
