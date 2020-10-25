package com.example.dateyoureve.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.dateyoureve.EventDetails;
import com.example.dateyoureve.R;


public class HomeFragment extends Fragment {
    CardView cardView;
    private HomeViewModel homeViewModel;
    TextView lati,longi,cur,loc,add;
    String s1,s2,s3,s4,s5;
    public HomeFragment(String s1, String s2, String s3, String s4, String s5){
        this.s1=s1;
        this.s2=s2;
        this.s3=s3;
        this.s4=s4;
        this.s5=s5;
    }
    public HomeFragment () {}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        cardView = root.findViewById(R.id.cardview);
        lati = root.findViewById(R.id.lati);
        longi = root.findViewById(R.id.longi);
        cur = root.findViewById(R.id.cur);
        loc = root.findViewById(R.id.loc);
        add = root.findViewById(R.id.add);
        lati.setText(s1);
        longi.setText(s2);
        cur.setText(s3);
        loc.setText(s4);
        add.setText(s5);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),EventDetails.class);
                startActivity(intent);
            }
        });
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.appbar, menu);
    }

}