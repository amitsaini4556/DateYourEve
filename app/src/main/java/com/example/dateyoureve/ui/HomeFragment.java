package com.example.dateyoureve.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dateyoureve.MyHomeAdapter;
import com.example.dateyoureve.MyHomeData;
import com.example.dateyoureve.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private List<MyHomeData> myHomeData;
    String s1,s2,s3,s4,s5;
    public HomeFragment(String s1, String s2, String s3, String s4, String s5){
        this.s1=s1;
        this.s2=s2;
        this.s3=s3;
        this.s4=s4;
        this.s5=s5;
    }
    public HomeFragment () {
    }
    MyHomeAdapter myHomeAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });


        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myHomeData = new ArrayList<MyHomeData>();
              myHomeData.add(new MyHomeData("Ecell, CTAE","To connect with entrepreneurial leaders, innovators and alumni of CTAE, Udaipur and engage them to foster the same passion and spirit in the current breed of students .","20/10/2020","CSE departmnet",R.drawable.download));
              myHomeData.add(new MyHomeData("Online Webinar","In this time of crisis we came across many students who have doubts regarding what next? Amongst many internship opportunities being cancelled, no sign of job interviews, even examination being delayed, we all have found ourselves wondering how can we utilise this time?","21/10/2020","CSE departmnet",R.drawable.i1));
              myHomeData.add(new MyHomeData("Something About ML","Machine learning is an application of artificial intelligence (AI) that provides systems the ability to automatically learn and improve from experience without being explicitly programmed. Machine learning focuses on the development of computer programs that can access data and use it learn for themselves","22/10/2020","CSE departmnet",R.drawable.i2));
              myHomeData.add(new MyHomeData("Programming...","Programming is the process of creating a set of instructions that tell a computer how to perform a task. Programming can be done using a variety of computer programming languages, such as JavaScript, Python, and C++. Created by Pamela Fox.","23/10/2020","CSE departmnet",R.drawable.i3));
              myHomeData.add(new MyHomeData("PrePlacement Talk","Pre-placement talks or we call it as the discussion about the company .Here we came to know about various aspects of a company and it tells us that if we are suitable enough to work for a company.","24/10/2020","CSE departmnet",R.drawable.i4));
        myHomeAdapter = new MyHomeAdapter(myHomeData,HomeFragment.this);
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
        inflater.inflate(R.menu.appbar, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myHomeAdapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

}