package com.example.dateyoureve.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dateyoureve.MyHomeData;
import com.example.dateyoureve.R;
import com.example.dateyoureve.RegisteredEventsAdapter;
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


public class RecentFragment extends Fragment {
    private List<MyHomeData> myHomeData,myHomeDataFull;
    DatabaseReference databaseReferenceFav,databaseReferenceEve;
    RegisteredEventsAdapter registeredEventsAdapter;
    FirebaseUser mAuth;
    String path;
    ProgressBar progressBar;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recent, container, false);
        progressBar = root.findViewById(R.id.progressBarRegister);
        textView = root.findViewById(R.id.textViewRegister);
        progressBar.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewRegister);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myHomeData = new ArrayList<MyHomeData>();
        myHomeDataFull = new ArrayList<MyHomeData>();
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        path = "users/" + mAuth.getUid() + "/InterestedEvents";
        databaseReferenceFav = FirebaseDatabase.getInstance().getReference(path);
        databaseReferenceFav.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot dataFav : snapshot.getChildren())
                {
                    path = "Events/" + dataFav.getValue().toString();
                    databaseReferenceEve = FirebaseDatabase.getInstance().getReference(path);
                    databaseReferenceEve.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                myHomeData.add(snapshot.getValue(MyHomeData.class));
                                registeredEventsAdapter.notifyDataSetChanged();
                            }
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
        if(myHomeData.isEmpty())
        {
            progressBar.setVisibility(View.GONE);
        }
        registeredEventsAdapter = new RegisteredEventsAdapter(myHomeData,RecentFragment.this);
        recyclerView.setAdapter(registeredEventsAdapter);
        return root;
    }

}