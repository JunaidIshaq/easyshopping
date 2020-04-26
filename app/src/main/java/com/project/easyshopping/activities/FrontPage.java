package com.project.easyshopping.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.easyshopping.R;

public class FrontPage extends AppCompatActivity {
    Button but;
    Animation frombottom, fromtop;
    ImageView img;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        but=(Button)findViewById(R.id.btn);
        img=(ImageView) findViewById(R.id.img1) ;

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        fromtop= AnimationUtils.loadAnimation(this,R.anim.fromtop);

        but.setAnimation(frombottom);
        img.setAnimation(fromtop);


    }
    public void onclick(View view)
    {
        Intent i= new Intent( FrontPage.this, LoginActivity.class);
        startActivity(i);
    }
}


