package com.example.note_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    TextInputEditText currentpassword,newpassword;
    AppCompatButton submit;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initWidget();


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                String currpassword=currentpassword.getText().toString().trim();
                String newpass=newpassword.getText().toString().trim();
                if (currpassword.isEmpty())
                {
                    currentpassword.setError("Current password is empty");
                }
                else if (newpass.isEmpty())
                {
                    newpassword.setError("new password is empty");
                }

                else {
                    firebaseUser.updatePassword(newpass).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Toast.makeText(ChangePassword.this, "Password updated successfully ", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressBar.setVisibility(View.INVISIBLE);
                            finish();
                        }
                    });
                }
            }
        });

    }

    private void initWidget() {
        currentpassword=findViewById(R.id.currentpassword);
        newpassword=findViewById(R.id.newpassword);
        submit=findViewById(R.id.submitchangepassword);
        progressBar=findViewById(R.id.progress_bar_change);
    }
}