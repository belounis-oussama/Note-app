package com.example.note_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainHolder extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_holder);

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReferenceFromUrl("gs://note-app-249c6.appspot.com");


        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer= findViewById(R.id.draw_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View header=navigationView.getHeaderView(0);
        CircleImageView profile=header.findViewById(R.id.userprofile);
        TextView name=header.findViewById(R.id.username);
        TextView email=header.findViewById(R.id.useremail);


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


        DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        Query query = mUserDatabase.child(firebaseUser.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User_Model user=snapshot.getValue(User_Model.class);

                   name.setText(user.getNickname());
                   email.setText(user.getEmail());
                  // profile.setImageURI(Uri.parse(user.getPictureLink()));
                //profile.setImageDrawable(getApplicationContext().getDrawable(R.drawable.profile_light));

               Picasso.get().load(user.getPictureLink()).into(profile);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        if (savedInstanceState == null)
        {

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new NoteFragment()).commit();
        navigationView.setCheckedItem(R.id.notes);}
        toggle.syncState();


       
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {


            case R.id.notes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new NoteFragment()).commit();
                break;

            case R.id.folders:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FoldersFragment()).commit();
                break;

            case R.id.starred:
                break;

            case R.id.settings:
                startActivity(new Intent(getApplicationContext(),Settings.class));
                break;

        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
}