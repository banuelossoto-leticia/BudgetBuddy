package com.example.cecs448;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                //this method will be executed once the timer is over
                //this is for the splash screen, start app with HomeScreenActivity
                Intent i = new Intent(SplashScreenActivity.this, HomeScreenActivity.class);
                startActivity(i);

                //closes this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}