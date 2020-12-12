package com.example.dateyoureve;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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

public class PastEventsList extends AppCompatActivity {
    PastEventDataAdapter myAdapter;
    private List<PastEventData> pastEventData;
    DatabaseReference databaseReferenceCreated,databaseReferenceEve;
    FirebaseUser mAuth;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_events_list);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pastEventData = new ArrayList<PastEventData>();
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        path = "users/" + mAuth.getUid() + "/createdEvents";
        databaseReferenceCreated = FirebaseDatabase.getInstance().getReference(path);
        databaseReferenceCreated.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
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
       pastEventData.add(new PastEventData("mid terms","10/01/2020","10"));
        pastEventData.add(new PastEventData("Projects","20/65/1523","20"));
        pastEventData.add(new PastEventData("Office Works","32/12/2021","40"));
        myAdapter = new PastEventDataAdapter(pastEventData,PastEventsList.this);
        recyclerView.setAdapter(myAdapter);
    }
}