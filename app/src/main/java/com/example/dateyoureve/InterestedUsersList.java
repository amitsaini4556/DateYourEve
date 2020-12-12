package com.example.dateyoureve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class InterestedUsersList extends AppCompatActivity {
    InterestedUsersAdapter myAdapter;
    private List<User> interestedUsersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested_users_list);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        interestedUsersData = new ArrayList<User>();
        interestedUsersData.add(new User("hema","10/01/2020","9461172491"));
        interestedUsersData.add(new User("amit","20/65/1523","7240770955"));
        interestedUsersData.add(new User("muskan","32/12/2021","9856478235"));
        myAdapter = new InterestedUsersAdapter(interestedUsersData,InterestedUsersList.this);
        recyclerView.setAdapter(myAdapter);
    }
}