package com.project.easyshopping.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.project.easyshopping.R;

public class Feedback extends AppCompatActivity

{
   private EditText name, email , city, message;
  private   Button sendmessage;
  private RatingBar rate;



    public Feedback() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        name= findViewById(R.id.edit1);
        email =findViewById(R.id.edit2);
        city= findViewById(R.id.edit3);
        message =findViewById(R.id.edit4);
        sendmessage=findViewById(R.id.Button);
        rate =findViewById(R.id.rate);


        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=name.getText().toString();
                String usermail=email.getText().toString();
                String usercity=city.getText().toString();
                String feedbackmsg=message.getText().toString();




            }
        });
    }
}
