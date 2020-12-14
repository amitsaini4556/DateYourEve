package com.example.dateyoureve;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    DatabaseReference databaseReferenceCreated,databaseReferenceEve,databaseReferenceCount;
    FirebaseUser mAuth;
    String path,count;
    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_events_list);
        progressBar = findViewById(R.id.progressBarPastEvent);
        progressBar.setVisibility(View.VISIBLE);
        Toolbar toolbar = findViewById(R.id.pasteventsList);
        textView = findViewById(R.id.oopsMessage);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    String path = "Events/" + dataSnapshot.getValue().toString() + "/InterestedPeoples";
                    databaseReferenceCount = FirebaseDatabase.getInstance().getReference(path);
                    databaseReferenceCount.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.i("count value",String.valueOf(snapshot.getChildrenCount()));
                            count = String.valueOf(snapshot.getChildrenCount());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    databaseReferenceEve = FirebaseDatabase.getInstance().getReference("Events/" + dataSnapshot.getValue().toString());
                    databaseReferenceEve.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            PastEventData obj = snapshot.getValue(PastEventData.class);
                            pastEventData.add(new PastEventData(obj.getTitle(),obj.getDate(),count,obj.getEventId()));
                            myAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    Log.i("inside for loop",dataSnapshot.getValue().toString());
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
        if(pastEventData.isEmpty())
        {
            progressBar.setVisibility(View.GONE);
        }
        myAdapter = new PastEventDataAdapter(pastEventData,PastEventsList.this);
        recyclerView.setAdapter(myAdapter);
    }
}