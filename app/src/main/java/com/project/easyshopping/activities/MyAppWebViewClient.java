package com.project.easyshopping.activities;

import android.app.ProgressDialog;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyAppWebViewClient extends WebViewClient {

    private ProgressDialog progressDialog;
    String TAG = "MyAppWebViewClient";

    public MyAppWebViewClient(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.i(TAG, "Processing webview url click...");
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        Log.i(TAG, "Finished loading URL: " + url);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
