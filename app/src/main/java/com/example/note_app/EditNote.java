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

public class EditNote extends AppCompatActivity {

    Intent data;
    EditText edittitle,editcontent;
    FloatingActionButton savenote;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        data=getIntent();
        initWidget();


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        String notetitle=data.getStringExtra("title");
        String noteContent=data.getStringExtra("Content");

        edittitle.setText(notetitle);
        editcontent.setText(noteContent);


        savenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference documentReference=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("mynotes").document(data.getStringExtra("noteId"));
                Map<String, Object> note=new HashMap<>();
                note.put("title",edittitle.getText().toString());
                note.put("content",editcontent.getText().toString());

                documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(getApplicationContext(), "Note Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainNotes.class));
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to update note", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainNotes.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initWidget() {
        edittitle=findViewById(R.id.edittitlenote);
        editcontent=findViewById(R.id.editcontentnote);
        savenote=findViewById(R.id.saveeditnote);
        toolbar=findViewById(R.id.toolbareditnote);
    }
}