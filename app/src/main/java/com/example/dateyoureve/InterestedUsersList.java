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

public class InterestedUsersList extends AppCompatActivity {
    InterestedUsersAdapter myAdapter;
    private List<User> interestedUsersData;
    DatabaseReference databaseReferenceFav,databaseReferenceEve;
    FirebaseUser mAuth;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested_users_list);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        interestedUsersData = new ArrayList<User>();
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        path = "Events/" + getIntent().getStringExtra("eventId") +"/InterestedPeoples";
        databaseReferenceFav = FirebaseDatabase.getInstance().getReference(path);
        databaseReferenceFav.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataFav : snapshot.getChildren())
                {
                    Log.i("first snapshot",dataFav.getValue().toString());
                    String path = "users/" + dataFav.getValue().toString();
                    databaseReferenceEve = FirebaseDatabase.getInstance().getReference("users");
                    databaseReferenceEve.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshotsecond) {
                            Log.i("test",snapshotsecond.getValue().toString());
                            interestedUsersData.add(snapshotsecond.getValue(User.class));
                            myAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    myAdapter.notifyDataSetChanged();
                    Log.i("test",dataFav.getValue().toString());
            }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myAdapter = new InterestedUsersAdapter(interestedUsersData,InterestedUsersList.this);
        recyclerView.setAdapter(myAdapter);
    }
}