package com.example.dateyoureve.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dateyoureve.R;
import com.example.dateyoureve.Sign_In;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    FloatingActionButton logout;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    TextView userName,email,phone,createdEvents,interestedEvents;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Details loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressBar = root.findViewById(R.id.loaderPro);
        progressBar.setVisibility(View.VISIBLE);
        mAuth = FirebaseAuth.getInstance();
        logout = root.findViewById(R.id.logout);
        userName = root.findViewById(R.id.userName);
        email = root.findViewById(R.id.textView31);
        phone = root.findViewById(R.id.textView33);
        createdEvents = root.findViewById(R.id.textView35);
        interestedEvents = root.findViewById(R.id.textView34);
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);
                userName.setText(snapshot.child("userName").getValue().toString());
                phone.setText(snapshot.child("phone").getValue().toString());
                email.setText(snapshot.child("email").getValue().toString());
                createdEvents.setText("Created Events +" + snapshot.child("createdEvents").getChildrenCount());
                interestedEvents.setText("Interested Events +" + snapshot.child("InterestedEvents").getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        progressDialog.dismiss();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("SignOuting...");
                progressDialog.show();
                mAuth.signOut();
                progressDialog.dismiss();
                Intent intent = new Intent(getContext(), Sign_In.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return root;
    }
}