package com.example.localgrocer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends Application {

    public MainActivity() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(false);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);


    }
}
