package com.app.aihealthapp.core.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Administrator on 2017/1/11.
 * Android Application程序的入口点基类
 */

public class BaseApplication extends Application {

    private static Context mContext;
    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static synchronized BaseApplication getContext() {
        return (BaseApplication) mContext;
    }

}
