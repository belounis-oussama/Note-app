package com.example.note_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateNote extends AppCompatActivity {

    EditText titlenote,contentnote;
    FloatingActionButton savenote;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        
        initWidget();

        Toolbar toolbar=findViewById(R.id.toolbarcreatenote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();


        savenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title=titlenote.getText().toString();
                String content=contentnote.getText().toString();
                if (title.isEmpty() || content.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Both fields are Required", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    DocumentReference documentReference=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("mynotes").document();
                    Map<String, Object> note=new HashMap<>();
                    note.put("title",title);
                    note.put("content",content);

                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                                Toast.makeText(getApplicationContext(), "Note created successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainHolder.class));
                                finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed to create note", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainHolder.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void initWidget() {
        titlenote=findViewById(R.id.createtitlenote);
        contentnote=findViewById(R.id.cretecontentnote);
        savenote=findViewById(R.id.savenote);

    }
}