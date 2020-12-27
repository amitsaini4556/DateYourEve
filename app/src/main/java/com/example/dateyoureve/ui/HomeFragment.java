package com.example.dateyoureve.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dateyoureve.MyHomeAdapter;
import com.example.dateyoureve.MyHomeData;
import com.example.dateyoureve.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.polyak.iconswitch.IconSwitch;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private List<MyHomeData> myHomeData;
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    String s1,s2,s3,s4,s5;
    RecyclerView recyclerView;
    public IconSwitch iconSwitch;
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
    boolean isDarkModeOn;
    SharedPreferences.Editor editor;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        if (isDarkModeOn) synchronized (this){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        progressBar = root.findViewById(R.id.loader);
        progressBar.setVisibility(View.VISIBLE);
        myHomeData = new ArrayList<MyHomeData>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Events");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataEvent : snapshot.getChildren())
                {
                    myHomeData.add(dataEvent.getValue(MyHomeData.class));
                }
                progressBar.setVisibility(View.GONE);
                myHomeAdapter = new MyHomeAdapter(myHomeData,HomeFragment.this);
                recyclerView.setAdapter(myHomeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        inflater.inflate(R.menu.appbar, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        MenuItem switchMenu = menu.findItem(R.id.app_bar_switch);
        switchMenu.setActionView(R.layout.switch_item);
        iconSwitch = switchMenu.getActionView().findViewById(R.id.app_switch);
        if (isDarkModeOn)
        {
            iconSwitch.setChecked(IconSwitch.Checked.RIGHT);
        }else
        {
            iconSwitch.setChecked(IconSwitch.Checked.LEFT);
        }
        iconSwitch.setCheckedChangeListener(current -> {
            switch (current) {

                case LEFT:
                    if (isDarkModeOn) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        editor.putBoolean("isDarkModeOn", false);
                        editor.apply();
                    }
                    break;

                case RIGHT:
                    if(!isDarkModeOn) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        editor.putBoolean("isDarkModeOn", true);
                        editor.apply();
                    }
                    break;
            }
        });
        SearchView searchView = (SearchView) searchItem.getActionView();
        List<MyHomeData> newDataList = new ArrayList();
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
    public SharedPreferences getSharedPreferences(String sharedPrefs, int modePrivate) {
        return this.getActivity().getSharedPreferences("shar", Context.MODE_PRIVATE);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}