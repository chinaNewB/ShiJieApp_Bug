package com.example.shijieapp.base;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    private static Context appContext;
    private static String userPhones ;

    public static String getUserPhones() {
        return userPhones;
    }

    public static void setUserPhones(String user) {
        userPhones = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getBaseContext();
    }

    public static Context getAppContext(){
        return appContext;
    }


}
