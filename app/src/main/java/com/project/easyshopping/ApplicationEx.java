package com.project.easyshopping;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.Application;
import android.util.Log;

public class ApplicationEx {
	private static String TAG = "ApplicationEx";
	private static final int CORE_POOL_SIZE = 10;
	private static final int MAXIMUM_POOL_SIZE = 20;
	
	public static ThreadPoolExecutor operationsQueue;
	
	public static void constructThreadPool() {
		Log.d(TAG, "onCreate--ApplicationExecutor--");
		operationsQueue = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 100000L, 
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());	
	}
	
	public static void reconstructThreadPool(){
		Log.d(TAG, "--reconstructThreadPool--");
		operationsQueue = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 100000L, 
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	}

}
