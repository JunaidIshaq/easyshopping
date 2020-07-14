package com.project.easyshopping.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.project.easyshopping.activities.MyApplication;

public class NetworkReceiver extends BroadcastReceiver {

    public static NetworkListener networkListener;

    public NetworkReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connec.getActiveNetworkInfo();

        if (networkListener != null) {
            networkListener.onNetworkChangedListener(activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting());
        }

    }

    public static boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) MyApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public interface NetworkListener{
        void onNetworkChangedListener(boolean isConnected);
    }
}
