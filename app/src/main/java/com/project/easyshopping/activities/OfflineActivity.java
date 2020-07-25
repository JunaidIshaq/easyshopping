package com.project.easyshopping.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.easyshopping.broadcasts.NetworkReceiver;
import com.project.easyshopping.R;
import com.project.easyshopping.util.Utility;

public class OfflineActivity extends AppCompatActivity {

    NetworkReceiver networkReceiver = new NetworkReceiver();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nointernet);
    }

    public void checkInternetConnection(android.view.View view) {
        boolean isConnected = Utility.isInternetAvailable(this);
        if(isConnected && LoginActivity.isUserloggedIn()){
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Internet Available", Toast.LENGTH_SHORT).show();
        }else if(isConnected && !LoginActivity.isUserloggedIn()){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Internet Available", Toast.LENGTH_SHORT).show();
        }
    }
}
