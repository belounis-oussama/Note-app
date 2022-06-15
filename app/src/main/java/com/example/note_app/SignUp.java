package com.example.note_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUp extends AppCompatActivity {

    TextInputEditText email,password,nickname;
    AppCompatButton signin;
    private FirebaseAuth firebaseAuth;

    ImageButton goback;
    ConstraintLayout layout;
    CircleImageView profileImage;
    ImageButton setpic;
    Uri imageUri;
    Intent dataHolder;
    StorageReference reference= FirebaseStorage.getInstance().getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initWidget();
        firebaseAuth=FirebaseAuth.getInstance();



        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(SignUp.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},300);
        }

        int nightModeFlags =
                getApplicationContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                layout.setBackground(getResources().getDrawable(R.drawable.signupdark));
                profileImage.setImageDrawable(getResources().getDrawable(R.drawable.profile_dark));
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                layout.setBackground(getResources().getDrawable(R.drawable.signuplight));
                profileImage.setImageDrawable(getResources().getDrawable(R.drawable.profile_light));
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                layout.setBackground(getResources().getDrawable(R.drawable.signuplight));
                profileImage.setImageDrawable(getResources().getDrawable(R.drawable.profile_light));
                break;
        }




        setpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String name=nickname.getText().toString().trim();
                String emailtext=email.getText().toString().trim();
                String passwordtext=password.getText().toString().trim();



                firebaseAuth.createUserWithEmailAndPassword(emailtext,passwordtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {


                            FirebaseDatabase.getInstance().getReference().child("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(new User_Model(name,emailtext,passwordtext,"fqfa"));

                            Toast.makeText(getApplicationContext(),"nice",Toast.LENGTH_SHORT).show();
                            emailVerification();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"no",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

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


    private String getFileExtension(Uri imageUri) {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(imageUri));


    }

    private void selectImage() {


        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(Intent.createChooser(intent,"Select image"),5);

    }

    private void emailVerification() {

        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if (firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(),"Verification Email is Sent ,\n Verify and Log In Again",Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            });
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode==5 &&  resultCode == RESULT_OK && data!=null && data.getData()!=null )
        {

            imageUri=data.getData();
            imageUri = data.getData();
            profileImage.setImageURI(imageUri);


        }
    }

    private void initWidget() {
        email=findViewById(R.id.email_signin);
        password=findViewById(R.id.password_signin);
        nickname=findViewById(R.id.namesignin);
        signin=findViewById(R.id.signinbtn);
        goback=findViewById(R.id.backSignin);
        layout=findViewById(R.id.layoutSignup);
        profileImage=findViewById(R.id.circleImageView);
        setpic=findViewById(R.id.setpic);
    }
}