package com.example.note_app;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {
    RelativeLayout layout;
    TextInputEditText emailinput,passwordinput;
    TextView forgotpassword;
    AppCompatButton login;
    ImageButton goback;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initWidget();
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();


        if (firebaseUser!=null)
        {
            //startActivity(new Intent(getApplicationContext(),));
           // finish();
        }


        int nightModeFlags =
                getApplicationContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                layout.setBackground(getResources().getDrawable(R.drawable.login_background_dark));
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                layout.setBackground(getResources().getDrawable(R.drawable.login_background_light));
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                layout.setBackground(getResources().getDrawable(R.drawable.login_background_light));
                break;}





        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=emailinput.getText().toString().trim();
                String password=passwordinput.getText().toString().trim();


                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {

                            checkMailVerification();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Account doesn't Exist ",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    }
                });
            }
        });


        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgotPassword.class));

            }
        });


        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

    }

    private void checkMailVerification() {

        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if (firebaseUser.isEmailVerified())
        {
            startActivity(new Intent(getApplicationContext(),MainNotes.class));
            finish();
        }
        else
        {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),"Please Verify your email before logging in",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

    private void initWidget() {

        layout=findViewById(R.id.layoutLogin);
        emailinput=findViewById(R.id.email_login);
        passwordinput=findViewById(R.id.password_login);
        login=findViewById(R.id.loginbtn);
        forgotpassword=findViewById(R.id.forgotpasswordtext);
        goback=findViewById(R.id.backLogin);
        progressBar=findViewById(R.id.progress_bar_login);
    }
}