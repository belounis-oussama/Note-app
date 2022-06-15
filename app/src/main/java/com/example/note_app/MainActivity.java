package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout activity;
    Button login,signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();



        int nightModeFlags =
                getApplicationContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                activity.setBackground(getResources().getDrawable(R.drawable.mainldark));
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                activity.setBackground(getResources().getDrawable(R.drawable.mainlighten));
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                activity.setBackground(getResources().getDrawable(R.drawable.mainlighten));
                break;
        }



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),LogIn.class);
                startActivity(intent);
                finish();
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void initWidget() {
        login=findViewById(R.id.LoginFirst);
        signin=findViewById(R.id.SignupFirst);
        activity=findViewById(R.id.myac);
    }


}