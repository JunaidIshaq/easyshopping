package com.project.easyshopping.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.easyshopping.R;
import com.project.easyshopping.broadcasts.NetworkReceiver;
import com.project.easyshopping.dto.FeedBackDTO;
import com.project.easyshopping.util.Utility;

import java.util.Date;

public class FeedBackActivity extends AppCompatActivity implements NetworkReceiver.NetworkListener {

    private static final String TAG = "FeedBackActivity";
    private EditText name, email, city, message;
    private Button sendmessage;
    private RatingBar rate;
    Toolbar toolbar;
    NetworkReceiver networkReceiver = new NetworkReceiver();

    public FeedBackActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        name = findViewById(R.id.edit1);
        email = findViewById(R.id.edit2);
        city = findViewById(R.id.edit3);
        message = findViewById(R.id.edit4);
        sendmessage = findViewById(R.id.sendMessage);
        rate = findViewById(R.id.rate);


        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String validationMessage = validateData();
                if (validationMessage.equals("")) {
                    FeedBackDTO dto = new FeedBackDTO();
                    dto.setName(name.getText().toString());
                    dto.setEmail(email.getText().toString());
                    dto.setCity(city.getText().toString());
                    dto.setMessage(message.getText().toString());
                    dto.setRate(String.valueOf(rate.getRating()));
                    dto.setDateTime(new Date());
                    sendUserFeedBack(FirebaseAuth.getInstance().getUid(), dto);
                    Toast.makeText(FeedBackActivity.this, "Your feedback saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FeedBackActivity.this, validationMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String validateData() {
        if (name.getText() == null || name.getText().toString().equals("")) {
            return "Please enter your name";
        } else if (email.getText() == null || email.getText().toString().equals("")) {
            return "Please enter your email id";
        } else if (!Utility.validateEmailAddress(email.getText().toString())) {
            return "Please enter your valid email id";
        } else if (city.getText() == null || city.getText().toString().equals("")) {
            return "Please enter your city";
        } else if (message.getText() == null || message.getText().toString().equals("")) {
            return "Please enter message";
        }
        return "";
    }

    private void setSupportActionBar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    private void sendUserFeedBack(String userId, FeedBackDTO dto) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("users_feedback").child(userId).setValue(dto).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,SearchActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, intentFilter);
        MyApplication.getInstance().setNetworkListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkReceiver);
    }

    @Override
    public void onNetworkChangedListener(boolean isConnected) {
        if(!isConnected){
            Utility.sendToOfflineActivity(this);
        }
    }
}
