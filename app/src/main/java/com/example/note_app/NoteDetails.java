package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteDetails extends AppCompatActivity {

    private TextView titlenotedetail,contentnotedetail;
    FloatingActionButton gotoeditnote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        
        initWidget();
        Intent data=getIntent();

        gotoeditnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(view.getContext(),EditNote.class);
                intent.putExtra("title",data.getStringExtra("title"));
                intent.putExtra("Content",data.getStringExtra("Content"));
                intent.putExtra("noteId",data.getStringExtra("noteId"));
                view.getContext().startActivity(intent);
            }
        });

        titlenotedetail.setText(data.getStringExtra("title"));
        contentnotedetail.setText(data.getStringExtra("Content"));

    }

    private void initWidget() {
        titlenotedetail=findViewById(R.id.titlenotedetail);
        contentnotedetail=findViewById(R.id.detailcontentnote);
        gotoeditnote=findViewById(R.id.gotoeditnote);
    }
}