package com.example.note_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;


public class FoldersFragment extends Fragment {

    FloatingActionButton add;
    RecyclerView list;
    FolderAdapter adapter;

    List<Folder_Modele> folders=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_folders, container, false);

        folders.add(new Folder_Modele("one","black"));
        folders.add(new Folder_Modele("two","black"));
        folders.add(new Folder_Modele("three","black"));
        folders.add(new Folder_Modele("three","black"));
        folders.add(new Folder_Modele("three","black"));
        folders.add(new Folder_Modele("three","black"));

        list=view.findViewById(R.id.recycleviewFolders);
        add=view.findViewById(R.id.Folderfab);

        adapter=new FolderAdapter(getContext(),folders);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL,false);
        list.setLayoutManager(gridLayoutManager);
        list.setAdapter(adapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });

        return view;
    }

    private void openColorPicker() {


        AmbilWarnaDialog colorPicker =new AmbilWarnaDialog(getContext(), R.color.dark_teal, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {


            }
        });

        colorPicker.show();

    }
}
