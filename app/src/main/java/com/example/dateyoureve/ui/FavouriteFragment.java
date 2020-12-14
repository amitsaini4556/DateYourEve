package com.example.dateyoureve.ui;

import android.os.Bundle;
import android.util.Log;
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

import com.example.dateyoureve.FavouriteDataAdapter;
import com.example.dateyoureve.MyHomeData;
import com.example.dateyoureve.R;
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


public class FavouriteFragment extends Fragment {
    private List<MyHomeData> myHomeData;
    DatabaseReference databaseReferenceFav,databaseReferenceEve;
    FavouriteDataAdapter myHomeAdapter;
    FirebaseUser mAuth;
    ProgressBar progressBar;
    String path;
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favourite, container, false);
        progressBar = root.findViewById(R.id.progressBarFav);
        progressBar.setVisibility(View.VISIBLE);
        textView = root.findViewById(R.id.oopsMessageFav);
        textView.setVisibility(View.GONE);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewFav);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myHomeData = new ArrayList<MyHomeData>();
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
                path = "users/" + mAuth.getUid() + "/FavEvents";
                databaseReferenceFav = FirebaseDatabase.getInstance().getReference(path);
                databaseReferenceFav.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        for(DataSnapshot dataFav : snapshot.getChildren())
                        {
                            String path = "Events/" + dataFav.getValue().toString();
                            databaseReferenceEve = FirebaseDatabase.getInstance().getReference(path);
                            databaseReferenceEve.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    myHomeData.add(snapshot.getValue(MyHomeData.class));
                                    myHomeAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            myHomeAdapter.notifyDataSetChanged();
                            Log.i("test",dataFav.getValue().toString());
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
        myHomeAdapter = new FavouriteDataAdapter(myHomeData,FavouriteFragment.this);
        recyclerView.setAdapter(myHomeAdapter);
        return root;
    }
}