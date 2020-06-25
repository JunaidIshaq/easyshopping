package com.project.easyshopping.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.easyshopping.R;
import com.project.easyshopping.data.model.FeedBackDTO;

import java.util.Date;

public class FeedbackActivity extends AppCompatActivity

{
   private EditText name, email , city, message;
   private Button sendmessage;
   private RatingBar rate;
   Toolbar toolbar;



    public FeedbackActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        name= findViewById(R.id.edit1);
        email = findViewById(R.id.edit2);
        city= findViewById(R.id.edit3);
        message = findViewById(R.id.edit4);
        sendmessage= findViewById(R.id.sendMessage);
        rate = findViewById(R.id.rate);


        sendmessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FeedBackDTO dto = new FeedBackDTO();
                dto.setName(name.getText().toString());
                dto.setEmail(email.getText().toString());
                dto.setCity(city.getText().toString());
                dto.setMessage(message.getText().toString());
                dto.setRate(String.valueOf(rate.getRating()));
                dto.setDateTime(new Date());
                sendUserFeedBack(FirebaseAuth.getInstance().getUid(), dto);
                Toast.makeText(FeedbackActivity.this, "Your feedback saved successfully", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setSupportActionBar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    private void sendUserFeedBack(String userId, FeedBackDTO dto) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("users_feedback").child(userId).setValue(dto);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
