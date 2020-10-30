package com.example.dateyoureve.ui;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dateyoureve.MyHomeAdapter;
import com.example.dateyoureve.MyHomeData;
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
//        lati = root.findViewById(R.id.lati);
//        longi = root.findViewById(R.id.longi);
//        cur = root.findViewById(R.id.cur);
//        loc = root.findViewById(R.id.loc);
//        add = root.findViewById(R.id.add);
//        lati.setText(s1);
//        longi.setText(s2);
//        cur.setText(s3);
//        loc.setText(s4);
//        add.setText(s5);
//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(),EventDetails.class);
//                startActivity(intent);
//            }
//        });
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });


        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MyHomeData[] myHomeData = new MyHomeData[]{
              new MyHomeData("Ecell, CTAE","To connect with entrepreneurial leaders, innovators and alumni of CTAE, Udaipur and engage them to foster the same passion and spirit in the current breed of students .","20/10/2020","CSE departmnet",R.drawable.download),
              new MyHomeData("Online Webinar","In this time of crisis we came across many students who have doubts regarding what next? Amongst many internship opportunities being cancelled, no sign of job interviews, even examination being delayed, we all have found ourselves wondering how can we utilise this time?","21/10/2020","CSE departmnet",R.drawable.i1),
              new MyHomeData("Something About ML","Machine learning is an application of artificial intelligence (AI) that provides systems the ability to automatically learn and improve from experience without being explicitly programmed. Machine learning focuses on the development of computer programs that can access data and use it learn for themselves","22/10/2020","CSE departmnet",R.drawable.i2),
              new MyHomeData("Programming...","Programming is the process of creating a set of instructions that tell a computer how to perform a task. Programming can be done using a variety of computer programming languages, such as JavaScript, Python, and C++. Created by Pamela Fox.","23/10/2020","CSE departmnet",R.drawable.i3),
              new MyHomeData("PrePlacement Talk","Pre-placement talks or we call it as the discussion about the company .Here we came to know about various aspects of a company and it tells us that if we are suitable enough to work for a company.","24/10/2020","CSE departmnet",R.drawable.i4),
        };
        MyHomeAdapter myHomeAdapter = new MyHomeAdapter(myHomeData,HomeFragment.this);
        recyclerView.setAdapter(myHomeAdapter);



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