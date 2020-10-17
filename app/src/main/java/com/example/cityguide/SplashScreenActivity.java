package com.example.cityguide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView backgroundImage;

    //animation
    Animation sideAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        //binding
        backgroundImage = findViewById(R.id.backgroundImage);

        //Animation
        sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);

//        setAnimation

        backgroundImage.setAnimation(sideAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent  = new Intent(SplashScreenActivity.this, OnBoardingActivity.class);
            startActivity(intent);
            finish();
            }
        },5000);


    }
}