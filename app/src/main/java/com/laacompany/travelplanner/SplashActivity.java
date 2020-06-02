package com.laacompany.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int splash_time_out = 2000;
        new Handler().postDelayed((Runnable) () -> {
            startActivity(MainActivity.newIntent(this));
            finish();
        }, splash_time_out);
    }
}
