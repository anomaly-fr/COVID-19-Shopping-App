package com.example.localgrocer.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.localgrocer.ConsumerActivity;
import com.example.localgrocer.LoginActivity;
import com.example.localgrocer.R;
import com.example.localgrocer.walkthrough.WalkthroughActivity;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView mSplashImage;
    TextView mSplashText;
    private static int SPLASH_TIME_OUT=2000;
    private SharedPreferences sharedPreferences;
    private static String SHARED_PREFERENCE_NAME = "Local Grocer Shared Preference";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //SharedPreference Initialization
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);

        initViews();
        initializeHandler();

    }
    private void initializeHandler() {//Excecuting the function after a delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPreferences.getBoolean("is_first_time", true)) {
                    Intent walkthrough = new Intent(SplashScreenActivity.this, WalkthroughActivity.class);
                    startActivity(walkthrough);
                } else {
                    Intent landpage = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(landpage);
                }
                finish();

            }
        }, SPLASH_TIME_OUT);
    }

    private void initViews() {
        mSplashImage=(ImageView) findViewById(R.id.xIVSplashLogo);
        mSplashText=(TextView) findViewById(R.id.xTVSplashText);
        mSplashText.setTypeface(Typeface.DEFAULT_BOLD);
    }
}
