package com.example.note_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    ConstraintLayout layout;
    TextInputEditText emailinput;
    AppCompatButton submite;
    FirebaseAuth firebaseAuth;
    ImageButton goback;
    ImageView forgotgirl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuth=FirebaseAuth.getInstance();
        initWidget();

        int nightModeFlags =
                getApplicationContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                layout.setBackground(getResources().getDrawable(R.drawable.forgotpassworddark));
                forgotgirl.setImageResource(R.drawable.thinking_dark);
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                layout.setBackground(getResources().getDrawable(R.drawable.forgotpasswordlight));
                forgotgirl.setImageResource(R.drawable.thinking_light);
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                layout.setBackground(getResources().getDrawable(R.drawable.forgotpasswordlight));
                forgotgirl.setImageResource(R.drawable.thinking_light);
                break;
        }



        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        submite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=emailinput.getText().toString().trim();
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Mail Sent , You can recover your password using \n the link sent to your email ",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),LogIn.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Email is Wrong \n or Account Not Exist ",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

    private void initWidget() {
        layout=findViewById(R.id.layoutForgot);
        emailinput=findViewById(R.id.email_forgot);
        submite=findViewById(R.id.submitforgotpasssword);
        goback=findViewById(R.id.backForgot);
        forgotgirl=findViewById(R.id.forgotgirl);

    }
}