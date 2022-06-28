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
import android.widget.TextView;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUp extends AppCompatActivity {

    TextInputEditText email,password,nickname;
    AppCompatButton signin;
    private FirebaseAuth firebaseAuth;
    TextView signuptologin;
    ImageButton goback;
    ConstraintLayout layout;
    CircleImageView profileImage;
    ImageButton setpic;
    Uri imageUri;
    Intent dataHolder;
    StorageReference reference= FirebaseStorage.getInstance().getReference();
    FirebaseStorage storage;
    StorageReference storageReference;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initWidget();
        firebaseAuth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReferenceFromUrl("gs://note-app-249c6.appspot.com");


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



        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        signuptologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LogIn.class));
                finish();
            }
        });

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

                if (emailtext.isEmpty())
                {
                    email.setError("Email field is empty");
                }
                else if (passwordtext.isEmpty())
                {
                    password.setError("Password field is empty");
                }
                else if (name.isEmpty())
                {
                    nickname.setError("Nickname field is empty");
                }

                else  if (!isEmailValid(emailtext))
                {
                    email.setError("Invalid Email");
                    Toast.makeText(SignUp.this, "make sure you email is correct", Toast.LENGTH_SHORT).show();
                }
                else if (imageUri!=null)
                {



                    progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(emailtext,passwordtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {

                            StorageReference reference=storageReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()+","+ Calendar.getInstance().getTime());
                            reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                                    while (!uriTask.isComplete());
                                    Uri uri=uriTask.getResult();
                                    FirebaseDatabase.getInstance().getReference().child("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(new User_Model(name,emailtext,passwordtext,uri.toString()));

                                    Toast.makeText(getApplicationContext(),"data Uploaded",Toast.LENGTH_SHORT).show();


                                    emailVerification();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Failed to upload data",Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"no",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        } });


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


            imageUri = data.getData();
            profileImage.setImageURI(imageUri);


        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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
        progressBar=findViewById(R.id.progress_bar_signup);
        signuptologin=findViewById(R.id.signuptologin);
    }
}