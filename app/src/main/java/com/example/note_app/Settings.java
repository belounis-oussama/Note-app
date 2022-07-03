package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.view.Change;

public class Settings extends AppCompatActivity {

    AppCompatButton logout;
    RelativeLayout changepassword;
    SwitchMaterial darkmode,lockswitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initWidget();

        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
        {
            darkmode.setChecked(true);
        }
        else
        {
            darkmode.setChecked(false);
        }


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("hasLoggedIn",false);

                editor.apply();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });



        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChangePassword.class));
            }
        });


        lockswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b)
                {
                    getTheme();
                }


            }
        });

        darkmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {


                if (checked)
                {

                    SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("thememode","dark");

                    editor.apply();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);



                    /*@Override
public Resources.Theme getTheme() {
    Resources.Theme theme = super.getTheme();
    if(useAlternativeTheme){
        theme.applyStyle(R.style.AlternativeTheme, true);
    }
    // you could also use a switch if you have many themes that could apply
    return theme;
}*/

                    //resetActivity();
                }
                else
                {
                    SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("thememode","light");

                    editor.apply();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    //resetActivity();
                }
            }
        });
    }

    private void resetActivity() {
        startActivity(new Intent(getApplicationContext(),Settings.class));
        finish();
    }

    private void initWidget() {
        logout=findViewById(R.id.logoutbtn);
        changepassword=findViewById(R.id.changepassword);
        darkmode=findViewById(R.id.darkmodeswitch);
        lockswitch=findViewById(R.id.lockswitch);
    }


    @Override
    public Resources.Theme getTheme() {
        Resources.Theme theme = super.getTheme();

            theme.applyStyle(R.style.Theme_Gruvbox, true);

        // you could also use a switch if you have many themes that could apply
        return theme;
    }




}