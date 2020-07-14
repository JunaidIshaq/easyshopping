package com.project.easyshopping.activities;

import android.app.Application;

import com.project.easyshopping.broadcasts.NetworkReceiver;

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setNetworkListener(NetworkReceiver.NetworkListener listener) {
        NetworkReceiver.networkListener = listener;
    }
}
