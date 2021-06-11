package com.example.ams;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        EasySplashScreen config=new EasySplashScreen(splash_Screen.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(1000)
                .withBackgroundColor(Color.parseColor("#000000"))
                //.withBackgroundResource(R.mipmap.corona1_foreground);
                .withLogo(R.mipmap.splash_foreground);
        View easySplashScreen = config.create();
        setContentView(easySplashScreen);

    }
}