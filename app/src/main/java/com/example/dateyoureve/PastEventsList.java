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
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    String path = "Events/" + dataSnapshot.getValue().toString();
                    databaseReferenceEve = FirebaseDatabase.getInstance().getReference(path);
                    databaseReferenceEve.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            DatabaseReference databaseReferenceCount;
                            String path1 = "Events/" + dataSnapshot.getValue().toString() + "InterestedPeoples";
                            databaseReferenceCount= FirebaseDatabase.getInstance().getReference(path1);



                           databaseReferenceCount.addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                   PastEventData obj = snapshot.getValue(PastEventData.class);
                                   obj.setInterested_count(snapshot.getChildrenCount());

                                   pastEventData.add(obj);
                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {

                               }
                           });



                            //pastEventData.add(snapshot.getValue(PastEventData.class));
                            myAdapter.notifyDataSetChanged();
                            Log.i("test",snapshot.getValue().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

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
        myAdapter = new PastEventDataAdapter(pastEventData,PastEventsList.this);
        recyclerView.setAdapter(myAdapter);
    }
}