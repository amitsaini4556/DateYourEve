package com.example.dateyoureve;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InterestedUsersList extends AppCompatActivity {
    InterestedUsersAdapter myAdapter;
    ProgressBar progressBar;
    private List<User> interestedUsersData;
    DatabaseReference databaseReferenceIns,databaseReferenceEve;
    FirebaseUser mAuth;
    FloatingActionButton qrbtn;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested_users_list);
        progressBar = findViewById(R.id.progressBarInterestList);
        qrbtn = findViewById(R.id.qrbtn);
        progressBar.setVisibility(View.VISIBLE);
        Toolbar toolbar = findViewById(R.id.interestedPeopleList);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        interestedUsersData = new ArrayList<User>();
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        path = "Events/" + getIntent().getStringExtra("eventId") +"/InterestedPeoples";
        databaseReferenceIns = FirebaseDatabase.getInstance().getReference(path);
        databaseReferenceIns.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    String path = "users/" + dataSnapshot.getValue().toString();
                    databaseReferenceEve = FirebaseDatabase.getInstance().getReference(path);
                    databaseReferenceEve.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            interestedUsersData.add(snapshot.getValue(User.class));
                            myAdapter = new InterestedUsersAdapter(interestedUsersData,InterestedUsersList.this);
                            recyclerView.setAdapter(myAdapter);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (interestedUsersData.size() == 0)
            progressBar.setVisibility(View.GONE);
        qrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(interestedUsersData.size() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Attendees list empty!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(),QrScanner.class);
                    intent.putExtra("eventId",getIntent().getStringExtra("eventId"));
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

}