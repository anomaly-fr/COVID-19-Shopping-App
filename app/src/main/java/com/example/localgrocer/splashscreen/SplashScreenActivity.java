package com.example.localgrocer.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.localgrocer.R;
import com.example.localgrocer.walkthrough.WalkthroughActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    ImageView mSplashImage;
    TextView mSplashText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initViews();
        initializeHandler();

    }

    private void initializeHandler() {//Excecuting the function after a delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent walkthrough = new Intent(SplashScreenActivity.this, WalkthroughActivity.class);
                startActivity(walkthrough);
                finish();

            }
        }, SPLASH_TIME_OUT);
    }

    private void initViews() {
        mSplashImage = (ImageView) findViewById(R.id.xIVSplashLogo);
        mSplashText = (TextView) findViewById(R.id.xTVSplashText);
        mSplashText.setTypeface(Typeface.DEFAULT_BOLD);
    }
}
