package com.example.note_app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoteFragment extends Fragment {
    FloatingActionButton add;
    RecyclerView notesRecycle;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    View view;


    FirestoreRecyclerAdapter<firebaseModele, NoteFragment.NoteViewHolder> noteAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


       view = inflater.inflate(R.layout.fragment_notes, container, false);



        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        firebaseFirestore=FirebaseFirestore.getInstance();


        initWidget();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),CreateNote.class));
                getActivity().finish();

            }
        });



        Query query=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("mynotes").orderBy("title",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<firebaseModele> allNotes= new FirestoreRecyclerOptions.Builder<firebaseModele>().setQuery(query,firebaseModele.class).build();

        noteAdapter=new FirestoreRecyclerAdapter<firebaseModele, NoteFragment.NoteViewHolder>(allNotes) {
            @Override
            protected void onBindViewHolder(@NonNull NoteFragment.NoteViewHolder holder, int position, @NonNull firebaseModele model) {


                ImageView popupmenu=holder.itemView.findViewById(R.id.menupopoupbtn);


                int colorcode=getRandomColor();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.mynote.setBackgroundColor(holder.itemView.getResources().getColor(colorcode,null));
                }


                holder.notetitle.setText(model.getTitle()) ;
                holder.notecontent.setText(model.getContent());

                String docId=noteAdapter.getSnapshots().getSnapshot(position).getId();

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        Intent intent=new Intent(view.getContext(),NoteDetails.class);

                        intent.putExtra("title",model.getTitle());
                        intent.putExtra("Content",model.getContent());
                        intent.putExtra("noteId",docId);

                        view.getContext().startActivity(intent);

                    }
                });


                popupmenu.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View view) {
                        PopupMenu popupMenu=new PopupMenu(view.getContext(),view);
                        popupMenu.setGravity(Gravity.END);

                        popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {

                                Intent intent=new Intent(view.getContext(),EditNote.class);
                                intent.putExtra("title",model.getTitle());
                                intent.putExtra("Content",model.getContent());
                                intent.putExtra("noteId",docId);
                                startActivity(intent);

                                return false;
                            }
                        });

                        popupMenu.getMenu().add("Delte").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                //Toast.makeText(NoteFragment.this, "note deleted", Toast.LENGTH_SHORT).show();

                                DocumentReference documentReference=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("mynotes").document(docId);
                                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getActivity(), "Note deleted", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Failed to delete", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return false;
                            }
                        });

                        popupMenu.show();
                    }
                });
            }

            @NonNull
            @Override
            public NoteFragment.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notelayout,parent,false);
                return new NoteFragment.NoteViewHolder(view);
            }
        };




        notesRecycle.setHasFixedSize(true);
        staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        notesRecycle.setLayoutManager(staggeredGridLayoutManager);
        notesRecycle.setAdapter(noteAdapter);

        return view;
    }



    private int getRandomColor() {
        List<Integer> colorCode=new ArrayList<>();

        colorCode.add(R.color.gray_blue);
        colorCode.add(R.color.light_brown);
        colorCode.add(R.color.dark_brown);
        colorCode.add(R.color.dark_teal);
        colorCode.add(R.color.light_teal);
        colorCode.add(R.color.orange);


        Random random=new Random();
        int randnumber=random.nextInt(colorCode.size());

        return colorCode.get(randnumber);
    }


    public static class  NoteViewHolder extends RecyclerView.ViewHolder
    {

        public TextView notetitle;
        public TextView notecontent;
        LinearLayout mynote;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            notetitle=itemView.findViewById(R.id.notetitle);
            notecontent=itemView.findViewById(R.id.notecontent);
            mynote=itemView.findViewById(R.id.note);
        }
    }

    private void initWidget() {
        add=view.findViewById(R.id.Notefab);
        notesRecycle= view.findViewById(R.id.recycleviewNotes);
    }




    @Override
    public void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        if (noteAdapter!=null)
        {
            noteAdapter.stopListening();
        }
    }
}
