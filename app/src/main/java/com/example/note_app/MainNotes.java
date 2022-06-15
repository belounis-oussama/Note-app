package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainNotes extends AppCompatActivity {

    FloatingActionButton add;
    RecyclerView notesRecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_notes);

        initWidget();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initWidget() {
        add=findViewById(R.id.Notefab);
        notesRecycle=findViewById(R.id.recycleviewNotes);
    }
}