package com.project.easyshopping.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.easyshopping.broadcasts.NetworkReceiver;
import com.project.easyshopping.R;
import com.project.easyshopping.util.Utility;

public class WebViewActivity extends AppCompatActivity implements NetworkReceiver.NetworkListener {

    WebView mWebView;
    String title;
    String webUrl;
    private ProgressDialog progressDialog;
    Toolbar toolbar;

    NetworkReceiver networkReceiver = new NetworkReceiver();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar (toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        webUrl = intent.getStringExtra("url");
        progressDialog = ProgressDialog.show(WebViewActivity.this, " Please wait", "Loading...");
//        getSupportActionBar().setTitle(title);
        mWebView = findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyAppWebViewClient(progressDialog));
        mWebView.loadUrl(webUrl);
    }

    private void setSupportActionBar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, intentFilter);
        MyApplication.getInstance().setNetworkListener((NetworkReceiver.NetworkListener) this);
    }


    @Override
    public void onNetworkChangedListener(boolean isConnected) {
        if(!isConnected){
            Utility.sendToOfflineActivity(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkReceiver);
    }
}
