package com.example.note_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    Context context;
    List<Folder_Modele> folders;
    LayoutInflater inflater;


    public FolderAdapter(Context context,List<Folder_Modele> folders)
    {
    this.inflater=LayoutInflater.from(context);
    this.folders=folders;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=inflater.inflate(R.layout.folderlayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.foldername.setText(folders.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return folders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView foldername;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foldername=itemView.findViewById(R.id.Foldernametext);
        }
    }



}
