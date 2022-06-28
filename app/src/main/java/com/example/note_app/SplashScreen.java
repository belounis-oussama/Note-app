package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME=3000; //1000=1s
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                boolean hasLogged=sharedPreferences.getBoolean("hasLoggedIn",false);

                if (hasLogged)
                {
                    Intent intent=new Intent(getApplicationContext(),MainHolder.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },SPLASH_TIME);
    }
    }
