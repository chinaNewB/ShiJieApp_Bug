package com.example.shijieapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.Uri;

import java.util.Formatter;
import java.util.Locale;

public class NetUtils {
    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;
    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;
    private boolean netOrLocal = true;
    private long totalSeconds;

    public NetUtils() {
        // 转换成字符串的时间
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    }


    /**
     * 把毫秒转换成：1:20:30这里形式
     * @param timeMs
     * @return
     */
    public String stringForTime(long timeMs,boolean netOrLocal) {
        if(!netOrLocal){
            totalSeconds= timeMs / 1000;
        }else{
            totalSeconds= timeMs;
        }
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;
        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%02d:%02d:%02d", hours, minutes, seconds).toString();
        } else{
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }


    public String byteToMB(long size){
        long kb = 1024;
        long mb = kb*1024;
        long gb = mb*1024;
        if (size >= gb){
            return String.format("%.2f GB",(float)size/gb);
        }else if (size >= mb){
            float f = (float) size/mb;
            return String.format(f > 100 ?"%.2f MB":"%.2f MB",f);
        }else if (size > kb){
            float f = (float) size / kb;
            return String.format(f>100?"%.2f KB":"%.2f KB",f);
        }else {
            return String.format("%d B",size);
        }
    }

    /**
      * 判断是否是网络的资源
      * @param uri
      * * @return
      */
    public boolean isNetUri(String uri){
        boolean result= false;
        if(uri != null){
            if(uri.toLowerCase().startsWith("http")||uri.toLowerCase().startsWith("rtsp")||uri.toLowerCase().startsWith("mms")){
                result = true;
            }
        }
        return result;
    }


    /**
     * 得到网络速度
     * 每隔两秒调用一次
     * @param context
     * @return
     */

    public String getNetSpeed(Context context){
        String netSpeed = "0 kb/s";
        long nowTotalRxBytes = TrafficStats.getUidRxBytes(context.getApplicationInfo().uid)==TrafficStats.UNSUPPORTED ? 0 :(TrafficStats.getTotalRxBytes()/1024);//转为KB;
        long nowTimeStamp= System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换
        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        netSpeed = String.valueOf(speed)+ " kb/s";

        return netSpeed;
    }

    //判断本地的文件权限是否打开
    public boolean isLocalAvalable(Context context){
        boolean has_permission = (PackageManager.PERMISSION_GRANTED
                == context.getPackageManager().checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, "com.example.shi_jie"));
        return has_permission;
    }

    //判断网络是否打开
    public boolean isNetWorkAvailable(Context context){
        boolean isAvailable = false ;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isAvailable()){
            isAvailable = true;
        }
        return isAvailable;
    }

    /**
     * 打开网络设置界面, android  4.0
     */
    public static void openSetting(Activity activity) {
        if(android.os.Build.VERSION.SDK_INT > 10 ){
            //3.0以上打开设置界面
            activity.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
        }else
        {
            activity.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        }
    }


    public static void openSettingLocal(Activity activity){
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        }
    }
}
