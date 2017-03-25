package com.example.cenk.flickralbum.classes.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cenk.flickralbum.R;

public class MainActivity extends AppCompatActivity {
    private final static int SPLASH_DURATION=1750;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        1750 milliseconds later runnable stops sleeping and changes
        the Activity. (Splash screen will be visible for 1.75 seconds)
         */
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,  ImageListActivity.class));
            }
        },SPLASH_DURATION);
    }
}
