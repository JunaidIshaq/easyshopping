package com.project.easyshopping.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.project.easyshopping.broadcasts.NetworkReceiver;
import com.project.easyshopping.activities.OfflineActivity;

import java.net.MalformedURLException;
import java.net.URL;

public class Utility {

	private Context mContext;
	public Utility(Context context) {
		this.mContext = context;
	}
	
	/**
	 * Added to Check if any Internet Connection (Wifi / Network Data
	 * Connection) is available or not.
	 * 
	 * @param context
	 * @return true if connnection is available
	 */
	public final static boolean isInternetAvailable(Context context) {
		if (isNetworkAvailable(context) || isWifiAvailable(context)) {
			return true;
		} else {
			Toast.makeText(context, "No Internet Available", Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	/**
	 * Check Internet is available or not.
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = connec.getActiveNetworkInfo();
		if (ni != null && ni.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check if Wi-Fi available or not. If not then doesn't show a pop-up.
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isWifiAvailable(Context context) {
		WifiManager connec = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		ConnectivityManager connec1 = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		State wifi = connec1.getNetworkInfo(1).getState();
		if (connec.isWifiEnabled()
				&& wifi.toString().equalsIgnoreCase("CONNECTED")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * function which validate links 
	 * @param1 String: URL
	 * @return
	 */
	private static boolean checkLink(String link) {
		if (link.startsWith("http://"))
			return true;
		if (link.startsWith("https://"))
			return true;
		return false;
	}

	public static String getBaseUrl(String url) {
		try {
			if(url != null){
				URL obj = new URL(url);
				return obj.getProtocol() + "://" + obj.getHost();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}

	public static void sendToOfflineActivity(Context context){
			Intent intent = new Intent(context, OfflineActivity.class);
			context.startActivity(intent);
			Toast.makeText(context, "No Internet Available", Toast.LENGTH_LONG).show();
	}

	public static boolean checkConnection() {
		return NetworkReceiver.isConnected();
	}

	public static boolean validateEmailAddress(String email) {
			String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
			return email.matches(regex);
	}
}
